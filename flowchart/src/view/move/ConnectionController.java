package view.move;

import java.util.LinkedList;

import entities.PointEntity;
import javafx.concurrent.Worker;

public class ConnectionController {

	private double minDistance = 50;
	private LinkedList<MoveController> moveControllers;
	private static ConnectionController connectionController = new ConnectionController();

	public static ConnectionController getInstance(){
		return connectionController;
	}

	private ConnectionController(){

	}

	public void setMoveControllers(LinkedList<MoveController> moveControllers) {
		this.moveControllers = moveControllers;
	}

	public PointEntity connnect(MoveController controller,PointEntity pointEntity){
		PointEntity nearPoint = null;
		MoveController nearController = null;
		double mindistance = Double.MAX_VALUE;
		for (MoveController moveController : moveControllers) {
			if(moveController.getConnectionPoints()==null)
				continue;
			for (PointEntity point: moveController.getConnectionPoints()) {
				if(mindistance>pointEntity.getDistanceFrom(point)){
					mindistance = pointEntity.getDistanceFrom(point);
					nearPoint = point;
					nearController = moveController;
				}
			}
		}
		nearController.addConnection(controller);
		controller.addConnection(nearController);
		return nearPoint;
	}

	public void separate(MoveController source,MoveController isRemoved){
		source.removeConnection(isRemoved);
	}

}
