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

public class StartScene extends JPanel implements ActionListener {
    
    private SceneManager sceneManager;
    private JTextField nameInputField; 
    private JButton backButton, confirmButton;
    private JLabel backgroundPanel; 
    
    // Path สำหรับรูปภาพพื้นหลัง (กรอบไม้) และปุ่ม BACK
    private final String FILE_FRAME_BG = MyConstants.FILE_START_SCENE_BG; 
    
    public StartScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        // 1. ตั้งค่าพื้นฐานของ JPanel หลัก
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); 
        this.setOpaque(false); // โปร่งใส เพื่อให้เห็น backgroundPanel ทับภาพ Food Truck
        
        // 2. สร้าง JLabel สำหรับรูปภาพพื้นหลังเฟรมไม้
        backgroundPanel = new JLabel();
        MyImageIcon bgIcon = ImageLoader.loadImageIcon(FILE_FRAME_BG);
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
        
        // 3.1 JTextField สำหรับใส่ชื่อ (ล่องหน)
        nameInputField = new JTextField(15); 
        nameInputField.setFont(new Font("Monospaced", Font.PLAIN, 30)); 
        nameInputField.setHorizontalAlignment(JTextField.CENTER);
        
        // ทำให้ JTextField โปร่งใสและไร้ขอบ 
        nameInputField.setOpaque(false); 
        nameInputField.setBorder(null);  
        
        // ตำแหน่ง JTextField: (Y + 15)
        nameInputField.setBounds(MyConstants.WIDTH / 2 - 280, MyConstants.HEIGHT / 2 + 15, 560, 60); 
        backgroundPanel.add(nameInputField); 
        
        // 4. สร้างปุ่ม CONFIRM (ล่องหน)
        confirmButton = createInvisibleButton("", 250, 70); 
        confirmButton.addActionListener(this);
        // ตำแหน่งปุ่ม CONFIRM: ตรงกับกรอบปุ่มในรูปภาพ
        confirmButton.setBounds(MyConstants.WIDTH / 2 - 125, MyConstants.HEIGHT / 2 + 130, 250, 70); 
        backgroundPanel.add(confirmButton); 
        
        // 5. สร้างปุ่ม BACK (ใช้รูปภาพ)
        backButton = createStyledButton("BACK", 150, 50);
        backButton.addActionListener(this);
        backButton.setBounds(50, MyConstants.HEIGHT - 120, 150, 50);
        backgroundPanel.add(backButton);
       
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
        if (e.getSource() == confirmButton) {
            String playerName = nameInputField.getText().trim();
            sceneManager.currentPlayerName = playerName;
            if (playerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                                              "Please enter your name to continue.", 
                                              "Input Required", 
                                              JOptionPane.WARNING_MESSAGE);
                nameInputField.requestFocusInWindow(); 
                return; 
            }
            
            sceneManager.switchToScene("Difficulty");
        } else if (e.getSource() == backButton) {
            sceneManager.switchToScene("Menu"); // กลับไปหน้า Main Menu
        }
    }
}