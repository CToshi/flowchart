package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.RootPane;

public class Main extends Application {
	/**
	 * 4个test函数仅为调试程序时方便输出使用
	 * @param a
	 */
	public static <T> void test(T a){
		System.out.println(a);
	}
	public static <T, T2> void test(T a, T2 b){
		System.out.println(a + " " + b);
	}
	public static <T, T2, T3> void test(T a, T2 b, T3 c){
		System.out.println(a + " " + b + " " + c);
	}
	public static <T> void test(T...ts){
		for(T t:ts){
			System.out.print(t + " ");
		}
		System.out.println();
	}

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
