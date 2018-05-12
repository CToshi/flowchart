package view.move;

import java.util.LinkedList;

import entities.DrawableState;
import entities.PointEntity;
import entities.RectangleEntity;
import entities.ShapeState;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ui.DrawPane;
import utility.Util;
import view.shape.ShapeItem;
import view.text_input.InputController;
import view.text_input.TextManager;

/**
 * 移动框，用于控制图形的移动
 *
 * @author Toshi
 *
 */
public class MoveFrame implements MoveController {

	private LinkedList<MoveController> connections;
	private DraggableRectangle rectangle;
	/**
	 * 移动框的8个点
	 */
	private MovePoint[] points;
	/**
	 * 8个点相对于rectangle的左上角坐标的坐标偏移量, offset[i][0]是第i个点的x偏移量，offset[i][1]是y偏移量
	 * x偏移量表示的是rectangle.width的倍数 ,y则是height, offset从0到7的8个点分别是 0 4 7 2 3 8 5 1
	 * 可以看到相邻的两个点的位置刚好是对立的，方便移动时的坐标确定
	 *
	 */
	private static final double offset[][] = { { 0, 0 }, { 1, 1 }, { 0, 0.5 }, { 1, 0.5 }, { 0.5, 0 }, { 0.5, 1 },
			{ 1, 0 }, { 0, 1 } };
	/**
	 * 鼠标样式，对应关系同offset
	 */
	private static final Cursor[] CURSORS = { Cursor.SE_RESIZE, Cursor.E_RESIZE, Cursor.S_RESIZE, Cursor.SW_RESIZE };

	private DrawPane parent;

	/**
	 * 显现的图形
	 */
	private ShapeItem shapeItem;
	private TextManager textManager;
	private InputController inputController;

	private LinkedList<Node> nodeList;
	/**
	 * 唯一ID，用于撤销操作
	 */
	private int ID;
	// private static int MOVE_FRAME_ID;

	private boolean isSelected;

	private boolean isInputIng;

	/**
	 *
	 * @param parent
	 *            该MoveFrame的父亲，即显示的Pane
	 * @param shapeItem
	 *            MoveFrame的初始位置由ShapeItem决定
	 * @param isClone
	 *            clone时ID不会自增
	 *
	 */
	public MoveFrame(DrawPane parent, ShapeItem shapeItem, int ID) {
		this.ID = ID;
		this.shapeItem = shapeItem;
		this.parent = parent;
		this.inputController = InputController.getInstance();
		this.connections = new LinkedList<MoveController>();
		rectangle = new DraggableRectangle(shapeItem.getX(), shapeItem.getY(), shapeItem.getWidth(),
				shapeItem.getHeight(), Cursor.MOVE, Color.TRANSPARENT) {
			private RectangleEntity lastRect;

			@Override
			protected void deal(double xDelta, double yDelta) {
				this.move(xDelta, yDelta);
				MoveFrame.this.fixPosition();
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				lastRect = this.getRectangle();
				if (!isSelected) {
					parent.informSelected(MoveFrame.this);
				}
				setSelected(true);
				if (mouse.getClickCount() >= 2) {
					isInputIng = true;
					inputController.getTextArea().setText(textManager.getText());
					inputController.setInformation(shapeItem.getTextRectangle(), textManager.getText());
					parent.add(inputController.getTextArea());
				}
			}

			@Override
			protected void whenReleased(MouseEvent mouse) {
				if (!getRectangle().equals(lastRect)) {
					fixPosition();
					informChange();
				}
			}

			@Override
			protected boolean isOutBound(double x, double y) {
				return parent.isOutBound(x, y);
			}

		};
		// rectangle.setAppearence(Color.TRANSPARENT, Color.BLACK, 1);
		points = new MovePoint[8];
		/**
		 * offset的值为0.5时，该项不可改变，因为处于中间, 因为怕double有精度问题故用abs(x - 0.5) > eps 代替 x
		 * != 0.5
		 */
		for (int i = 0; i < points.length; i++) {
			points[i] = new MovePoint(this, Math.abs(offset[i][0] - 0.5) > 0.0001,
					Math.abs(offset[i][1] - 0.5) > 0.0001, CURSORS[i / 2]);
		}
		for (int i = 0; i < points.length; i++) {
			points[i].setOtherPoint(points[i ^ 1]);
		}
		textManager = new TextManager(shapeItem.getTextRectangle());
		this.setHidden(true);
		nodeList = new LinkedList<>();
		this.initNodeList();
		this.fixPosition();
	}


	@Override
	public LinkedList<Node> getNodes() {
		return nodeList;
	}

