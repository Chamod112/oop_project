package com.fasttracklogistics.shipments;

import com.fasttracklogistics.data.DataManager;
import com.fasttracklogistics.model.Shipment;

import javax.swing.*;
import java.awt.*;

public class ShipmentPanel extends JPanel {
    private DataManager dataManager = DataManager.getInstance();
    private JTextField idField, senderField, receiverField, contentsField, statusField;
    private DefaultListModel<String> shipmentListModel;

    public ShipmentPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.add(new JLabel("Shipment ID:"));
        idField = new JTextField();
        formPanel.add(idField);
        formPanel.add(new JLabel("Sender:"));
        senderField = new JTextField();
        formPanel.add(senderField);
        formPanel.add(new JLabel("Receiver:"));
        receiverField = new JTextField();
        formPanel.add(receiverField);
        formPanel.add(new JLabel("Contents:"));
        contentsField = new JTextField();
        formPanel.add(contentsField);
        formPanel.add(new JLabel("Status:"));
        statusField = new JTextField();
        formPanel.add(statusField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");
        formPanel.add(addButton);
        formPanel.add(updateButton);
        formPanel.add(removeButton);

        add(formPanel, BorderLayout.NORTH);

        shipmentListModel = new DefaultListModel<>();
        JList<String> shipmentList = new JList<>(shipmentListModel);
        add(new JScrollPane(shipmentList), BorderLayout.CENTER);

        refreshList();

        addButton.addActionListener(e -> addShipment());
        updateButton.addActionListener(e -> updateShipment());
        removeButton.addActionListener(e -> removeShipment());
        shipmentList.addListSelectionListener(e -> {
            int index = shipmentList.getSelectedIndex();
            if (index >= 0) {
                Shipment shipment = dataManager.getShipments().get(index);
                idField.setText(shipment.getId());
                senderField.setText(shipment.getSender());
                receiverField.setText(shipment.getReceiver());
                contentsField.setText(shipment.getContents());
                statusField.setText(shipment.getStatus());
            }
        });
    }

    private void addShipment() {
        if (validateInput()) {
            Shipment shipment = new Shipment(
                    idField.getText(),
                    senderField.getText(),
                    receiverField.getText(),
                    contentsField.getText(),
                    statusField.getText()
            );
            dataManager.addShipment(shipment);
            refreshList();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
        }
    }

    private void updateShipment() {
        int index = shipmentListModel.size() - 1;
        if (index >= 0 && validateInput()) {
            Shipment shipment = new Shipment(
                    idField.getText(),
                    senderField.getText(),
                    receiverField.getText(),
                    contentsField.getText(),
                    statusField.getText()
            );
            dataManager.updateShipment(shipment);
            refreshList();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a shipment and fill all fields.");
        }
    }

    private void removeShipment() {
        int index = shipmentListModel.size() - 1;
        if (index >= 0) {
            dataManager.removeShipment(dataManager.getShipments().get(index).getId());
            refreshList();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "No shipment selected.");
        }
    }

    private void refreshList() {
        shipmentListModel.clear();
        for (Shipment shipment : dataManager.getShipments()) {
            shipmentListModel.addElement(shipment.getId() + ": " + shipment.getContents());
        }
    }

    private boolean validateInput() {
        return !idField.getText().isEmpty() &&
                !senderField.getText().isEmpty() &&
                !receiverField.getText().isEmpty() &&
                !contentsField.getText().isEmpty() &&
                !statusField.getText().isEmpty();
    }

    private void clearFields() {
        idField.setText("");
        senderField.setText("");
        receiverField.setText("");
        contentsField.setText("");
        statusField.setText("");
    }
}
