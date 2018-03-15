package view.shape;

import java.util.LinkedList;

import application.Main;
import entities.PointEntity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import view.Changable;
import view.DrawElement;
import view.move.MovePoint;

public class DrawRectangle extends DrawElement implements Changable{
	private DraggableRectangle rectangle;
	private MovePoint[] points;


	public DrawRectangle() {
		rectangle = new DraggableRectangle();
		rectangle.setFill(Color.GREEN);
		points = new MovePoint[1];
		points[0] = new MovePoint(this, rectangle.xProperty(), rectangle.yProperty());
	}

	@Override
	public Shape[] getShapes() {
		LinkedList<Shape> shapes = new LinkedList<>();
		shapes.add(rectangle);
		for(int i = 0;i<points.length;i++){
			shapes.add(points[i]);
		}

		return shapes.toArray(new Shape[0]);
	}


	@Override
	public void xAdd(double delta) {
		rectangle.setX(rectangle.getX() + delta);
	}

	@Override
	public void yAdd(double delta) {
		rectangle.setY(rectangle.getY() + delta);
	}

	@Override
	public void widthAdd(double delta) {
		rectangle.widthAdd(delta);
	}

	@Override
	public void heightAdd(double delta) {
		rectangle.heightAdd(delta);
	}

	@Override
	public void setStopDragged() {
		rectangle.setStopDragged();
	}


}
