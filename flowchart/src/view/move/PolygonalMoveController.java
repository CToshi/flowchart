package view.move;

import java.util.LinkedList;

import application.Main;
import entities.ArrowState;
import entities.DrawableState;
import entities.DrawableState.Type;
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

	private SyncMoveController syncMoveController = SyncMoveController.getInstance();

	public PolygonalMoveController(DrawPane parent, PolygonalArrowShape polygonalArrowShape, Cursor cursor, int id) {
		this.parent = parent;
		this.cursor = cursor;
		this.id = id;
		this.linkedList = new LinkedList<Node>();
		this.polygonalArrowShape = polygonalArrowShape;
		this.connections = new MoveController[3];
		this.index = 1;
		this.isSelected = false;
		this.hiddenPoint = new PointEntity(-100, -100);
		this.startDraggablePoint = new DraggablePoint(hiddenPoint) {
			private PointEntity startPoint;

			@Override
			public void update(PointEntity pointEntity) {
				updateCircle(pointEntity);
				polygonalArrowShape.setStartPoint(pointEntity);
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
			}

			@Override
			public void released(PointEntity pointEntity) {
				if(!startPoint.equals(polygonalArrowShape.getStartPoint())){
					parent.change(getID(), PolygonalMoveController.this);
				}
				ConnectionController.getInstance().separate(connections[0], PolygonalMoveController.this);
				removeConnection(null);
				PointEntity point = ConnectionController.getInstance().connnect(PolygonalMoveController.this,
						pointEntity);
				polygonalArrowShape.setStartPoint(point);
				index = 1;
				updateCircle(point);
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				this.startPoint = polygonalArrowShape.getStartPoint();
				index = 0;
			}
		};
		this.centerDraggablePoint = new DraggablePoint(hiddenPoint) {
			private PointEntity centerPoint;

			@Override
			public void update(PointEntity pointEntity) {
				if (polygonalArrowShape.isHorizontal()) {
					updateCircle(new PointEntity(polygonalArrowShape.getCenterPoint().getX(), pointEntity.getY()));
				} else {
					updateCircle(new PointEntity(pointEntity.getX(), polygonalArrowShape.getCenterPoint().getY()));
				}
				polygonalArrowShape.setCenterPoint(pointEntity);
			}

			@Override
			public void released(PointEntity pointEntity) {
				if(!centerPoint.equals(polygonalArrowShape.getCenterPoint())){
					parent.change(getID(), PolygonalMoveController.this);
				}
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				this.centerPoint = polygonalArrowShape.getCenterPoint();
			}

		};
		this.endDraggablePoint = new DraggablePoint(hiddenPoint) {
			private PointEntity endPoint;

			@Override
			public void update(PointEntity pointEntity) {
				updateCircle(pointEntity);
				polygonalArrowShape.setEndPoint(pointEntity);
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
			}

			@Override
			public void released(PointEntity pointEntity) {
				if(!endPoint.equals(polygonalArrowShape.getCenterPoint())){
					parent.change(getID(), PolygonalMoveController.this);
				}
				ConnectionController.getInstance().separate(connections[2], PolygonalMoveController.this);
				removeConnection(null);
				PointEntity point = ConnectionController.getInstance().connnect(PolygonalMoveController.this,
						pointEntity);
				polygonalArrowShape.setEndPoint(point);
				index = 1;
				updateCircle(point);
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				this.endPoint = polygonalArrowShape.getEndPoint();
				index = 2;
			}
		};
		this.draggable = new Draggable() {
			private PointEntity startPoint;
			private PointEntity endPoint;
			private PointEntity centerPoint;

			@Override
			protected void whenReleased(MouseEvent mouse) {
				if (!startPoint.equals(polygonalArrowShape.getStartPoint())
						|| !endPoint.equals(polygonalArrowShape.getEndPoint())||!centerPoint.equals(polygonalArrowShape.getCenterPoint())) {
//					parent.change(getID(), PolygonalMoveController.this);
					syncMoveController.movingFinished();
				}
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				this.startPoint = polygonalArrowShape.getStartPoint();
				this.centerPoint = polygonalArrowShape.getCenterPoint();
				this.endPoint = polygonalArrowShape.getEndPoint();
				if (!isSelected) {
					parent.informSelected(PolygonalMoveController.this);
				}
				setSelected(true);
				syncMoveController.initialMoving();
			}

			@Override
			protected void whenMoved(MouseEvent mouse) {
				if (PolygonalMoveController.this.cursor != Cursor.DEFAULT) {
					polygonalArrowShape.getPolygon().setOnMouseMoved(e -> {
						polygonalArrowShape.getPolygon().setCursor(PolygonalMoveController.this.cursor);
					});
				}
			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				// ArrowMoveController.this.move(xDelta, yDelta);
				// update();
				syncMoveController.informMoving(new MoveMsg(xDelta, yDelta));
			}

			@Override
			protected Node getNode() {
				return polygonalArrowShape.getPolygon();
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
		if (isSelected) {
			setHidden(false);
		} else {
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
		Type type = Type.ARROW_ERECT;
		if (polygonalArrowShape.isHorizontal()) {
			type = Type.ARROW_HORIZONTAL;
		}
		return new ArrowState(polygonalArrowShape.getStartPoint(),polygonalArrowShape.getCenterPoint(),polygonalArrowShape.getEndPoint(), this.id, type);
	}

	@Override
	public void setState(DrawableState state) {
		ArrowState arrowState = (ArrowState) state;
		polygonalArrowShape.setStartPoint(arrowState.getStartPoint());
		polygonalArrowShape.setEndPoint(arrowState.getEndPoint());
		polygonalArrowShape.setCenterPoint(arrowState.getCenterPoint());
		updateDraggable();
	}

	@Override
	public LinkedList<PointEntity> getConnectionPoints() {
		if (connections[1] != null)
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

	public void setHidden(boolean isHidden) {
		if (isHidden) {
			startDraggablePoint.updateCircle(hiddenPoint);
			centerDraggablePoint.updateCircle(hiddenPoint);
			endDraggablePoint.updateCircle(hiddenPoint);
		} else {
			updateDraggable();
		}
	}

	public void updateDraggable() {
		startDraggablePoint.updateCircle(polygonalArrowShape.getStartPoint());
		centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
		endDraggablePoint.updateCircle(polygonalArrowShape.getEndPoint());
	}

	@Override
	public void setChange(MoveMsg changeMsg) {
		polygonalArrowShape.move(changeMsg.getDeltaX(), changeMsg.getDeltaY());
		updateDraggable();
	}



}
