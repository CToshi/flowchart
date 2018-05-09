package ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import controller.ShapeCreationController;
import datastructure.LimitedStack;
import entities.DrawableState;
import entities.DrawableState.Type;
import entities.PointEntity;
import entities.RectangleEntity;
import factory.MoveControllerFactory;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import view.inter.Draggable;
import view.move.MoveController;

/**
 * 图形的绘制区域
 *
 * @author Toshi
 *
 */
public class DrawPane extends Pane {
	// private DrawManager manager;

	private RootPane parent;
	// private boolean hasSelected;
	/**
	 * 存放MoveController, 用以取消选中、撤销和反撤销操作
	 */
	private HashMap<Integer, DrawableState> oldMap;
	private HashMap<Integer, MoveController> map;

	private LimitedStack<Integer[], DrawableState[]> unDoStack;
	private LimitedStack<Integer[], DrawableState[]> reDoStack;

	private Rectangle selectRect;
	private static final int MAX_UNDO_TIMES = 100;
	private int selectedCount;
//	private int controllerID;
	private Type shapeCreationType;

	public DrawPane(RootPane parent, DoubleExpression width, DoubleExpression height) {
		this.parent = parent;
//		this.controllerID = 0;
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);

		this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10),
				new BorderWidths(1));
		Border border = new Border(borderStroke);
		this.setBorder(border);

		map = new HashMap<>();
		oldMap = new HashMap<>();
		selectRect = new Rectangle();
		selectRect.setFill(Color.TRANSPARENT);
		this.minWidthProperty().bind(width.subtract(100));
		this.minHeightProperty().bind(height.subtract(100));
		add(selectRect);
		new Draggable() {
			@Override
			protected void whenReleased(MouseEvent mouse) {
				/**
				 * 放开时，隐藏框，遍历所有MoveController，如果该controller处于选中框(selectRect)中，则设置为选中
				 */
				selectRect.setStroke(Color.TRANSPARENT);
				RectangleEntity rectE = new RectangleEntity(selectRect);
				for (Entry<Integer, MoveController> entry : map.entrySet()) {
					MoveController controller = entry.getValue();
					if (rectE.contains(controller.getRectangle())) {
						controller.setSelected(true);
					}
				}
				/**
				 * 移除屏幕，否则会影响点击空白判定
				 */
				selectRect.setX(-100);
				selectRect.setWidth(0);
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				boolean clickNothing = mouse.getTarget().equals(DrawPane.this);
				if (!clickNothing) {
					return;
				}
				if (shapeCreationType != null) {
					add(MoveControllerFactory.create(mouse.getX(), mouse.getY(), true, shapeCreationType));
					shapeCreationType = null;
					ShapeCreationController.createFinished();
					return;
				}
				selectedCount = 0;
				/**
				 * 按下时，位置移动到按下点，长宽设为0，显示框颜色，关闭所有图形的选中
				 */
				selectRect.setX(mouse.getX());
				selectRect.setY(mouse.getY());
				selectRect.setWidth(0);
				selectRect.setHeight(0);
				selectRect.setStroke(Color.BLACK);
				closeOthers(null);
			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				selectRect.setX(Math.min(getMousePosition().getX(), getStartPosition().getX()));
				selectRect.setY(Math.min(getMousePosition().getY(), getStartPosition().getY()));
				selectRect.setWidth(Math.abs(getMousePosition().getX() - getStartPosition().getX()));
				selectRect.setHeight(Math.abs(getMousePosition().getY() - getStartPosition().getY()));
			}

			@Override
			protected Node getNode() {
				return DrawPane.this;
			}

			@Override
			protected void whenMoved(MouseEvent mouse) {
				// TODO Auto-generated method stub

			}
		};
		unDoStack = new LimitedStack<>(MAX_UNDO_TIMES);
		reDoStack = new LimitedStack<>(MAX_UNDO_TIMES);
		parent.add(new KeyListener(KeyCode.DELETE) {
			@Override
			public void run() {
				deleteAllSelected();
			}
		});
		parent.add(new KeyListener(KeyCode.CONTROL, KeyCode.Z) {
			@Override
			public void run() {
				unDo();
			}
		});
		parent.add(new KeyListener(KeyCode.CONTROL, KeyCode.Y) {
			@Override
			public void run() {
				reDo();
			}
		});
	}

	public boolean isOutBound(double x, double y) {
		return !(0 <= x && x <= this.getWidth() && 0 <= y && y <= this.getHeight());
	}

	public void informSelected(MoveController frame) {
		if (selectedCount == 0 || hasKey(KeyCode.CONTROL)) {
			selectedCount++;
		} else if (selectedCount == 1 && !hasKey(KeyCode.CONTROL)) {
			closeOthers(frame);
		}
	}

	/**
	 *
	 * @param controller
	 */
	public void add(MoveController controller) {
		change(controller.getID(), controller);
	}

	public void delete(MoveController controller) {
		change(controller.getID(), null);
	}

	/**
	 * 将nodes加到DrawPane中显示出来
	 *
	 * @param nodes
	 *
	 */
	public void add(Node... nodes) {
		for (Node e : nodes) {
			this.getChildren().add(e);
		}
	}

	private void add(LinkedList<Node> nodes) {
		add(nodes.toArray(new Node[0]));
	}

	public void deleteAllSelected() {
		LinkedList<MoveController> list = new LinkedList<>();
		for (Entry<Integer, MoveController> entry : map.entrySet()) {
			MoveController controller = entry.getValue();
			if (controller.isSelected()) {
				list.add(controller);
				// delete(controller);
			}
		}
		for (MoveController controller : list) {
			delete(controller);
		}
	}

	private void delete(LinkedList<Node> nodes) {
		for (Node e : nodes) {
			this.getChildren().remove(e);
		}
	}

	public PointEntity getCenter() {
		return new PointEntity(this.getWidth() / 2f, this.getHeight() / 2f);
	}

	/**
	 * 关闭除controller外所有MoveController的选中状态
	 *
	 * @param controller
	 *            不关闭的那个controller，为null时关闭所有MoveController的选中
	 */
	public void closeOthers(MoveController controller) {
		for (Entry<Integer, MoveController> entry : map.entrySet()) {
			if (!entry.getValue().equals(controller)) {
				entry.getValue().setSelected(false);
			}
		}
	}

	public boolean hasKey(KeyCode... keyCodes) {
		return parent.hasKey(keyCodes);
	}

	public void change(int id, MoveController newController) {
		Integer[] ids = { id };
		MoveController[] newControllers = { newController };
		change(ids, newControllers);
	}

	public void change(Integer[] ids, MoveController[] newControllers) {
		DrawableState[] oldStates = new DrawableState[ids.length];
		for (int i = 0; i < ids.length; i++) {
			oldStates[i] = oldMap.get(ids[i]);
			if (!map.containsKey(ids[i])) {// 新建
				map.put(ids[i], newControllers[i]);
				add(newControllers[i].getNodes());
			} else if (newControllers[i] == null) {// 删除
				oldMap.remove(ids[i]);
				MoveController controller = map.get(ids[i]);
				map.remove(ids[i]);
				delete(controller.getNodes());
			}
			if (newControllers[i] != null) {
				oldMap.put(ids[i], newControllers[i].getState());
			}
		}
		unDoStack.push(ids, oldStates);
		reDoStack.clear();
	}

	public void unDo() {
		unDo(this.unDoStack, this.reDoStack);
	}

	private void unDo(LimitedStack<Integer[], DrawableState[]> unDoS,
			LimitedStack<Integer[], DrawableState[]> reDoS) {
		if (unDoS.size() == 0) {
			return;
		}
		/**
		 * 撤销时，需要维护map, oldMap, undoStack, redoStack
		 */
		Pair<Integer[], DrawableState[]> entry = unDoS.pop();
		Integer[] ids = entry.getKey();
		MoveController[] oldControllers = new MoveController[ids.length];
		DrawableState[] oldStates = new DrawableState[ids.length];
		DrawableState[] newStates = entry.getValue();
		for (int i = 0; i < ids.length; i++) {
			oldControllers[i] = map.get(ids[i]);
			if(oldControllers[i] != null && newStates[i] != null){//改变
				oldControllers[i].setState(newStates[i]);
				oldMap.put(ids[i], newStates[i]);
			}else if (newStates[i] == null){//旧状态为空，需要删除
				delete(oldControllers[i].getNodes());
				map.remove(ids[i]);
				oldMap.remove(ids[i]);
			}else if(oldControllers[i] == null){//当前不存在屏幕上，则需要新建
				MoveController mc = MoveControllerFactory.create(newStates[i]);
				this.add(mc.getNodes());
				map.put(ids[i], mc);
				oldMap.put(ids[i], newStates[i]);
			}
			if(oldControllers[i] != null){
				oldStates[i] = oldControllers[i].getState();
			}
//			if (nowControllers[i] != null) {
//				if(oldStates[i] != null){
//					nowControllers[i].setState(oldStates[i]);
//				}else{
//					delete(nowControllers[i].getNodes());
//					map.remove(ids[i]);
//				}
//			}else{
//				Main.test(nowControllers[i], oldStates[i]);
//				MoveController mc = MoveControllerFactory.create(oldStates[i]);
//				this.add(MoveControllerFactory.create(oldStates[i]));
//				map.put(ids[i], mc);
//			}
		}
		reDoS.push(ids, oldStates);
	}

	public void reDo() {
		/**
		 * reDo和unDo是相反过程，只需要颠倒顺序即可
		 */
		unDo(this.reDoStack, this.unDoStack);
	}

	public void remove(Node... nodes) {
		for (Node node : nodes) {
			this.getChildren().remove(node);
		}
	}

//	public int getControllerID() {
//		return controllerID++;
//	}

	public void setShapeCreationType(Type shapeCreationType) {
		this.shapeCreationType = shapeCreationType;
	}
}
