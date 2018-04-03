package view.shape;

import view.inter.Changable;
import view.inter.Drawable;

public abstract class ShapeItem implements Drawable, Changable, Cloneable {
	@Override
	public ShapeItem clone() {
		try {
			return (ShapeItem) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
