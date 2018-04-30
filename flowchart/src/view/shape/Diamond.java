package view.shape;

import java.util.LinkedList;

import entities.RectangleEntity;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import utility.Util;

public class Diamond extends ShapeItem {
	private Polygon polygon;
	private RectangleEntity rectangle;

	public Diamond(double x, double y, double width, double height) {
		this(new RectangleEntity(x, y, width, height));
	}

	public Diamond(RectangleEntity rectangle) {
		polygon = new Polygon();
		polygon.setFill(Color.TRANSPARENT);
		polygon.setStroke(Color.BLACK);
		this.rectangle = rectangle;
	}

	private Double[] getPoints() {
		double x = rectangle.getX();
		double y = rectangle.getY();
		double w = rectangle.getWidth();
		double h = rectangle.getHeight();
		return new Double[] { x + w / 2f, y, x + w, y + h / 2f, x + w / 2f, y + h, x, y + h / 2f };
	}

	private void update() {
		polygon.getPoints().clear();
		polygon.getPoints().addAll(getPoints());
	}

	@Override
	public LinkedList<Node> getNodes() {
		return Util.getList(polygon);
	}

	@Override
	public RectangleEntity getRectangle() {
		return rectangle;
	}

	@Override
	public void setX(double value) {
		rectangle.setX(value);
		update();
	}

	@Override
	public void setY(double value) {
		rectangle.setY(value);
		update();
	}

	@Override
	public void setWidth(double value) {
		rectangle.setWidth(value);
		update();
	}

	@Override
	public void setHeight(double value) {
		rectangle.setHeight(value);
		update();
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		this.rectangle = rectangle;
		update();
	}

}
