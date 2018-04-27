package view.move;

import entities.PointEntity;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import view.inter.Draggable;

public abstract class DraggablePoint{

	private Circle circle;
	private static final double RADIUS = 5;

	public DraggablePoint(PointEntity centerPoint){
		this.circle = new Circle(centerPoint.getX(),centerPoint.getY(),RADIUS);
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);
		new Draggable() {
			@Override
			protected void whenReleased(MouseEvent mouse) {
				
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {

			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				updateCircle(this.getMousePosition());
			}

			@Override
			protected Node getNode() {
				return circle;
			}
		};
	}

	private void updateCircle(PointEntity centerPoint){
		circle.setCenterX(centerPoint.getX());
		circle.setCenterY(centerPoint.getY());
	}
	public abstract void update(PointEntity point);
//	public abstract void whenReleased();
//	public abstract void whenPressed();
}
