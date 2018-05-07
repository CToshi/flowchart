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
	 * 会将监听器设置在返回的Node上
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
	 * 鼠标按下后会执行该函数
	 *
	 * @param mouse
	 *            当前鼠标
	 */
	protected abstract void whenPressed(MouseEvent mouse);

	/**
	 * 鼠标弹起后会执行该函数
	 *
	 * @param mouse
	 *            当前鼠标
	 */
	protected abstract void whenReleased(MouseEvent mouse);

	/**
	 * 当鼠标处于拖动状态时，每次鼠标移动都会调用此函数
	 *
	 * @param xDelta
	 *            此次移动的x变化量
	 * @param yDelta
	 *            此次移动的y变化量
	 */
	protected abstract void whenDragged(double xDelta, double yDelta);

	/**
	 * 鼠标移动到图形上时会调用这个函数
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
	 * @return 鼠标当前坐标
	 */
	public PointEntity getMousePosition() {
		return mousePosition;
	}

	/**
	 *
	 * @return 鼠标拖动时的上一次坐标
	 */
	public PointEntity getLastPosition() {
		return lastPosition;
	}

	/**
	 *
	 * @return 鼠标开始拖动时的坐标，即点击时坐标
	 */
	public PointEntity getStartPosition() {
		return startPosition;
	}
}
