/**
 * 
 */
package com;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class MembeDao {
	private static String memberId;
	private static String memberName;
	

	public MembeDao() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the memberName
	 */
	public static String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public static void setMemberName(String memberName) {
		MembeDao.memberName = memberName;
	}

	//회원 아이디 중복체크
	public static Boolean memIdCheck(String memberId) {
		boolean memIdCheck = false;
		
		try {
			String jsonMemberList = new String(Files.readAllBytes(Paths.get("memberList.json")), StandardCharsets.UTF_8);
		
			Gson gson = new Gson();
			// JSON 파일 읽기
			MemberVO[] memberList = gson.fromJson(jsonMemberList, MemberVO[].class);
			
			for(MemberVO getMemberList : memberList) {
				System.out.println(getMemberList.getMemberId());
				if(memberId.equals(getMemberList.getMemberId())) {
					memIdCheck = true;
					System.out.println(memIdCheck);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memIdCheck;
	}
	
	//로그인 회원 체크
	
	public static Boolean memberCheck(String memberId, String memberPw) {
			
	    boolean memCheck = false;
	        
		try {
			String jsonMemberList = new String(Files.readAllBytes(Paths.get("memberList.json")), StandardCharsets.UTF_8);
			
			Gson gson = new Gson();
			// JSON 파일 읽기
			MemberVO[] memberList = gson.fromJson(jsonMemberList, MemberVO[].class);
				
			for(MemberVO getMemberList : memberList) {
				if(memberId.equals(getMemberList.getMemberId())&& memberPw.equals(getMemberList.getMemberPw())) {
					memCheck = true;
					
					memberName = getMemberList.getMemberName();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return memCheck;
	}	
}
