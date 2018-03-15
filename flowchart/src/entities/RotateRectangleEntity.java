package entities;

public class RotateRectangleEntity extends RectangleEntity {

	private PointEntity centerPoint;
	private double angle;

	public RotateRectangleEntity(double width, double height, PointEntity centerPoint, double angle) {
		super(width, height);
		this.centerPoint = centerPoint;
		this.angle = angle;
	}

	public PointEntity getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(PointEntity centerPoint) {
		this.centerPoint = centerPoint;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	public boolean contains(PointEntity point){//´ýÍê³É
		return false;
	}

}
