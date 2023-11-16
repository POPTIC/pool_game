package org.items;

import java.util.Arrays;

public class VelocityVector {
    private final double[] v_vector;
    VelocityVector(){
        v_vector = new double[2];
    }

    private void velocity_decomposition(VelocityVector v, VelocityVector ref_unit_v){
        VelocityVector along_v = new VelocityVector();
        VelocityVector orthogonal_v = new VelocityVector();
        double projection_length = v.get_first_weight() * ref_unit_v.get_first_weight() +
                                    v.get_second_weight() * ref_unit_v.get_second_weight();
        along_v.setV_vector(projection_length * ref_unit_v.get_first_weight(),
                                projection_length * ref_unit_v.get_second_weight());
        orthogonal_v.setV_vector(v.get_first_weight() - along_v.get_first_weight(),
                                    v.get_second_weight() - along_v.get_second_weight());
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
