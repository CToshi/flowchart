package view.move;

import java.util.LinkedList;

import entities.PointEntity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LinkedPoint {

	private double x;
	private double y;
	private LinkedList<ArrowMovePoint> arrowMovePoints;
	private Circle circle;
	private static final double DEFAULT_RADIUS = 3;
	private int maxIn;
	private int maxOut;
	private int currentIn;
	private int currentOut;

	public LinkedPoint(PointEntity pointEntity,int maxIn,int maxOut){
		this(pointEntity.getX(), pointEntity.getY(),maxIn,maxOut);
	}

	public LinkedPoint(double x,double y, int maxIn, int maxOut){
		this.maxIn = maxIn;
		this.maxOut = maxOut;
		this.x = x;
		this.y = y;
		this.arrowMovePoints = new LinkedList<ArrowMovePoint>();
		circle = new Circle(x+DEFAULT_RADIUS, y+DEFAULT_RADIUS, DEFAULT_RADIUS);
		circle.setFill(Color.BLACK);
		setHidden(true);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		circle.setCenterX(x);
		for(ArrowMovePoint arrowMovePoint:arrowMovePoints){
			arrowMovePoint.setX(x);
		}
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		circle.setCenterY(y);
		for(ArrowMovePoint arrowMovePoint:arrowMovePoints){
			arrowMovePoint.setY(y);
		}
	}

	public Circle getNode(){
		return circle;
	}

	public void setHidden(boolean isHidden){
		if(isHidden){
			circle.setRadius(0);
		}else {
			circle.setRadius(DEFAULT_RADIUS);
		}
	}

	public void addConnectionPoint(ArrowMovePoint arrowMovePoint){
		if(arrowMovePoint.isStart()&&currentOut<maxOut){
			arrowMovePoints.add(arrowMovePoint);
			currentOut++;
		}else if(!arrowMovePoint.isStart()&&currentIn<maxIn){
			arrowMovePoints.add(arrowMovePoint);
			currentIn++;
		}
	}

	public void removeConnectionPoint(ArrowMovePoint arrowMovePoint){
		if(arrowMovePoints.contains(arrowMovePoint)){
			if(arrowMovePoint.isStart())currentOut--;
			else currentIn--;
			arrowMovePoints.remove(arrowMovePoint);
		}
	}

	public double getDistanceFrom(LinkedPoint other) {
		return Math.sqrt((x - other.getX()) * (x - other.getX()) + (y - other.getY()) * (y - other.getY()));
	}

	@Override
	public String toString() {
		return "LinkedPoint [x=" + x + ", y=" + y + ", arrowMovePoints=" + arrowMovePoints + ", circle=" + circle
				+ ", maxIn=" + maxIn + ", maxOut=" + maxOut + ", currentIn=" + currentIn + ", currentOut=" + currentOut
				+ "]";
	}
	
}
