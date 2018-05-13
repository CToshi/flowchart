package utility;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;

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

	public static void saveToImage(Node node, String fullPath) {
		WritableImage image = node.snapshot(new SnapshotParameters(), null);
		try {
			File file = new File(fullPath);
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), fullPath.substring(fullPath.indexOf(".")), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
