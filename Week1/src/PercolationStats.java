package com.coursera.percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double mean;
	private double stdDev;
	private double sampleSize;

	public PercolationStats(int N, int T) {
		if (N <= 0 || T <= 0)
			throw new java.lang.IllegalArgumentException();
		double[] results = new double[T];
		for (int i = 0; i < T; i++) {
			Percolation percolation = new Percolation(N);
			int count = 0;
			while (!percolation.percolates()) {
				int p = StdRandom.uniform(N) + 1;
				int q = StdRandom.uniform(N) + 1;
				if (!percolation.isOpen(p, q)) {
					count++;
					percolation.open(p, q);
				}
			}
			results[i] = (double) count / (N * N);
		}
		mean = StdStats.mean(results);
		stdDev = StdStats.stddev(results);
		sampleSize = T;
	}

	public double mean() {
		return mean;
	}

	public double stddev() {
		return stdDev;
	}

	public double confidenceLo() {
		return mean - 1.96 * stdDev / Math.sqrt(sampleSize);
	}

	public double confidenceHi() {
		return mean + 1.96 * stdDev / Math.sqrt(sampleSize);
	}

	public static void main(String[] args) {
		int N = Integer.parseInt(args[0]);
		int T = Integer.parseInt(args[1]);
		PercolationStats percolationStats = new PercolationStats(N, T);
		System.out.println("mean                    = "
				+ percolationStats.mean());
		System.out.println("stddev                  = "
				+ percolationStats.stddev());
		System.out.println("95% confidence interval = "
				+ percolationStats.confidenceLo() + ", "
				+ percolationStats.confidenceHi());
	}
}