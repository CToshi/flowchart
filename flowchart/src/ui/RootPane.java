package ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
		menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(width);
		this.getChildren().addAll(bottomPane, menuBar);
		drawPane = new DrawPane(this.widthProperty().multiply(0.8), this.heightProperty().subtract(menuBar.heightProperty()));
		toolPane = new ToolPane(this.widthProperty().multiply(0.2), this.heightProperty().subtract(menuBar.heightProperty()));
		bottomPane.layoutYProperty().bind(menuBar.heightProperty());
		drawPane.layoutXProperty().bind(toolPane.widthProperty());
		bottomPane.getChildren().addAll(drawPane, toolPane);
//		this.setOnMouseClicked(e->{
//			System.out.println("RootPane:"+this.getWidth()+" "+this.getHeight());
//			System.out.println(e.getX()+" "+e.getY());
//			System.out.println(drawPane.getHeight()+" "+menuBar.getHeight());
//		});
	}
}
