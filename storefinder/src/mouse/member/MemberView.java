package mouse.member;


import java.util.Scanner;

import mouse.cmn.DateUtil;

public class MemberView {
	private MemberDao dao;
	private Scanner scanner;
	
	public MemberView(Scanner scanner) {
		super();
		this.scanner = scanner;
		dao = new MemberDao();
		
		//menu
		loop:while(true) {
			String menuStr = menu();
			System.out.println(menuStr);	
			
			System.out.print("메뉴를 입력 하세요.>");
			String menuInput = scanner.nextLine().trim();
			
			switch(menuInput) {
				case "2"://단건 조회
					doSelectOne();
					break;
				case "3"://단건 둥록
					doSave();
					break;
				case "4"://단건 수정
					doUpdate();
					break;					
					
				case "5"://단건 삭제
					doDelete();
					break;
					
				case "6"://종료
					System.out.println("┌───────────────────────┐");
					System.out.println("│종료                     │");
					System.out.println("└───────────────────────┘");		
					break loop;
			
			}
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
	 * 회원 저장
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
	 * 회원 삭제
	 */
	public void doDelete() {
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
	 * 회원 단건조회
	 */
	public void doSelectOne() {
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
//		System.out.println("inVO : "+inVO);
		MemberVO outVO = dao.doSelectOne(inVO);
		
		
		if(null == outVO) {
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[X] (%s)회원이 존재하지 않습니다. │\n",inVO.getMemberId());
			System.out.printf("└────────────────────────┘\n");
		}else {
			System.out.printf("┌────────────────────────┐\n");
			System.out.printf("│[성공] 회원 정보              │\n");
			System.out.printf("└────────────────────────┘\n");
			System.out.printf("->%s%n",outVO.voToTable());
		}
	}
	public String menu() {
		StringBuilder sb=new StringBuilder(300);
		sb.append("=== 회원관리 ===\n");
		sb.append("1. 회원 목록 조회 \n");
		sb.append("2. 회원 단건 조회 \n");
		sb.append("3. 회원 저장    \n");
		sb.append("4. 회원 수정    \n");
		sb.append("5. 회원 삭제    \n");
		sb.append("6. 종료  \n");
		
		return sb.toString();
	}

	

	
}

