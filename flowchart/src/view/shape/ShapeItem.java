package view.shape;

import java.util.LinkedList;

import entities.DrawableState.Type;
import entities.RectangleEntity;
import view.inter.Changable;
import view.inter.Drawable;
import view.move.LinkedPoint;

public abstract class ShapeItem implements Changable, Cloneable, Drawable {
	private static final double DEFAULT_OFFSET[][] = { { 0, 0.5 }, { 1, 0.5 }, { 0.5, 0 }, { 0.5, 1 }, };
	private LinkedList<LinkedPoint> linkedPonts;

	public ShapeItem() {
		this.linkedPonts = new LinkedList<LinkedPoint>();
		for (int i = 0; i < getLinkedPointsOffset().length; i++) {
			linkedPonts.add(new LinkedPoint(0, 0, getMaxIn(), getMaxOut()));
		}
	}

	protected int getMaxIn() {
		return Integer.MAX_VALUE;
	}

	protected int getMaxOut() {
		return 1;
	}

	@Override
	public ShapeItem clone() {
		try {
			return (ShapeItem) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	public RectangleEntity getTextRectangle() {
		return this.getRectangle();
	}

	public abstract Type getType();

	public LinkedList<LinkedPoint> getLinkedPoints() {
		return linkedPonts;
	}

	public void updateLinkedPoint() {
		RectangleEntity rectangle = getRectangle();
		for (int i = 0; i < getLinkedPointsOffset().length; i++) {
			linkedPonts.get(i).setX(rectangle.getX() + rectangle.getWidth() * getLinkedPointsOffset()[i][0]);
			linkedPonts.get(i).setY(rectangle.getY() + rectangle.getHeight() * getLinkedPointsOffset()[i][1]);
		}
	}

	public void setLinkedPointsHidden(boolean isHidden) {
		for (LinkedPoint linkedPoint : linkedPonts) {
			linkedPoint.setHidden(isHidden);
		}
	}

	protected double[][] getLinkedPointsOffset() {
		return DEFAULT_OFFSET;
	}

}
