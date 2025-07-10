package com.fasttracklogistics.notification.customer;

import com.fasttracklogistics.data.DataManager;
import com.fasttracklogistics.model.Shipment;
import com.fasttracklogistics.notification.NotificationService;

import javax.swing.*;
import java.awt.*;

public class CustomerNotificationPanel extends JPanel {
    private DataManager dataManager = DataManager.getInstance();
    private NotificationService notificationService = new NotificationService();
    private JTextField shipmentIdField, messageField;
    private DefaultListModel<String> notificationListModel;

    public CustomerNotificationPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Shipment ID:"));
        shipmentIdField = new JTextField();
        formPanel.add(shipmentIdField);
        formPanel.add(new JLabel("Message:"));
        messageField = new JTextField();
        formPanel.add(messageField);

        JButton sendButton = new JButton("Send Notification");
        formPanel.add(sendButton);

        add(formPanel, BorderLayout.NORTH);

        notificationListModel = new DefaultListModel<>();
        JList<String> notificationList = new JList<>(notificationListModel);
        add(new JScrollPane(notificationList), BorderLayout.CENTER);

        refreshNotifications();

        sendButton.addActionListener(e -> sendNotification());
    }

    private void sendNotification() {
        if (shipmentIdField.getText().isEmpty() || messageField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        for (Shipment shipment : dataManager.getShipments()) {
            if (shipment.getId().equals(shipmentIdField.getText())) {
                notificationService.sendCustomerNotification(shipmentIdField.getText(), messageField.getText());
                refreshNotifications();
                clearFields();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Shipment ID not found.");
    }

    private void refreshNotifications() {
        notificationListModel.clear();
        for (String notification : notificationService.getNotifications()) {
            if (notification.contains("Customer Notification")) {
                notificationListModel.addElement(notification);
            }
        }
    }

    private void clearFields() {
        shipmentIdField.setText("");
        messageField.setText("");
    }
}
