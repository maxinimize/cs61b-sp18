public class LinkedListDeque<T> {
	private class Node<T> {
		private Node prev;
		private T item;
		private Node next;

		public Node(T i, Node p, Node n) {
			prev = p;
			item = i;
			next = n;
		}
	}

	private Node sentinel;
	private int size;

	/** Constructor: empty */
	public LinkedListDeque() {
		sentinel = new Node("Sentinel", null, null);
		size = 0;
	}

	/** Constructor: copy - private as a helper function for sp18 grading */
	private LinkedListDeque(LinkedListDeque<T> copy) {
		sentinel = new Node("Sentinel", null, null);
		for (int i = 0; i < copy.size(); i++) {
			this.addLast(copy.get(i));
		}
		size = copy.size();
	}

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
			System.out.print(ptr.item + " ");
			ptr = ptr.next;
		}
		System.out.print(ptr.item + " ");
	}

	public T removeFirst() {
		if (sentinel.next == null) {
			return null;
		}
		Node<T> first = sentinel.next;
		if (size == 1) {
			sentinel.next = null;
		} else {
			sentinel.next.next.prev = sentinel.next.prev;
			sentinel.next.prev.next = sentinel.next.next;
			sentinel.next = sentinel.next.next;
		}
		size -= 1;
		return first.item;
	}

	public T removeLast() {
		if (sentinel.next == null) {
			return null;
		}
		Node<T> last = sentinel.next.prev;
		if (size == 1) {
			sentinel.next = null;
		} else {
			sentinel.next.prev.prev.next = sentinel.next;
			sentinel.next.prev = sentinel.next.prev.prev;
		}
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
		LinkedListDeque<T> ptr = new LinkedListDeque<T>(this);
		if (index == 0) {
			return ptr.get(0);
		} else {
			ptr.removeFirst();
			return ptr.getRecursive(index - 1);
		}
	}
}
