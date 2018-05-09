package entities;

import javafx.scene.shape.Rectangle;

public class RectangleEntity implements Cloneable{
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

	public RectangleEntity(Rectangle rect) {
		this(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double value) {
		this.width = value;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double value) {
		this.height = value;
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

	/**
	 * 同boolean contains(RectangleEntity rect)
	 *
	 * @param rect
	 * @return
	 */
	public boolean contains(Rectangle rect) {
		return contains(new RectangleEntity(rect), 0.001);
	}

	/**
	 * 默认精度0.001
	 *
	 * @param rect
	 * @return
	 */
	public boolean contains(RectangleEntity rect) {
		return contains(rect, 0.001);
	}

	/**
	 * 判断另一个矩形是否包含在该矩形内
	 *
	 * @param rect
	 *            另一个矩形
	 * @param eps
	 *            精度
	 * @return 当左上角小于等于另一个矩形的左上角且右下角大于等于另一个矩形右下角时返回true
	 */
	public boolean contains(RectangleEntity rect, double eps) {
		PointEntity rectRB = rect.getRightBottom();
		PointEntity rB = this.getRightBottom();
		return lessOrEquals(leftTop, rect.getLeftTop(), eps) && lessOrEquals(rectRB, rB, eps);
	}

	/**
	 * double 类型的小于等于比较
	 *
	 * @param a
	 * @param b
	 * @param eps
	 * @return
	 */
	private boolean lessOrEquals(double a, double b, double eps) {
		return a < b || equals(a, b, eps);
	}

	private boolean lessOrEquals(PointEntity a, PointEntity b, double eps) {
		return lessOrEquals(a.getX(), b.getX(), eps) && lessOrEquals(a.getY(), b.getY(), eps);
	}

	public PointEntity getRightBottom() {
		return new PointEntity(getX() + getWidth(), getY() + getHeight());
	}

	private boolean equals(double a, double b, double eps) {
		return Math.abs(a - b) < eps;
	}

	@Override
	public boolean equals(Object obj) {
		return equals(obj, 0.001);
	}

	public boolean equals(Object obj, double eps) {
		if (obj instanceof RectangleEntity) {
			RectangleEntity rect = (RectangleEntity) obj;
			return equals(rect.getX(), this.getX(), eps) && equals(rect.getY(), this.getY(), eps)
					&& equals(rect.width, this.width, eps) && equals(rect.height, this.height, eps);
		}
		return false;
	}
	@Override
	public String toString() {
		return String.format("[x=%.2f, y=%.2f][width=%.2f, height=%.2f]", getX(), getY(), getWidth(), getHeight());
	}
	@Override
	public RectangleEntity clone(){
		try {
			return (RectangleEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
