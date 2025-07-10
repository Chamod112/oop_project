package com.fasttracklogistics.reports;

import com.fasttracklogistics.data.DataManager;
import com.fasttracklogistics.model.Shipment;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.time.LocalDate;

public class ReportPanel extends JPanel {
    private DataManager dataManager = DataManager.getInstance();

    public ReportPanel() {
        setLayout(new BorderLayout());

        JButton generateButton = new JButton("Generate Monthly Report");
        add(generateButton, BorderLayout.NORTH);

        generateButton.addActionListener(e -> generateReport());
    }

    private void generateReport() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("MonthlyReport_" + LocalDate.now() + ".pdf"));
            document.open();
            document.add(new Paragraph("FastTrack Logistics Monthly Report"));
            document.add(new Paragraph("Generated on: " + LocalDate.now()));
            document.add(new Paragraph("Shipment Volume: " + dataManager.getShipments().size()));
            document.add(new Paragraph("Delivery Performance: " + calculatePerformance() + "%"));
            document.add(new Paragraph("Customer Satisfaction: " + calculateSatisfaction() + "%"));
            document.add(new Paragraph("\nShipment Details:"));
            for (Shipment shipment : dataManager.getShipments()) {
                document.add(new Paragraph("Shipment " + shipment.getId() + ": " + shipment.getStatus() + " to " + shipment.getReceiver()));
            }
            document.close();
            JOptionPane.showMessageDialog(this, "Report generated as MonthlyReport_" + LocalDate.now() + ".pdf");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
        }
    }

    private double calculatePerformance() {
        long delivered = dataManager.getShipments().stream().filter(s -> s.getStatus().equalsIgnoreCase("Delivered")).count();
        return dataManager.getShipments().isEmpty() ? 0 : (delivered * 100.0 / dataManager.getShipments().size());
    }

    private double calculateSatisfaction() {
        return dataManager.getShipments().isEmpty() ? 0 : 90.0; // Placeholder
    }
}
