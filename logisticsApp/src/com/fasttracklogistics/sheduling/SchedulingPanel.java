package com.fasttracklogistics.sheduling;

import com.fasttracklogistics.data.DataManager;
import com.fasttracklogistics.model.Shipment;
import com.fasttracklogistics.notification.NotificationService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SchedulingPanel extends JPanel {
    private DataManager dataManager = DataManager.getInstance();
    private NotificationService notificationService = new NotificationService();
    private JTextField shipmentIdField, dateTimeField;
    private DefaultListModel<String> scheduleListModel;

    public SchedulingPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Shipment ID:"));
        shipmentIdField = new JTextField();
        formPanel.add(shipmentIdField);
        formPanel.add(new JLabel("Delivery DateTime (yyyy-MM-dd HH:mm):"));
        dateTimeField = new JTextField();
        formPanel.add(dateTimeField);

        JButton scheduleButton = new JButton("Schedule Delivery");
        formPanel.add(scheduleButton);

        add(formPanel, BorderLayout.NORTH);

        scheduleListModel = new DefaultListModel<>();
        JList<String> scheduleList = new JList<>(scheduleListModel);
        add(new JScrollPane(scheduleList), BorderLayout.CENTER);

        scheduleButton.addActionListener(e -> scheduleDelivery());
    }

    private void scheduleDelivery() {
        if (shipmentIdField.getText().isEmpty() || dateTimeField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        try {
            LocalDateTime deliveryTime = LocalDateTime.parse(dateTimeField.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            for (Shipment shipment : dataManager.getShipments()) {
                if (shipment.getId().equals(shipmentIdField.getText())) {
                    shipment.setEstimatedDelivery(deliveryTime);
                    scheduleListModel.addElement("Shipment " + shipmentIdField.getText() + " scheduled for " + deliveryTime);
                    notificationService.sendCustomerNotification(shipmentIdField.getText(), "Delivery scheduled for " + deliveryTime);
                    clearFields();
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Shipment ID not found.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Use yyyy-MM-dd HH:mm");
        }
    }

    private void clearFields() {
        shipmentIdField.setText("");
        dateTimeField.setText("");
    }
}
