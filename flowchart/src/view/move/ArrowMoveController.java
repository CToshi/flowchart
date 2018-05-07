package view.move;

import java.util.LinkedList;

import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import ui.DrawPane;
import view.shape.ArrowShape;

public class ArrowMoveController implements Cloneable,MoveController{

	private ArrowShape arrowShape;
	private DraggableLine draggableLine;
	private LinkedList<Node> linkedList;
	private DraggablePoint startDraggablePoint;
	private DraggablePoint endDraggablePoint;
	private DrawPane parent;
	private boolean isSelected;
	private int ID;

	public ArrowMoveController(DrawPane parent,ArrowShape arrowShape) {
		this(parent,arrowShape,false);
	}

	private ArrowMoveController(DrawPane parent,ArrowShape arrowShape,boolean isClone) {
		this.parent = parent;
		if(!isClone)
			this.ID = parent.getControllerID();
		this.linkedList = new LinkedList<Node>();
		this.arrowShape = arrowShape;
		this.isSelected = false;
		this.draggableLine = new DraggableLine(arrowShape.getLine(),this);
		PointEntity startPoint = arrowShape.getStartPoint();
		PointEntity endPoint = arrowShape.getEndPoint();
		this.startDraggablePoint = new DraggablePoint(startPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				arrowShape.setStartPoint(pointEntity);
			}};
		this.endDraggablePoint = new DraggablePoint(endPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				arrowShape.setEndPoint(pointEntity);
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
		return ID;
	}

	@Override
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
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
}
