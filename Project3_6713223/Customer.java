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
    private int x, y;
    private MyImageIcon icon;
    private MyImageIcon angryIcon;
    private String orderName;
    
   private boolean isAngry = false;
   private long angryStartTime = 0;
  
   

    //Menu
    private static final String[] MENU_LIST = {
        "Custard Choc Chip Toast", "Custard Foi Thong Toast", "Custard Marshmallow Toast",
        "Chocolate Choc Chip Toast", "Chocolate Foi Thong Toast", "Chocolate Marshmallow Toast",
        "Thai Tea Choc Chip Toast", "Thai Tea Foi Thong Toast", "Thai Tea Marshmallow Toast"
    };

    public Customer(int x, int y) {
        this.x = x;
        this.y = y;
       
        
       
        Random rand = new Random();
        this.orderName = MENU_LIST[rand.nextInt(MENU_LIST.length)];
        
      
        String[] imgPaths = {
            MyConstants.CUSTOMER_1, MyConstants.CUSTOMER_2, 
            MyConstants.CUSTOMER_3,
            MyConstants.CUSTOMER_5,MyConstants.CUSTOMER_6,
            MyConstants.CUSTOMER_7,MyConstants.CUSTOMER_8,
            MyConstants.CUSTOMER_9
        };
        
        String[] angryPaths = {
           
           MyConstants.CUS_ANGRY_1, MyConstants.CUS_ANGRY_2, 
            MyConstants.CUS_ANGRY_3, 
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
            
            // (Optional) วาดเครื่องหมายตกใจ หรือข้อความบ่น
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 24));
            g.drawString("!!!", x + 120, y); 

        }
        
        else if (icon != null) {
            g.drawImage(icon.getImage(), x, y, null);
            
          
            g.setColor(Color.WHITE);
            g.fillRoundRect(x + 150, y - 10, 180, 60, 20, 20);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x + 150, y - 10, 180, 60, 20, 20);
            
         
            g.setFont(new Font("Arial", Font.BOLD, 12));
            g.drawString("I want:", x + 160, y + 10);
            g.drawString(orderName, x + 160, y + 30);
        }
    }

   
    public boolean checkOrder(Bread b) {
        if (b == null) return false;
        System.out.println("Customer wants: " + orderName + " | Got: " + b.getMenuName());
        return orderName.equals(b.getMenuName());
    }

   
    public boolean isHit(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + 150 && 
               mouseY >= y && mouseY <= y + 250;
    }
    
    public void makeAngry() {
        this.isAngry = true;
        this.angryStartTime = System.currentTimeMillis();
    }
    
    public boolean isAngryFinished() {
      
        return isAngry && (System.currentTimeMillis() - angryStartTime > 1000);
    }
    
   
   
}