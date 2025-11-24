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
    private JLabel backgroundLabel; // ใช้สำหรับแสดงภาพพื้นหลัง Food Truck
    
    // Path สำหรับรูปภาพพื้นหลังหลัก (Food Truck) และปุ่ม BACK
    private final String FILE_BACK_BUTTON_IMG = MyConstants.FILE_BACK_BUTTON_IMG;
    
    // ข้อมูลรายชื่อสมาชิก (รวมนามสกุลไว้ในคอลัมน์ 1)
    private final String[][] members = {
        {"6713247", "Rachapon Wichipornchai"},
        {"6713248", "Ratchasin Karnjanabhunt"},
        {"6713250", "Sayklang Saramolee"},
        {"6713223", "Chayapol Dechsorn"},
        {"6713116", "Zabit Madali"}
    };
    
    public CreditScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // 1. ตั้งค่าพื้นฐาน
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); 
        this.setOpaque(true); // ตั้งเป็น true เพื่อแสดง BG ที่เราจะใส่เข้าไป
        this.setBackground(Color.WHITE);
        
        // 2. สร้าง JLabel สำหรับรูปภาพพื้นหลัง Food Truck
        backgroundLabel = new JLabel();
        MyImageIcon bgIcon = ImageLoader.loadImageIcon(MyConstants.FILE_CREDIT_BG);
        if (bgIcon != null) {
            backgroundLabel.setIcon(bgIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT));
        } else {
             backgroundLabel.setOpaque(true);
             backgroundLabel.setBackground(Color.DARK_GRAY); 
        }
        backgroundLabel.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT); 
        backgroundLabel.setLayout(null); // ใช้ null layout สำหรับวาง Components ทับ
        this.add(backgroundLabel); 

        /*
        // 3. สร้าง Panel สำหรับรายชื่อ (GridBagLayout)
        JPanel creditContentPanel = new JPanel(new GridBagLayout());
        creditContentPanel.setOpaque(false); // สำคัญ: ต้องโปร่งใสเพื่อเห็น Food Truck
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.insets = new Insets(10, 20, 10, 20);
        
        // Font ใหม่: ตัวหนา (Font.BOLD)
        Font memberFont = new Font("Arial", Font.BOLD, 30); 
        
        for (int i = 0; i < members.length; i++) {
            // ID (คอลัมน์ 0)
            JLabel idLabel = new JLabel(members[i][0], SwingConstants.LEFT);
            idLabel.setFont(memberFont);
            idLabel.setForeground(Color.BLACK); // <<< สีดำตามภาพ
            gbc.gridx = 0; gbc.gridy = i;
            creditContentPanel.add(idLabel, gbc);
            
            // Name and Surname (คอลัมน์ 1)
            JLabel nameLabel = new JLabel(members[i][1], SwingConstants.LEFT);
            nameLabel.setFont(memberFont);
            nameLabel.setForeground(Color.BLACK); // <<< สีดำตามภาพ
            gbc.gridx = 1; gbc.gridy = i;
            creditContentPanel.add(nameLabel, gbc);
        }
        
        // กำหนดตำแหน่ง Credit Panel (กลางจอ เยื้องลงมาเล็กน้อย)
        int panelWidth = 650;
        int panelHeight = 350;
        creditContentPanel.setBounds(MyConstants.WIDTH / 2 - panelWidth / 2, 200, panelWidth, panelHeight); 
        backgroundLabel.add(creditContentPanel);
        */
       
        // 4. สร้างปุ่ม BACK (ใช้รูปภาพ)
        MyImageIcon backButtonIcon = ImageLoader.loadImageIcon(FILE_BACK_BUTTON_IMG);
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
        
        // ตำแหน่ง BACK: มุมล่างซ้าย
        backButton.setBounds(20, MyConstants.HEIGHT - 70 - 45, 150, 70); 
        backButton.addActionListener(this);
        backgroundLabel.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            sceneManager.switchToScene("Menu");
        }
    }
}