package view.shape;

import java.util.LinkedList;

import entities.DrawableState.Type;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polyline;
import utility.Util;

public class CurveRectangle extends ShapeItem{

	private LinkedList<Node> linkedList;
	private RectangleEntity rectangle;
	private Polyline polyline;
	private Arc arc;
	private Arc arc2;
	public CurveRectangle(double x,double y,double width, double height){
		this.rectangle = new RectangleEntity(x,y,width,height);
		polyline = new Polyline();
		arc = new Arc();
		arc2 = new Arc();
		linkedList = Util.getList(new Node[]{polyline, arc, arc2});
	}

	private void update() {
		PointEntity leftTop = rectangle.getLeftTop();
		double width = rectangle.getWidth();
		double height = rectangle.getHeight();
		ObservableList<Double> list = polyline.getPoints();
		list.clear();
		double angle = 30.0;
		double radian = angle / 180.0 * Math.PI;
		double radius = width / (4.0 * Math.sin(radian * 0.5));
		double h = height - radius * (1.0 - Math.cos(radian * 0.5));
		list.add(leftTop.getX());
		list.add(leftTop.getY() + h);
		list.add(leftTop.getX());
		list.add(leftTop.getY());
		list.add(leftTop.getX() + width);
		list.add(leftTop.getY());
		list.add(leftTop.getX() + width);
		list.add(leftTop.getY() + h);
		arc.setCenterX(leftTop.getX() + Math.sin(radian * 0.5) * radius);
		arc.setCenterY(leftTop.getY() + height - radius);
		arc.setRadiusX(radius);
		arc.setRadiusY(radius);
		arc.setStartAngle(0.5 * (180.0 - angle) + 180.0);
		arc.setLength(angle);
		arc.setFill(Color.WHITE);
		arc.setStroke(Color.BLACK);
		arc.setType(ArcType.OPEN);
		arc2.setCenterX(leftTop.getX() + 3.0 * Math.sin(radian * 0.5) * radius);
		arc2.setCenterY(leftTop.getY() + h + radius * Math.cos(radian * 0.5));
		arc2.setRadiusX(radius);
		arc2.setRadiusY(radius);
		arc2.setStartAngle(0.5 * (180.0 - angle));
		arc2.setLength(angle);
		arc2.setFill(Color.WHITE);
		arc2.setStroke(Color.BLACK);
		arc2.setType(ArcType.OPEN);
	}

	@Override
	public RectangleEntity getRectangle() {
		return new RectangleEntity(rectangle.getX(),rectangle.getY(),rectangle.getWidth(),rectangle.getHeight());
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		this.rectangle.setX(rectangle.getX());
		this.rectangle.setY(rectangle.getY());
		this.rectangle.setWidth(rectangle.getWidth());
		this.rectangle.setHeight(rectangle.getHeight());
		update();
	}

	@Override
	public LinkedList<Node> getNodes() {
		return linkedList;
	}

	@Override
	public Type getType() {
		return Type.CurveRectangle;
	}

}
