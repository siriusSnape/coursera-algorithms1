import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;

public class Subset {

	public static void main(String[] args) {
		int k = Integer.parseInt(args[0]);
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		while (!StdIn.isEmpty())
			queue.enqueue((StdIn.readString()));
		Iterator<String> iterator = queue.iterator();
		while (k > 0) {
			System.out.println(queue.dequeue());
			k--;
		}

	}

}
