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
 * ���ͼ��ѡ������Pane
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
		toolItems[index] = new ToolItem(Type.RECTANGLE, "�����,��ʾ�㷨��һ������,һ��������", index++);
		toolItems[index] = new ToolItem(Type.ROUNDED_RECTANGLE, "Բ�Ǿ��Σ���ֹ�򣬱�ʾ�㷨�Ŀ�ʼ�ͽ���", index++);
		toolItems[index] = new ToolItem(Type.DIAMOND, "���Σ��жϿ򣬱�ʾ�㷨��һ���жϡ�һ������", index++);
		toolItems[index] = new ToolItem(Type.PARALLELOGRAM, "ƽ���ı��Σ���������򣬱�ʾ�㷨��һ����������", index++);
		toolItems[index] = new ToolItem(Type.ARROW, "��ͷ��,����������", index++);
		toolItems[index] = new ToolItem(Type.ARROW_HORIZONTAL, "ˮƽ���߼�ͷ", index++);
		toolItems[index] = new ToolItem(Type.ARROW_ERECT, "��ֱ���߼�ͷ", index++);
		toolItems[index] = new ToolItem(Type.CIRCLE,
				"СԲ�Σ����ӷ��ţ���������ͼ������Ҫ��ҳ����ʱ����ǰһҳ����һ�����ӷ��ţ���һҳ����һ�����ӷ��ţ���ʾ������ͼ������������������", index++);
		toolItems[index] = new ToolItem(Type.CurveRectangle, "���߾���", index++);
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
