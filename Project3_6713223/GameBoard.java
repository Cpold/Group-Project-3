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
import java.util.concurrent.CopyOnWriteArrayList;



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
    private MyImageIcon burntBreadIcon;

    private Toast toastL, toastR;
    private Tray tray;
    private Thread thread;
    private boolean running;

    private ArrayList<Customer> customers;
    private long lastLeaveTime = 0;
    private int servedCount = 0;
    private int targetCustomers = 5;
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

    private int hoveredTraySlot = -1;

    private int binX = 1100;
    private int binY = 600;
    private int binSize = 120;

    private CopyOnWriteArrayList<Effect_Graphics> particles = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<FloatingText> texts = new CopyOnWriteArrayList<>();

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
        burntBreadIcon =ImageLoader.loadImageIcon(MyConstants.BREAD_BURN);
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

        if (b != null && (b.state == Bread.TOASTED || b.state == Bread.BURNT)) {

            boolean hasSpace = (tray.getSlot(0) == null || tray.getSlot(1) == null);

            if (hasSpace) {

                Bread moved = toast.removeBread();
                tray.addBread(moved);
                System.out.println("TOAST -> TRAY");
            } else {

                System.out.println("Tray is full! Bread stays in toaster.");
            }
        }
    }

    void applyJamToTray(int jamType) {
        for (int i = 0; i < 2; i++) {
            Bread b = tray.getSlot(i);
            if (b != null && b.state == Bread.TOASTED && !b.hasJam()) {
                b.addJam(jamType);
                b.playPopAnimation();
                spawnParticles(tray.x + (i == 0 ? 0 : tray.slotWidth + tray.spacing) + 45, tray.y + 45, getJamColor(jamType));
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
                b.playPopAnimation();
                spawnParticles(tray.x + (i == 0 ? 0 : tray.slotWidth + tray.spacing) + 45, tray.y + 45, getToppingColor(toppingType));

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
            } else if (c.isTimeout() && !c.penaltyApplied) {
                lives--;
                c.penaltyApplied = true;
                c.makeAngry();
                System.out.println("Timeout! Lives left: " + lives);

                if (lives <= 0) {
                    lives = 0;
                    sceneManager.showEndScene(false, "koonpolz");
                    running = false;
                }
            }
        }
        if (customers.isEmpty() && (currentTime - lastLeaveTime > 2000)) {

            customers.add(new Customer(550, 90));

            System.out.println("Next customer arrived!");
        }

        for (Effect_Graphics p : particles) {
            if (!p.update()) {
                particles.remove(p); // สั่งลบตรงๆ ได้เลย ไม่ต้องใช้ Iterator
            }
        }
        for (FloatingText t : texts) {
            if (!t.update()) {
                texts.remove(t);
            }
        }

    }

    void updateToasts() {
       
     
        if (toastL.getBread() != null) {
            Bread b = toastL.getBread();
            
            if (b.state == Bread.TOASTING || b.state == Bread.TOASTED) {
              
                boolean effectiveBoost = isBoosting && (b.state == Bread.TOASTING);
                
                b.tick(effectiveBoost); 
                
            }
        }

       
        if (toastR.getBread() != null) {
            Bread b = toastR.getBread();
            
            if (b.state == Bread.TOASTING || b.state == Bread.TOASTED) {
               
                boolean effectiveBoost = isBoosting && (b.state == Bread.TOASTING);
                
                b.tick(effectiveBoost);
               
            }
        }
      
        
    }

    MyImageIcon getBreadIcon(Bread b) {
        if (b == null) {
            return null;
        }

        if (b.toppingType != -1) {
            if (b.jamType == 0) {
                if (b.toppingType == 0) {
                    return CHOCFOIIcon;
                }
                if (b.toppingType == 1) {
                    return CHOCHIPIcon;
                }
                if (b.toppingType == 2) {
                    return CHOCMARIcon;
                }
            }//foi 0 choc 1 mar2
            if (b.jamType == 1) {
                if (b.toppingType == 0) {
                    return TEACFOIIcon;
                }
                if (b.toppingType == 1) {
                    return TEACHIPIcon;
                }
                if (b.toppingType == 2) {
                    return TEAMARIcon;
                }
            }
            if (b.jamType == 2) {
                if (b.toppingType == 0) {
                    return CUSFOIIcon;
                }
                if (b.toppingType == 1) {
                    return CUSCHIPIcon;
                }
                if (b.toppingType == 2) {
                    return CUSMARIcon;
                }
            }
        }

        if (b.jamType != -1) {
            if (b.jamType == 0) {
                return breadChocIcon;
            }
            if (b.jamType == 1) {
                return breadThaiIcon;
            }
            if (b.jamType == 2) {
                return breadCustIcon;
            }
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
        int timerY = 15;

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
            sceneManager.showEndScene(false, "koonpolz");
        }

        for (Effect_Graphics p : particles) {
            p.draw((Graphics2D) g);
        }
        for (FloatingText t : texts) {
            t.draw((Graphics2D) g);
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

        // Effect 
        if (isBoosting && toast.bread != null && toast.bread.state == Bread.TOASTING) {
            x += (Math.random() * 4) - 2;
            y += (Math.random() * 4) - 2;
        }

        if (toast.bread != null) {
            Bread b = toast.bread;

           
            MyImageIcon iconToDraw;
            if (b.state == Bread.BURNT) {
                iconToDraw = burntBreadIcon; 
            } else {
                iconToDraw = getBreadIcon(b); 
            }

            if (iconToDraw != null) {
                MyImageIcon resized = iconToDraw.resize(90, 90);
                g.drawImage(resized.getImage(), x + 10, y + 10, null);
            }
            // ------------------------------------------------

            Graphics2D g2d = (Graphics2D) g;

            // 1.  (BURNT) 
            if (b.state == Bread.BURNT) {
                long time = System.currentTimeMillis();
                g2d.setColor(new Color(50, 50, 50, 180)); // ควันเทาดำ
                for (int i = 0; i < 5; i++) {
                    int smokeX = x + 30 + (int) (Math.sin(time * 0.008 + i) * 20);
                    int smokeY = y - (int) ((time / 8 + i * 40) % 80);
                    g2d.fillOval(smokeX, smokeY, 15 + i * 2, 15 + i * 2);
                }
            } 
            // 2.  (TOASTED) ->  Overlay
            else if (b.state == Bread.TOASTED) {
                float burnProgress = (float) (b.getProgress() - b.getMaxProgress()) /
                        (b.getBurntLimit() - b.getMaxProgress());
                int alpha = (int) (burnProgress * 200);
                if (alpha > 200) alpha = 200;
                if (alpha < 0) alpha = 0;

                g2d.setColor(new Color(100, 50, 0, alpha));
                g2d.fillRoundRect(x + 10, y + 10, 90, 90, 15, 15);
            } 
            // 3.(TOASTING) ->  Overlay 
            else if (b.state == Bread.TOASTING) {
                float progress = (float) b.getProgress() / b.getMaxProgress();
                int alpha = (int) (progress * 160);
                if (alpha > 255) alpha = 255;
                if (alpha < 0) alpha = 0;

                g2d.setColor(new Color(100, 60, 20, alpha));
                g2d.fillRoundRect(x + 10, y + 10, 90, 90, 15, 15);

                if (progress > 0.5f) {
                    long time = System.currentTimeMillis();
                    g2d.setColor(new Color(255, 255, 255, 100));
                    for (int i = 0; i < 3; i++) {
                        int smokeX = x + 45 + (int) (Math.sin(time * 0.005 + i) * 15);
                        int smokeY = y - (int) ((time / 10 + i * 50) % 60);
                        int size = 10 + i * 5;
                        g2d.fillOval(smokeX, smokeY, size, size);
                    }
                }
            }

            // Progress Bar
            if (b.state == Bread.TOASTING || b.state == Bread.TOASTED) {
                int barWidth = 80;
                int barHeight = 10;
                int barX = x + 10;
                int barY = y - 15;

                float percent = 0;
                Color barColor = Color.GRAY;

                if (b.state == Bread.TOASTING) {
                    percent = (float) b.getProgress() / b.getMaxProgress();
                    if (isBoosting) {
                        barColor = new Color(255, 100, 100);
                    } else {
                        barColor = new Color(255, 200, 0);
                    }
                } else if (b.state == Bread.TOASTED) {
                    double burnDuration = b.getBurntLimit() - b.getMaxProgress();
                    double currentBurn = b.getProgress() - b.getMaxProgress();
                    percent = (float) (currentBurn / burnDuration);
                    barColor = Color.RED;
                }

                if (percent > 1.0f) percent = 1.0f;
                if (percent < 0.0f) percent = 0.0f;

                g.setColor(new Color(50, 50, 50));
                g.fillRoundRect(barX, barY, barWidth, barHeight, 5, 5);

                g.setColor(barColor);
                int currentWidth = (int) (barWidth * percent);
                g.fillRoundRect(barX, barY, currentWidth, barHeight, 5, 5);

                g.setColor(Color.WHITE);
                g.drawRoundRect(barX, barY, barWidth, barHeight, 5, 5);
            }
        }
    }

    void drawTray(Graphics g) {
        int x = tray.x;
        int y = tray.y;
        boolean isHover0 = (hoveredTraySlot == 0);

        drawTraySlot(g, x, y, tray.getSlot(0), isHover0);

        boolean isHover1 = (hoveredTraySlot == 1);
        drawTraySlot(g, x + tray.slotWidth + tray.spacing, y, tray.getSlot(1), isHover1);

    }

 void drawTraySlot(Graphics g, int x, int y, Bread bread, boolean isHovered) {
        int slotSize = 90;

        
        if (isHovered) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(255, 255, 255, 100));
            g2d.fillRoundRect(x, y, 110, 110, 20, 20);

            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(x, y, 110, 110, 20, 20);
            g2d.setStroke(new BasicStroke(1));
        }

      
        if (bread != null) {
            int currentSize = (int) (slotSize * bread.scale);
            int offset = (currentSize - slotSize) / 2;

            MyImageIcon iconToDraw;
            
           
            if (bread.state == Bread.BURNT) {
                iconToDraw = burntBreadIcon; 
            } else {
                iconToDraw = getBreadIcon(bread);
            }
 
            if (iconToDraw != null) {
                MyImageIcon resized = iconToDraw.resize(currentSize, currentSize);
                g.drawImage(resized.getImage(), x + 10 - offset, y + 10 - offset, null);
            }
          
        }
    }

    void drawBin(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // เช็คว่า: กำลังลากของอยู่ AND ปลายเมาส์อยู่ที่ถังขยะใช่ไหม?
        boolean isHoveringBin = isDragging && inBin(dragX, dragY);

        if (isHoveringBin) {
            // 🔥 โหมดปีศาจ: ถ้าจะทิ้ง ให้ถังขยะแดง + สั่น

            // 1. สั่นกึกๆ
            int shakeX = (int) (Math.random() * 6) - 3;
            int size = (int) (binSize * 1.1); // ขยายใหญ่ขึ้น 10%
            int offset = (size - binSize) / 2; // จัดกึ่งกลาง

            // 2. วาดพื้นหลังสีแดงจางๆ (Alert Area)
            g2.setColor(new Color(255, 100, 100, 100));
            g2.fillRect(binX - offset + shakeX, binY - offset, size, size);

            // 3. วาดเส้นขอบสีแดงหนาๆ
            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(5)); // เส้นหนา
            g2.drawRect(binX - offset + shakeX, binY - offset, size, size);

            // 4. วาดกากบาท (X) ตรงกลาง
            g2.drawLine(binX, binY, binX + binSize, binY + binSize);
            g2.drawLine(binX + binSize, binY, binX, binY + binSize);

            // (Optional) ใส่ข้อความ "DELETE"
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("DELETE!", binX + 15, binY - 10);

        } else {
            // 🗑️ โหมดปกติ: ถังขยะนิ่งๆ
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2)); // เส้นปกติ
            g2.drawRect(binX, binY, binSize, binSize);

            // วาดลายเส้นถังขยะ (เส้นตั้ง 3 เส้น) ให้ดูมีดีเทลนิดนึง
            g2.setStroke(new BasicStroke(1));
            int quarter = binSize / 4;
            g2.drawLine(binX + quarter, binY, binX + quarter, binY + binSize);
            g2.drawLine(binX + quarter * 2, binY, binX + quarter * 2, binY + binSize);
            g2.drawLine(binX + quarter * 3, binY, binX + quarter * 3, binY + binSize);
        }

        g2.setStroke(new BasicStroke(1)); // คืนค่าเส้นให้เป็นปกติก่อนจบ
    }

    void spawnParticles(int x, int y, Color c) {
        for (int i = 0; i < 15; i++) {
            // เปลี่ยนจาก new EffectParticle(...) เป็น
            particles.add(new Effect_Graphics(x, y, c));
        }
    }

    void spawnServedEffect(int x, int y, boolean isSuccess) {
        for (int i = 0; i < 30; i++) {

            particles.add(new Effect_Graphics(x, y, isSuccess));
        }
    }

    // Helper เลือกสีให้ตรงกับแยม
    Color getJamColor(int type) {
        if (type == 0) {
            return new Color(80, 40, 0);   // Choc
        }
        if (type == 1) {
            return new Color(255, 100, 0); // Thai Tea
        }
        return new Color(255, 255, 150);             // Custard
    }

