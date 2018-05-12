package view.move;

import java.util.LinkedList;

import application.Main;
import entities.DrawableState;
import entities.RectangleEntity;
import entities.ShapeState;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ui.DrawPane;
import utility.Util;
import view.shape.ShapeItem;
import view.text_input.InputController;
import view.text_input.TextManager;

/**
 * �ƶ������ڿ���ͼ�ε��ƶ�
 *
 * @author Toshi
 *
 */
public class MoveFrame implements MoveController {

	private DraggableRectangle rectangle;
	/**
	 * �ƶ����8����
	 */
	private MovePoint[] points;
	/**
	 * 8���������rectangle�����Ͻ����������ƫ����, offset[i][0]�ǵ�i�����xƫ������offset[i][1]��yƫ����
	 * xƫ������ʾ����rectangle.width�ı��� ,y����height, offset��0��7��8����ֱ��� 0 4 7 2 3 8 5 1
	 * ���Կ������ڵ��������λ�øպ��Ƕ����ģ������ƶ�ʱ������ȷ��
	 *
	 */
	private static final double offset[][] = { { 0, 0 }, { 1, 1 }, { 0, 0.5 }, { 1, 0.5 }, { 0.5, 0 }, { 0.5, 1 },
			{ 1, 0 }, { 0, 1 } };
	/**
	 * �����ʽ����Ӧ��ϵͬoffset
	 */
	private static final Cursor[] CURSORS = { Cursor.SE_RESIZE, Cursor.E_RESIZE, Cursor.S_RESIZE, Cursor.SW_RESIZE };

	private DrawPane parent;

	/**
	 * ���ֵ�ͼ��
	 */
	private ShapeItem shapeItem;
	private TextManager textManager;
	private InputController inputController;

	private LinkedList<Node> nodeList;
	/**
	 * ΨһID�����ڳ�������
	 */
	private int ID;
	// private static int MOVE_FRAME_ID;

	private boolean isSelected;

	private boolean isInputIng;

	private SyncMoveController syncMoveController = SyncMoveController.getInstance();
	/**
	 *
	 * @param parent
	 *            ��MoveFrame�ĸ��ף�����ʾ��Pane
	 * @param shapeItem
	 *            MoveFrame�ĳ�ʼλ����ShapeItem����
	 * @param isClone
	 *            cloneʱID��������
	 *
	 */
	public MoveFrame(DrawPane parent, ShapeItem shapeItem, int ID) {
		this.ID = ID;
		this.shapeItem = shapeItem;
		this.parent = parent;
		this.inputController = InputController.getInstance();
		rectangle = new DraggableRectangle(shapeItem.getX(), shapeItem.getY(), shapeItem.getWidth(),
				shapeItem.getHeight(), Cursor.MOVE, Color.TRANSPARENT) {
			private RectangleEntity lastRect;

			@Override
			protected void deal(double xDelta, double yDelta) {
//				this.move(xDelta, yDelta);
//				MoveFrame.this.fixPosition();
				syncMoveController.informMoving(new MoveMsg(xDelta, yDelta));
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				MoveFrame.this.closeInput();
				lastRect = this.getRectangle();
				// setHasSelected(true);
				if (!isSelected) {
					parent.informSelected(MoveFrame.this);
				}
				setSelected(true);
				syncMoveController.initialMoving();
				// setSelected(true, true);

			}

			@Override
			protected void whenReleased(MouseEvent mouse) {
				// setHasSelected(false);
				// parent.informSelected(null, false);
				if (!getRectangle().equals(lastRect)) {
					fixPosition();
					syncMoveController.movingFinished();
//					informChange();
				}else{
					if (mouse.getClickCount() >= 2) {
						isInputIng = true;
						inputController.getTextArea().setText(textManager.getText());
						inputController.setInformation(shapeItem.getTextRectangle(), textManager.getText());
						parent.add(inputController.getTextArea());
						// textManager.showInput();
					}
				}
			}

			@Override
			protected boolean isOutBound(double x, double y) {
				return parent.isOutBound(x, y);
			}

		};
		// rectangle.setAppearence(Color.TRANSPARENT, Color.BLACK, 1);
		points = new MovePoint[8];
		/**
		 * offset��ֵΪ0.5ʱ������ɸı䣬��Ϊ�����м�, ��Ϊ��double�о����������abs(x - 0.5) > eps ���� x
		 * != 0.5
		 */
		for (int i = 0; i < points.length; i++) {
			points[i] = new MovePoint(this, Math.abs(offset[i][0] - 0.5) > 0.0001,
					Math.abs(offset[i][1] - 0.5) > 0.0001, CURSORS[i / 2]);
		}
		for (int i = 0; i < points.length; i++) {
			points[i].setOtherPoint(points[i ^ 1]);
		}
		textManager = new TextManager(shapeItem.getTextRectangle());
		setHidden();
		nodeList = new LinkedList<>();
		this.initNodeList();
		this.fixPosition();
	}

