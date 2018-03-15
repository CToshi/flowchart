package ui;

import application.Main;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//public class RootPane extends BorderPane {
//	private ToolPane toolPane;
//	private DrawPane drawPane;
//	private MenuBar menuBar;
//
//	public RootPane(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
//		this.prefHeightProperty().bind(width);
//
//		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
//
//		toolPane = new ToolPane(width.multiply(0.2), height);
//		menuBar = new MenuBar();
//		drawPane = new DrawPane(width.multiply(0.8), height);
//
//
//		this.setTop(menuBar);
//		this.setLeft(toolPane);
//		this.setRight(drawPane);
//
//		{
//			Rectangle rectangle = new Rectangle(-50,-50,10,10);
//			this.getChildren().add(rectangle);
////			Main.test(drawPane.getChildren().contains(rectangle));
////			Main.test(toolPane.getChildren().contains(rectangle));
//		}
//
//	}
//}
public class RootPane extends Pane {
	private ToolPane toolPane;
	private DrawPane drawPane;
	private MenuBar menuBar;
	private Pane bottomPane;

	public RootPane(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
		this.prefHeightProperty().bind(width);

		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		bottomPane = new Pane();
		toolPane = new ToolPane(width.multiply(0.2), height);
		menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(width);
		this.getChildren().addAll(bottomPane, menuBar);
		drawPane = new DrawPane(width.multiply(0.8), height);
		bottomPane.layoutYProperty().bind(menuBar.heightProperty());
		drawPane.layoutXProperty().bind(toolPane.widthProperty());
		bottomPane.getChildren().addAll(drawPane, toolPane);
	}
}
