package view.inter;

import entities.RectangleEntity;

public interface Changable {

	/**
	 *
	 * @param rectangle
	 *            当前所在的矩形
	 */
	void setRectangle(RectangleEntity rectangle);

	RectangleEntity getRectangle();

	default double getX() {
		return getRectangle().getX();
	}

	default double getY() {
		return getRectangle().getY();
	}

	default double getWidth() {
		return getRectangle().getWidth();
	}

	default double getHeight() {
		return getRectangle().getHeight();
	}
	 void setX(double value);
	 void setY(double value);
	 void setWidth(double value);
	 void setHeight(double value);

	// default void setX(double value) {
	// getRectangle().setX(value);
	// setRectangle(getRectangle());
	// }
	//
	// default void setY(double value) {
	// getRectangle().setY(value);
	// setRectangle(getRectangle());
	// }
	//
	// default void setWidth(double value) {
	// getRectangle().setWidth(value);
	// setRectangle(getRectangle());
	// }
	//
	// default void setHeight(double value) {
	// getRectangle().setHeight(value);
	// setRectangle(getRectangle());
	// }

}
