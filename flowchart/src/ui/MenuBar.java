package ui;

import java.io.File;
import java.io.IOException;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * 菜单栏
 * @author Toshi
 *
 */
public class MenuBar extends javafx.scene.control.MenuBar {
	private Menu fileMenu;
	private File target;

	public MenuBar() {
		fileMenu = new Menu("文件");
		MenuItem newMenuItem = new MenuItem("新建文件");
		MenuItem openMenuItem = new MenuItem("打开文件");
		MenuItem saveMenuItem = new MenuItem("保存");
		MenuItem saveAsMenuItem = new MenuItem("另存为");

		newMenuItem.setOnAction(e->{
			File file = getCatalog();
			if(file != null){
				target = new File(file.getAbsolutePath(),"haha.txt");
				if(!target.exists()){
					try {
						target.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		openMenuItem.setOnAction(e->{
			FileChooser fileChooser = new FileChooser();
			File file = fileChooser.showOpenDialog(new Stage());
			if(file!=null){
				System.out.println(file.getPath());
			}
		});

		saveAsMenuItem.setOnAction(e->{
			DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setInitialDirectory(new File("C:/Users/ASUS/Desktop"));
			File file = directoryChooser.showDialog(new Stage());
			if(file!=null){
				System.out.println(file.getPath());
			}
		});

		saveMenuItem.setOnAction(e->{
			if(target == null || !target.exists()){
				File file = getCatalog();
				if(file != null){
					target = new File(file.getAbsolutePath(),"haha.txt");
					if(!target.exists()){
						try {
							target.createNewFile();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			QzySaver.getInstance().saveTo(target);
		});

		fileMenu.getItems().addAll(newMenuItem, openMenuItem,saveMenuItem,saveAsMenuItem);

		this.getMenus().addAll(fileMenu);
	}

	public File getCatalog(){
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(new File("C:/Users/ASUS/Desktop"));
		File file = directoryChooser.showDialog(new Stage());
		return file;
	}

}
