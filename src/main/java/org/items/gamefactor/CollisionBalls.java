package org.items.gamefactor;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 * @param
 * @return
 */
public class CollisionBalls implements Drawable, Iterator{
    ArrayList<Ball> hitBall;
    PlainVector ball_center_direction; // 两个球球心之间形成的方向向量
    double[][] distance_matrix; // 两球之间的距离
    double[][] ball_radius_distance; // 两两小球间的半径距离
    double[][] ball_slope;
    boolean[] ball_out;
    double[] ball_velocity_scale;
    double scale;

    private int index = 0;

    public CollisionBalls(ArrayList<Ball> balls) throws IOException {
        hitBall = balls;
        ball_out = new boolean[hitBall.size()];
        distance_matrix = new double[hitBall.size()][hitBall.size()];
        ball_center_direction = new PlainVector();
        ball_radius_distance = new double[hitBall.size()][hitBall.size()];
        ball_slope = new double[hitBall.size()][hitBall.size()];
        ball_velocity_scale = new double[hitBall.size()];
        cal_ball_radius_distance(); // 计算小球两两之间的半径距离
        cal_ball_velocity_scale(); // 计算速度标量

    }
    public void swap_velocity_move(){
        for(int i = 0; i < hitBall.size(); i++){
            if(ball_out[i]){
                continue;
            }
            for(int j = i + 1; j < hitBall.size(); j++){
                if(!ball_out[j] && hit_detect(i, j)){
                    swap_velocity(i, j);
                }
            }
        }
    }

    public void Momentum_move(double bound_up, double bound_down, double bound_left, double bound_right){ // 使用动量定理和能量守恒定理推导得出的速度表达式，容易受计算精度误差影响
        for(int i = 0; i < hitBall.size(); i++){
            for(int j = i + 1; j < hitBall.size(); j++){
                if(hit_detect(i, j)){
                    ball_slope[i][j] =(hitBall.get(i).getCenterY() - hitBall.get(j).getCenterY())/
                            (hitBall.get(i).getCenterX() - hitBall.get(j).getCenterX());
                    ball_collision(i, j);
                }
            }
        }
        bound_collision(bound_up, bound_down, bound_left, bound_right);
        for (Ball ball : hitBall) {
            ball.setCenterX(ball.getCenterX() + ball.getSpeed_x());
            ball.setCenterY(ball.getCenterY() + ball.getSpeed_y());
        }
    }

    @Override
    public void addToGroup(ObservableList<Node> group) {
        group.addAll(hitBall);
    }

    private void cal_ball_velocity_scale(){
        for(int i = 0; i < hitBall.size(); i++){
            ball_velocity_scale[i] = Math.sqrt(Math.pow(hitBall.get(i).getSpeed_x(),2) +
                    Math.pow(hitBall.get(i).getSpeed_y(), 2));
        }
    }
    private void cal_ball_radius_distance(){
        for(int i = 0; i < hitBall.size(); i++){
            for(int j = i + 1; j < hitBall.size(); j++){
                ball_radius_distance[i][j] = Math.pow(hitBall.get(i).getRadius() + hitBall.get(i).getRadius(), 2);
            }
        }
    }
    private void swap_velocity(int i, int j){
        update_unit_direction_vector(i, j);
        // 计算i在j方向上的投影
        Vector<PlainVector> v_i = velocity_decomposition(hitBall.get(i).velocityVector, ball_center_direction);
        Vector<PlainVector> v_j = velocity_decomposition(hitBall.get(j).velocityVector, ball_center_direction);
        hitBall.get(i).setSpeed_x(v_i.get(1).get_first_weight() + v_j.get(0).get_first_weight());
        hitBall.get(i).setSpeed_y(v_i.get(1).get_second_weight() + v_j.get(0).get_second_weight());
        hitBall.get(j).setSpeed_x(v_j.get(1).get_first_weight() + v_i.get(0).get_first_weight());
        hitBall.get(j).setSpeed_y(v_j.get(1).get_second_weight() + v_i.get(0).get_second_weight());
    }


