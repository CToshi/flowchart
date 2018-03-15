package manager;

import java.util.HashMap;
import java.util.Map.Entry;

import view.DrawElement;

public class DrawManager {
//	private static DrawManager manager;
	private HashMap<Integer, DrawElement> elements;
//	static {
//		manager = new DrawManager();
//	}

//	private DrawManager() {
//		drawables = new HashMap<>();
//	}
	public DrawManager(){
		elements = new HashMap<>();
	}

//	public static DrawManager getInstance() {
//		return manager;
//	}

	public void add(DrawElement element) {
		elements.put(element.hashCode(), element);
	}
	public void setOutBound(boolean isOutBound){
		for(Entry<Integer, DrawElement> entry:elements.entrySet()){
			entry.getValue().setOutBound(isOutBound);
		}
	}
}