	/**
	 * 纠正8个拖动点、shapeItem, textManager的坐标
	 */
	void fixPosition() {
		// Main.test("fix");
		// for (int i = 0; i < points.length; i++) {
		// points[i].setCenterXY(rectangle.getX() + rectangle.getWidth() *
		// offset[i][0],
		// rectangle.getY() + rectangle.getHeight() * offset[i][1]);
		// }
		// RectangleEntity rect = rectangle.getRectangle();
		// shapeItem.setRectangle(rect);
		// textManager.setRectangle(shapeItem.getTextRectangle());
		fixPosition(true);
	}

	private void fixPosition(boolean needFixPoints) {
		if (needFixPoints) {
			for (int i = 0; i < points.length; i++) {
				points[i].setCenterXY(rectangle.getX() + rectangle.getWidth() * offset[i][0],
						rectangle.getY() + rectangle.getHeight() * offset[i][1]);
			}
		}
		RectangleEntity rect = rectangle.getRectangle();
		shapeItem.setRectangle(rect);
		textManager.setRectangle(shapeItem.getTextRectangle());
	}

	public void setHidden(boolean isHidden){
//		rectangle.setHidden(isHidden);
		for (int i = 0; i < points.length; i++) {
			points[i].setHidden(isHidden);
		}
		if(isHidden){
			this.closeInput();
		}
	}
	public void setX(double value) {
		rectangle.setX(value);
		shapeItem.setX(value);
	}

	public void setY(double value) {
		rectangle.setY(value);
		shapeItem.setY(value);
	}

	public void setWidth(double value) {
		rectangle.setWidth(value);
		shapeItem.setWidth(value);
	}

	public void setHeight(double value) {
		rectangle.setHeight(value);
		shapeItem.setHeight(value);
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if (isSelected) {
			setHidden(false);
		} else {
			setHidden(true);
		}
	}

	public boolean isSelected() {
		return isSelected;
	}

	/**
	 *
	 * @return 数组形式的nodeList里的Node
	 */

	private void initNodeList() {
		nodeList.clear();
		nodeList.addAll(shapeItem.getNodes());
		for (int i = 0; i < points.length; i++) {
			nodeList.addAll(points[i].getNodes());
		}
		nodeList.addAll(textManager.getNodes());
		nodeList.addAll(rectangle.getNodes());
	}

	public int getID() {
		return ID;
	}

	public RectangleEntity getRectangle() {
		return rectangle.getRectangle();
	}

	// @Override
	// public MoveFrame clone() {
	// MoveFrame frame = new MoveFrame(parent, shapeItem.clone(), true);
	// frame.ID = ID;
	// return frame;
	// }

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MoveFrame) {
			return ((MoveFrame) obj).getID() == this.getID();
		}
		return false;
	}

	public void informChange() {
		parent.change(getID(), this);
	}

	public void remove(Node... nodes) {
		parent.remove(nodes);
	}

	public void add(Node... nodes) {
		parent.add(nodes);
	}

	private void closeInput() {
		if (isInputIng) {
			textManager.setText(inputController.getTextArea().getText());
			parent.remove(inputController.getTextArea());
			isInputIng = false;
		}
	}

	@Override
	public DrawableState getState() {
		return new ShapeState(getRectangle(), textManager.getText(), shapeItem.getType(), getID());
	}

	@Override
	public void setState(DrawableState state) {
		ShapeState shapeState = (ShapeState) state;
		rectangle.setRectangle(shapeState.getRectangle());
		textManager.setText(shapeState.getText());
		fixPosition();
		this.ID = state.getID();
	}

	public void whenChanging() {
		fixPosition(false);
	}

	public void changeFinished() {
		textManager.setHidden(false);
	}


	@Override
	public LinkedList<PointEntity> getConnectionPoints() {
		LinkedList<PointEntity> linkPoints = new LinkedList<PointEntity>();
		for (int i = 0; i < 8; i++) {
			if (Util.isEquals(offset[i][0], 0.5) || Util.isEquals(offset[i][1], 0.5)) {
				linkPoints.add(new PointEntity(points[i].getX(),points[i].getY()));
			}
		}
		return linkPoints;
	}


	@Override
	public void addConnection(MoveController moveController) {
		connections.add(moveController);
	}


	@Override
	public void removeConnection(MoveController moveController) {
		if(connections.contains(moveController)){
			connections.remove(moveController);
		}
	}


//	public MovePoint[] getLinkPoints() {
//		MovePoint[] linkPoints = new MovePoint[4];
//		int index = 0;
//		for (int i = 0; i < 8; i++) {
//			if (Util.isEquals(offset[i][0], 0.5) || Util.isEquals(offset[i][1], 0.5)) {
//				linkPoints[index++] = points[i];
//			}
//		}
//		return linkPoints;
//	}
}
