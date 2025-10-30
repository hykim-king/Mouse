package mouse.storesearch;

public class Distance {

    // 지구 반지름 (km 단위, 평균값)

    private static final double EARTH_RADIUS_KM = 6371; 
    
   
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        
        // 1. 도(Degrees)를 라디안(Radians)으로 변환
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        
        // 2. 하버사인 공식의 sin^2(Δφ/2) + cos(φ1) * cos(φ2) * sin^2(Δλ/2) 부분 계산
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        
        // 3. 중앙각 거리 c = 2 * atan2(sqrt(a), sqrt(1-a)) 계산
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        // 4. 거리 = R * c 계산
        double distance = EARTH_RADIUS_KM * c;
        
        return distance;
    }
}