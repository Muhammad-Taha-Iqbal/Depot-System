package model;

public class Parcel {
    private String parcelId;
    private double weight;
    private double length;
    private double width;
    private int noOfDays;
    private boolean processed;


    public Parcel(String parcelId, double weight, double length, double width, int noOfDays) {
        this.parcelId = parcelId;
        this.weight = weight;
        this.length = length;
        this.width = width;
        this.noOfDays = noOfDays;
        this.processed = false;
    }

    public String getParcelId() {
        return parcelId;
    }

    public double getWeight() {
        return weight;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String toString() {
        return "Parcel ID: " + parcelId + ", Weight: " + weight + " kg, Dimensions: " 
               + length + "x" + width + ", Days: " + noOfDays + ", Processed: " + processed;
    }
}
