package view.inter;

import entities.PointEntity;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class Draggable implements Changable {
	private PointEntity lastPosition;
	private PointEntity startPosition;
	private PointEntity mousePosition;
	private boolean isOutBound;
	private Cursor cursor;

	public Draggable(double x, double y, double width, double height, Cursor cursor) {
		lastPosition = new PointEntity(0, 0);
		startPosition = new PointEntity(this.getX(), this.getY());
		mousePosition = new PointEntity(0, 0);
		initListener();
	}

	protected abstract Node getNode();

	private void initListener() {
		getNode().setOnMousePressed(e -> {
			lastPosition.setXY(e.getX(), e.getY());
			startPosition.setXY(this.getX(), this.getY());
			isOutBound = false;
			whenClicked(e);
		});
		getNode().setOnMouseDragged(e -> {
			if (isOutBound) {
				return;
			}
			mousePosition.setXY(e.getX(), e.getY());
			double xDelta = e.getX() - lastPosition.getX();
			double yDelta = e.getY() - lastPosition.getY();
			this.deal(xDelta, yDelta);
			lastPosition.setXY(e.getX(), e.getY());
		});
		getNode().setOnMouseReleased(e -> {
			if (isOutBound) {
				this.setX(startPosition.getX());
				this.setY(startPosition.getY());
			}
			whenReleased(e);
		});
		if (this.cursor != Cursor.DEFAULT) {
			getNode().setOnMouseMoved(e -> {
				getNode().setCursor(this.cursor);
			});
		}
	}

	protected abstract void whenClicked(MouseEvent mouse);

	protected abstract void whenReleased(MouseEvent mouse);

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

	public PointEntity getMouse() {
		return mousePosition;
	}

	public PointEntity getLastMouse() {
		return lastPosition;
	}
}
