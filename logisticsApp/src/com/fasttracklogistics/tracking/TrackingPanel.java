package com.fasttracklogistics.tracking;

import com.fasttracklogistics.data.DataManager;
import com.fasttracklogistics.model.Shipment;
import com.fasttracklogistics.notification.NotificationService;

import javax.swing.*;
import java.awt.*;

public class TrackingPanel extends JPanel {
    private DataManager dataManager = DataManager.getInstance();
    private NotificationService notificationService = new NotificationService();
    private JTextField shipmentIdField, locationField, statusField;
    private DefaultListModel<String> trackingListModel;

    public TrackingPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Shipment ID:"));
        shipmentIdField = new JTextField();
        formPanel.add(shipmentIdField);
        formPanel.add(new JLabel("Current Location:"));
        locationField = new JTextField();
        formPanel.add(locationField);
        formPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        formPanel.add(statusField);

        JButton updateButton = new JButton("Update Tracking");
        formPanel.add(updateButton);

        add(formPanel, BorderLayout.NORTH);

        trackingListModel = new DefaultListModel<>();
        JList<String> trackingList = new JList<>(trackingListModel);
        add(new JScrollPane(trackingList), BorderLayout.CENTER);

        updateButton.addActionListener(e -> updateTracking());
    }

    private void updateTracking() {
        if (shipmentIdField.getText().isEmpty() || locationField.getText().isEmpty() || statusField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        for (Shipment shipment : dataManager.getShipments()) {
            if (shipment.getId().equals(shipmentIdField.getText())) {
                shipment.setLocation(locationField.getText());
                shipment.setStatus(statusField.getText());
                trackingListModel.addElement("Shipment " + shipmentIdField.getText() + ": " + locationField.getText() + " - " + statusField.getText());
                notificationService.sendCustomerNotification(shipmentIdField.getText(), "Status updated: " + statusField.getText() + " at " + locationField.getText());
                clearFields();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Shipment ID not found.");
    }

    private void clearFields() {
        shipmentIdField.setText("");
        locationField.setText("");
        statusField.setText("");
    }
}
