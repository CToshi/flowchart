package view.shape;

import java.util.LinkedList;

import entities.DrawableState.Type;
import entities.PointEntity;
import entities.RectangleEntity;
import factory.ShapeFactory;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import utility.Util;

public class RectangleShape extends ShapeItem {
	private Rectangle self;

	public RectangleShape(double x, double y, double width, double height) {
		this(x, y, width, height, Color.WHITE);
	}
	public RectangleShape(double x, double y, double width, double height, Paint color) {
		super();
		self = new Rectangle(x, y, width, height);
		self.setFill(color);
		self.setStroke(Color.BLACK);
	}

	@Override
	public LinkedList<Node> getNodes() {
		return Util.getList(self);
	}

	@Override
	public RectangleEntity getRectangle() {
		return new RectangleEntity(new PointEntity(self.getX(), self.getY()), self.getWidth(), self.getHeight());
	}

	@Override
	public void setX(double value) {
		self.setX(value);
	}

	@Override
	public void setY(double value) {
		self.setY(value);
	}

	@Override
	public void setWidth(double value) {
		self.setWidth(value);
	}

	@Override
	public void setHeight(double value) {
		self.setHeight(value);
	}

	public Rectangle getSelf() {
		return self;
	}

	public void setRectangle(RectangleEntity rectangle) {
		setX(rectangle.getX());
		setY(rectangle.getY());
		setWidth(rectangle.getWidth());
		setHeight(rectangle.getHeight());
	}

	@Override
	public RectangleShape clone() {
		RectangleShape res = (RectangleShape) ShapeFactory.create(self.getX(), self.getY(), false, Type.RECTANGLE);
		return res;
	}
	@Override
	public Type getType() {
		return Type.RECTANGLE;
	}
}
