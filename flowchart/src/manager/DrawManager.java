package manager;

import java.util.HashMap;
import java.util.Map.Entry;

import entities.PointEntity;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import view.Selectable;

public class DrawManager {
	private static DrawManager manager;
	private HashMap<String, Selectable> selectables;
	private EventHandler<MouseEvent> handler;
	static {
		manager = new DrawManager();
	}

	private DrawManager() {
		selectables = new HashMap<>();

		handler = (e) -> {
			PointEntity point = new PointEntity(e.getX(), e.getY());
			for (Entry<String, Selectable> entry : selectables.entrySet()) {
				Selectable select = entry.getValue();
				if (select.getJudgeRectangle().contains(point)) {
					select.selected();
				}
			}
		};
	}

	public EventHandler<MouseEvent> getHandler() {
		return handler;
	}

	public static DrawManager getInstance() {
		return manager;
	}
}
