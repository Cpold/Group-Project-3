/*
 * @author Rachapon - 6713247
 * Ratchasin - 6713248
 * Sayklang - 6713250
 * Chayapol - 6713223
 * Zabit - 6713116
 */
package Project3_6713223;

import java.awt.Color;
import java.awt.Graphics2D;

public class Effect_Graphics {
    
    private float x, y;      
    private float vx, vy;    
    private float size;    
    private Color color;    
    private int life = 255; 

   
    public Effect_Graphics(int startX, int startY, Color c) {
        this.x = startX;
        this.y = startY;
        this.color = c;
        
        this.size = 5 + (float)(Math.random() * 8);
        
       
        double angle = Math.random() * Math.PI * 2;
        double speed = 2 + Math.random() * 6;
        
        this.vx = (float)(Math.cos(angle) * speed);
        this.vy = (float)(Math.sin(angle) * speed);
    }

  
    public Effect_Graphics(int startX, int startY, boolean isSuccess) {
        this.x = startX;
        this.y = startY;
        
        this.size = 10 + (float)(Math.random() * 15);
        

        if (isSuccess) {
           
            if (Math.random() > 0.5) this.color = new Color(255, 215, 0); // Gold
            else this.color = new Color(50, 255, 50); // Green
        } else {
            this.color = new Color(255, 50, 50); // Red (Fail)
        }

        
        double angle = Math.random() * Math.PI * 2;
        double speed = 3 + Math.random() * 9; 
        
        this.vx = (float)(Math.cos(angle) * speed);
        this.vy = (float)(Math.sin(angle) * speed);
    }

    public boolean update() {
        x += vx;
        y += vy;
        
        vy += 0.5f;  // Gravity
        vx *= 0.95f; // Friction
        
        life -= 10; 
        
        return life > 0;
    }

    public void draw(Graphics2D g2) {
        if (life < 0) return;
        
    
        int alpha = life;
        if (alpha < 0) alpha = 0;
        if (alpha > 255) alpha = 255;
        
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
        g2.fillOval((int)x, (int)y, (int)size, (int)size);
    }
    
   
}

