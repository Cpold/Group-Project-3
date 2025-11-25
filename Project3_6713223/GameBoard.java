/*
 * @author Rachapon - 6713247
 *         Ratchasin - 6713248
 *         Sayklang - 6713250
 *         Chayapol - 6713223
 *         Zabit - 6713116
 */
package Project3_6713223;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;

class GameBoard extends JPanel implements Runnable,
        MouseListener, MouseMotionListener, KeyListener {

    private SceneManager sceneManager;
    
    private MyImageIcon bgIcon;
    private MyImageIcon breadRawIcon, breadToastIcon;
    private MyImageIcon breadChocIcon, breadThaiIcon, breadCustIcon;
    private MyImageIcon CHOCHIPIcon, CHOCFOIIcon, CHOCMARIcon;
    private MyImageIcon TEACHIPIcon, TEACFOIIcon, TEAMARIcon;
    private MyImageIcon CUSCHIPIcon, CUSFOIIcon, CUSMARIcon;
    private MyImageIcon uiScoreIcon, uiHeartIcon, uiClockIcon;

    private Toast toastL, toastR;
    private Tray tray;
    private Thread thread;
    private boolean running;

    private ArrayList<Customer> customers;
    private long lastLeaveTime = 0;
    private int servedCount = 0;
    private int targetCustomers = 9;
    private int lives = 5;
    private boolean isBoosting = false;

    private BreadButton breadBtn;
    private JamButton jamChoc, jamThai, jamCust;
    private ToppingButton topFoi, topCho, topMar;
    private ToastClickButton toastLBtn, toastRBtn;

    private GameTimer gameTimer;

    private Bread draggingBread;
    private int dragX, dragY;
    private boolean isDragging;

    private int binX = 1100;
    private int binY = 600;
    private int binSize = 120;

    public GameBoard(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        setBackground(new Color(220, 200, 150));
        setLayout(null);
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        gameTimer = new GameTimer();
        toastL = new Toast(480, 590);
        toastR = new Toast(720, 590);
        tray = new Tray(600 - 60, 340 + 20);
        customers = new ArrayList<>();
        lastLeaveTime = System.currentTimeMillis();

        loadImages();
        createButtons();

        running = true;
        thread = new Thread(this);
        thread.start();
        this.requestFocusInWindow();

    }
    
    public void resetGame() {
    System.out.println("[GameBoard] Resetting game...");
    
    servedCount = 0;
    lives = 5;
    isBoosting = false;
    draggingBread = null;
    isDragging = false;
    lastLeaveTime = System.currentTimeMillis();
    
    toastL.clear();
    toastR.clear();
    tray.clear();
    customers.clear();
    customers.add(new Customer(550, 90));
    gameTimer = new GameTimer();
}
    public void stopGame() {
    running = false;
    try {
        if (thread != null && thread.isAlive()) {
            thread.join(1000);
        }
    } catch (InterruptedException e) {
        System.err.println("Error stopping game thread: " + e.getMessage());
    }
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
        CUSMARIcon = ImageLoader.loadImageIcon(MyConstants.CUSMAR);
        CUSFOIIcon = ImageLoader.loadImageIcon(MyConstants.CUSFOI);
        uiScoreIcon = ImageLoader.loadImageIcon(MyConstants.UI_SCORE_BG);
        uiHeartIcon = ImageLoader.loadImageIcon(MyConstants.UI_HEART);
        uiClockIcon = ImageLoader.loadImageIcon(MyConstants.UI_CLOCK_BG);
        System.out.println("Images loaded");
    }

    void createButtons() {
        breadBtn = new BreadButton(280, 525, this);
        breadBtn.setFocusable(false);
        add(breadBtn);

        toastLBtn = new ToastClickButton(toastL, 460, 540, this);
        toastLBtn.setFocusable(false);
        toastRBtn = new ToastClickButton(toastR, 690, 540, this);
        toastRBtn.setFocusable(false);
        add(toastLBtn);
        add(toastRBtn);

        jamChoc = new JamButton(0, 1000, 360, this);
        jamChoc.setFocusable(false);
        jamThai = new JamButton(1, 1055, 430, this);
        jamThai.setFocusable(false);
        jamCust = new JamButton(2, 1110, 480, this);
        jamCust.setFocusable(false);
        add(jamCust);
        add(jamThai);
        add(jamChoc);
        
        topFoi = new ToppingButton(0, 110, 380, this);
        topFoi.setFocusable(false);
        topCho = new ToppingButton(1, 85, 470, this);
        topCho.setFocusable(false);
        topMar = new ToppingButton(2, 45, 570, this);
        topMar.setFocusable(false);
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

    /*
    @Override
    public void run() {
        while (running) {
            updateToasts();
            updateGameLogic();
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
    */
    
    
    @Override //SMOOT RUM I DONT KNOW WHY
    public void run() {
        // กำหนดความเร็วเกมที่ 60 FPS (Frames Per Second)
        double drawInterval = 1000000000 / 60;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while (running) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                updateToasts();     
                updateGameLogic();
                repaint();        
                delta--;
            }
        }
    }

    void updateGameLogic() {
        long currentTime = System.currentTimeMillis();

        gameTimer.update();
        if (gameTimer.isTimeUp()) {

            return;
        }
        Iterator<Customer> it = customers.iterator();
        while (it.hasNext()) {
            Customer c = it.next();

            if (c.isAngryFinished()) {
                it.remove(); 
                lastLeaveTime = System.currentTimeMillis(); 
                System.out.println("Angry customer left.");
            }
        }
        if (customers.isEmpty() && (currentTime - lastLeaveTime > 2000)) {

            customers.add(new Customer(550, 90));

            System.out.println("Next customer arrived!");
        }
    }

    void updateToasts() {
        if (toastL.getBread() != null) {
            Bread b = toastL.getBread();
            if (b.state == Bread.TOASTING) {

                b.tick(isBoosting);

                if (b.isDone()) {
                    b.finishToast();
                    System.out.println("LEFT TOAST done!");
                }
            }
        }

        if (toastR.getBread() != null) {
            Bread b = toastR.getBread();
            if (b.state == Bread.TOASTING) {
                b.tick(isBoosting);

                if (b.isDone()) {
                    b.finishToast();
                    System.out.println("RIGHT TOAST done!");
                }
            }
        }
    }

    MyImageIcon getBreadIcon(Bread b) {
        if (b == null) {
            return null;
        }

        if (b.toppingType != -1) {
            if (b.jamType == 0) {
                if (b.toppingType == 0) {return CHOCFOIIcon;}
                if (b.toppingType == 1) {return CHOCHIPIcon;}
                if (b.toppingType == 2) {return CHOCMARIcon;}
            }//foi 0 choc 1 mar2
            if (b.jamType == 1) {
                if (b.toppingType == 0) {return TEACFOIIcon;}
                if (b.toppingType == 1) {return TEACHIPIcon;}
                if (b.toppingType == 2) {return TEAMARIcon;}
            }
            if (b.jamType == 2) {
                if (b.toppingType == 0) {return CUSFOIIcon;}
                if (b.toppingType == 1) {return CUSCHIPIcon;}
                if (b.toppingType == 2) {return CUSMARIcon;}
            }
        }

        if (b.jamType != -1) {
            if (b.jamType == 0) {return breadChocIcon;}
            if (b.jamType == 1) {return breadThaiIcon;}
            if (b.jamType == 2) {return breadCustIcon;}
        }
        
        if (b.state == Bread.TOASTED) {return breadToastIcon;}
        
        return breadRawIcon;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (bgIcon != null) {
            MyImageIcon resizedBg = bgIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT);
            g.drawImage(resizedBg.getImage(), 0, 0, null);
        }

        for (Customer c : customers) {
            c.draw(g);
        }

        int scoreX = 100;
        int scoreY = 20;
        if (uiScoreIcon != null) {

            MyImageIcon scoreBg = uiScoreIcon.resize(200, 80);
            g.drawImage(scoreBg.getImage(), scoreX, scoreY, null);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        String statusText = servedCount + " / " + targetCustomers;
        g.drawString(" " + statusText, scoreX + 90, scoreY + 50);

        int heartX = 525;
        int heartY = 15;
        int heartSize = 40;

        if (uiHeartIcon != null) {
            MyImageIcon heartResized = uiHeartIcon.resize(heartSize, heartSize);
            for (int i = 0; i < lives; i++) {
                g.drawImage(heartResized.getImage(), heartX + (i * (heartSize + 5)), heartY, null);
            }
        } else {
            g.setColor(Color.RED);
            g.drawString("Lives: " + lives, heartX, heartY + 20);
        }

        drawToast(g, toastL, "LEFT");
        drawToast(g, toastR, "RIGHT");
        drawTray(g);
        drawBin(g);

        int timerX = MyConstants.WIDTH - 250;
        int timerY = 20;

        if (uiClockIcon != null) {
            MyImageIcon clockBg = uiClockIcon.resize(200, 80);
            g.drawImage(clockBg.getImage(), timerX, timerY, null);
        }

        if (gameTimer.getTimeRemaining() <= 10) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.setFont(new Font("Arial", Font.BOLD, 24));

        g.drawString(gameTimer.getTimeString(), timerX + 105, timerY + 50);

        if (gameTimer.isTimeUp()) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            String msg = "TIME'S UP!";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(msg, (MyConstants.WIDTH - fm.stringWidth(msg)) / 2, MyConstants.HEIGHT / 2);

       
            g.setFont(new Font("Arial", Font.BOLD, 30));
            sceneManager.showEndScene(false, sceneManager.currentPlayerName);
        }

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

        if (toast.bread != null) {

            Bread b = toast.bread;
            MyImageIcon icon = getBreadIcon(toast.bread);
            if (icon != null) {
                MyImageIcon resized = icon.resize(90, 90);
                g.drawImage(resized.getImage(), x + 10, y + 10, null);
            }

            if (b.state == Bread.TOASTING) {
                if (isBoosting) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.GREEN);
                }

                int barWidth = (int) ((double) b.getProgress() / b.getMaxProgress() * 60);

                g.fillRect(x + 10, y - 10, barWidth, 8); // x, y, width, height

                g.setColor(Color.BLACK);
                g.drawRect(x + 10, y - 10, 60, 8);
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
        int slotSize = 90;
        if (bread != null) {
            MyImageIcon icon = getBreadIcon(bread);
            if (icon != null) {
                //MyImageIcon resized = icon.resize(slotSize - 10, slotSize - 10);
                MyImageIcon resized = icon.resize(slotSize, slotSize);
                g.drawImage(resized.getImage(), x + 10, y + 10, null);
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
        this.requestFocusInWindow();

        if (gameTimer.isTimeUp()) {
            return;
        }

        if (x > trayX && x < trayX + trayWidth
                && y > trayY && y < trayY + trayHeight) {

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
        if (gameTimer.isTimeUp()) {
            return;
        }
        if (isDragging && draggingBread != null) {
            int x = e.getX();
            int y = e.getY();
            boolean handled = false;

            if (inBin(x, y)) {
                draggingBread = null;
                System.out.println("Bread deleted!");
                handled = true;
            } else {
                Iterator<Customer> it = customers.iterator();
                while (it.hasNext()) {
                    Customer c = it.next();
                    if (c.isHit(x, y)) { // ถ้าลากไปโดนตัวลูกค้า
                        if (c.checkOrder(draggingBread)) {

                            servedCount++;
                            if (servedCount >= targetCustomers) {
                                System.out.println("YOU WIN!");
                                running = false;
                                sceneManager.showEndScene(true, sceneManager.currentPlayerName);
                            }
                            it.remove();
                        } else {

                            lives--;
                            c.makeAngry();
                            System.out.println("Wrong Order! Lives left: " + lives);
                            if (lives <= 0) {
                                lives = 0;
                                sceneManager.showEndScene(false, sceneManager.currentPlayerName);
                                running = false;
                                System.out.println("GAME OVER: Out of lives!");
                            }
                        }
                        draggingBread = null;
                        handled = true;
                        break;
                    }
                }
            }

            if (!handled) {
                tray.addBread(draggingBread);
                System.out.println("Bread returned to tray");
            }
            
            isDragging = false;
            repaint();
        }
    }

    boolean inBin(int x, int y) {
        return x > binX && x < binX + binSize
                && y > binY && y < binY + binSize;
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
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
            isBoosting = true;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
            isBoosting = false;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}
