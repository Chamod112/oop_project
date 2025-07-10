package com.fasttracklogistics.assignment;

import com.fasttracklogistics.data.DataManager;
import com.fasttracklogistics.model.Driver;
import com.fasttracklogistics.model.Shipment;
import com.fasttracklogistics.notification.NotificationService;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

public class AssignmentPanel extends JPanel {
    private DataManager dataManager = DataManager.getInstance();
    private NotificationService notificationService = new NotificationService();
    private JTextField shipmentIdField, driverIdField;
    private DefaultListModel<String> assignmentListModel;

    public AssignmentPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        formPanel.add(new JLabel("Shipment ID:"));
        shipmentIdField = new JTextField();
        formPanel.add(shipmentIdField);
        formPanel.add(new JLabel("Driver ID (or leave blank for auto-assign):"));
        driverIdField = new JTextField();
        formPanel.add(driverIdField);

        JButton assignButton = new JButton("Assign Driver");
        formPanel.add(assignButton);

        add(formPanel, BorderLayout.NORTH);

        assignmentListModel = new DefaultListModel<>();
        JList<String> assignmentList = new JList<>(assignmentListModel);
        add(new JScrollPane(assignmentList), BorderLayout.CENTER);

        assignButton.addActionListener(e -> assignDriver());
    }

    private void assignDriver() {
        if (shipmentIdField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Shipment ID must be filled.");
            return;
        }

        Shipment targetShipment;
        targetShipment = dataManager.getShipments().stream().filter(shipment -> shipment.getId().equals(shipmentIdField.getText())).findFirst().orElse(null);

        if (targetShipment == null) {
            JOptionPane.showMessageDialog(this, "Shipment ID not found.");
            return;
        }

        String driverId = driverIdField.getText();
        Driver assignedDriver = null;

        if (driverId.isEmpty()) {
            assignedDriver = dataManager.getDrivers().stream()
                    .filter(Driver::isAvailable)
                    .min(Comparator.comparingInt(d -> Math.abs(d.getRoute().length() - targetShipment.getReceiver().length())))
                    .orElse(null);
        } else {
            for (Driver driver : dataManager.getDrivers()) {
                if (driver.getId().equals(driverId)) {
                    assignedDriver = driver;
                    break;
                }
            }
        }

        if (assignedDriver == null) {
            JOptionPane.showMessageDialog(this, "No available driver found or Driver ID not found.");
            return;
        }

        targetShipment.setDriverId(assignedDriver.getId());
        assignedDriver.setAvailable(false);
        assignedDriver.addDelivery(targetShipment.getId());
        assignmentListModel.addElement("Shipment " + targetShipment.getId() + " assigned to Driver " + assignedDriver.getId());
        notificationService.sendDriverNotification(assignedDriver.getId(), "Assigned to Shipment " + targetShipment.getId());
        clearFields();
    }

    private void clearFields() {
        shipmentIdField.setText("");
        driverIdField.setText("");
    }
}
