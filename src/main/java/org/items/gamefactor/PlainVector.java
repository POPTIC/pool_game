package org.items.gamefactor;

import java.util.Arrays;

public class PlainVector {
    private final double[] v_vector;
    PlainVector(){
        v_vector = new double[2];
    }

    public void setV_vector(double v_x, double v_y){
        v_vector[0] = v_x;
        v_vector[1] = v_y;
    }
    public double get_first_weight(){
        return v_vector[0];
    }
    public double get_second_weight(){
        return v_vector[1];
    }
    public void set_vector_x(double x){
        v_vector[0] = x;
    }
    public void set_vector_y(double y){
        v_vector[1] = y;
    }
    @Override
    public String toString() {
        return "VelocityVector{" +
                "v_vector=" + Arrays.toString(v_vector) +
                '}';
    }
}

