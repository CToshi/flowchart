package ui;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * �˵���
 *
 * @author Toshi
 *
 */
public class MenuBar extends javafx.scene.control.MenuBar {
	private Menu fileMenu;
	private File target;
	private QzyFileManager manager = QzyFileManager.getInstance();

	public MenuBar() {
		fileMenu = new Menu("�ļ�");
		MenuItem newMenuItem = new MenuItem("�½��ļ�");
		MenuItem openMenuItem = new MenuItem("���ļ�");
		MenuItem saveMenuItem = new MenuItem("����");
		MenuItem saveAsMenuItem = new MenuItem("���Ϊ");
//		saveAsMenuItem.setDisable(true);
		newMenuItem.setOnAction(event -> {
			FileChooser fileChooser = getFileChooser();
			target = fileChooser.showSaveDialog(new Stage());
			if(target != null) {
				try {
					target.delete();
					target.createNewFile();
					manager.importFile(null);
					saveAsMenuItem.setDisable(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		openMenuItem.setOnAction(event -> {
			FileChooser fileChooser = getFileChooser();
			target = fileChooser.showOpenDialog(new Stage());
			if(target != null){
				manager.importFile(target);
				saveAsMenuItem.setDisable(false);

			}
		});

		saveMenuItem.setOnAction(event -> {
			if (target == null || !target.exists()) {
				target = getFileChooser().showSaveDialog(new Stage());
			}
			if(target != null){
				manager.saveTo(target);
			}
		});
		saveAsMenuItem.setOnAction(event -> {
			FileChooser fileChooser = getFileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("ͼƬ", "*.png"), new ExtensionFilter("ͼƬ", "*.jpg"));
			target = fileChooser.showSaveDialog(new Stage());
			if(target != null){
				manager.saveTo(target);
			}
		});


		fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem);

		this.getMenus().addAll(fileMenu);
	}
	private FileChooser getFileChooser(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.getExtensionFilters().add(new ExtensionFilter("����ͼ", "*.qzy"));
		return fileChooser;
	}

}