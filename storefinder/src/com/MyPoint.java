package com;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MyPoint {

    // --- 🎯 Gson 모델 클래스 정의 (외부 호출이 가능하도록 public static으로 유지) ---
    // Point 객체를 반환하기 위해 Point 클래스를 public static으로 선언합니다.

    public static class Point {
        // !!! 수정: x, y 필드를 String에서 double로 변경하여 바로 실수 값으로 받습니다.
        private String x; // 경도 (Longitude)
        private String y; // 위도 (Latitude)

        public String getX() { return x; }
        public String getY() { return y; }
        
        // 디버깅 용도로 toString()을 오버라이드합니다.
        // 정밀도 유지를 위해 String.format을 사용합니다.
        @Override
        public String toString() {
            // 소수점 아래 14자리까지 출력 포맷을 지정하여 정밀도를 유지합니다.
            return String.format("경도(X): %s, 위도(Y): %s", x, y);
        }
    }

    // 내부에서만 사용되므로 private static으로 선언합니다.
    private static class Result {
        private Point point;
        public Point getPoint() { return point; }
    }
    
    private static class Response {
        private Result result;
        private Status status;
        public Result getResult() { return result; }
        public Status getStatus() { return status; }
    }
    
    
    
    private static class ResponseContainer {
        private Response response;
        public Response getResponse() { return response; }
    }
    
    
    
    // --- 🎯 Gson 모델 클래스 정의 끝 ---

    /**
     * 주소를 이용하여 VWorld API에서 좌표를 조회하는 메서드.
     * @param apikey VWorld API 인증키
     * @param searchAddr 조회할 주소 (예: "삼평동 624")
     * @return 주소에 해당하는 좌표(x, y)를 담고 있는 Point 객체 (double 타입 필드 포함)
     * @throws IOException 네트워크 통신 오류 또는 주소 인코딩 오류 시 발생
     */
    public static Point getCoordinatesFromAddress(String apikey, String searchAddr) throws IOException {
    	String searchType = "parcel";
        String epsg = "epsg:4326";
        
        StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
        sb.append("?service=address");
        sb.append("&request=getCoord");
        sb.append("&format=json");
        sb.append("&crs=" + epsg);
        sb.append("&key=" + apikey);
        sb.append("&type=" + searchType);
        
        // 주소 인코딩 및 URL 완성
        String encodedAddr = URLEncoder.encode(searchAddr, StandardCharsets.UTF_8.toString());
        sb.append("&address=" + encodedAddr);

        URL url = new URL(sb.toString());
        
        // 1. API 통신 및 JSON 문자열로 받기
        String jsonString;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            jsonString = result.toString();
        } 
        System.out.println(jsonString);
        // 2. Gson 파싱 및 좌표 추출
        Gson gson = new Gson();
        
        JsonElement element = JsonParser.parseString(jsonString);
		JsonObject object = element.getAsJsonObject();
        JsonObject outStatus = object.get("response").getAsJsonObject();
		Status status = gson.fromJson(outStatus, Status.class);
		if(status.getStatus() != "OK") {
			System.out.println(status.getStatus());
		}else {
			
		}
		
        // JSON 응답의 "x", "y" 필드에 담긴 String 값은
        // Point 클래스의 double 타입 필드로 자동 변환되어 파싱됩니다.
        ResponseContainer container = gson.fromJson(jsonString, ResponseContainer.class);
        
        
        
		
        
        Point point = container.getResponse().getResult().getPoint();
        System.out.println(container.getResponse().getStatus());
        
        List<ResultVO> resultVo = new ArrayList<ResultVO>();
		
        ResultVO getResultVo = new ResultVO(status.getStatus(),point.getX(),point.getY());
        
		
        resultVo.add(getResultVo);
        
        System.out.println(resultVo.toString());
        
        return point; // double 타입 필드를 가진 Point 객체 반환
    }
    
    // 이 클래스 자체를 실행할 때 테스트 코드로 활용합니다.
    public static void main(String[] args) {
        // 메인 함수는 이제 위의 메서드를 호출하는 역할만 합니다.
        // 테스트를 위해 여기에 유효한 API 키를 넣어주세요.
        String apikey = "DBBDD515-A8B0-3BFF-BBAD-4C0F71172A74"; 
        String address = "삼평동 624";
        
        try {
            Point resultPoint = getCoordinatesFromAddress(apikey, address);
            
            System.out.println(resultPoint.toString());
            System.out.println("===== API 호출 결과 =====");
            System.out.println("조회 주소: " + address);
            
            // toString()을 통해 정밀도 있게 출력
            System.out.println("변환된 좌표 (toString): " + resultPoint.toString()); 
            
            // double 타입으로 직접 접근 및 출력 테스트
//            double lon = resultPoint.getX();
//            double lat = resultPoint.getY();

//            System.out.println("\n--- double 타입으로 직접 접근 ---");
//            System.out.println("경도(X, double): " + lon);
//            System.out.println("위도(Y, double): " + lat);
//            
//            // 정밀도 확인을 위해 printf로 출력
//            System.out.println("\n--- 정밀도 확인 (printf) ---");
//            System.out.printf("경도(X, %.14f): %.14f\n", lon, lon);
//            System.out.printf("위도(Y, %.14f): %.14f\n", lat, lat);
            
        } catch (IOException e) {
            System.err.println("API 호출 또는 통신 중 오류 발생:");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("데이터 파싱 또는 기타 오류 발생:");
            e.printStackTrace();
        }
    }
}
