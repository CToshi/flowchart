package ui;

import java.util.HashMap;
import java.util.Map.Entry;

import application.Main;
import entities.PointEntity;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
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
	private boolean hasSelected;
	/**
	 * 存放moveframe, 用以取消选中、撤销和反撤销操作
	 */
	private HashMap<Integer, MoveFrame> map;

	public DrawPane(DoubleExpression width, DoubleExpression height) {
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);
		this.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		this.setOnMousePressed(mouse -> {
			if (hasSelected) {
				Main.test("有选中");
			} else {
				for (Entry<Integer, MoveFrame> entry : map.entrySet()) {
					entry.getValue().setSelected(false);
				}
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

}
