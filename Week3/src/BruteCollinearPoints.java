import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BruteCollinearPoints {
	private List<LineSegment> segments;
	private int numSegments;

	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new java.lang.NullPointerException();
		for (int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new java.lang.NullPointerException(
						"Point object cannot be null");
		}
		for (int i = 0; i < points.length - 1; i++) {
			for (int j = i + 1; j < points.length; j++)
				if (points[i].compareTo(points[j]) == 0)
					throw new java.lang.IllegalArgumentException();
		}
		findSegments(points);

	}

	private void findSegments(Point[] oldArray) {
		Point[] points = oldArray.clone();
		Arrays.sort(points);
		segments = new LinkedList<LineSegment>();
		for (int i = 0; i < points.length - 3; i++) {
			for (int j = i + 1; j < points.length - 2; j++) {
				for (int k = j + 1; k < points.length - 1; k++) {
					for (int l = k + 1; l < points.length; l++) {
						if (points[i].slopeTo(points[j]) == points[j]
								.slopeTo(points[k])
								&& points[j].slopeTo(points[k]) == points[k]
										.slopeTo(points[l])) {
							segments.add(new LineSegment(points[i], points[l]));
							numSegments++;
						}
					}
				}
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