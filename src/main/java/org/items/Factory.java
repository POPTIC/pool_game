package org.items;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.configtool.ConfigReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Factory {
    // 配置circle对象
    public static List<Circle> createCircle() throws IOException {
        List<CircleDataObject> circleDataList = ConfigReader.circleArray_config();
        List<Circle> circleList = new ArrayList<Circle>(circleDataList.size()); // 这里指定的数值只是指定了ArrayList的上限，
                                                                            // 并没有给容器初始化值
        for (CircleDataObject circleDataObject : circleDataList) {
            circleList.add(new Circle(circleDataObject.getLocation_x(),
                    circleDataObject.getLocation_y(),
                    circleDataObject.getRadius(),
                    getcolor(circleDataObject.getColor())));
        }
        return circleList;
    }
    public static List<Ball> createBalls() throws IOException {
        List<CircleDataObject> circleDataList = ConfigReader.circleArray_config();
        List<Ball> ballsList = new ArrayList<Ball>(circleDataList.size()); // 这里指定的数值只是指定了ArrayList的上限，
         // 并没有给容器初始化值
        for (CircleDataObject circleDataObject: circleDataList) {
            ballsList.add(new Ball(circleDataObject.getLocation_x(),circleDataObject.getLocation_y(),
                                    circleDataObject.getRadius(),getcolor(circleDataObject.getColor()),
                                    circleDataObject.get_speed_x(),circleDataObject.get_speed_y()));
        }
        return ballsList;
    }
    public static Color getcolor(int index){
        return switch (index) {
            case 1 -> Color.RED;
            case 2 -> Color.BLUE;
            case 3 -> Color.YELLOW;
            default -> Color.BLACK;
        };
    }
}
