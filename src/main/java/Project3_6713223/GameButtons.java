/*
 * @author Rachapon - 6713247
 *         Ratchasin - 6713247
 *         Sayklang - 6713250
 *         Chayapol - 6713223
 *         Zabit - 6713116
 */
package Project3_6713223;

import javax.swing.*;
import java.awt.event.*;

// ===== BREAD BUTTON =====
class BreadButton extends JButton implements MouseListener {
    private GameBoard gameBoard;
    
    public BreadButton( int x, int y, GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        setBounds(x, y, 80, 120);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        gameBoard.addBreadToToast();
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

// ===== TOAST BUTTON =====
class ToastClickButton extends JButton implements MouseListener {
    private GameBoard gameBoard;
    private Toast toast;
    
    public ToastClickButton(Toast toast, int x, int y, GameBoard gameBoard) {
        this.toast = toast;
        this.gameBoard = gameBoard;
        setBounds(x, y, 80, 80);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        gameBoard.moveToastToBread(toast);
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

// ===== JAM BUTTON =====
class JamButton extends JButton implements MouseListener {
    private int jamType;
    private GameBoard gameBoard;
    
    public JamButton(int jamType, int x, int y, GameBoard gameBoard) {
        this.jamType = jamType;
        this.gameBoard = gameBoard;
        setBounds(x, y, 80, 80);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        gameBoard.applyJamToTray(jamType);
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}

// ===== TOPPING BUTTON =====
class ToppingButton extends JButton implements MouseListener {
    private int toppingType;
    private GameBoard gameBoard;
    
    public ToppingButton(int toppingType, int x, int y, GameBoard gameBoard) {
        this.toppingType = toppingType;
        this.gameBoard = gameBoard;
        setBounds(x, y, 80, 80);
        setContentAreaFilled(false);
        setBorderPainted(false);
        addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        gameBoard.applyToppingToTray(toppingType);
    }
    
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}