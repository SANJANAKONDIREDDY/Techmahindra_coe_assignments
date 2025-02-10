package week2.Warehouse_Inventory_Management_System;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager();
        String dataFile = "inventory.dat";

        // Load inventory data from file
        inventoryManager.loadInventoryData(dataFile);

        // Initialize inventory
        Location location1 = new Location(1, 1, 1);
        Location location2 = new Location(1, 1, 2);
        Location location3 = new Location(2, 1, 1);

        Product product1 = new Product("P100", "Laptop", 10, location1);
        Product product2 = new Product("P101", "Mouse", 50, location2);
        Product product3 = new Product("P200", "Keyboard", 30, location3);
        Product product4 = new Product("P201", "Monitor", 15, location1);
        Product product5 = new Product("P202", "Webcam", 40, location2);

        inventoryManager.addProduct(product1);
        inventoryManager.addProduct(product2);
        inventoryManager.addProduct(product3);
        inventoryManager.addProduct(product4);
        inventoryManager.addProduct(product5);

        // Simulate concurrent order processing
        int numberOfThreads = 3;
        inventoryManager.simulateConcurrentOrders(numberOfThreads);

        inventoryManager.printInventory();

        // Save inventory data to file
        inventoryManager.saveInventoryData(dataFile);
    }
}