	/**
	 * ��֪parent�Լ���ѡ��
	 *
	 * @param hasSelected
	 */
	// void setHasSelected(boolean hasSelected) {
	// parent.informSelected(this, hasSelected);
	// }

	@Override
	public LinkedList<Node> getNodes() {
		return nodeList;
	}

	/**
	 * ����8���϶��㡢shapeItem, textManager������
	 */
	void fixPosition() {
		fixPosition(true);
	}

	private void fixPosition(boolean needFixPoints) {
		if (needFixPoints) {
			for (int i = 0; i < points.length; i++) {
//				Main.test(233);
				points[i].setCenterXY(rectangle.getX() + rectangle.getWidth() * offset[i][0],
						rectangle.getY() + rectangle.getHeight() * offset[i][1]);
			}
		}
		RectangleEntity rect = rectangle.getRectangle();
		shapeItem.setRectangle(rect);
		textManager.setRectangle(shapeItem.getTextRectangle());
	}

	/**
	 * ��8���㱻�϶������л���ô˺�������������8����
	 */
	void setHidden() {
		// rectangle.setHidden(true);
		rectangle.setStroke(Color.TRANSPARENT);
		for (int i = 0; i < points.length; i++) {
			points[i].setHidden(true);
		}
		// textManager.setHidden(true);
		this.closeInput();
	}

	/**
	 * �϶�������ָ���ʾ
	 */
	void setShow() {
		// rectangle.setHidden(false);
		rectangle.setStroke(Color.BLACK);
		for (int i = 0; i < points.length; i++) {
			points[i].setHidden(false);
		}
		// textManager.setHidden(false);
	}

	public void setX(double value) {
		rectangle.setX(value);
		shapeItem.setX(value);
	}

	public void setY(double value) {
		rectangle.setY(value);
		shapeItem.setY(value);
	}

	public void setWidth(double value) {
		rectangle.setWidth(value);
		shapeItem.setWidth(value);
	}

	public void setHeight(double value) {
		rectangle.setHeight(value);
		shapeItem.setHeight(value);
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if (isSelected) {
			setShow();
		} else {
			setHidden();
		}
	}

	public boolean isSelected() {
		return isSelected;
	}

	/**
	 *
	 * @return ������ʽ��nodeList���Node
	 */

	private void initNodeList() {
		nodeList.clear();
		nodeList.addAll(shapeItem.getNodes());
		for (int i = 0; i < points.length; i++) {
			nodeList.addAll(points[i].getNodes());
		}
		nodeList.addAll(textManager.getNodes());
		nodeList.addAll(rectangle.getNodes());
	}

	public int getID() {
		return ID;
	}

	public RectangleEntity getRectangle() {
		return rectangle.getRectangle();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MoveFrame) {
			return ((MoveFrame) obj).getID() == this.getID();
		}
		return false;
	}

	void informChange() {
		parent.change(getID(), this);
	}

	public void remove(Node... nodes) {
		parent.remove(nodes);
	}

	public void add(Node... nodes) {
		parent.add(nodes);
	}

	private void closeInput() {
		if (isInputIng) {
			textManager.setText(inputController.getTextArea().getText());
			parent.remove(inputController.getTextArea());
			isInputIng = false;
		}
	}

	@Override
	public DrawableState getState() {
		return new ShapeState(getRectangle(), textManager.getText(), shapeItem.getType(), getID());
	}

	@Override
	public void setState(DrawableState state) {
		ShapeState shapeState = (ShapeState) state;
		rectangle.setRectangle(shapeState.getRectangle());
		textManager.setText(shapeState.getText());
		fixPosition();
		this.ID = state.getID();
	}

	public void whenChanging() {
		fixPosition(false);
	}

	public void changeFinished() {
		textManager.setHidden(false);
	}

	public MovePoint[] getLinkPoints() {
		MovePoint[] linkPoints = new MovePoint[4];
		int index = 0;
		for (int i = 0; i < 8; i++) {
			if (Util.isEquals(offset[i][0], 0.5) || Util.isEquals(offset[i][1], 0.5)) {
				linkPoints[index++] = points[i];
			}
		}
		return linkPoints;
	}

	@Override
	public void setChange(MoveMsg changeMsg) {
		RectangleEntity rectangle = getRectangle();
		rectangle.setX(rectangle.getX() + changeMsg.getDeltaX());
		rectangle.setY(rectangle.getY() + changeMsg.getDeltaY());
		this.rectangle.setRectangle(rectangle);
		fixPosition();
	}
}
