package ui;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Window {

	public Window(){

		Stage window =  new Stage();
		window.setTitle("save");
	    window.initModality(Modality.APPLICATION_MODAL);
	    window.setMinWidth(300);
	    window.setMinHeight(150);

	    BorderPane pane =  new BorderPane();
	    Label message = new Label("请选择要保存的路径:");
	    pane.setTop(message);
	    Label catalog = new Label("目录:");
	    Button button = new Button("浏览");

	    DirectoryChooser directoryChooser = new DirectoryChooser();
	    button.setOnAction(e->{
//	    	File selectedFile = directoryChooser.showDialog(window);
	    	directoryChooser.setInitialDirectory(new File("C://"));
	    	File selectedFile = directoryChooser.getInitialDirectory();
	    	System.out.println(selectedFile.getPath());
	    });

	    HBox hBox = new HBox();
	    hBox.getChildren().addAll(catalog,button);
	    pane.setCenter(hBox);

	    Scene scene = new Scene(pane);
	    window.setScene(scene);
	    window.showAndWait();
	}

}
