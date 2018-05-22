package ui;

import java.io.File;
import java.io.IOException;

import javax.swing.filechooser.FileSystemView;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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
	private Menu editMenu;
	private DrawPane drawPane;

	public MenuBar() {
		initFileMenu();
		initEditMenu();
		this.getMenus().addAll(fileMenu, editMenu);
	}
	private void initFileMenu(){
		fileMenu = new Menu("�ļ�");
		MenuItem newMenuItem = new MenuItem("�½��ļ�");
		MenuItem openMenuItem = new MenuItem("���ļ�");
		MenuItem saveMenuItem = new MenuItem("����");
		MenuItem saveAsMenuItem = new MenuItem("���Ϊ");
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
		saveMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		saveAsMenuItem.setOnAction(event -> {
			FileChooser fileChooser = getFileChooser();
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("ͼƬ", "*.png"), new ExtensionFilter("ͼƬ", "*.jpg"));
			target = fileChooser.showSaveDialog(new Stage());
			if(target != null){
				manager.saveTo(target);
			}
		});


		fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem);
	}
	private void initEditMenu(){
		editMenu = new Menu("�༭");
		MenuItem undo = new MenuItem("����");
		MenuItem redo = new MenuItem("������");
		MenuItem copy = new MenuItem("����");
		MenuItem paste = new MenuItem("ճ��");
		MenuItem selecteAll = new MenuItem("ȫѡ");
		MenuItem delete = new MenuItem("ɾ��");

		undo.setOnAction(event->{
			drawPane.unDo();
		});
		undo.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));

		redo.setOnAction(event->{
			drawPane.reDo();
		});
		redo.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));

		copy.setOnAction(event->{
			drawPane.copy();
		});
		copy.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

		paste.setOnAction(event->{
			drawPane.paste();
		});
		paste.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));

		delete.setOnAction(event->{
			drawPane.deleteAllSelected();
		});
		delete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));

		selecteAll.setOnAction(event->{
			drawPane.setAllSelected();
		});
		selecteAll.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));

		editMenu.getItems().addAll(undo, redo, copy, paste, delete, selecteAll);
	}
	private FileChooser getFileChooser(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getHomeDirectory());
		fileChooser.getExtensionFilters().add(new ExtensionFilter("����ͼ", "*.qzy"));
		return fileChooser;
	}

	public void setDrawPane(DrawPane drawPane){
		this.drawPane = drawPane;
	}

}