package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import entities.DrawableState;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Border;

public class QzyFileManager {

	private DrawPane drawPane;
	private static QzyFileManager qzyFileManager = new QzyFileManager();

	private QzyFileManager() {
	}

	public static QzyFileManager getInstance() {
		return qzyFileManager;
	}

	public static void setDrawPane(DrawPane drawPane) {
		getInstance().drawPane = drawPane;
	}

	public void saveTo(File file) {
		if (file.getPath().endsWith(".qzy")) {
			try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
				oos.writeObject(drawPane.getAllState());
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			saveToImage(file);
		}
	}

	private void saveToImage(File file) {
		SnapshotParameters parameters = new SnapshotParameters();
		// parameters.setFill(Color.BLACK);
		Border border = drawPane.getBorder();
		drawPane.setBorder(null);
		WritableImage image = drawPane.snapshot(parameters, null);
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		drawPane.setBorder(border);
	}

	@SuppressWarnings("unchecked")
	public void importFile(File file) {
		if (file == null) {
			drawPane.importStates(null);
		} else {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
				LinkedList<DrawableState> list = (LinkedList<DrawableState>) ois.readObject();
				drawPane.importStates(list);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}