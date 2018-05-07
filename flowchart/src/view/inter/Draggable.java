package view.inter;

import entities.PointEntity;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public abstract class Draggable{
	private PointEntity lastPosition;
	private PointEntity startPosition;
	private PointEntity mousePosition;

	public Draggable() {
		lastPosition = new PointEntity();
		startPosition = new PointEntity();
		mousePosition = new PointEntity();
		initListener();
	}

	/**
	 * �Ὣ�����������ڷ��ص�Node��
	 * @return
	 */
	protected abstract Node getNode();

	private void initListener() {
		getNode().setOnMousePressed(e -> {
			lastPosition.setXY(e.getX(), e.getY());
			startPosition.setXY(e.getX(), e.getY());
			whenPressed(e);
		});
		getNode().setOnMouseDragged(e -> {
			mousePosition.setXY(e.getX(), e.getY());
			double xDelta = e.getX() - lastPosition.getX();
			double yDelta = e.getY() - lastPosition.getY();
			this.whenDragged(xDelta, yDelta);
			lastPosition.setXY(e.getX(), e.getY());
		});
		getNode().setOnMouseReleased(e -> {
			whenReleased(e);
		});
		getNode().setOnMouseMoved(e->{
			whenMoved(e);
		});
	}

	/**
	 * ��갴�º��ִ�иú���
	 *
	 * @param mouse
	 *            ��ǰ���
	 */
	protected abstract void whenPressed(MouseEvent mouse);

	/**
	 * ��굯����ִ�иú���
	 *
	 * @param mouse
	 *            ��ǰ���
	 */
	protected abstract void whenReleased(MouseEvent mouse);

	/**
	 * ����괦���϶�״̬ʱ��ÿ������ƶ�������ô˺���
	 *
	 * @param xDelta
	 *            �˴��ƶ���x�仯��
	 * @param yDelta
	 *            �˴��ƶ���y�仯��
	 */
	protected abstract void whenDragged(double xDelta, double yDelta);

	/**
	 * ����ƶ���ͼ����ʱ������������
	 *
	 * @param mouse
	 */
	protected abstract void whenMoved(MouseEvent mouse);
	// public void setCenterXY(double x, double y) {
	// this.setCenterX(x);
	// this.setCenterY(y);
	// }
	//
	// public void setCenterX(double value) {
	// this.setX(value - this.getWidth() / 2f);
	// }
	//
	// public void setCenterY(double value) {
	// this.setY(value - this.getHeight() / 2f);
	// }
	//
	// public double getCenterX() {
	// return this.getX() + this.getWidth() / 2f;
	// }
	//
	// public double getCenterY() {
	// return this.getY() + this.getHeight() / 2f;
	// }

	/**
	 *
	 * @return ��굱ǰ����
	 */
	public PointEntity getMousePosition() {
		return mousePosition;
	}

	/**
	 *
	 * @return ����϶�ʱ����һ������
	 */
	public PointEntity getLastPosition() {
		return lastPosition;
	}

	/**
	 *
	 * @return ��꿪ʼ�϶�ʱ�����꣬�����ʱ����
	 */
	public PointEntity getStartPosition() {
		return startPosition;
	}
}
