package model;

import java.util.HashMap;

public class ParcelMap {
    private HashMap<String, Parcel> parcelMap;

    public ParcelMap() {
        parcelMap = new HashMap<>();
    }

    public void addParcel(Parcel parcel) {
        parcelMap.put(parcel.getParcelId(), parcel);
    }

    public Parcel getParcel(String parcelId) {
        return parcelMap.get(parcelId);
    }

    public HashMap<String, Parcel> getParcelMap() {
        return parcelMap;
    }
}
