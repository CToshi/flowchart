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
	 *            ���ŵ��þ�����ʱ�������ʽ
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
	 * ��갴�¡�����϶�������ͷŵļ�������ʼ��
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
	 * ��갴�º��ִ�иú���
	 *
	 * @param mouse
	 *            ��ǰ���
	 */
	protected abstract void whenPressed(MouseEvent mouse);

	/**
	 * ��굯����ִ�иú���
	 *
	 * @param mouse
	 *            ��ǰ���
	 */
	protected abstract void whenReleased(MouseEvent mouse);

	/**
	 * �ж��Ƿ�Խ�硣����굯��ʱ���������Ƶ����⣨���ú�������true)������Դ˴��϶����ص��϶�ǰ��λ��
	 *
	 * @param x
	 *            ����x����
	 * @param y
	 *            ����y����
	 * @return
	 */
	protected abstract boolean isOutBound(double x, double y);

	protected void move(double xDelta, double yDelta) {
		this.setX(this.getX() + xDelta);
		this.setY(this.getY() + yDelta);
	}

	/**
	 * ����괦���϶�״̬ʱ��ÿ������ƶ�������ô˺���
	 *
	 * @param xDelta
	 *            �˴��ƶ���x�仯��
	 * @param yDelta
	 *            �˴��ƶ���y�仯��
	 */
	protected abstract void deal(double xDelta, double yDelta);

	public void setCenterXY(double x, double y) {
		this.setCenterX(x);
		this.setCenterY(y);
	}

	/**
	 * ���þ������ĵ��x����
	 *
	 * @param value
	 */
	public void setCenterX(double value) {
		this.setX(value - this.getWidth() / 2f);
	}

	/**
	 * ���þ������ĵ��y����
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
