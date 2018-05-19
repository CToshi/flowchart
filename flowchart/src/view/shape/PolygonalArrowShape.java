package view.shape;

import java.util.LinkedList;

import entities.DrawableState.Type;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import view.move.LinkedPoint;

public class PolygonalArrowShape extends ShapeItem {

	private RectangleEntity rectangle;
	private TriangleShape triangleShape;
	private LinkedList<Node> linkedList;
	private Polyline polyline;
	private PointEntity[] points;
	private boolean isHorizontal;
	private Polygon surround;

	private static final double[] OFFSET_HORIZONTAL = { 8f, 8f, 8f, 2f, 4f / 3f, 2f, 4f / 3f, 4f / 3f };
	private static final double[] OFFSET_ERECT = { 8f, 8f, 2f, 8f, 2f, 4f / 3f, 4f / 3f, 4f / 3f };

	private LinkedList<LinkedPoint> linkedPoints;

	public PolygonalArrowShape(PointEntity startPoint, PointEntity endPoint, boolean isHorizontal) {
		this.rectangle = new RectangleEntity(0, 0, 0, 0);
		this.polyline = new Polyline();
		this.isHorizontal = isHorizontal;
		if (isHorizontal) {
			this.points = new PointEntity[] { startPoint,
					new PointEntity(startPoint.getX(), (startPoint.getY() + endPoint.getY()) / 2),
					new PointEntity(endPoint.getX(), (startPoint.getY() + endPoint.getY()) / 2), endPoint };
		} else {
			this.points = new PointEntity[] { startPoint,
					new PointEntity((startPoint.getX() + endPoint.getX()) / 2, startPoint.getY()),
					new PointEntity((startPoint.getX() + endPoint.getX()) / 2, endPoint.getY()), endPoint };
		}
		this.polyline.getPoints().addAll(getPoints());
		this.surround = new Polygon();
		this.surround.setFill(Color.WHITE);
		this.surround.getPoints().addAll(getSurround());
		this.triangleShape = new TriangleShape(points[2], points[3]);
		this.linkedList = new LinkedList<Node>();
		this.linkedList.add(surround);
		this.linkedList.add(polyline);
		this.linkedList.addAll(triangleShape.getNodes());
		LinkedPoint linkedPoint = new LinkedPoint(getCenterPoint(),1,1);
		this.linkedPoints = new LinkedList<LinkedPoint>();
		linkedPoints.add(linkedPoint);
	}

	public Polygon getPolygon(){
		return surround;
	}

	public Polyline getPolyline() {
		return polyline;
	}

	public void setStartPoint(PointEntity pointEntity) {
		points[0] = pointEntity;
		if (isHorizontal) {
			points[1].setX(pointEntity.getX());
		} else {
			points[1].setY(pointEntity.getY());
		}
		update();
	}

	public void setCenterPoint(PointEntity pointEntity) {
		if (isHorizontal) {
			points[1].setY(pointEntity.getY());
			points[2].setY(pointEntity.getY());
		} else {
			points[1].setX(pointEntity.getX());
			points[2].setX(pointEntity.getX());
		}
		update();
	}

	public void setEndPoint(PointEntity pointEntity) {
		points[3] = pointEntity;
		if (isHorizontal) {
			points[2].setX(pointEntity.getX());
		} else {
			points[2].setY(pointEntity.getY());
		}
		update();
	}

	public PointEntity getCenterPoint() {
		return new PointEntity((points[2].getX() + points[1].getX()) / 2, (points[1].getY() + points[2].getY()) / 2);
	}

	public PointEntity getStartPoint() {
		return points[0].clone();
	}

	public PointEntity getEndPoint() {
		return points[3].clone();
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
		return new RectangleEntity(points[0], points[3].getX() - points[0].getX(), points[3].getY() - points[0].getY());
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		if (isHorizontal) {
			for (int i = 0, j = 0; i < points.length && j < OFFSET_HORIZONTAL.length; i++, j += 2) {
				points[i].setX(rectangle.getX() + rectangle.getWidth() / OFFSET_HORIZONTAL[j]);
				points[i].setY(rectangle.getY() + rectangle.getHeight() / OFFSET_HORIZONTAL[j + 1]);
			}
		} else {
			for (int i = 0, j = 0; i < points.length && j < OFFSET_ERECT.length; i++, j += 2) {
				points[i].setX(rectangle.getX() + rectangle.getWidth() / OFFSET_ERECT[j]);
				points[i].setY(rectangle.getY() + rectangle.getHeight() / OFFSET_ERECT[j + 1]);
			}
		}
		update();
	}

