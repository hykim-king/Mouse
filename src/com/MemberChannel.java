/**
 * 
 */
package com;

import java.util.ArrayList;
import java.util.List;

public class MemberChannel {
	List<MemberVO> items=new ArrayList<>();

	public MemberChannel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberChannel(List<MemberVO> items) {
		super();
		this.items = items;
	}

	/**
	 * @return the items
	 */
	public List<MemberVO> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(List<MemberVO> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "MemberChannel [items=" + items + "]";
	}
	
	
}
