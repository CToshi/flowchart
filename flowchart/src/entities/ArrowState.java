package entities;

import view.move.ArrowMovePoint;

public class ArrowState extends DrawableState {

	private PointEntity startPoint;
	private PointEntity centerPoint;
	private PointEntity endPoint;

	public ArrowState(PointEntity startPoint, PointEntity centerPoint, PointEntity endPoint, int ID, Type type,
			boolean isSelected) {
		super(type, isSelected, ID);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.centerPoint = centerPoint;
	}

	public PointEntity getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(PointEntity startPoint) {
		this.startPoint = startPoint;
	}

	public PointEntity getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(PointEntity endPoint) {
		this.endPoint = endPoint;
	}

	public PointEntity getCenterPoint() {
		return centerPoint;
	}

	public void setCenterPoint(PointEntity centerPoint) {
		this.centerPoint = centerPoint;
	}

}
