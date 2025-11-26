/*
 * @author Rachapon - 6713247
 * Ratchasin - 6713248
 * Sayklang - 6713250
 * Chayapol - 6713223
 * Zabit - 6713116
 */
package Project3_6713223;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditScene extends JPanel implements ActionListener {
    
    private SceneManager sceneManager;
    private JButton backButton;
    private JLabel backgroundLabel;
    
    private final String[][] members = {
        {"6713247", "Rachapon Wichipornchai"},
        {"6713248", "Ratchasin Karnjanabhunt"},
        {"6713250", "Sayklang Saramolee"},
        {"6713223", "Chayapol Dechsorn"},
        {"6713116", "Zabit Madali"}
    };
    
    public CreditScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); 
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        
        backgroundLabel = new JLabel();
        MyImageIcon bgIcon = ImageLoader.loadImageIcon(MyConstants.FILE_CREDIT_BG);
        if (bgIcon != null) {
            backgroundLabel.setIcon(bgIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT));
        } else {
             backgroundLabel.setOpaque(true);
             backgroundLabel.setBackground(Color.DARK_GRAY); 
        }
        backgroundLabel.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT); 
        backgroundLabel.setLayout(null);
        this.add(backgroundLabel); 

        backButton = createStyledButton("BACK", 150, 50);
        backButton.addActionListener(this);
        backButton.setBounds(50, MyConstants.HEIGHT - 120, 150, 50);
        backgroundLabel.add(backButton);
    }
     private JButton createStyledButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 24));
        btn.setPreferredSize(new Dimension(width, height));
        btn.setForeground(Color.WHITE); 
        btn.setBackground(new Color(139, 69, 19)); 
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            sceneManager.switchToScene("Menu");
        }
    }
}