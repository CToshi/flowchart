package entities;

public class RectangleEntity {
	private double width;
	private double height;
	private PointEntity leftTop;

	public RectangleEntity(double x, double y, double width, double height) {
		this(new PointEntity(x, y), width, height);
	}

	public RectangleEntity(PointEntity leftTop, double width, double height) {
		this.leftTop = leftTop;
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public PointEntity getLeftTop() {
		return leftTop;
	}

	public void setLeftTop(PointEntity leftTop) {
		this.leftTop = leftTop;
	}

	public void setX(double value) {
		leftTop.setX(value);
	}

	public void setY(double value) {
		leftTop.setY(value);
	}

	public double getX() {
		return leftTop.getX();
	}

	public double getY() {
		return leftTop.getY();
	}

}
