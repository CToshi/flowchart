package ui;

import factory.RectangleShapeFactory;
import factory.RectangleShapeFactory.TYPE;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import view.move.MoveFrame;

/**
 * 存放图形选择区的Pane
 *
 * @author Toshi
 *
 */
public class ToolPane extends VBox {

	public ToolPane(RootPane parent, DoubleExpression width, DoubleExpression height) {
		this.prefHeightProperty().bind(height);
		this.prefWidthProperty().bind(width);
		this.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
		Button rectangleButton = new Button("Rectangle");
		this.getChildren().add(rectangleButton);
		rectangleButton.setOnAction(event -> {
			parent.addToDrawPane(new MoveFrame(parent.getDrawPane(),
					RectangleShapeFactory.create(parent.getDrawPane().getCenter())));
		});
		Button RRButton = new Button("RoundedRectangle");
		this.getChildren().add(RRButton);
		RRButton.setOnAction(event -> {
			parent.addToDrawPane(new MoveFrame(parent.getDrawPane(),
					RectangleShapeFactory.create(parent.getDrawPane().getCenter(), TYPE.ROUDED)));
		});

	}
}
