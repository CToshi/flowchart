package entities;

public abstract class DrawableState {
	public static enum Type {
		RECTANGLE, ROUNDED_RECTANGLE, DIAMOND, PARALLELOGRAM, ARROW,
	}

	private int ID;

	public DrawableState(int ID) {
		this.ID = ID;
	}

	public abstract Type getType();

	public final int getID() {
		return ID;
	}
}
