package view.shape;

import entities.PointEntity;
import javafx.scene.shape.Rectangle;
import view.Draggable;

public class DraggableRectangle extends Rectangle implements Draggable {
	private PointEntity lastPosition;
	private PointEntity startPosition;
	private boolean isOutBound;

	public DraggableRectangle() {
		this(100, 100, 100, 100);
		initListener();
	}

	public DraggableRectangle(double x, double y, double width, double height) {
		super(x, y, width, height);
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
			this.deal(xDelta, yDelta);
			lastPosition.setXY(e.getX(), e.getY());
		});
		this.setOnMouseReleased(e -> {
			if (isOutBound) {
				this.setX(startPosition.getX());
				this.setY(startPosition.getY());
			}
			whenReleased();
		});
	}

	protected void whenClicked() {

	}

	protected void whenReleased() {

	}

	@Override
	public void setOutBound(boolean isOutBound) {
		this.isOutBound = isOutBound;
	}

	protected void deal(double xDelta, double yDelta) {
		this.setX(this.getX() + xDelta);
		this.setY(this.getY() + yDelta);
	}

}
