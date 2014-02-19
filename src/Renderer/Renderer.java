/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Renderer;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author brian
 */
public class Renderer {

    //TODO implement hide and show
    private final List<Drawable> drawables;

    public Renderer() {
        drawables = new ArrayList<>();
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }

    public void addDrawableList(List<Drawable> drawableList) {
        drawables.addAll(drawableList);
    }

    public void draw(Graphics graphics) {
        for (Drawable drawable : drawables) {
            drawable.draw(graphics);
        }
    }

}
