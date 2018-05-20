package entities;

import utility.Util;

public class PointEntity implements java.io.Serializable, Cloneable {
	private double x;
	private double y;

	public PointEntity() {
		this(0, 0);
	}

	public PointEntity(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setXY(double x, double y) {
		setX(x);
		setY(y);
	}

	public double getDistanceFrom(PointEntity other) {
		return Math.sqrt((x - other.getX()) * (x - other.getX()) + (y - other.getY()) * (y - other.getY()));
	}

	public boolean equals(PointEntity pointEntity) {
		return (Util.isEquals(getX(), pointEntity.getX()) && Util.isEquals(getY(), pointEntity.getY()));
	}

	@Override
	public String toString() {
		return "PointEntity [x=" + x + ", y=" + y + "]";
	}

	@Override
	public PointEntity clone() {
		try {
			return (PointEntity) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
