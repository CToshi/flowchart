package ui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
/**
 * �˵���
 * @author Toshi
 *
 */
public class MenuBar extends javafx.scene.control.MenuBar {
	private Menu fileMenu;

	public MenuBar() {
		fileMenu = new Menu("�ļ�");
		MenuItem newMenuItem = new MenuItem("�½��ļ�");
		MenuItem openMenuItem = new MenuItem("���ļ�");

		fileMenu.getItems().addAll(newMenuItem, openMenuItem);

		this.getMenus().addAll(fileMenu);
	}
}
