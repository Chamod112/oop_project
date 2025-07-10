package com.fasttracklogistics.data;

import com.fasttracklogistics.model.Shipment;
import com.fasttracklogistics.model.Driver;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager instance = new DataManager();
    private List<Shipment> shipments;
    private List<Driver> drivers;

    private DataManager() {
        shipments = new ArrayList<>();
        drivers = new ArrayList<>();
    }

    public static DataManager getInstance() {
        return instance;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void addShipment(Shipment shipment) {
        shipments.add(shipment);
    }

    public void updateShipment(Shipment shipment) {
        for (int i = 0; i < shipments.size(); i++) {
            if (shipments.get(i).getId().equals(shipment.getId())) {
                shipments.set(i, shipment);
                break;
            }
        }
    }

    public void removeShipment(String id) {
        shipments.removeIf(s -> s.getId().equals(id));
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void addDriver(Driver driver) {
        drivers.add(driver);
    }

    public void updateDriver(Driver driver) {
        for (int i = 0; i < drivers.size(); i++) {
            if (drivers.get(i).getId().equals(driver.getId())) {
                drivers.set(i, driver);
                break;
            }
        }
    }

    public void removeDriver(String id) {
        drivers.removeIf(d -> d.getId().equals(id));
    }
}
