/*
 * @author Rachapon - 6713247
 * Ratchasin - 6713248
 * Sayklang - 6713250
 * Chayapol - 6713223
 * Zabit - 6713116
 */
package Project3_6713223;

public class GameTimer {
    private long startTime;
    private long timeLimit;      // เวลาจำกัด (วินาที)
    private long timeRemaining;  // เวลาที่เหลือ (วินาที)
    private boolean isTimeUp;
    private String difficulty;

    public GameTimer() {
        // ค่าเริ่มต้น
        setDifficulty("Normal");
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        switch (difficulty.toLowerCase()) {
            case "easy":
                this.timeLimit = 180;
                break;
            case "normal":
                this.timeLimit = 120;  
                break;
            case "medium":
                this.timeLimit = 90;  
                break;
            case "hard":
                this.timeLimit = 60; 
                break;
            case "chef":
                this.timeLimit = 30;  
                break;
            default:
                this.timeLimit = 60;
        }
        reset(); // รีเซ็ตเวลาใหม่ทุกครั้งที่ตั้งค่า
    }

    // เริ่มจับเวลาใหม่
    public void reset() {
        this.startTime = System.currentTimeMillis();
        this.isTimeUp = false;
        this.timeRemaining = timeLimit;
    }

    // เมธอดสำหรับอัปเดตเวลา (เรียกใน Game Loop)
    public void update() {
        if (isTimeUp) return;

        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        timeRemaining = timeLimit - elapsed;

        if (timeRemaining <= 0) {
            timeRemaining = 0;
            isTimeUp = true;
        }
    }

    public String getTimeString() {
    return String.valueOf(timeRemaining);
}

    // Getters
    public boolean isTimeUp() { return isTimeUp; }
    public long getTimeRemaining() { return timeRemaining; }
    public String getDifficulty() { return difficulty; }
}