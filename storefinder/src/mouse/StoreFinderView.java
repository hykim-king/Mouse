/**
 * 
 */
package mouse;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import mouse.cmn.DateUtil;
import mouse.cmn.Menu;
import mouse.member.MemberDao;
import mouse.member.MemberVO;
import mouse.storesearch.MyPoint;
import mouse.storesearch.ResultVO;
import mouse.storesearch.StoreSearch;



public class StoreFinderView {
	private MemberDao dao;
	private Scanner scanner;
	public static boolean loginSession = false; // 로그인 체크
	public static String loginMemberId = ""; // 로그인 아이디
	
	public StoreFinderView(Scanner scanner) throws IOException {
		super();
		this.scanner = scanner;
		dao = new MemberDao();
		
		//메뉴 설정
        Menu menuStr = new Menu();
      //매장 찾기
        StoreSearch storeSearch = new StoreSearch();
        MyPoint myPoint = new MyPoint();
		//menu
		loop:while(true) {
//			System.out.println(loginSession);
			StringBuilder menu = menuStr.menuSet(loginSession, "1");
			System.out.println(menu);	
			
			System.out.print("메뉴를 입력 하세요.>");
			String menuInput = scanner.nextLine().trim();
			
			
			home:switch(menuInput) {
				case "1"://매장 찾기
					do {
						StringBuilder menuSubTitle = menuStr.menuSet(loginSession, "2");
			        	System.out.println(menuSubTitle);
						
						System.out.printf("검색하실 메뉴를 선택해 주세요>");
						menuInput = scanner.nextLine().trim();
						
						System.out.println();
						
						// 검색 종류 설정
						String storeType = "";
						subMenu:switch(menuInput) {
							case "8":
								storeType = "general";
								break;
							case "9":
								storeType = "generalDT";
								break;
							case "0":
								
								break home;
						}
						
						
						System.out.println();
						System.out.print("주소를 입력하세요>");
						String address = scanner.nextLine().trim();

						
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
					            storeSearch.StoreSearch(Mylon, Mylat, storeType, killo);
					        }
				        }
					}while(true);
					
				case"2": //로그인
					doLogin();
					break;
				case"3": //회원가입
					doSave();
					break;
				case"4": //이전 내역보기
					
					break;
				case"5": //회원정보 수정
					if(loginSession == true) {
						doUpdate();
					}else {
						System.out.println("잘못된 접근입니다.");
					}
					break;					
				case"6": //회원탈퇴
					if(loginSession == true) {
						doDelete();
					}else {
						System.out.println("잘못된 접근입니다.");
					}
					break;
				case"7": //로그아웃
					if(loginSession == true) {
						loginSession = false;
					}else {
						System.out.println("잘못된 접근입니다.");
					}
					break;
				case"q":
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
					break loop;
				}
			
//			switch(menuInput) {
//				case "2"://단건 조회
//					doSelectOne();
//					break;
//				case "3"://단건 둥록
//					doSave();
//					break;
//				case "4"://단건 수정
//					doUpdate();
//					break;					
//					
//				case "5"://단건 삭제
//					doDelete();
//					break;
//					
//				case "6"://종료
//					System.out.println("┌───────────────────────┐");
//					System.out.println("│종료                     │");
//					System.out.println("└───────────────────────┘");		
//					break loop;
//			
//			}
		}
	}
	/**
	 * 회원 수정
	 */
	public void doUpdate() {
		MemberVO inVO=new MemberVO(); //Dao전달 Param
		//회원ID입력
		while(true) {
			System.out.printf("회원ID를 입력 하세요.>");
			String memberId = scanner.nextLine().trim();
			
			if(memberId.isEmpty() || memberId.isBlank()) {
				System.out.println("※[입력] 회원ID를 입력 하세요.>\n");
				continue;
			}
			
			inVO.setMemberId(memberId);
			
			//회원존재 여부 확인 : 회원이 없으면
			if(dao.isExistsMember(inVO)==false) {
				System.out.println("※[입력] 회원ID를 확인 하세요.>\n");
				continue;
			}
			
			break;
		}
		
		// 이름
		while(true) {
			System.out.printf("이름을 입력 하세요.>");
			String name = scanner.nextLine().trim();
			
			if(name.isEmpty() || name.isBlank()) {
				System.out.println("※[입력] 이름을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setName(name);
			break;
		}
		// 비밀번호
		while(true) {
			System.out.printf("비밀번호를 입력 하세요.>");
			String password = scanner.nextLine().trim();
			
			if(password.isEmpty() || password.isBlank()) {
				System.out.println("※[입력] 비밀번호를 입력 하세요.>\n");
				continue;
			}
			
			inVO.setPassword(password);
			break;
		}
		
		// 이메일
		while(true) {
			System.out.printf("이메일을 입력 하세요.>");
			String email = scanner.nextLine().trim();
			
			if(email.isEmpty() || email.isBlank()) {
				System.out.println("※[입력] 이메일을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setEmail(email);
			break;
		}
		
		//가입일
		inVO.setRegDt(DateUtil.getCurrentDate("yyyy/MM/dd"));
		
		// 권한
		while(true) {
			System.out.printf("권한을 입력 하세요.(예:관리자/일반)>");
			String role = scanner.nextLine().trim();
			
			if(role.isEmpty() || role.isBlank()) {
				System.out.println("※[입력] 권한을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setRole(role);
			break;
		}
		
		//dao
		int flag = dao.doUpdate(inVO);
		
		if(1==flag) { //성공
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[수정:성공] 수정 회원:%s       │\n",inVO.voToCsv());
			System.out.printf("└────────────────────────┘\n");
			
			//파일 저장
			dao.writeFile(dao.MEMBER_DATA);
		}else if(2==flag) {//memberId존재
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[X] (%s)회원이 존재 합니다.    │\n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
		}else if(0==flag) {//실패
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[수정:실패] 회원 정보:%s       │\n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
		}
	}
	
	/**
	 * 회원 삭제
	 */
	public void doDelete() {
		MemberVO inVO=new MemberVO(); //Dao전달 Param
		
		
		
		int flag = dao.doDelete(inVO);
		
		if(1==flag) {//삭제 성공
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[삭제:성공] 삭제 회원:%s       │\n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
			
			//파일 저장
			dao.writeFile(dao.MEMBER_DATA);
			
		}else if(2==flag) {//회원ID 없음
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[X] (%s)회원이 존재하지 않습니다  │\n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
		}else if(0==flag) {
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[삭제:실패] 회원 정보:%s       │\n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
		}
	}
	/**
	 * 회원 가입
	 */
	public void doSave() {
		MemberVO inVO=new MemberVO(); //Dao전달 Param
		//회원ID입력
		while(true) {
			System.out.printf("회원ID를 입력 하세요.>");
			String memberId = scanner.nextLine().trim();
			
			if(memberId.isEmpty() || memberId.isBlank()) {
				System.out.println("※[입력] 회원ID를 입력 하세요.>\n");
				continue;
			}
			
			inVO.setMemberId(memberId);
			break;
		}
		
		// 이름
		while(true) {
			System.out.printf("이름을 입력 하세요.>");
			String name = scanner.nextLine().trim();
			
			if(name.isEmpty() || name.isBlank()) {
				System.out.println("※[입력] 이름을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setName(name);
			break;
		}
		// 비밀번호
		while(true) {
			System.out.printf("비밀번호를 입력 하세요.>");
			String password = scanner.nextLine().trim();
			
			if(password.isEmpty() || password.isBlank()) {
				System.out.println("※[입력] 비밀번호를 입력 하세요.>\n");
				continue;
			}
			
			inVO.setPassword(password);
			break;
		}
		
		// 이메일
		while(true) {
			System.out.printf("이메일을 입력 하세요.>");
			String email = scanner.nextLine().trim();
			
			if(email.isEmpty() || email.isBlank()) {
				System.out.println("※[입력] 이메일을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setEmail(email);
			break;
		}
		
		//가입일
		inVO.setRegDt(DateUtil.getCurrentDate("yyyy/MM/dd"));
		
		// 권한
		while(true) {
			System.out.printf("권한을 입력 하세요.(예:관리자/일반)>");
			String role = scanner.nextLine().trim();
			
			if(role.isEmpty() || role.isBlank()) {
				System.out.println("※[입력] 권한을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setRole(role);
			break;
		}
		
		//dao
		int flag = dao.doSave(inVO);
		if(1==flag) { //성공
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[가입:성공] 등록 회원:%s       │\n",inVO.voToCsv());
			System.out.printf("└────────────────────────┘\n");
			
			//파일 저장
			dao.writeFile(dao.MEMBER_DATA);
		}else if(2==flag) {//memberId존재
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[X] (%s)회원이 존재   합니다.  │\n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
		}else if(0==flag) {//실패
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[등록:실패] 회원 정보:%s       │\n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
		}
		
		//pcwk03,이상무03,a4321,jamesol@paran.com,2025/10/25,일반

	}
	
	/**
	 * 회원 로그인
	 */
	public void doLogin() {

		MemberVO inVO=new MemberVO();
		
		//회원ID입력
		while(true) {
			
			System.out.printf("회원ID를 입력 하세요.>");
			String memberId = scanner.nextLine().trim();
			
			if(memberId.isEmpty() || memberId.isBlank()) {
				System.out.println("※[입력] 회원ID를 입력 하세요.>\n");
				continue;
			}
			inVO.setMemberId(memberId);
			break;
		}
		
		//회원PW입력
		while(true) {
			System.out.printf("회원 비밀번호를 입력 하세요.>");
			String password = scanner.nextLine().trim();
			
			if(password.isEmpty() || password.isBlank()) {
				System.out.println("※[입력] 회원 비밀번호를 입력 하세요.>\n");
				continue;
			}
			inVO.setPassword(password);
			break;
		}

		MemberVO outVO = dao.doSelectOne(inVO);
		
		
		if(null == outVO) {
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf(" [%s] 회원이 존재하지 않습니다.     \n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
			
		}else {
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf(" [%s]님 로그인 하셨습니다.       \n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
//			System.out.printf("->%s%n",outVO.voToTable());
			loginSession = true;
		}
	}
	
	
}
