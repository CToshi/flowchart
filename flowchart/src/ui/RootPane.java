package ui;

import controller.ShapeCreationController;
import factory.MoveControllerFactory;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import view.move.SyncMoveController;

/**
 * ×î´óµÄPane
 *
 * @author Toshi
 *
 */
public class RootPane extends Pane {
	private ToolPane toolPane;
	private DrawPane drawPane;
	private MenuBar menuBar;
	private Pane bottomPane;

	public RootPane(ReadOnlyDoubleProperty stageWidth, ReadOnlyDoubleProperty stageHeight) {
		this.prefHeightProperty().bind(stageWidth);

		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		bottomPane = new Pane();

		menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(this.widthProperty());
		this.getChildren().addAll(bottomPane, menuBar);
		final double TOOL_PANE_WIDTH = 100;
		toolPane = new ToolPane(this, TOOL_PANE_WIDTH,
				this.heightProperty().subtract(menuBar.heightProperty()));
		drawPane = new DrawPane(this, this.widthProperty().subtract(TOOL_PANE_WIDTH),
				this.heightProperty().subtract(menuBar.heightProperty()));
		bottomPane.layoutYProperty().bind(menuBar.heightProperty());
		drawPane.layoutXProperty().bind(toolPane.widthProperty());
		bottomPane.getChildren().addAll(drawPane, toolPane.getPane());
		ShapeCreationController.getInstance().setDrawPane(drawPane);
		ShapeCreationController.getInstance().setToolPane(toolPane);;
		MoveControllerFactory.setDrawPane(drawPane);
		SyncMoveController.setDrawPane(drawPane);
		QzyFileManager.setDrawPane(drawPane);

		menuBar.setDrawPane(drawPane);
	}


	public DrawPane getDrawPane() {
		return drawPane;
	}

	public ToolPane getToolPane() {
		return toolPane;
	}

}