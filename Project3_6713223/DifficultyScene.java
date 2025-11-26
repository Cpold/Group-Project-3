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

public class DifficultyScene extends JPanel implements ActionListener {
    
    private SceneManager sceneManager;
    private JLabel backgroundPanel;
    private JButton backButton, nextButton;
    public JList<String> difficultyList;
    private JScrollPane scrollPane;
    
    private final String[] difficultyLevels = {"Very Easy", "Easy", "Normal", "Hard", "Very Hard"};

    private final String FILE_FRAME_BG = MyConstants.FILE_DIFFICULTY_BG; 
    
    public DifficultyScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        this.setPreferredSize(new Dimension(MyConstants.WIDTH, MyConstants.HEIGHT));
        this.setLayout(null); 
        this.setOpaque(true); 
        this.setBackground(Color.WHITE); 
        
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
        
        difficultyList = new JList<>(difficultyLevels);
        difficultyList.setFont(new Font("Arial", Font.BOLD, 30)); 
        difficultyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        difficultyList.setSelectedIndex(2);
        
        difficultyList.setOpaque(false); 
        difficultyList.setForeground(Color.WHITE); 
        difficultyList.setBackground(new Color(0, 0, 0, 0)); 
        
        difficultyList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setOpaque(false); 
                label.setForeground(Color.WHITE); 
                
                if (isSelected) {
                    label.setOpaque(true);
                    label.setBackground(new Color(255, 255, 255, 150)); 
                    label.setForeground(Color.BLACK);
                }
                return label;
            }
        });
        
        scrollPane = new JScrollPane(difficultyList);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); 
        scrollPane.setBorder(null); 
        
        int listWidth = 350;
        int listHeight = 250;
        scrollPane.setBounds(MyConstants.WIDTH / 2 - 175, MyConstants.HEIGHT / 2 - 70, listWidth, listHeight); 
        backgroundPanel.add(scrollPane);

        backButton = createStyledButton("BACK", 150, 50);
        backButton.addActionListener(this);
        backButton.setBounds(50, MyConstants.HEIGHT - 120, 150, 50);
        backgroundPanel.add(backButton);
        
        nextButton = createStyledButton("NEXT", 150, 50);
        nextButton.addActionListener(this);
        nextButton.setBounds(MyConstants.WIDTH - 200, MyConstants.HEIGHT - 120, 150, 50);
        backgroundPanel.add(nextButton);
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
        if (e.getSource() == backButton) {
            sceneManager.switchToScene("StartInput");
        } else if (e.getSource() == nextButton) {
            if (difficultyList.getSelectedIndex() == -1) {
                 JOptionPane.showMessageDialog(this, "Please select a difficulty level.", "Selection Required", JOptionPane.WARNING_MESSAGE);
                 return;
            }
            sceneManager.switchToScene("Music"); 
        }
    }
}