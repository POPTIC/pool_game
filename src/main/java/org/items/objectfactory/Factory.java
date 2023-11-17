package org.items.objectfactory;

import javafx.scene.paint.Color;
import org.items.gamefactor.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Factory {
    private static final int BLACK = 0;
    private static final int RED = 1;
    private static final int BLUE = 2;
    private static final int PINK = 3;
    private static final int GREEN = 4;
    private static final int YELLOW = 5;

    private static final int UP_LEFT = 0;
    private static final int UP_MID = 1;
    private static final int UP_RIGHT = 2;
    private static final int DOWN_LEFT = 3;
    private static final int DOWN_MID = 4;
    private static final int DOWN_RIGHT = 5;
    private static final int MAX_HOLE = 6;

    public static Color getcolor(int index){
        return switch (index) {
            case BLACK -> Color.BLACK;
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case YELLOW -> Color.YELLOW;
            case PINK-> Color.PINK;
            case GREEN-> Color.GREEN;
            default -> Color.GREY;
        };
    }

    private static void get_ball_hole_location(int index, double table_width,
                                                            double table_height, double hole_radius,
                                                            ArrayList<Double> location_v){
        switch (index){
            case DOWN_LEFT:
                location_v.set(0, hole_radius + 10);
                location_v.set(1, table_height);
                return;
            case DOWN_MID:
                location_v.set(0, table_width/2 + 15);
                location_v.set(1, table_height);
                return;
            case DOWN_RIGHT:
                location_v.set(0, table_width);
                location_v.set(1, table_height);
                return;
            case UP_LEFT:
                location_v.set(0, hole_radius + 10);
                location_v.set(1, hole_radius);
                return;
            case UP_MID:
                location_v.set(0, table_width/2 + 15);
                location_v.set(1, hole_radius);
                return;
            case UP_RIGHT:
                location_v.set(0, table_width);
                location_v.set(1, hole_radius);
                return;
        }
        return;
    }
    public static GameItemSet parse_game_config() throws ParseException, IOException {

        ArrayList<Ball> hitBalls = null;
        GameTable table = null;
        ControlBall controlBall = null;
        ArrayList<BallHole> ballHoles = new ArrayList<>();

        String path = "/game_config.json";
        String whole_path = Objects.requireNonNull(Factory.class.getResource(path)).getPath();
        JSONParser parser = new JSONParser();

        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(whole_path));
        JSONObject jsonTable = (JSONObject) jsonObject.get("table");
        JSONObject jsonControlBall = (JSONObject) jsonObject.get("key-ball");
        JSONArray jsonBalls = (JSONArray) jsonObject.get("ball_set");
        JSONObject jsonHole = (JSONObject)  jsonObject.get("ball-hole");

        Double location_x = (Double) jsonControlBall.get("location_x");
        Double location_y = (Double) jsonControlBall.get("location_y");
        Long color = (Long) jsonControlBall.get("color");
        Double circle_radius = (Double) jsonControlBall.get("circle_radius");
        Double init_speed_x = (Double) jsonControlBall.get("init_speed_x");
        Double init_speed_y = (Double) jsonControlBall.get("init_speed_y");

        Ball specialBall = new Ball(location_x,location_y,circle_radius,getcolor(color.intValue()),init_speed_x,init_speed_y);
        controlBall = new ControlBall();
        controlBall.setBall(specialBall);

        Double table_length = (Double) jsonTable.get("table_length");
        Double table_width = (Double) jsonTable.get("table_width");
        Double frictional_coefficient = (Double) jsonTable.get("frictional_coefficient");
        table = new GameTable(table_length, table_width, frictional_coefficient);


        hitBalls = new ArrayList<>(jsonBalls.size());
        for (Object obj : jsonBalls) {
            JSONObject jsonBall = (JSONObject) obj;
            location_x = (Double) jsonBall.get("location_x");
            location_y = (Double) jsonBall.get("location_y");
            color = (Long) jsonBall.get("color");
            circle_radius = (Double) jsonBall.get("circle_radius");
            init_speed_x = (Double) jsonBall.get("init_speed_x");
            init_speed_y = (Double) jsonBall.get("init_speed_y");
            hitBalls.add(new Ball(location_x,location_y,circle_radius,getcolor(color.intValue()),init_speed_x,init_speed_y));
        }
        hitBalls.add(specialBall);

        double hole_size = (Double) jsonHole.get("size");
        ArrayList<Double> location_v = new ArrayList<>(2);
        location_v.add(0.0);
        location_v.add(0.0);
        for(int i = 0; i < MAX_HOLE; i++){
            get_ball_hole_location(i, table_length, table_width, hole_size, location_v);
            ballHoles.add(new BallHole(hole_size, location_v.get(0), location_v.get(1)));
        }


        return new GameItemSet(hitBalls, controlBall, table, ballHoles);
    }
}
