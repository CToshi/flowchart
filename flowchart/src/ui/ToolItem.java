package ui;

import java.util.LinkedList;

import controller.ShapeCreationController;
import entities.DrawableState.Type;
import entities.RectangleEntity;
import factory.ShapeFactory;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import view.inter.Drawable;
import view.shape.ShapeItem;

public class ToolItem implements Drawable {
	private static final double DEFAULT_INSET = 10;
	private LinkedList<Node> list;
	private Label label;
	private ShapeItem shapeItem;
	private BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.DOTTED, new CornerRadii(10),new BorderWidths(3));
	private Border border = new Border(borderStroke);
	private int ID;

	public ToolItem(Type shapeType, String tipString,int ID) {
		this(new RectangleEntity(0, 0, 0, 0), shapeType, tipString,ID);
	}
	public ToolItem(RectangleEntity rectangle, Type shapeType, String tipString,int ID) {
		this(rectangle, ShapeFactory.create(shapeType), tipString);
		this.ID = ID;
	}

	private ToolItem(RectangleEntity rectangle, ShapeItem shapeItem, String tipString) {
		this.label = new Label();
		this.shapeItem = shapeItem;
		Tooltip tooltip = new Tooltip(tipString);
		label.setTooltip(tooltip);
		setRectangle(rectangle);
		label.setOnMouseClicked(e -> {
			// whenClicked(e);
			ShapeCreationController.getInstance().inform(e, shapeItem.getType(),ID);
			label.setBorder(border);
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
		RectangleEntity rect = new RectangleEntity(rectangle.getX() + DEFAULT_INSET,
				rectangle.getY() + DEFAULT_INSET,
				rectangle.getWidth() - 2 * DEFAULT_INSET,
				rectangle.getHeight() - 2 * DEFAULT_INSET);
		shapeItem.setRectangle(rect);
//		shapeItem.setX(rectangle.getX() + DEFAULT_INSET);
//		shapeItem.setY(rectangle.getY() + DEFAULT_INSET);
//		shapeItem.setWidth(rectangle.getWidth() - 2 * DEFAULT_INSET);
//		shapeItem.setHeight(rectangle.getHeight() - 2 * DEFAULT_INSET);
	}

	public void setStroke(Border border){
		label.setBorder(border);
	}
}
