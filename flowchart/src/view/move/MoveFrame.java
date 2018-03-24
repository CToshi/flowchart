package view.move;

import java.util.LinkedList;

import application.Main;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import ui.DrawPane;
import view.inter.Drawable;
import view.shape.ShapeItem;
import view.text_input.TextManager;

/**
 * �ƶ������ڿ���ͼ�ε��ƶ�
 *
 * @author Toshi
 *
 */
public class MoveFrame implements Drawable {

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

	private LinkedList<Node> nodeList;
	/**
	 * ΨһID�����ڳ�������
	 */
	private int ID;
	private static int MOVE_FRAME_ID;

	/**
	 *
	 * @param parent
	 *            ��MoveFrame�ĸ��ף�����ʾ��Pane
	 */
	public MoveFrame(DrawPane parent, ShapeItem shapeItem) {
		this.ID = MOVE_FRAME_ID++;
		this.shapeItem = shapeItem;
		this.parent = parent;
		rectangle = new DraggableRectangle(shapeItem.getX(), shapeItem.getY(), shapeItem.getWidth(),
				shapeItem.getHeight()) {

			@Override
			protected void deal(double xDelta, double yDelta) {
				this.move(xDelta, yDelta);
				MoveFrame.this.fixPosition();
			}

			@Override
			protected void whenReleased(MouseEvent mouse) {
				fixPosition();
				setHasSelected(false);
			}

			@Override
			protected void whenPressed(MouseEvent mouse) {
				setHasSelected(true);
				setSelected(true);
			}

			@Override
			protected boolean isOutBound(double x, double y) {
				return parent.isOutBound(x, y);
			}
		};
		rectangle.setAppearence(Color.TRANSPARENT, Color.WHITE, 1);
		points = new MovePoint[8];
		/**
		 * offset��ֵΪ0.5ʱ������ɸı䣬��Ϊ�����м�, ��Ϊ��double�о����������abs(x - 0.5) > eps ����
		 * x != 0.5
		 */
		for (int i = 0; i < points.length; i++) {
			points[i] = new MovePoint(this, Math.abs(offset[i][0] - 0.5) > 0.0001,
					Math.abs(offset[i][1] - 0.5) > 0.0001, CURSORS[i / 2]);
		}
		for (int i = 0; i < points.length; i++) {
			points[i].setOtherPoint(points[i ^ 1]);
		}
		this.fixPosition();
		nodeList = new LinkedList<>();
		this.initNodeList();
		setHidden();
	}

	/**
	 * ��֪parent�Լ���ѡ��
	 *
	 * @param hasSelected
	 */
	void setHasSelected(boolean hasSelected) {
		parent.setHasSelected(hasSelected);
	}

	@Override
	public Node[] getNodes() {
		return getNodeListToArray();
	}

	/**
	 * ����8���϶��������
	 */
	void fixPosition() {
		for (int i = 0; i < points.length; i++) {
			points[i].setCenterXY(rectangle.getX() + rectangle.getWidth() * offset[i][0],
					rectangle.getY() + rectangle.getHeight() * offset[i][1]);
		}
		shapeItem.setRectangle(rectangle.getRectangle());
	}

	/**
	 * ��8���㱻�϶������л���ô˺�������������8����
	 */
	void setHidden() {
		rectangle.setHide();
		for (int i = 0; i < points.length; i++) {
			points[i].setHide();
		}
	}

	/**
	 * �϶�������ָ���ʾ
	 */
	void setShow() {
		rectangle.setShow();
		for (int i = 0; i < points.length; i++) {
			points[i].setShow();
		}
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

	/**
	 * ȡ��ѡ�л������ƶ��򣬴���true��Ч��
	 *
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected) {
		if (isSelected) {
			setShow();
			parent.closeOthers(this);
		} else {
			setHidden();
		}
	}

	/**
	 *
	 * @return ������ʽ��nodeList���Node
	 */
	private Node[] getNodeListToArray() {
		return nodeList.toArray(new Node[0]);
	}

	private void initNodeList() {
		nodeList.clear();
		for (Node e : shapeItem.getNodes()) {
			nodeList.add(e);
		}
		// for (Node e : textManager.getNodes()) {
		// nodeList.add(e);
		// }
		nodeList.add(rectangle);
		for (int i = 0; i < points.length; i++) {
			nodeList.add(points[i]);
		}
	}

	public int getID() {
		return ID;
	}
	// @Override
	// public void setRectangle(RectangleEntity rectangle) {
	// setX(rectangle.getX());
	// setY(rectangle.getY());
	// setWidth(rectangle.getWidth());
	// setHeight(rectangle.getHeight());
	// }
}
