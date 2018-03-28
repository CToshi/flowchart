package view.move;

import java.util.LinkedList;

import application.Main;
import entities.RectangleEntity;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ui.DrawPane;
import view.inter.Drawable;
import view.shape.ShapeItem;
import view.text_input.TextManager;

/**
 * 移动框，用于控制图形的移动
 *
 * @author Toshi
 *
 */
public class MoveFrame implements Drawable, Cloneable {

	public DraggableRectangle rectangle;
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

	private LinkedList<Node> nodeList;
	/**
	 * 唯一ID，用于撤销操作
	 */
	private int ID;
	private static int MOVE_FRAME_ID;

	private boolean isSelected;

	/**
	 *
	 * @param parent
	 *            该MoveFrame的父亲，即显示的Pane
	 */
	public MoveFrame(DrawPane parent, ShapeItem shapeItem) {
		this.ID = MOVE_FRAME_ID++;
		this.shapeItem = shapeItem;
		this.parent = parent;
		rectangle = new DraggableRectangle(shapeItem.getX(), shapeItem.getY(), shapeItem.getWidth(),
				shapeItem.getHeight()) {

			@Override
			protected void deal(double xDelta, double yDelta) {
				this.move(xDelta, yDelta);
				MoveFrame.this.fixPosition();
			}

			@Override
			protected void whenReleased(MouseEvent mouse) {
				fixPosition();
				setHasSelected(false);
				informChange();

			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				setHasSelected(true);
				setSelected(true, true);
			}

			@Override
			protected boolean isOutBound(double x, double y) {
				return parent.isOutBound(x, y);
			}
		};
		rectangle.setAppearence(Color.TRANSPARENT, Color.WHITE, 1);
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
		this.fixPosition();
		nodeList = new LinkedList<>();
		this.initNodeList();
		setHidden();
	}

	/**
	 * 告知parent自己被选中
	 *
	 * @param hasSelected
	 */
	void setHasSelected(boolean hasSelected) {
		parent.setHasSelected(hasSelected);
	}

	@Override
	public LinkedList<Node> getNodes() {
		return nodeList;
	}

	/**
	 * 纠正8个拖动点的坐标
	 */
	void fixPosition() {
		for (int i = 0; i < points.length; i++) {
			points[i].setCenterXY(rectangle.getX() + rectangle.getWidth() * offset[i][0],
					rectangle.getY() + rectangle.getHeight() * offset[i][1]);
		}
		shapeItem.setRectangle(rectangle.getRectangle());
	}

	/**
	 * 在8个点被拖动过程中会调用此函数，会隐藏这8个点
	 */
	void setHidden() {
		rectangle.setHide();
		for (int i = 0; i < points.length; i++) {
			points[i].setHide();
		}
	}

	/**
	 * 拖动结束后恢复显示
	 */
	void setShow() {
		rectangle.setShow();
		for (int i = 0; i < points.length; i++) {
			points[i].setShow();
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

	/**
	 * 是否显示移动框
	 *
	 * @param isSelected
	 *            true时显示，否则隐藏
	 * @param onlyOne
	 *            isSelected和onlyOne都为true且当前没有按下Ctrl键时，会调用parent(DrawPane)的closeOthers函数取消其它MoveFrame的选中
	 */
	public void setSelected(boolean isSelected, boolean onlyOne) {
		this.isSelected = isSelected;
		if (isSelected) {
			setShow();
			if (onlyOne && !parent.hasKey(KeyCode.CONTROL))
				parent.closeOthers(this);
		} else {
			setHidden();
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
		nodeList.addAll(rectangle.getNodes());
		for (int i = 0; i < points.length; i++) {
			nodeList.addAll(points[i].getNodes());
		}
	}

	public int getID() {
		return ID;
	}

	public RectangleEntity getRectangle() {
		return rectangle.getRectangle();
	}

	// @Override
	// public void setRectangle(RectangleEntity rectangle) {
	// setX(rectangle.getX());
	// setY(rectangle.getY());
	// setWidth(rectangle.getWidth());
	// setHeight(rectangle.getHeight());
	// }
	@Override
	public MoveFrame clone() {
//		try {
			MoveFrame frame = new MoveFrame(parent, shapeItem.clone());
//			MoveFrame frame = (MoveFrame) super.clone();
//			frame.shapeItem = frame.shapeItem.clone();
//			frame.rectangle = frame.rectangle.clone();
//			frame.points = new MovePoint[8];
			return frame;
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}
//		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MoveFrame) {
			return ((MoveFrame) obj).getID() == this.getID();
		}
		return false;
	}
	public void informChange(){
		parent.change(getID(), this);
	}
}
