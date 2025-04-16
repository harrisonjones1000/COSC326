import java.awt.Frame;
import java.awt.event.*;


public class CustomWindowListener implements WindowListener {

    private Frame frame;

    public CustomWindowListener(Frame f) {
        frame = f;
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
       
    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {
       
    }
    
}
