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
 * ͼ�εĻ�������
 *
 * @author Toshi
 *
 */
public class DrawPane extends Pane {
	// private DrawManager manager;
	private RootPane parent;
	private boolean hasSelected;
	/**
	 * ���moveframe, ����ȡ��ѡ�С������ͷ���������
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
	 * ����ȡ��ѡ�С��������������������ὫMoveFrame���뵽map�У���MoveFrame���ƶ���ı��С����ʱ��Ҫ���е��øú�����
	 *
	 * @param moveFrame
	 */
	public void add(MoveFrame moveFrame) {
		add(moveFrame.getNodes());
		map.put(moveFrame.getID(), moveFrame);
	}

	/**
	 * DrawPane�������û��MoveFrame��ѡ�ж�ִ�ж�������û��MoveFrame��ѡ��ʱDrawPane����������MoveFrameΪδѡ��״̬
	 *
	 * @param hasSelected
	 */
	public void setHasSelected(boolean hasSelected) {
		this.hasSelected = hasSelected;
	}

	/**
	 * ��nodes�ӵ�DrawPane����ʾ����
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
	 * ��ֻѡ��һ��moveframeʱ��frame���Ե��øú���ȥȡ������frame��ѡ��
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
