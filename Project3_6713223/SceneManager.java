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

public class SceneManager {
    
    private JFrame mainFrame;
    private MainMenuScene mainMenu;
    public GameBoard gameBoard;
    private CreditScene creditScene;
    private StartScene startInputScene; 
    public DifficultyScene difficultyScene;
    private MusicScene musicScene;
    private TutorialScene tutorialScene;
    
    public String currentPlayerName = "Guest"; 

    public SceneManager(JFrame mainFrame) {
        this.mainFrame = mainFrame;
        
        mainMenu = new MainMenuScene(this); 
        creditScene = new CreditScene(this); 
        startInputScene = new StartScene(this); 
        difficultyScene = new DifficultyScene(this);
        musicScene = new MusicScene(this);
        tutorialScene = new TutorialScene(this); 
    }
    
    public MainMenuScene getMainMenuScene() {
        return mainMenu;
    }

    private void toggleVolumeIcon(boolean visible) {
        if (mainFrame instanceof MainApplication) {
            try {
                JButton volumeIcon = mainMenu.getVolumeIcon();
                if (volumeIcon != null) {
                    volumeIcon.setVisible(visible);
                }
            } catch (Exception e) {
                System.err.println("Error accessing Volume Icon: Make sure getVolumeIcon() is public in MainApplication.");
            }
        }
    }
    
    public void showEndScene(boolean isWin, String playerName) {
        this.currentPlayerName = playerName;
        
        EndScene endScene = new EndScene(this, isWin, playerName);
        
        mainFrame.getContentPane().removeAll();
        toggleVolumeIcon(false);
        
        mainFrame.setTitle("Pang Ping - Game Over");
        endScene.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
        mainFrame.getContentPane().add(endScene);
        
        mainFrame.revalidate();
        mainFrame.repaint();
    }


    public void switchToScene(String sceneName) {
        Container contentPane = mainFrame.getContentPane();
        
        Component[] components = contentPane.getComponents();
        for (Component c : components) {
            if (!(c instanceof JSlider) && !(c instanceof JButton) && !c.getClass().getName().contains("volumeControlPanel")) {
                 if (c.getWidth() == MyConstants.WIDTH && c.getHeight() == MyConstants.HEIGHT) {
                    contentPane.remove(c);
                 }
            }
        }
        
        boolean showVolume = sceneName.equals("Menu");
        toggleVolumeIcon(showVolume); 
        
        switch (sceneName) {
            case "Menu":
                mainFrame.setTitle("Pang Ping - Main Menu");
                mainMenu.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
                contentPane.add(mainMenu);
                break;
            case "StartInput":
                mainFrame.setTitle("Pang Ping - Enter Name");
                startInputScene.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
                contentPane.add(startInputScene);
                startInputScene.revalidate();
                startInputScene.repaint();
                break;
            case "Difficulty":
                mainFrame.setTitle("Pang Ping - Select Mode");
                difficultyScene.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
                contentPane.add(difficultyScene);
                difficultyScene.revalidate();
                difficultyScene.repaint();
                break;
            case "Music":
                mainFrame.setTitle("Pang Ping - Select Music");
                musicScene.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
                contentPane.add(musicScene);
                musicScene.revalidate();
                musicScene.repaint();
                break;
            case "Game":

                getMainMenuScene().menuthemeSound.stop();
                switch (MusicScene.musicGroup.getSelection().getActionCommand()) {
                    case "Chill Jazz (Default)":
                        getMainMenuScene().menuthemeSound = new MySoundEffect(MyConstants.MUSIC1);
                        getMainMenuScene().menuthemeSound.setVolume(getMainMenuScene().volumeSlider.getValue()/100.0f);
                        getMainMenuScene().menuthemeSound.playLoop();
                        break;
                    case "Upbeat Pop":
                        getMainMenuScene().menuthemeSound = new MySoundEffect(MyConstants.MUSIC2);
                        getMainMenuScene().menuthemeSound.setVolume(getMainMenuScene().volumeSlider.getValue()/100.0f);
                        getMainMenuScene().menuthemeSound.playLoop();
                        break;
                    case "Lo-fi Beats":
                        getMainMenuScene().menuthemeSound = new MySoundEffect(MyConstants.MUSIC3);
                        getMainMenuScene().menuthemeSound.setVolume(getMainMenuScene().volumeSlider.getValue()/100.0f);
                        getMainMenuScene().menuthemeSound.playLoop();
                        break;
                    case "Classic Rock":
                        getMainMenuScene().menuthemeSound = new MySoundEffect(MyConstants.MUSIC4);
                        getMainMenuScene().menuthemeSound.setVolume(getMainMenuScene().volumeSlider.getValue()/100.0f);
                        getMainMenuScene().menuthemeSound.playLoop();
                        break;
                    case "8-bit Retro":
                        getMainMenuScene().menuthemeSound = new MySoundEffect(MyConstants.MUSIC5);
                        getMainMenuScene().menuthemeSound.setVolume(getMainMenuScene().volumeSlider.getValue()/100.0f);
                        getMainMenuScene().menuthemeSound.playLoop();
                        break;
                    default:
                        getMainMenuScene().menuthemeSound = new MySoundEffect(MyConstants.MUSIC1);
                        getMainMenuScene().menuthemeSound.setVolume(getMainMenuScene().volumeSlider.getValue()/100.0f);
                        getMainMenuScene().menuthemeSound.playLoop();
                        break;
                } 
                
                mainFrame.setTitle("Pang Ping - Gameplay");
                if (gameBoard != null) {
                    gameBoard.stopGame();
                }
    
                gameBoard = new GameBoard(this);
                gameBoard.resetGame();
                gameBoard.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
                contentPane.add(gameBoard);
                gameBoard.requestFocusInWindow();
                break;
            case "Credit": 
                mainFrame.setTitle("Pang Ping - Credits");
                creditScene.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
                contentPane.add(creditScene);
                break;
            case "Tutorial": 
                mainFrame.setTitle("Pang Ping - Tutorial Guide");
                tutorialScene.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
                contentPane.add(tutorialScene);
                break;
            default:
                System.err.println("Unknown scene: " + sceneName);
        }
        
        contentPane.revalidate();
        contentPane.repaint();
    }
}