package view.shape;

import java.util.LinkedList;

import application.Main;
import entities.DrawableState.Type;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class ArrowShape extends ShapeItem{

	private RectangleEntity rectangle;
	private TriangleShape triangleShape;
	private Line line;
	private LinkedList<Node> linkedList;
	private PointEntity startPoint;
	private PointEntity endPoint;

	public ArrowShape(PointEntity startPoint,double length){
		this(startPoint, new PointEntity(startPoint.getX()+length,startPoint.getY()));
	}

	public ArrowShape(PointEntity startPoint,PointEntity endPoint){
		this.rectangle = new RectangleEntity(0,0,0,0);
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		line = new Line(startPoint.getX(),startPoint.getY(),endPoint.getX(),endPoint.getY());
		triangleShape = new TriangleShape(startPoint, endPoint);
		this.linkedList = new LinkedList<Node>();
		linkedList.add(line);
		Polygon polygon = (Polygon)triangleShape.getNodes().get(0);
		Main.test(polygon.getPoints());
		linkedList.addAll(triangleShape.getNodes());
	}

	public PointEntity getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(PointEntity startPoint) {
		this.startPoint = startPoint;
		update();
	}

	public PointEntity getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(PointEntity endPoint) {
		this.endPoint = endPoint;
		update();
	}

	@Override
	public LinkedList<Node> getNodes() {
		return linkedList;
	}

	@Override
	@Deprecated
	public void setX(double value) {
		rectangle.setX(value);
	}

	@Override
	@Deprecated
	public void setY(double value) {
		rectangle.setY(value);
	}

	@Override
	@Deprecated
	public void setWidth(double value) {
		rectangle.setWidth(value);
	}

	@Override
	@Deprecated
	public void setHeight(double value) {
		rectangle.setHeight(value);
	}

	@Override
	public RectangleEntity getRectangle() {
		return new RectangleEntity(startPoint, endPoint.getX()-startPoint.getX(), endPoint.getY()-startPoint.getY());
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		startPoint.setX(rectangle.getX()+rectangle.getWidth()/4f);
		startPoint.setY(rectangle.getY()+rectangle.getHeight()/4f);
		endPoint.setX(startPoint.getX()+rectangle.getWidth()/2f);
		endPoint.setY(startPoint.getY());
		update();
	}

	private void update(){
		line.setStartX(startPoint.getX());
		line.setStartY(startPoint.getY());
		line.setEndX(endPoint.getX());
		line.setEndY(endPoint.getY());
		triangleShape.setDirectPoint(startPoint);
		triangleShape.setVertex(endPoint);
	}

	@Override
	public Type getType() {
		return null;
	}
}