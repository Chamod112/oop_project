package com.fasttracklogistics.model;

import java.util.ArrayList;
import java.util.List;

public class Driver {
    private String id;
    private String name;
    private String route;
    private boolean isAvailable;
    private List<String> deliveryHistory;

    public Driver(String id, String name, String route) {
        this.id = id;
        this.name = name;
        this.route = route;
        this.isAvailable = true;
        this.deliveryHistory = new ArrayList<>();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRoute() { return route; }
    public void setRoute(String route) { this.route = route; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }
    public List<String> getDeliveryHistory() { return deliveryHistory; }
    public void addDelivery(String shipmentId) { deliveryHistory.add(shipmentId); }
}
