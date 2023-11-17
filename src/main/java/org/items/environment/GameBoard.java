package org.items.environment;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import org.items.gamefactor.Drawable;

/**
 * @param
 * @return
 */
public class GameBoard implements Drawable {
    private Canvas canvas;
    private Label scoreLabel;
    private Integer score;

    double location_x;
    double location_y;
    public GameBoard(double width, double height,double inset_top, double inset_left){
        canvas = new Canvas(width, height);
        scoreLabel = new Label("Score: " + score);
        Font font = new Font("Arial", 30);
        scoreLabel.setFont(font);
        scoreLabel.setPadding(new Insets(inset_top ,0 ,0 ,inset_left));
        this.score = 0;
    }
    public void add_point(){
        score++;
    }
    public void deduce_point(){
        score = score - 2;
    }
    public void text_update(){
        scoreLabel.setText("score:" + score.toString());
    }

    @Override
    public void addToGroup(ObservableList<Node> group) {
        group.add(canvas);
        group.add(scoreLabel);
    }
}
