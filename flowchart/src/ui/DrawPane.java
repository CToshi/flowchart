package ui;

import java.util.HashMap;
import java.util.Map.Entry;

import application.Main;
import entities.PointEntity;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import view.inter.Drawable;
import view.move.MoveFrame;

/**
 * 图形的绘制区域
 *
 * @author Toshi
 *
 */
public class DrawPane extends Pane {
	// private DrawManager manager;
	private RootPane parent;
	private boolean hasSelected;
	/**
	 * 存放moveframe, 用以取消选中、撤销和反撤销操作
	 */
	private HashMap<Integer, MoveFrame> map;

	public DrawPane(RootPane parent, DoubleExpression width, DoubleExpression height) {
		this.parent = parent;
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);
		this.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		this.setOnMousePressed(mouse -> {
			if (!hasSelected) {
				closeOthers(null);
			}
		});
		map = new HashMap<>();

	}

	public boolean isOutBound(double x, double y) {
		return !(0 <= x && x <= this.getWidth() && 0 <= y && y <= this.getHeight());
	}

	/**
	 * 用于取消选中、撤销及反撤销操作。会将MoveFrame加入到map中，当MoveFrame有移动或改变大小操作时需要自行调用该函数。
	 *
	 * @param moveFrame
	 */
	public void add(MoveFrame moveFrame) {
		add(moveFrame.getNodes());
		map.put(moveFrame.getID(), moveFrame);
	}

	/**
	 * DrawPane会根据有没有MoveFrame被选中而执行动作，当没有MoveFrame被选中时DrawPane会设置所有MoveFrame为未选中状态
	 *
	 * @param hasSelected
	 */
	public void setHasSelected(boolean hasSelected) {
		this.hasSelected = hasSelected;
	}

	/**
	 * 将nodes加到DrawPane中显示出来
	 *
	 * @param nodes
	 *
	 */
	public void add(Node... nodes) {
		for (Node e : nodes) {
			this.getChildren().add(e);
		}
	}

	public void remove(Node... nodes) {
		for (Node e : nodes) {
			if (this.getChildren().contains(e)) {
				this.getChildren().remove(e);
			}
		}
	}

	public PointEntity getCenter() {
		return new PointEntity(this.getWidth() / 2f, this.getHeight() / 2f);
	}

	/**
	 * 当只选定一个moveframe时，frame可以调用该函数去取消其它frame的选中
	 * @param frame
	 */
	public void closeOthers(MoveFrame frame){
		if(parent.hasKey(KeyCode.CONTROL)){
			return;
		}
		for (Entry<Integer, MoveFrame> entry : map.entrySet()) {
			if(!entry.getValue().equals(frame)){
				entry.getValue().setSelected(false);
			}
		}
	}

}
