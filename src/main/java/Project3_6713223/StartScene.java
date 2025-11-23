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

public class StartScene extends JPanel implements ActionListener {
    
    private SceneManager sceneManager;
    private JTextField nameInputField; 
    private JButton backButton, confirmButton; // กู้คืน confirmButton
    private JLabel backgroundPanel; 
    
    public StartScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // 1. ตั้งค่าพื้นฐานของ JPanel หลัก
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); 
        this.setOpaque(false); 
        
        // 2. สร้าง JLabel สำหรับรูปภาพพื้นหลังเฟรมไม้
        backgroundPanel = new JLabel();
        MyImageIcon bgIcon = ImageLoader.loadImageIcon(MyConstants.FILE_START_SCENE_BG);
        if (bgIcon != null) {
            backgroundPanel.setIcon(bgIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT));
        } else {
             backgroundPanel.setOpaque(true);
             backgroundPanel.setBackground(Color.LIGHT_GRAY); 
        }
        backgroundPanel.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT); 
        backgroundPanel.setLayout(null); 
        
        this.add(backgroundPanel); 
        
        // 3. สร้าง Components 
        
        // 3.1 JTextField สำหรับใส่ชื่อ
        nameInputField = new JTextField(15); 
        nameInputField.setFont(new Font("Monospaced", Font.PLAIN, 30)); 
        nameInputField.setHorizontalAlignment(JTextField.CENTER);
        
        // ทำให้ JTextField โปร่งใสและไร้ขอบ 
        nameInputField.setOpaque(false); 
        nameInputField.setBorder(null);  
        
        // ตำแหน่ง JTextField: (Y + 15)
        nameInputField.setBounds(MyConstants.WIDTH / 2 - 280, MyConstants.HEIGHT / 2 + 15, 560, 60); 
        backgroundPanel.add(nameInputField); 
        
        // 4. สร้างและวางปุ่ม
        
        // 4.1 ปุ่ม BACK (มีกรอบและมองเห็นได้)
        backButton = createVisibleButton("BACK", 150, 50); // ใช้เมธอดสร้างปุ่มที่มองเห็นได้
        backButton.addActionListener(this);
        backButton.setBounds(50, MyConstants.HEIGHT - 120, 150, 50); 
        backgroundPanel.add(backButton); 
        
        // 4.2 ปุ่ม CONFIRM (ล่องหน)
        confirmButton = createInvisibleButton("", 250, 70); // <<< สร้างปุ่มล่องหนกลับมา
        confirmButton.addActionListener(this);
        
        // ตำแหน่งปุ่ม CONFIRM: (ใช้ค่าเดิมเพื่อให้ตรงกับพื้นที่รูปปุ่ม)
        confirmButton.setBounds(MyConstants.WIDTH / 2 - 125, MyConstants.HEIGHT / 2 + 130, 250, 70); 
        backgroundPanel.add(confirmButton); 
    }
    
    // เมธอดสำหรับสร้างปุ่มที่มีกรอบและพื้นหลัง (BACK)
    private JButton createVisibleButton(String text, int width, int height) {
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
    
    // เมธอดสำหรับสร้างปุ่มล่องหน (CONFIRM)
    private JButton createInvisibleButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 28)); 
        btn.setPreferredSize(new Dimension(width, height));
        btn.setForeground(Color.WHITE); 
        
        // ทำให้ปุ่มมองไม่เห็น (โปร่งใส)
        btn.setOpaque(false); 
        btn.setContentAreaFilled(false); 
        btn.setBorderPainted(false); 
        btn.setFocusPainted(false);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1. จัดการปุ่ม CONFIRM
        if (e.getSource() == confirmButton) {
            String playerName = nameInputField.getText().trim();
            
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                                              "Please enter your name to continue.", 
                                              "Input Required", 
                                              JOptionPane.WARNING_MESSAGE);
                nameInputField.requestFocusInWindow(); 
                return; 
            }
            
            sceneManager.switchToScene("Difficulty");
        } 
        // 2. จัดการปุ่ม BACK
        else if (e.getSource() == backButton) {
            sceneManager.switchToScene("Menu"); // กลับไปหน้า Main Menu
        }
    }
}