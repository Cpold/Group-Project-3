/*
 * @author Rachapon - 6713247
 * Ratchasin - 6713248
 * Sayklang - 6713250
 * Chayapol - 6713223
 * Zabit - 6713116
 */
package Project3_6713223;

import java.awt.*;
import java.util.Random;

public class Customer {

    public int x, y;
    private MyImageIcon icon;
    private MyImageIcon angryIcon;
    private String orderName;

    private boolean isAngry = false;
    private long angryStartTime = 0;

    private long entryTime;
    private long patienceTime = 20000;
    public boolean penaltyApplied = false;

 //(Poke)
    private String chatText = "";
    private long chatTimer = 0;
    private long nextComplainTime = 0;
   private String[] complainLines = {
        "Hurry up!",             
        "So hungry...",         
        "Is it ready?",        
        "My grandma is faster!", 
        "Zzzz...",               
        "Hello???",              
        "Nong bao tam nan jung!",        
        "Where is my toast?",    
        "Do you even cook?",     
        "1 Star Review!",        
        "My Rome was built in a day",           
        "I am spiderman!"   ,
        "Is it KhakKhak"
           ,
        "You so Handsome //-//"
           ,
        "<3 <3 <3"
    };
    // ------------------------------------

    // Menu
    private static final String[] MENU_LIST = {
        "Custard Choc Chip Toast", "Custard Foi Thong Toast", "Custard Marshmallow Toast",
        "Chocolate Choc Chip Toast", "Chocolate Foi Thong Toast", "Chocolate Marshmallow Toast",
        "Thai Tea Choc Chip Toast", "Thai Tea Foi Thong Toast", "Thai Tea Marshmallow Toast"
    };

    public Customer(int x, int y) {
        this.x = x;
        this.y = y;

        this.entryTime = System.currentTimeMillis();
        this.nextComplainTime = System.currentTimeMillis() + 3000 + new Random().nextInt(5000);

        Random rand = new Random();
        this.orderName = MENU_LIST[rand.nextInt(MENU_LIST.length)];

        String[] imgPaths = {
            MyConstants.CUSTOMER_1, MyConstants.CUSTOMER_2,
            MyConstants.CUSTOMER_3, MyConstants.CUSTOMER_4,
            MyConstants.CUSTOMER_5, MyConstants.CUSTOMER_6,
            MyConstants.CUSTOMER_7, MyConstants.CUSTOMER_8,
            MyConstants.CUSTOMER_9
        };

        String[] angryPaths = {
            MyConstants.CUS_ANGRY_1, MyConstants.CUS_ANGRY_2,
            MyConstants.CUS_ANGRY_3, MyConstants.CUS_ANGRY_4,
            MyConstants.CUS_ANGRY_5, MyConstants.CUS_ANGRY_6,
            MyConstants.CUS_ANGRY_7, MyConstants.CUS_ANGRY_8,
            MyConstants.CUS_ANGRY_9
        };

        int randomIndex = rand.nextInt(imgPaths.length);

        this.icon = new MyImageIcon(imgPaths[randomIndex]).resize(150, 250);
        this.angryIcon = new MyImageIcon(angryPaths[randomIndex]).resize(150, 250);
    }

    public void draw(Graphics g) {

        if (isAngry && angryIcon != null) {
            g.drawImage(angryIcon.getImage(), x, y, null);

           
            long time = System.currentTimeMillis();

            int shakeX = (int) (Math.sin(time * 0.05) * 5);
            int shakeY = (int) (Math.cos(time * 0.07) * 5);

            int markX = x + 120 + shakeX;
            int markY = y + 50 + shakeY;

            g.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 60));

            g.setColor(Color.WHITE);
            g.drawString("!!!", markX - 4, markY - 4);
            g.drawString("!!!", markX + 4, markY + 4);

            g.setColor(Color.BLACK);
            g.drawString("!!!", markX - 3, markY);
            g.drawString("!!!", markX + 3, markY);
            g.drawString("!!!", markX, markY - 3);
            g.drawString("!!!", markX, markY + 3);

