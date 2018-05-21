package view.move;

import entities.PointEntity;
import view.shape.DraggableArrow;

public class ArrowMovePoint {

	private double x;
	private double y;
	private boolean isStart;
	private DraggableArrow arrow;
	private LinkedPoint linkedPoint;
	private boolean isCurve;

	public ArrowMovePoint(boolean isStart, DraggableArrow arrow) {
		this.isStart = isStart;
		this.arrow = arrow;
		this.linkedPoint = null;
		this.isCurve = false;
	}

	public boolean isStart() {
		return isStart;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		update(x, getY());
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		update(getX(), y);
	}

	public LinkedPoint getLinkedPoint(){
		return linkedPoint;
	}

	public void setLinkedPoint(LinkedPoint linkedPoint) {
		this.linkedPoint = linkedPoint;
	}

	public void removeConnectionPoint(){
		this.linkedPoint = null;
	}

	public void update(double x,double y) {
		if (isStart) {
			arrow.setStartPoint(new PointEntity(x,y));
		} else {
			arrow.setEndPoint(new PointEntity(x,y));
		}
	}

	public boolean isCurve() {
		return isCurve;
	}

	public void setCurve(boolean isCurve) {
		this.isCurve = isCurve;
	}

}
