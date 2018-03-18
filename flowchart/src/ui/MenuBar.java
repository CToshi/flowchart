package ui;

import java.io.File;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MenuBar extends javafx.scene.control.MenuBar {
	private Menu fileMenu;

	public MenuBar() {
		fileMenu = new Menu("�ļ�");
		MenuItem newMenuItem = new MenuItem("�½��ļ�");
		MenuItem openMenuItem = new MenuItem("���ļ�");
		MenuItem saveMenuItem = new MenuItem("����");
		MenuItem saveAsMenuItem = new MenuItem("���Ϊ");

		openMenuItem.setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(new Stage());
			System.out.println(file.getPath());
		});

		saveAsMenuItem.setOnAction(e->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setInitialDirectory(new File("C:/Users/ASUS/Desktop"));
			File file = directoryChooser.showDialog(new Stage());
			System.out.println(file.getPath());
		});

		fileMenu.getItems().addAll(newMenuItem, openMenuItem,saveMenuItem,saveAsMenuItem);

		this.getMenus().addAll(fileMenu);
	}
}
