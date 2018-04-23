package datastructure;

import java.util.LinkedList;
import java.util.Map.Entry;

import javafx.util.Pair;

/**
 * ���ƴ�С��ջ����Ԫ�س����������maxSizeʱ���Զ�ɾȥջ��Ԫ��
 *
 * @author Toshi
 *
 * @param <K,
 *            V> ��������
 */
public class LimitedStack<K, V> {
	private LinkedList<Pair<K, V>> list;

	private int maxSize;

	public LimitedStack() {
		this(10);
	}

	public LimitedStack(int maxSize) {
		this.maxSize = maxSize;
		list = new LinkedList<>();
	}

	public void push(K key, V value) {
		list.addLast(new Pair<K, V>(key, value));
		if (list.size() > maxSize) {
			list.pollFirst();
		}
	}

	public Pair<K, V> pop() {
		return list.pollLast();
	}

	public int size() {
		return list.size();
	}

	public void push(Entry<K, V> entry) {
		push(entry.getKey(), entry.getValue());
	}

	public void clear(){
		list.clear();
	}
}
