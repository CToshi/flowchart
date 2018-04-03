package ui;

import factory.RectangleShapeFactory;
import factory.RectangleShapeFactory.TYPE;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.move.MoveFrame;

/**
 * 存放图形选择区的Pane
 *
 * @author Toshi
 *
 */
public class ToolPane extends Pane {

	private static final double HEIGHT_PROPORTION = 10;

	public ToolPane(RootPane parent, DoubleExpression width, DoubleExpression height) {
		this.prefHeightProperty().bind(height);
		this.prefWidthProperty().bind(width);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1));
		Border border = new Border(borderStroke);
		this.setBorder(border);


		Rectangle rectangle = new Rectangle();
		rectangle.setX(20);
		rectangle.setY(20);
		rectangle.setFill(Color.YELLOW);
		rectangle.widthProperty().bind(width.multiply(0.8));
		rectangle.heightProperty().bind(height.divide(HEIGHT_PROPORTION));
		Label label = new Label();
		label.setLayoutX(rectangle.getX());
		label.setLayoutY(rectangle.getY());
		label.minWidthProperty().bind(rectangle.widthProperty());
		label.minHeightProperty().bind(rectangle.heightProperty());
		this.getChildren().addAll(rectangle, label);
		Tooltip tooltip = new Tooltip("处理框,表示算法的一个步骤,一个处理环节");
		label.setTooltip(tooltip);
		label.setOnMouseClicked(e -> {
			parent.addToDrawPane(new MoveFrame(parent.getDrawPane(),
					RectangleShapeFactory.create(parent.getDrawPane().getCenter())));
		});

		// rectangle.setFill(Color.TRANSPARENT);
		// Button rectangleButton = new Button("",rectangle);
		// Tooltip tooltip = new Tooltip("处理框,表示算法的一个步骤,一个处理环节");
		// rectangleButton.setTooltip(tooltip);
		// this.getChildren().add(rectangleButton);
		// rectangleButton.setOnAction(event -> {
		// parent.addToDrawPane(new MoveFrame(parent.getDrawPane(),
		// RectangleShapeFactory.create(parent.getDrawPane().getCenter())));
		// });

		// Button RRButton = new Button("RounderRectangleShape");
		// this.getChildren().add(RRButton);
		// RRButton.setOnAction(event -> {
		// parent.addToDrawPane(new MoveFrame(parent.getDrawPane(),
		// RectangleShapeFactory.create(parent.getDrawPane().getCenter(),
		// TYPE.ROUDED)));
		// });

	}
}
