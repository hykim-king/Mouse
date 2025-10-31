/**
 * 
 */
package mouse.cmn;

import java.util.ArrayList;

import mouse.member.MemberDao;

/**
 * 
 */
public class Menu {

	static final String MENU = ""; // 메뉴 
	String menuType;
	private MemberDao dao;

	public Menu() {
		super();
	}

	//메뉴설정
	public StringBuilder menuSet(boolean loginSession, String menuType, String memberId) {
		ArrayList<String> getMenuTitle=new ArrayList<>();
		if(menuType.equals("1")) {
			if(loginSession == false) {
				getMenuTitle.add("│  Store Finder	 	│");
	        	getMenuTitle.add("├───────────────────────┤");
				getMenuTitle.add("│  1. 가까운 매장찾기		│");
				getMenuTitle.add("│  2. 로그인		│");
				getMenuTitle.add("│  3. 회원가입		│");
				getMenuTitle.add("│  q. 종료하기		│");
				
//				getMenuTitle.add("order. 주문하기(점검중)");
	        }else{
	        	getMenuTitle.add("│  Store Finder	 	│");
	        	getMenuTitle.add("├───────────────────────┤");
	        	getMenuTitle.add("   ▶ "+memberId+"님" );
	        	getMenuTitle.add("├───────────────────────┤");
	        	getMenuTitle.add("│  1. 가까운 매장찾기		│");
	        	getMenuTitle.add("│  4. 이전 내역보기		│");
	        	getMenuTitle.add("│  5. 회원정보 수정		│");
	        	getMenuTitle.add("│  6. 회웥탈퇴		│");
	        	getMenuTitle.add("│  7. 로그아웃		│");
		       	getMenuTitle.add("│  q. 종료하기		│");
	        }
		}else if (menuType.equals("2")){
				getMenuTitle.add("│  8. 전체 매장 검색		│");
				getMenuTitle.add("│  9. 드라이브스루 매장 검색	│");
				getMenuTitle.add("│  0. 메인화면으로 이동	│");
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("┌───────────────────────┐\n");
		for(String list : getMenuTitle) {
			sb.append(list+"\n");
		}
		sb.append("└───────────────────────┘\n");
			
		return sb;
	}
}

