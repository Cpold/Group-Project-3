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
        this.setOpaque(true); // ต้องทึบแสงเพื่อไม่ให้เห็นฉาก GameBoard ข้างใต้
        this.setBackground(Color.WHITE); 
        
        // 1. สร้าง JLabel สำหรับรูปภาพพื้นหลังเฟรมไม้
        setupBackground();
        
        // 2. สร้าง Content ตามผลลัพธ์ (YOU LOST / CONGRATULATIONS)
        setupContent();

        // 3. สร้างปุ่ม PLAY AGAIN!
        playAgainButton = createStyledButton("PLAY AGAIN!", 300, 70); 
        playAgainButton.addActionListener(this);
        
        // ตำแหน่งปุ่ม: ตรงกลางด้านล่างของเฟรมไม้
        playAgainButton.setBounds(MyConstants.WIDTH / 2 - 150, MyConstants.HEIGHT / 2 + 180, 300, 70); 
        backgroundPanel.add(playAgainButton);
    }
    
    private void setupBackground() {
        backgroundPanel = new JLabel();
        MyImageIcon bgIcon = ImageLoader.loadImageIcon(FILE_END_BG);
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
        Color textColor = Color.WHITE; 
        Font titleFont = new Font("Arial", Font.BOLD, 50);
        Font subFont = new Font("Arial", Font.PLAIN, 30);
        
        // ----- Title (YOU LOST! / CONGRATULATIONS!)
        String titleText = isWin ? "CONGRATULATIONS!" : "YOU LOST!";
        JLabel titleLabel = new JLabel(titleText, SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
        titleLabel.setBounds(MyConstants.WIDTH / 2 - 350, MyConstants.HEIGHT / 2 - 250, 700, 60);
        backgroundPanel.add(titleLabel);
        
        // ----- Subtitle
        String subText = isWin ? "YOU ARE THE BREAD MASTER!" : "TRY AGAIN NEXT TIME!";
        JLabel subLabel = new JLabel(subText, SwingConstants.CENTER);
        subLabel.setFont(subFont);
        subLabel.setForeground(textColor);
        subLabel.setBounds(MyConstants.WIDTH / 2 - 350, MyConstants.HEIGHT / 2 - 190, 700, 40);
        backgroundPanel.add(subLabel);

        // ----- Image (Burnt Bread / Master Bread)
        String imagePath = isWin ? FILE_BREAD_WIN : FILE_BREAD_LOST;
        JLabel imageLabel = new JLabel();
        MyImageIcon breadIcon = ImageLoader.loadImageIcon(imagePath);
        if (breadIcon != null) {
            imageLabel.setIcon(breadIcon.resize(200, 200)); 
        }
        imageLabel.setBounds(MyConstants.WIDTH / 2 - 100, MyConstants.HEIGHT / 2 - 100, 200, 200);
        backgroundPanel.add(imageLabel);

        // ----- Player Name Field (เฉพาะฉากชนะ)
        if (isWin) {
            JLabel namePrompt = new JLabel("YOUR NAME:", SwingConstants.CENTER);
            namePrompt.setFont(subFont);
            namePrompt.setForeground(textColor);
            namePrompt.setBounds(MyConstants.WIDTH / 2 - 350, MyConstants.HEIGHT / 2 - 140, 300, 40);
            backgroundPanel.add(namePrompt);
            
            // แสดงชื่อผู้เล่นที่กรอกเข้ามาในกล่องข้อความ
            JTextField nameDisplay = new JTextField(playerName);
            nameDisplay.setFont(subFont);
            nameDisplay.setEditable(false); // ไม่อนุญาตให้แก้ไข
            nameDisplay.setHorizontalAlignment(JTextField.CENTER);
            nameDisplay.setBounds(MyConstants.WIDTH / 2 - 50, MyConstants.HEIGHT / 2 - 140, 250, 40);
            backgroundPanel.add(nameDisplay);
        }
    }
    
    private JButton createStyledButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 28)); 
        btn.setPreferredSize(new Dimension(width, height));
        btn.setForeground(new Color(139, 69, 19)); // สีน้ำตาล
        
        // สร้างปุ่มสีชมพูตามรูปภาพ
        btn.setBackground(new Color(255, 192, 203)); 
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 3)); 
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