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

public class MusicScene extends JPanel implements ActionListener {
    
    private SceneManager sceneManager;
    public static ButtonGroup musicGroup; 
    private JButton backButton, startButton;
    private JLabel backgroundPanel;
    
    private final String[] musicTitles = {
        "Chill Jazz (Default)", 
        "Upbeat Pop", 
        "Lo-fi Beats", 
        "Classic Rock", 
        "8-bit Retro"
    };

    public MusicScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); 
        this.setOpaque(true);
        this.setBackground(Color.WHITE); 
        
        MyImageIcon bgIcon = ImageLoader.loadImageIcon(MyConstants.FILE_MUSIC_BG);
        backgroundPanel = new JLabel();
        backgroundPanel.setIcon(bgIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT));
        backgroundPanel.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
        backgroundPanel.setLayout(null); 
        this.add(backgroundPanel); 

        
        // Panel สำหรับรวม JRadioButton
        JPanel radioPanel = setupRadioButtons();
        
        // กำหนดขนาด Panel
        // 🌟 แก้ไข: เพิ่มความกว้างเพื่อให้พอดีกับข้อความ "Chill Jazz (Default)" 🌟
        int panelWidth = 450; 
        int panelHeight = 300; 
        
        // 🌟 โค้ดที่แก้ไข: นำค่าชดเชย +30 ออก เพื่อให้ Panel ที่กว้างขึ้นอยู่กึ่งกลางอีกครั้ง (200 คือ Y ที่ถูกเลื่อนขึ้น)
        radioPanel.setBounds(MyConstants.WIDTH / 2 - panelWidth / 2 + 120, 200, panelWidth, panelHeight); 
        backgroundPanel.add(radioPanel);
        
        // ปุ่ม BACK
        backButton = createStyledButton("BACK", 150, 50);
        backButton.addActionListener(this);
        backButton.setBounds(50, MyConstants.HEIGHT - 120, 150, 50);
        backgroundPanel.add(backButton);
        
        // ปุ่ม START (เพื่อเริ่มเกมจริง)
        startButton = createStyledButton("START", 150, 50);
        startButton.addActionListener(this);
        startButton.setBounds(MyConstants.WIDTH - 200, MyConstants.HEIGHT - 120, 150, 50);
        backgroundPanel.add(startButton);
    }
    
    private JPanel setupRadioButtons() {
        JPanel panel = new JPanel();
        // เพิ่มระยะห่างแนวตั้ง (vgap) เป็น 30 พิกเซล
        panel.setLayout(new GridLayout(musicTitles.length, 1, 0, 30)); 
        panel.setOpaque(false);
        
        musicGroup = new ButtonGroup();
        
        for (int i = 0; i < musicTitles.length; i++) {
            JRadioButton radioBtn = new JRadioButton(musicTitles[i]);
            // 🌟 NOTE: ปรับ Font.BOLD เป็น Font.PLAIN ถ้าต้องการให้ดูเหมือนในภาพ แต่ใช้ 30
            // เนื่องจากคุณส่งโค้ดที่ใช้ Font.BOLD, 30 มา ผมจึงรักษาส่วนนี้ไว้
            radioBtn.setFont(new Font("Arial", Font.BOLD, 30));
            radioBtn.setOpaque(false);
            radioBtn.setForeground(Color.WHITE);
            radioBtn.setActionCommand(musicTitles[i]); // กำหนดคำสั่ง
            
            if (i == 0) {
                radioBtn.setSelected(true); // เลือกค่าแรกเป็นค่าเริ่มต้น
            }
            
            musicGroup.add(radioBtn);
            panel.add(radioBtn);
        }
        return panel;
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
            sceneManager.switchToScene("Difficulty"); // กลับไปหน้าเลือกความยาก
        } else if (e.getSource() == startButton) {
            String selectedMusic = musicGroup.getSelection().getActionCommand();
            sceneManager.switchToScene("Game"); // เริ่มเกมจริง
        }
    }
}