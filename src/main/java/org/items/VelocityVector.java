package org.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class VelocityVector {
    private final double[] v_vector;
    VelocityVector(){
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
    @Override
    public String toString() {
        return "VelocityVector{" +
                "v_vector=" + Arrays.toString(v_vector) +
                '}';
    }
}
