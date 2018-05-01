package ui;

import javafx.scene.Scene;

public class MainScene extends Scene{

	public MainScene(RootPane root, double width, double height) {
		super(root, width, height);
		this.setOnKeyPressed(key -> {
			root.keyPressed(key);
		});
		this.setOnKeyReleased(key -> {
			root.KeyReleased(key);
		});
	}



}
