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
	private static String apikey = "DBBDD515-A8B0-3BFF-BBAD-4C0F71172A74"; 
    // --- 🎯 Gson 모델 클래스 정의 (외부 호출이 가능하도록 public static으로 유지) ---
    // Point 객체를 반환하기 위해 Point 클래스를 public static으로 선언합니다.

    // 내부에서만 사용되므로 private static으로 선언합니다.
    private static class Result {
        private Point point;
        public Point getPoint() { return point; }
    }
    
    private static class Response {
        private Result result;
        private String status;
        public Result getResult() { return result; }
        public String getStatus() { return status; }
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
    public static List<ResultVO> getCoordinatesFromAddress(String searchAddr, String searchType) throws IOException {
        String epsg = "epsg:4326";
       
        StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
        sb.append("?service=address"); //주소
        sb.append("&request=getCoord"); //Get, POST
        sb.append("&format=json"); //받는 형식
        sb.append("&crs=" + epsg);
        sb.append("&key=" + apikey); // Api Key
        sb.append("&type=" + searchType); // PARCEL -> 지번주소, ROAD -> 도로명주소
        
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

        // 2. Gson 파싱 및 좌표 추출
        Gson gson = new Gson();
        
		
        // JSON 응답의 "x", "y" 필드에 담긴 String 값은
        // Point 클래스의 double 타입 필드로 자동 변환되어 파싱됩니다.
        ResponseContainer container = gson.fromJson(jsonString, ResponseContainer.class);

        List<ResultVO> resultVo = new ArrayList<ResultVO>();

        if(container.getResponse().getStatus().equals("OK")) {
        	Point point = container.getResponse().getResult().getPoint();
        	ResultVO resultVo01 = new ResultVO(container.getResponse().getStatus(),point.getX(),point.getY());
        	resultVo.add(resultVo01);
        }else {
        	ResultVO resultVo01 = new ResultVO(container.getResponse().getStatus(),"","");
        	resultVo.add(resultVo01);
        }

		return resultVo;

    }
  

}
