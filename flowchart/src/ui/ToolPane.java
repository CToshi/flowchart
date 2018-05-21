package ui;

import java.util.LinkedList;

import entities.DrawableState.Type;
import entities.RectangleEntity;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * 存放图形选择区的Pane
 *
 * @author Toshi
 *
 */
public class ToolPane {

	private int iDSelected;
	private ToolItem[] toolItems = new ToolItem[Type.values().length];
	private Pane toolPane;

	public ToolPane(RootPane parent, double width, DoubleExpression height) {
		toolPane = new Pane();
		toolPane.prefHeightProperty().bind(height);
		toolPane.setPrefWidth(width);

		toolPane.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10),
				new BorderWidths(1));
		Border border = new Border(borderStroke);
		toolPane.setBorder(border);
		this.iDSelected = -1;

		int index = 0;
		toolItems[index] = new ToolItem(Type.RECTANGLE, "处理框,表示算法的一个步骤,一个处理环节", index++);
		toolItems[index] = new ToolItem(Type.ROUNDED_RECTANGLE, "圆角矩形，起止框，表示算法的开始和结束", index++);
		toolItems[index] = new ToolItem(Type.DIAMOND, "菱形，判断框，表示算法的一个判断、一个条件", index++);
		toolItems[index] = new ToolItem(Type.PARALLELOGRAM, "平行四边形，输入输出框，表示算法的一个输入或输出", index++);
		toolItems[index] = new ToolItem(Type.ARROW, "箭头类,连接两个框", index++);
		toolItems[index] = new ToolItem(Type.ARROW_HORIZONTAL, "水平折线箭头", index++);
		toolItems[index] = new ToolItem(Type.ARROW_ERECT, "竖直折线箭头", index++);
		toolItems[index] = new ToolItem(Type.CIRCLE,
				"小圆形，连接符号，用于流程图过大，需要分页绘制时，在前一页绘制一个连接符号，后一页绘制一个连接符号，表示将流程图的两个部分连接起来", index++);
		toolItems[index] = new ToolItem(Type.CurveRectangle, "曲边矩形", index++);
		for (int i = 0; i < index; i++) {
			toolItems[i].setRectangle(new RectangleEntity(0, i * width / 1.5f, width, width / 1.5f));
			this.add(toolItems[i].getNodes());
		}
	}

	private void add(LinkedList<Node> nodes) {
		add(nodes.toArray(new Node[0]));
	}

	private void add(Node... nodes) {
		for (Node e : nodes) {
			toolPane.getChildren().add(e);
		}
	}

	public void setiDSelected(int iDSelected) {
		if (this.iDSelected != -1) {
			setItemStroke(null);
		}
		this.iDSelected = iDSelected;
	}

	public void setItemStroke(Border border) {
		if (iDSelected != -1) {
			toolItems[iDSelected].setStroke(border);
			iDSelected = -1;
		}
	}

//	public double getPrefWidth() {
//		return prefWidth;
//	}
//
//	public double getPrefHeight() {
//		return prefHeight;
//	}

	public ReadOnlyDoubleProperty widthProperty() {
		return toolPane.widthProperty();
	}
	public Pane getPane(){
		return toolPane;
	}
}
