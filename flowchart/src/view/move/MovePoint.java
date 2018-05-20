package view.move;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MovePoint extends DraggableRectangle{

	private static final double size = 10;
	private MoveFrame parent;
	private MovePoint other;
	private boolean xChangable;
	private boolean yChangable;

	public MovePoint(MoveFrame target, boolean xChangable, boolean yChangable, Cursor cursor) {
		super(0, 0, size, size, cursor, Color.BLACK);
		this.setWidth(size);
		this.setHeight(size);
		this.parent = target;
		this.xChangable = xChangable;
		this.yChangable = yChangable;
	}

	/**
	 * 设置对立点
	 * @param other
	 */
	public void setOtherPoint(MovePoint other) {
		this.other = other;
	}

	/**
	 * 处理鼠标移动的函数。
	 * 具体做法是，如果x可以改变(某些情况如该点是第一行的中间的点，此时拖动该点不能改变x)，则对比自己的坐标和对立点(other)的坐标, 取较小值。
	 * y同x
	 */
	@Override
	protected void deal(double xDelta, double yDelta) {
		if (xChangable) {
			this.setX(this.getX() + xDelta);
			parent.setX(Math.min(this.getCenterX(), other.getCenterX()));
			parent.setWidth(Math.abs(this.getCenterX() - other.getCenterX()));
		}
		if (yChangable) {
			this.setY(this.getY() + yDelta);
			parent.setY(Math.min(this.getCenterY(), other.getCenterY()));
			parent.setHeight(Math.abs(this.getCenterY() - other.getCenterY()));
		}
		parent.whenChanging();
	}

	@Override
	public void setCenterXY(double x, double y) {
		this.setCenterX(x);
		this.setCenterY(y);
	}

	@Override
	protected void whenPressed(MouseEvent mouse) {
		parent.setHidden(true);
	}

	@Override
	protected void whenReleased(MouseEvent mouse) {
		parent.fixPosition();
		parent.setHidden(false);
		parent.informChange();
	}

}
