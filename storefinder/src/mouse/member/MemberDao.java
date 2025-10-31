/**
 * 파일명: MemberDao.java
 * 설명:
 * 작성자: user
 * 작성일: 2025-10-17
 * 버전 : 1.0
 */
package mouse.member;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mouse.cmn.SearchVO;
import mouse.cmn.WorkDiv;

/**
 * 회원관리
 */
public class MemberDao implements WorkDiv<MemberVO> {
	public final Logger log = LogManager.getLogger(getClass());
		
	public static final String MEMBER_DATA = ".\\storefinder\\data\\member.csv"; //회원정보 저장 경로
	
	static final List<MemberVO> members = new ArrayList<MemberVO>();
	
	public static String MEMBER_LOGIN_ID = ""; //회원 아이디
	public static boolean MEMBER_LOGIN_SESSION = false; //회원 로그인 섹션

	//생성자
	//등록
	//수정
	//삭제
	//단건조회
	//목록조회
	
	public MemberDao() {
		super();
		int count = getMemberReadFile(MEMBER_DATA);
		log.debug("회원수 : "+count);
	}


	/**
	 * members List<MemberVO> members를 파일에 기록
	 * @param filePath
	 * @return 기록된 회원수
	 */
	public int writeFile(String filePath) {
		int count = 0;
		
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));){
			//Value Object To csv
			for(MemberVO vo : members) {
				count++;
				bw.write(vo.voToCsv());
			}
		} catch (IOException e) {
			log.debug("IOException : "+e.getMessage());
			e.printStackTrace();
		}
		
		log.debug("회원수:{}명",count);
		
		return count;
	}
	
	//회원ID 존재 여부
	public boolean isExistsMember(MemberVO param) {
		boolean flag = false;
		for (MemberVO vo:members) {
			if(vo.getMemberId().equals(param.getMemberId())) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public int getMemberReadFile(String MEMBER_DATA) {
		
		int flag = 0;
		String str = "";
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(MEMBER_DATA))) {
			String line = "";
			
			
			while((line = bufferedReader.readLine())!=null) {
//				log.debug(line);
				//split()는 쉼표를 기준으로 데이터 분리:
				String[] strArr = line.split(",");

				if(strArr.length == 6) {
					MemberVO vo=new MemberVO(strArr[0], strArr[1], strArr[2], strArr[3], strArr[4], strArr[5]);
<<<<<<< HEAD
//					log.debug(vo);
					members.add(vo);
=======
					log.debug(vo);
					//members.add(vo);
>>>>>>> b702c5399d2102d6fd3570f2867d291a05e63560
					flag++;
				}
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
		
	}
	
	/**
	 * 저장
	 * @param param
	 * @return 실패:0/성공:1
	 */
	@Override
	public int doSave(MemberVO param) {
		int flag = 0;
		
		//members에 저장
		MemberVO inVO = param;
		
		if(isExistsMember(inVO)==true) {
			log.debug("회원ID존재 flag:{}",flag);
			return 2;
		}

		flag = (members.add(inVO))==true ? 1 : 0;
		
		log.debug("flag:{}",flag);
		
		return flag;
	}
	
	/**
	 * 수정
	 * @param param
	 * @return 실패:0/성공:1
	 */
	@Override
	public int doUpdate(MemberVO param) {
		int flag = 0;
		
		MemberVO inVO = param;
		//MemberId존재 확인
		//1.삭제
		//2.등록
		if( isExistsMember(inVO)==true) {
			int isDelete = doDelete(inVO);
			if(1==isDelete) {
				flag = doSave(inVO);
			}
		}else {
			//수정실패
			flag = 2;
		}
		
		
		return flag;
	}
	
	/**
	 * 삭제
	 * @param param
	 * @return 실패:0/성공:1
	 */
	@Override
	public int doDelete(MemberVO param) {
		int flag = 0;
		MemberVO inVO = param;
		
		
		for (MemberVO vo:members) {
			if(vo.getMemberId().equals(inVO.getMemberId())) {
				flag = members.remove(inVO)==true?1:0;
				log.debug("flag:"+flag);
				break;
			}else {
				flag = 2;
			}
		}
		return flag;
	}
	
	/**
	 * 단건조회
	 * @param param
	 * @return 실패:null/성공: 
	 */
	@Override
	public MemberVO doSelectOne(MemberVO param) {
		MemberVO outVO = null;
		MemberVO inVO = param;
		for (MemberVO vo:members) {
			if(vo.equals(inVO)) {
				outVO = vo;
				break;
			}
		}
		return outVO;
	}
	
	/**
	 * 로그인
	 * @param param
	 * @return 실패:null/성공: 
	 */
	@Override
	public MemberVO doLogin(MemberVO param) {
		MemberVO outVO = null;
		MemberVO inVO = param;
		for (MemberVO vo:members) {
			if(vo.getMemberId().equals(inVO.getMemberId()) && vo.getPassword().equals(inVO.getPassword())) {
				outVO = vo;
				break;
			}
		}
		return outVO;
	}
	
	/**
	 * 목록조회
	 * @param param
	 * @return
	 */
	@Override
	public List<MemberVO> doRetrieve(SearchVO search) {
		
		//"" || "ALL" : 전체
		//10: 회원 ID
		//20: name
		//30: emaiil
		
		List<MemberVO> outList = new ArrayList<>();
		
		log.debug("param:"+search);
		
		String searchDiv = search.getSearchDiv();
		
		if(searchDiv.equals("")|| searchDiv.equalsIgnoreCase("ALL")) {
			return this.members;
		//10 : 회원ID
		}else if(searchDiv.equals("10")) {
			for(MemberVO vo : members) {
				
				if(vo.getMemberId().contains(search.getSearchWord())) {
					outList.add(vo);
				}
			}
			
		//20 : name
		}else if(searchDiv.equals("20")) {
			for(MemberVO vo : members) {
				if(vo.getName().contains(search.getSearchWord())) {
					System.out.println(vo);
					outList.add(vo);
				}
			}
		//30 : email
		}else if(searchDiv.equals("30")) {
			for(MemberVO vo : members) {
				if(vo.getEmail().contains(search.getSearchWord())) {
					outList.add(vo);
				}
			}
		}
		return outList;
	}	
	
}
