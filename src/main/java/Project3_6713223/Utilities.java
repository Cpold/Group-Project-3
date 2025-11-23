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

interface MyConstants {

    static final String PATH = "src/main/java/Project3_6713223/resources/";
    static final String FILE_START_BG = PATH + "Start.jpg";
    static final String FILE_MENU_BG = PATH + "MainMenu.jpg";
    static final String FILE_GAME_BG = PATH + "Game.jpg";
    static final String FILE_End_BG = PATH + "End.jpg";
    static final String FILE_POL = PATH + "PolFace.png";

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
