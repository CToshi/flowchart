package entities;
public abstract class DrawableState {
	public static enum Type {
		RECTANGLE, ROUNDED_RECTANGLE, DIAMOND, PARALLELOGRAM, ARROW,
	}
	public abstract Type getType();
}