            if ((time / 100) % 2 == 0) {
                g.setColor(Color.RED);
            } else {
                g.setColor(new Color(255, 200, 0));
            }
            g.drawString("!!!", markX, markY);

        } else if (icon != null) {
            g.drawImage(icon.getImage(), x, y, null);
if (System.currentTimeMillis() > nextComplainTime) {
                // สุ่มคำบ่นใหม่
                Random r = new Random();
                this.chatText = complainLines[r.nextInt(complainLines.length)];
                
                // โชว์ข้อความนาน 2.5 วินาที
                this.chatTimer = System.currentTimeMillis() + 2500;
                
                // ตั้งเวลาบ่นครั้งถัดไป (สุ่มอีก 4-9 วินาที)
                this.nextComplainTime = System.currentTimeMillis() + 4000 + r.nextInt(5000);
            }
            
            drawOrderBubble(g);
            
           
            if (!chatText.isEmpty() && System.currentTimeMillis() < chatTimer) {
                drawChatBubble(g);
                
            }
            if (!chatText.isEmpty() && System.currentTimeMillis() < chatTimer) {
                drawChatBubble(g);
            }
          
            drawPatienceBar(g);
        }
    }
    
  
   private void drawOrderBubble(Graphics g) {
        // ขยายความกว้างกล่องหน่อยนะครับ เพราะคำว่า Topping ... Toast มันยาว
        int bubbleW = 250; 
        int bubbleX = x + 130; 
        int bubbleY = y - 20;
        int bubbleH = 100;

        Graphics2D g2 = (Graphics2D) g;

        // 1. วาดกล่อง (เหมือนเดิม)
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRoundRect(bubbleX + 5, bubbleY + 5, bubbleW, bubbleH, 15, 15);
        
        g2.setColor(new Color(255, 250, 230)); 
        g2.fillRoundRect(bubbleX, bubbleY, bubbleW, bubbleH, 15, 15);
        
        g2.setColor(new Color(100, 60, 20)); 
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(bubbleX, bubbleY, bubbleW, bubbleH, 15, 15);
        g2.setStroke(new BasicStroke(1));

        // หัวข้อ
        g2.setColor(new Color(100, 60, 20));
        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        g2.drawString("Order Ticket #1", bubbleX + 15, bubbleY + 25);
        
        g2.setColor(new Color(200, 180, 150));
        g2.drawLine(bubbleX + 10, bubbleY + 30, bubbleX + bubbleW - 10, bubbleY + 30);

        // --- 🔥 แยกบรรทัดแบบล็อคคำ (Manual Split) 🔥 ---
        String line1 = "";
        String line2 = "";

        // เช็คชื่อแยม เพื่อแยกบรรทัดที่ 1
        if (orderName.startsWith("Custard")) {
            line1 = "Custard";
            // เอาส่วนที่เหลือ (Foi Thong Toast) มาใส่บรรทัด 2
            line2 =  orderName.substring(8); 
        } 
        else if (orderName.startsWith("Chocolate")) {
            line1 = "Chocolate";
            line2 = orderName.substring(10);
        } 
        else if (orderName.startsWith("Thai Tea")) {
            line1 = "Thai Tea";
            line2 = orderName.substring(9);
        }
        // ------------------------------------------------

        // วาดบรรทัดที่ 1 (ชื่อแยม) - สีตามรสชาติ
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        if (line1.equals("Chocolate")) g2.setColor(new Color(101, 67, 33));
        else if (line1.equals("Thai Tea")) g2.setColor(new Color(210, 105, 30)); 
        else g2.setColor(new Color(60, 160, 40)); 
        
        g2.drawString(line1, bubbleX + 15, bubbleY + 55);
        
        // วาดบรรทัดที่ 2 (Topping ...) - สีเทาเข้ม/ดำ
        g2.setFont(new Font("Arial", Font.BOLD, 14)); // ปรับตัวหนาให้อ่านง่าย
        g2.setColor(Color.DARK_GRAY); // หรือ new Color(80, 50, 20) ถ้าอยากได้สีน้ำตาล
        g2.drawString(line2, bubbleX + 15, bubbleY + 80);
    }


    private void drawChatBubble(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int bubbleW = 220;
        int bubbleH = 50;
        
        int cx = x - 200; 
        int cy = y + 60;  

        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRoundRect(cx + 4, cy + 4, bubbleW, bubbleH, 20, 20);

        int[] shadowX = {cx + bubbleW - 5, cx + bubbleW + 15, cx + bubbleW - 5};
        int[] shadowY = {cy + 15, cy + 25, cy + 35};
        g2.fillPolygon(shadowX, shadowY, 3);

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(cx, cy, bubbleW, bubbleH, 20, 20);

        int[] xPoints = {cx + bubbleW - 10, cx + bubbleW + 20, cx + bubbleW - 10};
        int[] yPoints = {cy + 15, cy + 25, cy + 35};
        g2.fillPolygon(xPoints, yPoints, 3);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(cx, cy, bubbleW, bubbleH, 20, 20);
        
        g2.setColor(Color.WHITE);
        g2.drawLine(cx + bubbleW - 10, cy + 16, cx + bubbleW - 10, cy + 34);
        
        g2.setColor(Color.BLACK);
        g2.drawLine(cx + bubbleW - 10, cy + 15, cx + bubbleW + 20, cy + 25);
        g2.drawLine(cx + bubbleW + 20, cy + 25, cx + bubbleW - 10, cy + 35);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        
        FontMetrics fm = g2.getFontMetrics();
        int textW = fm.stringWidth(chatText);
        int textX = cx + (bubbleW - textW) / 2;
        int textY = cy + (bubbleH + fm.getAscent() - 10) / 2 + 3;
        
        g2.drawString(chatText, textX, textY);
    }


    public void poke() {
        Random r = new Random();
       
        this.chatText = complainLines[r.nextInt(complainLines.length)];
 
        this.chatTimer = System.currentTimeMillis() + 2000; 

        this.entryTime += 3000; 
 
        if (this.entryTime > System.currentTimeMillis()) {
            this.entryTime = System.currentTimeMillis();
        }
    }

    public boolean checkOrder(Bread b) {
        if (b == null) {
            return false;
        }
        System.out.println("Customer wants: " + orderName + " | Got: " + b.getMenuName());
        return orderName.equals(b.getMenuName());
    }

    public boolean isHit(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + 150
                && mouseY >= y && mouseY <= y + 250;
    }

    public void makeAngry() {
        this.isAngry = true;
        this.angryStartTime = System.currentTimeMillis();
    }

    public boolean isAngry() {
        return isAngry;
    }

    public boolean isAngryFinished() {
        boolean patienceRunOut = (System.currentTimeMillis() - entryTime) > patienceTime;
        if (patienceRunOut && !isAngry) {
            makeAngry();
            return false;
        }
        return isAngry && (System.currentTimeMillis() - angryStartTime > 1000);
    }

    public boolean isTimeout() {
        return (System.currentTimeMillis() - entryTime) > patienceTime;
    }

    private void drawPatienceBar(Graphics g) {
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - entryTime;
        long timeRemaining = patienceTime - timePassed;

        if (timeRemaining < 0) {
            timeRemaining = 0;
        }

        float percent = (float) timeRemaining / patienceTime;

        int barWidth = 100;
        int barHeight = 10;
        int barX = x + 25; 
        int barY = y - 10;

        g.setColor(new Color(50, 50, 50));
        g.fillRoundRect(barX, barY, barWidth, barHeight, 5, 5);

        if (percent > 0.5f) {
            g.setColor(Color.GREEN);
        } else if (percent > 0.2f) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.RED);
        }

        int currentWidth = (int) (barWidth * percent);
        g.fillRoundRect(barX, barY, currentWidth, barHeight, 5, 5);

        g.setColor(Color.WHITE);
        g.drawRoundRect(barX, barY, barWidth, barHeight, 5, 5);
    }
    
  public boolean isBubbleHit(int mouseX, int mouseY) {
       
        if (chatText.isEmpty() || System.currentTimeMillis() >= chatTimer) {
            return false; 
        }

       
        if (chatText.equals("Thanks! <3")) {
            return false;
        }
        

       
        int bubbleW = 220;
        int bubbleH = 50;
        
      
        int cx = x - 200; 
        int cy = y + 60;

        return mouseX >= cx && mouseX <= cx + bubbleW && 
               mouseY >= cy && mouseY <= cy + bubbleH;
    }
    
    public void soothe() {
       
        this.entryTime += 3000;
        if (this.entryTime > System.currentTimeMillis()) {
            this.entryTime = System.currentTimeMillis();
        }

      
        this.chatText = "Thanks! <3";
        this.chatTimer = System.currentTimeMillis() + 1000;
    }
    
    
    
}