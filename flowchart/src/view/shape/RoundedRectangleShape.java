package view.shape;

import entities.DrawableState.Type;
import factory.ShapeFactory;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class RoundedRectangleShape extends RectangleShape {
	private Rectangle self;

	public RoundedRectangleShape(RectangleShape rectangle) {
		this(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
				rectangle.getSelf().getFill());
	}

	public RoundedRectangleShape(double x, double y, double width, double height) {
		this(x, y, width, height, Color.WHITE);
	}

	public RoundedRectangleShape(double x, double y, double width, double height, Paint color) {
		super(x, y, width, height, color);
		self = this.getSelf();
		setWidth(self.getWidth());
		setHeight(self.getHeight());
	}

	@Override
	public void setWidth(double value) {
		super.setWidth(value);
		setArc();
	}

	@Override
	public void setHeight(double value) {
		super.setHeight(value);
		setArc();
	}

	private void setArc() {
		self.setArcWidth(Math.min(self.getWidth(), self.getHeight()) * 0.5);
		self.setArcHeight(Math.min(self.getWidth(), self.getHeight()) * 0.5);
	}

	@Override
	public RoundedRectangleShape clone() {
		RoundedRectangleShape res = (RoundedRectangleShape) ShapeFactory.create(self.getX(), self.getY(), false,
				Type.ROUNDED_RECTANGLE);
		return res;
	}
	@Override
	public Type getType() {
		return Type.ROUNDED_RECTANGLE;
	}
}
