package ui;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

import application.Main;
import datastructure.LimitedStack;
import entities.PointEntity;
import entities.RectangleEntity;
import javafx.beans.binding.DoubleExpression;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
	 * ���MoveFrame, ����ȡ��ѡ�С������ͷ���������
	 */
	private HashMap<Integer, MoveFrame> oldMap;
	private HashMap<Integer, MoveFrame> map;

	private LimitedStack<Integer[], MoveFrame[]> unDoStack;
	private LimitedStack<Integer[], MoveFrame[]> reDoStack;

	private Rectangle selectRect;

	public DrawPane(RootPane parent, DoubleExpression width, DoubleExpression height) {
		this.parent = parent;
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);
		this.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
		map = new HashMap<>();
		oldMap = new HashMap<>();
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
				if (hasSelected)
					return;
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
		unDoStack = new LimitedStack<>();
		reDoStack = new LimitedStack<>();
	}

	public boolean isOutBound(double x, double y) {
		return !(0 <= x && x <= this.getWidth() && 0 <= y && y <= this.getHeight());
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
	 *
	 * @param frame
	 */
	public void add(MoveFrame frame) {
		change(frame.getID(), frame);
	}

	public void delete(MoveFrame frame) {
		change(frame.getID(), null);
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

	private void add(LinkedList<Node> nodes) {
		add(nodes.toArray(new Node[0]));
	}

	public void deleteAllSelected() {
		for (Entry<Integer, MoveFrame> entry : map.entrySet()) {
			MoveFrame frame = entry.getValue();
			if (frame.isSelected()) {
				delete(frame);
			}
		}
	}

	private void delete(LinkedList<Node> nodes) {
		for (Node e : nodes) {
			this.getChildren().remove(e);
		}
	}

	public PointEntity getCenter() {
		return new PointEntity(this.getWidth() / 2f, this.getHeight() / 2f);
	}

	/**
	 * �رճ�frame������MoveFrame��ѡ��״̬
	 *
	 * @param frame
	 *            ���رյ��Ǹ�frame��Ϊnullʱ�ر�����MoveFrame��ѡ��
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

	public void change(int id, MoveFrame newFrame) {
		Integer[] ids = { id };
		MoveFrame[] newFrames = { newFrame };
		change(ids, newFrames);
	}

	public void change(Integer[] ids, MoveFrame[] newFrames) {
		MoveFrame[] oldFrames = new MoveFrame[ids.length];
		for (int i = 0; i < ids.length; i++) {
			oldFrames[i] = oldMap.get(ids[i]);
			if (!map.containsKey(ids[i])) {// �½�
				map.put(ids[i], newFrames[i]);
				add(newFrames[i].getNodes());
			}
			if (newFrames[i] == null) {// ɾ��
				oldMap.put(ids[i], null);
				map.remove(ids[i]);
				delete(oldFrames[i].getNodes());
			} else {// �ı�
				MoveFrame old = newFrames[i].clone();
				oldMap.put(ids[i], old);
			}
		}
		unDoStack.push(ids, oldFrames);
	}

	public void unDo() {
		unDo(this.unDoStack, this.reDoStack);
	}

	private void unDo(LimitedStack<Integer[], MoveFrame[]> unDoS, LimitedStack<Integer[], MoveFrame[]> reDoS) {
		if (unDoS.size() == 0) {
			return;
		}
		Entry<Integer[], MoveFrame[]> entry = unDoS.pop();
		Integer[] ids = entry.getKey();
		MoveFrame[] oldFrames = entry.getValue();
		MoveFrame[] nowFrames = new MoveFrame[ids.length];
		for (int i = 0; i < ids.length; i++) {
			nowFrames[i] = map.get(ids[i]);
			if(nowFrames[i] != null){
				delete(nowFrames[i].getNodes());
			}
			if (oldFrames[i] != null) {
				add(oldFrames[i].getNodes());
				map.put(ids[i], oldFrames[i]);
			} else {
				map.remove(ids[i]);
			}
		}
		reDoS.push(ids, nowFrames);
	}

	public void reDo() {
		/**
		 * reDo��unDo���෴���̣�ֻ��Ҫ�ߵ�˳�򼴿�
		 */
		unDo(this.reDoStack, this.unDoStack);
	}
}