    private void ball_collision(int i, int j) {
        double dxA1 = (Math.pow(ball_slope[i][j], 2) * hitBall.get(i).getSpeed_x() -
                ball_slope[i][j] * hitBall.get(i).getSpeed_y() +
                hitBall.get(j).getSpeed_x() +
                ball_slope[i][j] * hitBall.get(j).getSpeed_y()) /
                (Math.pow(ball_slope[i][j], 2) + 1);
        double dyA1 = hitBall.get(i).getSpeed_y() + ball_slope[i][j] * (dxA1 - hitBall.get(i).getSpeed_x());
        double dxB1 = hitBall.get(i).getSpeed_y() - dxA1 + hitBall.get(j).getSpeed_y();
        double dyB1 = ball_slope[i][j] * (dxB1 - hitBall.get(j).getSpeed_x()) + hitBall.get(j).getSpeed_y();
        hitBall.get(i).setSpeed_y(dxA1);
        hitBall.get(i).setSpeed_x(dyA1);
        hitBall.get(j).setSpeed_y(dxB1);
        hitBall.get(j).setSpeed_x(dyB1);
    }

    private boolean hit_detect(int i, int j) {
        distance_matrix[i][j] = Math.pow(hitBall.get(i).getCenterX() - hitBall.get(j).getCenterX(),2) +
                Math.pow(hitBall.get(i).getCenterY() - hitBall.get(j).getCenterY(),2);
        return distance_matrix[i][j] <= ball_radius_distance[i][j];
    }

    private void bound_collision(double bound_up, double bound_down, double bound_left, double bound_right){
        double xMin = 0;
        double xMax = 0;
        double yMin = 0;
        double yMax = 0;
        for (Ball ball : hitBall) {
            xMin = ball.getBoundsInParent().getMinX();
            xMax = ball.getBoundsInParent().getMaxX();
            yMin = ball.getBoundsInParent().getMinY();
            yMax = ball.getBoundsInParent().getMaxY();
            if (xMin < bound_left || xMax > bound_right) {
                ball.setSpeed_x(-1 * ball.getSpeed_x());
            }
            if (yMin < bound_down || yMax > bound_up) {
                ball.setSpeed_y(-1 * ball.getSpeed_y());
            }
        }

    }
    /**
     *
     * @param v ： 被投影的的速度向量
     * @param ref_unit_v ：投影向量的参考方向向量
     * @return ：元素0：垂直于ref_unit_v的分量；元素1：沿着ref_unit_v的分量
     */
    private Vector<PlainVector> velocity_decomposition(PlainVector v, PlainVector ref_unit_v) {
        Vector<PlainVector> decomposition_v = new Vector<PlainVector>(2);
        decomposition_v.add(new PlainVector());
        decomposition_v.add(new PlainVector());
        double projection_length = v.get_first_weight() * ref_unit_v.get_first_weight() +
                v.get_second_weight() * ref_unit_v.get_second_weight();
        decomposition_v.get(0).setV_vector(projection_length * ref_unit_v.get_first_weight(),
                projection_length * ref_unit_v.get_second_weight());
        decomposition_v.get(1).setV_vector(v.get_first_weight() - decomposition_v.get(0).get_first_weight(),
                v.get_second_weight() - decomposition_v.get(0).get_second_weight());
        return decomposition_v;
    }
    private void update_unit_direction_vector(int i, int j){
        double direction_x = hitBall.get(i).getCenterX() - hitBall.get(j).getCenterX();
        double direction_y = hitBall.get(i).getCenterY() - hitBall.get(j).getCenterY();
        scale = Math.sqrt(Math.pow(direction_x, 2) + Math.pow(direction_y, 2));
        ball_center_direction.set_vector_x(direction_x / scale);
        ball_center_direction.set_vector_y(direction_y / scale);
    }

    public ArrayList<Ball> getHitBall(){
        return hitBall;
    }

    public boolean isOut(int index){
        return ball_out[index];
    }
    public void setOutBall(int index){
        ball_out[index] = true;
    }
    @Override
    public boolean hasNext() {
        if(index < hitBall.size()){
            return true;
        }
        index = 0;
        return false;
    }

    @Override
    public Object next() {
        return hitBall.get(index++);
    }
}
