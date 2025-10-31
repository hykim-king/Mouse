/**
 * 
 */
package mouse.cmn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import mouse.member.MemberDao;
import mouse.member.MemberVO;
import mouse.storesearch.MyPoint;
import mouse.storesearch.ResultVO;
import mouse.storesearch.StoreSearch;



public class StoreFinderView {
	private MemberDao dao;
	private Scanner scanner;
	
	public StoreFinderView(Scanner scanner) throws IOException {
		super();
		this.scanner = scanner;
		dao = new MemberDao();
		
		
		teamLogView(2);//로그 출력
		
		//메뉴 설정
        Menu menuStr = new Menu();
      //매장 찾기
        StoreSearch storeSearch = new StoreSearch();
        MyPoint myPoint = new MyPoint();
		//menu
		loop:while(true) {
//			
			StringBuilder menu = menuStr.menuSet(dao.MEMBER_LOGIN_SESSION, "1", dao.MEMBER_LOGIN_ID);
			System.out.println(menu);	
			
			System.out.print("메뉴를 입력 하세요.>");
			String menuInput = scanner.nextLine().trim();
			
			home:switch(menuInput) {
				case "1"://매장 찾기
					do {
						String storeType = "";
						StringBuilder menuSubTitle = menuStr.menuSet(dao.MEMBER_LOGIN_SESSION, "2", dao.MEMBER_LOGIN_ID);
			        	System.out.println(menuSubTitle);
						
						System.out.printf("검색하실 메뉴를 선택해 주세요>");
						menuInput = scanner.nextLine().trim();
						
						System.out.println();
						
						if(menuInput.isEmpty() || menuInput.isBlank()) {
							System.out.println("※[입력] 잘못된 입력 입니다. 다시 입력 하세요.>\n");
							continue;
						}
						
						if(menuInput.equals("8") ||menuInput.equals("9") || menuInput.equals("0") ) {
							// 검색 종류 설정
							
							subMenu:switch(menuInput) {
								case "8": // 전체검색
									storeType = "general";
									break;
								case "9": // 드라이브 스루
									storeType = "generalDT";
									break;
								case "0": // 홈츠로
									
									break home;
							}
						}else {
							System.out.println("잘못된 입력 입니다. 다시 입력해주세요.");
							continue;
						}
						
						
						System.out.println();
						System.out.print("주소를 입력하세요>");
						String address = scanner.nextLine().trim();

						
				        if(address=="") {
				        	System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				        	System.out.println("▶  잘못된 입력 입니다. 다시 입력해주세요.	 ◀");
				        	System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				        }else {
				        	System.out.println();
							System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
							System.out.println("▶ [예)5 Enter => 5Km 방경 검색]	◀");
							System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
							
							System.out.print("반경 거리(Km)를 입력하세요 >");
							String killoText = scanner.nextLine();
							int killo = 5;
							if(killoText.equals("")) {
								System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
								System.out.println("▶ 입력내용이 없습니다. 5Km로 기본 설정 됩니다.  ◀");
								System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
								System.out.println("");
							}else {
								killo = Integer.parseInt(killoText);
							}
							
					        
					        List<ResultVO> resultPoint = myPoint.getCoordinatesFromAddress(address,"parcel");

					        if(resultPoint.get(0).getStatus().equals("NOT_FOUND")) {
				            	List<ResultVO> resultPoint1 = myPoint.getCoordinatesFromAddress(address,"ROAD");
				            	resultPoint = resultPoint1;
				            }
					        if(resultPoint.get(0).getStatus().equals("NOT_FOUND")) {
					        	System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
					        	System.out.println("▶    검색하신 주소는 없는 주소입니다.    ◀");
					        	System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
					        }else {
						        double Mylon = Double.parseDouble(resultPoint.get(0).getGetX());
					            double Mylat = Double.parseDouble(resultPoint.get(0).getGetY());
					            storeSearch.StoreSearch(Mylon, Mylat, storeType, killo);
					        }
				        }
					}while(true);
					
				case"2": //로그인
					if(dao.MEMBER_LOGIN_SESSION == true) {
						doKeepOut();
					}else {
						doLogin();
					}
					break;
				case"3": //회원가입
					if(dao.MEMBER_LOGIN_SESSION == true) {
						doKeepOut();
					}else {
						doSave();
					}
				
					break;
				case"4": //이전 내역보기
					if(dao.MEMBER_LOGIN_SESSION == true) {
						doUserHistory();
					}else {
						doKeepOut();
					}
					
					break;
				case"5": //회원정보 수정
					if(dao.MEMBER_LOGIN_SESSION == true) {
						doUpdate();
					}else {
						doKeepOut();
					}
					break;					
				case"6": //회원탈퇴
					if(dao.MEMBER_LOGIN_SESSION == true) {
						doDelete();
					}else {
						doKeepOut();
					}
					break;
				case"7": //로그아웃
					if(dao.MEMBER_LOGIN_SESSION == true) {
						dao.MEMBER_LOGIN_SESSION = false;
					}else {
						doKeepOut();
					}
					break;
				case"q": //종료
					
					teamLogView(1);//로그 출력
					
					System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■");
					System.out.println("▶  종료 되었습니다.		◀");
					System.out.println("▶  이용해 주셔서 감사합니다.	◀");
					System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■");
//					System.out.println(lastMogi);
					break loop;
				default:
					doKeepOut();
					break;
			}
			
		}
	}
	
