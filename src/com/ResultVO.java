/**
 * 파일명: BoardVO.java
 * 설명:
 * 작성자: user
 * 작성일: 2025-10-22
 * 버전 : 1.0
 */
package com;

/**
 * 
 */
public class ResultVO {
	
	private	String  status;//제목	
	private	String  getX;	
	private	String  getY;
	public ResultVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ResultVO(String status, String getX, String getY) {
		super();
		this.status = status;
		this.getX = getX;
		this.getY = getY;
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
	/**
	 * @return the getX
	 */
	public String getGetX() {
		return getX;
	}
	/**
	 * @param getX the getX to set
	 */
	public void setGetX(String getX) {
		this.getX = getX;
	}
	/**
	 * @return the getY
	 */
	public String getGetY() {
		return getY;
	}
	/**
	 * @param getY the getY to set
	 */
	public void setGetY(String getY) {
		this.getY = getY;
	}
	@Override
	public String toString() {
		return "ResultVO [status=" + status + ", getX=" + getX + ", getY=" + getY + "]";
	}
	
	
	
}
