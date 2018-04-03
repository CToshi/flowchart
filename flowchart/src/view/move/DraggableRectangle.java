package view.move;

import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public abstract class DraggableRectangle extends Rectangle implements Cloneable{
	private PointEntity lastPosition;
	private PointEntity startPosition;
	private PointEntity mousePosition;
	private Cursor cursor;
	private Paint fill;
	private Paint stroke;

	public DraggableRectangle(double x, double y, double width, double height) {
		this(x, y, width, height, Cursor.DEFAULT);
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
	public DraggableRectangle(double x, double y, double width, double height, Cursor cursor) {
		super(x, y, width, height);
		this.cursor = cursor;
		initListener();
		lastPosition = new PointEntity(0, 0);
		startPosition = new PointEntity(this.getX(), this.getY());
		mousePosition = new PointEntity(0, 0);
		setAppearence(Color.BLACK, Color.BLACK, 0);
	}

	/**
	 * 鼠标按下、鼠标拖动、鼠标释放的监听器初始化
	 */
	private void initListener() {
		this.setOnMousePressed(e -> {
			lastPosition.setXY(e.getX(), e.getY());
			startPosition.setXY(this.getX(), this.getY());
			whenPressed(e);
		});
		this.setOnMouseDragged(e -> {
			if (isOutBound(e.getX(), e.getY())) {
				return;
			}
			mousePosition.setXY(e.getX(), e.getY());
			double xDelta = e.getX() - lastPosition.getX();
			double yDelta = e.getY() - lastPosition.getY();
			this.deal(xDelta, yDelta);
			lastPosition.setXY(e.getX(), e.getY());
		});
		this.setOnMouseReleased(e -> {
			if (isOutBound(e.getX(), e.getY())) {
				this.setX(startPosition.getX());
				this.setY(startPosition.getY());
			}
			whenReleased(e);
		});
		if (this.cursor != Cursor.DEFAULT) {
			this.setOnMouseMoved(e -> {
				this.setCursor(this.cursor);
			});
		}
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
	protected abstract boolean isOutBound(double x, double y);

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

	public PointEntity getMouse() {
		return mousePosition;
	}

	public PointEntity getLastMouses() {
		return lastPosition;
	}

	public void setHide() {
		this.setStroke(Color.TRANSPARENT);
		this.setFill(Color.TRANSPARENT);
	}

	public void setShow() {
		this.setStroke(stroke);
		this.setFill(fill);
	}

	public RectangleEntity getRectangle() {
		return new RectangleEntity(this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}
	public void setAppearence(Color fill, Color stroke, double strokeWidth){
		this.fill = fill;
		this.stroke = stroke;
		this.setFill(fill);
		this.setStroke(stroke);
		this.setStrokeWidth(strokeWidth);
	}

}
