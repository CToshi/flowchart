package utility;

import java.util.LinkedList;

public class Util {
	public static <T> LinkedList<T> getList(T t){
		LinkedList<T> list = new LinkedList<>();
		list.add(t);
		return list;
	}
	public static <T> LinkedList<T> getList(T[] ts){
		LinkedList<T> list = new LinkedList<>();
		for (T t : ts) {
			list.add(t);
		}
		return list;
	}
}
