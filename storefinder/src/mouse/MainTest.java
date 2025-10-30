/**
 * 
 */
package mouse;

import java.io.IOException;
import java.util.Scanner;

import mouse.cmn.DateUtil;


public class MainTest {
	StoreFinderView sfv;
	Scanner scanner = new Scanner(System.in);
	
	
	public MainTest() throws IOException {
		super();
		sfv = new StoreFinderView(scanner);
	}
	
	public static void getCurrentDate() {
		System.out.println("오늘날짜:"+DateUtil.getCurrentDate("yyyy/MM/dd"));
	}
	
	public static void main(String[] args) throws IOException {
		MainTest maintest = new MainTest();
		maintest.getCurrentDate();
	}

}
