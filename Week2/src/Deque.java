import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node first;
	private Node last;
	private int size;

	public Deque() {
		first = null;
		size = 0;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public int size() {
		return size;// return the number of items on the deque
	}

	public void addFirst(Item item) {
		checkForNull(item);
		Node oldFirst = first;
		first = new Node();
		first.item = item;
		first.previous = null;
		first.next = oldFirst;
		if (oldFirst != null) {
			oldFirst.previous = first;
		} else {
			last = first;
		}
		size++;
	}

	private void checkForNull(Item item) {
		if (item == null)
			throw new java.lang.NullPointerException(
					"Cannot push null objects in the queue");

	}

	private void checkForEmpty() {
		if (first == null || last == null || size == 0)
			throw new java.util.NoSuchElementException(
					"Cannot remove from empty queue");

	}

	public void addLast(Item item) {
		checkForNull(item);
		Node oldLast = last;
		last = new Node();
		last.previous = oldLast;
		last.next = null;
		last.item = item;
		if (oldLast != null) {
			oldLast.next = last;
		} else {
			first = last;
		}
		size++;

	}

	public Item removeFirst() {
		checkForEmpty();
		Node toRemove = first;
		first = first.next;
		if (first == null) {
			last = null;
		} else {
			first.previous = null;
		}
		size--;
		return toRemove.item;

	}

	public Item removeLast() {
		checkForEmpty();
		Node toRemove = last;
		last = last.previous;
		if (last == null) {
			first = null;
		} else {
			last.next = null;
		}
		size--;
		return toRemove.item;
	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {

		private Node current = first;

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return (current != null);
		}

		@Override
		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException("End of Queue");
			Item item = current.item;
			current = current.next;
			return item;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();

		}

	}

	private class Node {
		private Item item;
		private Node next;
		private Node previous;
	}

	public static void main(String[] args) {
		Deque<Integer> dq = new Deque<Integer>();
		for (int i = 0; i < 10; i++)
			dq.addFirst(null);

		for (int i = 0; i < 10; i++) {
			System.out.println(dq.removeLast());
		}

	}

}
