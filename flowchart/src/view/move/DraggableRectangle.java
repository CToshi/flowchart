package view.move;

import java.util.LinkedList;

import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import utility.Util;
import view.inter.Changeable;
import view.inter.Drawable;

public abstract class DraggableRectangle implements Changeable, Drawable {
	private Rectangle self;

	private PointEntity lastPosition;
	private PointEntity startPosition;
	private PointEntity mousePosition;
	private Cursor cursor;
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
	 *            ���ŵ��þ�����ʱ�������ʽ
	 */
	public DraggableRectangle(double x, double y, double width, double height, Cursor cursor, Color fill) {
		self = new Rectangle(x, y, width, height);
		rectangle = new RectangleEntity(self);
		this.cursor = cursor;
		initListener();
		lastPosition = new PointEntity(0, 0);
		startPosition = new PointEntity(this.getX(), this.getY());
		mousePosition = new PointEntity(0, 0);
		self.setFill(fill);
	}

	/**
	 * ��갴�¡�����϶�������ͷŵļ�������ʼ��
	 */
	private void initListener() {
		self.setOnMousePressed(e -> {
			lastPosition.setXY(e.getX(), e.getY());
			startPosition.setXY(this.getX(), this.getY());
			whenPressed(e);
		});
		self.setOnMouseDragged(e -> {
			mousePosition.setXY(e.getX(), e.getY());
			double xDelta = e.getX() - lastPosition.getX();
			double yDelta = e.getY() - lastPosition.getY();
			this.deal(xDelta, yDelta);
			lastPosition.setXY(e.getX(), e.getY());
		});
		self.setOnMouseReleased(e -> {
			whenReleased(e);
		});
		if (this.cursor != Cursor.DEFAULT) {
			self.setOnMouseMoved(e -> {
				self.setCursor(this.cursor);
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
//	protected abstract boolean isOutBound(double x, double y);

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
	 * ��newһ���µ�rectangle����
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
