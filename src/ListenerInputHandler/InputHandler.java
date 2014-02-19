/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListenerInputHandler;

import java.awt.Component;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author brian
 */
public class InputHandler implements InputHandlerClient {
    
    //private final List<Component> sourceList;
    private final List<InputHandlerClient> clients;
    private final InputEventQueue receivedInput;
    private final InputEventQueue receivedInputCopy;
    
    
    public InputHandler(){
        clients = new ArrayList<InputHandlerClient>();
        receivedInput = new InputEventQueue();
        receivedInputCopy = new InputEventQueue();
    }
    
    //<editor-fold defaultstate="collapsed" desc="add and remove sources">
    public void addInputSource(Component sourceComponent){
        //sourceList.add(sourceComponent);
        sourceComponent.addKeyListener(this);
        sourceComponent.addMouseListener(this);
        sourceComponent.addMouseMotionListener(this);
        sourceComponent.addMouseWheelListener(this);
    }
    
    public void addInputSourceList(List<Component> sourceComponentList){
        for (Component sourceComponent : sourceComponentList){
            addInputSource(sourceComponent);
        }
    }
    
    public void removeInputSource(Component sourceComponent) {
        sourceComponent.removeKeyListener(this);
        sourceComponent.removeMouseListener(this);
        sourceComponent.removeMouseMotionListener(this);
        sourceComponent.removeMouseWheelListener(this);
    }
    
