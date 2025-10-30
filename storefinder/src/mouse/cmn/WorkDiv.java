package mouse.cmn;

import java.util.List;

import mouse.member.MemberVO;

public interface WorkDiv<E> {
	
	//생성자
	//등록
	//수정
	//삭제
	//단건조회
	//목록조회
	
	/**
	 * 저장
	 * @param param
	 * @return 실패:0/성공:1
	 */
	int doSave(E param);

	/**
	 * 수정
	 * @param param
	 * @return 실패:0/성공:1
	 */
	int doUpdate(E param);

	/**
	 * 삭제
	 * @param param
	 * @return 실패:0/성공:1
	 */
	int doDelete(E param);

	/**
	 * 단건조회
	 * @param param
	 * @return 실패:null/성공: 
	 */
	E doSelectOne(E param);

	/**
	 * 목록조회
	 * @param param
	 * @return
	 */
	List<E> doRetrieve(SearchVO param);

	/**
	 * 로그인
	 * @param param
	 * @return 실패:null/성공: 
	 */
	E doLogin(E param);

}