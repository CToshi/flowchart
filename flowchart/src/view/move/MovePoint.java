package view.move;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MovePoint extends DraggableRectangle{
	private static final double size = 10;
	private MoveFrame parent;
	private MovePoint other;
	private boolean xChangable;
	private boolean yChangable;

	public MovePoint(MoveFrame target, boolean xChangable, boolean yChangable, Cursor cursor) {
		super(0, 0, size, size, cursor, Color.BLACK);
		this.setWidth(size);
		this.setHeight(size);
		this.parent = target;
		this.xChangable = xChangable;
		this.yChangable = yChangable;

	}

	/**
	 * ���ö�����
	 * @param other
	 */
	public void setOtherPoint(MovePoint other) {
		this.other = other;
	}

	/**
	 * ��������ƶ��ĺ�����
	 * ���������ǣ����x���Ըı�(ĳЩ�����õ��ǵ�һ�е��м�ĵ㣬��ʱ�϶��õ㲻�ܸı�x)����Ա��Լ�������Ͷ�����(other)������, ȡ��Сֵ��
	 * yͬx
	 */
	@Override
	protected void deal(double xDelta, double yDelta) {
		if (xChangable) {
			this.setX(this.getX() + xDelta);
			parent.setX(Math.min(this.getCenterX(), other.getCenterX()));
			parent.setWidth(Math.abs(this.getCenterX() - other.getCenterX()));
		}
		if (yChangable) {
			this.setY(this.getY() + yDelta);
			parent.setY(Math.min(this.getCenterY(), other.getCenterY()));
			parent.setHeight(Math.abs(this.getCenterY() - other.getCenterY()));
		}
		parent.whenChanging();
	}

	@Override
	protected void whenPressed(MouseEvent mouse) {
//		parent.setHasSelected(true);
		parent.setHidden();
//		parent.whenChanging();
	}

	@Override
	protected void whenReleased(MouseEvent mouse) {
//		parent.setHasSelected(false);
		parent.fixPosition();
		parent.setShow();
		parent.informChange();
//		parent.changeFinished();
	}

	/**
	 * ����Ϊ���أ������ǽ�Fill����ΪColor.TRANSPARENT�������Ϊ��ɫ���游��
	 */

	/**
	 * �ƶ����Խ�粻����
	 */
	@Override
	protected boolean isOutBound(double x, double y) {
		return false;
	}

}