	public void doKeepOut() {
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■");
		System.out.println("▶	잘못된 접근입니다.	◀");
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■");
	}
	
	/**
	 * 2. 회원 로그인
	 */
	public void doLogin() {

		MemberVO inVO=new MemberVO();
		String memberId = "";
		//회원ID입력
		while(true) {
			
			System.out.printf("회원ID를 입력 하세요.>");
			memberId = scanner.nextLine().trim();
			
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

		MemberVO outVO = dao.doLogin(inVO);
		
		if(null == outVO) {
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			System.out.printf("▶ (%s) 회원이 존재하지 않습니다.  ◀\n",inVO.getMemberId());
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		}else {
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			System.out.printf("▶ (%s)님 로그인 하셨습니다.  ◀\n",inVO.getMemberId());
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			
			dao.MEMBER_LOGIN_SESSION=true; //섹션 로그인 상태 변경
			dao.MEMBER_LOGIN_ID = memberId; //회원 아이디 저장
		}
	}
	

	/**
	 * 3. 회원 가입
	 */
	public void doSave() {
		MemberVO inVO=new MemberVO(); //Dao전달 Param
		
		boolean flag2 = false;
		//회원ID입력
		
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
    	System.out.println("▶	숫자 [0]을 누르면 메인으로 이동합니다.	◀");
    	System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		while(true) {
			System.out.printf("회원ID를 입력 하세요.>");
			String memberId = scanner.nextLine().trim();
			
			if(memberId.equals("0")) {
				flag2 = true;
				break;
			}
			
			if(memberId.isEmpty() || memberId.isBlank()) {
				System.out.println("※[입력] 회원ID를 입력 하세요.>\n");
				continue;
			}
			
			if(memberId.length() < 4) {
				System.out.println("※[자리수 제한] 4자 이상 입력해주세요>\n");
				continue;
			}
			
			if(memberId.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {
				//한글이 포함된 문자열
				System.out.println("※[한글 제한] 한글은 포함될 수 없습니다.>\n");
				continue;
			}
				
			inVO.setMemberId(memberId);
			break;
		}
		
		// 이름
		while(true) {
			if(flag2 == true) {
				break;
			}
			System.out.printf("이름을 입력 하세요.>");
			String name = scanner.nextLine().trim();
			
			if(name.equals("0")) {
				flag2 = true;
				break;
			}
			
			if(name.isEmpty() || name.isBlank()) {
				System.out.println("※[입력] 이름을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setName(name);
			break;
		}
		// 비밀번호
		while(true) {
			if(flag2 == true) {
				break;
			}
			System.out.printf("비밀번호를 입력 하세요.>");
			String password = scanner.nextLine().trim();
			
			if(password.equals("0")) {
				flag2 = true;
				break;
			}
			
			if(password.isEmpty() || password.isBlank()) {
				System.out.println("※[입력] 비밀번호를 입력 하세요.>\n");
				continue;
			}
			
			inVO.setPassword(password);
			break;
		}
		
		// 이메일
		while(true) {
			if(flag2 == true) {
				break;
			}
			
			System.out.printf("이메일을 입력 하세요.>");
			String email = scanner.nextLine().trim();
			
			if(email.equals("0")) {
				flag2 = true;
				break;
			}
			
			if(email.isEmpty() || email.isBlank()) {
				System.out.println("※[입력] 이메일을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setEmail(email);
			break;
		}
		
		//가입일
		inVO.setRegDt(DateUtil.getCurrentDate("yyyy/MM/dd"));
		
		String role = "일반";
		inVO.setRole(role);
		
		if(flag2 == false) {
			//dao
			int flag = dao.doSave(inVO);
			
			if(1==flag) { //성공
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
				System.out.printf("▶ (%s)님 회원 가입 완료 하였습니다.            ◀\n",inVO.getMemberId());
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
				//파일 저장
				dao.writeFile(dao.MEMBER_DATA);
			}else if(2==flag) {//memberId존재
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
				System.out.printf("▶ (%s) 아이디가 이미 존재 합니다..           ◀\n",inVO.getMemberId());
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
				
			}else if(0==flag) {//실패
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
				System.out.printf("▶ 회원가입 하지 못했습니다. 관리자에게 문의해주세요        ◀\n");
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			}
		}
	}
	/**
	 * 4. 회원 이전 조회 내역
	 */
	public void doUserHistory() {
		String line = "";
		//회원이 조회시 이력 저장
        if(dao.MEMBER_LOGIN_ID != "") {
        	String userFilePath = ".\\storefinder\\data\\"+dao.MEMBER_LOGIN_ID+"_history.txt";
        	File file = new File(userFilePath);
    		if(file.exists()==true) {
				try (BufferedReader bufferedReader = new BufferedReader(new FileReader(userFilePath))) {
					line = "";
					
					while((line = bufferedReader.readLine())!=null) {
						System.out.println(line);
					}
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("■■■■■■■■■■■■■■■■■■■■");
				System.out.println("▶  이전 이력이 없습니다.  ◀");
				System.out.println("■■■■■■■■■■■■■■■■■■■■");
			}
        }
        
        
	}
	
	/**
	 * 5. 회원 수정
	 */
	public void doUpdate() {
		MemberVO inVO=new MemberVO(); //Dao전달 Param

		//회원ID 자동 입력
		inVO.setMemberId(dao.MEMBER_LOGIN_ID);
		boolean flag2 = false;
		
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
    	System.out.println("▶	숫자 [0]을 누르면 메인으로 이동합니다.	◀");
    	System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		
		// 이름
		while(true) {
			System.out.printf("이름을 입력 하세요.>");
			String name = scanner.nextLine().trim();
			
			if(name.equals("0")) {
				flag2 = true;
				break;
			}
			
			if(name.isEmpty() || name.isBlank()) {
				System.out.println("※[입력] 이름을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setName(name);
			break;
		}
		// 비밀번호
		while(true) {
			if(flag2 == true) {
				break;
			}
			System.out.printf("비밀번호를 입력 하세요.>");
			String password = scanner.nextLine().trim();
			if(password.equals("0")) {
				flag2 = true;
				break;
			}
			if(password.isEmpty() || password.isBlank()) {
				System.out.println("※[입력] 비밀번호를 입력 하세요.>\n");
				continue;
			}
			
			inVO.setPassword(password);
			break;
		}
		
		// 이메일
		while(true) {
			if(flag2 == true) {
				break;
			}
			System.out.printf("이메일을 입력 하세요.>");
			String email = scanner.nextLine().trim();
			if(email.equals("0")) {
				flag2 = true;
				break;
			}
			if(email.isEmpty() || email.isBlank()) {
				System.out.println("※[입력] 이메일을 입력 하세요.>\n");
				continue;
			}
			
			inVO.setEmail(email);
			break;
		}
		
		//가입일
		inVO.setRegDt(DateUtil.getCurrentDate("yyyy/MM/dd"));
		
		String role = "일반";
		inVO.setRole(role);
		
		//dao
		int flag = dao.doUpdate(inVO);
		
		if(1==flag) { //성공
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			System.out.printf("▶[수정완료] 수정을 완료했습니다.                            ◀\n");
			System.out.printf("▶ %s ◀\n",inVO.voToCsvLog());
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		
			//파일 저장
			dao.writeFile(dao.MEMBER_DATA);
		}else if(2==flag) {//memberId존재
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			System.out.printf("▶ (%s)회원이 존재 합니다.  ◀\n",inVO.getMemberId());
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		}else if(0==flag) {//실패
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			System.out.printf("▶ 수정하지 못했습니다. 관리자에게 문의해주세요            ◀\n");
			System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
		}
	}
	
	/**
	 * 6. 회원 삭제
	 */
	public void doDelete() {
		MemberVO inVO=new MemberVO(); //Dao전달 Param
		String memberId;
		//회원ID입력
		while(true) {
			System.out.printf("정말 회원탈퇴 하시겠습니까?\n탈퇴를 원하시면 로그인시 아이디를 입력하세요.>");
			memberId = scanner.nextLine().trim();
			
			if(memberId.isEmpty() || memberId.isBlank()) {
				System.out.println("※[입력] 회원ID를 입력 하세요.>\n");
				continue;
			}
		
			break;
		}
		
		if(dao.MEMBER_LOGIN_ID.equalsIgnoreCase(memberId)) {
			inVO.setMemberId(memberId);
			int flag = dao.doDelete(inVO);
			
			if(1==flag) {//삭제 성공
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
				System.out.printf("▶ (%s)님 회원 탈퇴 완료 하였습니다.            ◀\n",inVO.getMemberId());
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
				
				dao.MEMBER_LOGIN_ID = ""; //로그인 아이디 삭제
				dao.MEMBER_LOGIN_SESSION=false; //로그인 섹션 변경
				dao.writeFile(dao.MEMBER_DATA);//파일 저장
				
			}else if(2==flag) {//회원ID 없음
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
				System.out.printf("▶ (%s)님 아이디가 존재하지 않습니다.           ◀\n",inVO.getMemberId());
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			}else if(0==flag) {
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
				System.out.printf("▶ 탈퇴실패 못했습니다. 관리자에게 문의해주세요            ◀\n");
				System.out.printf("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■\n");
			}
		}else {
			System.out.println("※[입력] 회원아이디가 일치 하지 않습니다.>\n");
		}
		
		
	}
	
	public void teamLogView(int logNum) {
		String teamLog = "";
		if(logNum == 1) {
			teamLog = ".----------------.  .----------------.  .----------------.  .----------------.  .----------------.\r\n"
					+ "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\r\n"
					+ "| | ____    ____ | || |     ____     | || | _____  _____ | || |    _______   | || |  _________   | |\r\n"
					+ "| ||_   \\  /   _|| || |   .'    `.   | || ||_   _||_   _|| || |   /  ___  |  | || | |_   ___  |  | |\r\n"
					+ "| |  |   \\/   |  | || |  /  .--.  \\  | || |  | |    | |  | || |  |  (__ \\_|  | || |   | |_  \\_|  | |\r\n"
					+ "| |  | |\\  /| |  | || |  | |    | |  | || |  | '    ' |  | || |   '.___`-.   | || |   |  _|  _   | |\r\n"
					+ "| | _| |_\\/_| |_ | || |  \\  `--'  /  | || |   \\ `--' /   | || |  |`\\____) |  | || |  _| |___/ |  | |\r\n"
					+ "| ||_____||_____|| || |   `.____.'   | || |    `.__.'    | || |  |_______.'  | || | |_________|  | |\r\n"
					+ "| |              | || |              | || |              | || |              | || |              | |\r\n"
					+ "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\r\n"
					+ " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'";
		}else if(logNum == 2) {
			teamLog = " ad88888ba                                                 88888888888  88                        88\r\n"
					+ "d8\"     \"8b  ,d                                            88           \"\"                        88\r\n"
					+ "Y8,          88                                            88                                     88\r\n"
					+ "`Y8aaaaa,  MM88MMM  ,adPPYba,   8b,dPPYba,   ,adPPYba,     88aaaaa      88  8b,dPPYba,    ,adPPYb,88   ,adPPYba,  8b,dPPYba,\r\n"
					+ "  `\"\"\"\"\"8b,  88    a8\"     \"8a  88P'   \"Y8  a8P_____88     88\"\"\"\"\"      88  88P'   `\"8a  a8\"    `Y88  a8P_____88  88P'   \"Y8\r\n"
					+ "        `8b  88    8b       d8  88          8PP\"\"\"\"\"\"\"     88           88  88       88  8b       88  8PP\"\"\"\"\"\"\"  88\r\n"
					+ "Y8a     a8P  88,   \"8a,   ,a8\"  88          \"8b,   ,aa     88           88  88       88  \"8a,   ,d88  \"8b,   ,aa  88\r\n"
					+ " \"Y88888P\"   \"Y888  `\"YbbdP\"'   88           `\"Ybbd8\"'     88           88  88       88   `\"8bbdP\"Y8   `\"Ybbd8\"'  88";
		}
		
		System.out.println(teamLog);
		
	}
}