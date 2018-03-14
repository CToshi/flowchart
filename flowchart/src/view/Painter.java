package view;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

public class Painter {
	private static Painter painter;
	private Pane pane;
	static {
		painter = new Painter();
	}

	private Painter() {
	}

	public static void setPane(Pane pane) {
		painter.pane = pane;
	}

	public void add(Shape shape) {
		pane.getChildren().add(shape);
	}

	public void add(Drawable drawable) {
		for (Shape s : drawable.getShapes()) {
			this.add(s);
		}
	}
	public static Painter getInstance(){
		return painter;
	}
}
