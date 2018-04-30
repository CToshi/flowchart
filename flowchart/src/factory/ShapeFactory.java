package factory;

import entities.PointEntity;
import javafx.scene.paint.Color;
import view.shape.RectangleShape;
import view.shape.RoundedRectangleShape;

public class ShapeFactory {
	public static enum Type {
		DEFAULT, ROUDED
	};

	// private static final Color DEFAULT_COLOR = Color.BLACK;


	public static RectangleShape create(PointEntity center) {
		return create(center.getX(), center.getY());
	}

	public static RectangleShape create(Type type) {
		return create(0, 0, false, type);
	}

	public static RectangleShape create(PointEntity center, Type type) {
		return create(center.getX(), center.getY(), true, type);
	}

	public static RectangleShape create(double x, double y) {
		return create(x, y, true);
	}

	public static RectangleShape create(double x, double y, boolean isCenter) {
		double width = 200;
		double height = 100;
		if (isCenter) {
			x -= width / 2f;
			y -= height / 2f;
		}
		return new RectangleShape(x, y, width, height, Color.WHITE);
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
	public static RectangleShape create(double x, double y, boolean isCenter, Type type) {
		RectangleShape res = create(x, y, isCenter);
		if (type == Type.ROUDED) {
			res = new RoundedRectangleShape(res);
		}
		return res;
	}

}
