package ui;

import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ToolPane extends VBox{
	public ToolPane(DoubleExpression width, DoubleExpression height) {
		this.prefHeightProperty().bind(height);
		this.prefWidthProperty().bind(width);
//		this.minHeightProperty().bind(width.divide(5));
//		this.setMinHeight(300);
//		this.setMinSize(300, 300);
		this.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
		
	}
}
