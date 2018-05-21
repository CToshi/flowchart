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
	private boolean isHidden;

	public TriangleShape(PointEntity directPoint,PointEntity vertex){
		this.directPoint = directPoint;
		this.vertex = vertex;
		polygon = new Polygon();
		polygon.getPoints().addAll(this.getTriangle());
		this.isHidden = false;
	}

	private Double[] getTriangle() {
		return getTriangle(directPoint,vertex);
	}

	private void update(){
		this.polygon.getPoints().clear();
		if (!isHidden) {
			this.polygon.getPoints().addAll(getTriangle());
		}
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

	private Double[] getTriangle(PointEntity A, PointEntity B) {
		double t = 10, x1, x2, y1, y2; // Triangle's length of sides
		Vector alpha = new Vector(B, A);
		if (alpha.getY() == 0) {
			double k = 1;
			if (alpha.getX() < 0)
				k = -1;
			x1 = x2 = B.getX() + k * Math.sqrt(3.0) * 0.5 * t;
			y1 = B.getY() + 0.5 * t;
			y2 = B.getY() - 0.5 * t;
		} else {
			double k = alpha.getMod() * t * Math.sqrt(3.0) * 0.5;
			double a = alpha.getMod() * alpha.getMod();
			double b = -2 * alpha.getX() * k;
			double c = k * k - alpha.getY() * alpha.getY() * t * t;
			double n1 = (-b + Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a);
			double n2 = (-b - Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a);
			x1 = n1 + B.getX();
			x2 = n2 + B.getX();
			y1 = (k - alpha.getX() * (x1 - B.getX())) / alpha.getY() + B.getY();
			y2 = (k - alpha.getX() * (x2 - B.getX())) / alpha.getY() + B.getY();
		}
		return new Double[] { B.getX(), B.getY(), x1, y1, x2, y2 };
	}

	public void setHidden(boolean isHidden) {
		this.isHidden = isHidden;
		update();
	}
}
