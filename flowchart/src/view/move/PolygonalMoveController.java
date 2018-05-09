package view.move;

import java.util.LinkedList;

import entities.DrawableState;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import ui.DrawPane;
import utility.Util;
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
	private int id;

	private MoveController[] connections;
	private int index;

	public PolygonalMoveController(DrawPane parent, PolygonalArrowShape polygonalArrowShape, Cursor cursor, int id) {
		this.parent = parent;
		this.cursor = cursor;
		this.id = id;
		this.linkedList = new LinkedList<Node>();
		this.polygonalArrowShape = polygonalArrowShape;
		this.connections = new MoveController[3];
		this.index = 1;
		this.isSelected = false;
		this.startdDraggablePoint = new DraggablePoint(polygonalArrowShape.getStartPoint()) {
			@Override
			public void update(PointEntity pointEntity) {
				polygonalArrowShape.setStartPoint(pointEntity);
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
			}

			@Override
			public void released(PointEntity pointEntity) {
				ConnectionController.getInstance().separate(connections[0],PolygonalMoveController.this);
				removeConnection(null);
				polygonalArrowShape.setStartPoint(ConnectionController.getInstance().connnect(PolygonalMoveController.this, pointEntity));
				index = 1;
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				index = 0;
			}
		};
		this.centerDraggablePoint = new DraggablePoint(polygonalArrowShape.getCenterPoint()) {
			@Override
			public void update(PointEntity pointEntity) {
				if (polygonalArrowShape.getInit()) {
					polygonalArrowShape.setCenterPoint(new PointEntity(pointEntity.getX(),polygonalArrowShape.getCenterPoint().getY()));
				}else {
					polygonalArrowShape.setCenterPoint(new PointEntity(polygonalArrowShape.getCenterPoint().getX(),pointEntity.getY()));
				}
			}
			@Override
			public void released(PointEntity pointEntity) {

			}

			@Override
			public void pressed(PointEntity pointEntity) {

			}

		};
		this.endDraggablePoint = new DraggablePoint(polygonalArrowShape.getEndPoint()) {
			@Override
			public void update(PointEntity pointEntity) {
				polygonalArrowShape.setEndPoint(pointEntity);
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
			}

			@Override
			public void released(PointEntity pointEntity) {
				ConnectionController.getInstance().separate(connections[2],PolygonalMoveController.this);
				removeConnection(null);
				polygonalArrowShape.setEndPoint(ConnectionController.getInstance().connnect(PolygonalMoveController.this, pointEntity));
				index = 1;
			}
			@Override
			public void pressed(PointEntity pointEntity) {
				index = 2;
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
		return polygonalArrowShape.getRectangle();
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
		if(connections[1]==null)
			return null;
		return Util.getList(polygonalArrowShape.getCenterPoint());
	}

	@Override
	public void addConnection(MoveController moveController) {
		connections[index] = moveController;
	}

	@Override
	public void removeConnection(MoveController moveController) {
		connections[index] = null;
	}

}
