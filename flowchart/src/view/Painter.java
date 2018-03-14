package view;

import javafx.scene.shape.Shape;
import ui.DrawPane;

public class Painter {
	private static Painter painter;
	private DrawPane drawPane;
	static {
		painter = new Painter();
	}

	private Painter() {
	}

	public static void setPane(DrawPane drawPane) {
		painter.drawPane = drawPane;
	}

	public void add(Shape shape) {
		drawPane.getChildren().add(shape);
	}

	public void add(DrawElement element) {
		drawPane.add(element);
		for (Shape s : element.getShapes()) {
			this.add(s);
		}
	}
	public static Painter getInstance(){
		return painter;
	}
}
