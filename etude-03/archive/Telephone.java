
/* Compile with: 
 * javac -cp commons-math3-3.6.1-bin/commons-math3-3.6.1/commons-math3-3.6.1.jar -d . Telephone.java 
*/
/* Run with:
 * java -cp .:./commons-math3-3.6.1-bin/commons-math3-3.6.1/commons-math3-3.6.1.jar Telephone < ../test-input.txt 
 */
import java.util.*;
import java.math.*;
import java.io.*;
import org.apache.commons.math3.linear.*;

class Telephone {
    public static void main(String[] args) {

        long startTime = System.nanoTime();

        long makeArray = System.nanoTime();

        ArrayList<String> linesIn = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            linesIn.add(sc.nextLine());
        }
        sc.close();

        ArrayList<double[]> coordList = new ArrayList<>();
        for (String line : linesIn) {
            String[] sPoints = line.split(", ");
            double[] fPoints = { Double.parseDouble(sPoints[0]), Double.parseDouble(sPoints[1]) };
            coordList.add(fPoints);
        }
        double[][] coordArray = new double[coordList.size()][2];
        coordList.toArray(coordArray);

        long makeArrayEnd = System.nanoTime();
        System.out.println("Time to make array: " + (makeArrayEnd - makeArray) / 1000000 + "ms");

        long getCombinations = System.nanoTime();

        ArrayList<double[][]> combList = new ArrayList<>();
        for (int i_1 = 0; i_1 < coordArray.length; i_1++) {
            for (int i_2 = i_1 + 1; i_2 < coordArray.length; i_2++) {
                for (int i_3 = i_2 + 1; i_3 < coordArray.length; i_3++) {
                    combList.add(coordArray[i_1], coordArray[i_2], coordArray[i_3]);
                    // System.out.println(combList.get(combList.size() - 1));
                }
            }
        }
        // TriplePoint[] combArray = new TriplePoint[combList.size()];
        // combList.toArray(combArray);
        double[][][] combArray = new double[combList.size()][3][2];
        combList.toArray(combArray);

        long getCombinationsEnd = System.nanoTime();
        System.out.println("Time to get comb: " + (getCombinationsEnd - getCombinations) / 1000000 + "ms");

        long makeCircles = System.nanoTime();
        ArrayList<Circle> circleList = new ArrayList<>();

        for (int i = 0; i < combArray.length; i++) {

            // double x1 = combArray[i].p_1[0];
            // double y1 = combArray[i].p_1[1];
            // double x2 = combArray[i].p_2[0];
            // double y2 = combArray[i].p_2[1];
            // double x3 = combArray[i].p_3[0];
            // double y3 = combArray[i].p_3[1];

            double x1 = combArray[i][0][0];
            double y1 = combArray[i][0][1];
            double x2 = combArray[i][1][0];
            double y2 = combArray[i][1][1];
            double x3 = combArray[i][2][0];
            double y3 = combArray[i][2][1];

            double[][] coefficientData = { { 1, (y1 - y2) / (x1 - x2) }, { (x1 - x3) / (y1 - y3), 1 } };
            RealMatrix coefficients = new Array2DRowRealMatrix(coefficientData, true);
            DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();

            double[] constantData = {
                    (Math.pow(x1, 2) - Math.pow(x2, 2) + Math.pow(y1, 2) - Math.pow(y2, 2)) / (2 * (x1 - x2)),
                    (Math.pow(y1, 2) - Math.pow(y3, 2) + Math.pow(x1, 2) - Math.pow(x3, 2)) / (2 * (y1 - y3)) };

            RealVector constants = new ArrayRealVector(constantData, true);
            RealVector solution = solver.solve(constants);
            RealVector point_1 = new ArrayRealVector(combArray[i][0], true);

            circleList.add(new Circle(solution.toArray(), solution.getDistance(point_1)));

        }
        long makeCirclesEnd = System.nanoTime();
        System.out.println("Time to make circles: " + (makeCirclesEnd - makeCircles) / 1000000 + "ms");

        long findCircle = System.nanoTime();

        Collections.sort(circleList, new SortByRadius());

        for (Circle circle : circleList) {
            int pInCircle = 0;
            for (int i = 0; i < coordArray.length; i++) {
                if (L2NormDist(circle.origin, coordArray[i]) <= circle.radius + 0.00001) {
                    pInCircle++;
                }
            }
            if (pInCircle >= 12) {
                System.out.println(circle);
                break;
            }
        }

        long endTime = System.nanoTime();
        System.out.println("Time to find circle: " + (endTime - findCircle) / 1000000 + "ms");
        System.out.println("Total time: " + (endTime - startTime) / 1000000 + " ms");

    }

    // static class TriplePoint {
    // double[] p_1;
    // double[] p_2;
    // double[] p_3;

    // public TriplePoint(double[] p_1, double[] p_2, double[] p_3) {
    // this.p_1 = p_1;
    // this.p_2 = p_2;
    // this.p_3 = p_3;
    // }

    // public String toString() {
    // return "p1: (" + this.p_1[0] + ", " + this.p_1[1] + ")\np2: (" + this.p_2[0]
    // + ", " + this.p_2[1]
    // + ")\np3: ("
    // + this.p_3[0] + ", " + this.p_3[1] + ")";
    // }
    // }

    static class Circle {
        double[] origin;
        double radius;

        public Circle(double[] origin, double radius) {
            this.origin = origin;
            this.radius = radius;
        }

        public String toString() {
            return "(" + origin[0] + ", " + origin[1] + "), " + radius;
        }
    }

    static class SortByRadius implements Comparator<Circle> {
        public int compare(Circle a, Circle b) {
            if (a.radius < b.radius) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    static double L2NormDist(double[] a, double[] b) {
        return Math.sqrt(Math.pow(a[0] - b[0], 2) + Math.pow(a[1] - b[1], 2));
    }

}