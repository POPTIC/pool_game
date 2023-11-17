package org.items.gamefactor;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ball extends Circle{
    PlainVector velocityVector;

    //TODO: 查询final关键字的作用

    public double getSpeed_x() {
        return velocityVector.get_first_weight();
    }

    public double getSpeed_y() {
        return velocityVector.get_second_weight();
    }
    public void setSpeed_x(double x){
        velocityVector.set_vector_x(x);
    }
    public void setSpeed_y(double y){
        velocityVector.set_vector_y(y);
    }
    public Ball(double location_x, double location_y, double radius, Color color, double init_speed_x, double init_speed_y) {
        super(location_x, location_y, radius, color);
        velocityVector= new PlainVector();
        velocityVector.setV_vector(init_speed_x, init_speed_y);
    }
}
