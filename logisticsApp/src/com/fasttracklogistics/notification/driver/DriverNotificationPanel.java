package com.fasttracklogistics.notification.driver;

import com.fasttracklogistics.data.DataManager;
import com.fasttracklogistics.model.Driver;
import com.fasttracklogistics.notification.NotificationService;

import javax.swing.*;
import java.awt.*;

public class DriverNotificationPanel extends JPanel {
    private DataManager dataManager = DataManager.getInstance();
    private NotificationService notificationService = new NotificationService();
    private JTextField driverIdField, messageField;
    private DefaultListModel<String> notificationListModel;

    public DriverNotificationPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Driver ID:"));
        driverIdField = new JTextField();
        formPanel.add(driverIdField);
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
        if (driverIdField.getText().isEmpty() || messageField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
            return;
        }

        for (Driver driver : dataManager.getDrivers()) {
            if (driver.getId().equals(driverIdField.getText())) {
                notificationService.sendDriverNotification(driverIdField.getText(), messageField.getText());
                refreshNotifications();
                clearFields();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Driver ID not found.");
    }

    private void refreshNotifications() {
        notificationListModel.clear();
        for (String notification : notificationService.getNotifications()) {
            if (notification.contains("Driver Notification")) {
                notificationListModel.addElement(notification);
            }
        }
    }

    private void clearFields() {
        driverIdField.setText("");
        messageField.setText("");
    }
}
