package view.move;

import java.util.LinkedList;

import entities.DrawableState;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import ui.DrawPane;
import view.shape.PolygonalArrowShape;

public class PolygonalMoveController implements Cloneable,MoveController{

	private PolygonalArrowShape polygonalArrowShape;
	private LinkedList<Node> linkedList;
	private DraggablePoint startdDraggablePoint;
	private DraggablePoint endDraggablePoint;
	private DrawPane parent;
	private boolean isSelected;
	private int id;


	public PolygonalMoveController(DrawPane parent,PointEntity startPoint,PointEntity endPoint, int id){
		this.id = id;
		this.parent = parent;
		this.linkedList = new LinkedList<Node>();
		this.polygonalArrowShape = new PolygonalArrowShape(startPoint, endPoint);
		this.isSelected = false;
		this.startdDraggablePoint = new DraggablePoint(startPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				startPoint.setX(pointEntity.getX());
				startPoint.setY(pointEntity.getY());
			}};
		this.endDraggablePoint = new DraggablePoint(endPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				endPoint.setX(pointEntity.getX());
				endPoint.setY(pointEntity.getY());
			}};
		this.linkedList.addAll(polygonalArrowShape.getNodes());
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
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public MoveController clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RectangleEntity getRectangle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DrawableState getState() {
		return null;
	}

	@Override
	public void setState(DrawableState state) {
	}

}
