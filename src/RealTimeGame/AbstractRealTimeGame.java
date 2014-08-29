/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RealTimeGame;

import GameGui.BaseGameGui;
import GameTimer.GameTimer;
import ListenerInputHandler.InputHandler;
import ListenerInputHandler.InputHandlerClient;
import java.awt.Component;
import java.awt.Toolkit;

/**
 *
 * @author brian
 * @param <T>
 */
public abstract class AbstractRealTimeGame<T extends BaseGameGui> {
    
    private class GameLoopRunnable implements Runnable {
        
        @Override
        public void run() {
            gameTimer.updateTime();
            while (true) {
                runGameLoop();
            }
        }
        
    }
    
    private final InputHandler inputHandler;
    private final GameTimer gameTimer;
    private T gameGui;
    protected final InputHandlerClient inputHandlerClient;
    
    public AbstractRealTimeGame(InputHandlerClient inputHandlerClient, T gameGui) {
        this.inputHandlerClient = inputHandlerClient;
        inputHandler = new InputHandler();
        
        this.gameGui = gameGui;
        
        gameTimer = new GameTimer();
        init();
    }
    
    private void init() {
        initInputHandler(gameGui.getInputProducerComponent());
        gameGui.setVisible(true);
    }
    
    private void initInputHandler(Component inputProducerComponent) {
        inputHandler.addInputSource(inputProducerComponent);
        inputHandler.addClient(inputHandlerClient);
    }
    
    final protected void setFramesPerSecond(double fps) {
        gameTimer.setFramesPerSecond(fps);
    }
    
    final protected void startGameLoopThread() {
        final GameLoopRunnable gameLoopRunnable = new GameLoopRunnable();
        final Thread thread = new Thread(gameLoopRunnable);
        thread.start();
    }
    
    private void runGameLoop() {
        inputHandler.processInput();
        doLogic();
        gameGui.repaint();
        Toolkit.getDefaultToolkit().sync();
        gameTimer.waitForNextFrame();
    }
    
    abstract protected void doLogic();
    
    protected T getGameGui() {
        return gameGui;
    }
    
    protected void setGameGui(T gameGui) {
        removeOldGameGui();
        this.gameGui = gameGui;
        gameGui.setVisible(true);
        inputHandler.addInputSource(gameGui.getInputProducerComponent());
    }
    
    private void removeOldGameGui() {
        inputHandler.removeInputSource(gameGui.getInputProducerComponent());
    }
    
}
