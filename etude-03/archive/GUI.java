import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class GUI {
    
    private Frame frame;
    private CustomCanvas canvas;
    private BufferStrategy bs;
    private double[][] coords;
    private ArrayList<double[]> circles;

    public GUI(double[][] c) {
        coords = c;
        circles = new ArrayList<double[]>();
        frame = new Frame("Telephones");
        frame.addWindowListener(new CustomWindowListener(frame));
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        canvas = new CustomCanvas();
        frame.add(canvas);
        frame.setVisible(true);
    }

    private class CustomCanvas extends Canvas {
        @Override
        public void paint(Graphics g) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
            g.setColor(Color.RED);
            double[] range = coordsRange(coords);
            double offsetX = -1.0d * (int)Math.round(range[0]);
            double offsetY = -1.0d * (int)Math.round(range[2]);
            double scaleX = canvas.getWidth() / (range[1] - range[0]);
            double scaleY = canvas.getHeight() / (range[3] - range[2]);
            double chosenScale = Math.min(scaleX, scaleY);

            for(int i = 0; i < coords.length; i++) {
                int r = 4;
                g.fillOval((int)Math.round((coords[i][0] + offsetX) * chosenScale) - r, canvas.getHeight() - ((int)Math.round((coords[i][1] + offsetY) * chosenScale)) - r, r * 2, r*2);
            }

            g.setColor(Color.GREEN);
            for(int i = 0; i < circles.size(); i++) {
                double[] circle = circles.get(i);

                g.drawOval((int)Math.round((circle[0] + offsetX - circle[2]) * chosenScale), canvas.getHeight() - (int)Math.round((circle[1] + offsetY + circle[2]) * chosenScale), (int)Math.round(circle[2] * 2 * chosenScale), (int)Math.round(circle[2]* 2 * chosenScale));
            }
        }
    }

    public void drawCircle(double x, double y, double r) {
        double[] circle = {x, y, r};
        circles.add(circle);
        canvas.repaint();
    }

    public void flushCircles() {
        circles.clear();
    }

    private double[] coordsRange(double[][] coords) {
        /* Calculates the outer most boundary points
        Returns array in the form [minX, maxX, minY, maxY]
         */
        
        double minX = coords[0][0];
        double maxX = coords[0][1];
        double minY = coords[1][0];
        double maxY = coords[1][1];

        for(double[] coord : coords) {
            double x = coord[0];
            double y = coord[1];

            if(x > maxX) {
                maxX = x;
            }
            if(x < minX) {
                minX = x;
            }
            if(y > maxY) {
                maxY = y;
            }
            if(y < minY) {
                minY = y;
            }
        }
        double[] range = {minX, maxX, minY, maxY};
        return range;
    }

}
