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

        //screenSize.setSize(new Dimension((int)screenSize.getWidth(), (int)(0.9*screenSize.getHeight())));

        setPreferredSize(screenSize);

        this.screen=screenSize;
        this.factor = factor;
        this.order = order;

        double heightfactor=0;
        double widthfactor=0;
        

        for(int i=0; i<order; i++){ 
            if(i%2==0){ //odd
                widthfactor+=Math.pow(factor,i);
            }else{//even
                heightfactor+=Math.pow(factor,i);
            }   
        }

        widthfactor = screenSize.getWidth()/widthfactor; //L to fit width ways

        this.length = widthfactor;

        if(order!=1){
            heightfactor = screenSize.getHeight()/heightfactor; //L to fit height ways
            if(heightfactor<widthfactor) this.length = heightfactor; //L is smaller
        } 

        this.length = (0.75*this.length);
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        drawLine(g, 1, (int)screen.getWidth()/2, (int)screen.getHeight()/2);
    }

    private void drawLine(Graphics g, int order, int x, int y){
        int a = (int)(Math.pow(this.factor, order-1)*this.length/2);
        if(a==0 && factor<1){ 
            return; //Line is not worth drawing
        }

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