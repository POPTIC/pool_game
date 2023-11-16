package org.configtool;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.shape.Circle;
import org.items.Ball;
import org.items.CircleDataObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ConfigReader {
    public static Ball ball_config(Ball ball) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 从资源包中获取文件的输入流
        InputStream inputStream = ConfigReader.class.getResourceAsStream("/ball_config.json");
        // 从输入流中读取JSON并映射到Java对象
        ball = objectMapper.readValue(inputStream, Ball.class);
        System.out.println(ball.toString());
        return ball;
    }
    public static List<CircleDataObject> circleArray_config() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        // 从资源包中获取文件的输入流
        InputStream inputStream = ConfigReader.class.getResourceAsStream("/circle_config.json");
//        // 从输入流中读取JSON并映射到Java对象
        return Arrays.asList(objectMapper.readValue(inputStream,
                CircleDataObject[].class));
    }
    public static List<Ball> ballsArray_config() throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        // 从资源包中获取文件的输入流
        InputStream inputStream = ConfigReader.class.getResourceAsStream("/ball_config.json");
//        // 从输入流中读取JSON并映射到Java对象
//        circle = objectMapper.readValue(inputStream, CircleDataObject.class);
        return Arrays.asList(objectMapper.readValue(inputStream,
                Ball[].class));
    }
}
