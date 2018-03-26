package ui;

import application.Main;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import manager.DrawManager;
import view.DrawElement;
import view.Painter;

public class DrawPane extends Pane {
	private DrawManager manager;

	public DrawPane(DoubleExpression width, DoubleExpression height) {
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);
		this.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		this.setMinSize(100, 100);
//		{
//			Rectangle rectangle = new Rectangle(-10,-10,50,50);
//			rectangle.setFill(Color.GREEN);
//			this.getChildren().add(rectangle);
//		}
		Painter.setPane(this);
		manager = new DrawManager();
		this.setOnMouseClicked(e->{
			System.out.println("DrawPane:"+this.getWidth()+" "+this.getHeight());
			System.out.println(e.getX()+" "+e.getY());
		});
		this.setOnMouseDragged(mouse -> {
			if(isOutBound(mouse.getX(), mouse.getY())){
				manager.setOutBound(true);
			} else {
				manager.setOutBound(false);
			}
		});

//		{
//			this.setOnMouseMoved(e->{
//				Main.test(e.getX(), e.getY());
//			});
//		}
	}

	private boolean isOutBound(double x, double y) {
		return !(0 <= x && x <= this.getWidth() && 0 <= y && y <= this.getHeight());
	}

	public void add(DrawElement element){
		manager.add(element);
	}
}
