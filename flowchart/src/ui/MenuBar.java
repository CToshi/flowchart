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
		fileMenu = new Menu("文件");
		MenuItem newMenuItem = new MenuItem("新建文件");
		MenuItem openMenuItem = new MenuItem("打开文件");
		MenuItem saveMenuItem = new MenuItem("保存");
		MenuItem saveAsMenuItem = new MenuItem("另存为");

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
