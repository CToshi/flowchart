package view.shape;

import java.util.LinkedList;

import entities.PointEntity;
import entities.RectangleEntity;
import javafx.scene.Node;
import javafx.scene.shape.Polyline;

public class PolygonalArrowShape extends ShapeItem {

	private RectangleEntity rectangle;
	private TriangleShape triangleShape;
	private LinkedList<Node> linkedList;
	private Polyline polyline;
	private PointEntity[] points;
	private boolean init = true;

	private static final double[] OFFSET = { 8f, 8f, 8f, 2f, 4f / 3f, 2f, 4f / 3f, 4f / 3f };

	public PolygonalArrowShape(PointEntity startPoint, PointEntity endPoint) {
		this.rectangle = new RectangleEntity(0, 0, 0, 0);
		this.polyline = new Polyline();
		this.points = new PointEntity[] { startPoint, new PointEntity(startPoint.getX(),
				startPoint.getY() + (endPoint.getY() - startPoint.getY()) / 2),
				new PointEntity(endPoint.getX(),startPoint.getY() + (endPoint.getY() - startPoint.getY()) / 2),
				endPoint};
		this.polyline.getPoints().addAll(getPoints());
		this.triangleShape = new TriangleShape(points[2], points[3]);
		this.linkedList = new LinkedList<Node>();
		this.linkedList.add(polyline);
		this.linkedList.addAll(triangleShape.getNodes());
	}

	public Polyline getPolyline() {
		return polyline;
	}

	public void setStartPoint(PointEntity pointEntity){
		points[0] = pointEntity;
		if(init){
			points[1].setX(points[0].getX());
			points[1].setY((points[3].getY()+points[0].getY())/2);
			points[2].setY((points[3].getY()+points[0].getY())/2);
		}else{
			points[1].setX((points[3].getX()+points[0].getX())/2);
			points[1].setY(pointEntity.getY());
			points[2].setX((points[3].getX()+points[0].getX())/2);
		}
		update();
		adjust();
	}

	public void setCenterPoint(PointEntity pointEntity) {
		if(init){
			points[1].setY(pointEntity.getY());
			points[2].setY(pointEntity.getY());
		}else {
			points[1].setX(pointEntity.getX());
			points[2].setX(pointEntity.getX());
		}
		update();
	}

	public void setEndPoint(PointEntity pointEntity) {
		points[3] = pointEntity;
		if(init){
			points[1].setY((points[3].getY()+points[0].getY())/2);
			points[2].setX(points[3].getX());
			points[2].setY((points[3].getY()+points[0].getY())/2);
		}else {
			points[1].setX((points[3].getX()+points[0].getX())/2);
			points[2].setX((points[3].getX()+points[0].getX())/2);
			points[2].setY(pointEntity.getY());
		}
		update();
		adjust();
	}

	public PointEntity getCenterPoint() {
		return new PointEntity((points[2].getX()+points[1].getX())/2,(points[1].getY()+points[2].getY())/2);
	}

	public PointEntity getStartPoint(){
		return points[0];
	}

	public PointEntity getEndPoint(){
		return points[3];
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
		return new RectangleEntity(points[0], points[3].getX()-points[0].getX(), points[3].getY()-points[0].getY());
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		for (int i = 0,j=0; i < points.length&&j<OFFSET.length; i++,j+=2) {
			points[i].setX(rectangle.getX() + rectangle.getWidth() / OFFSET[j]);
			points[i].setY(rectangle.getY() + rectangle.getHeight() / OFFSET[j + 1]);
		}
		update();
		adjust();
	}

	public void update(){
		polyline.getPoints().clear();
		polyline.getPoints().addAll(getPoints());
		triangleShape.setDirectPoint(points[2]);
		triangleShape.setVertex(points[3]);
	}

	public Double[] getPoints(){
		Double[] points = new Double[this.points.length*2];
		for(int i=0,j=0;i<this.points.length&&j<points.length;i++,j+=2){
			points[j] = this.points[i].getX();
			points[j+1] = this.points[i].getY();
		}
		return points;
	}

	private void adjust(){
		if(Math.abs(points[3].getX()-points[0].getX())>Math.abs(points[3].getY()-points[0].getY())){
			init = false;
			points[1].setX((points[3].getX()+points[0].getX())/2);
			points[1].setY(points[0].getY());
			points[2].setX((points[3].getX()+points[0].getX())/2);
			points[2].setY(points[3].getY());
		}else{
			init = true;
			points[1].setX(points[0].getX());
			points[1].setY((points[3].getY()+points[0].getY())/2);
			points[2].setX(points[3].getX());
			points[2].setY((points[3].getY()+points[0].getY())/2);
		}
	}

	public void move(double xDelta, double yDelta) {
		for(int i=0;i<points.length;i++){
			points[i].setX(points[i].getX()+xDelta);
			points[i].setY(points[i].getY()+yDelta);
		}
		update();
		adjust();
	}

}
