package view.move;

import java.util.LinkedList;

import entities.PointEntity;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utility.Util;
import view.inter.Draggable;
import view.inter.Drawable;

public abstract class DraggablePoint implements Drawable{

	private Circle circle;
	private static final double RADIUS = 5;

	public DraggablePoint(PointEntity centerPoint){
		this.circle = new Circle(centerPoint.getX(),centerPoint.getY(),RADIUS);
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);
		new Draggable() {
			@Override
			protected void whenReleased(MouseEvent mouse) {
				released(new PointEntity(mouse.getX(),mouse.getY()));
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				pressed(new PointEntity(mouse.getX(),mouse.getY()));
			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				update(this.getMousePosition());
			}

			@Override
			protected void whenMoved(MouseEvent mouse) {

			}

			@Override
			protected Node getNode() {
				return circle;
			}
		};
	}

	public void updateCircle(PointEntity centerPoint){
		circle.setCenterX(centerPoint.getX());
		circle.setCenterY(centerPoint.getY());
	}
	@Override
	public LinkedList<Node> getNodes() {
		return Util.getList(circle);
	}
	public abstract void update(PointEntity pointEntity);
	public abstract void released(PointEntity pointEntity);
	public abstract void pressed(PointEntity pointEntity);
}