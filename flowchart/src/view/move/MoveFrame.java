package view.move;

import java.util.LinkedList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import view.DrawElement;
import view.shape.DraggableRectangle;

public class MoveFrame extends DrawElement {
	private DraggableRectangle rectangle;
	private MovePoint[] points;
	private static final double offset[][] = { { 0, 0 }, { 1, 1 }, { 0, 0.5 }, { 1, 0.5 }, { 0.5, 0 }, { 0.5, 1 },
			{ 1, 0 }, { 0, 1 } };

	public MoveFrame() {
		rectangle = new DraggableRectangle();
		rectangle.setFill(Color.GREEN);
		points = new MovePoint[8];
		double offset[][] = { { 0, 0 }, { 1, 1 }, { 0, 0.5 }, { 1, 0.5 }, { 0.5, 0 }, { 0.5, 1 }, { 1, 0 }, { 0, 1 } };
		for (int i = 0; i < points.length; i++) {
			points[i] = new MovePoint(this, Math.abs(offset[i][0] - 0.5) > 0.0001,
					Math.abs(offset[i][1] - 0.5) > 0.0001);
			// points[i] = new MovePoint(this,
			// rectangle.xProperty().add(rectangle.widthProperty().multiply(offset[i][0])),
			// rectangle.yProperty().add(rectangle.heightProperty().multiply(offset[i][1])),
			// Math.abs(offset[i][0] - 0.5) > 0.0001, Math.abs(offset[i][1] -
			// 0.5) > 0.0001, offset[i][0],
			// offset[i][1]);
			// points[i] = new MovePoint(this, Math.abs(offset[i][0] - 0.5) >
			// 0.0001, Math.abs(offset[i][1] - 0.5) > 0.0001);
		}
		for (int i = 0; i < points.length; i++) {
			points[i].setOtherPoint(points[i ^ 1]);
		}
		// this.fixPosition(points[0].getX(), points[0].getY());
		points[0].setFill(Color.YELLOW);
		this.fixPosition();
	}

	@Override
	public Shape[] getShapes() {
		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(rectangle);
		for (int i = 0; i < points.length; i++) {
			shapes.add(points[i]);
		}

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
		points[0].setFill(Color.YELLOW);
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

}
