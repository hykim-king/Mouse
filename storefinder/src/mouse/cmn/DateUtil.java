/**
 * 파일명: DateUtil.java
 * 설명:
 * 작성자: user
 * 작성일: 2025-10-29
 * 버전 : 1.0
 */
package mouse.cmn;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 날짜 Util
 */
public class DateUtil {

	public static String getCurrentDate(String format) {
		LocalDate today = LocalDate.now();//현재 날짜
		
		//yyyy : 년도
		//MM : 월
		//dd : 일
		if(null==format || format.length()==0) {
			format = "yyyy/MM/dd";
		}

		return today.format(DateTimeFormatter.ofPattern(format));
	}
	
}
