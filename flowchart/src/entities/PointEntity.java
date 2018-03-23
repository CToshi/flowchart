package entities;

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

//	public double getDistanceFrom(PointEntity other) {
//		return Math.sqrt((x - other.getX()) * (x - other.getX()) + (y - other.getY()) * (y - other.getY()));
//	}

	// public PointEntity subtract(PointEntity other) {
	// return new PointEntity(x - other.getX(), y - other.getY());
	// }

	/**
	 *
	 * @param other
	 *            原点
	 * @return 以origin为原点的极坐标下这个点的角度
	 */
//	public double getAngleFrom(PointEntity origin) {
//		double x = this.x - origin.getX();
//		double p = this.getDistanceFrom(origin);
//		return Math.acos(x / p) * 180 / Math.PI;
//
//	}
//
//	public PointEntity rotate(double angle, PointEntity origin) {
//		double radian = angle * Math.PI / 180.0;
//		double radius = this.getDistanceFrom(origin);
//		if (radius < 0.001)
//			return origin;
//		PointEntity aPoint = new PointEntity(x - origin.getX(), y - origin.getY());
//		double aAngle;
//		if (Math.abs(aPoint.getX()) < 0.001)
//			aAngle = 0.5 * Math.PI;
//		else
//			aAngle = Math.acos(aPoint.getX() / radius);
//		if (aPoint.getY() < 0)
//			aAngle = 0 - aAngle;
//		double bAngle = aAngle + radian;
//		if (bAngle > Math.PI)
//			bAngle -= 2 * Math.PI;
//		if (bAngle < -Math.PI)
//			bAngle += 2 * Math.PI;
//		return new PointEntity(radius * Math.cos(bAngle) + origin.getX(), radius * Math.sin(bAngle) + origin.getY());
//	}
//
//	public double getAngleFrom(PointEntity other, PointEntity origin) {
//		double radius = this.getDistanceFrom(origin);
//		PointEntity aPoint = new PointEntity(x - origin.getX(), y - origin.getY());
//		PointEntity bPoint = new PointEntity(other.getX() - origin.getX(), other.getY() - origin.getY());
//		double aAngle;
//		if (Math.abs(aPoint.getX()) < 0.001)
//			aAngle = 0.5 * Math.PI;
//		else
//			aAngle = Math.acos(aPoint.getX() / radius);
//		if (aPoint.getY() < 0)
//			aAngle = 0 - aAngle;
//		double bAngle;
//		radius = other.getDistanceFrom(origin);
//		if (Math.abs(bPoint.getX()) < 0.001)
//			bAngle = 0.5 * Math.PI;
//		else
//			bAngle = Math.acos(bPoint.getX() / radius);
//		if (bPoint.getY() < 0)
//			bAngle = 0 - bAngle;
//		double angle = aAngle - bAngle;
//		return angle / (2 * Math.PI) * 360.0;
//	}
}
