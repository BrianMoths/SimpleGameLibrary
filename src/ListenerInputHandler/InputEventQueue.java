/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ListenerInputHandler;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author brian
 */
class InputEventQueue {
    
    private final LinkedList<KeyEvent> keyEventQueue;
    private final LinkedList<MouseEvent> mouseEventQueue;
    private final LinkedList<MouseWheelEvent> mouseWheelEventQueue;
    private final LinkedList<HandledEventType> queuedEventTypes;
    
    InputEventQueue(){
        keyEventQueue = new LinkedList<KeyEvent>();
        mouseEventQueue = new LinkedList<MouseEvent>();
        mouseWheelEventQueue = new LinkedList<MouseWheelEvent>();
        queuedEventTypes = new LinkedList<HandledEventType>();
    }
            
    public void of(InputEventQueue inputEventQueue){
        shallowCopyDstFromSrc(keyEventQueue, inputEventQueue.keyEventQueue);
        shallowCopyDstFromSrc(mouseEventQueue, inputEventQueue.mouseEventQueue);
        shallowCopyDstFromSrc(mouseWheelEventQueue, inputEventQueue.mouseWheelEventQueue);
        shallowCopyDstFromSrc(queuedEventTypes, inputEventQueue.queuedEventTypes);
    }
    
    private <T> void shallowCopyDstFromSrc(List<T> destination, List<? extends T> source){
        destination.clear();
        destination.addAll(source);
    }
    
    public void clear(){
        keyEventQueue.clear();
        mouseEventQueue.clear();
        mouseWheelEventQueue.clear();
        queuedEventTypes.clear();
    }
            
    public boolean isEmpty(){
        return queuedEventTypes.isEmpty();
    }
    
    public void addKeyEvent(KeyEvent keyEvent){
        keyEventQueue.add(keyEvent);
        queuedEventTypes.add(HandledEventType.KEY_EVENT);
    }
    
    public void addMouseEvent(MouseEvent mouseEvent){
        mouseEventQueue.add(mouseEvent);
        queuedEventTypes.add(HandledEventType.MOUSE_EVENT);
    }
    
    public void addMouseWheelEvent(MouseWheelEvent mouseWheelEvent){
        mouseWheelEventQueue.add(mouseWheelEvent);
        queuedEventTypes.add(HandledEventType.MOUSE_WHEEL_EVENT);
    }
    
    public HandledEventType removeEventType(){
        return queuedEventTypes.remove();
    }
    
    public KeyEvent removeKeyEvent(){
        return keyEventQueue.remove();
    }
    
    public MouseEvent removeMouseEvent(){
        return mouseEventQueue.remove();
    }
    
    public MouseWheelEvent removeMouseWheelEvent(){
        return mouseWheelEventQueue.remove();
    }
    
}
