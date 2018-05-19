package view.shape;

import java.util.LinkedList;

import entities.DrawableState.Type;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import view.move.LinkedPoint;

public class ArrowShape extends ShapeItem{

	private RectangleEntity rectangle;
	private TriangleShape triangleShape;
	private Line line;
	private LinkedList<Node> linkedList;
	private Polygon surround;
	private LinkedList<LinkedPoint> linkedPoints;

	public ArrowShape(PointEntity startPoint,double length){
		this(startPoint, new PointEntity(startPoint.getX()+length,startPoint.getY()));
	}

	public ArrowShape(PointEntity startPoint,PointEntity endPoint){
		this.rectangle = new RectangleEntity(0,0,0,0);
		line = new Line(startPoint.getX(),startPoint.getY(),endPoint.getX(),endPoint.getY());
		triangleShape = new TriangleShape(startPoint, endPoint);
		this.linkedList = new LinkedList<Node>();
		this.surround = new Polygon();
		surround.setFill(Color.WHITE);
//		surround.setStroke(Color.BLACK);
		surround.getPoints().addAll(getSurround());
		linkedList.add(surround);
		linkedList.add(line);
		linkedList.addAll(triangleShape.getNodes());
		LinkedPoint linkedPoint = new LinkedPoint((startPoint.getX()+endPoint.getX())/2, (startPoint.getY()+endPoint.getY()/2),1,1);
		this.linkedPoints = new LinkedList<LinkedPoint>();
		linkedPoints.add(linkedPoint);
	}

	public Line getLine() {
		return line;
	}

	public Polygon getPolygon(){
		return surround;
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
		surround.getPoints().clear();
		surround.getPoints().addAll(getSurround());
	}
	@Override
	public Type getType() {
		return Type.ARROW;
	}

	public Double[] getSurround(){
		double width = 5;
		double distance =getStartPoint().getDistanceFrom(getEndPoint());
		double k = width/distance;
		double xDelta = getLine().getEndX()-getLine().getStartX();
		double yDelta = getLine().getEndY()-getLine().getStartY();
		double sign;
		if(xDelta==0||yDelta==0){
			sign = 1;
		}else {
			sign = xDelta/Math.abs(xDelta)*yDelta/Math.abs(yDelta);
		}
		Double[] points = new Double[]{
				getStartPoint().getX()+Math.abs(yDelta)*k*sign,getStartPoint().getY()-Math.abs(xDelta)*k,
				getStartPoint().getX()-Math.abs(yDelta)*k*sign,getStartPoint().getY()+Math.abs(xDelta)*k,
				getStartPoint().getX()-Math.abs(yDelta)*k*sign+xDelta,getStartPoint().getY()+Math.abs(xDelta)*k+yDelta,
				getStartPoint().getX()+Math.abs(yDelta)*k*sign+xDelta,getStartPoint().getY()-Math.abs(xDelta)*k+yDelta,
			};
		return points;
	}
}
