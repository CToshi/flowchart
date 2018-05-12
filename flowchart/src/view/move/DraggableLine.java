package view.move;

import java.util.LinkedList;

import entities.PointEntity;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import view.inter.Drawable;

public class DraggableLine implements Drawable {

	private Line self;
	private LinkedList<Node> linkedList;
	private PointEntity startPoint;
	private PointEntity endPoint;
	private PointEntity firstMousePosition;
	private PointEntity mousePosition;
	private Cursor cursor;
	private ArrowMoveController moveController;

	public DraggableLine(Line line,ArrowMoveController moveController) {
		this(line,moveController,Cursor.MOVE);
	}

	private DraggableLine(Line line,ArrowMoveController moveController,Cursor cursor) {
		this.self = line;
		this.startPoint = new PointEntity(line.getStartX(),line.getStartY());
		this.endPoint = new PointEntity(line.getEndX(),line.getEndY());
		this.cursor = cursor;
		this.moveController = moveController;
		this.firstMousePosition = new PointEntity(0, 0);
		this.mousePosition = new PointEntity(0, 0);
		initListener();
	}

	private void initListener() {
		self.setOnMousePressed(e -> {
			firstMousePosition.setXY(e.getX(), e.getY());
			whenPressed(e);
		});
		self.setOnMouseDragged(e -> {
			mousePosition.setXY(e.getX(), e.getY());
			double xDelta = e.getX() - firstMousePosition.getX();
			double yDelta = e.getY() - firstMousePosition.getY();
			this.deal(xDelta, yDelta);
			firstMousePosition.setXY(e.getX(), e.getY());
		});
		self.setOnMouseReleased(e -> {
			whenReleased(e);
		});
		if (this.cursor != Cursor.DEFAULT) {
			self.setOnMouseMoved(e -> {
				self.setCursor(this.cursor);
			});
		}
	}

	private void deal(double xDelta, double yDelta) {
		this.move(xDelta, yDelta);
		moveController.update();
	};

	private void whenPressed(MouseEvent mouse) {
		moveController.setSelected(true);
	};

	private void whenReleased(MouseEvent mouse) {

	};

	public PointEntity getMouse() {
		return mousePosition;
	}

	@Override
	public LinkedList<Node> getNodes() {
		return linkedList;
	}

	public PointEntity getStartPoint() {
		return startPoint;
	}

	public PointEntity getEndPoint() {
		return endPoint;
	}

	public void setStartPoint(double x, double y) {
		startPoint.setX(x);
		startPoint.setY(y);
		self.setStartX(x);
		self.setStartY(y);
	}

	public void setEndPoint(double x, double y) {
		endPoint.setX(x);
		endPoint.setY(y);
		self.setEndX(x);
		self.setEndY(y);
	}

	protected void move(double xDelta, double yDelta) {
		this.setStartPoint(self.getStartX() + xDelta, self.getStartY() + yDelta);
		this.setEndPoint(self.getEndX() + xDelta, self.getEndY() + yDelta);
	}
}
