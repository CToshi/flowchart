package view.move;

import java.util.LinkedList;

import entities.RectangleEntity;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utility.Util;
import view.inter.Changeable;
import view.inter.Draggable;
import view.inter.Drawable;

public class MovePoint implements Changeable, Drawable {

	private static final double size = 5;
	private MoveFrame parent;
	private MovePoint other;
	private Circle circle;

	public MovePoint(MoveFrame target, boolean xChangable, boolean yChangable, Cursor cursor) {
		this.parent = target;
		circle = new Circle();
		circle.setRadius(size);
		circle.setFill(Color.WHITE);
		circle.setStroke(Color.BLACK);
		new Draggable() {

			@Override
			protected void whenPressed(MouseEvent mouse) {
				parent.setHidden(true);
			}

			@Override
			protected void whenReleased(MouseEvent mouse) {
				parent.fixPosition();
				parent.setHidden(false);
				parent.informChange();
			}

			@Override
			protected void whenMoved(MouseEvent mouse) {
				circle.setCursor(cursor);
			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				if (xChangable) {
					circle.setCenterX(circle.getCenterX() + xDelta);
					parent.setX(Math.min(circle.getCenterX(), other.getCenterX()));
					parent.setWidth(Math.abs(circle.getCenterX() - other.getCenterX()));
				}
				if (yChangable) {
					circle.setCenterY(circle.getCenterY() + yDelta);
					parent.setY(Math.min(circle.getCenterY(), other.getCenterY()));
					parent.setHeight(Math.abs(circle.getCenterY() - other.getCenterY()));
				}
				parent.whenChanging();
			}

			@Override
			protected Node getNode() {
				return circle;
			}
		};
	}

	/**
	 * 设置对立点
	 *
	 * @param other
	 */
	public void setOtherPoint(MovePoint other) {
		this.other = other;
	}

	/**
	 * 处理鼠标移动的函数。
	 * 具体做法是，如果x可以改变(某些情况如该点是第一行的中间的点，此时拖动该点不能改变x)，则对比自己的坐标和对立点(other)的坐标, 取较小值。
	 * y同x
	 */

	public void setCenterXY(double x, double y) {
		circle.setCenterX(x);
		circle.setCenterY(y);
	}

	@Override
	public LinkedList<Node> getNodes() {
		return Util.getList(circle);
	}

	@Override
	public RectangleEntity getRectangle() {
		return new RectangleEntity(circle.getCenterX() - circle.getRadius(), circle.getCenterY() - circle.getRadius(),
				circle.getRadius()*2, circle.getRadius()*2);
	}

	@Override
	public void setRectangle(RectangleEntity rectangle) {
		circle.setCenterX(rectangle.getX() + rectangle.getWidth() / 2f);
		circle.setCenterY(rectangle.getY() + rectangle.getHeight() / 2f);
		circle.setRadius(Math.min(rectangle.getWidth(), rectangle.getHeight())/2f);
	}


	public double getCenterX() {
		return circle.getCenterX();
	}

	public double getCenterY() {
		return circle.getCenterY();
	}
	
	public void setHidden(boolean isHidden){
		if(isHidden){
			circle.setRadius(-1);
		}else{
			circle.setRadius(size);
		}
	}

}
