package view.move;

import java.util.LinkedList;

import entities.DrawableState;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import ui.DrawPane;
import view.shape.ArrowShape;

public class ArrowMoveController implements Cloneable, MoveController {

	private ArrowShape arrowShape;
	private LinkedList<Node> linkedList;
	private DraggablePoint startDraggablePoint;
	private DraggablePoint endDraggablePoint;
//	private DrawPane parent;
	private boolean isSelected;
	private int id;

	public ArrowMoveController(DrawPane parent, ArrowShape arrowShape, int id) {
		this.id = id;
//		this.parent = parent;
		this.linkedList = new LinkedList<Node>();
		this.arrowShape = arrowShape;
		this.isSelected = false;
		PointEntity startPoint = arrowShape.getStartPoint();
		PointEntity endPoint = arrowShape.getEndPoint();
		this.startDraggablePoint = new DraggablePoint(startPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				startPoint.setX(pointEntity.getX());
				startPoint.setY(pointEntity.getY());
			}
		};
		this.endDraggablePoint = new DraggablePoint(endPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				endPoint.setX(pointEntity.getX());
				endPoint.setY(pointEntity.getY());
			}
		};
		this.linkedList.addAll(arrowShape.getNodes());
		this.linkedList.addAll(this.startDraggablePoint.getNodes());
		this.linkedList.addAll(this.endDraggablePoint.getNodes());
	}

	public void hide() {

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

	@Override
	public DrawableState getState() {
		return null;
	}

	@Override
	public void setState(DrawableState state) {
	}

	@Override
	public void setChange(MoveMsg changeMsg) {
	}
}