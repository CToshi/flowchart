package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.RootPane;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		RootPane root = new RootPane(primaryStage.widthProperty(), primaryStage.heightProperty());

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
