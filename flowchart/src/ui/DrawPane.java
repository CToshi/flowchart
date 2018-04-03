package ui;

import java.util.HashMap;
import java.util.Map.Entry;

import entities.PointEntity;
import entities.RectangleEntity;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import view.inter.Draggable;
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

	private Rectangle selectRect;

	public DrawPane(RootPane parent, DoubleExpression width, DoubleExpression height) {
		this.parent = parent;
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		
		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1));
		Border border = new Border(borderStroke);
		this.setBorder(border);
		
		this.setOnMousePressed(mouse -> {
		});
		map = new HashMap<>();

		selectRect = new Rectangle();
		selectRect.setFill(Color.TRANSPARENT);

		add(selectRect);
		new Draggable() {
			@Override
			protected void whenReleased(MouseEvent mouse) {
				/**
				 * �ſ�ʱ�����ؿ򣬱�������MoveFrame�������frame����ѡ�п�(selectRect)�У�������Ϊѡ��
				 */
				selectRect.setStroke(Color.TRANSPARENT);
				RectangleEntity rectE = new RectangleEntity(selectRect);
				for (Entry<Integer, MoveFrame> entry : map.entrySet()) {
					MoveFrame frame = entry.getValue();
					if (rectE.contains(frame.getRectangle())) {
						frame.setSelected(true, false);
					}
				}
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				/**
				 * ����ʱ��λ���ƶ������µ㣬������Ϊ0����ʾ����ɫ���ر�����ͼ�ε�ѡ��
				 */
				selectRect.setX(mouse.getX());
				selectRect.setY(mouse.getY());
				selectRect.setWidth(0);
				selectRect.setHeight(0);
				selectRect.setStroke(Color.WHITE);
				if (!DrawPane.this.hasSelected) {
					closeOthers(null);
				}
			}

			@Override
			protected void whenDragged(double xDelta, double yDelta) {
				selectRect.setX(Math.min(getMousePosition().getX(), getStartPosition().getX()));
				selectRect.setY(Math.min(getMousePosition().getY(), getStartPosition().getY()));
				selectRect.setWidth(Math.abs(getMousePosition().getX() - getStartPosition().getX()));
				selectRect.setHeight(Math.abs(getMousePosition().getY() - getStartPosition().getY()));
			}

			@Override
			protected Node getNode() {
				return DrawPane.this;
			}
		};
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

	// public void remove(Node... nodes) {
	// for (Node e : nodes) {
	// // if (this.getChildren().contains(e)) {
	// this.getChildren().remove(e);
	// // }
	// }
	// }

	public PointEntity getCenter() {
		return new PointEntity(this.getWidth() / 2f, this.getHeight() / 2f);
	}

	/**
	 * �رճ�frame������MoveFrame��ѡ��״̬
	 *
	 * @param frame ���رյ��Ǹ�frame��Ϊnullʱ�ر�����MoveFrame��ѡ��
	 */
	public void closeOthers(MoveFrame frame) {
		for (Entry<Integer, MoveFrame> entry : map.entrySet()) {
			if (!entry.getValue().equals(frame)) {
				entry.getValue().setSelected(false, false);
			}
		}
	}

	public boolean hasKey(KeyCode... keyCodes) {
		return parent.hasKey(keyCodes);

	}
}
