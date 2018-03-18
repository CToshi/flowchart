package entities;

public class LineEntity {
	private PointEntity startPoint;
	private PointEntity endPoint;

	public LineEntity(PointEntity startPoint, PointEntity endPoint) {
		super();
		this.startPoint = startPoint;
		this.endPoint = endPoint;
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

}
