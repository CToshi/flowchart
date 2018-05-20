package view.move;

import java.util.LinkedList;

import entities.ArrowState;
import entities.DrawableState;
import entities.DrawableState.Type;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import ui.DrawPane;
import view.inter.Draggable;
import view.shape.DraggableArrow;
import view.shape.PolygonalArrowShape;

public class PolygonalMoveController implements Cloneable, MoveController, DraggableArrow {

	private PolygonalArrowShape polygonalArrowShape;
	private LinkedList<Node> linkedList;
	private DraggablePoint startDraggablePoint;
	private DraggablePoint centerDraggablePoint;
	private DraggablePoint endDraggablePoint;
	private Cursor cursor;
	private boolean isSelected;
	private PointEntity hiddenPoint;
	private int id;

	private ArrowMovePoint startMovePoint;
	private ArrowMovePoint endMovePoint;

	private SyncMoveController syncMoveController = SyncMoveController.getInstance();

	private ConnectionController connectionController = ConnectionController.getInstance();

	public PolygonalMoveController(DrawPane parent, PolygonalArrowShape polygonalArrowShape, Cursor cursor, int id) {
		this.cursor = cursor;
		this.id = id;
		this.linkedList = new LinkedList<Node>();
		this.polygonalArrowShape = polygonalArrowShape;
		this.isSelected = false;
		this.hiddenPoint = new PointEntity(-100, -100);
		this.startMovePoint = new ArrowMovePoint(true, this);
		this.endMovePoint = new ArrowMovePoint(false, this);
		this.startDraggablePoint = new DraggablePoint(hiddenPoint) {
			private PointEntity startPoint;

			@Override
			public void update(PointEntity pointEntity) {
				updateCircle(pointEntity);
				polygonalArrowShape.setStartPoint(pointEntity);
				centerDraggablePoint.updateCircle(polygonalArrowShape.getCenterPoint());
				connectionController.separate(startMovePoint.getLinkedPoint(), startMovePoint);
				PointEntity point = connectionController.connnect(PolygonalMoveController.this, pointEntity,
						startMovePoint);
				polygonalArrowShape.setStartPoint(point);
				updateCircle(point);
			}

			@Override
			public void released(PointEntity pointEntity) {
				if (!startPoint.equals(polygonalArrowShape.getStartPoint())) {
					parent.change(getID(), PolygonalMoveController.this);
				}
				connectionController.whenMovingFinish();
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				this.startPoint = polygonalArrowShape.getStartPoint();
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
				if (!centerPoint.equals(polygonalArrowShape.getCenterPoint())) {
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
				connectionController.separate(endMovePoint.getLinkedPoint(), endMovePoint);
				PointEntity point = connectionController.connnect(PolygonalMoveController.this, pointEntity,
						endMovePoint);
				polygonalArrowShape.setEndPoint(point);
				updateCircle(point);
			}

			@Override
			public void released(PointEntity pointEntity) {
				if (!endPoint.equals(polygonalArrowShape.getCenterPoint())) {
					parent.change(getID(), PolygonalMoveController.this);
				}
				connectionController.whenMovingFinish();
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				this.endPoint = polygonalArrowShape.getEndPoint();
			}
		};
		new Draggable() {
			private PointEntity startPoint;
			private PointEntity endPoint;
			private PointEntity centerPoint;

			@Override
			protected void whenReleased(MouseEvent mouse) {
				if (!startPoint.equals(polygonalArrowShape.getStartPoint())
						|| !endPoint.equals(polygonalArrowShape.getEndPoint())
						|| !centerPoint.equals(polygonalArrowShape.getCenterPoint())) {
					// parent.change(getID(), PolygonalMoveController.this);
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
		return new ArrowState(polygonalArrowShape.getStartPoint(), polygonalArrowShape.getCenterPoint(),
				polygonalArrowShape.getEndPoint(), this.id, type, isSelected);
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
	public LinkedList<LinkedPoint> getConnectionPoints() {
		return polygonalArrowShape.getLinkedPoints();
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

	@Override
	public PointEntity getStartPoint() {
		return polygonalArrowShape.getStartPoint();
	}

	@Override
	public void setStartPoint(PointEntity pointEntity) {
		polygonalArrowShape.setStartPoint(pointEntity);
	}

	@Override
	public PointEntity getEndPoint() {
		return polygonalArrowShape.getEndPoint();
	}

	@Override
	public void setEndPoint(PointEntity pointEntity) {
		polygonalArrowShape.setEndPoint(pointEntity);
	}

	@Override
	public void setLinkedPointsHidden(boolean isHidden) {
		polygonalArrowShape.setLinkedPointsHidden(isHidden);
	}

}
