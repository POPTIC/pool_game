package org.items.gamefactor;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @param
 * @return
 */
public class BallHole extends Circle {
    public BallHole(double size, double location_x, double location_y){
        super(location_x, location_y, size, Color.GREY);
    }
}
