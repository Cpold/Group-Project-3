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

public class CreditScene extends JPanel implements ActionListener {
    
    private SceneManager sceneManager;
    private JButton backButton;
    
    public CreditScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // 1. ตั้งค่าพื้นฐาน
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); // ใช้ null layout
        this.setOpaque(false); // ต้องโปร่งใสเพื่อให้เห็น Background
        
        // 2. สร้าง Header "CREDIT"
        /*JLabel title = new JLabel("CREDIT", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setForeground(Color.BLACK); // <<< ใช้สีขาว
        title.setBounds(MyConstants.WIDTH / 2 - 150, 100, 300, 70); 
        this.add(title);
        */
        
        // 3. สร้าง Panel สำหรับรายชื่อ (ใช้ GridBagLayout จัดให้อยู่ตรงกลาง)
        JPanel creditContentPanel = new JPanel(new GridBagLayout());
        creditContentPanel.setOpaque(false);
        
        // ข้อมูลรายชื่อสมาชิก
        String[][] members = {
            {"6713247", "Rachapon", "Wichipornchai"},
            {"6713247", "Ratchasin", "Karnjanabhunt"}, 
            {"6713250", "Sayklang", "Saramolee"}, 
            {"6713223", "Chayapol", "Dechsorn"},
            {"6713116", "Zabit", "Madali"}
        };
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        
        // Font ใหม่: ตัวหนา (Font.BOLD) และขนาดใหญ่ขึ้น
        Font memberFont = new Font("Arial", Font.BOLD, 30); 
        
        for (int i = 0; i < members.length; i++) {
            // ID (คอลัมน์ 0)
            JLabel idLabel = new JLabel(members[i][0], SwingConstants.LEFT);
            idLabel.setFont(memberFont);
            idLabel.setForeground(Color.BLACK); // <<< ใช้สีขาว
            gbc.gridx = 0; gbc.gridy = i;
            creditContentPanel.add(idLabel, gbc);
            
            // Name and Surname (คอลัมน์ 1)
            JLabel nameLabel = new JLabel(members[i][1] + " " + members[i][2], SwingConstants.LEFT);
            nameLabel.setFont(memberFont);
            nameLabel.setForeground(Color.BLACK); // <<< ใช้สีขาว
            gbc.gridx = 1; gbc.gridy = i;
            creditContentPanel.add(nameLabel, gbc);
        }
        
        // 4. กำหนดตำแหน่งและขนาดของ creditContentPanel
        int panelWidth = 650;
        int panelHeight = 350;
        creditContentPanel.setBounds(MyConstants.WIDTH / 2 - panelWidth / 2, 200, panelWidth, panelHeight); 
        this.add(creditContentPanel);
        
        // 5. สร้างปุ่ม BACK
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(this);
        
        // ตั้งค่า Style ให้ปุ่ม BACK ชัดเจน
        backButton.setForeground(Color.BLACK);
        backButton.setBackground(new Color(255, 255, 255, 200));
        backButton.setOpaque(true);
        backButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // ตำแหน่งของปุ่ม BACK ที่มุมล่างซ้าย
        backButton.setBounds(50, MyConstants.HEIGHT - 120, 150, 50); 
        this.add(backButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            sceneManager.switchToScene("Menu");
        }
    }
}