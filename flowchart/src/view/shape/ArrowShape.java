package view.shape;

import java.util.LinkedList;

import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import javafx.scene.shape.Line;

public class ArrowShape extends ShapeItem{

	private RectangleEntity rectangle;
	private TriangleShape triangleShape;
	private Line line;
	private LinkedList<Node> linkedList;

	public ArrowShape(PointEntity startPoint,double length){
		this(startPoint, new PointEntity(startPoint.getX(),startPoint.getY()+length));
	}

	public ArrowShape(PointEntity startPoint,PointEntity endPoint){
		this.rectangle = new RectangleEntity(0,0,0,0);
		System.out.println(startPoint.getX()+" "+startPoint.getY()+" "+endPoint.getX()+" "+endPoint.getY());
		line = new Line(startPoint.getX(),startPoint.getY(),endPoint.getX(),endPoint.getY());
		triangleShape = new TriangleShape(startPoint, endPoint);
		this.linkedList = new LinkedList<Node>();
		linkedList.add(line);
		linkedList.addAll(triangleShape.getNodes());
	}

	public Line getLine() {
		return line;
	}

	public PointEntity getStartPoint() {
		return new PointEntity(line.getStartX(),line.getStartY());
	}

	public void setStartPoint(PointEntity startPoint) {
		line.setStartX(startPoint.getX());
		line.setStartY(startPoint.getY());
		update();
	}

	public PointEntity getEndPoint() {
		return new PointEntity(line.getEndX(),line.getEndY());
	}

	public void setEndPoint(PointEntity endPoint) {
		line.setEndX(endPoint.getX());
		line.setEndY(endPoint.getY());
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
		return new RectangleEntity(this.getStartPoint(), line.getEndX()-line.getStartX(), line.getEndY()-line.getStartY());
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		line.setStartX(rectangle.getX()+rectangle.getWidth()/8f);
		line.setStartY(rectangle.getY()+rectangle.getHeight()/2f);
		line.setEndX(line.getStartX()+rectangle.getWidth()/4f*3f);
		line.setEndY(line.getStartY());
		update();
	}

	public void update(){
		triangleShape.setDirectPoint(this.getStartPoint());
		triangleShape.setVertex(this.getEndPoint());
	}
}
