package org.example;

import javafx.application.Application;
import javafx.stage.Stage;
import org.items.Game;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws IOException, ParseException {
        Game game = new Game();
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setTitle("BallGame");

        primaryStage.show();
        primaryStage.setScene(game.getScene());
    }
}
