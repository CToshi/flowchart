package view.move;

import java.util.LinkedList;

import entities.PointEntity;
import javafx.concurrent.Worker;

public class ConnectionController {

	private double minDistance = 10;
	private LinkedList<MoveController> moveControllers;
	private static ConnectionController connectionController = new ConnectionController();

	public static ConnectionController getInstance(){
		return connectionController;
	}

	private ConnectionController(){
		moveControllers = new LinkedList<MoveController>();
	}

	public void setMoveControllers(LinkedList<MoveController> moveControllers) {
		this.moveControllers = moveControllers;
	}

	public PointEntity connnect(MoveController controller,PointEntity pointEntity){
		PointEntity nearPoint = pointEntity;
		MoveController nearController = null;
		double minDistance = this.minDistance;
		for (MoveController moveController : moveControllers) {
			if(moveController.getConnectionPoints()==null||moveController==controller)
				continue;
			for (PointEntity point: moveController.getConnectionPoints()) {
				if(minDistance>pointEntity.getDistanceFrom(point)){
					minDistance = pointEntity.getDistanceFrom(point);
					nearPoint = point;
					nearController = moveController;
				}
			}
		}
		if(nearController != null){
			nearController.addConnection(controller);
			controller.addConnection(nearController);
		}
		return nearPoint;
	}

	public void addController(MoveController moveController){
		moveControllers.add(moveController);
	}

	public void removeController(MoveController moveController){
		moveControllers.remove(moveController);
	}

	public void separate(MoveController source,MoveController isRemoved){
		if(source != null){
			source.removeConnection(isRemoved);
		}
	}

}
