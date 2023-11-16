package org.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.configtool.ConfigReader;

import java.util.concurrent.BlockingDeque;

public class Ball extends Circle{
    double speed_x = 0;
    double speed_y = 0;
    //TODO: 查询final关键字的作用

    public double getSpeed_x() {
        return speed_x;
    }

    public double getSpeed_y() {
        return speed_y;
    }

    Ball(double location_x, double location_y, double radius, Color color, double init_speed_x, double init_speed_y) {
        super(location_x, location_y, radius, color);
        speed_x = init_speed_x;
        speed_y = init_speed_y;
    }
}
