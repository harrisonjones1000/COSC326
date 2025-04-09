
/* Compile with: 
 * javac -cp lib/commons-math3-3.6.1-bin/commons-math3-3.6.1/commons-math3-3.6.1.jar -d bin src/TelephoneImproved.java 
*/
/* Run with:
 * java -cp bin:lib/commons-math3-3.6.1-bin/commons-math3-3.6.1/commons-math3-3.6.1.jar TelephoneImproved < test-input.txt 
 */
// By Oliver Hurst, Harrison Jones, Jack Bredenbeck and Ben Darlington
// Etude 3 - Cordless Phones
// 2025-04-09

import java.util.*;
import java.math.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.random.*;
import org.apache.commons.math3.linear.*;

class TelephoneImproved {
    public static void main(String[] args) {

        ArrayList<String> linesIn = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            linesIn.add(sc.nextLine());
        }
        sc.close();

        ArrayList<Point2D> pointList = new ArrayList<>();
        for (String line : linesIn) {
            String[] sPoints = line.split(", ");
            Point2D point = new Point2D(Double.parseDouble(sPoints[0]), Double.parseDouble(sPoints[1]));
            pointList.add(point);
        }
        Collections.sort(pointList, new SortByX());
        double startRad = getFirstRad(pointList);

        double finalRad = getRadGreaterEleven(pointList, startRad);
        System.out.println("Final radius: " + finalRad);
    }

    static int fastFindNextRad(ArrayList<Point2D> pointList, int fewestPoints, double currentRadius) {
        for (int i = 0; i < pointList.size(); i++) {
            ArrayList<Point2D> pointsInCircle = FindPoints(pointList, pointList.get(i), currentRadius);
            if (pointsInCircle.size() < fewestPoints && pointsInCircle.size() > 11) {
                return pointsInCircle.size();
            }
        }
        return fewestPoints;
    }

    static double getFirstRad(ArrayList<Point2D> pointList) {

        double currentRadius = 0;
        double minX = pointList.get(0).x;
        double maxX = pointList.get(pointList.size() - 1).x;
        double xDiff = maxX - minX;

        ArrayList<Point2D> pointsInCircle = new ArrayList<>();
        for (int i = 0; i < pointList.size(); i++) {
            currentRadius += xDiff / pointList.size();
            if (PointNormDist(pointList.get(0), pointList.get(i)) < 2 * currentRadius) {
                pointsInCircle.add(pointList.get(i));
                if (pointsInCircle.size() > 12) {
                    return currentRadius;
                }
            }
        }
        return currentRadius;
    }

    static double getRadGreaterEleven(ArrayList<Point2D> pointList, double currentRadius) {

        // Circle bestCircle = new Circle(new Point2D(0, 0), 1);
        for (int i = 0; i < pointList.size(); i++) {
            ArrayList<Point2D> pointsInCheckCircle = FindPoints(pointList, pointList.get(i), currentRadius);

            ArrayList<ArrayList<Point2D>> tripleCombList = new ArrayList<>();
            ArrayList<ArrayList<Point2D>> doubleCombList = new ArrayList<>();

            // Adds points of all circles that can be defined by pointsInBox
            addCircles(pointsInCheckCircle, tripleCombList, doubleCombList);
            ArrayList<Circle> circleList = solve2PCircles(doubleCombList);
            circleList.addAll(solve3PCircles(tripleCombList));

            for (Circle circle : circleList) {
                int nPoints = 0;
                for (Point2D point : pointsInCheckCircle) {
                    if (PointNormDist(circle.origin, point) < circle.radius + 0.00001) {
                        nPoints++;
                    }
                }
                if (nPoints > 11 && circle.radius < currentRadius) {
                    currentRadius = circle.radius;
                    // bestCircle = circle;
                }
            }

        }
        // System.out.println("Best circle: " + bestCircle);
        return currentRadius;
    }

    static double getNextRad(ArrayList<Point2D> pointList, double currentRadius) {

        ArrayList<Double> bestRadii = new ArrayList<>();
        bestRadii.add(currentRadius);
        Comparator<Circle> sortByRad = new SortByRadius();
        for (int i = 0; i < pointList.size(); i++) {

            ArrayList<Point2D> pointsInCheckCircle = FindPoints(pointList, pointList.get(i), currentRadius);

            ArrayList<ArrayList<Point2D>> tripleCombList = new ArrayList<>();
            ArrayList<ArrayList<Point2D>> doubleCombList = new ArrayList<>();
            // Adds all circles that can be defined by pointsInBox
            addCircles(pointsInCheckCircle, tripleCombList, doubleCombList);

            ArrayList<Circle> circleList = solve2PCircles(doubleCombList);
            circleList.addAll(solve3PCircles(tripleCombList));
            Collections.sort(circleList, sortByRad);
            double newRadius = getSmallestRad(circleList, pointList);
            if (newRadius < bestRadii.get(bestRadii.size() - 1)) {
                bestRadii.add(newRadius);
            }
        }
        return bestRadii.get(bestRadii.size() - 1);
    }

    /**
     * Takes an array of points sorted by x value and the radius of the best circle
     * so far
     * 
     * @param points The array of points, sorted by x value.
     * @param origin The middle of the square.
     * @param radius The radius of the best circle thus far.
     * @return An array of points that are contained within a square, centred on a
     *         point with width equal to two times radius.
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

    static ArrayList<Circle> solve2PCircles(ArrayList<ArrayList<Point2D>> circlePoints) {
        ArrayList<Circle> circleList = new ArrayList<>();
        for (int i = 0; i < circlePoints.size(); i++) {
            double x1 = circlePoints.get(i).get(0).x;
            double y1 = circlePoints.get(i).get(0).y;
            double x2 = circlePoints.get(i).get(1).x;
            double y2 = circlePoints.get(i).get(1).y;

            double originx = x1 - (x2 / 2);
            double originy = y1 - (y2 / 2);
            Point2D origin = new Point2D(originx, originy);
            double radius = PointNormDist(origin, circlePoints.get(i).get(0));
            Circle circle = new Circle(origin, radius);
            circleList.add(circle);
        }
        return circleList;
    }

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
            RealMatrix coefficients = new Array2DRowRealMatrix(coefficientData, true);
            DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

            double[] constantData = {
                    (Math.pow(x1, 2) - Math.pow(x2, 2) + Math.pow(y1, 2) - Math.pow(y2, 2)) / (2 * (x1 - x2)),
                    (Math.pow(y1, 2) - Math.pow(y3, 2) + Math.pow(x1, 2) - Math.pow(x3, 2)) / (2 * (y1 - y3)) };

            RealVector constants = new ArrayRealVector(constantData, true);
            RealVector solution = solver.solve(constants);
            double[] originCoords = solution.toArray();
            Point2D origin = new Point2D(originCoords[0], originCoords[1]);
            circleList.add(new Circle(origin, PointNormDist(origin, circlePoints.get(i).get(0))));
        }
        return circleList;
    }

    static double PointNormDist(Point2D a, Point2D b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    static double getSmallestRad(ArrayList<Circle> circleList, ArrayList<Point2D> points) {
        for (Circle circle : circleList) {
            int pInCircle = 0;
            for (int i = 0; i < points.size(); i++) {
                if (PointNormDist(circle.origin, points.get(i)) <= circle.radius + 0.00001) {
                    pInCircle++;
                }
            }
            if (pInCircle >= 12) {
                // System.out.println("New circle: " + circle);
                return circle.radius;
            }
        }
        System.out.println("ERROR, no circle contains 12 points");
        return 0;

    }

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

    static class SortByRadius implements Comparator<Circle> {
        public int compare(Circle a, Circle b) {
            if (a.radius > b.radius) {
                return 1;
            } else if (a.radius < b.radius) {
                return -1;
            } else {
                return 0;
            }
        }
    }

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

    static class ReturnTuple {
        int fewestPoints;
        double currentRadius;

        public ReturnTuple(int fewestPoints, double currentRadius) {
            this.fewestPoints = fewestPoints;
            this.currentRadius = currentRadius;
        }
    }
}