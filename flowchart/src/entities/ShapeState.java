package entities;

public class ShapeState {
	private RectangleEntity rectangle;
	private String text;
	private TYPE type;

	public static enum TYPE {
		RECTANGLE, ROUNDED_RECTANGLE, DIAMOND, PARALLELOGRAM, ARROW,
	}

	public ShapeState(RectangleEntity rectangle, String text, TYPE type) {
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

	public TYPE getType() {
		return type;
	}

}
