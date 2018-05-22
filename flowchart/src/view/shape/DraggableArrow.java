package view.shape;

import entities.PointEntity;
import view.move.ArrowMovePoint;

public interface DraggableArrow {

	public PointEntity getStartPoint();
	public void setStartPoint(PointEntity pointEntity);
	public PointEntity getEndPoint();
	public void setEndPoint(PointEntity pointEntity);
	public ArrowMovePoint getStartMovePoint();
	public ArrowMovePoint getEndMovePoint();

}
