package com.fasttracklogistics.notification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationService {
    private List<String> notifications = new ArrayList<>();

    public void sendCustomerNotification(String shipmentId, String message) {
        notifications.add(String.format("[%s] Customer Notification for Shipment %s: %s", LocalDateTime.now(), shipmentId, message));
    }

    public void sendDriverNotification(String driverId, String message) {
        notifications.add(String.format("[%s] Driver Notification for Driver %s: %s", LocalDateTime.now(), driverId, message));
    }

    public List<String> getNotifications() {
        return notifications;
    }
}