    public void removeInputSourceList(List<Component> sourceComponentList) {
        for (Component sourceComponent : sourceComponentList) {
            removeInputSource(sourceComponent);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="add and remove clients">
    public void addClient(InputHandlerClient newClient) {
        clients.add(newClient);
    }
    
    public void addClientList(List<InputHandlerClient> newClientList) {
        clients.addAll(newClientList);
    }
    
    public void removeClient(InputHandlerClient client) {
        clients.remove(client);
    }
    
    public void removeClientList(List<InputHandlerClient> clientList) {
        for (InputHandlerClient client : clientList) {
            clients.remove(client);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Process Input">
    public void processInput() {
        flushInputToCopy();
        handleCopiedInput();
    }
    
    private synchronized void flushInputToCopy(){
        receivedInputCopy.of(receivedInput);
        receivedInput.clear();
    }
    
    private void handleCopiedInput(){
        while(!receivedInputCopy.isEmpty()){
            switch (receivedInputCopy.removeEventType()) {
                case KEY_EVENT :
                    handleKeyEvent(receivedInputCopy.removeKeyEvent());
                    break;
                case MOUSE_EVENT:
                    handleMouseEvent(receivedInputCopy.removeMouseEvent());
                    break;
                case MOUSE_WHEEL_EVENT:
                    handleMouseWheelEvent(receivedInputCopy.removeMouseWheelEvent());
                    break;
                default:
                    throw new AssertionError();
            }
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Handle Key Events">
    private void handleKeyEvent(KeyEvent keyEvent){
        switch (keyEvent.getID()) {
            case KeyEvent.KEY_TYPED:
                handleKeyTypedEvent(keyEvent);
                break;
            case KeyEvent.KEY_PRESSED:
                handleKeyPressedEvent(keyEvent);
                break;
            case KeyEvent.KEY_RELEASED:
                handleKeyReleasedEvent(keyEvent);
                break;
            default:
                throw new AssertionError();
        }
    }
    
    private void handleKeyTypedEvent(KeyEvent keyEvent){
        for (InputHandlerClient client : clients) {
            client.keyTyped(keyEvent);
        }
    }
    
    private void handleKeyPressedEvent(KeyEvent keyEvent){
        for (InputHandlerClient client : clients) {
            client.keyPressed(keyEvent);
        }
    }
    
    private void handleKeyReleasedEvent(KeyEvent keyEvent){
        for (InputHandlerClient client : clients) {
            client.keyReleased(keyEvent);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Handle Mouse Events">
    private void handleMouseEvent(MouseEvent mouseEvent){
        switch (mouseEvent.getID()) {
            case MouseEvent.MOUSE_CLICKED:
                handleMouseClickedEvent(mouseEvent);
                break;
            case MouseEvent.MOUSE_PRESSED:
                handleMousePressedEvent(mouseEvent);
                break;
            case MouseEvent.MOUSE_RELEASED:
                handleMouseReleasedEvent(mouseEvent);
                break;
            case MouseEvent.MOUSE_ENTERED:
                handleMouseEnteredEvent(mouseEvent);
                break;
            case MouseEvent.MOUSE_EXITED:
                handleMouseExitedEvent(mouseEvent);
                break;
            case MouseEvent.MOUSE_DRAGGED:
                handleMouseDraggedEvent(mouseEvent);
                break;
            case MouseEvent.MOUSE_MOVED:
                handleMouseMovedEvent(mouseEvent);
                break;
            default:
                throw new AssertionError();
        }
    }
    
    private void handleMouseClickedEvent(MouseEvent mouseEvent){
        for (InputHandlerClient client : clients) {
            client.mouseClicked(mouseEvent);
        }
    }
    
    private void handleMousePressedEvent(MouseEvent mouseEvent){
        for (InputHandlerClient client : clients) {
            client.mousePressed(mouseEvent);
        }
    }
    
    private void handleMouseReleasedEvent(MouseEvent mouseEvent){
        for (InputHandlerClient client : clients) {
            client.mouseReleased(mouseEvent);
        }
    }
    
    private void handleMouseEnteredEvent(MouseEvent mouseEvent){
        for (InputHandlerClient client : clients) {
            client.mouseEntered(mouseEvent);
        }
    }
    
    private void handleMouseExitedEvent(MouseEvent mouseEvent){
        for (InputHandlerClient client : clients) {
            client.mouseExited(mouseEvent);
        }
    }
    
    private void handleMouseDraggedEvent(MouseEvent mouseEvent){
        for (InputHandlerClient client : clients) {
            client.mouseDragged(mouseEvent);
        }
    }
    
    private void handleMouseMovedEvent(MouseEvent mouseEvent){
        for (InputHandlerClient client : clients) {
            client.mouseMoved(mouseEvent);
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Handle Mouse Wheel Event">
    private void handleMouseWheelEvent(MouseWheelEvent mouseWheelEvent){
        for (InputHandlerClient client : clients) {
            client.mouseWheelMoved(mouseWheelEvent);
        }
    }
    //</editor-fold>
    //</editor-fold>
    
    //< editor-fold defaultstate="collapsed" desc="Input Handler Client Methods">
    @Override
    public void keyTyped(KeyEvent event) {
        receivedInput.addKeyEvent(event);
    }

    @Override
    public synchronized void keyPressed(KeyEvent event) {
        receivedInput.addKeyEvent(event);
    }

    @Override
    public synchronized void keyReleased(KeyEvent event) {
        receivedInput.addKeyEvent(event);
    }

    @Override
    public synchronized void mouseClicked(MouseEvent event) {
        receivedInput.addMouseEvent(event);
    }

    @Override
    public synchronized void mousePressed(MouseEvent event) {
        receivedInput.addMouseEvent(event);
    }

    @Override
    public synchronized void mouseReleased(MouseEvent event) {
        receivedInput.addMouseEvent(event);
    }

    @Override
    public synchronized void mouseEntered(MouseEvent event) {
        receivedInput.addMouseEvent(event);
    }

    @Override
    public synchronized void mouseExited(MouseEvent event) {
        receivedInput.addMouseEvent(event);
    }

    @Override
    public synchronized void mouseDragged(MouseEvent event) {
        receivedInput.addMouseEvent(event);
    }

    @Override
    public synchronized void mouseMoved(MouseEvent event) {
        receivedInput.addMouseEvent(event);
    }

    @Override
    public synchronized void mouseWheelMoved(MouseWheelEvent event) {
        receivedInput.addMouseWheelEvent(event);
    }
     //</editor-fold>

}
