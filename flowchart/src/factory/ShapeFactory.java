package factory;

import application.Main;
import entities.PointEntity;
import entities.ShapeState.Type;
import view.shape.Diamond;
import view.shape.Parallelogram;
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

	/**
	 *
	 * @param x
	 *            ���ĵ��x����
	 * @param y
	 *            ���ĵ��y����
	 * @param type
	 * @return
	 */
	public static ShapeItem create(double x, double y, boolean isCenter, Type type) {
		double width = DEFAULT_WIDTH;
		double height = DEFAULT_HEIGHT;
		if (isCenter) {
			x -= width / 2f;
			y -= height / 2f;
		}
		switch (type) {
		case RECTANGLE:
			return new RectangleShape(x, y, width, height);
		case ROUNDED_RECTANGLE:
			return new RoundedRectangleShape(x, y, width, height);
		case DIAMOND:
			return new Diamond(x, y, width, height);
		case PARALLELOGRAM:
			return new Parallelogram(x, y, width, height);
		default:
			Main.test("��û��");
			return null;
		}
	}

	public static ShapeItem create(double x, double y, Type type) {
		return create(x, y, false, type);
	}

}
