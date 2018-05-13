package entities;

public abstract class DrawableState {
	private boolean isSelected;

	public static enum Type {
		RECTANGLE, ROUNDED_RECTANGLE, DIAMOND, PARALLELOGRAM, ARROW, ARROW_HORIZONTAL, ARROW_ERECT
	}

	private int ID;

	public DrawableState(boolean isSelected, int ID) {
		this.ID = ID;
		this.isSelected = isSelected;
	}

	public abstract Type getType();

	public final int getID() {
		return ID;
	}

	public boolean isSelected() {
		return isSelected;
	}

}
