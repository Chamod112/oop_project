package com.fasttracklogistics.drivers;

import com.fasttracklogistics.data.DataManager;
import com.fasttracklogistics.model.Driver;

import javax.swing.*;
import java.awt.*;

public class DriverPanel extends JPanel {
    private DataManager dataManager = DataManager.getInstance();
    private JTextField idField, nameField, routeField;
    private DefaultListModel<String> driverListModel;

    public DriverPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Driver ID:"));
        idField = new JTextField();
        formPanel.add(idField);
        formPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);
        formPanel.add(new JLabel("Route:"));
        routeField = new JTextField();
        formPanel.add(routeField);

        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton removeButton = new JButton("Remove");
        formPanel.add(addButton);
        formPanel.add(updateButton);
        formPanel.add(removeButton);

        add(formPanel, BorderLayout.NORTH);

        driverListModel = new DefaultListModel<>();
        JList<String> driverList = new JList<>(driverListModel);
        add(new JScrollPane(driverList), BorderLayout.CENTER);

        refreshList();

        addButton.addActionListener(e -> addDriver());
        updateButton.addActionListener(e -> updateDriver());
        removeButton.addActionListener(e -> removeDriver());
        driverList.addListSelectionListener(e -> {
            int index = driverList.getSelectedIndex();
            if (index >= 0) {
                Driver driver = dataManager.getDrivers().get(index);
                idField.setText(driver.getId());
                nameField.setText(driver.getName());
                routeField.setText(driver.getRoute());
            }
        });
    }

    private void addDriver() {
        if (validateInput()) {
            Driver driver = new Driver(
                    idField.getText(),
                    nameField.getText(),
                    routeField.getText()
            );
            dataManager.addDriver(driver);
            refreshList();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "All fields must be filled.");
        }
    }

    private void updateDriver() {
        int index = driverListModel.size() - 1;
        if (index >= 0 && validateInput()) {
            Driver driver = new Driver(
                    idField.getText(),
                    nameField.getText(),
                    routeField.getText()
            );
            dataManager.updateDriver(driver);
            refreshList();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a driver and fill all fields.");
        }
    }

    private void removeDriver() {
        int index = driverListModel.size() - 1;
        if (index >= 0) {
            dataManager.removeDriver(dataManager.getDrivers().get(index).getId());
            refreshList();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "No driver selected.");
        }
    }

    private void refreshList() {
        driverListModel.clear();
        for (Driver driver : dataManager.getDrivers()) {
            driverListModel.addElement(driver.getId() + ": " + driver.getName());
        }
    }

    private boolean validateInput() {
        return !idField.getText().isEmpty() &&
                !nameField.getText().isEmpty() &&
                !routeField.getText().isEmpty();
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        routeField.setText("");
    }
}
