package org.items;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game implements MovingProcess{
    public double FRAME_DURATION = 0.0027;
    List<Ball> hitBall;
    Group root;
    Scene scene;
    Canvas canvas;
    Timeline animationLoop;

    double[][] distance_matrix;
    double[][] ball_radius_distance;
    double[][] ball_slope;
    double[] ball_velocity_scale;

    double direction_x;
    double direction_y;
    double scale;


    // constructor
    public Game() throws IOException {
        root = new Group();

        scene = new Scene(root);

        hitBall = Factory.createBalls();

        canvas = new Canvas(600,400);

        root.getChildren().add(canvas);
        for (Ball ball : hitBall) {
            root.getChildren().add(ball);
        }
        distance_matrix = new double[hitBall.size()][hitBall.size()];
        ball_radius_distance = new double[hitBall.size()][hitBall.size()];
        ball_slope = new double[hitBall.size()][hitBall.size()];
        ball_velocity_scale = new double[hitBall.size()];

        cal_ball_radius_distance(); // 计算小球两两之间的半径距离
        cal_ball_velocity_scale(); // 计算速度标量

        animationLoop = new Timeline();
        animationLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(FRAME_DURATION),(actionEvent)->swap_velocity_move());
        animationLoop.getKeyFrames().add(frame);

        animationLoop.play();

    }
    public Scene getScene() {
        return scene;
    }

    // tool function
    public void cal_ball_velocity_scale(){
        for(int i = 0; i < hitBall.size(); i++){
            ball_velocity_scale[i] = Math.sqrt(Math.pow(hitBall.get(i).getSpeed_x(),2) +
                                    Math.pow(hitBall.get(i).getSpeed_y(), 2));
        }
    }
    public void cal_ball_radius_distance(){
        for(int i = 0; i < hitBall.size(); i++){
            for(int j = i + 1; j < hitBall.size(); j++){
                ball_radius_distance[i][j] = Math.pow(hitBall.get(i).getRadius() + hitBall.get(i).getRadius(), 2);
            }
        }
    }
    public void swap_velocity(int i, int j){
        update_unit_direction_vector(i, j);

        // 计算i在j方向上的投影
        double projection_length_i = hitBall.get(i).speed_x * direction_x +
                                     hitBall.get(i).speed_y * direction_y;
        double projection_i_x = projection_length_i * direction_x;
        double projection_i_y = projection_length_i * direction_y;
        double orthogonalProjection_i_x = hitBall.get(i).speed_x - projection_i_x;
        double orthogonalProjection_i_y = hitBall.get(i).speed_y - projection_i_y;
        // 计算j在i方向上的投影
        double projection_length_j = hitBall.get(j).speed_x * direction_x +
                                     hitBall.get(j).speed_y * direction_y;
        double projection_j_x = projection_length_j * direction_x;
        double projection_j_y = projection_length_j * direction_y;
        double orthogonalProjection_j_x = hitBall.get(j).speed_x - projection_j_x;
        double orthogonalProjection_j_y = hitBall.get(j).speed_y - projection_j_y;
        hitBall.get(i).speed_x = projection_j_x + orthogonalProjection_i_x;
        hitBall.get(i).speed_y = projection_j_y + orthogonalProjection_i_y;
        hitBall.get(j).speed_x = projection_i_x + orthogonalProjection_j_x;
        hitBall.get(j).speed_y = projection_i_y + orthogonalProjection_j_y;
    }
    public void swap_velocity_move(){
        for(int i = 0; i < hitBall.size(); i++){
            for(int j = i + 1; j < hitBall.size(); j++){
                if(hit_detect(i, j)){
                    swap_velocity(i, j);
                }
            }
        }
        bound_collision();
        for (Ball ball : hitBall) {
            ball.setCenterX(ball.getCenterX() + ball.speed_x);
            ball.setCenterY(ball.getCenterY() + ball.speed_y);
        }
    }
    public void Momentum_move(){ // 使用动量定理和能量守恒定理推导得出的速度表达式，容易受计算精度误差影响
        for(int i = 0; i < hitBall.size(); i++){
            for(int j = i + 1; j < hitBall.size(); j++){
                if(hit_detect(i, j)){
                    ball_slope[i][j] =(hitBall.get(i).getCenterY() - hitBall.get(j).getCenterY())/
                            (hitBall.get(i).getCenterX() - hitBall.get(j).getCenterX());
                    ball_collision(i, j);
                }
            }
        }
        bound_collision();
        for (Ball ball : hitBall) {
            ball.setCenterX(ball.getCenterX() + ball.speed_x);
            ball.setCenterY(ball.getCenterY() + ball.speed_y);
        }
    }
    @Override
    public void ball_collision(int i, int j) {
        double dxA1 = (Math.pow(ball_slope[i][j], 2) * hitBall.get(i).speed_x -
                                ball_slope[i][j] * hitBall.get(i).speed_y +
                                hitBall.get(j).speed_x +
                                ball_slope[i][j] * hitBall.get(j).speed_y) /
                                (Math.pow(ball_slope[i][j], 2) + 1);
        double dyA1 = hitBall.get(i).speed_y + ball_slope[i][j] * (dxA1 - hitBall.get(i).speed_x);
        double dxB1 = hitBall.get(i).speed_y - dxA1 + hitBall.get(j).speed_y;
        double dyB1 = ball_slope[i][j] * (dxB1 - hitBall.get(j).speed_x) + hitBall.get(j).speed_y;
        hitBall.get(i).speed_y = dxA1;
        hitBall.get(i).speed_x = dyA1;
        hitBall.get(j).speed_y = dxB1;
        hitBall.get(j).speed_x = dyB1;
    }

    @Override
    public boolean hit_detect(int i, int j) {
        distance_matrix[i][j] = Math.pow(hitBall.get(i).getCenterX() - hitBall.get(j).getCenterX(),2) +
                Math.pow(hitBall.get(i).getCenterY() - hitBall.get(j).getCenterY(),2);
        return distance_matrix[i][j] <= ball_radius_distance[i][j];
    }
    public void bound_collision(){
        double xMin = 0;
        double xMax = 0;
        double yMin = 0;
        double yMax = 0;
        for (Ball ball : hitBall) {
            xMin = ball.getBoundsInParent().getMinX();
            xMax = ball.getBoundsInParent().getMaxX();
            yMin = ball.getBoundsInParent().getMinY();
            yMax = ball.getBoundsInParent().getMaxY();
            if (xMin < 0 || xMax > canvas.getBoundsInLocal().getMaxX()) {
                ball.speed_x = -1 * ball.speed_x;
            }
            if (yMin < 0 || yMax > canvas.getBoundsInLocal().getMaxY()) {
                ball.speed_y = -1 * ball.speed_y;
            }
        }

    }
    public ArrayList<VelocityVector> velocity_decomposition(VelocityVector v, VelocityVector ref_unit_v){
        ArrayList<VelocityVector> decomposition_v = new ArrayList<>(2);
        VelocityVector a = new VelocityVector();
        double projection_length = v.get_first_weight() * ref_unit_v.get_first_weight() +
                v.get_second_weight() * ref_unit_v.get_second_weight();
        decomposition_v.get(0).setV_vector(projection_length * ref_unit_v.get_first_weight(),
                projection_length * ref_unit_v.get_second_weight());
        decomposition_v.get(1).setV_vector(v.get_first_weight() - decomposition_v.get(0).get_first_weight(),
                v.get_second_weight() - decomposition_v.get(0).get_second_weight());

        return decomposition_v;
    }
    private void update_unit_direction_vector(int i, int j){
        direction_x = hitBall.get(i).getCenterX() - hitBall.get(j).getCenterX();
        direction_y = hitBall.get(i).getCenterY() - hitBall.get(j).getCenterY();
        scale = Math.sqrt(Math.pow(direction_x, 2) + Math.pow(direction_y, 2));
        direction_x = direction_x / scale;
        direction_y = direction_y / scale;
    }
    private void velocity_decomposition(int i){
        double projection_length = hitBall.get(i).speed_x * direction_x +
                hitBall.get(i).speed_y * direction_y;
        double projection_x = projection_length * direction_x;
        double projection_y = projection_length * direction_y;
        double orthogonalProjection_x = hitBall.get(i).speed_x - projection_x;
        double orthogonalProjection_y = hitBall.get(i).speed_y - projection_y;
    }
}
