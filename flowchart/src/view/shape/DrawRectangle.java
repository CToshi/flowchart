//package view.shape;
//
//import java.util.LinkedList;
//
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Shape;
//import view.Changable;
//import view.DrawElement;
////import view.move.MovePoint;
//
//public class DrawRectangle extends DrawElement{
//	private DraggableRectangle rectangle;
////	private MovePoint[] points;
//	private static final double offset[][] = { { 0, 0 }, { 1, 1 }, { 0, 0.5 }, { 1, 0.5 }, { 0.5, 0 }, { 0.5, 1 },
//			{ 1, 0 }, { 0, 1 } };
//
//	public DrawRectangle() {
//		rectangle = new DraggableRectangle();
//		rectangle.setFill(Color.GREEN);
////		points = new MovePoint[8];
////		double offset[][] = { { 0, 0 }, { 1, 1 }, { 0, 0.5 }, { 1, 0.5 }, { 0.5, 0 }, { 0.5, 1 }, { 1, 0 }, { 0, 1 } };
////		for (int i = 0; i < points.length; i++) {
////			points[i] = new MovePoint(this, rectangle.xProperty().add(rectangle.widthProperty().multiply(offset[i][0])),
////					rectangle.yProperty().add(rectangle.heightProperty().multiply(offset[i][1])),
////					Math.abs(offset[i][0] - 0.5) > 0.0001, Math.abs(offset[i][1] - 0.5) > 0.0001, offset[i][0],
////					offset[i][1]);
////			// points[i] = new MovePoint(this, Math.abs(offset[i][0] - 0.5) >
////			// 0.0001, Math.abs(offset[i][1] - 0.5) > 0.0001);
////		}
////		for (int i = 0; i < points.length; i++) {
////			points[i].setOtherPoint(points[i ^ 1]);
////		}
////		this.fixPosition(points[0].getX(), points[0].getY());
////		points[0].setFill(Color.YELLOW);
////		// fixPosition();
//	}
//
//	@Override
//	public Shape[] getShapes() {
//		LinkedList<Shape> shapes = new LinkedList<>();
//		shapes.add(rectangle);
////		for (int i = 0; i < points.length; i++) {
////			shapes.add(points[i]);
////		}
//
//		return shapes.toArray(new Shape[0]);
//	}
//
////	@Override
////	public void xAdd(double delta) {
////		rectangle.xAdd(delta);
////	}
////
////	@Override
////	public void yAdd(double delta) {
////		rectangle.yAdd(delta);
////	}
//
//	public void setX(double value) {
//		rectangle.setX(value);
//	}
//
//	public void setY(double value) {
//		rectangle.setY(value);
//	}
//
////	@Override
////	public void widthAdd(double delta) {
////		rectangle.widthAdd(delta);
////	}
////
////	@Override
////	public void heightAdd(double delta) {
////		rectangle.heightAdd(delta);
////	}
//
//	@Override
//	public void setOutBound(boolean isOutBound) {
//		rectangle.setOutBound(isOutBound);
//	}
//
////	public void fixPosition(double left, double top) {
////		points[0].setX(left);
////		points[0].setY(top);
////		for (int i = 1; i < points.length; i++) {
////			points[i].setXY(points[0].getX() + rectangle.getRectWidth() * offset[i][0],
////					points[0].getY() + rectangle.getRectHeight() * offset[i][1]);
////		}
////	}
////	public double getRectWidth(){
////		return rectangle.getRectWidth();
////	}
////	public double getRectHeight(){
////		return rectangle.getRectHeight();
////	}
//
//
//	public void setWidth(double value) {
//		rectangle.setWidth(value);
//	}
//
//	public void setHeight(double value) {
//		rectangle.setHeight(value);
//	}
//
//}
