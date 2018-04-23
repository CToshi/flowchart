package view.move;

import entities.PointEntity;
import entities.Vector;

public class ArrowMoveController {

	static final double EDGE_SIZE = 10;

	private Double[] getTriangle(PointEntity A, PointEntity B) {
		// Point A = new Point(2, 30), B = new Point(30, 30); // Line's two
		// points
		double t = EDGE_SIZE; // Triangle's length of sides
		Vector alpha = new Vector(B, A);
		double a = alpha.getMod() * alpha.getMod();
		double b = -Math.sqrt(3) * t * alpha.getMod() * alpha.getX();
		double c = 3.0 / 4.0 * t * t * a - t * t * alpha.getY() * alpha.getY();
		double X1 = (-b + Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a);
		double X2 = (-b - Math.sqrt(b * b - 4.0 * a * c)) / (2.0 * a);
		double x1 = X1 + B.getX(), x2 = X2 + B.getX();
		double y1 = B.getY() + 0.5 * t;
		double y2 = B.getY() - 0.5 * t;
		if (alpha.getY() != 0) {
			y1 = (Math.sqrt(3) / 2.0 * t * alpha.getMod() - alpha.getX() * X1) / alpha.getY() + B.getY();
			y2 = (Math.sqrt(3) / 2.0 * t * alpha.getMod() - alpha.getX() * X2) / alpha.getY() + B.getY();
		}
		return new Double[] { B.getX(), B.getY(), x1, y1, x2, y2 };
	}

}
