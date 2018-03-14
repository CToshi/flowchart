package ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class RootPane extends BorderPane {
	private ToolPane toolPane;
	private DrawPane drawPane;
	private MenuBar menuBar;

	public RootPane(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height) {
		this.prefHeightProperty().bind(width);
		
		this.setBackground(new Background(new BackgroundFill(Color.BLACK,null,null)));
		
		toolPane = new ToolPane(width.multiply(0.2), height);
		menuBar = new MenuBar();
		drawPane = new DrawPane(width.multiply(0.8), height);
		
		this.setTop(menuBar);
		this.setLeft(toolPane);
		this.setRight(drawPane);
	}
}
