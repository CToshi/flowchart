package factory;

import entities.PointEntity;
import entities.ShapeState.Type;
import view.shape.Diamond;
import view.shape.RectangleShape;
import view.shape.RoundedRectangleShape;
import view.shape.ShapeItem;

public class ShapeFactory {

	private static final double DEFAULT_WIDTH = 200;
	private static final double DEFAULT_HEIGHT = 100;

	public static ShapeItem create(Type type) {
		return create(0, 0, false, type);
	}

	public static ShapeItem create(PointEntity center, Type type) {
		return create(center.getX(), center.getY(), true, type);
	}

	public static ShapeItem create(double x, double y, Type type) {
		return create(x, y, false, type);
	}

	/**
	 *
	 * @param x
	 *            中心点的x坐标
	 * @param y
	 *            中心点的y坐标
	 * @param type
	 * @return
	 */
	public static ShapeItem create(double x, double y, boolean isCenter, Type type) {
		if (isCenter) {
			x -= DEFAULT_WIDTH / 2f;
			y -= DEFAULT_HEIGHT / 2f;
		}
		switch (type) {
		case RECTANGLE:
			return new RectangleShape(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		case ROUNDED_RECTANGLE:
			return new RoundedRectangleShape(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		case DIAMOND:
			return new Diamond(x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		default:
			return null;
		}
	}

}
