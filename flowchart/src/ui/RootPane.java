package ui;

import java.util.LinkedList;

import controller.ShapeCreationController;
import factory.MoveControllerFactory;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import view.move.SyncMoveController;

/**
 * 最大的Pane
 *
 * @author Toshi
 *
 */
public class RootPane extends Pane {
	private ToolPane toolPane;
	private DrawPane drawPane;
	private MenuBar menuBar;
	private Pane bottomPane;
	private LinkedList<KeyCode> keyList;
	private LinkedList<KeyListener> keyListeners;

	public RootPane(ReadOnlyDoubleProperty stageWidth, ReadOnlyDoubleProperty stageHeight) {
		this.prefHeightProperty().bind(stageWidth);

		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		keyListeners = new LinkedList<>();

		bottomPane = new Pane();

		menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(this.widthProperty());
		this.getChildren().addAll(bottomPane, menuBar);
		final double TOOL_PANE_WIDTH = 100;
		toolPane = new ToolPane(this, TOOL_PANE_WIDTH,
				this.heightProperty().subtract(menuBar.heightProperty()));
		drawPane = new DrawPane(this, this.widthProperty().subtract(TOOL_PANE_WIDTH),
				this.heightProperty().subtract(menuBar.heightProperty()));
		bottomPane.layoutYProperty().bind(menuBar.heightProperty());
		drawPane.layoutXProperty().bind(toolPane.widthProperty());
		bottomPane.getChildren().addAll(drawPane, toolPane.getPane());
		keyList = new LinkedList<>();
		ShapeCreationController.getInstance().setDrawPane(drawPane);
		ShapeCreationController.getInstance().setToolPane(toolPane);;
		MoveControllerFactory.setDrawPane(drawPane);
		SyncMoveController.setDrawPane(drawPane);
		QzyFileManager.setDrawPane(drawPane);
	}

//	public void addToDrawPane(MoveController moveController) {
//		drawPane.add(moveController);
//	}
//
//	public void addToDrawPane(Node... nodes) {
//		drawPane.add(nodes);
//	}

	public DrawPane getDrawPane() {
		return drawPane;
	}

	public ToolPane getToolPane() {
		return toolPane;
	}

	/**
	 * 检测当前是否有某个按键组合
	 *
	 * @param keyCodes
	 * @return
	 */
	public boolean hasKey(KeyCode... keyCodes) {
		if (keyList.size() != keyCodes.length) {
			return false;
		}
		for (int i = 0; i < keyCodes.length; i++) {
			if (keyCodes[i] != keyList.get(i)) {
				return false;
			}
		}
		return true;
	}

	public void keyPressed(KeyEvent key) {
		if (!keyList.contains(key.getCode())) {
			keyList.add(key.getCode());
		}
		KeyCode[] codes = keyList.toArray(new KeyCode[0]);
		for (KeyListener listener : keyListeners) {
			if (listener.equals(codes)) {
				listener.run();
			}
		}
	}

	public void KeyReleased(KeyEvent key) {
		keyList.remove(key.getCode());
	}

	public void add(KeyListener listener) {
		keyListeners.add(listener);
	}
}