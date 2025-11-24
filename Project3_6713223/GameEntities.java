/*
 * @author Rachapon - 6713247
 *         Ratchasin - 6713248
 *         Sayklang - 6713250
 *         Chayapol - 6713223
 *         Zabit - 6713116
 */
package Project3_6713223;

// ===== BREAD =====
class Bread {
    public static final int RAW = 0, TOASTING = 1, TOASTED = 2;
    int state;
    private double progress = 0;    
    private final double MAX_PROGRESS = 500;
    int jamType = -1;
    int toppingType = -1;
    
    public Bread() {
        this.state = RAW;
    }
    
    void startToast() {
        state = TOASTING;
        progress = 0;
    }
    
    void tick(boolean isBoosting) {
        if (state != TOASTING) return;

        if (isBoosting) {
            progress += 5.0;
        } else {
            progress += 2.0;
       
        }
    }
    
    boolean isDone() {
        if (state != TOASTING) return false;
        return progress >= MAX_PROGRESS;
    }
    
    void finishToast() {
        state = TOASTED;
        progress = MAX_PROGRESS;
    }
    
    public int getProgress() {
        return (int) progress;
    }
    
    public int getMaxProgress() {
        return (int) MAX_PROGRESS;
    }
    
    void addJam(int type) {
        jamType = type;
    }
    
    void addTopping(int type) {
        toppingType = type;
    }
    
    boolean hasJam() {
        return jamType != -1;
    }
    
    boolean hasTopping() {
        return toppingType != -1;
    }
    
     public String getMenuName() {
        if (state != TOASTED) return "Raw Bread";
        
        String jamName = "";
        String topName = "";
        
        // แปลง jamType (0,1,2) เป็นชื่อ
        if (jamType == 0) jamName = "Chocolate";
        else if (jamType == 1) jamName = "Thai Tea";
        else if (jamType == 2) jamName = "Custard"; // สังขยา
        else return "Plain Toast";

        // แปลง toppingType (0,1,2) เป็นชื่อ
        // 0=FoiThong(Banana?), 1=Chip, 2=Marshmallow 
        // (อิงตามไฟล์รูป CUSFOI, CUSCHIP ใน Utilities)
        if (toppingType == 0) topName = "Foi Thong"; 
        else if (toppingType == 1) topName = "Choc Chip";
        else if (toppingType == 2) topName = "Marshmallow";
        else return jamName + " Toast";

        return jamName + " " + topName + " Toast";
    }
    
}

// ===== TOAST =====
class Toast {
    Bread bread;
    int x, y;
    
    Toast(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    boolean addBread(Bread b) {
        if (bread == null) {
            bread = b;
            return true;
        }
        return false;
    }
    
    Bread getBread() {
        return bread;
    }
    
    Bread removeBread() {
        Bread temp = bread;
        bread = null;
        return temp;
    }
}

// ===== TRAY =====
class Tray {
    Bread[] slots;
    int x, y;
    int slotWidth = 100;
    int slotHeight = 100;
    int spacing = 100;
    
    Tray(int x, int y) {
        this.x = x;
        this.y = y;
        this.slots = new Bread[2];
    }
    
    boolean addBread(Bread b) {
        for (int i = 0; i < 2; i++) {
            if (slots[i] == null) {
                slots[i] = b;
                return true;
            }
        }
        return false;
    }
    
    Bread getSlot(int i) {
        if (i >= 0 && i < 2) {
            return slots[i];
        }
        return null;
    }
    
    void removeSlot(int i) {
        if (i >= 0 && i < 2) {
            slots[i] = null;
        }
    }
    
    int getSlotAtPosition(int clickX, int clickY) {
        int slot1X = x + 5;
        int slot2X = x + slotWidth + spacing + 5;
        int slotY = y + 5;
        
        if (clickX >= slot1X && clickX < slot1X + slotWidth - 10 &&
            clickY >= slotY && clickY < slotY + slotHeight - 10) {
            return 0;
        }
        
        if (clickX >= slot2X && clickX < slot2X + slotWidth - 10 &&
            clickY >= slotY && clickY < slotY + slotHeight - 10) {
            return 1;
        }
        
        return -1;
    }
    
    int getTrayWidth() {
        return slotWidth * 2 + spacing;
    }
    
    int getTrayHeight() {
        return slotHeight;
    }
}
