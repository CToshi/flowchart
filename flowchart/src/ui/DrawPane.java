package ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import application.Main;
import datastructure.LimitedStack;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
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
public class DrawPane extends ScrollPane {
	// private DrawManager manager;

	private AnchorPane anchorPane;
	private RootPane parent;
	private boolean hasSelected;
	/**
	 * 存放MoveController, 用以取消选中、撤销和反撤销操作
	 */
	private HashMap<Integer, MoveController> oldMap;
	private HashMap<Integer, MoveController> map;

	private LimitedStack<Integer[], MoveController[]> unDoStack;
	private LimitedStack<Integer[], MoveController[]> reDoStack;

	private Rectangle selectRect;
	private static final int MAX_UNDO_TIMES = 100;

	public DrawPane(RootPane parent, DoubleExpression width, DoubleExpression height) {
		this.parent = parent;
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
		this.anchorPane = new AnchorPane();
		this.setOnMouseMoved(e->{
			Main.test(this.getHvalue());
		});
		this.setContent(this.anchorPane);
		// this.setOnMouseClicked(e->{
		// e.getClickCount();
		//
		// Main.test("鼠标", e.getX(), e.getY(), e.getClickCount());
		// });
		add(selectRect);
		Parent p = null;
		ScrollPane s = (ScrollPane) p;
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
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				if (hasSelected)
					return;
				/**
				 * 按下时，位置移动到按下点，长宽设为0，显示框颜色，关闭所有图形的选中
				 */
				selectRect.setX(mouse.getX());
				selectRect.setY(mouse.getY());
				selectRect.setWidth(0);
				selectRect.setHeight(0);
				selectRect.setStroke(Color.BLACK);
				// if (!DrawPane.this.hasSelected) {
				closeOthers(null);
				// }
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

	/**
	 * DrawPane会根据有没有MoveController被选中而执行动作，当没有MoveController被选中时DrawPane会设置所有MoveController为未选中状态
	 *
	 * @param hasSelected
	 */
	// public void setHasSelected(boolean hasSelected) {
	//
	// this.hasSelected = hasSelected;
	// }
	public void informSelected(MoveController controller, boolean isSelected) {
		this.hasSelected = isSelected;
		if (!hasKey(KeyCode.CONTROL) && isSelected) {
			closeOthers(controller);
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
			anchorPane.getChildren().add(e);
		}
	}

	private void add(LinkedList<Node> nodes) {
		add(nodes.toArray(new Node[0]));
	}

	public void deleteAllSelected() {
		for (Entry<Integer, MoveController> entry : map.entrySet()) {
			MoveController controller = entry.getValue();
			if (controller.isSelected()) {
				delete(controller);
			}
		}
	}

	private void delete(LinkedList<Node> nodes) {
		for (Node e : nodes) {
			anchorPane.getChildren().remove(e);
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
		MoveController[] oldcontrollers = new MoveController[ids.length];
		for (int i = 0; i < ids.length; i++) {
			oldcontrollers[i] = oldMap.get(ids[i]);
			if (!map.containsKey(ids[i])) {// 新建
				map.put(ids[i], newControllers[i]);
				add(newControllers[i].getNodes());
			}else if (newControllers[i] == null) {// 删除
				oldMap.remove(ids[i]);
				MoveController controller = map.get(ids[i]);
				map.remove(ids[i]);
				delete(controller.getNodes());
//				delete(oldControllers[i].getNodes());
			}

			if(newControllers[i] != null){
				oldMap.put(ids[i], newControllers[i].clone());
			}
		}
		unDoStack.push(ids, oldcontrollers);
		reDoStack.clear();
	}

	public void unDo() {
		unDo(this.unDoStack, this.reDoStack);
	}

	private void unDo(LimitedStack<Integer[], MoveController[]> unDoS, LimitedStack<Integer[], MoveController[]> reDoS) {
		if (unDoS.size() == 0) {
			return;
		}
		Pair<Integer[], MoveController[]> entry = unDoS.pop();
		Integer[] ids = entry.getKey();
		MoveController[] oldControllers = entry.getValue();
		MoveController[] nowControllers = new MoveController[ids.length];
		for (int i = 0; i < ids.length; i++) {
			nowControllers[i] = map.get(ids[i]);
			if (nowControllers[i] != null) {
				delete(nowControllers[i].getNodes());
			}
			if (oldControllers[i] != null) {
				add(oldControllers[i].getNodes());
				map.put(ids[i], oldControllers[i]);
				oldMap.put(ids[i], oldControllers[i].clone());
			} else {
				map.remove(ids[i]);
			}
		}
		reDoS.push(ids, nowControllers);
		//
	}

	public void reDo() {
		/**
		 * reDo和unDo是相反过程，只需要颠倒顺序即可
		 */
		unDo(this.reDoStack, this.unDoStack);
	}

	public void remove(Node... nodes) {
		for (Node node : nodes) {
			anchorPane.getChildren().remove(node);
		}
	}
}
