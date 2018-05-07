package view.move;

import java.util.LinkedList;

import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import ui.DrawPane;
import view.inter.Draggable;
import view.shape.PolygonalArrowShape;

public class PolygonalMoveController implements Cloneable, MoveController {

	private Draggable draggable;
	private PolygonalArrowShape polygonalArrowShape;
	private LinkedList<Node> linkedList;
	private DraggablePoint startdDraggablePoint;
	private DraggablePoint centerDraggablePoint;
	private DraggablePoint endDraggablePoint;
	private DrawPane parent;
	private Cursor cursor;
	private boolean isSelected;
	private int ID;

	public PolygonalMoveController(DrawPane parent, PolygonalArrowShape polygonalArrowShape) {
		this(parent, polygonalArrowShape, Cursor.MOVE);
	}

	private PolygonalMoveController(DrawPane parent, PolygonalArrowShape polygonalArrowShape, Cursor cursor) {
		this.parent = parent;
		this.cursor = cursor;
		this.linkedList = new LinkedList<Node>();
		this.polygonalArrowShape = polygonalArrowShape;
		this.isSelected = false;
		this.startdDraggablePoint = new DraggablePoint(polygonalArrowShape.getStartPoint()) {
			@Override
			public void update(PointEntity pointEntity) {
				polygonalArrowShape.setStartPoint(pointEntity);
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
			}
		};
		this.centerDraggablePoint = new DraggablePoint(polygonalArrowShape.getCenterPoint()) {
			@Override
			public void update(PointEntity pointEntity) {
				polygonalArrowShape.setCenterPoint(pointEntity);
			}
		};
		this.endDraggablePoint = new DraggablePoint(polygonalArrowShape.getEndPoint()) {
			@Override
			public void update(PointEntity pointEntity) {
				polygonalArrowShape.setEndPoint(pointEntity);
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
			}
		};
		this.draggable = new Draggable() {

			@Override
			protected void whenReleased(MouseEvent mouse) {

			}

			@Override
			protected void whenPressed(MouseEvent mouse) {

			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				polygonalArrowShape.move(xDelta,yDelta);
				startdDraggablePoint.updateCircle(polygonalArrowShape.getStartPoint());
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
				endDraggablePoint.updateCircle(polygonalArrowShape.getEndPoint());
			}

			@Override
			protected void whenMoved(MouseEvent mouse) {
				getNode().setCursor(cursor);
			}

			@Override
			protected Node getNode() {
				return polygonalArrowShape.getPolyline();
			}

		};
		this.linkedList.addAll(polygonalArrowShape.getNodes());
		this.linkedList.addAll(startdDraggablePoint.getNodes());
		this.linkedList.addAll(centerDraggablePoint.getNodes());
		this.linkedList.addAll(endDraggablePoint.getNodes());
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
		return isSelected;
	}

	@Override
	public MoveController clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RectangleEntity getRectangle() {
		return polygonalArrowShape.getRectangle();
	}

}
