package view.shape;

import entities.PointEntity;

public interface DraggableArrow {

	public PointEntity getStartPoint();
	public void setStartPoint(PointEntity pointEntity);
	public PointEntity getEndPoint();
	public void setEndPoint(PointEntity pointEntity);

}
