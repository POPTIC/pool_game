package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.configtool.ConfigReader;
import org.items.CircleDataObject;

import java.io.IOException;
import java.io.InputStream;

public class Main3 {
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // 从资源包中获取文件的输入流
        InputStream inputStream = ConfigReader.class.getResourceAsStream("./data.json");
//        // 从输入流中读取JSON并映射到Java对象
        CircleDataObject circleData = objectMapper.readValue(inputStream, CircleDataObject.class);
        System.out.println(circleData.getLocation_x());
    }
}
