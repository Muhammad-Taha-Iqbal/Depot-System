package Controller;

import model.Parcel;
import model.Log;

import java.util.ArrayList;
import java.util.List;

public class Staff {

    private String staffID;
    private String firstName;
    private String lastName;
    private List<Parcel> processedParcels;

    public Staff(String staffID, String firstName, String lastName) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.processedParcels = new ArrayList<>();
    }


    public String getStaffID() {
        return staffID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public List<Parcel> getProcessedParcels() {
        return processedParcels;
    }

    public void processParcel(Parcel parcel) {
        processedParcels.add(parcel);
        Log.getInstance().logEvent(getFullName() + " processed parcel ID: " + parcel.getParcelId());
        System.out.println(getFullName() + " processed parcel ID: " + parcel.getParcelId());
    }

    public int getProcessedParcelCount() {
        return processedParcels.size();
    }


    public String toString() {
        return "Staff{" +
                "staffID='" + staffID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", processedParcels=" + processedParcels.size() +
                '}';
    }
}

