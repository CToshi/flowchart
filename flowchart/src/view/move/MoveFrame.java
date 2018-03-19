package view.move;

import java.util.LinkedList;

import application.Main;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Rotate;
import view.DrawElement;
import view.shape.DraggableRectangle;

public class MoveFrame extends DrawElement {
	private DraggableRectangle rectangle;
	private MovePoint[] points;
	private static final double offset[][] = { { 0, 0 }, { 1, 1 }, { 0, 0.5 }, { 1, 0.5 }, { 0.5, 0 }, { 0.5, 1 },
			{ 1, 0 }, { 0, 1 } };
	private static final Cursor[] CURSORS = { Cursor.SE_RESIZE, Cursor.E_RESIZE, Cursor.S_RESIZE, Cursor.SW_RESIZE };
	private Rotate rotate;
	private RotatePoint rotatePoint;

	public MoveFrame() {
		rotate = new Rotate(0);
		rectangle = new DraggableRectangle() {

			@Override
			protected void deal(double xDelta, double yDelta) {
				this.move(xDelta, yDelta);
				MoveFrame.this.fixPosition();
			}

			@Override
			protected void whenReleased(MouseEvent mouse) {
				Main.test("rel", mouse.getX(),mouse.getY());
				fixPosition();
				fixPivot();
			}
		};
		rectangle.getTransforms().add(rotate);
		rectangle.setStroke(Color.GREEN);
		rectangle.setStrokeWidth(5);
		rectangle.setFill(Color.TRANSPARENT);
		points = new MovePoint[8];
		for (int i = 0; i < points.length; i++) {
			points[i] = new MovePoint(this, Math.abs(offset[i][0] - 0.5) > 0.0001,
					Math.abs(offset[i][1] - 0.5) > 0.0001, CURSORS[i / 2]);
			points[i].getTransforms().add(rotate);
		}
		for (int i = 0; i < points.length; i++) {
			points[i].setOtherPoint(points[i ^ 1]);
		}
		rotatePoint = new RotatePoint(this);
		rotatePoint.getTransforms().add(rotate);
		this.fixPosition();
		this.fixPivot();
	}

	@Override
	public Shape[] getShapes() {
		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(rectangle);
		for (int i = 0; i < points.length; i++) {
			shapes.add(points[i]);
		}
		shapes.add(rotatePoint);
		return shapes.toArray(new Shape[0]);
	}

	@Override
	public void setOutBound(boolean isOutBound) {
		rectangle.setOutBound(isOutBound);
	}

	public void fixPosition() {
		for (int i = 0; i < points.length; i++) {
			points[i].setCenterXY(rectangle.getX() + rectangle.getWidth() * offset[i][0],
					rectangle.getY() + rectangle.getHeight() * offset[i][1]);
		}
		rotatePoint.setCenterXY(rectangle.getCenterX(), rectangle.getY() - 30);
	}

	public void fixPivot() {
		Main.test("fixpivot");
		Main.test(rectangle.getX(),rectangle.getY());
		rotate.setPivotX(rectangle.getX() + rectangle.getWidth() * 0.5);
		rotate.setPivotY(rectangle.getY() + rectangle.getHeight() * 0.5);
	}

	public void setHidden() {
		for (int i = 0; i < points.length; i++) {
			points[i].setHide();
		}
	}

	public void setShow() {
		for (int i = 0; i < points.length; i++) {
			points[i].setShow();
		}
	}

	public void setX(double value) {
		rectangle.setX(value);
	}

	public void setY(double value) {
		rectangle.setY(value);
	}

	public void setWidth(double value) {
		rectangle.setWidth(value);
	}

	public void setHeight(double value) {
		rectangle.setHeight(value);
	}

	public void setRotate(double value) {
		rotate.setAngle(value);
	}
}
