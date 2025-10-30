/**
 * 
 */
package mouse.member;

import java.util.Objects;

import mouse.cmn.DTO;

/**
 * 회원정보 Value Object 
 */
public class MemberVO extends DTO {
	private String memberId;	// 회원  ID
	private String name;		// 이름
	private String password;	// 비밀번호
	private String email;		// 이메일
	private String regDt;		// 가입일
	private String role;		// 권한
	
	//생성자
	public MemberVO() {
		super();
	}
	
	//인자있는 생성자
	public MemberVO(String memberId, String name, String password, String email, String regDt, String role) {
		super();
		this.memberId = memberId;
		this.name = name;
		this.password = password;
		this.email = email;
		this.regDt = regDt;
		this.role = role;
	}
	
	//vo to csv
	/**
	 * Value Object를 CSV 포맷으로 변환
	 * @return CSV로 변환된 문자열 
	 */
	public String voToCsv() {
		return String.format("%s,%s,%s,%s,%s,%s%n", memberId
										   , name
										   , password
										   , email
										   , regDt
										   , role
										   );
		
	}
	
	public String voToCsvLog() {
		return String.format("%s,%s,%s,%s,%s,%s", memberId
										   , name
										   , password
										   , email
										   , regDt
										   , role
										   );
		
	}
	
	public String voToTable() {
		return String.format("%-10s,%-7s,%-10s,%-20s,%10s,%-10s%n", memberId
										   , name
										   , password
										   , email
										   , regDt
										   , role
										   );
		
	}
	
	
	
	//setter / getter
	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(memberId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberVO other = (MemberVO) obj;
		return Objects.equals(memberId, other.memberId);
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the regDt
	 */
	public String getRegDt() {
		return regDt;
	}

	/**
	 * @param regDt the regDt to set
	 */
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	
	//toString : super.toString()
	public String toString() {
		return "MemberVO [memberId=" + memberId + ", name=" + name + ", password=" + password + ", email=" + email
				+ ", regDt=" + regDt + ", role=" + role + ", toString()=" + super.toString() + "]";
	}
	
	
	
	
}
