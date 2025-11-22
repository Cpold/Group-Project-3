/*
 * @author Rachapon - 6713247
 *         Ratchasin - 6713247
 *         Sayklang - 6713250
 *         Chayapol - 6713223
 *         Zabit - 6713116
 */
package Project3_6713223;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GameBoard extends JPanel implements Runnable, 
        MouseListener, MouseMotionListener, KeyListener {
    
    private MyImageIcon bgIcon;
    private MyImageIcon breadRawIcon, breadToastIcon;
    private MyImageIcon breadChocIcon, breadThaiIcon, breadCustIcon;
    private MyImageIcon CHOCHIPIcon,CHOCFOIIcon,CHOCMARIcon;
    private MyImageIcon TEACHIPIcon,TEACFOIIcon,TEAMARIcon;
    private MyImageIcon CUSCHIPIcon,CUSFOIIcon,CUSMARIcon;
    
    private Toast toastL, toastR;
    private Tray tray;
    private Thread thread;
    private boolean running;
    
    private BreadButton breadBtn;
    private JamButton jamChoc, jamThai, jamCust;
    private ToppingButton topFoi, topCho, topMar;
    private ToastClickButton toastLBtn, toastRBtn;
    
    private Bread draggingBread;
    private int dragX, dragY;
    private boolean isDragging;
    
    private int binX = 1100;
    private int binY = 600;
    private int binSize = 120;
    
    
    public GameBoard() {
        setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        setBackground(new Color(220, 200, 150));
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        
        toastL = new Toast(480, 590);
        toastR = new Toast(720, 590);
        tray = new Tray(600, 340);
        
        loadImages();
        createButtons();
        
        running = true;
        thread = new Thread(this);
        thread.start();
    }
    
    void loadImages() {
        System.out.println("[GameBoard] Loading images...");
        bgIcon = ImageLoader.loadImageIcon(MyConstants.BG);
        breadRawIcon = ImageLoader.loadImageIcon(MyConstants.BREAD_RAW);
        breadToastIcon = ImageLoader.loadImageIcon(MyConstants.BREAD_TOAST);
        breadChocIcon = ImageLoader.loadImageIcon(MyConstants.BREAD_CHOC);
        breadThaiIcon = ImageLoader.loadImageIcon(MyConstants.BREAD_THAI);
        breadCustIcon = ImageLoader.loadImageIcon(MyConstants.BREAD_CUST);
        CHOCHIPIcon = ImageLoader.loadImageIcon(MyConstants.CHOCHIP);
        CHOCFOIIcon = ImageLoader.loadImageIcon(MyConstants.CHOCFOI);
        CHOCMARIcon = ImageLoader.loadImageIcon(MyConstants.CHOCMAR);
        TEACHIPIcon = ImageLoader.loadImageIcon(MyConstants.TEACHIP);
        TEACFOIIcon = ImageLoader.loadImageIcon(MyConstants.TEACFOI);
        TEAMARIcon = ImageLoader.loadImageIcon(MyConstants.TEAMAR);
        CUSCHIPIcon = ImageLoader.loadImageIcon(MyConstants.CUSCHIP);
        CUSFOIIcon = ImageLoader.loadImageIcon(MyConstants.CUSFOI);
        CUSMARIcon = ImageLoader.loadImageIcon(MyConstants.CUSMAR);
        System.out.println("Images loaded");
    }
    
    void createButtons() {
        breadBtn = new BreadButton( 300, 550, this);
        add(breadBtn);
        
        toastLBtn = new ToastClickButton(toastL, 480, 590, this);
        toastRBtn = new ToastClickButton(toastR, 720, 590, this);
        add(toastLBtn);
        add(toastRBtn);
        
        jamChoc = new JamButton(0, 1000, 340, this);
        jamThai = new JamButton(1, 1050, 430, this);
        jamCust = new JamButton(2, 1110, 515, this);
        add(jamChoc);
        add(jamThai);
        add(jamCust);
        
        topFoi = new ToppingButton(0, 120, 380, this);
        topCho = new ToppingButton(1, 90, 460, this);
        topMar = new ToppingButton(2, 60, 550, this);
        add(topFoi);
        add(topCho);
        add(topMar);
    }
    
    void addBreadToToast() {
        Bread b = new Bread();
        b.startToast();
        
        if (toastL.addBread(b)) {
            System.out.println("BREAD → LEFT TOAST");
        } else if (toastR.addBread(b)) {
            System.out.println("BREAD → RIGHT TOAST");
        } else {
            System.out.println("Both toasters full!");
        }
    }
    
    void moveToastToBread(Toast toast) {
        Bread b = toast.getBread();
        if (b != null && b.state == Bread.TOASTED) {
            Bread moved = toast.removeBread();
            if (tray.addBread(moved)) {
                System.out.println("TOAST → TRAY");
            }
        }
    }
    
    void applyJamToTray(int jamType) {
        for (int i = 0; i < 2; i++) {
            Bread b = tray.getSlot(i);
            if (b != null && b.state == Bread.TOASTED && !b.hasJam()) {
                b.addJam(jamType);
                System.out.println("JAM " + jamType + " added to slot " + i);
                repaint();
                return;
            }
        }
        System.out.println("No toasted bread in tray!");
    }
    
    void applyToppingToTray(int toppingType) {
    for (int i = 0; i < 2; i++) {
        Bread b = tray.getSlot(i);
        if (b != null && b.state == Bread.TOASTED && b.hasJam() && !b.hasTopping()) {
            b.addTopping(toppingType);
            System.out.println("TOPPING " + toppingType + " added to slot " + i);
            repaint();
            return;
        }
    }
    System.out.println("No bread available for topping!");
}
    
    @Override
    public void run() {
        while (running) {
            updateToasts();
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
    
    void updateToasts() {
        if (toastL.getBread() != null) {
            Bread b = toastL.getBread();
            if (b.state == Bread.TOASTING && b.isDone()) {
                b.finishToast();
                System.out.println("LEFT TOAST done! (Click to move to tray)");
            }
        }
        
        if (toastR.getBread() != null) {
            Bread b = toastR.getBread();
            if (b.state == Bread.TOASTING && b.isDone()) {
                b.finishToast();
                System.out.println("RIGHT TOAST done! (Click to move to tray)");
            }
        }
    }
    
    MyImageIcon getBreadIcon(Bread b) {
        if (b == null) return null;
        
        if (b.toppingType != -1) {
            if (b.jamType == 0) {
                if (b.toppingType == 0) return CHOCFOIIcon;
                if (b.toppingType == 1) return CHOCHIPIcon;
                if (b.toppingType == 2) return CHOCMARIcon;
            }//foi 0 choc 1 mar2
            if (b.jamType == 1) {
                if (b.toppingType == 0) return TEACFOIIcon;
                if (b.toppingType == 1) return TEACHIPIcon;
                if (b.toppingType == 2) return TEAMARIcon;
            }
            if (b.jamType == 2) {
                if (b.toppingType == 0) return CUSFOIIcon;
                if (b.toppingType == 1) return CUSCHIPIcon;
                if (b.toppingType == 2) return CUSMARIcon;
            }
        }
        
        if (b.jamType != -1) {
            if (b.jamType == 0) return breadChocIcon;
            if (b.jamType == 1) return breadThaiIcon;
            if (b.jamType == 2) return breadCustIcon;
        }
        
        if (b.state == Bread.TOASTED) {
            return breadToastIcon;
        }
        return breadRawIcon;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (bgIcon != null) {
            MyImageIcon resizedBg = bgIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT);
            g.drawImage(resizedBg.getImage(), 0, 0, null);
        }
        
        drawToast(g, toastL, "LEFT");
        drawToast(g, toastR, "RIGHT");
        drawTray(g);
        drawBin(g);
        
        if (isDragging && draggingBread != null) {
            MyImageIcon icon = getBreadIcon(draggingBread);
            if (icon != null) {
                MyImageIcon resized = icon.resize(60, 60);
                g.drawImage(resized.getImage(), dragX - 30, dragY - 30, null);
            }
        }
    }
    
    void drawToast(Graphics g, Toast toast, String label) {
        int x = toast.x;
        int y = toast.y;
        
        if (toast.bread == null) {} 
        else {
            MyImageIcon icon = getBreadIcon(toast.bread);
            if (icon != null) {
                MyImageIcon resized = icon.resize(60, 60);
                g.drawImage(resized.getImage(), x + 10, y + 10, null);
            }
        }
    }
    
    void drawTray(Graphics g) {
        int x = tray.x;
        int y = tray.y;
        drawTraySlot(g, x, y, tray.getSlot(0));
        drawTraySlot(g, x + tray.slotWidth + tray.spacing, y, tray.getSlot(1));
        
    }
    
    void drawTraySlot(Graphics g, int x, int y, Bread bread) {
        int slotSize = 70;
        if (bread != null) {
        MyImageIcon icon = getBreadIcon(bread);
        if (icon != null) {
            MyImageIcon resized = icon.resize(slotSize - 10, slotSize - 10);
            g.drawImage(resized.getImage(), x + 5, y + 5, null);
        }
    } 
    }
    
    void drawBin(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(binX, binY, binSize, binSize);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        
        int trayX = tray.x;
        int trayY = tray.y;
        int trayWidth = tray.getTrayWidth();
        int trayHeight = tray.getTrayHeight();
        
        if (x > trayX && x < trayX + trayWidth &&
            y > trayY && y < trayY + trayHeight) {
            
            int slotIndex = tray.getSlotAtPosition(x, y);
            
            if (slotIndex >= 0) {
                Bread b = tray.getSlot(slotIndex);
                if (b != null && b.state == Bread.TOASTED) {
                    draggingBread = b;
                    tray.removeSlot(slotIndex);
                    isDragging = true;
                    dragX = x;
                    dragY = y;
                    System.out.println("Dragging bread from slot " + slotIndex);
                }
            }
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (isDragging) {
            dragX = e.getX();
            dragY = e.getY();
            repaint();
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
        if (isDragging && draggingBread != null) {
            int x = e.getX();
            int y = e.getY();
            
            if (inBin(x, y)) {
                draggingBread = null;
                System.out.println("Bread deleted!");
            } else {
                tray.addBread(draggingBread);
                System.out.println("Bread returned to tray");
            }
            
            isDragging = false;
            repaint();
        }
    }
    
    boolean inBin(int x, int y) {
        return x > binX && x < binX + binSize &&
               y > binY && y < binY + binSize;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}