/*
 * @author Rachapon - 6713247
 *         Ratchasin - 6713247
 *         Sayklang - 6713250
 *         Chayapol - 6713223
 *         Zabit - 6713116
 */
package Project3_6713223;

// ===== BREAD =====
class Bread {
    public static final int RAW = 0, TOASTING = 1, TOASTED = 2;
    int state;
    long toastTime;
    int jamType = -1;
    int toppingType = -1;
    
    public Bread() {
        this.state = RAW;
    }
    
    void startToast() {
        state = TOASTING;
        toastTime = System.currentTimeMillis();
    }
    
    boolean isDone() {
        if (state != TOASTING) return false;
        return System.currentTimeMillis() - toastTime >= 5000;
    }
    
    void finishToast() {
        state = TOASTED;
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
    int slotWidth = 70;
    int slotHeight = 80;
    int spacing = 20;
    
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
