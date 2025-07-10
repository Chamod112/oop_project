package com.fasttracklogistics.gui;

import com.fasttracklogistics.shipments.ShipmentPanel;
import com.fasttracklogistics.drivers.DriverPanel;
import com.fasttracklogistics.sheduling.SchedulingPanel;
import com.fasttracklogistics.tracking.TrackingPanel;
import com.fasttracklogistics.assignment.AssignmentPanel;
import com.fasttracklogistics.reports.ReportPanel;
import com.fasttracklogistics.notification.customer.CustomerNotificationPanel;
import com.fasttracklogistics.notification.driver.DriverNotificationPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("FastTrack Logistics Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Manage Shipments", new ShipmentPanel());
        tabbedPane.addTab("Manage Drivers", new DriverPanel());
        tabbedPane.addTab("Schedule Deliveries", new SchedulingPanel());
        tabbedPane.addTab("Track Shipments", new TrackingPanel());
        tabbedPane.addTab("Assign Drivers", new AssignmentPanel());
        tabbedPane.addTab("Generate Reports", new ReportPanel());
        tabbedPane.addTab("Customer Notifications", new CustomerNotificationPanel());
        tabbedPane.addTab("Driver Notifications", new DriverNotificationPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }
}
