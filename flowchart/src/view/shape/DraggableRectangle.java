package view.shape;

import application.Main;
import entities.PointEntity;
import javafx.scene.Cursor;
import javafx.scene.shape.Rectangle;
import view.Draggable;
import view.move.RotatePoint;

public abstract class DraggableRectangle extends Rectangle implements Draggable {
	private PointEntity lastPosition;
	private PointEntity startPosition;
	private boolean isOutBound;
	private Cursor cursor;

	public DraggableRectangle() {
		this(100, 100, 100, 100, Cursor.DEFAULT);
	}

	public DraggableRectangle(double x, double y, double width, double height, Cursor cursor) {
		super(x, y, width, height);
		this.cursor = cursor;
		initListener();
	}

	private void initListener() {
		this.setOnMousePressed(e -> {
			lastPosition = new PointEntity(e.getX(), e.getY());
			startPosition = new PointEntity(this.getX(), this.getY());
			isOutBound = false;
			whenClicked();
		});
		this.setOnMouseDragged(e -> {
			if (isOutBound) {
				return;
			}
			double xDelta = e.getX() - lastPosition.getX();
			double yDelta = e.getY() - lastPosition.getY();
			lastPosition.setXY(e.getX(), e.getY());
			this.deal(xDelta, yDelta);
		});
		this.setOnMouseReleased(e -> {
			if (isOutBound) {
				this.setX(startPosition.getX());
				this.setY(startPosition.getY());
			}
			whenReleased();
		});
		if (this.cursor != Cursor.DEFAULT) {
			this.setOnMouseMoved(e -> {
				this.setCursor(this.cursor);
			});
		}
	}

	protected void whenClicked() {

	}

	protected abstract void whenReleased();

	@Override
	public void setOutBound(boolean isOutBound) {
		this.isOutBound = isOutBound;
	}

	protected void move(double xDelta, double yDelta) {
		this.setX(this.getX() + xDelta);
		this.setY(this.getY() + yDelta);
	}

	protected abstract void deal(double xDelta, double yDelta);

	public void setCenterXY(double x, double y) {
		this.setCenterX(x);
		this.setCenterY(y);
	}

	public void setCenterX(double value) {
		this.setX(value - this.getWidth() / 2f);
	}

	public void setCenterY(double value) {
		this.setY(value - this.getHeight() / 2f);
	}

	public double getCenterX() {
		return this.getX() + this.getWidth() / 2f;
	}

	public double getCenterY() {
		return this.getY() + this.getHeight() / 2f;
	}
	public PointEntity getMouse(){
		return lastPosition;
	}
}
