package factory;

import application.Main;
import entities.DrawableState.Type;
import entities.PointEntity;
import view.shape.ArrowShape;
import view.shape.Diamond;
import view.shape.Parallelogram;
import view.shape.PolygonalArrowShape;
import view.shape.RectangleShape;
import view.shape.RoundedRectangleShape;
import view.shape.ShapeItem;

public class ShapeFactory {

	private static final double DEFAULT_WIDTH = 200;
	private static final double DEFAULT_HEIGHT = 100;
	private static final double DEFAULT_ARROW_LENGTH = 100;

	public static ShapeItem create(Type type) {
		return create(0, 0, false, type);
	}

	public static ShapeItem create(PointEntity center, Type type) {
		return create(center.getX(), center.getY(), true, type);
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
		double width = DEFAULT_WIDTH;
		double height = DEFAULT_HEIGHT;
		if(type == Type.ARROW){
			width = DEFAULT_ARROW_LENGTH;
			height = 0;
		}
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
		case ARROW:
			return new ArrowShape(new PointEntity(x,y),DEFAULT_ARROW_LENGTH);
		case POLYGONAL_ARROW:
			return new PolygonalArrowShape(new PointEntity(x,y),new PointEntity(x+width,y+height));
		default:
			Main.test("还没做");
			return null;
		}
	}

	public static ShapeItem create(double x, double y, Type type) {
		return create(x, y, false, type);
	}
}
