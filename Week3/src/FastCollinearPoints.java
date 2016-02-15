import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class FastCollinearPoints {
	private List<LineSegment> segments;
	private int numSegments;

	public FastCollinearPoints(Point[] points) {
		// check if argument is null
		if (points == null)
			throw new java.lang.NullPointerException();
		// check if any of the points is null
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new java.lang.NullPointerException(
						"Point object cannot be null");
		}
		// check if any two points are equal
		for (int i = 0; i < points.length - 1; i++) {
			for (int j = i + 1; j < points.length; j++)
				if (points[i].compareTo(points[j]) == 0)
					throw new java.lang.IllegalArgumentException();
		}

		findSegements(points);
	}

	private void findSegements(Point[] oldPoints) {
		// sort in natural order by y coordinate and breaking ties by x
		// coordinate
		Point[] points = oldPoints.clone();	
		Arrays.sort(points);
		// temporary list to hold segments
		segments = new LinkedList<LineSegment>();
		for (int i = 0; i < points.length; i++) {
			// always preserve the previous natural order
			Point[] slopeOrder = points.clone();
			// sort on the slope with respect to the chosen point which is now
			// origin, considered in
			// natural order
			Arrays.sort(slopeOrder, slopeOrder[i].slopeOrder());
			// will hold the slopes with respect to the chosen point
			Double[] slopes = new Double[slopeOrder.length];
			// calculate slopes with respect to the point chosen
			for (int j = 0; j < slopeOrder.length; j++) {
				slopes[j] = points[i].slopeTo(slopeOrder[j]);
			}
			int start = 0;
			int end = start;
			while (end < slopes.length) {
				// calculate the interval until the slope is equal
				while (end < slopes.length
						&& Double.compare(slopes[start], slopes[end]) == 0)
					end++;
				// if we have found a set of 4 or more collinear points
				if (end - start > 2) {
					// if the starting point is the lowest by natural order, to
					// prevent subsegments to go in the list
					if (points[i].compareTo(slopeOrder[start]) < 0) {
						segments.add(new LineSegment(points[i],
								slopeOrder[end - 1]));
						numSegments++;
					}
				}
				start = end;
			}
		}
	}

	public int numberOfSegments() {
		return numSegments;
	}

	public LineSegment[] segments() {
		LineSegment[] lineSegments = new LineSegment[segments.size()];
		return segments.toArray(lineSegments);
	}
}