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

public class DifficultyScene extends JPanel implements ActionListener {
    
    private SceneManager sceneManager;
    private JComboBox<String> modeComboBox;
    private JButton backButton, startButton;
    
    private final String[] difficultyLevels = {"Very Easy", "Easy", "Normal", "Hard", "Very Hard"};

    public DifficultyScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); 
        this.setOpaque(false);
        
        // Title "MODE"
        JLabel titleLabel = new JLabel("MODE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(MyConstants.WIDTH / 2 - 100, 250, 200, 70); // เลื่อนลง
        this.add(titleLabel);
        
        // JComboBox สำหรับเลือกความยาก
        modeComboBox = new JComboBox<>(difficultyLevels);
        modeComboBox.setFont(new Font("Arial", Font.PLAIN, 24));
        modeComboBox.setBounds(MyConstants.WIDTH / 2 - 150, 350, 300, 40); // เลื่อนลง
        this.add(modeComboBox);
        
        // ปุ่ม BACK
        backButton = createStyledButton("BACK", 150, 50);
        backButton.addActionListener(this);
        backButton.setBounds(50, MyConstants.HEIGHT - 120, 150, 50);
        this.add(backButton);
        
        // ปุ่ม START
        startButton = createStyledButton("NEXT", 150, 50);
        startButton.addActionListener(this);
        startButton.setBounds(MyConstants.WIDTH - 200, MyConstants.HEIGHT - 120, 150, 50);
        this.add(startButton);
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
            sceneManager.switchToScene("StartInput"); 
        } else if (e.getSource() == startButton) {
            String selectedMode = (String) modeComboBox.getSelectedItem();
            sceneManager.switchToScene("Music"); // ไปหน้าเลือกเพลง
        }
    }
}