package entities;

public abstract class DrawableState implements java.io.Serializable{
	public static enum Type {
		RECTANGLE, ROUNDED_RECTANGLE, DIAMOND, PARALLELOGRAM, ARROW, ARROW_HORIZONTAL, ARROW_ERECT,CurveRectangle
	}

	private int ID;
	private Type type;

	public DrawableState(Type type, int ID) {
		this.ID = ID;
		this.type = type;
	}

	public final Type getType(){
		return type;
	}

	public final int getID() {
		return ID;
	}
}
