package view.shape;

import java.util.LinkedList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import view.Changable;
import view.DrawElement;
import view.move.MovePoint;

public class DrawRectangle extends DrawElement implements Changable {
	private DraggableRectangle rectangle;
	private MovePoint[] points;

	public DrawRectangle() {
		rectangle = new DraggableRectangle();
		rectangle.setFill(Color.GREEN);
		points = new MovePoint[8];
		double base[] = { 0, 0.5, 1 };
		int index = 0;
		for (int i = 0; i < base.length; i++) {
			for (int j = 0; j < base.length; j++) {
				if (i == 1 && j == 1) {
					continue;
				}
				points[index++] = new MovePoint(this,
						rectangle.xProperty().add(rectangle.widthProperty().multiply(base[i])),
						rectangle.yProperty().add(rectangle.heightProperty().multiply(base[j])));
			}
		}
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
	public void xAdd(double delta) {
		rectangle.xAdd(delta);
	}

	@Override
	public void yAdd(double delta) {
		rectangle.yAdd(delta);
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
	public void change(){
		double x, y;
		x = y = -100000;

//		for(MovePoint p:points){
//			x = Math.min(x,  p.getX())
//		}
	}

}
