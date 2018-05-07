package view.shape;

import entities.RectangleEntity;

public class Diamond extends Polygonal {

	public Diamond(double x, double y, double width, double height) {
		this(new RectangleEntity(x, y, width, height));
	}

	public Diamond(RectangleEntity rectangle) {
		super(rectangle);
	}

	@Override
	protected Double[] getPoints() {
		double x = getRectangle().getX();
		double y = getRectangle().getY();
		double w = getRectangle().getWidth();
		double h = getRectangle().getHeight();
		return new Double[] { x + w / 2f, y, x + w, y + h / 2f, x + w / 2f, y + h, x, y + h / 2f };
	}

	@Override
	public RectangleEntity getTextRectangle() {
		double x = getX() + getWidth() / 4f;
		double y = getY() + getHeight() / 4f;
		double w = getWidth() / 2f;
		double h = getHeight() / 2f;
		return new RectangleEntity(x, y, w, h);
	}

}
