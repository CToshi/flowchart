package view.shape;

import java.util.LinkedList;

import entities.DrawableState.Type;
import entities.RectangleEntity;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utility.Util;

public class CircleShape extends ShapeItem {

	private Circle circle;

	public CircleShape(double centerX, double centerY, double radius) {
		circle = new Circle(centerX, centerY, radius);
		circle.setFill(Color.TRANSPARENT);
		circle.setStroke(Color.BLACK);
	}

	@Override
	public RectangleEntity getRectangle() {
		return new RectangleEntity(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(),
				circle.getRadius()*2, circle.getRadius()*2);
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		circle.setCenterX(rectangle.getX() + rectangle.getWidth() / 2f);
		circle.setCenterY(rectangle.getY() + rectangle.getHeight() / 2f);
		circle.setRadius(Math.min(rectangle.getWidth(), rectangle.getHeight())/2f);
	}

	@Override
	public LinkedList<Node> getNodes() {
		return Util.getList(circle);
	}

	@Override
	public Type getType() {
		return Type.CIRCLE;
	}
	
	@Override
	public RectangleEntity getTextRectangle() {
		RectangleEntity rectangle = getRectangle();
		double radius = rectangle.getWidth()/2f;
		rectangle.setX(rectangle.getX() + radius * (1 - 1/Math.sqrt(2)));
		rectangle.setY(rectangle.getY() + radius * (1 - 1/Math.sqrt(2)));
		rectangle.setWidth(radius * Math.sqrt(2));
		rectangle.setHeight(radius* Math.sqrt(2));
		return rectangle;
	}

}
