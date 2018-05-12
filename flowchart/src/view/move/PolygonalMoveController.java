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
	private DraggablePoint startDraggablePoint;
	private DraggablePoint centerDraggablePoint;
	private DraggablePoint endDraggablePoint;
	private DrawPane parent;
	private Cursor cursor;
	private boolean isSelected;
	private PointEntity hiddenPoint;
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
		this.hiddenPoint = new PointEntity(-100,-100);
		this.startDraggablePoint = new DraggablePoint(hiddenPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				updateCircle(pointEntity);
				polygonalArrowShape.setStartPoint(pointEntity);
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
			}

			@Override
			public void released(PointEntity pointEntity) {
				ConnectionController.getInstance().separate(connections[0],PolygonalMoveController.this);
				removeConnection(null);
				PointEntity point = ConnectionController.getInstance().connnect(PolygonalMoveController.this, pointEntity);
				polygonalArrowShape.setStartPoint(point);
				index = 1;
				updateCircle(point);
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				index = 0;
			}
		};
		this.centerDraggablePoint = new DraggablePoint(hiddenPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				if(polygonalArrowShape.getIsHorizontal()){
					updateCircle(new PointEntity(polygonalArrowShape.getCenterPoint().getX(),pointEntity.getY()));
				}else {
					updateCircle(new PointEntity(pointEntity.getX(),polygonalArrowShape.getCenterPoint().getY()));
				}
				polygonalArrowShape.setCenterPoint(pointEntity);
			}
			@Override
			public void released(PointEntity pointEntity) {

			}

			@Override
			public void pressed(PointEntity pointEntity) {

			}

		};
		this.endDraggablePoint = new DraggablePoint(hiddenPoint) {
			@Override
			public void update(PointEntity pointEntity) {
				updateCircle(pointEntity);
				polygonalArrowShape.setEndPoint(pointEntity);
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
			}

			@Override
			public void released(PointEntity pointEntity) {
				ConnectionController.getInstance().separate(connections[2],PolygonalMoveController.this);
				removeConnection(null);
				PointEntity point = ConnectionController.getInstance().connnect(PolygonalMoveController.this, pointEntity);
				polygonalArrowShape.setEndPoint(point);
				index = 1;
				updateCircle(point);
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
				setSelected(true);
			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				polygonalArrowShape.move(xDelta,yDelta);
				startDraggablePoint.updateCircle(polygonalArrowShape.getStartPoint());
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
		this.linkedList.addAll(startDraggablePoint.getNodes());
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
		if(isSelected){
			setHidden(false);
		}else {
			setHidden(true);
		}
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public MoveController clone() {
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
		if(connections[1]!=null)
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

	public void setHidden(boolean isHidden){
		if(isHidden){
			startDraggablePoint.updateCircle(hiddenPoint);
			centerDraggablePoint.updateCircle(hiddenPoint);
			endDraggablePoint.updateCircle(hiddenPoint);
		}else{
			startDraggablePoint.updateCircle(polygonalArrowShape.getStartPoint());
			centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
			endDraggablePoint.updateCircle(polygonalArrowShape.getEndPoint());
		}
	}

	@Override
	public void setChange(MoveMsg changeMsg) {
		polygonalArrowShape.move(changeMsg.getDeltaX(),changeMsg.getDeltaY());
		startDraggablePoint.updateCircle(polygonalArrowShape.getStartPoint());
		centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
		endDraggablePoint.updateCircle(polygonalArrowShape.getEndPoint());
	}

}
