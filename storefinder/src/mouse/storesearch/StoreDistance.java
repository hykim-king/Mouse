/**
 * 
 */
package mouse.storesearch;

//거리 포함한 새 클래스
public class StoreDistance {
	private Store store;
	private String type;
    private double distance;

    public StoreDistance(Store store, double distance, String type) {
        this.store = store;
        this.distance = distance;
        this.type = type;
    }

    public Store getStore() {
        return store;
    }

    public double getDistance() {
        return distance;
    }
    
    public String getType() {
        return type;
    }
}
