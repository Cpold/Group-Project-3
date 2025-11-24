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

public class EndScene extends JPanel implements ActionListener {
    
    private SceneManager sceneManager;
    private JButton playAgainButton;
    private JLabel backgroundPanel;
    
    // Path สำหรับรูปภาพ End Scene
    private final String FILE_END_BG = MyConstants.FILE_START_SCENE_BG; 
    private final String FILE_BREAD_LOST = MyConstants.FILE_BREAD_LOST; 
    private final String FILE_BREAD_WIN = MyConstants.FILE_BREAD_WIN; 

    private boolean isWin;
    private String playerName;

    // Constructor รับ SceneManager, ผลลัพธ์ (ชนะ/แพ้) และชื่อผู้เล่น
    public EndScene(SceneManager sceneManager, boolean isWin, String playerName) {
        this.sceneManager = sceneManager;
        this.isWin = isWin;
        this.playerName = playerName;
        
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); 
        this.setOpaque(true); 
        this.setBackground(Color.WHITE); 
        
        // 1. สร้าง JLabel สำหรับรูปภาพพื้นหลัง
        setupBackground();
        
        // 2. สร้าง Content ตามผลลัพธ์ (ว่างเปล่าสำหรับทั้ง Win และ Loss)
        setupContent();

        // 3. สร้างปุ่ม PLAY AGAIN! (แบบโปร่งใส)
        playAgainButton = createStyledButton("", 300, 70); 
        playAgainButton.addActionListener(this);
        
        // ตำแหน่งปุ่ม: ใช้ตำแหน่ง +220 ที่ปรับล่าสุด
        playAgainButton.setBounds(MyConstants.WIDTH / 2 - 150, MyConstants.HEIGHT / 2 + 220, 300, 70); 
        backgroundPanel.add(playAgainButton);
    }
    
    private void setupBackground() {
        backgroundPanel = new JLabel();
        
        String bgPath;
        if (isWin) {
            bgPath = FILE_BREAD_WIN; 
        } else {
            // **[ปรับแก้ไข]** ใช้ FILE_BREAD_LOST เป็นพื้นหลังสำหรับฉากแพ้
            bgPath = FILE_BREAD_LOST;    
        }

        MyImageIcon bgIcon = ImageLoader.loadImageIcon(bgPath);
        if (bgIcon != null) {
            backgroundPanel.setIcon(bgIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT));
        } else {
             backgroundPanel.setOpaque(true);
             backgroundPanel.setBackground(Color.LIGHT_GRAY); 
        }
        backgroundPanel.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT); 
        backgroundPanel.setLayout(null); 
        this.add(backgroundPanel); 
    }

    private void setupContent() {
        // **[ปรับแก้ไข]** ลบโค้ดทั้งหมดในเมธอดนี้
        // ทั้งฉากชนะและแพ้จะไม่มี Title, Subtitle หรือ Image แสดงผลแล้ว
    }
    
    private JButton createStyledButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 28)); 
        btn.setPreferredSize(new Dimension(width, height));
        
        // **[ปุ่มโปร่งใส]**
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setOpaque(false);
        
        // ตั้งค่าข้อความที่รับเข้ามา
        btn.setText(text);

        // ตั้งค่าเดิมที่ไม่จำเป็นต้องใช้สำหรับปุ่มใส
        btn.setForeground(new Color(139, 69, 19)); 
        btn.setBackground(new Color(255, 192, 203)); 
        btn.setFocusPainted(false);
        

        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playAgainButton) {
            // กลับไปหน้า Menu หลัก
            sceneManager.switchToScene("Menu"); 
        }
    }
}