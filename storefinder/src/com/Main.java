package com;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.MyPoint.Point;
import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) throws IOException {

        Gson gson = new Gson();
        Distance distance = new Distance();
        Scanner scanner=new Scanner(System.in);
        
        String menuSelect = "";//menu
		
        int nNum=0;
        				
        				
        ArrayList<String> menuTitle=new ArrayList<>(List.of("1. 가까운 매장찾기","2. 이전 내역보기","3. 종료하기"));
        
        do {
			
			System.out.println("──────────────────────────────────────────────────────");
			for(String mt : menuTitle) {
				System.out.println(mt.intern());
//				System.out.println(mt.toString());
			}
			System.out.println("──────────────────────────────────────────────────────");
			
			System.out.printf("메뉴를 선택해 주세요>");
			menuSelect = scanner.nextLine();
			
			
			
			
			if("1".equals(menuSelect)) {	//1. 근처 매장 찾기
				System.out.println();
				System.out.print("주소를 입력하세요>");
				String address = scanner.nextLine();
				
		        String apikey = "DBBDD515-A8B0-3BFF-BBAD-4C0F71172A74"; 
//		        String address = "서교동 354-8";
//		        String address = "서울 마포구 양화로 122";
		        
		        MyPoint myPoint = new MyPoint();
		        Point resultPoint = myPoint.getCoordinatesFromAddress(apikey, address);
		        
		        
		        double Mylon = resultPoint.getX();
		        double Mylat = resultPoint.getY();

		        // JSON 파일 읽기
		        String jsonString = new String(Files.readAllBytes(Paths.get("starbucks.json")), StandardCharsets.UTF_8);
		        Store[] stores = gson.fromJson(jsonString, Store[].class);

		        // 리스트로 변환해서 거리정보 추가
		        List<StoreDistance> storeList = new ArrayList<>();

		        for (Store store : stores) {
		            double storeLat = Double.parseDouble(store.latitude);
		            double storeLon = Double.parseDouble(store.longitude);
		            double dist = distance.calculateDistance(storeLat, storeLon, Mylat, Mylon);
		            
		            storeList.add(new StoreDistance(store, dist));
		        }
		        
		        

		        // 거리 오름차순 정렬
		        storeList.sort(Comparator.comparingDouble(StoreDistance::getDistance));

		        // 상위 10개만 예시 출력
		        System.out.println("==== 가까운 매장 TOP 10 ====");
		        for (int i = 0; i < Math.min(100, storeList.size()); i++) {
		        	StoreDistance sd = storeList.get(i);
		        	if(sd.getDistance() < 5) {
		        		System.out.printf("%2d : %s (%.2f km)\n", i + 1, sd.getStore().name, sd.getDistance());
		        	}
		        }
				
			}else if("2".equals(menuSelect)) {	//2. 이전 내역 조회
		
				System.out.println("이전 내역이 없습니다.");
				System.out.println("--------------------------------------");;
				
			}else if("3".equals(menuSelect)) {	//3. 종료
				String lastMogi="""
						⊂_ヽ
						　 ＼＼ Λ＿Λ
						　　 ＼( ‘ㅅ' ) 두둠칫
						　　　 >　⌒ヽ
						　　　/ 　 へ＼
						　　 /　　/　＼＼
						　　 ﾚ　ノ　　 ヽ_つ
						　　/　/두둠칫
						　 /　/|
						　(　(ヽ
						　|　|、＼
						　| 丿 ＼ ⌒)
						　| |　　) /
						`ノ )　　Lﾉ 
						""";
				System.out.println("종료 되었습니다.\n이용해 주셔서 감사합니다.");
				System.out.println(lastMogi);
				break;
			}
			
			
		}while(true);
		
		System.out.println("프로그램 종료!");
			
			
		



       
        
        

    }
}

// 거리 포함한 새 클래스
class StoreDistance {
    private Store store;
    private double distance;

    public StoreDistance(Store store, double distance) {
        this.store = store;
        this.distance = distance;
    }

    public Store getStore() {
        return store;
    }

    public double getDistance() {
        return distance;
    }
}
