package controller;

import entities.DrawableState.Type;
import factory.MoveControllerFactory;
import factory.ShapeFactory;
import javafx.scene.input.MouseEvent;
import ui.DrawPane;
import view.move.MoveFrame;

public class ShapeCreationController {
	private static ShapeCreationController scc = new ShapeCreationController();
	private DrawPane drawPane;

	private ShapeCreationController() {
	}

	public static ShapeCreationController getInstance() {
		return scc;
	}

	public void setDrawPane(DrawPane drawPane) {
		this.drawPane = drawPane;
	}

	public void inform(MouseEvent mouse, Type shapeType) {
		if (mouse.getClickCount() == 1) {
			drawPane.setShapeCreationType(shapeType);
		} else {
//			drawPane.add(new MoveFrame(drawPane, ShapeFactory.create(drawPane.getCenter(), shapeType)));
			drawPane.add(MoveControllerFactory.create(drawPane.getCenter(), shapeType));
			drawPane.setShapeCreationType(null);
		}
	}
}
