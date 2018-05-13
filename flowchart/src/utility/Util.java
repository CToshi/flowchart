package utility;

import java.util.LinkedList;

import entities.PointEntity;
import javafx.scene.shape.Polygon;
import view.shape.ArrowShape;

public class Util {
	public static <T> LinkedList<T> getList(T t) {
		LinkedList<T> list = new LinkedList<>();
		list.add(t);
		return list;
	}

	public static <T> LinkedList<T> getList(T[] ts) {
		LinkedList<T> list = new LinkedList<>();
		for (T t : ts) {
			list.add(t);
		}
		return list;
	}

	public static boolean isEquals(double a, double b) {
		return isEquals(a, b, 0.001);
	}

	public static boolean isEquals(double a, double b, double eps) {
		return Math.abs(a - b) < eps;
	}

}
