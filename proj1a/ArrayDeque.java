public class ArrayDeque<T> {
	private T[] items;
	private int size;
	private int nextFirst;
	private int nextLast;

	public ArrayDeque() {
		items = (T[]) new Object[8];
		size = 0;
		nextFirst = 7;
		nextLast = 0;
	}

	/** Helper function: Resizing the underlying array to the target capacity. */
	private void resize(int capacity) {
		T[] a = (T[]) new Object[capacity];

		if (nextFirst + 1 <= nextLast - 1) {
			// Case: First, ..., Last
			System.arraycopy(items, nextFirst + 1, a, 0, size);
		} else {
			// Case: First, ..., items.length - 1, 0, ..., Last
			System.arraycopy(items, nextFirst + 1, a, 0, items.length - nextFirst - 1);
			System.arraycopy(items, 0, a,items.length - nextFirst - 1, size - items.length + nextFirst + 1);
		}
		// Reset nextFirst and nextLast for the resized array
		nextFirst = items.length - 1;
		nextLast = size;
		items = a;
	}

	/** Helper functions: Indexing in circular array */
	public int addOne(int x) {
		if (x == items.length - 1) {
			return 0;
		}
		return x++;
	}

	public int minusOne(int x) {
		if (x == 0) {
			return items.length - 1;
		}
		return x--;
	}

	public void addFirst(T item) {
		if (size == items.length) {
			resize(size * 2);
		}
		items[nextFirst] = item;
		minusOne(nextFirst);
		size++;
	}

	public void addLast(T item) {
		if (size == items.length) {
			resize(size * 2);
		}

		items[nextLast] = item;
		addOne(nextLast);
		size++;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public void printDeque() {
		int i = 0;
		while (i < size) {
			System.out.print(get(i)+" ");
			i++;
		}
	}

	public T removeFirst() {
		if (size == 0) {
			return null;
		}
		T x = get(nextFirst + 1);
		items[nextFirst + 1] = null;
		addOne(nextFirst);
		size--;
		if (Double.valueOf(size) / Double.valueOf(items.length) < 0.25) {
			resize(items.length / 2);
		}
		return x;
	}

	public T removeLast() {
		if (size == 0) {
			return null;
		}
		T x = get(nextLast - 1);
		items[nextLast - 1] = null;
		minusOne(nextLast);
		size--;
		if (Double.valueOf(size) / Double.valueOf(items.length) < 0.25) {
			resize(items.length / 2);
		}
		return x;
	}

	public T get(int index) {
		if (index >= size) {
			return null;
		}
		if (nextFirst + 1 + index < items.length) {
			return items[nextFirst + 1 + index];
		} else {
			return items[nextFirst + 1 + index - items.length];
		}
	}
}