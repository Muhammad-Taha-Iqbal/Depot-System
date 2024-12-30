package view;

import Controller.DataLoader;
import model.Customer;
import model.Parcel;
import model.Log;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ParcelProcessingGUI extends JFrame {

    private JTextArea parcelArea;
    private JTextArea customerArea;
    private JTextArea logArea;
    private JTextField searchField;
    private JButton searchButton;
    private DataLoader dataLoader;
    private JButton refreshButton;
    private JButton clearButton;
    private JButton calculateFeeButton;

    public ParcelProcessingGUI(DataLoader dataLoader) {
        this.dataLoader = dataLoader;

        setTitle("Parcel Processing System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.CYAN);
        JLabel title = new JLabel("Parcel Processing System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(title);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

     
        parcelArea = new JTextArea(10, 40);
        customerArea = new JTextArea(10, 40);
        logArea = new JTextArea(5, 40);

        parcelArea.setBorder(BorderFactory.createTitledBorder("Parcel Details"));
        customerArea.setBorder(BorderFactory.createTitledBorder("Customer Details"));
        logArea.setBorder(BorderFactory.createTitledBorder("Log Messages"));

        parcelArea.setEditable(false);
        customerArea.setEditable(false);
        logArea.setEditable(false);

        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(3, 1, 10, 10));
        dataPanel.add(new JScrollPane(parcelArea));
        dataPanel.add(new JScrollPane(customerArea));
        dataPanel.add(new JScrollPane(logArea));

        mainPanel.add(dataPanel, BorderLayout.CENTER);

     
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        refreshButton = new JButton("Refresh Data");
        clearButton = new JButton("Clear Data");
        calculateFeeButton = new JButton("Calculate Fees");

        refreshButton.addActionListener(e -> refreshData());
        clearButton.addActionListener(e -> clearData());
        calculateFeeButton.addActionListener(e -> calculateFees());

        buttonPanel.add(refreshButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(calculateFeeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

    
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JLabel searchLabel = new JLabel("Search Parcel ID: ");
        searchField = new JTextField(15);
        searchButton = new JButton("Search and Process");
        searchButton.addActionListener(e -> searchParcel());

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

      
        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

     
        refreshData();
    }

    
    private void refreshData() {
        StringBuilder parcelData = new StringBuilder("Parcel Details:\n");
        Map<String, Parcel> parcelMap = dataLoader.getParcelMap();
        if (parcelMap != null && !parcelMap.isEmpty()) {
            for (Parcel parcel : parcelMap.values()) {
                parcelData.append(parcel).append("\n");
            }
        } else {
            parcelData.append("No parcel data available.\n");
        }
        parcelArea.setText(parcelData.toString());

        StringBuilder customerData = new StringBuilder("Customer Details:\n");
        if (dataLoader.getCustomerList() != null && !dataLoader.getCustomerList().isEmpty()) {
            for (Customer customer : dataLoader.getCustomerList()) {
                customerData.append(customer).append("\n");
            }
        } else {
            customerData.append("No customer data available.\n");
        }
        customerArea.setText(customerData.toString());

        logArea.append("Data refreshed.\n");
        Log.getInstance().logEvent("Data refreshed.");
    }

    private void clearData() {
        parcelArea.setText("");
        customerArea.setText("");
        logArea.setText("");
        logArea.append("All data cleared.\n");
        Log.getInstance().logEvent("All data cleared.");
    }

    private void calculateFees() {
        StringBuilder feeData = new StringBuilder("Parcel Fees:\n");
        Map<String, Parcel> parcelMap = dataLoader.getParcelMap();
        if (parcelMap != null && !parcelMap.isEmpty()) {
            for (Parcel parcel : parcelMap.values()) {
                double fee = calculateFee(parcel);
                feeData.append("Parcel ID: ").append(parcel.getParcelId())
                       .append(", Fee: ").append(fee).append("\n");
            }
        } else {
            feeData.append("No parcel data available for fee calculation.\n");
        }
        parcelArea.setText(feeData.toString());
        logArea.append("Fees calculated and displayed.\n");
        Log.getInstance().logEvent("Fees calculated and displayed.");

        try {
            Log.getInstance().writeToFile("C:\\Users\\ND.COM\\Desktop\\logs.txt");
        } catch (IOException e) {
            logArea.append("Error writing logs to file: " + e.getMessage() + "\n");
        }
    }

    private void searchParcel() {
        String parcelId = searchField.getText().trim();
        if (parcelId.isEmpty()) {
            logArea.append("Please enter a Parcel ID to search.\n");
            return;
        }

        Parcel parcel = dataLoader.getParcelMap().get(parcelId);
        if (parcel != null) {
            double fee = calculateFee(parcel);
            String parcelDetails = "Parcel Found:\n" + parcel + "\nFee: " + fee;
            parcelArea.setText(parcelDetails);

            String logMessage = "Parcel ID " + parcelId + " processed with Fee: " + fee;
            logArea.append(logMessage + "\n");
            Log.getInstance().logEvent(logMessage);

            parcel.setProcessed(true);
            dataLoader.getParcelMap().remove(parcelId);


            refreshData();


            try {
                Log.getInstance().writeToFile("C:\\Users\\ND.COM\\Desktop\\logs.txt");
            } catch (IOException e) {
                logArea.append("Error writing logs to file: " + e.getMessage() + "\n");
            }
        } else {
            logArea.append("Parcel ID " + parcelId + " The parcel has already been collected.\n");
        }
    }

    private double calculateFee(Parcel parcel) {
        double baseRate = 5.0;
        double weightRate = 2.0;
        double sizeRate = 1.0;
        double weightFee = parcel.getWeight() * weightRate;
        double sizeFee = (parcel.getLength() * parcel.getWidth()) * sizeRate;
        return baseRate + weightFee + sizeFee;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataLoader dataLoader = new DataLoader();
            try {
                dataLoader.loadParcelsFromCSV(new File("Parcels.csv"));
                dataLoader.loadCustomersFromCSV(new File("Custs.csv"));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading CSV files: " + e.getMessage());
            }
            new ParcelProcessingGUI(dataLoader);
        });
    }
}
