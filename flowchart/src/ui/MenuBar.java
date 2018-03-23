package ui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
/**
 * 菜单栏
 * @author Toshi
 *
 */
public class MenuBar extends javafx.scene.control.MenuBar {
	private Menu fileMenu;

	public MenuBar() {
		fileMenu = new Menu("文件");
		MenuItem newMenuItem = new MenuItem("新建文件");
		MenuItem openMenuItem = new MenuItem("打开文件");

		fileMenu.getItems().addAll(newMenuItem, openMenuItem);

		this.getMenus().addAll(fileMenu);
	}
}
