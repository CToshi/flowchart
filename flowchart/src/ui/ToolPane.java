package ui;

import java.util.LinkedList;

import entities.RectangleEntity;
import entities.ShapeState.Type;
import javafx.beans.binding.DoubleExpression;
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
public class ToolPane extends Pane {

	private int iDSelected;
	private ToolItem[] toolItems = new ToolItem[Type.values().length];

	public ToolPane(RootPane parent, double width, DoubleExpression height) {
		this.prefHeightProperty().bind(height);
		this.setPrefWidth(width);
		// this.prefWidthProperty().bind(width);

		this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10),
				new BorderWidths(1));
		Border border = new Border(borderStroke);
		this.setBorder(border);
		this.iDSelected = -1;

		int index = 0;
		toolItems[index] = new ToolItem(Type.RECTANGLE, "处理框,表示算法的一个步骤,一个处理环节",index++);
		toolItems[index] = new ToolItem(Type.ROUNDED_RECTANGLE, "圆角矩形，起止框，表示算法的开始和结束",index++);
		toolItems[index] = new ToolItem(Type.DIAMOND, "菱形，判断框，表示算法的一个判断、一个条件",index++);
		toolItems[index] = new ToolItem(Type.PARALLELOGRAM, "平行四边形，输入输出框，表示算法的一个输入或输出",index++);
		toolItems[index] = new ToolItem(Type.ARROW, "箭头类,连接两个框",index++);
		toolItems[index] = new ToolItem(Type.POLYGONALARROW, "折线箭头", index++);
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
			this.getChildren().add(e);
		}
	}


	public void setiDSelected(int iDSelected) {
		if(this.iDSelected!=-1){
			setItemStroke(null);
		}
		this.iDSelected = iDSelected;
	}

	public void setItemStroke(Border border){
		if(iDSelected!=-1){
			toolItems[iDSelected].setStroke(border);
			iDSelected = -1;
		}
	}
}
