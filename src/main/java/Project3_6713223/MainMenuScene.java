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

public class MainMenuScene extends JPanel implements ActionListener {
    
    private JButton startButton, creditsButton, tutorialButton, exitButton; // ลบ settingButton
    private SceneManager sceneManager;
    
    // 1. แก้ไข: Constructor ต้องรับ SceneManager
    public MainMenuScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // 1. ตั้งค่าพื้นฐาน
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        
        // 2. สร้าง Components
        Font buttonFont = new Font("Arial", Font.BOLD, 24);
        int buttonWidth = 200;
        int buttonHeight = 50;
        
        // 2.1 โหลดรูปภาพสำหรับปุ่มที่เหลือ
        MyImageIcon startIcon = ImageLoader.loadImageIcon(MyConstants.BUTTON_START_IMG);
        MyImageIcon creditsIcon = ImageLoader.loadImageIcon(MyConstants.BUTTON_CREDITS_IMG);
        MyImageIcon tutorialIcon = ImageLoader.loadImageIcon(MyConstants.BUTTON_TUTORIAL_IMG);
        MyImageIcon exitIcon = ImageLoader.loadImageIcon(MyConstants.BUTTON_EXIT_IMG);
        
        // **********************************
        // ลบ MyConstants.BUTTON_SETTING_IMG ออกไปแล้ว
        // **********************************
        
        startButton = createStyledButton("", buttonFont, buttonWidth, buttonHeight, startIcon);
        creditsButton = createStyledButton("", buttonFont, buttonWidth, buttonHeight, creditsIcon);
        tutorialButton = createStyledButton("", buttonFont, buttonWidth, buttonHeight, tutorialIcon); // TUTORIAL ไม่มีรูปภาพ
        exitButton = createStyledButton("", buttonFont, buttonWidth, buttonHeight, exitIcon); 
        
        // 3. เพิ่ม Listener
        startButton.addActionListener(this);
        creditsButton.addActionListener(this);
        tutorialButton.addActionListener(this); // TUTORIAL แทนที่ตำแหน่ง SETTING เดิม
        exitButton.addActionListener(this);
        
        // 4. จัดวาง Components ด้วย GridBagLayout (เรียงแนวตั้ง)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        
        // ปุ่ม START (gridy=0)
        gbc.gridy = 0; 
        gbc.insets = new Insets(150, 0, 10, 0); 
        this.add(startButton, gbc);
        
        // ปุ่ม CREDITS (เลื่อนขึ้นมาแทน SETTING เดิม)
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0); 
        this.add(creditsButton, gbc);
        
        // ปุ่ม TUTORIAL (เลื่อนขึ้นมาแทน CREDITS เดิม)
        gbc.gridy = 2;
        this.add(tutorialButton, gbc);
        
        // ปุ่ม EXIT
        gbc.gridy = 3;
        this.add(exitButton, gbc);
    }
    
    private JButton createStyledButton(String text, Font font, int width, int height, MyImageIcon icon) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setForeground(Color.BLACK); 
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false); 
        
        if (icon != null) {
            Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(scaledImage));
            btn.setHorizontalTextPosition(SwingConstants.CENTER);
            btn.setVerticalTextPosition(SwingConstants.CENTER);
        } else {
             btn.setBackground(new Color(255, 255, 255, 200)); 
             btn.setOpaque(true);
             btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            sceneManager.switchToScene("StartInput");
        } else if (e.getSource() == creditsButton) {
            sceneManager.switchToScene("Credit");
        } else if (e.getSource() == tutorialButton) { // TUTORIAL ยังคงพาไป Tutorial Scene
            sceneManager.switchToScene("Tutorial"); 
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}