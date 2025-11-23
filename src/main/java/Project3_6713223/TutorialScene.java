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
    private JButton readyButton; // Assuming readyButton still exists for "READY TO EAT!"

    private final String FILE_TUTORIAL_GUIDE = MyConstants.PATH + "BreadMakerGuide.jpg"; 

    public TutorialScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null);
        this.setOpaque(true); // Must be true if backgroundPanel is added on top
        this.setBackground(Color.WHITE);

        // 1. สร้าง JLabel สำหรับรูปภาพพื้นหลัง (Guide Image)
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

        // 2. ปุ่ม BACK (ใช้รูปภาพและย้ายไปมุมล่างซ้าย)
        MyImageIcon backButtonIcon = ImageLoader.loadImageIcon(MyConstants.FILE_BACK_BUTTON_IMG);
        if (backButtonIcon != null) {
            backButton = new JButton(backButtonIcon.resize(150, 70));
            backButton.setOpaque(false);
            backButton.setContentAreaFilled(false);
            backButton.setBorderPainted(false);
            backButton.setFocusPainted(false);
        } else {
            backButton = new JButton("BACK");
            backButton.setFont(new Font("Arial", Font.BOLD, 28));
        }
        // 🛠️ ปรับตำแหน่ง: เลื่อนขึ้น 10px (Y: -30 instead of -20)
        backButton.setBounds(20, MyConstants.HEIGHT - 70 - 45, 150, 70); 
        backButton.addActionListener(this);
        backgroundPanel.add(backButton);
        
        // 3. สร้างปุ่ม READY TO EAT! (ล่องหน)
        readyButton = createInvisibleButton("", 300, 60); 
        readyButton.addActionListener(this);
        // ตำแหน่ง: ตรงกลางด้านล่างของ Guide Frame ในรูปภาพ (ใช้ค่าเดิม)
        readyButton.setBounds(MyConstants.WIDTH / 2 - 150, MyConstants.HEIGHT / 2 + 195, 300, 60); 
        backgroundPanel.add(readyButton);
    }
    
    private JButton createInvisibleButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 28)); 
        btn.setPreferredSize(new Dimension(width, height));
        btn.setOpaque(false); 
        btn.setContentAreaFilled(false); 
        btn.setBorderPainted(false); 
        btn.setFocusPainted(false);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Logics based on previous requests (BACK and READY TO EAT!)
        if (e.getSource() == backButton || e.getSource() == readyButton) {
            sceneManager.switchToScene("Menu"); 
        }
    }
}