package ui;

import javafx.beans.binding.DoubleExpression;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import manager.DrawManager;
import view.Painter;

public class DrawPane extends Pane{
	private DrawManager manager;
	public DrawPane(DoubleExpression width, DoubleExpression height) {
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);
		this.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
		this.setMinSize(100, 100);
		Painter.setPane(this);
		manager = new DrawManager();
		this.setOnMouseMoved(mouse->{
//			System.out.println(233);
//			manager.setStopDragged();
		});
	}
}
