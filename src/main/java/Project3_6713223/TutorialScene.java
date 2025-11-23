/*
 * @author Rachapon - 6713247
 * Ratchasin - 6713247
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
    private JButton readyButton; // ลบ backButton ออก

    // Path สำหรับรูปภาพ BREAD MAKER GUIDE (ต้องกำหนดใน MyConstants.java)
    private final String FILE_TUTORIAL_GUIDE = MyConstants.PATH + "BreadMakerGuide.jpg"; 

    public TutorialScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); 
        this.setOpaque(false);
        
        // 1. สร้าง JLabel สำหรับรูปภาพพื้นหลัง Guide
        backgroundPanel = new JLabel();
        MyImageIcon bgIcon = ImageLoader.loadImageIcon(FILE_TUTORIAL_GUIDE);
        if (bgIcon != null) {
            backgroundPanel.setIcon(bgIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT));
        } else {
             backgroundPanel.setOpaque(true);
             backgroundPanel.setBackground(Color.WHITE); 
        }
        backgroundPanel.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT); 
        backgroundPanel.setLayout(null); 
        
        this.add(backgroundPanel); 
        
        // 2. สร้างปุ่ม READY TO EAT! (ล่องหน)
        readyButton = createInvisibleButton("", 300, 60); 
        readyButton.addActionListener(this);
        
        // ตำแหน่ง: ตรงกลางด้านล่างของ Guide Frame ในรูปภาพ (W=300, H=60)
        readyButton.setBounds(MyConstants.WIDTH / 2 - 150, MyConstants.HEIGHT / 2 + 195, 300, 60); 
        backgroundPanel.add(readyButton); 
    }
    
    private JButton createVisibleButton(String text, int width, int height) {
        // เมธอดนี้ถูกเก็บไว้ แต่ไม่ได้ถูกใช้ในฉากนี้
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 24)); 
        btn.setPreferredSize(new Dimension(width, height));
        btn.setForeground(Color.WHITE); 
        btn.setBackground(new Color(139, 69, 19)); 
        btn.setOpaque(true); 
        btn.setBorderPainted(true); 
        btn.setFocusPainted(false);
        return btn;
    }
    
    private JButton createInvisibleButton(String text, int width, int height) {
        // เมธอดสำหรับปุ่มที่ล่องหน (เช่น READY TO EAT!)
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 28)); 
        btn.setPreferredSize(new Dimension(width, height));
        btn.setForeground(Color.WHITE); 
        
        btn.setOpaque(false); 
        btn.setContentAreaFilled(false); 
        btn.setBorderPainted(false); 
        btn.setFocusPainted(false);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // ตรวจสอบเฉพาะ readyButton เท่านั้น
        if (e.getSource() == readyButton) {
            sceneManager.switchToScene("Menu"); // กลับไปหน้า Menu
        }
    }
}