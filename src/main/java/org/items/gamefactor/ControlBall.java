package org.items.gamefactor;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * @param
 * @return
 */
public class ControlBall{
    private Ball ball;
    private static final double add_speed = 1;
    private Line mouseDragLine;
    public ControlBall(double location_x, double location_y, double radius, Color color, double init_speed_x, double init_speed_y) {
        ball = new Ball(location_x, location_y, radius, color, init_speed_x, init_speed_y);
        mouseDragLine = new Line();
        mouseDragLine.setVisible(false);
    }
    public Ball getBall(){
        return ball;
    }
    public ControlBall() {
        ball = null;
        mouseDragLine = null;
    }

    public void setBall(Ball ball){
        this.ball = ball;
        this.mouseDragLine = new Line();
        this.mouseDragLine.setVisible(false);
        ball.setOnMouseDragged(
                (actionEvent) ->{
                    this.mouseDragLine.setVisible(true);
                    this.mouseDragLine.setStartX(ball.getCenterX());
                    this.mouseDragLine.setStartY(ball.getCenterY());
                    this.mouseDragLine.setEndX(actionEvent.getSceneX());
                    this.mouseDragLine.setEndY(actionEvent.getSceneY());
                        }
        );
        ball.setOnMouseReleased(
                (actionEvent) ->{
                    this.mouseDragLine.setVisible(false);
                    double x = ball.getCenterX() - actionEvent.getSceneX();
                    double y = ball.getCenterY() - actionEvent.getSceneY();
                    double scale =  Math.sqrt(Math.pow(x, 2) +
                            Math.pow(y, 2));
                    ball.setSpeed_x(2 * x / scale);
                    ball.setSpeed_y(2 * y / scale);
                }
        );
    }
    public void addToGroup(ObservableList<Node> groupChildren) {
        groupChildren.add(mouseDragLine);
    }
}
