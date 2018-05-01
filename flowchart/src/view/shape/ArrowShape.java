package view.shape;

import java.util.LinkedList;

import entities.PointEntity;
import javafx.scene.Node;
import javafx.scene.shape.Line;
import view.inter.Drawable;

public class ArrowShape implements Drawable{

	private TriangleShape triangleShape;
	private Line line;
	private LinkedList<Node> linkedList;

	public ArrowShape(PointEntity startPoint,PointEntity endPoint){
		line = new Line(startPoint.getX(),startPoint.getY(),endPoint.getX(),endPoint.getY());
		triangleShape = new TriangleShape(startPoint, endPoint);
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

}
