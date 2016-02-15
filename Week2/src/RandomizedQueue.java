import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private int index;
	private Item[] items;

	public static void main(String[] args) {
		RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		for (int i = 0; i < 5; i++)
			rq.enqueue(i);
		Iterator<Integer> it1 = rq.iterator();
		while (it1.hasNext())
			System.out.println(it1.next());
		Iterator<Integer> it2 = rq.iterator();
		System.out.println("fsfsf");
		while (it2.hasNext())
			System.out.println(it2.next());
	}

	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new RandomIterator();
	}

	public RandomizedQueue() {
		items = (Item[]) new Object[1];
		index = 0;
	}

	public boolean isEmpty() {
		return index == 0;
		// is the queue empty?
	}

	public int size() {
		return index;
		// return the number of items on the queue
	}

	public void enqueue(Item item) {
		checkForNull(item);
		items[index++] = item;
		if (index == items.length)
			resize(2 * index);
	}

	public Item dequeue() {
		checkForEmpty();
		int removeIndex = (int) StdRandom.uniform(0, index);
		Item toReturn = items[removeIndex];
		items[removeIndex] = items[--index];
		items[index] = null;
		if (index > 0 && index == items.length / 4)
			resize(items.length / 2);
		return toReturn;
		// remove and return a random item
	}

	public Item sample() {
		checkForEmpty();
		int removeIndex = (int) StdRandom.uniform(0, index);
		return items[removeIndex];
	}

	private void resize(int capacity) {
		Item[] temp = (Item[]) new Object[capacity];
		for (int i = 0; i < index; i++) {
			temp[i] = items[i];
		}
		items = temp;
	}

	private void checkForNull(Item item) {
		if (item == null)
			throw new java.lang.NullPointerException(
					"Cannot push null objects in the queue");

	}

	private void checkForEmpty() {
		if (index == 0)
			throw new java.util.NoSuchElementException(
					"Cannot remove from empty queue");
	}

	private class RandomIterator implements Iterator<Item> {
		private int i = index;
		private int[] order;

		public RandomIterator() {
			order = new int[i];
			for (int j = 0; j < i; ++j) {
				order[j] = j;
			}
			StdRandom.shuffle(order);
		}

		public boolean hasNext() {
			return i > 0;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (!hasNext())
				throw new java.util.NoSuchElementException();
			return items[order[--i]];
		}
	}

}
