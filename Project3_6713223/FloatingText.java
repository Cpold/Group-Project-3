package Project3_6713223;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/*
 * @author Rachapon - 6713247
 * Ratchasin - 6713248
 * Sayklang - 6713250
 * Chayapol - 6713223
 * Zabit - 6713116
 */
class FloatingText {

    String text;
    int x, y;
    Color color;
    int life = 150;
    int yOffset = 0;

    public FloatingText(String text, int x, int y, Color color) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public boolean update() {
        yOffset -= 1;
        life--;
        return life > 0;
    }

    public void draw(Graphics2D g) {
        if (life <= 0) {
            return;
        }

        int alpha = (int) ((life / 60.0) * 255);
        if (alpha > 255) {
            alpha = 255;
        }
        if (alpha < 0) {
            alpha = 0;
        }

        int finalX = x - 170;
        int finalY = y + 150 + yOffset;

        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));

        g.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
       g.drawString(text, finalX + 2, finalY + 2); 
       g.drawString(text, finalX, finalY);
       g.drawString(text, finalX+400, finalY-20);
       g.drawString(text, finalX +400+2, finalY -20+2); 
    }
}
