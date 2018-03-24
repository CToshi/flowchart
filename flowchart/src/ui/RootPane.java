package ui;

import java.util.LinkedList;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import view.move.MoveFrame;

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

	public RootPane(ReadOnlyDoubleProperty stageWidth, ReadOnlyDoubleProperty stageHeight) {
		this.prefHeightProperty().bind(stageWidth);

		this.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

		bottomPane = new Pane();
		toolPane = new ToolPane(this, this.widthProperty().multiply(0.2), this.heightProperty());
		menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(this.widthProperty());
		this.getChildren().addAll(bottomPane, menuBar);
		drawPane = new DrawPane(this, this.widthProperty().multiply(0.8),
				this.heightProperty().subtract(menuBar.heightProperty()));
		bottomPane.layoutYProperty().bind(menuBar.heightProperty());
		drawPane.layoutXProperty().bind(toolPane.widthProperty());
		bottomPane.getChildren().addAll(drawPane, toolPane);

		keyList = new LinkedList<>();
		this.setOnKeyPressed(key -> {
			keyList.add(key.getCode());
		});
		this.setOnKeyReleased(key -> {
			keyList.remove(key.getCode());
		});
	}

	public void addToDrawPane(MoveFrame frame) {
		drawPane.add(frame);
	}

	public void addToDrawPane(Node... nodes) {
		drawPane.add(nodes);
	}

	public DrawPane getDrawPane() {
		return drawPane;
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
}
