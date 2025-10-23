package com;

//import java.awt.Point;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.MyPoint.Point;
import com.google.gson.Gson;

public class testsjson {
    public static void main(String[] args) throws IOException {

        Gson gson = new Gson();
        
        Distance distance = new Distance();


        String apikey = "DBBDD515-A8B0-3BFF-BBAD-4C0F71172A74"; 
        String address = "삼평동 624";
        MyPoint myPoint = new MyPoint();
        Point resultPoint = myPoint.getCoordinatesFromAddress(apikey, address);
        
        // JSON 파일 읽기 (프로젝트 루트에 starbucks.json이 있어야 함)
        String jsonString = new String(Files.readAllBytes(Paths.get("starbucks.json")), StandardCharsets.UTF_8);

        // JSON → Store 객체 배열로 변환
        Store[] stores = gson.fromJson(jsonString, Store[].class);

        // 각 매장 정보 출력
        for (Store store : stores) {
            System.out.println("매장이름: " + store.name);
            System.out.println("위도: " + store.latitude);
            System.out.println("경도: " + store.longitude);
            //System.out.println("매장타입: " + store.type);
            //System.out.println("주소: " + store.address);
            //System.out.println("전화: " + store.phone);
            //System.out.println("설명: " + store.description);
            
            double Doublelat = Double.parseDouble(store.latitude);
            double Doublelon = Double.parseDouble(store.longitude);
            double Mylon = resultPoint.getX();
            double Mylat = resultPoint.getY();
            
            
            
            System.out.printf("%.14f\n", Doublelat);
            System.out.printf("%.14f\n", Doublelon);
            System.out.printf("%.14f\n", Mylat);
            System.out.printf("%.14f\n", Mylon);
            System.out.println("===== API 호출 결과 =====");
            System.out.println("현재위치 : " + address);
            System.out.println("변환된 좌표: " + resultPoint.toString()); 
            double Mydistance = distance.calculateDistance(Doublelat, Doublelon, Mylat, Mylon);
            
            System.out.println(Mydistance);
            
            
            
            System.out.println("--------------------------");
            
        }
        
        
    }
}
