import javax.swing.*;
import java.awt.*;

public class Draw extends JPanel{
    private Dimension screen;
    private double length;
    private int order;
    private double factor;

    Draw(int order, double factor){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        setPreferredSize(screenSize);

        this.screen=screenSize;
        this.factor = factor;
        this.order = order;

        double l = 0;

        //Need to calculate how wide and tall a HV-tree of length L, order o and factor f would be.
        //Then pick the largest L such that it doesnt get out of the screens bounds. 

        double heightfactor=0;
        double widthfactor=0;
        

        for(int i=1; i<order+1; i++){ 
            if(i%2==1){ //odd
                widthfactor+=Math.pow(factor,2*i);
            }else{//even
                heightfactor += Math.pow(factor,2*i-1);
            }   
        }

        widthfactor = screenSize.getWidth()/widthfactor; //L to fit width ways
        heightfactor = screenSize.getWidth()/heightfactor; //L to fit height ways

        this.length = heightfactor;
        if(widthfactor<heightfactor) this.length = widthfactor; //pick smaller L to fit.

    }

    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        drawLine(g, 1, (int)screen.getWidth()/2, (int)screen.getHeight()/2);
    }

    private void drawLine(Graphics g, int order, int x, int y){
        System.out.println(length);
        System.out.println(screen.toString());
        int a = (int)(Math.pow(this.factor, order-1)*this.length/2);
        if(order%2==1){ //odd, 1, 3, 5, draw horizontal
            g.drawLine(x-a,y,x+a,y);
        }else{ //even, 2, 4, 6, 8, draw vertical
            g.drawLine(x,y-a,x,y+a);
        }
        
        if(order == this.order){
            return;
        }
        
        if(order%2==1){
            drawLine(g, order+1, x-a, y);
            drawLine(g, order+1, x+a, y);
        }else{
            drawLine(g, order+1, x, y-a);
            drawLine(g, order+1, x, y+a);
        }

    }
}