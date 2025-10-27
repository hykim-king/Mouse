/**
 * 
 */
package com;


public class Point {
	// !!! 수정: x, y 필드를 String에서 double로 변경하여 바로 실수 값으로 받습니다.
    private String x; // 경도 (Longitude)
    private String y; // 위도 (Latitude)

    public String getX() { return x; }
    public String getY() { return y; }
    
    // 디버깅 용도로 toString()을 오버라이드합니다.
    // 정밀도 유지를 위해 String.format을 사용합니다.
    @Override
    public String toString() {
        // 소수점 아래 14자리까지 출력 포맷을 지정하여 정밀도를 유지합니다.
        return String.format("경도(X): %s, 위도(Y): %s", x, y);
    }
}
