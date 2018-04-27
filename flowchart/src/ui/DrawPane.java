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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
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
//	private boolean hasSelected;
	/**
	 * ���MoveFrame, ����ȡ��ѡ�С������ͷ���������
	 */
	private HashMap<Integer, MoveFrame> oldMap;
	private HashMap<Integer, MoveFrame> map;

	private LimitedStack<Integer[], MoveFrame[]> unDoStack;
	private LimitedStack<Integer[], MoveFrame[]> reDoStack;
	private Rectangle selectRect;
	private static final int MAX_UNDO_TIMES = 100;
	private int selectedCount;

	public DrawPane(RootPane parent, DoubleExpression width, DoubleExpression height) {
		this.parent = parent;
		this.prefWidthProperty().bind(width);
		this.prefHeightProperty().bind(height);
		this.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));

		BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10),
				new BorderWidths(1));
		Border border = new Border(borderStroke);
		this.setBorder(border);

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
						frame.setSelected(true);
					}
				}
				/**
				 * �Ƴ���Ļ�������Ӱ�����հ��ж�
				 */
				selectRect.setX(-100);
				selectRect.setWidth(0);
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				if (!mouse.getTarget().equals(DrawPane.this)) {

					return;
				}
				selectedCount = 0;
				Main.test(selectedCount);
				/**
				 * ����ʱ��λ���ƶ������µ㣬������Ϊ0����ʾ����ɫ���ر�����ͼ�ε�ѡ��
				 */
				selectRect.setX(mouse.getX());
				selectRect.setY(mouse.getY());
				selectRect.setWidth(0);
				selectRect.setHeight(0);
				selectRect.setStroke(Color.BLACK);
				closeOthers(null);
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
		unDoStack = new LimitedStack<>(MAX_UNDO_TIMES);
		reDoStack = new LimitedStack<>(MAX_UNDO_TIMES);
		parent.add(new KeyListener(KeyCode.DELETE) {
			@Override
			public void run() {
				deleteAllSelected();
			}
		});
		parent.add(new KeyListener(KeyCode.CONTROL, KeyCode.Z) {
			@Override
			public void run() {
				unDo();
			}
		});
		parent.add(new KeyListener(KeyCode.CONTROL, KeyCode.Y) {
			@Override
			public void run() {
				reDo();
			}
		});
	}

	public boolean isOutBound(double x, double y) {
		return !(0 <= x && x <= this.getWidth() && 0 <= y && y <= this.getHeight());
	}

	/**
	 * DrawPane�������û��MoveFrame��ѡ�ж�ִ�ж�������û��MoveFrame��ѡ��ʱDrawPane����������MoveFrameΪδѡ��״̬
	 *
	 * @param hasSelected
	 */
//	public void setHasSelected(boolean hasSelected) {
//		this.hasSelected = hasSelected;
//		if (selectedCount == 0 || hasKey(KeyCode.CONTROL)) {
//			selectedCount++;
//		} else if (selectedCount == 1 && !hasKey(KeyCode.CONTROL)) {
//			closeOthers((MoveFrame) mouse.getTarget());
//		}
//		Main.test(selectedCount);
//	}

	public void informSelected(MoveFrame frame) {
//		this.hasSelected = isSelected;
		if (selectedCount == 0 || hasKey(KeyCode.CONTROL)) {
			selectedCount++;
		} else if (selectedCount == 1 && !hasKey(KeyCode.CONTROL)) {
			closeOthers(frame);
		}
		Main.test(selectedCount);
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
				entry.getValue().setSelected(false);
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
			} else if (newFrames[i] == null) {// ɾ��
				oldMap.remove(ids[i]);
				MoveFrame frame = map.get(ids[i]);
				map.remove(ids[i]);
				delete(frame.getNodes());
				// delete(oldFrames[i].getNodes());
			}

			if (newFrames[i] != null) {
				oldMap.put(ids[i], newFrames[i].clone());
			}
		}
		unDoStack.push(ids, oldFrames);
		reDoStack.clear();
	}

	public void unDo() {
		unDo(this.unDoStack, this.reDoStack);
	}

	private void unDo(LimitedStack<Integer[], MoveFrame[]> unDoS, LimitedStack<Integer[], MoveFrame[]> reDoS) {
		if (unDoS.size() == 0) {
			return;
		}
		Pair<Integer[], MoveFrame[]> entry = unDoS.pop();
		Integer[] ids = entry.getKey();
		MoveFrame[] oldFrames = entry.getValue();
		MoveFrame[] nowFrames = new MoveFrame[ids.length];
		for (int i = 0; i < ids.length; i++) {
			nowFrames[i] = map.get(ids[i]);
			if (nowFrames[i] != null) {
				delete(nowFrames[i].getNodes());
			}
			if (oldFrames[i] != null) {
				add(oldFrames[i].getNodes());
				map.put(ids[i], oldFrames[i]);
				oldMap.put(ids[i], oldFrames[i].clone());
			} else {
				map.remove(ids[i]);
			}
		}
		reDoS.push(ids, nowFrames);
		//
	}

	public void reDo() {
		/**
		 * reDo��unDo���෴���̣�ֻ��Ҫ�ߵ�˳�򼴿�
		 */
		unDo(this.reDoStack, this.unDoStack);
	}

	public void remove(Node... nodes) {
		for (Node node : nodes) {
			this.getChildren().remove(node);
		}
	}
}
