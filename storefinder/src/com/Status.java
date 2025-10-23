/**
 * 파일명: Status.java
 * 설명:
 * 작성자: user
 * 작성일: 2025-10-22
 * 버전 : 1.0
 */
package com;


public class Status {
	private String status;

	public Status() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Status(String status) {
		super();
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Status [status=" + status + "]";
	}


}
