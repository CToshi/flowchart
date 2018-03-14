package ui;

import application.Main;
import entities.PointEntity;
import entities.RotateRectangleEntity;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import view.DrawElement;
import view.Drawable;
import view.Painter;

public class ToolPane extends VBox {
	private Painter painter = Painter.getInstance();
	private int cnt;

	public ToolPane(DoubleExpression width, DoubleExpression height) {
		this.prefHeightProperty().bind(height);
		this.prefWidthProperty().bind(width);
		// this.minHeightProperty().bind(width.divide(5));
		// this.setMinHeight(300);
		// this.setMinSize(300, 300);
		this.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
		Button button = new Button("rectangle");
		this.getChildren().add(button);
		button.setOnAction(event->{
			painter.add(new MyRectangle());
		});
//		button.setOnAction(e -> {
//			Rectangle rectangle = new Rectangle(100, 100);
//			System.out.println(e.getTarget());
//			rectangle.setOnMouseDragged(edrag -> {
//				System.out.println(edrag.getX() + " " + edrag.getY());
//				System.out.println(edrag.getTarget());
//				System.out.println(cnt++);
//			});
//			rectangle.setOnMousePressed(e2 -> {
//				System.out.println(e2.getX() + " click " + e2.getY());
//			});
//			painter.add(rectangle);
//
//		});
	}
}

class MyRectangle extends DrawElement {

	private PointEntity lastPosition;
	private Rectangle rectangle;
	private boolean isDragged;

	public MyRectangle() {
		rectangle = new Rectangle(50, 50, 100, 100);
		rectangle.setOnMousePressed(e -> {
			lastPosition = new PointEntity(e.getX(), e.getY());
			isDragged = true;
		});
		rectangle.setOnMouseDragged(e -> {
			if(!isDragged) return;
			double xDelta = e.getX() - lastPosition.getX();
			double yDelta = e.getY() - lastPosition.getY();
//			Main.test(lastPosition.getX(), lastPosition.getY(), e.getX(), e.getY());
//			Main.test(e.getX(), e.getY());
			rectangle.setX(rectangle.getX() + xDelta);
			rectangle.setY(rectangle.getY() + yDelta);
			lastPosition.setXY(e.getX(), e.getY());
		});
	}

	@Override
	public Shape[] getShapes() {
		Shape[] shapes = {rectangle};
		return shapes;
	}


	@Override
	public void setStopDragged() {
		isDragged = false;
	}
}
