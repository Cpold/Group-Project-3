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

public class TutorialScene extends JPanel implements ActionListener {

    private SceneManager sceneManager;
    private JLabel backgroundPanel;
    private JButton backButton;

    private final String FILE_TUTORIAL_GUIDE = MyConstants.PATH + "BreadMakerGuide.jpg"; 

    public TutorialScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null);
        this.setOpaque(true); // Must be true if backgroundPanel is added on top
        this.setBackground(Color.WHITE);

        backgroundPanel = new JLabel();
        MyImageIcon bgIcon = ImageLoader.loadImageIcon(FILE_TUTORIAL_GUIDE);
        if (bgIcon != null) {
            backgroundPanel.setIcon(bgIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT));
        } else {
            backgroundPanel.setOpaque(true);
            backgroundPanel.setBackground(Color.LIGHT_GRAY);
        }
        backgroundPanel.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
        backgroundPanel.setLayout(null);
        this.add(backgroundPanel);

              backButton = createStyledButton("BACK", 150, 50);
        backButton.addActionListener(this);
        backButton.setBounds(50, MyConstants.HEIGHT - 120, 150, 50);
        backgroundPanel.add(backButton);
        
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
        // Logics based on previous requests (BACK and READY TO EAT!)
        if (e.getSource() == backButton ) {
            sceneManager.switchToScene("Menu"); 
        }
    }
}