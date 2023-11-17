package org.items.gamefactor;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.shape.Line;

import java.util.ArrayList;

/**
 * @param
 * @return
 */
public class GameTable implements Drawable{
    private final double table_length;
    private final double table_width;
    private double frictional_coefficient = 0.1;

    private final Line upline;
    private final Line downline;
    private final Line leftline;
    private final Line rightline;
    double delta = 20;
    public GameTable(double table_length, double table_width, double frictional_coefficient) {
        this.table_length = table_length;
        this.table_width = table_width;
        this.frictional_coefficient = frictional_coefficient;

        upline = new Line(0 + delta, this.table_width + delta, this.table_length + delta, this.table_width+ delta);
        downline = new Line(delta, 0+ delta, this.table_length + delta,0+ delta);
        leftline = new Line(delta, 0+ delta, 0 + delta, this.table_width+ delta);
        rightline = new Line(this.table_length + delta, 0+ delta, this.table_length + delta, this.table_width+ delta);
        upline.setStrokeWidth(5);
        downline.setStrokeWidth(5);
        rightline.setStrokeWidth(5);
        leftline.setStrokeWidth(5);
    }
    public void frictional_process(ArrayList<Double> speed){
        if(speed.get(0) == 0 && speed.get(1) == 0){
            return;
        }
        double speed_scale = Math.sqrt(Math.pow(speed.get(0),2) + Math.pow(speed.get(1), 2));
        speed.set(0, (speed.get(0) / speed_scale) * (speed_scale - 0.5  * Math.pow(this.frictional_coefficient, 2)));
        speed.set(1, (speed.get(1) / speed_scale) * (speed_scale - 0.5  * Math.pow(this.frictional_coefficient, 2)));
    }
    public double get_boundary_up(){
        return table_width + delta;
    }
    public double get_boundary_left(){
        return 0 + delta;
    }
    public double get_boundary_right(){
        return table_length + delta;
    }
    public double get_boundary_down(){
        return delta;
    }
    public Line getUpline() {
        return upline;
    }

    public Line getDownline() {
        return downline;
    }

    public Line getLeftline() {
        return leftline;
    }

    public Line getRightline() {
        return rightline;
    }

    public double getTable_length() {
        return table_length;
    }

    public double getTable_width() {
        return table_width;
    }

    public double getFrictional_coefficient() {
        return frictional_coefficient;
    }

    @Override
    public void addToGroup(ObservableList<Node> group) {
        group.add(upline);
        group.add(downline);
        group.add(leftline);
        group.add(rightline);
    }
}
