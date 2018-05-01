package view.shape;

import java.util.LinkedList;

import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class ArrowShape extends ShapeItem{

	private Rectangle self;
	private TriangleShape triangleShape;
	private Line line;
	private LinkedList<Node> linkedList;

	public ArrowShape(double x,double y,double width,double height,PointEntity startPoint,PointEntity endPoint){
		self = new Rectangle(x,y,width,height);
		line = new Line(startPoint.getX(),startPoint.getY(),endPoint.getX(),endPoint.getY());
		triangleShape = new TriangleShape(startPoint, endPoint);
		this.linkedList = new LinkedList<Node>();
		linkedList.add(line);
		linkedList.addAll(triangleShape.getNodes());
	}

	public TriangleShape getTriangleShape() {
		return triangleShape;
	}

	public Line getLine() {
		return line;
	}

	@Override
	public LinkedList<Node> getNodes() {
		return linkedList;
	}

	@Override
	public void setX(double value) {
		self.setX(value);
	}

	@Override
	public void setY(double value) {
		self.setY(value);
	}

	@Override
	public void setWidth(double value) {
		self.setWidth(value);
	}

	@Override
	public void setHeight(double value) {
		self.setHeight(value);
	}

	@Override
	public RectangleEntity getRectangle() {
		return new RectangleEntity(new PointEntity(self.getX(), self.getY()), self.getWidth(), self.getHeight());
	}
}
