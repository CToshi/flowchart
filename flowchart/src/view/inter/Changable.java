package view.inter;

import entities.RectangleEntity;

public interface Changable {

	/**
	 *
	 * @param rectangle
	 *            当前所在的矩形
	 */

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

	void setRectangle(RectangleEntity rectangle);
	// default void setRectangle(RectangleEntity rectangle) {
	// setX(rectangle.getX());
	// setY(rectangle.getY());
	// setWidth(rectangle.getWidth());
	// setHeight(rectangle.getHeight());
	// }

	// void setX(double value);
	//
	// void setY(double value);
	//
	// void setWidth(double value);
	//
	// void setHeight(double value);

	default void setX(double value) {
		RectangleEntity rectangle = getRectangle();
		rectangle.setX(value);
		setRectangle(rectangle);
	}
	default void setY(double value) {
		RectangleEntity rectangle = getRectangle();
		rectangle.setY(value);
		setRectangle(rectangle);
	}default void setWidth(double value) {
		RectangleEntity rectangle = getRectangle();
		rectangle.setWidth(value);
		setRectangle(rectangle);
	}default void setHeight(double value) {
		RectangleEntity rectangle = getRectangle();
		rectangle.setHeight(value);
		setRectangle(rectangle);
	}

}
