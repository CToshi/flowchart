package view.move;

import java.util.LinkedList;

import entities.PointEntity;

public class ConnectionController {

	private static final double MIN_DISTANCE = 10;
	private static final double DISTANCE_FOR_SHOW = 30;
	private LinkedList<MoveController> moveControllers;
	private static ConnectionController connectionController = new ConnectionController();
	private MoveController showingController;

	public static ConnectionController getInstance() {
		return connectionController;
	}

	private ConnectionController() {
		moveControllers = new LinkedList<MoveController>();
	}

	public PointEntity connnect(MoveController controller, PointEntity pointEntity, ArrowMovePoint arrowMovePoint) {
		PointEntity result = pointEntity;
		LinkedPoint nearPoint = null;
		MoveController nearController = null;
		whenMovingFinish();
		showingController = null;
		double minDistance = MIN_DISTANCE;
		double distanceForShow = DISTANCE_FOR_SHOW;
		for (MoveController moveController : moveControllers) {
			if (moveController.getConnectionPoints() == null || moveController == controller)
				continue;
			for (LinkedPoint point : moveController.getConnectionPoints()) {
				if (minDistance > getDistance(point, pointEntity)) {
					minDistance = getDistance(point, pointEntity);
					nearPoint = point;
					nearController = moveController;
				}else if(distanceForShow > getDistance(point, pointEntity)){
					distanceForShow = getDistance(point, pointEntity);
					showingController = moveController;
				}
			}
		}
		if (nearController != null) {
			nearPoint.addConnectionPoint(arrowMovePoint);
			arrowMovePoint.setLinkedPoint(nearPoint);
			result.setX(nearPoint.getX());
			result.setY(nearPoint.getY());
			showingController = nearController;
		}
		if(showingController != null){
			showingController.setLinkedPointsHidden(false);
		}
		return result;
	}

	public void addController(MoveController moveController) {
		moveControllers.add(moveController);
	}

	public void clearConnections(){
		moveControllers.clear();
	}

	public void separate(LinkedPoint linkedPoint, ArrowMovePoint arrowMovePoint) {
		if (linkedPoint != null) {
			linkedPoint.removeConnectionPoint(arrowMovePoint);
			arrowMovePoint.removeConnectionPoint();
		}
	}

	public double getDistance(LinkedPoint linkedPoint, PointEntity pointEntity) {
		return Math.sqrt((linkedPoint.getX() - pointEntity.getX()) * (linkedPoint.getX() - pointEntity.getX())
				+ (linkedPoint.getY() - pointEntity.getY()) * (linkedPoint.getY() - pointEntity.getY()));
	}

	public void whenMovingFinish(){
		if(showingController != null){
			showingController.setLinkedPointsHidden(true);
			showingController = null;
		}
	}
}
