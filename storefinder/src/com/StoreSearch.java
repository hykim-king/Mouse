/**
 * 
 */
package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class StoreSearch {
//	static double Mylon=0;
//	static double Mylat=0;
	
	
	public static void main(String[] args) {
		//서울 마포구 양화로 122
		//Latitude : 37.5540720468966
		//Longitude : 126.920603903509
		// 김포시청 위도(Latitude) : 37.6153488 / 경도(Longitude) : 126.715451
		double mylon = 126.715451;
		double mylat = 37.6153488;
		String storeType = "generalDT";
		StoreSearch(mylon, mylat, storeType, 1000);
	}
	
	public static void StoreSearch(double mylon, double mylat, String storeType, int killo) {
		
		Gson gson = new Gson();
		Distance distance = new Distance();
		String readStoreTmp = "";//이전 검색 리스트
		
		// JSON 파일 읽기
		String jsonString;
		try {
			jsonString = new String(Files.readAllBytes(Paths.get("./storefinder/data/starbucks.json")), StandardCharsets.UTF_8);
			Store[] stores = gson.fromJson(jsonString, Store[].class);
			List<StoreDistance> storeList = new ArrayList<>();
			
	        for (Store store : stores) {
	            double storeLat = Double.parseDouble(store.latitude);
	            double storeLon = Double.parseDouble(store.longitude);
	            
	            double dist = distance.calculateDistance(storeLat, storeLon, mylat, mylon);
	            if(dist < 1000 && store.type == "generalDT") {
	            	System.out.println(store.name);
	            }
	            
	            storeList.add(new StoreDistance(store, dist, store.type ));
	        }

	        // 거리 오름차순 정렬
	        storeList.sort(Comparator.comparingDouble(StoreDistance::getDistance));
	        
	     // 상위 10개만 예시 출력
	        int j=0;
	        StringBuilder sb = new StringBuilder();
	        
	        for (int i = 0; i < storeList.size(); i++) {
	        	StoreDistance sd = storeList.get(i);
	        	if(storeType.equals("generalDT")) {
	        		if(sd.getType().equals(storeType) && sd.getDistance() < killo && j < 30) {
	        			
		        		sb.append((j + 1) + ". " +sd.getStore().name + " " + String.format("%.2f", sd.getDistance())+" km \n");
		        		j++;
		        	}
	        	}else {
	        		if(sd.getDistance() < killo  && i < 30) {
		        		sb.append((i + 1) + ". " +sd.getStore().name + " " + String.format("%.2f", sd.getDistance())+" km \n");
		        		j++;
		        	}
	        	}
	        }
	        
	        System.out.printf("===== 가까운 매장 TOP %s Start =====%n",j);
	        if(j < 1) {
		        System.out.println("#### 검색하신 매장이 근처에 없습니다. ####");
	        }else {
	        	readStoreTmp = sb.toString();
	        	System.out.println(sb.toString());
	        }
	        System.out.printf("==== 가까운 매장 TOP %s End ====%n",j);
	        
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         


	}



	
}
