/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameTimer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brian
 */
public class GameTimer {

    private long lastTime;
    private int frameTime;

    public GameTimer() {
        frameTime = 16;
        lastTime = System.currentTimeMillis();
    }

    public void updateTime() {
        lastTime = System.currentTimeMillis();
    }

    public void setFramesPerSecond(double framesPerSecond) {
        frameTime = (int) (1000 / framesPerSecond);
    }

    public void setFrameTime(int frameTime) {
        this.frameTime = frameTime;
    }

    public int getFrameTime() {
        return frameTime;
    }

    public void waitForNextFrame() {
        long waitTime = frameTime - (System.currentTimeMillis() - lastTime);
        if (waitTime > 0) {
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        lastTime = System.currentTimeMillis();
    }

}
