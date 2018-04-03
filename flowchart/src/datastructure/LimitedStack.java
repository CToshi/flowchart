package datastructure;

import java.util.LinkedList;
import java.util.Map.Entry;

import view.move.MoveFrame;

/**
 * 限制大小的栈，在元素超过最大数量maxSize时会自动删去栈底元素
 *
 * @author Toshi
 *
 * @param <K,
 *            V> 数据类型
 */
public class LimitedStack<K, V> {
	private LinkedList<Node<K, V>> list;

	static class Node<K, V> implements Entry<K, V> {

		private K key;
		private V value;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			return this.value = value;
		}

	}

	private int maxSize;

	public LimitedStack() {
		this(10);
	}

	public LimitedStack(int maxSize) {
		this.maxSize = maxSize;
		list = new LinkedList<>();
	}

	public void push(K key, V value) {
		list.addLast(new Node<K, V>(key, value));
		if (list.size() > maxSize) {
			list.pollFirst();
		}
	}

	public Node<K, V> pop() {
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
