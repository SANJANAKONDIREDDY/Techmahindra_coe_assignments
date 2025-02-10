package week2.Warehouse_Inventory_Management_System;


import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L; // Added serialVersionUID
    private int aisle;
    private int shelf;
    private int bin;

    public Location(int aisle, int shelf, int bin) {
        this.aisle = aisle;
        this.shelf = shelf;
        this.bin = bin;
    }

    // Getters and setters
    public int getAisle() {
        return aisle;
    }

    public void setAisle(int aisle) {
        this.aisle = aisle;
    }

    public int getShelf() {
        return shelf;
    }

    public void setShelf(int shelf) {
        this.shelf = shelf;
    }

    public int getBin() {
        return bin;
    }

    public void setBin(int bin) {
        this.bin = bin;
    }

    @Override
    public String toString() {
        return "Location{" +
                "aisle=" + aisle +
                ", shelf=" + shelf +
                ", bin=" + bin +
                '}';
    }
}
