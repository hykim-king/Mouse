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
	double Mylon;
	double Mylat;
	
	
	public StoreSearch(double mylon, double mylat) {
		
		this.Mylat = mylon;
		this.Mylat = mylat;
		Gson gson = new Gson();
		
		// JSON 파일 읽기
        
         
//        String jsonString = new String(Files.readAllBytes(Paths.get("starbucks.json")), StandardCharsets.UTF_8);


        // 리스트로 변환해서 거리정보 추가
        List<StoreDistance> storeList = new ArrayList<>();

//        for (Store store : stores) {
//            double storeLat = Double.parseDouble(store.latitude);
//            double storeLon = Double.parseDouble(store.longitude);
//            double dist = distance.calculateDistance(storeLat, storeLon, Mylat, Mylon);
//            
//            storeList.add(new StoreDistance(store, dist));
//        }

        // 거리 오름차순 정렬
        storeList.sort(Comparator.comparingDouble(StoreDistance::getDistance));
	}


	/**
	 * @return the mylon
	 */
	public double getMylon() {
		return Mylon;
	}


	/**
	 * @param mylon the mylon to set
	 */
	public void setMylon(double mylon) {
		Mylon = mylon;
	}


	/**
	 * @return the mylat
	 */
	public double getMylat() {
		return Mylat;
	}


	/**
	 * @param mylat the mylat to set
	 */
	public void setMylat(double mylat) {
		Mylat = mylat;
	}
	
	
	

	
	
	
	
	
	
	
	
}
