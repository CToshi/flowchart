package view.shape;

import java.util.LinkedList;

import entities.PointEntity;
import entities.RectangleEntity;
import factory.RectangleShapeFactory;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import utility.Util;

public class RectangleShape extends ShapeItem {
	private Rectangle self;

	public RectangleShape(double x, double y, double width, double height, Paint color) {
		self = new Rectangle(x, y, width, height);
		self.setFill(color);
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

	protected Rectangle getSelf() {
		return self;
	}

	@Override
	public RectangleShape clone() {
		RectangleShape res = RectangleShapeFactory.create(self.getX(), self.getY(), false);
		return res;
	}
}
