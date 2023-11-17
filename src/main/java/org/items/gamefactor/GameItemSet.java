package org.items.gamefactor;

import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @param
 * @return
 */
public class GameItemSet implements Drawable {
    CollisionBalls collisionBalls;
    ControlBall controlBall;
    GameTable gameTable;
    ArrayList<BallHole> ballHoles;

    public GameItemSet(ArrayList<Ball> collisionBalls, ControlBall controlBall, GameTable gameTable, ArrayList<BallHole> ballHoles) throws IOException {
        this.collisionBalls = new CollisionBalls(collisionBalls);
        this.controlBall = controlBall;
        this.gameTable = gameTable;
        this.ballHoles = ballHoles;
    }

    public LinkedList<Integer> motion(){
        LinkedList<Integer> outBallList= gain_score_detect();
        for(int i : outBallList){
            collisionBalls.setOutBall(i);
        }
        collisionBalls.swap_velocity_move();
        bound_collision();
        ArrayList<Double> tmp_speed = new ArrayList<>(2);
        tmp_speed.add(0.0);
        tmp_speed.add(0.0);
        for (Ball ball : collisionBalls.getHitBall()) {
            tmp_speed.set(0, ball.getSpeed_x());
            tmp_speed.set(1, ball.getSpeed_y());
            gameTable.frictional_process(tmp_speed);
            ball.setSpeed_x(tmp_speed.get(0));
            ball.setSpeed_y(tmp_speed.get(1));
            ball.setCenterX(ball.getCenterX() + ball.getSpeed_x());
            ball.setCenterY(ball.getCenterY() + ball.getSpeed_y());
        }
        return outBallList;
    }
    private void bound_collision(){
        for (Ball ball : collisionBalls.getHitBall()) {
            if (ball.getBoundsInParent().getMinX() < gameTable.get_boundary_left() ||
                    ball.getBoundsInParent().getMaxX() > gameTable.get_boundary_right()) {
                ball.setSpeed_x(-1 * ball.getSpeed_x());
            }
            if (ball.getBoundsInParent().getMinY() < gameTable.get_boundary_down() ||
                    ball.getBoundsInParent().getMaxY() > gameTable.get_boundary_up()) {
                ball.setSpeed_y(-1 * ball.getSpeed_y());
            }
        }

    }
    public LinkedList<Integer> gain_score_detect(){
        LinkedList<Integer> outBallList = new LinkedList<>();
        ArrayList<Ball> ballList = collisionBalls.getHitBall();
        for (int i = 0; i < ballList.size(); i++) {
            if(collisionBalls.isOut(i)){
                continue;
            }
            for(BallHole ballhole : ballHoles){
                if(Math.sqrt(Math.pow(ballhole.getCenterX() - ballList.get(i).getCenterX(), 2) +
                            Math.pow(ballhole.getCenterY() - ballList.get(i).getCenterY(), 2))
                        <= ballhole.getRadius()){
                    outBallList.add(i);
                }
            }
        }
        return outBallList;
    }
    public boolean isSpecialBall(int index){
        return controlBall.getBall().equals(collisionBalls.getHitBall().get(index));
    }
    public CollisionBalls getCollisionBalls() {
        return collisionBalls;
    }

    public ControlBall getControlBall() {
        return controlBall;
    }

    public GameTable getGameTable() {
        return gameTable;
    }

    public ArrayList<BallHole> getBallHoles() {
        return ballHoles;
    }

    @Override
    public void addToGroup(ObservableList<Node> group) {
        collisionBalls.addToGroup(group);
        controlBall.addToGroup(group);
        gameTable.addToGroup(group);
        group.addAll(ballHoles);
    }
}
