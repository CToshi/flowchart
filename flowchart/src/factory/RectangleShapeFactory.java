package factory;

import application.Main;
import entities.PointEntity;
import javafx.scene.paint.Color;
import view.shape.RectangleShape;
import view.shape.RoundedRectangleShape;

public class RectangleShapeFactory {
	public static enum TYPE {
		DEFAULT, ROUDED
	};

	public static RectangleShape create(PointEntity center) {
		return create(center.getX(), center.getY());
	}
	public static RectangleShape create(PointEntity center, TYPE type) {
		return create(center.getX(), center.getY(), type);
	}

	public static RectangleShape create(double x, double y) {
		double width = 200;
		double height = 100;

		return new RectangleShape(x - width / 2f, y - height / 2f, width, height, Color.WHITE);
	}

	public static RectangleShape create(double x, double y, TYPE type) {
		RectangleShape res = create(x, y);
		if (type == TYPE.ROUDED) {
			res = new RoundedRectangleShape(res);
		}
		return res;
	}
}
