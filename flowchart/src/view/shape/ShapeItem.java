package view.shape;

import entities.RectangleEntity;
import entities.DrawableState.Type;
import view.inter.Changable;
import view.inter.Drawable;

public abstract class ShapeItem implements Changable, Cloneable, Drawable{
	@Override
	public ShapeItem clone() {
		try {
			return (ShapeItem) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
	public RectangleEntity getTextRectangle(){
		return this.getRectangle();
	}
	public abstract Type getType();
}
