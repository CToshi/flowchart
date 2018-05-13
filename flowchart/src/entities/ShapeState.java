package entities;

public class ShapeState extends DrawableState{
	private RectangleEntity rectangle;
	private String text;

	public ShapeState(RectangleEntity rectangle, String text, Type type, int ID) {
		super(type,ID);
		this.rectangle = rectangle;
		this.text = text;
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


}
