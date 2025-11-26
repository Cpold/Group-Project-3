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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainMenuScene extends JPanel implements ActionListener {
    
    private JButton startButton, creditsButton, tutorialButton, exitButton;
    private SceneManager sceneManager;
    public MySoundEffect menuthemeSound;
    
    // Components Volume Control
    public JSlider volumeSlider;
    private JLabel musicLabel;
    private JButton closeVolumeButton;
    private JPanel volumeControlPanel;
    private JButton volumeIcon; 
    
    //Constructor SceneManager
    public MainMenuScene(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        
        Font buttonFont = new Font("Arial", Font.BOLD, 24);
        int buttonWidth = 200;
        int buttonHeight = 50;
        
        MyImageIcon startIcon = ImageLoader.loadImageIcon(MyConstants.BUTTON_START_IMG);
        MyImageIcon creditsIcon = ImageLoader.loadImageIcon(MyConstants.BUTTON_CREDITS_IMG);
        MyImageIcon tutorialIcon = ImageLoader.loadImageIcon(MyConstants.BUTTON_TUTORIAL_IMG);
        MyImageIcon exitIcon = ImageLoader.loadImageIcon(MyConstants.BUTTON_EXIT_IMG);
        
        // **********************************
        // MyConstants.BUTTON_SETTING_IMG
        // **********************************
        
        startButton = createStyledButton("", buttonFont, buttonWidth, buttonHeight, startIcon);
        creditsButton = createStyledButton("", buttonFont, buttonWidth, buttonHeight, creditsIcon);
        tutorialButton = createStyledButton("", buttonFont, buttonWidth, buttonHeight, tutorialIcon);
        exitButton = createStyledButton("", buttonFont, buttonWidth, buttonHeight, exitIcon); 
        
        startButton.addActionListener(this);
        creditsButton.addActionListener(this);
        tutorialButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        
        gbc.gridy = 0; 
        gbc.insets = new Insets(150, 0, 10, 0); 
        this.add(startButton, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0); 
        this.add(creditsButton, gbc);
        
        gbc.gridy = 2;
        this.add(tutorialButton, gbc);
        
        gbc.gridy = 3;
        this.add(exitButton, gbc);
        
        setupVolumeControl();
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
        } else if (e.getSource() == tutorialButton) {
            sceneManager.switchToScene("Tutorial"); 
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
    
        public void setupVolumeControl() {
        menuthemeSound = new MySoundEffect(MyConstants.SONG1);
        menuthemeSound.playLoop();
        menuthemeSound.setVolume(0.5f);
        // Panel Volume Control (JSlider)
        volumeControlPanel = new JPanel();
        volumeControlPanel.setLayout(null); 
        volumeControlPanel.setOpaque(true); 
        volumeControlPanel.setBackground(new Color(255, 230, 230, 200));
        
        // JSlider
        volumeSlider = new JSlider(SwingConstants.VERTICAL, 0, 100, 50);
        volumeSlider.setBounds(50, 20, 50, 150);
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        volumeSlider.setOpaque(true);
        volumeSlider.setBackground(Color.WHITE);
        volumeSlider.addChangeListener(e -> {
            musicLabel.setText("Music : " + volumeSlider.getValue());
            volumeSlider.repaint();
        });
        
        // Music Label
        musicLabel = new JLabel("Music : " + volumeSlider.getValue(), SwingConstants.CENTER);
        musicLabel.setFont(new Font("Arial", Font.BOLD, 16));
        musicLabel.setBounds(20, 175, 120, 25);
        musicLabel.setOpaque(true);
        musicLabel.setBackground(Color.WHITE);
        
        // Close Button
        closeVolumeButton = new JButton("CLOSE");
        closeVolumeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeVolumeButton.setBounds(30, 210, 90, 30);
        closeVolumeButton.addActionListener(e -> volumeControlPanel.setVisible(false));
        
        volumeControlPanel.add(volumeSlider);
        volumeControlPanel.add(musicLabel);
        volumeControlPanel.add(closeVolumeButton);
        
        int panelWidth = 150;
        int panelHeight = 250;
        volumeControlPanel.setBounds(20, 20, panelWidth, panelHeight); 
        MainApplication.backgroundLabel.add(volumeControlPanel);
        volumeControlPanel.setVisible(false); 
        
        // Volume Icon Button
        volumeIcon = new JButton("");
        volumeIcon.setIcon(ImageLoader.loadImageIcon(MyConstants.BUTTON_VOLUME_IMG));
        volumeIcon.setFont(new Font("Arial", Font.BOLD, 24));
        volumeIcon.setBounds(MyConstants.WIDTH - 80, MyConstants.HEIGHT - 100, 50, 50); 
        volumeIcon.addActionListener(e -> volumeControlPanel.setVisible(!volumeControlPanel.isVisible()));
        MainApplication.backgroundLabel.add(volumeIcon);
        
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                menuthemeSound.setVolume(volumeSlider.getValue() / 100.0f);
            }
        });
            
    }
        
    // SceneManager Volume
    public JButton getVolumeIcon() {
        return volumeIcon;
    }
}