	public void update() {
		polyline.getPoints().clear();
		polyline.getPoints().addAll(getPoints());
		triangleShape.setDirectPoint(points[2]);
		triangleShape.setVertex(points[3]);
		surround.getPoints().clear();
		surround.getPoints().addAll(getSurround());
	}

	public Double[] getPoints() {
		Double[] points = new Double[this.points.length * 2];
		for (int i = 0, j = 0; i < this.points.length && j < points.length; i++, j += 2) {
			points[j] = this.points[i].getX();
			points[j + 1] = this.points[i].getY();
		}
		return points;
	}

	public void move(double xDelta, double yDelta) {
		for (int i = 0; i < points.length; i++) {
			points[i].setX(points[i].getX() + xDelta);
			points[i].setY(points[i].getY() + yDelta);
		}
		update();
	}

	@Override
	public Type getType() {
		if (isHorizontal) {
			return Type.ARROW_HORIZONTAL;
		} else {
			return Type.ARROW_ERECT;
		}
	}

	public boolean isHorizontal() {
		return isHorizontal;
	}

	public Double[] getSurround() {
		double width = 5;
		if (isHorizontal) {
			double ySign2;
			double xSign2;
			double ySign3;
			ySign2 = (points[1].getY() - points[0].getY()) / Math.abs(points[1].getY() - points[0].getY());
			xSign2 = (points[2].getX() - points[1].getX()) / Math.abs(points[2].getX() - points[1].getX());
			ySign3 = (points[3].getY() - points[2].getY()) / Math.abs(points[3].getY() - points[2].getY());
			if(Math.abs(points[1].getY() - points[0].getY())<0.001)ySign2 = 1;
			if(Math.abs(points[2].getX() - points[1].getX())<0.001)xSign2 = 1;
			if(Math.abs(points[3].getY() - points[2].getY())<0.001)ySign3 = 1;
			return new Double[] { points[0].getX() - width, points[0].getY(), points[0].getX() + width,
					points[0].getY(), points[0].getX() + width, points[1].getY() - width * ySign2 * xSign2,
					points[2].getX() + width * ySign2 * ySign3, points[1].getY() - width * ySign2 * xSign2,
					points[2].getX() + width * ySign2 * ySign3, points[3].getY(),
					points[2].getX() - width * ySign2 * ySign3, points[3].getY(),
					points[3].getX() - width * ySign2 * ySign3, points[1].getY() + width * ySign2 * xSign2,
					points[0].getX() - width, points[1].getY() + width * ySign2 * xSign2 };
		} else {
			double xSign2;
			double ySign2;
			double xSign3;
			xSign2 = (points[1].getX() - points[0].getX()) / Math.abs(points[1].getX() - points[0].getX());
			ySign2 = (points[2].getY() - points[1].getY()) / Math.abs(points[2].getY() - points[1].getY());
			xSign3 = (points[3].getX() - points[2].getX()) / Math.abs(points[3].getX() - points[2].getX());
			if(Math.abs(points[1].getX() - points[0].getX())<0.001)xSign2 = 1;
			if(Math.abs(points[2].getY() - points[1].getY())<0.001)ySign2 = 1;
			if(Math.abs(points[3].getX() - points[2].getX())<0.001)xSign3 = 1;
			return new Double[] {
					points[0].getX(), points[0].getY() - width,
					points[0].getX(),points[0].getY() + width,
					points[1].getX() - width * xSign2 * ySign2, points[0].getY() + width,
					points[1].getX() - width * xSign2 * ySign2, points[2].getY() + width * xSign2 * xSign3,
					points[3].getX(), points[2].getY() + width * xSign2 * xSign3,
					points[3].getX(),points[2].getY() - width * xSign2 * xSign3,
					points[1].getX() + width * ySign2 * xSign2,points[2].getY() - width * xSign2 * xSign3,
					points[1].getX() + width * ySign2 * xSign2,points[0].getY() - width
			};
		}
	}

}
