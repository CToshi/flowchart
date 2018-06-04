package view.shape;

import entities.DrawableState.Type;
import entities.RectangleEntity;

public class Parallelogram extends Polygonal {

	private static final double TILT_RATE = 0.2;
	private static final double OFFSET[][] = { {  0.5 + TILT_RATE / 2f,0 },
	{ 1 - TILT_RATE / 2f, 0.5 }, {  TILT_RATE / 2f,0.5 }, { 0.5 - TILT_RATE / 2f, 1 }, { 0.5, 0 }, { 0.5, 1 },};
	public Parallelogram(RectangleEntity rectangle) {
		this(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
	}

	public Parallelogram(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	protected Double[] getPoints() {
		return new Double[] { getX() + getWidth() * TILT_RATE, getY(), getX() + getWidth(), getY(),
				getX() + getWidth() * (1 - TILT_RATE), getY() + getHeight(), getX(), getY() + getHeight(), };
	}

	@Override
	public RectangleEntity getTextRectangle() {
		double len = getWidth() * TILT_RATE;
		return new RectangleEntity(getX() + len, getY(), getWidth() - 2 * len, getHeight());
	}

	@Override
	public Type getType() {
		return Type.PARALLELOGRAM;
	}

	@Override
	protected double[][] getLinkedPointsOffset() {
		return OFFSET;
	}
}