// Helper เลือกสีให้ตรงกับท็อปปิ้ง
    Color getToppingColor(int type) {
        if (type == 0) {
            return new Color(255, 200, 0); // Foi Thong
        }
        if (type == 1) {
            return new Color(50, 30, 10);  // Choc Chip
        }
        return new Color(255, 255, 255);             // Marshmallow
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
                if (b != null && (b.state == Bread.TOASTED || b.state == Bread.BURNT)) {
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

        if (!isDragging) {
            for (Customer c : customers) {

                if (c.isBubbleHit(e.getX(), e.getY())) {

                    c.soothe();

                    texts.add(new FloatingText("+3s", c.x - 150, c.y + 80, Color.GREEN));

                    break;
                }
            }
        }

        if (isDragging && draggingBread != null) {
            int x = e.getX();
            int y = e.getY();
            boolean handled = false;

            
            if (inBin(x, y)) {
                draggingBread = null;
                System.out.println("Bread deleted!");
                handled = true;
            } 
            else {
               
                if (draggingBread.state == Bread.BURNT) {
                    for (Customer c : customers) {
                        if (c.isHit(x, y)) {
                           
                            texts.add(new FloatingText("YUCK!", c.x + 20, c.y, Color.RED));
                            texts.add(new FloatingText("Get away!", c.x + 20, c.y - 30, Color.RED));

                            
                            break;
                        }
                    }
                } 
                else {
                    Iterator<Customer> it = customers.iterator();
                    while (it.hasNext()) {
                        Customer c = it.next();

                        
                        if (c.isHit(x, y)) {
                            if (c.checkOrder(draggingBread)) {
                                
                                spawnServedEffect(c.x + 50, c.y + 50, true);
                                texts.add(new FloatingText("Goodboy", c.x + 50, c.y, Color.GREEN));
                                texts.add(new FloatingText("Delicious!", c.x + 50, c.y - 30, Color.GREEN));

                                servedCount++;
                                if (servedCount >= targetCustomers) {
                                    System.out.println("YOU WIN!");
                                    running = false;
                                    sceneManager.showEndScene(true, "koonpolz");
                                }
                                it.remove();

                            } else {
                               
                                spawnServedEffect(c.x + 50, c.y + 50, false);
                                texts.add(new FloatingText("WRONG!", c.x + 50, c.y, Color.RED));
                                texts.add(new FloatingText("Da HellBro", 525, 50, Color.RED));

                                lives--;
                                c.makeAngry(); // ทำให้โกรธ
                                System.out.println("Wrong Order! Lives left: " + lives);

                                if (lives <= 0) {
                                    lives = 0;
                                    sceneManager.showEndScene(false, "koonpolz");
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
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int newHoverSlot = tray.getSlotAtPosition(e.getX(), e.getY());

        if (newHoverSlot != hoveredTraySlot) {
            hoveredTraySlot = newHoverSlot;
            repaint();
        }
    }

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
    public void keyTyped(KeyEvent e) {
    }
}
