import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        int numPoints = points.length;

        // Check for null points
        for (int i = 0; i < numPoints; i++) {
            if (points[i] == null) {
                    throw new IllegalArgumentException();
            }
        }

        // Check for duplicate points
        for (int i = 0; i < numPoints; i++) {
            for (int j = i+1; j < numPoints; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        this.segments = new ArrayList<LineSegment>();
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        for (int i = 0; i < numPoints; i++) {
            for (int j = i + 1; j < numPoints; j++) {
                for (int k = j + 1; k < numPoints; k++) {
                    for (int m = k + 1; m < numPoints; m++) {
                        Point point1 = pointsCopy[i];
                        Point point2 = pointsCopy[j];
                        Point point3 = pointsCopy[k];
                        Point point4 = pointsCopy[m];

                        if (point1.slopeTo(point2) == point2.slopeTo(point3)
                                && point2.slopeTo(point3) == point3.slopeTo(point4)) {
                            LineSegment segment = new LineSegment(point1, point4);
                            segments.add(segment);
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[numberOfSegments()]);
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
