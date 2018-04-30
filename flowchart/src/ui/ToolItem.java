package ui;

import java.util.LinkedList;

import entities.RectangleEntity;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import view.inter.Drawable;
import view.shape.ShapeItem;

public abstract class ToolItem implements Drawable {
	private static final double DEFAULT_INSET = 10;
	private LinkedList<Node> list;

	public ToolItem(RectangleEntity rectangle, ShapeItem shapeItem, String tipString) {
		Label label = new Label();
		label.setLayoutX(rectangle.getX());
		label.setLayoutY(rectangle.getY());
		label.setPrefWidth(rectangle.getWidth());
		label.setPrefHeight(rectangle.getHeight());
		Tooltip tooltip = new Tooltip(tipString);
		label.setTooltip(tooltip);
		shapeItem.setX(rectangle.getX() + DEFAULT_INSET);
		shapeItem.setY(rectangle.getY() + DEFAULT_INSET);
		shapeItem.setWidth(rectangle.getWidth() - 2 * DEFAULT_INSET);
		shapeItem.setHeight(rectangle.getHeight() - 2 * DEFAULT_INSET);

		label.setOnMouseClicked(e -> {
			whenClicked(e);
		});
		list = new LinkedList<>();
		list.addAll(shapeItem.getNodes());
		list.add(label);
	}

	public abstract void whenClicked(MouseEvent mouse);

	@Override
	public final LinkedList<Node> getNodes() {
		return list;
	}
}
