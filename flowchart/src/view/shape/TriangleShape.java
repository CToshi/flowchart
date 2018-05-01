package view.shape;

import java.util.LinkedList;

import entities.PointEntity;
import entities.Vector;
import javafx.scene.Node;
import javafx.scene.shape.Polygon;
import utility.Util;
import view.inter.Drawable;

public class TriangleShape implements Drawable{

	static final double EDGE_SIZE = 10;
	private Polygon polygon;
	private PointEntity directPoint;
	private PointEntity vertex;

	public TriangleShape(PointEntity directPoint,PointEntity vertex){
		this.directPoint = directPoint;
		this.vertex = vertex;
		polygon = new Polygon();
		polygon.getPoints().addAll(this.getTriangle());
	}

	private Double[] getTriangle() {
		// Point A = new Point(2, 30), B = new Point(30, 30); // Line's two
		// points
		double t = EDGE_SIZE; // Triangle's length of sides
		Vector alpha = new Vector(vertex, directPoint);
		double a = alpha.getMod() * alpha.getMod();
		double b = -Math.sqrt(3) * t * alpha.getMod() * alpha.getX();
		double c = 3.0 / 4.0 * t * t * a - t * t * alpha.getY() * alpha.getY();
		double X1 = (-b + Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a);
		double X2 = (-b - Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a);
		double x1 = X1 + vertex.getX(), x2 = X2 + vertex.getX();
		double y1 = vertex.getY() + 0.5 * t;
		double y2 = vertex.getY() - 0.5 * t;
		if (alpha.getY() != 0) {
			y1 = (Math.sqrt(3) / 2.0 * t * alpha.getMod() - alpha.getX() * X1) / alpha.getY() + vertex.getY();
			y2 = (Math.sqrt(3) / 2.0 * t * alpha.getMod() - alpha.getX() * X2) / alpha.getY() + vertex.getY();
		}
		return new Double[] { vertex.getX(), vertex.getY(), x1, y1, x2, y2 };
	}

	private void update(){
		this.polygon.getPoints().clear();
		this.polygon.getPoints().addAll(getTriangle());
	}

	public void setDirectPoint(PointEntity directPoint) {
		this.directPoint = directPoint;
		update();
	}

	public void setVertex(PointEntity vertex) {
		this.vertex = vertex;
		update();
	}

	@Override
	public LinkedList<Node> getNodes() {
		return Util.getList(polygon);
	}

}
