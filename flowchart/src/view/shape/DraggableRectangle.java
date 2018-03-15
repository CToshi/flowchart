package view.shape;

import application.Main;
import entities.PointEntity;
import javafx.scene.shape.Rectangle;
import view.Changable;
import view.Draggable;

public class DraggableRectangle extends Rectangle implements Draggable, Changable {
	private PointEntity lastPosition;
	private boolean isDragged;
	private double width;
	private double height;

	public DraggableRectangle() {
		this(100, 100, 100, 100);
		initListener();
	}

	public DraggableRectangle(double x, double y, double width, double height) {
		super(x, y, width, height);
		this.width = width;
		this.height = height;
	}

	private void initListener() {
		this.setOnMousePressed(e -> {
			lastPosition = new PointEntity(e.getX(), e.getY());
			isDragged = true;
		});
		this.setOnMouseDragged(e -> {
			if (!isDragged) {
				return;
			}
			double xDelta = e.getX() - lastPosition.getX();
			double yDelta = e.getY() - lastPosition.getY();
			this.xAdd(xDelta);
			this.yAdd(yDelta);
			lastPosition.setXY(e.getX(), e.getY());
		});
	}

	@Override
	public void xAdd(double delta) {
		this.setX(this.getX() + delta);
	}

	@Override
	public void yAdd(double delta) {
		this.setY(this.getY() + delta);
	}

	@Override
	public void setStopDragged() {
		isDragged = false;
	}

	@Override
	public void widthAdd(double delta) {
		this.width += delta;
		this.setWidth(Math.abs(this.width));
	}

	@Override
	public void heightAdd(double delta) {
		this.height += delta;
		this.setHeight(Math.abs(this.height));
	}
}
