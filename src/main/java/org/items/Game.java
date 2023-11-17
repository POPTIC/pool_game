package org.items;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Duration;
import org.items.gamefactor.*;
import org.items.environment.GameBoard;
import org.items.objectfactory.Factory;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Game{
    public double FRAME_DURATION = 0.0027;
    List<Ball> hitBall;
    Group root;
    Scene scene;

    GameBoard gameBoard;
    Timeline animationLoop;

    GameItemSet gameItemSet;
    // constructor
    public Game() throws IOException, ParseException {
        root = new Group();

        scene = new Scene(root);

        gameItemSet = Factory.parse_game_config();

        gameBoard = new GameBoard(800, 600, 100, 650);

        gameBoard.addToGroup(root.getChildren());

        gameItemSet.addToGroup(root.getChildren());


        animationLoop = new Timeline();
        animationLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(FRAME_DURATION),(actionEvent)->game_frame_update());
        animationLoop.getKeyFrames().add(frame);

        animationLoop.play();

    }
    public void game_frame_update(){
        // 球体碰撞运动
        LinkedList<Integer> out_ball = gameItemSet.motion();
        // 计分
        for(int out : out_ball){
            // 球进洞消失
            gameItemSet.getCollisionBalls().getHitBall().get(out).setVisible(false);
            // 设置球速度为0
            gameItemSet.getCollisionBalls().getHitBall().get(out).setSpeed_x(0);
            gameItemSet.getCollisionBalls().getHitBall().get(out).setSpeed_y(1);
            // 关键球进洞扣分
            if(gameItemSet.isSpecialBall(out)){
                // 重置关键球位置
                gameBoard.deduce_point();
            }
            // 普通求进洞得分
            else{
                gameBoard.add_point();
            }
        }
        gameBoard.text_update();
    }
    public Scene getScene() {
        return scene;
    }

    // tool function
}
