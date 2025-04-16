// By Oliver Hurst, Harrison Jones, Jack Bredenbeck and Ben Darlington
// Etude 3 - Cordless Phones
// 2025-04-09

import java.util.*;
import java.math.*;
import java.io.*;
import java.util.random.*;
// import org.apache.commons.math3.linear.*;

/**
 * Class for finding the maximum range of cordless phones from a text file
 * containing coordinates of phones such that, no matter
 * a person is, no more than 11 phones are in range.
 */
class TelephoneImproved {
    public static void main(String[] args) {

        ArrayList<String> linesIn = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            linesIn.add(sc.nextLine());
        }
        sc.close();
        linesIn.remove(0);

        ArrayList<Point2D> pointList = new ArrayList<>();
        for (String line : linesIn) {
            String[] sPoints = line.split("\\s+");
            Point2D point = new Point2D(Double.parseDouble(sPoints[0]), Double.parseDouble(sPoints[1]));
            pointList.add(point);
        }
        Collections.sort(pointList, new SortByX());
        // Get starting radius
        double startRad = getFirstRad(pointList);
        // Get final radius
        double finalRad = getBestRad(pointList, startRad);
        System.out.println("Maximum Range: " + finalRad + " meters");
    }

    /**
     * Takes a list of points, returns the radius of a circle that can be moved to
     * enclose at least 12 points.
     * 
     * @param pointList An arraylist of points.
     * @return The radius of a circle that can be moved to enclose at least 12
     *         points.
     */
    static double getFirstRad(ArrayList<Point2D> pointList) {

        // If dataset has only 11 points, this method needs to communicate that.
        double currentRadius = 0;
        double minX = pointList.get(0).x;
        double maxX = pointList.get(pointList.size() - 1).x;
        double xDiff = maxX - minX;

        ArrayList<Point2D> pointsInCircle = new ArrayList<>();
        for (int i = 0; i < pointList.size(); i++) {
            currentRadius += xDiff / pointList.size();
            if (PointNormDist(pointList.get(0), pointList.get(i)) < 2 * currentRadius) {
                pointsInCircle.add(pointList.get(i));
                if (pointsInCircle.size() > 11) {
                    return currentRadius;
                }
            }
        }
        return currentRadius;
    }

    /**
     * Takes a list of points and a radius that is known to be larger than the
     * radius of the largest circle that when placed anywhere on the points, cannot
     * enclose more than 11 points. Returns the radius of the largest circle that
     * when placed anywhere, will not enclose more than 11 points.
     * 
     * @param pointList     The arraylist of points.
     * @param currentRadius A radius that is known to be larger than the radius of
     *                      the largest circle that when placed anywhere on the
     *                      points will not enclose more than 11.
     * @return The radius of the largest circle that when placed anywhere will not
     *         enclose more that 11 points.
     */
    static double getBestRad(ArrayList<Point2D> pointList, double currentRadius) {

        // Circle bestCircle = new Circle(new Point2D(0, 0), 1);
        for (int i = 0; i < pointList.size(); i++) {
            // To improve performance, keep track of i.x, then use i.x - 2*radius to get min
            // x, i.x + 2*radius to get max x.
            // Then pass limits into find points, or make sublist here and pass sublist into
            // find points.
            // Could even store points in a treemap using floor key, ceiling key and finally
            // submap(fromkey, tokey) to get a sorted map of points in the x range. Then you
            // could repeat this somehow to get points in the y range.
            ArrayList<Point2D> pointsInCheckCircle = FindPoints(pointList, pointList.get(i), currentRadius);

            ArrayList<ArrayList<Point2D>> tripleCombList = new ArrayList<>();
            ArrayList<ArrayList<Point2D>> doubleCombList = new ArrayList<>();

            // Adds points of all circles that can be defined by pointsInBox
            addCircles(pointsInCheckCircle, tripleCombList, doubleCombList);
            // For every unique pair of points, solve the circle and add them to arraylist
            ArrayList<Circle> circleList = solve2PCircles(doubleCombList);
            // Repeat last step but with triples of points
            circleList.addAll(solve3PCircles(tripleCombList));

            for (Circle circle : circleList) {
                if (circle.radius >= currentRadius) {
                    continue;
                }
                int nPoints = 0;
                for (Point2D point : pointsInCheckCircle) {
                    if (PointNormDist(circle.origin, point) < circle.radius + 0.00001) {
                        nPoints++;
                    }
                }
                if (nPoints > 11) { // If there are more than 11 points and the radius is smaller than the current
                                    // best radius.
                    currentRadius = circle.radius;
                    // bestCircle = circle;
                }
            }

        }
        // System.out.println("Best circle: " + bestCircle);
        return currentRadius;
    }

    /**
     * Takes an array of all points and an origin, radius. Returns the set of points
     * that are contained in a circle centred on the origin, with radius = 2*radius.
     * so far
     * 
     * @param points The array of all points.
     * @param origin The center of the circle.
     * @param radius Half the radius of the circle.
     * @return An array of points that are contained within the circle.
     */
    static ArrayList<Point2D> FindPoints(ArrayList<Point2D> points, Point2D origin, double radius) {

        ArrayList<Point2D> pointsInCircle = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            if (PointNormDist(origin, points.get(i)) < 2 * radius) {
                pointsInCircle.add(points.get(i));
            }
        }
        return pointsInCircle;
    }

    /**
     * Takes an arraylist of points and two empty arraylists. Adds all unique
     * triples of points to one arraylist and all unique pairs of points to the
     * other.
     * 
     * @param coordList      The arraylist of points.
     * @param tripleCombList The empty arraylist to get unique triples.
     * @param doubleCombList The empty arraylist to get unique pairs.
     */
    static void addCircles(ArrayList<Point2D> coordList, ArrayList<ArrayList<Point2D>> tripleCombList,
            ArrayList<ArrayList<Point2D>> doubleCombList) {

        for (int i_1 = 0; i_1 < coordList.size(); i_1++) {
            for (int i_2 = i_1 + 1; i_2 < coordList.size(); i_2++) {
                ArrayList<Point2D> dCircle = new ArrayList<>();
                dCircle.add(coordList.get(i_1));
                dCircle.add(coordList.get(i_2));
                doubleCombList.add(dCircle);
                for (int i_3 = i_2 + 1; i_3 < coordList.size(); i_3++) {
                    ArrayList<Point2D> tCircle = new ArrayList<>();
                    tCircle.add(coordList.get(i_1));
                    tCircle.add(coordList.get(i_2));
                    tCircle.add(coordList.get(i_3));
                    tripleCombList.add(tCircle);
                }
            }
        }

    }

    /**
     * Takes an arraylist of pairs of points, returns and arraylist of circles
     * defined by each pair.
     * 
     * @param circlePoints An arraylist of pairs of points.
     * @return An arraylist of circles defined by pairs of points.
     */
    static ArrayList<Circle> solve2PCircles(ArrayList<ArrayList<Point2D>> circlePoints) {
        ArrayList<Circle> circleList = new ArrayList<>();
        for (int i = 0; i < circlePoints.size(); i++) {
            double x1 = circlePoints.get(i).get(0).x;
            double y1 = circlePoints.get(i).get(0).y;
            double x2 = circlePoints.get(i).get(1).x;
            double y2 = circlePoints.get(i).get(1).y;

            double originx = (x1 + x2) / 2;
            double originy = (y1 + y2) / 2;

            Point2D origin = new Point2D(originx, originy);
            double radius = PointNormDist(origin, circlePoints.get(i).get(0));
            Circle circle = new Circle(origin, radius);
            circleList.add(circle);
        }
        return circleList;
    }

    /**
     * Takes an arraylist of triples of points, returns and arraylist of circles
     * defined by each triple.
     * 
     * @param circlePoints An arraylist of triples of points.
     * @return An arraylist of circles, each defined by a triple of points.
     */
    static ArrayList<Circle> solve3PCircles(ArrayList<ArrayList<Point2D>> circlePoints) {

        ArrayList<Circle> circleList = new ArrayList<>();
        for (int i = 0; i < circlePoints.size(); i++) {

            double x1 = circlePoints.get(i).get(0).x;
            double y1 = circlePoints.get(i).get(0).y;
            double x2 = circlePoints.get(i).get(1).x;
            double y2 = circlePoints.get(i).get(1).y;
            double x3 = circlePoints.get(i).get(2).x;
            double y3 = circlePoints.get(i).get(2).y;

            double[][] coefficientData = { { 1, (y1 - y2) / (x1 - x2) }, { (x1 - x3) / (y1 - y3), 1 } };
            // RealMatrix coefficients = new Array2DRowRealMatrix(coefficientData, true);
            // DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

            double[] constantData = {
                    (Math.pow(x1, 2) - Math.pow(x2, 2) + Math.pow(y1, 2) - Math.pow(y2, 2)) / (2 * (x1 - x2)),
                    (Math.pow(y1, 2) - Math.pow(y3, 2) + Math.pow(x1, 2) - Math.pow(x3, 2)) / (2 * (y1 - y3)) };

            // RealVector constants = new ArrayRealVector(constantData, true);
            // RealVector solution = solver.solve(constants);
            // double[] originCoords = solution.toArray();
            // System.out.println("Apache Solution: ");
            // printArray(originCoords);

            double[][] X = coefficientData;
            double[] originCoords = solveLinearEquation(X, constantData);
            Point2D origin = new Point2D(originCoords[0], originCoords[1]);
            circleList.add(new Circle(origin, PointNormDist(origin, circlePoints.get(i).get(0))));
        }
        return circleList;
    }

    /**
     * Calculates and returns the L2 norm of the difference of two (2D) vectors.
     * 
     * @param a Vector a.
     * @param b Vector b.
     * @return The L2 norm of the difference of a and and b.
     */
    static double PointNormDist(Point2D a, Point2D b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    static double[][] transposeSquare2DMatrix(double[][] matrix) {
        double[][] transposedMatrix = { { matrix[0][0], matrix[1][0] }, { matrix[0][1], matrix[1][1] } };
        return transposedMatrix;
    }

    static double[][] inverseSquare2DMatrix(double[][] matrix) {
        double a = matrix[0][0];
        double b = matrix[0][1];
        double c = matrix[1][0];
        double d = matrix[1][1];

        double determinant = (a * d) - (b * c);

        double[][] inverseMatrix = { { d / determinant, -b / determinant }, { -c /
                determinant, a / determinant } };
        return inverseMatrix;
    }

    static double[][] matMult(double[][] A, double[][] B) {

        double[][] result = new double[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {

            for (int k = 0; k < B[0].length; k++) {
                double sum = 0;
                for (int j = 0; j < B.length; j++) {
                    sum += A[i][j] * B[j][k];
                }
                result[i][k] = sum;
            }
        }

        return result;
    }

    static double[] solveLinearEquation(double[][] X, double[] y) {
        double[][] yT = { { y[0] }, { y[1] } };
        double[][] Xt = transposeSquare2DMatrix(X);
        double[][] betaT = matMult(
                matMult(inverseSquare2DMatrix(matMult(Xt, X)), Xt),
                yT);
        double[] beta = { betaT[0][0], betaT[1][0] };

        return beta;

    }

    static void printArray(double[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + ", ");
            }
            System.out.println();
        }
        System.out.println();
    }

    static void printArray(double[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        System.out.println();
    }

    // static double[] dotProduct2DMatrixVec(double[][] A, double[] v) {
    // double[] result = {{A[0]}}
    // }

    /**
     * A class to represent a 2D point.
     */
    static class Point2D {
        double x;
        double y;

        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + this.x + ", " + this.y + ")";
        }
    }

    /**
     * A class to represent a circle, each described by their origin and radius.
     */
    static class Circle {
        Point2D origin;
        double radius;

        public Circle(Point2D origin, double radius) {
            this.origin = origin;
            this.radius = radius;
        }

        public String toString() {
            return "(" + origin.x + ", " + origin.y + "), " + radius;
        }
    }

    /**
     * Used to sort points by their x value.
     */
    static class SortByX implements Comparator<Point2D> {
        public int compare(Point2D a, Point2D b) {
            if (a.x > b.x) {
                return 1;
            } else if (a.x < b.x) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
