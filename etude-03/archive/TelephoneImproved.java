
/* Compile with: 
 * javac -cp lib/commons-math3-3.6.1-bin/commons-math3-3.6.1/commons-math3-3.6.1.jar -d bin src/TelephoneImproved.java 
*/
/* Run with:
 * java -cp bin:lib/commons-math3-3.6.1-bin/commons-math3-3.6.1/commons-math3-3.6.1.jar TelephoneImproved < test-input.txt 
 */
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
        double currentRadius = 1d;
        boolean firstRun = true;

        Collections.sort(pointList, new SortByX());
        // System.out.println("All points: ");
        // for (Point2D point2d : pointList) {
        // System.out.println(point2d);
        // }

        while (firstRun) {
            ArrayList<Point2D> pointsInBox = FindPoints(pointList, pointList.get(0), currentRadius / 2d);
            if (pointsInBox.size() >= 12) {
                // update radius
                firstRun = false;
                // System.out.println("N points in box:" + pointsInBox.size());
                // System.out.println("Radius : " + currentRadius);
            } else {
                currentRadius = currentRadius * 2;
            }
        }
        System.out.println("first rad: " + currentRadius);
        boolean lastRun = false;
        while (true) {
            double newRad = getNextRad(pointList, currentRadius);
            if (newRad > currentRadius - 0.000001 && newRad < currentRadius + 0.000001) {
                break;
                // if (lastRun) {
                // break;
                // } else {
                // lastRun = true;
                // }
            } else {
                currentRadius = newRad;
            }
            System.out.println("Current radius: " + newRad);
        }
        System.out.println("final radius: " + currentRadius);
        // Use sorted list here???
        // Need to be able to use built in binary search?
        // Point2D[] pointArray = new double[pointList.size()];
        // coordList.toArray(coordList);

        // for (int i = 0; i < pointList.size(); i++) {
        // System.out.println(pointList.get(i));
        // }
    }

    static double getNextRad(ArrayList<Point2D> pointList, double currentRadius) {

        ArrayList<Double> bestRadii = new ArrayList<>();
        bestRadii.add(currentRadius);
        Comparator<Circle> sortByRad = new SortByRadius();
        for (int i = 0; i < pointList.size(); i++) {

            // System.out.println("Broke into for");
            ArrayList<Point2D> pointsInBox = FindPoints(pointList, pointList.get(i), currentRadius);

            // System.out.println("n points in box: " + pointsInBox.size());
            // System.out.println("n points: " + pointList.size());

            ArrayList<ArrayList<Point2D>> tripleCombList = new ArrayList<>();
            ArrayList<ArrayList<Point2D>> doubleCombList = new ArrayList<>();
            addMinCircleRad(pointsInBox, tripleCombList, doubleCombList);

            // System.out.println("N C 3 combos: " + tripleCombList.size());
            // System.out.println("N C 2 combos: " + doubleCombList.size());

            ArrayList<Circle> circleList = solve2PCircles(doubleCombList);
            circleList.addAll(solve3PCircles(tripleCombList));

            // System.out.println("n circles: " + circleList.size());
            // for (Circle circle : circleList) {
            // System.out.println(circle);

            // }

            // Comparator<Circle> cmp = new SortByRadius();
            // for (Circle circle1 : circleList) {
            // for (Circle circle2 : circleList) {
            // int greaterThan = cmp.compare(circle1, circle2);
            // System.out.println(circle1);
            // System.out.println(circle2);
            // System.out.println(greaterThan);
            // }
            // }
            // System.out.println("N circles: " + circleList.size());
            // System.exit(0);
            Collections.sort(circleList, sortByRad);

            // System.exit(0);
            double newRadius = getSmallestRad(circleList, pointList);
            // System.out.println("computed rad: " + newRadius);
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

        double xMin = origin.x - 2 * radius;
        double xMax = origin.x + 2 * radius;
        double yMin = origin.y - 2 * radius;
        double yMax = origin.y + 2 * radius;

        int xMinI = 0;
        int xMaxI = points.size() - 1;
        double xMinV = points.get(xMaxI).x;
        double xMaxV = points.get(xMinI).x;

        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).x > xMin && points.get(i).x < xMinV) {
                xMinI = i;
                xMinV = points.get(i).x;
            }
            if (points.get(i).x < xMax && points.get(i).x > xMaxV) {
                xMaxI = i;
                xMaxV = points.get(i).x;
            }
        }
        // System.out.println("Points in box: ");
        // System.out.println("xMin: " + xMin + "\nxMax: " + xMax);
        // System.out.println("yMin: " + yMin + "\nyMax: " + yMax);
        List<Point2D> pointsInXRange = points.subList(xMinI, xMaxI);
        ArrayList<Point2D> pointsInBox = new ArrayList<>();

        for (Point2D point2d : pointsInXRange) {
            if (point2d.y > yMin && point2d.y < yMax) {
                pointsInBox.add(point2d);
            }
        }

        return pointsInBox;

    }

    static void addMinCircleRad(ArrayList<Point2D> coordList,
            ArrayList<ArrayList<Point2D>> tripleCombList, ArrayList<ArrayList<Point2D>> doubleCombList) {
        // ArrayList<ArrayList<Point2D>> tripleCombList = new ArrayList<>();
        // ArrayList<ArrayList<Point2D>> doubleCombList = new ArrayList<>();

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
            // RealVector point_1 = new ArrayRealVector(circlePoints.get(i).get(0), true);
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
                System.out.println("New circle: " + circle);
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
}