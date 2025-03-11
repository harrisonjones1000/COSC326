//Harrison Jones
//First argument should be order
//Second should be factor

import javax.swing.*;
import java.awt.*;

public class Etude13 {
    public static void main(String[] args){
        int order;
        double factor;
        if(args.length!=2){
            System.out.println("Arguments should be the order (int) and then the factor of the HV tree (double)");
            return;
        }else{
            try{
                order = Integer.parseInt(args[0]);
                factor = Double.parseDouble(args[1]);
            }catch(NumberFormatException e){
                System.out.println("Your inputs did not format");
                return;
            }
        }

        JFrame frame = new JFrame("My Frame");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        Draw d = new Draw(order, factor);
        frame.getContentPane().add(d);
        frame.pack();
        frame.setVisible(true);
        
    }
}