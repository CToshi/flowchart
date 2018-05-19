package view.move;

import java.util.LinkedList;

import entities.ArrowState;
import entities.DrawableState;
import entities.PointEntity;
import entities.RectangleEntity;
import entities.DrawableState.Type;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import ui.DrawPane;
import utility.Util;
import view.inter.Draggable;
import view.shape.ArrowShape;
import view.shape.DraggableArrow;
import view.shape.Parallelogram;

public class ArrowMoveController implements Cloneable, MoveController,DraggableArrow {

	private ArrowMovePoint startMovePoint;
	private ArrowMovePoint endMovePoint;
	private ArrowShape arrowShape;
//	private DraggableLine draggableLine;
	private Draggable draggable;
	private LinkedList<Node> linkedList;
	private DraggablePoint startDraggablePoint;
	private DraggablePoint endDraggablePoint;
	private PointEntity hiddenPoint;
	private DrawPane parent;
	private boolean isSelected;
	private Cursor cursor;
	private int id;
	private SyncMoveController syncMoveController = SyncMoveController.getInstance();

	private ConnectionController connectionController = ConnectionController.getInstance();

	public ArrowMoveController(DrawPane parent,ArrowShape arrowShape,int id) {
		this.parent = parent;
		this.id = id;
		this.linkedList = new LinkedList<Node>();
		this.arrowShape = arrowShape;
		this.isSelected = false;
		this.startMovePoint = new ArrowMovePoint(true, this);
		this.endMovePoint = new ArrowMovePoint(false, this);
//		this.draggableLine = new DraggableLine(arrowShape.getLine(),this);
		this.hiddenPoint = new PointEntity(-100,-100);
		this.cursor = Cursor.MOVE;
		this.draggable = new Draggable() {
			private PointEntity startPoint;
			private PointEntity endPoint;

			@Override
			protected void whenReleased(MouseEvent mouse) {
				if(!startPoint.equals(arrowShape.getStartPoint())||!endPoint.equals(arrowShape.getEndPoint())){
//					parent.change(getID(), ArrowMoveController.this);
					syncMoveController.movingFinished();
				}
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				this.startPoint = arrowShape.getStartPoint();
				this.endPoint = arrowShape.getEndPoint();
				if (!isSelected) {
					parent.informSelected(ArrowMoveController.this);
				}
				setSelected(true);
				syncMoveController.initialMoving();
			}

			@Override
			protected void whenMoved(MouseEvent mouse) {
				if (ArrowMoveController.this.cursor != Cursor.DEFAULT) {
					arrowShape.getPolygon().setOnMouseMoved(e -> {
						arrowShape.getPolygon().setCursor(ArrowMoveController.this.cursor);
					});
				}
			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
//				ArrowMoveController.this.move(xDelta, yDelta);
//				update();
				syncMoveController.informMoving(new MoveMsg(xDelta, yDelta));
			}

			@Override
			protected Node getNode() {
				return arrowShape.getPolygon();
			}
		};
		this.startDraggablePoint = new DraggablePoint(hiddenPoint) {
			private PointEntity startPoint;

			@Override
			public void update(PointEntity pointEntity) {
				updateCircle(pointEntity);
				arrowShape.setStartPoint(pointEntity);
				connectionController.separate(startMovePoint.getLinkedPoint(),startMovePoint);
				PointEntity point = connectionController.connnect(ArrowMoveController.this,pointEntity,startMovePoint);
				arrowShape.setStartPoint(point);
				updateCircle(point);
			}
			@Override
			public void released(PointEntity pointEntity) {
				if(!startPoint.equals(arrowShape.getStartPoint())){
					parent.change(getID(), ArrowMoveController.this);
				}
				connectionController.whenMovingFinish();
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				this.startPoint = arrowShape.getStartPoint();
			}};
		this.endDraggablePoint = new DraggablePoint(hiddenPoint) {
			private PointEntity endPoint;

			@Override
			public void update(PointEntity pointEntity) {
				updateCircle(pointEntity);
				arrowShape.setEndPoint(pointEntity);
				connectionController.separate(endMovePoint.getLinkedPoint(),endMovePoint);
				PointEntity point = connectionController.connnect(ArrowMoveController.this, pointEntity,endMovePoint);
				arrowShape.setEndPoint(point);
				updateCircle(point);
			}

			@Override
			public void released(PointEntity pointEntity) {
				if(!endPoint.equals(arrowShape.getEndPoint())){
					parent.change(getID(), ArrowMoveController.this);
				}
				connectionController.whenMovingFinish();
			}

			@Override
			public void pressed(PointEntity pointEntity) {
				this.endPoint = arrowShape.getEndPoint();
			}};
//		this.parallelogram = new Parallelogram()

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
		startDraggablePoint.updateCircle(arrowShape.getStartPoint());
		endDraggablePoint.updateCircle(arrowShape.getEndPoint());
		arrowShape.update();
	}
	@Override
	public DrawableState getState() {
		return new ArrowState(arrowShape.getStartPoint(),null,arrowShape.getEndPoint(), this.id, Type.ARROW,isSelected);
	}

	@Override
	public void setState(DrawableState state) {
		ArrowState arrowState = (ArrowState)state;
		arrowShape.setStartPoint(arrowState.getStartPoint());
		arrowShape.setEndPoint(arrowState.getEndPoint());
		update();
	}

	@Override
	public LinkedList<LinkedPoint> getConnectionPoints() {
		return arrowShape.getLinkedPoints();
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

	public void move(double xDelta,double yDelta){
		arrowShape.setStartPoint(new PointEntity(arrowShape.getStartPoint().getX()+xDelta
				,arrowShape.getStartPoint().getY()+yDelta));
		arrowShape.setEndPoint(new PointEntity(arrowShape.getEndPoint().getX()+xDelta
				,arrowShape.getEndPoint().getY()+yDelta));
		update();
	}

	public void setChange(MoveMsg changeMsg) {
		this.move(changeMsg.getDeltaX(), changeMsg.getDeltaY());
	}

	@Override
	public void setStartPoint(PointEntity pointEntity) {
		arrowShape.setStartPoint(pointEntity);
	}

	@Override
	public void setEndPoint(PointEntity pointEntity) {
		arrowShape.setEndPoint(pointEntity);
	}

	@Override
	public PointEntity getStartPoint() {
		return arrowShape.getStartPoint();
	}

	@Override
	public PointEntity getEndPoint() {
		// TODO Auto-generated method stub
		return arrowShape.getStartPoint();
	}

	@Override
	public void setLinkedPointsHidden(boolean isHidden) {
		arrowShape.setLinkedPointsHidden(isHidden);
	}


}