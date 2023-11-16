package org.example;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Circle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import org.items.Factory;

import java.io.IOException;
import java.util.List;

public class Main1 extends Application {
    private static final double KEY_FRAME_DURATION = 0.0017;
    private static double dyA = 2;
    private static double dxA = 2;
    private static double dxB = -1;
    private static double dyB = -1;
    private static double k = 0;

    private static int count = 0;
    private static boolean flag = true;
    private static double radius;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setWidth(600);
        primaryStage.setHeight(440);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FX");
        primaryStage.show();
        Canvas canvas = new Canvas(600,400);
        root.getChildren().add(canvas);
        List<Circle> circleList = Factory.createCircle();
        Circle circleA = circleList.get(0);
        Circle circleB = circleList.get(1);
        // 如果位置防止不当导致球体部分超出边界则会导致球体原地抖动
        radius = Math.pow((circleA.getRadius() + circleB.getRadius()), 2);
//        circle.setOnMouseDragged(event -> {
//            circle.setCenterX(event.getX());
//            circle.setCenterY(event.getY());
//            circle.setFill(Color.RED);
//        });
        root.getChildren().add(circleA);
        root.getChildren().add(circleB);
        Timeline animationLoop = new Timeline();
        animationLoop.setCycleCount(Timeline.INDEFINITE);
        KeyFrame frame = new KeyFrame(Duration.seconds(KEY_FRAME_DURATION), // 第一个参数事件时间触发刷新频率
                (actionEvent) -> { // 第二个参数定义每个tick下要触发的事件
//Your event handler logic
                    double yMinA = circleA.getBoundsInParent().getMinY();
                    double yMaxA = circleA.getBoundsInParent().getMaxY();
                    double xMinA = circleA.getBoundsInParent().getMinX();
                    double xMaxA = circleA.getBoundsInParent().getMaxX();
                    double yMinB = circleB.getBoundsInParent().getMinY();
                    double yMaxB = circleB.getBoundsInParent().getMaxY();
                    double xMinB = circleB.getBoundsInParent().getMinX();
                    double xMaxB = circleB.getBoundsInParent().getMaxX();
                    double circleA_x = circleA.getCenterX();
                    double circleA_y = circleA.getCenterY();
                    double circleB_x = circleB.getCenterX();
                    double circleB_y = circleB.getCenterY();
                    if(Math.pow(circleA_x - circleB_x,2) + Math.pow(circleA_y - circleB_y,2) <= radius){
                        if(flag){
                            count++;
                            flag = false;
                        }
                        double dxA1 = (Math.pow(k, 2) * dxA - k * dyA + dxB + k*dyB) / (Math.pow(k, 2) + 1);
                        double dyA1 = dyA + k * (dxA1 - dxA);
                        double dxB1 = dxA - dxA1 + dxB;
                        double dyB1 = k * (dxB1 - dxB) + dyB;
                        dxA = dxA1;
                        dyA = dyA1;
                        dxB = dxB1;
                        dyB = dyB1;
                    }
                    else{
                        flag = true;
                        k = (circleA.getCenterY() - circleB.getCenterY())/(circleA.getCenterX() - circleB.getCenterX());

                    }
                    if (yMinA < 0 || yMaxA > canvas.getBoundsInLocal().getMaxY())
                    {
                        dyA = dyA * -1;
                    }
                    if (xMinA < 0 || xMaxA > canvas.getBoundsInLocal().getMaxX()) {
                        dxA = dxA * -1;
                    }
                    if (yMinB < 0 || yMaxB > canvas.getBoundsInLocal().getMaxY())
                    {
                        dyB = dyB * -1;
                    }
                    if (xMinB < 0 || xMaxB > canvas.getBoundsInLocal().getMaxX()) {
                        dxB = dxB * -1;
                    }
                    circleA.setCenterX(circleA.getCenterX() + dxA);
                    circleA.setCenterY(circleA.getCenterY() + dyA);
                    circleB.setCenterX(circleB.getCenterX() + dxB);
                    circleB.setCenterY(circleB.getCenterY() + dyB);

                });
        animationLoop.getKeyFrames().add(frame);
        animationLoop.play();
    }
}