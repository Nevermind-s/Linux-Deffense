package Main;

import java.awt.event.*;
import java.awt.*;

import Main.Screen.KeyTyped;

public class KeyHandel implements MouseMotionListener, MouseListener, KeyListener {
 private Screen screen ;
 private Screen.KeyTyped keyTyped;
 public KeyHandel() { 
	 
 }

 public KeyHandel (Screen screen) {
	 this.screen= screen; 
	 this.keyTyped = this.screen.new KeyTyped();
 }
    @Override
    public void mouseDragged(MouseEvent e) {
        Screen.mse = new Point(e.getX() - (Frame.size.width - Screen.myWidth)/2, e.getY() - (Frame.size.height - Screen.myHeight) + (Frame.size.width - Screen.myWidth)/2);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Screen.mse = new Point(e.getX() - (Frame.size.width - Screen.myWidth)/2, e.getY() - (Frame.size.height - Screen.myHeight) + (Frame.size.width - Screen.myWidth)/2);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Screen.hUD.click(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode(); 
		if (keyCode == 27) {
			this.keyTyped.keyESC(); 
			
		}
		else if ( keyCode == 32) {
			this.keyTyped.keySPACE();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}
