package controller;

import entities.DrawableState.Type;
import factory.MoveControllerFactory;
import javafx.scene.input.MouseEvent;
import ui.DrawPane;
import ui.ToolPane;

public class ShapeCreationController {
	private static ShapeCreationController scc = new ShapeCreationController();
	private DrawPane drawPane;
	private static ToolPane toolPane;

	private ShapeCreationController() {
	}

	public static ShapeCreationController getInstance() {
		return scc;
	}

	public void setDrawPane(DrawPane drawPane) {
		this.drawPane = drawPane;
	}

	public void setToolPane(ToolPane toolPane){
		this.toolPane = toolPane;
	}

	public void inform(MouseEvent mouse, Type shapeType,int ID) {
		if (mouse.getClickCount() == 1) {
			drawPane.setShapeCreationType(shapeType);
			toolPane.setiDSelected(ID);
		} else {
			drawPane.add(MoveControllerFactory.create(drawPane.getCenter(), shapeType));
			drawPane.setShapeCreationType(null);
		}
	}

	public static void createFinished(){
		toolPane.setItemStroke(null);
	}
}
