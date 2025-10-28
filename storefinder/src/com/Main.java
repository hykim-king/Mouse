package com;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import common.Menu;

public class Main {

    public static void main(String[] args) throws IOException{
//        String address = "서교동 354-8";
//        String address = "서울 마포구 양화로 122";

        Gson gson = new Gson();
        Distance distance = new Distance();
        Scanner scanner=new Scanner(System.in);
        
        String menuSelect = "";//menu
        String readStoreTmp = "";//이전 검색 리스트
        
        boolean loginSession = false; // 로그인 체크
        
        int nNum=0;
        
        //회원정보 Dao
        MembeDao membeDao = new MembeDao();
        
        //메뉴 설정
        Menu menu = new Menu();

        do {
        	StringBuilder menuTitle1 = menu.menuSet(loginSession, "1");
        	System.out.println(menuTitle1);
        	
			System.out.printf("메뉴를 선택해 주세요>");
			menuSelect = scanner.nextLine();
			
			if("1".equals(menuSelect)) {	//1. 근처 매장 찾기
				
				do {
					StringBuilder menuSubTitle1 = menu.menuSet(loginSession, "2");
		        	System.out.println(menuSubTitle1);
					
					System.out.printf("검색하실 메뉴를 선택해 주세요>");
					menuSelect = scanner.nextLine();
					
					System.out.println();
					
					// 검색 종류 설정
					String storeType = "";
					if("8".equals(menuSelect)) {	//8. 전체 검색
						storeType = "general";
					}else if("9".equals(menuSelect)) { //9. 드라이브 스루
						storeType = "generalDT";
					}else if("0".equals(menuSelect)) { //0. 메인메뉴로 이동
						break;
					}
					
					System.out.println();
					System.out.print("주소를 입력하세요>");
					String address = scanner.nextLine();

			        if(address=="") {
			        	System.out.println("=================================");
			        	System.out.println("=== 검색하신 주소는 없는 주소입니다. ====");
			        	System.out.println("=================================");
			        }else {
			        	System.out.println();
						System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
						System.out.println("▶ [예)5 Enter => 5Km 방경 검색]  ◀");
						System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
						
						System.out.print("반경 거리(Km)를 입력하세요 >");
						String killoText = scanner.nextLine();
						int killo = 5;
						if(killoText.equals("")) {
							System.out.println("입력내용이 없습니다. 5Km로 기본 설정 됩니다.");
						}else {
							killo = Integer.parseInt(killoText);
						}
						
				        MyPoint myPoint = new MyPoint();
				        List<ResultVO> resultPoint = myPoint.getCoordinatesFromAddress(address,"parcel");

				        if(resultPoint.get(0).getStatus().equals("NOT_FOUND")) {
			            	List<ResultVO> resultPoint1 = myPoint.getCoordinatesFromAddress(address,"ROAD");
			            	resultPoint = resultPoint1;
			            }
				        if(resultPoint.get(0).getStatus().equals("NOT_FOUND")) {
				        	System.out.println("┌───────────────────────┐");
				        	System.out.println("│  검색하신 주소는 없는 주소입니다  │");
				        	System.out.println("└───────────────────────┘");
				        }else {
					        double Mylon = Double.parseDouble(resultPoint.get(0).getGetX());
				            double Mylat = Double.parseDouble(resultPoint.get(0).getGetY());
				            
//				            StoreSearch storeSearch = new StoreSearch(Mylon, Mylat);
//				            System.out.println(storeSearch.toString());
					        // JSON 파일 읽기
					        String jsonString = new String(Files.readAllBytes(Paths.get("starbucks.json")), StandardCharsets.UTF_8);
					        Store[] stores = gson.fromJson(jsonString, Store[].class);
			
					        // 리스트로 변환해서 거리정보 추가
					        List<StoreDistance> storeList = new ArrayList<>();
			
					        for (Store store : stores) {
					            double storeLat = Double.parseDouble(store.latitude);
					            double storeLon = Double.parseDouble(store.longitude);
					            double dist = distance.calculateDistance(storeLat, storeLon, Mylat, Mylon);
					            
					            
					            storeList.add(new StoreDistance(store, dist, store.type ));
					        }
			
					        // 거리 오름차순 정렬
					        storeList.sort(Comparator.comparingDouble(StoreDistance::getDistance));
					       
					        // 상위 10개만 예시 출력
					        int j=0;
					        StringBuilder sb = new StringBuilder();
					        System.out.println("===== 가까운 매장 TOP 30 Start =====");
					        for (int i = 0; i < Math.min(100, storeList.size()); i++) {
					        	StoreDistance sd = storeList.get(i);
					        	if(storeType=="generalDT") {
//					        		if(sd.getType() == "generalDT" && sd.getDistance() < killo ) {
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
					        System.out.println(j);
					        	
					        if(j < 1) {
						        System.out.println("#### 검색하신 매장이 근처에 없습니다. ####");
					        }else {
					        	readStoreTmp = sb.toString();
					        	System.out.println(sb.toString());
					        }
					        System.out.println("==== 가까운 매장 TOP 30 End ====");
					        
				        }
			        }
				}while(true);
				
				
				
				
			}else if("4".equals(menuSelect)) {	//4. 이전 내역 조회
				if(readStoreTmp != "") {
					System.out.println("==== 최근 검색하신 가까운 매장 TOP 30 Start ====");
					System.out.println(readStoreTmp);
					System.out.println("==== 최근 검색하신 가까운 매장 TOP 30 End ====");
				}else {
					System.out.println("=================================");
		        	System.out.println("=== 이전 내역이 없습니다. ====");
		        	System.out.println("=================================");
				}
			
			}else if("2".equals(menuSelect)) {	//2. 로그인
				if(loginSession != true) {
					System.out.print("아이디를 입력하세요>");
					String memId = scanner.nextLine();
					System.out.print("비밀번호를 입력하세요>");
					String memPw = scanner.nextLine();
					
					boolean memberCheck = MembeDao.memberCheck(memId, memPw);
					
					if(memberCheck == true) {
						System.out.println("["+membeDao.getMemberName()+"]님 로그인 하셨습니다.");
						loginSession = true;
					}else {
						System.out.println("아이디 / 비밀번호가 일치하지 않습니다.");
					}
				}else {
					System.out.println("잘못된 접근입니다.");
				}
				
		
			}else if("3".equals(menuSelect)) {	//3. 회원가입
				if(loginSession != true) {
					System.out.print("회원 가입하실 아이디를 입력하세요>");
					String memId = scanner.nextLine();
					
					boolean memberIdCheck = true;
					
					do {
						System.out.println(memId);
						memberIdCheck = membeDao.memIdCheck(memId);
						System.out.println("이미 있는 아이디 입니다.");
						System.out.print("회원 가입하실 아이디를 입력하세요>");
						String memId2 = scanner.nextLine();
						memId = memId2;
					}while(memberIdCheck == false);
				
					System.out.print("회원 가입하실 비밀번호를 입력하세요>");
					String memPw = scanner.nextLine();
					System.out.print("회원 가입하실 비밀번호를 확인 입력하세요>");
					String memRePw = scanner.nextLine();
					
					if(memPw == memRePw) {
						System.out.println("비밀번호가 일치하지 않습니다.");
					}
					
					System.out.print("회원 가입하실 이름을 입력하세요>");
					String memNm = scanner.nextLine();
				} else {
					System.out.println("잘못된 접근입니다.");
				}
		
			}else if("5".equals(menuSelect)) {	//5. 회원정보 수정
				if(loginSession == true) {
					
				}else {
					System.out.println("잘못된 접근입니다.");
				}
		
			}else if("6".equals(menuSelect)) {	//. 회원탈퇴
				if(loginSession == true) {
					
				}else {
					System.out.println("잘못된 접근입니다.");
				}
		
			}else if("7".equals(menuSelect)) {	//7. 로그아웃
				if(loginSession == true) {
					
				}else {
					System.out.println("잘못된 접근입니다.");
				}
				loginSession = false;
		
			}else if("q".equals(menuSelect)) {	//q. 종료
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


