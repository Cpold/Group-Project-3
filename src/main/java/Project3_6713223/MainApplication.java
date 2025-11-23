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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainApplication extends JFrame{
    
    private JLabel backgroundLabel;
    private SceneManager sceneManager;
    
    // Components สำหรับ Volume Control
    private JSlider volumeSlider;
    private JLabel musicLabel;
    private JButton closeVolumeButton;
    private JPanel volumeControlPanel;
    private JButton volumeIcon; 

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApplication());
    }

    public MainApplication(){
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(MyConstants.WIDTH, MyConstants.HEIGHT);
        setTitle("Pang Ping - Main Menu");
        setResizable(false);
        setLocationRelativeTo(null);
        
        backgroundLabel = new JLabel();
        MyImageIcon backgroundIcon = ImageLoader.loadImageIcon(MyConstants.FILE_START_BG);
        if (backgroundIcon != null) {
            backgroundLabel.setIcon(backgroundIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT));
        }
        setContentPane(backgroundLabel);
        
        backgroundLabel.setLayout(null); 
        
        sceneManager = new SceneManager(this);
        
        MainMenuScene mainMenu = sceneManager.getMainMenuScene();
        mainMenu.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
        backgroundLabel.add(mainMenu);
        
        setupVolumeControl();

        setVisible(true);
    }
    
    // เมธอดที่ SceneManager ใช้เพื่อเข้าถึงปุ่ม Volume
    public JButton getVolumeIcon() {
        return volumeIcon;
    }
    
    private void setupVolumeControl() {
        // Panel สำหรับ Volume Control (JSlider)
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
        backgroundLabel.add(volumeControlPanel);
        volumeControlPanel.setVisible(false); 
        
        // Volume Icon Button (มุมล่างขวา)
        volumeIcon = new JButton("");
        volumeIcon.setIcon(ImageLoader.loadImageIcon(MyConstants.BUTTON_VOLUME_IMG));
        volumeIcon.setFont(new Font("Arial", Font.BOLD, 24));
        volumeIcon.setBounds(MyConstants.WIDTH - 100, MyConstants.HEIGHT - 100, 50, 50); 
        volumeIcon.addActionListener(e -> volumeControlPanel.setVisible(!volumeControlPanel.isVisible()));
        backgroundLabel.add(volumeIcon);
    }
}    