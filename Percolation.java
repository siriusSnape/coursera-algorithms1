import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private WeightedQuickUnionUF weightedQuickUnionUF;
	private boolean[][] grid;
	private WeightedQuickUnionUF backWashWeightedQuickUnionUF;

	public Percolation(int N) {
		if (N <= 0)
			throw new java.lang.IllegalArgumentException();
		weightedQuickUnionUF = new WeightedQuickUnionUF(N * N + 2);
		backWashWeightedQuickUnionUF = new WeightedQuickUnionUF(N * N + 1);
		grid = new boolean[N][];
		for (int i = 0; i < N; i++) {
			{
				grid[i] = new boolean[N];
			}
		}

	}

	private int getIndex(int i, int j) {
		return grid.length * (i - 1) + j;
	}

	private boolean isValid(int i, int j) {
		return (i > 0 && j > 0 && i <= grid.length && j <= grid.length);
	}

	public void open(int i, int j) {
		if (!isValid(i, j)) {
			throw new IndexOutOfBoundsException();
		}
		if (!isOpen(i, j)) {
			grid[i - 1][j - 1] = true;
			int p = getIndex(i, j), q;
			if (isValid(i - 1, j) && isOpen(i - 1, j)) {
				q = getIndex(i - 1, j);
				weightedQuickUnionUF.union(p, q);
				backWashWeightedQuickUnionUF.union(p, q);
			}
			if (isValid(i + 1, j) && isOpen(i + 1, j)) {
				q = getIndex(i + 1, j);
				weightedQuickUnionUF.union(p, q);
				backWashWeightedQuickUnionUF.union(p, q);
			}
			if (isValid(i, j - 1) && isOpen(i, j - 1)) {
				q = getIndex(i, j - 1);
				weightedQuickUnionUF.union(p, q);
				backWashWeightedQuickUnionUF.union(p, q);
			}
			if (isValid(i, j + 1) && isOpen(i, j + 1)) {
				q = getIndex(i, j + 1);
				weightedQuickUnionUF.union(p, q);
				backWashWeightedQuickUnionUF.union(p, q);
			}
			if (i == 1) {
				weightedQuickUnionUF.union(0, p);
				backWashWeightedQuickUnionUF.union(0, p);
			}
			if (i == grid.length) {
				weightedQuickUnionUF.union(i * i + 1, p);
			}
		}
	}

	public boolean isOpen(int i, int j) {
		if (!isValid(i, j)) {
			throw new IndexOutOfBoundsException();
		}
		return grid[i - 1][j - 1];
	}

	public boolean isFull(int i, int j) {
		if (!isValid(i, j)) {
			throw new IndexOutOfBoundsException();
		}
		int index = getIndex(i, j);
		return weightedQuickUnionUF.connected(0, index)
				&& backWashWeightedQuickUnionUF.connected(0, index);
	}

	public boolean percolates() {
		return weightedQuickUnionUF.connected(0, grid.length * grid.length + 1);
	}

	public static void main(String[] args) {
	}
}
