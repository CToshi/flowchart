package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;

public class Main extends Application {
	@Override
	public void start(Stytage primaryStage) {
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
		Canvas canvas;
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
