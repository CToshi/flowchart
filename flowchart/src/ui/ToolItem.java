package ui;

import entities.RectangleEntity;
import javafx.scene.input.MouseEvent;
import view.shape.ShapeItem;

public abstract class ToolItem {
	public ToolItem(RectangleEntity rectangle, ShapeItem shapeItem) {

	}
	public abstract void whenClicked(MouseEvent mouse);
}
