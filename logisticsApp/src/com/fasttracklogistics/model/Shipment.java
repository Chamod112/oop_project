package com.fasttracklogistics.model;

import java.time.LocalDateTime;

public class Shipment {
    private String id;
    private String sender;
    private String receiver;
    private String contents;
    private String status;
    private LocalDateTime estimatedDelivery;
    private String driverId;
    private String location;

    public Shipment(String id, String sender, String receiver, String contents, String status) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.contents = contents;
        this.status = status;
        this.estimatedDelivery = LocalDateTime.now().plusDays(2);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getReceiver() { return receiver; }
    public void setReceiver(String receiver) { this.receiver = receiver; }
    public String getContents() { return contents; }
    public void setContents(String contents) { this.contents = contents; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getEstimatedDelivery() { return estimatedDelivery; }
    public void setEstimatedDelivery(LocalDateTime estimatedDelivery) { this.estimatedDelivery = estimatedDelivery; }
    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
}
