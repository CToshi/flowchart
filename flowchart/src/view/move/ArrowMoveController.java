package view.move;

import java.util.LinkedList;

import entities.DrawableState;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import ui.DrawPane;
import utility.Util;
import view.shape.ArrowShape;

public class ArrowMoveController implements Cloneable, MoveController {

	private ArrowShape arrowShape;
	private DraggableLine draggableLine;
	private LinkedList<Node> linkedList;
	private DraggablePoint startDraggablePoint;
	private DraggablePoint endDraggablePoint;
	private PointEntity hiddenPoint;
	private DrawPane parent;
	private boolean isSelected;
	private int id;

	private MoveController[]  connections;
	private int index;

	public ArrowMoveController(DrawPane parent,ArrowShape arrowShape,int id) {
		this.parent = parent;
		this.id = id;
		this.linkedList = new LinkedList<Node>();
		this.connections = new MoveController[3];
		this.index = 1;
		this.arrowShape = arrowShape;
		this.isSelected = false;
		this.draggableLine = new DraggableLine(arrowShape.getLine(),this);
		this.hiddenPoint = new PointEntity(-100,-100);
		this.startDraggablePoint = new DraggablePoint(hiddenPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				updateCircle(pointEntity);
				arrowShape.setStartPoint(pointEntity);
			}
			@Override
			public void released(PointEntity pointEntity) {
				ConnectionController.getInstance().separate(connections[0],ArrowMoveController.this);
				removeConnection(null);
				PointEntity point = ConnectionController.getInstance().connnect(ArrowMoveController.this, pointEntity);
				arrowShape.setStartPoint(point);
				updateCircle(point);
				index = 1;
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				index = 0;
			}};
		this.endDraggablePoint = new DraggablePoint(hiddenPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				updateCircle(pointEntity);
				arrowShape.setEndPoint(pointEntity);
			}

			@Override
			public void released(PointEntity pointEntity) {
				ConnectionController.getInstance().separate(connections[2],ArrowMoveController.this);
				removeConnection(null);
				PointEntity point = ConnectionController.getInstance().connnect(ArrowMoveController.this, pointEntity);
				arrowShape.setEndPoint(point);
				updateCircle(point);
				index = 1;
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				index = 2;
			}};
		this.linkedList.addAll(arrowShape.getNodes());
		this.linkedList.addAll(this.startDraggablePoint.getNodes());
		this.linkedList.addAll(this.endDraggablePoint.getNodes());
	}

	public void hide(){

	}

	public ArrowShape getArrowShape() {
		return arrowShape;
	}

	@Override
	public LinkedList<Node> getNodes() {
		return linkedList;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if(isSelected){
			setHidden(false);
		}else{
			setHidden(true);
		}
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return isSelected;
	}

	@Override
	public MoveController clone() {
		return null;
	}

	@Override
	public RectangleEntity getRectangle() {
		return arrowShape.getRectangle();
	}

	public void update(){
		startDraggablePoint.updateCircle(draggableLine.getStartPoint());
		endDraggablePoint.updateCircle(draggableLine.getEndPoint());
		arrowShape.update();
	}
	@Override
	public DrawableState getState() {
		return null;
	}

	@Override
	public void setState(DrawableState state) {

	}

	@Override
	public LinkedList<PointEntity> getConnectionPoints() {
		if(connections[1] != null)
			return null;
		return Util.getList(new PointEntity((arrowShape.getLine().getStartX()+arrowShape.getLine().getEndX())/2,
				(arrowShape.getLine().getStartY()+arrowShape.getLine().getEndY())/2));
	}

	@Override
	public void addConnection(MoveController moveController) {
		connections[index] = moveController;
	}

	@Override
	public void removeConnection(MoveController moveController) {
		connections[index] = null;
	}

	public void setHidden(boolean isHidden){
		if(isHidden){
			startDraggablePoint.updateCircle(hiddenPoint);
			endDraggablePoint.updateCircle(hiddenPoint);
		}else{
			startDraggablePoint.updateCircle(arrowShape.getStartPoint());
			endDraggablePoint.updateCircle(arrowShape.getEndPoint());
		}
	}

}