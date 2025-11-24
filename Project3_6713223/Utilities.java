/*
 * @author Rachapon - 6713247
 *         Ratchasin - 6713247
 *         Sayklang - 6713250
 *         Chayapol - 6713223
 *         Zabit - 6713116
 */
package Project3_6713223;

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;     // for sounds

interface MyConstants {

    static final String PATH = "src/main/java/Project3_6713223/resources/";
    static final String FILE_START_BG = PATH + "Start.jpg";
    static final String FILE_MENU_BG = PATH + "MainMenu.jpg";
    static final String FILE_GAME_BG = PATH + "Game.jpg";
    static final String FILE_End_BG = PATH + "End.jpg";
    static final String FILE_POL = PATH + "PolFace.png";
    static final String FILE_START_SCENE_BG = PATH + "StartScene_Background.jpg";
    static final String FILE_TUTORIAL_GUIDE = PATH + "BreadMakerGuide.jpg";
    static final String FILE_BREAD_LOST = PATH + "bread_burnt.jpg";
    static final String FILE_BREAD_WIN = PATH + "bread_master.jpg";
    static final String FILE_DIFFICULTY_BG = PATH + "Difficulty_Background.jpg";
    static final String FILE_MUSIC_BG = PATH + "Music_Background.jpg";
    static final String FILE_CREDIT_BG = PATH + "Credit_Background.jpg";
    
    static final String BUTTON_START_IMG    = PATH + "button_start.png";
    static final String BUTTON_TUTORIAL_IMG  = PATH + "button_tutorial.png";
    static final String BUTTON_CREDITS_IMG  = PATH + "button_credits.png";
    static final String BUTTON_EXIT_IMG     = PATH + "button_exit.png";
    static final String BUTTON_VOLUME_IMG     = PATH + "button_volume.png";
    static final String FILE_BACK_BUTTON_IMG = PATH + "back_button.png";
    
    static final String SONG1     = PATH + "theme.wav";

    static final String PAT = "src/main/java/Project3_6713223/resources/game/";
    static final String BG = PAT + "cooking_background.jpg";
    static final String BREAD_RAW = PAT + "breadraw.png";
    static final String BREAD_TOAST = PAT + "bread_toasted.png";
    static final String BREAD_CHOC = PAT + "bread_chocolate.png";
    static final String BREAD_THAI = PAT + "bread_thai_tea.png";
    static final String BREAD_CUST = PAT + "bread_custard.png";
    static final String BREAD_FOI = PAT + "bread_foithong.png";
    static final String BREAD_CHO = PAT + "bread_chochip.png";
    static final String BREAD_MAR = PAT + "bread_marshmallow.png";

    static final String CHOCHIP = PAT + "ChocChip.png";
    static final String CHOCFOI = PAT + "ChocFoi.png";
    static final String CHOCMAR = PAT + "ChocMarsh.png";
    static final String TEACHIP = PAT + "TeaChip.png";
    static final String TEACFOI = PAT + "TeaFoi.png";
    static final String TEAMAR = PAT + "TeaMarsh.png";
    static final String CUSCHIP = PAT + "CusChip.png";
    static final String CUSFOI = PAT + "CusFoi.png";
    static final String CUSMAR = PAT + "CusMarsh.png";

    static final int WIDTH = 1280;
    static final int HEIGHT = 720;

    static final String CUSTOMER_1 = PATH + "customer/doctor.png";
    static final String CUSTOMER_2 = PATH + "customer/Tae.png";
    static final String CUSTOMER_3 = PATH + "customer/E3.png";
    static final String CUSTOMER_4 = PATH + "customer/Art.png";
    static final String CUSTOMER_5 = PATH + "customer/1.png";
    static final String CUSTOMER_6 = PATH + "customer/2.png";
    static final String CUSTOMER_7 = PATH + "customer/3.png";
    static final String CUSTOMER_8 = PATH + "customer/4.png";
    static final String CUSTOMER_9 = PATH + "customer/5.png";

    static final String CUS_ANGRY_1 = PATH +"customer/a/0.png";
    static final String CUS_ANGRY_2 = PATH +"customer/a/6.png";
    static final String CUS_ANGRY_3 = PATH +"customer/a/0.png";
    static final String CUS_ANGRY_4 = PATH +"customer/a/0.png";
    static final String CUS_ANGRY_5 = PATH +"customer/a/1.png";
    static final String CUS_ANGRY_6 = PATH +"customer/a/2.png";
    static final String CUS_ANGRY_7 = PATH +"customer/a/3.png";
    static final String CUS_ANGRY_8 = PATH +"customer/a/4.png";
    static final String CUS_ANGRY_9 = PATH +"customer/a/5.png";

    static final String UI_SCORE_BG = PATH + "ui/score_bg.png";
    static final String UI_HEART = PATH + "ui/heart.png";
    static final String UI_CLOCK_BG = PATH + "ui/clock_bg.png";
}

class MyImageIcon extends ImageIcon {

    public MyImageIcon(String fname) {
        super(fname);
    }// Load image from file path

    public MyImageIcon(Image image) {
        super(image);
    }// Load image from Image object

    public MyImageIcon resize(int width, int height) {
        Image oldimg = this.getImage();
        Image newimg = oldimg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new MyImageIcon(newimg);
    }
}

class ImageLoader {

    public static MyImageIcon loadImageIcon(String path) {
        try {
            return new MyImageIcon(path);
        } catch (Exception e) {
            return null;
        }
    }
}

// Auxiliary class to play sound effect (support .wav or .mid file)
class MySoundEffect
{
    private Clip         clip;
    private FloatControl gainControl;         

    public MySoundEffect(String filename)
    {
	try
	{
            java.io.File file = new java.io.File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);            
            gainControl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
	}
	catch (Exception e) { e.printStackTrace(); }
    }
    public void playOnce()             { clip.setMicrosecondPosition(0); clip.start(); }
    public void playLoop()             { clip.loop(Clip.LOOP_CONTINUOUSLY); }
    public void stop()                 { clip.stop(); }
    public void setVolume(float gain)
    {
        if (gain < 0.0f)  gain = 0.0f;
        if (gain > 1.0f)  gain = 1.0f;
        float dB = (float)(Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
}