/** Performs some basic linked list tests. */
public class LinkedListDeque<T> {
	private class Node<T> {
		public Node prev;
		public T item;
		public Node next;

		public Node(T i, Node p, Node n) {
			prev = p;
			item = i;
			next = n;
		}
	}

	/* The first item (if it exists) is at sentinel.next. */
	private Node sentinel;
	private int size;

	public LinkedListDeque() {
		sentinel = new Node("Sentinel", null, null);
		size = 0;
	}
	/*
	public LinkedListDeque(T item) {
		sentinel = new Node("Sentinel", null, null);
		sentinel.next = new Node(item, sentinel.next, sentinel.next);
		size = 1;
	}
	 */

	public void addFirst(T item) {
		if (size == 0) {
			sentinel.next = new Node(item, null, null);
			sentinel.next.next = sentinel.next;
			sentinel.next.prev = sentinel.next;
		} else {
			sentinel.next = new Node(item, sentinel.next.prev, sentinel.next);
			sentinel.next.next.prev = sentinel.next;
			sentinel.next.prev.next = sentinel.next;
		}
		size += 1;
	}

	public void addLast(T item) {
		if (size == 0) {
			sentinel.next = new Node(item, null, null);
			sentinel.next.next = sentinel.next;
			sentinel.next.prev = sentinel.next;
		} else {
			sentinel.next.prev = new Node(item, sentinel.next.prev, sentinel.next);
			sentinel.next.prev.prev.next = sentinel.next.prev;
		}
		size += 1;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void printDeque() {
		Node<T> ptr = sentinel.next;
		while (ptr.next != sentinel.next) {
			System.out.print(ptr.item+" ");
			ptr = ptr.next;
		}
		System.out.print(ptr.item+" ");
	}

	public T removeFirst() {
		if (sentinel.next == null) {
			return null;
		}
		Node<T> first = sentinel.next;
		sentinel.next.next.prev = sentinel.next.prev;
		sentinel.next.prev.next = sentinel.next.next;
		sentinel.next = sentinel.next.next;
		size -= 1;
		return first.item;
	}

	public T removeLast() {
		if (sentinel.next == null) {
			return null;
		}
		Node<T> last = sentinel.next.prev;
		sentinel.next.prev.prev.next = sentinel.next;
		sentinel.next.prev = sentinel.next.prev.prev;
		size -= 1;
		return last.item;
	}

	public T get(int index) {
		if (index >= size) {
			return null;
		}
		Node<T> ptr = sentinel.next;
		for (int i = 0; i < index; i++) {
			ptr = ptr.next;
		}
		return ptr.item;
	}

	public T getRecursive(int index) {
		if (index >= size) {
			return null;
		}
		LinkedListDeque<T> ptr = this;
		if (index == 0) {
			return ptr.get(0);
		} else {
			ptr.removeFirst();
			return ptr.getRecursive(index - 1);
		}
	}
}