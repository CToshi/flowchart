package entities;

import application.Main;

public class PointEntity {
	private double x;
	private double y;

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

	// public PointEntity subtract(PointEntity other) {
	// return new PointEntity(x - other.getX(), y - other.getY());
	// }

	/**
	 *
	 * @param other
	 *            原点
	 * @return 以origin为原点的极坐标下这个点的角度
	 */
	public double getAngleFrom(PointEntity origin) {
//		Main.test(x, y, origin.getX(), origin.getY());
		double x = this.x - origin.getX();
		// double y = this.y - origin.getY();
		// double p = Math.sqrt(x * x + y * y);
		double p = this.getDistanceFrom(origin);
		return Math.acos(x / p) * 180 / Math.PI;
	}

//	public PointEntity rotate(double angle, PointEntity origin) {
//		double p = this.getDistanceFrom(origin);
//		angle += this.getAngleFrom(origin);
//		return new PointEntity(p * Math.cos(angle), p * Math.sin(angle));
//	}
	public PointEntity rotate(double angle, PointEntity origin) {
		double p = this.getDistanceFrom(origin);
		angle += this.getAngleFrom(origin);
		double radian = angle * Math.PI / 180;
		return new PointEntity(p * Math.cos(radian) + origin.getX(), origin.getY() - p * Math.sin(radian));
	}
}
