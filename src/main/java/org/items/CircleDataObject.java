package org.items;

public class CircleDataObject{ // 使用JSON进行类配置时，如果将成员变量设置为private类型，则需要添加setter函数
    private double location_x;
    private double location_y;
    private int color;
    private double circle_radius;
    private double init_speed_x;
    private double init_speed_y;
    public CircleDataObject() {}
    public double getLocation_x() {
        return location_x;
    }
    public double getLocation_y() {
        return location_y;
    }
    public int getColor() {
        return color;
    }
    public double getRadius() {
        return circle_radius;
    }

    public double get_speed_x() {
        return init_speed_x;
    }

    public double get_speed_y() {
        return init_speed_y;
    }

    public void setLocation_x(double location_x) {
        this.location_x = location_x;
    }

    public void setLocation_y(double location_y) {
        this.location_y = location_y;
    }

    public void setColor(int circle_color) {
        this.color = circle_color;
    }

    public void setCircle_radius(double circle_radius) {
        this.circle_radius = circle_radius;
    }

    public void setInit_speed_x(double init_speed_x) {
        this.init_speed_x = init_speed_x;
    }

    public void setInit_speed_y(double init_speed_y) {
        this.init_speed_y = init_speed_y;
    }
}
