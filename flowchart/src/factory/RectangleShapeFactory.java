package factory;

import entities.PointEntity;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import view.shape.RectangleShape;
import view.shape.RoundedRectangleShape;

public class RectangleShapeFactory {
	public static enum TYPE {
		DEFAULT, ROUDED
	};

//	private static final Color DEFAULT_COLOR = Color.BLACK;

	public static RectangleShape create(PointEntity center) {
		return create(center.getX(), center.getY());
	}

	public static RectangleShape create(PointEntity center, TYPE type) {
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
	public static RectangleShape create(double x, double y, boolean isCenter, TYPE type) {
		RectangleShape res = create(x, y, isCenter);
		if (type == TYPE.ROUDED) {
			res = new RoundedRectangleShape(res);
		}
		return res;
	}

}
