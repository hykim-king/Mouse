/**
 * 
 */
package com;

//거리 포함한 새 클래스
public class StoreDistance {
	private Store store;
    private double distance;

    public StoreDistance(Store store, double distance) {
        this.store = store;
        this.distance = distance;
    }

    public Store getStore() {
        return store;
    }

    public double getDistance() {
        return distance;
    }
}
