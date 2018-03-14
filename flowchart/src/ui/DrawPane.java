package ui;

import javafx.beans.binding.DoubleExpression;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DrawPane extends Pane{
	public DrawPane(DoubleExpression width, DoubleExpression height) {
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);
		this.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
		this.setMinSize(100, 100);
	}
}
