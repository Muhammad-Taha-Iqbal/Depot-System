package Controller;

import model.Customer;
import model.Parcel;

import java.io.*;
import java.util.*;

public class DataLoader {
    private Map<String, Parcel> parcelMap;
    private List<Customer> customerList;


    public DataLoader() {
        this.parcelMap = new HashMap<>();
        this.customerList = new ArrayList<>();
    }


    public Map<String, Parcel> getParcelMap() {
        return parcelMap;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }


    public void loadParcelsFromCSV(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Parcel parcel = new Parcel(data[0], Double.parseDouble(data[1]), Double.parseDouble(data[2]), 
                                            Double.parseDouble(data[3]), Integer.parseInt(data[4]));
                parcelMap.put(data[0], parcel);
            }
        }
    }


    public void loadCustomersFromCSV(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Customer customer = new Customer(data[0], data[1]);
                customerList.add(customer);
            }
        }
    }
}

