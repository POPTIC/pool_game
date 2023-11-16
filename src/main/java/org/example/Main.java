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
import org.items.Game;

import java.awt.font.GlyphMetrics;
import java.io.IOException;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Game game = new Game();
        primaryStage.setWidth(600);
        primaryStage.setHeight(440);
        primaryStage.setTitle("BallGame");
        primaryStage.show();
        primaryStage.setScene(game.getScene());
    }
}
