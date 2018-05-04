package ui;

import java.util.LinkedList;

import controller.ShapeCreationController;
import entities.DrawableState.Type;
import entities.RectangleEntity;
import factory.ShapeFactory;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import view.inter.Drawable;
import view.shape.ShapeItem;

public class ToolItem implements Drawable {
	private static final double DEFAULT_INSET = 10;
	private LinkedList<Node> list;
	private Label label;
	private ShapeItem shapeItem;

	public ToolItem(Type shapeType, String tipString) {
		this(new RectangleEntity(0, 0, 0, 0), shapeType, tipString);
	}
	public ToolItem(RectangleEntity rectangle, Type shapeType, String tipString) {
		this(rectangle, (ShapeItem) ShapeFactory.create(shapeType), tipString);
	}

	private ToolItem(RectangleEntity rectangle, ShapeItem shapeItem, String tipString) {
		this.label = new Label();
		this.shapeItem = shapeItem;
		Tooltip tooltip = new Tooltip(tipString);
		label.setTooltip(tooltip);
		setRectangle(rectangle);
		label.setOnMouseClicked(e -> {
			// whenClicked(e);
			ShapeCreationController.getInstance().inform(e, shapeItem.getType());
		});
		list = new LinkedList<>();
		list.addAll(shapeItem.getNodes());
		list.add(label);
	}

	// public abstract void whenClicked(MouseEvent mouse);

	@Override
	public final LinkedList<Node> getNodes() {
		return list;
	}
	public void setRectangle(RectangleEntity rectangle) {
		label.setLayoutX(rectangle.getX());
		label.setLayoutY(rectangle.getY());
		label.setPrefWidth(rectangle.getWidth());
		label.setPrefHeight(rectangle.getHeight());
		shapeItem.setX(rectangle.getX() + DEFAULT_INSET);
		shapeItem.setY(rectangle.getY() + DEFAULT_INSET);
		shapeItem.setWidth(rectangle.getWidth() - 2 * DEFAULT_INSET);
		shapeItem.setHeight(rectangle.getHeight() - 2 * DEFAULT_INSET);
	}
}
