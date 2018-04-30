package view.shape;

import java.util.LinkedList;

import entities.PointEntity;
import javafx.scene.Node;
import javafx.scene.shape.Polyline;
import view.inter.Drawable;

public class PolygonalArrowShape implements Drawable{

	private TriangleShape triangleShape;
	private LinkedList<Node> linkedList;
	private Polyline polyline;

	public PolygonalArrowShape(PointEntity startPoint,PointEntity endPoint){
		this.polyline = new Polyline();
		this.polyline.getPoints().addAll(new Double[]{
				startPoint.getX(),startPoint.getY(),
				startPoint.getX(),startPoint.getY()+(endPoint.getY()-startPoint.getY())/2,
				endPoint.getX(),startPoint.getY()+(endPoint.getY()-startPoint.getY())/2,
				endPoint.getX(),endPoint.getY()
		});
		this.linkedList = new LinkedList<Node>();
		this.linkedList.add(polyline);
		this.linkedList.addAll(triangleShape.getNodes());
	}

	public TriangleShape getTriangleShape() {
		return triangleShape;
	}

	public Polyline getPolyline() {
		return polyline;
	}

	@Override
	public LinkedList<Node> getNodes() {
		return linkedList;
	}

}
