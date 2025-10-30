/**
 * 
 */
package mouse.storesearch;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import mouse.member.MemberDao;
import mouse.member.MemberVO;
public class StoreSearch {
//	static double Mylon=0;
//	static double Mylat=0;
	private static MemberDao dao;
	public static final String STORE_DATA = ".\\storefinder\\data\\starbucks.json"; //스토어 저장 경로
	public final static Logger log = LogManager.getLogger(StoreSearch.class);
	
	public static void StoreSearch(double mylon, double mylat, String storeType, int killo) {
		
		Gson gson = new Gson();
		Distance distance = new Distance();
		String readStoreTmp = "";//이전 검색 리스트
		dao = new MemberDao();
		
		// JSON 파일 읽기
		String jsonString;
		try {
			jsonString = new String(Files.readAllBytes(Paths.get(STORE_DATA)), StandardCharsets.UTF_8);
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
	        
	        //회원이 조회시 이력 저장
	        if(dao.MEMBER_LOGIN_ID != "") {
	        	String userFilePath = ".\\storefinder\\data\\"+dao.MEMBER_LOGIN_ID+"_history.txt";
	        	try(BufferedWriter bw = new BufferedWriter(new FileWriter(userFilePath));){
					bw.write(sb.toString());
				} catch (IOException e) {
					log.debug("IOException : "+e.getMessage());
					e.printStackTrace();
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
