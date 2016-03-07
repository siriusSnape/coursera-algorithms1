import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Board {

	private int[][] blocks;
	private int blankx, blanky;
	private int dimension;
	public Board(int[][] blocks) {
		// construct a board from an N-by-N array of blocks
		if (null == blocks)
			throw new java.lang.NullPointerException();
		this.blocks = clonedBlocks(blocks);
		dimension = blocks.length;
		for (int i = 0; i < blocks.length; i++) {
			for (int j = 0; j < blocks[0].length; j++) {
				if (blocks[i][j] == 0) {
					blankx = i;
					blanky = j;
					i = dimension;
					break;
				}
			}
		}
	}

	// (where blocks[i][j] = block in row i, column j)
	public int dimension() {
		return dimension;
	}

	public int hamming() {
		int count = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != 0
						&& blocks[i][j] != i * dimension + j + 1)
					count++;
			}
		}
		return count;
	}

	public int manhattan() {
		int count = 0;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (blocks[i][j] != 0) {
					int xgoal = (blocks[i][j] - 1) / dimension;
					int ygoal = (blocks[i][j] - 1) % dimension;
					count += Math.abs(i - xgoal) + Math.abs(j - ygoal);
				}
			}
		}
		return count;
	}

	public boolean isGoal() {
		int N = dimension;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (blocks[i][j] != 0
						&& blocks[i][j] != i * N + j + 1)
					return false;
			}
		}
    	return true;
	}

	public Board twin() {
		int[][] newBoard = clonedBlocks();
		boolean flag = false;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension - 1; j++) {
				if (newBoard[i][j] > 0 && newBoard[i][j + 1] > 0) {
					int tmp = newBoard[i][j];
					newBoard[i][j] = newBoard[i][j + 1];
					newBoard[i][j + 1] = tmp;
					flag = true;
					break;
				}
			}
			if (flag)
				break;
		}
		return new Board(newBoard);

	}

	public boolean equals(Object y) {
		if (y == null)
			return false;
		if(!(y instanceof Board))
			return false;
		Board other = (Board) y;
		if (other.dimension != this.dimension)
			return false;
		for (int i = 0; i < other.dimension; i++) {
			for (int j = 0; j < other.dimension; j++) {
				if (other.blocks[i][j] != this.blocks[i][j])
					return false;
			}
		}
		return true;
	}

	public Iterable<Board> neighbors() {
		List<Board> results = new ArrayList<Board>();
		int[] di = new int[] { 1, -1, 0, 0 };
		int[] dj = new int[] { 0, 0, 1, -1 };
		for (int i = 0; i < 4; i++) {
			int newI = blankx + di[i];
			int newY = blanky + dj[i];
			if (newI >= 0 && newI < blocks.length && newY >= 0
					&& newY < blocks.length) {
				Board neighbor = new Board(blocks);
				neighbor.blocks[blankx][blanky] = neighbor.blocks[newI][newY];
				neighbor.blocks[newI][newY] = 0;
				neighbor.blankx = newI;
				neighbor.blanky = newY;
				results.add(neighbor);
			}
		}
		return results;
	}

	public String toString() { // string representation of this board (in the
		String output = Integer.toString(dimension()) + "\n";
		for (int i = 0; i < dimension(); i++) {
			for (int j = 0; j < dimension(); j++) {
				output += Integer.toString(blocks[i][j]) + " ";// output format
																// specified
																// below)
			}
			output = output.trim() + "\n";
		}
		return output;

	}

	public static void main(String[] args) { // unit tests (not graded)
		int[][] board = new int[][] { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };
		Board b = new Board(board);
		System.out.println(b.twin().isGoal());
		Iterator<Board> it = b.neighbors().iterator();
		while (it.hasNext()) {
			System.out.println(it.next());

		}
		System.out.println(b.manhattan());
	}

	private int[][] clonedBlocks() {
		int[][] copy = new int[dimension()][];
		for (int i = 0; i < dimension(); i++)
			copy[i] = Arrays.copyOf(blocks[i], blocks.length);
		return copy;
	}

	private int[][] clonedBlocks(int[][] original) {
		int[][] copy = new int[original.length][];
		for (int i = 0; i < original.length; i++)
			copy[i] = Arrays.copyOf(original[i], original.length);
		return copy;
	}
}
