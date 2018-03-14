package view.shape;

import entities.PointEntity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import view.DrawElement;

public class DrawRectangle extends DrawElement{
	private PointEntity lastPosition;
	private Rectangle rectangle;
	private boolean isDragged;

	public DrawRectangle() {
		rectangle = new Rectangle(100, 100, 100, 100);
		rectangle.setFill(Color.GREEN);
		rectangle.setOnMousePressed(e -> {
			lastPosition = new PointEntity(e.getX(), e.getY());
			isDragged = true;
		});
		rectangle.setOnMouseDragged(e -> {
			if (!isDragged){
				return;
			}
			double xDelta = e.getX() - lastPosition.getX();
			double yDelta = e.getY() - lastPosition.getY();
			rectangle.setX(rectangle.getX() + xDelta);
			rectangle.setY(rectangle.getY() + yDelta);
			lastPosition.setXY(e.getX(), e.getY());
		});
	}

	@Override
	public Shape[] getShapes() {
		Shape[] shapes = { rectangle };
		return shapes;
	}

	@Override
	public void setStopDragged() {
		isDragged = false;
	}


}
