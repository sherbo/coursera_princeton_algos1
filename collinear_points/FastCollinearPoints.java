import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

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

        lineSegments = new ArrayList<>();

        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);

        for (int i = 0; i < numPoints - 3; i++) {
            Arrays.sort(pointsCopy);

            // Sort the points by the slopes they makes with p.
            Arrays.sort(pointsCopy, pointsCopy[i].slopeOrder());

            for (int p = 0, start = 1, end = 2; end < numPoints; end++) {
                // Find the last collinear point to p
                while (end < numPoints
                        && Double.compare(pointsCopy[p].slopeTo(pointsCopy[start]), pointsCopy[p].slopeTo(pointsCopy[end])) == 0) {
                    end++;
                }
                // Form a segment if there are >= 3 points and the segment is unique
                if (end - start >= 3 && pointsCopy[p].compareTo(pointsCopy[start]) < 0) {
                    lineSegments.add(new LineSegment(pointsCopy[p], pointsCopy[end - 1]));
                }

                start = end;
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.size();

    }
    public LineSegment[] segments() {
        return lineSegments.toArray(new LineSegment[lineSegments.size()]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
