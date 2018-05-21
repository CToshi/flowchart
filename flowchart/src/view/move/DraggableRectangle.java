package view.move;

import java.util.LinkedList;

import entities.RectangleEntity;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import utility.Util;
import view.inter.Changeable;
import view.inter.Draggable;
import view.inter.Drawable;

public abstract class DraggableRectangle implements Changeable, Drawable {
	private Rectangle self;

	private boolean isHidden;
	private RectangleEntity rectangle;

	public DraggableRectangle(double x, double y, double width, double height) {
		this(x, y, width, height, Cursor.DEFAULT, Color.TRANSPARENT);
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param cursor
	 *            鼠标放到该矩形上时的鼠标样式
	 */
	public DraggableRectangle(double x, double y, double width, double height, Cursor cursor, Color fill) {
		self = new Rectangle(x, y, width, height);
		rectangle = new RectangleEntity(self);
		self.setFill(fill);
		new Draggable() {

			@Override
			protected void whenReleased(MouseEvent mouse) {
				DraggableRectangle.this.whenReleased(mouse);
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				DraggableRectangle.this.whenPressed(mouse);
			}

			@Override
			protected void whenMoved(MouseEvent mouse) {
				self.setCursor(cursor);
			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				DraggableRectangle.this.deal(xDelta, yDelta);
			}

			@Override
			protected Node getNode() {
				return self;
			}
		};

	}


	/**
	 * 鼠标按下后会执行该函数
	 *
	 * @param mouse
	 *            当前鼠标
	 */
	protected abstract void whenPressed(MouseEvent mouse);

	/**
	 * 鼠标弹起后会执行该函数
	 *
	 * @param mouse
	 *            当前鼠标
	 */
	protected abstract void whenReleased(MouseEvent mouse);

	/**
	 * 判断是否越界。当鼠标弹起时，如果鼠标移到界外（即该函数返回true)，会忽略此次拖动，回到拖动前的位置
	 *
	 * @param x
	 *            鼠标的x坐标
	 * @param y
	 *            鼠标的y坐标
	 * @return
	 */
//	protected abstract boolean isOutBound(double x, double y);

	protected void move(double xDelta, double yDelta) {
		this.setX(this.getX() + xDelta);
		this.setY(this.getY() + yDelta);
	}

	/**
	 * 当鼠标处于拖动状态时，每次鼠标移动都会调用此函数
	 *
	 * @param xDelta
	 *            此次移动的x变化量
	 * @param yDelta
	 *            此次移动的y变化量
	 */
	protected abstract void deal(double xDelta, double yDelta);

	public void setCenterXY(double x, double y) {
		this.setCenterX(x);
		this.setCenterY(y);
	}

	/**
	 * 设置矩形中心点的x坐标
	 *
	 * @param value
	 */
	public void setCenterX(double value) {
		this.setX(value - this.getWidth() / 2f);
	}

	/**
	 * 设置矩形中心点的y坐标
	 *
	 * @param value
	 */
	public void setCenterY(double value) {
		this.setY(value - this.getHeight() / 2f);
	}

	public double getCenterX() {
		return this.getX() + this.getWidth() / 2f;
	}

	public double getCenterY() {
		return this.getY() + this.getHeight() / 2f;
	}

//	public PointEntity getMouse() {
//		return mousePosition;
//	}
//
//	public PointEntity getLastMouses() {
//		return lastPosition;
//	}

	public void setHidden(boolean isHidden) {
		if (this.isHidden == isHidden)
			return;
		this.isHidden = isHidden;
		if (isHidden) {
			// rectangle = getRectangle();
			saveState();
			setRectangle(new RectangleEntity(10, 10, 0, 0), false);
		} else {
			getBack();
			setRectangle(rectangle);
		}
	}

	/**
	 * 会new一个新的rectangle返回
	 */
	@Override
	public RectangleEntity getRectangle() {
		return rectangle.clone();
	}


	@Override
	public LinkedList<Node> getNodes() {
		return Util.getList(self);
	}

	@Override
	public void setX(double value) {
		self.setX(value);
		rectangle.setX(value);
	}

	@Override
	public void setY(double value) {
		self.setY(value);
		rectangle.setY(value);
	}

	@Override
	public void setWidth(double value) {
		self.setWidth(value);
		rectangle.setWidth(value);
	}

	@Override
	public void setHeight(double value) {
		self.setHeight(value);
		rectangle.setHeight(value);
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		setRectangle(rectangle, true);
	}

	private void setRectangle(RectangleEntity rectangle, boolean saveState) {
		self.setX(rectangle.getX());
		self.setY(rectangle.getY());
		self.setWidth(rectangle.getWidth());
		self.setHeight(rectangle.getHeight());
		if (saveState) {
			this.rectangle = rectangle;
		}
	}

	private void saveState() {
		this.rectangle = new RectangleEntity(self);
	}

	private void getBack() {
		setRectangle(this.rectangle);
	}

	public void setStroke(Paint value) {
		self.setStroke(value);
	}

}
