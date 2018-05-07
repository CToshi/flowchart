package entities;

public class ShapeState {
	private RectangleEntity rectangle;
	private String text;
	private Type type;

	public static enum Type {
		RECTANGLE, ROUNDED_RECTANGLE, DIAMOND, PARALLELOGRAM, ARROW, POLYGONALARROW
	}

	public ShapeState(RectangleEntity rectangle, String text, Type type) {
		this.rectangle = rectangle;
		this.text = text;
		this.type = type;
	}

	public RectangleEntity getRectangle() {
		return rectangle;
	}

	public void setRectangle(RectangleEntity rectangle) {
		this.rectangle = rectangle;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Type getType() {
		return type;
	}

}
