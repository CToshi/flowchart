package ui;

import javafx.beans.binding.DoubleExpression;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import view.Painter;
import view.move.MoveFrame;

public class ToolPane extends VBox {
	private Painter painter = Painter.getInstance();

	public ToolPane(DoubleExpression width, DoubleExpression height) {
		this.prefHeightProperty().bind(height);
		this.prefWidthProperty().bind(width);
		// this.minHeightProperty().bind(width.divide(5));
		// this.setMinHeight(300);
		// this.setMinSize(300, 300);
		this.setBackground(new Background(new BackgroundFill(Color.BLUE, null, null)));
		Button button = new Button("rectangle");
		this.getChildren().add(button);
		button.setOnAction(event -> {
			painter.add(new MoveFrame());
		});
	}
}

