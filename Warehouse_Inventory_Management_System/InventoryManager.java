package week2.Warehouse_Inventory_Management_System;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.*;
import java.io.*;

public class InventoryManager {
    private final Map<String, Product> products; // Key: ProductID, Value: Product
    private final PriorityQueue<Order> orderQueue;
    private final Lock lock = new ReentrantLock();
    private final Logger logger = Logger.getLogger(InventoryManager.class.getName());

    public InventoryManager() {
        products = new ConcurrentHashMap<>();
        orderQueue = new PriorityQueue<>(Comparator.comparing(Order::getPriority)); // Orders are processed based on priority
        setupLogger();
    }

    private void setupLogger() {
        try {
            FileHandler fh = new FileHandler("inventory.log", true); // Append to the log file
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.addHandler(fh);
            logger.setLevel(Level.INFO); // Log level
        } catch (IOException e) {
            System.err.println("Could not set up logger: " + e.getMessage());
        }
    }

    public void addProduct(Product product) {
        products.put(product.getProductID(), product);
        logger.info("Added product: " + product);
    }

    public Product getProduct(String productID) {
        return products.get(productID);
    }

    public void updateProductQuantity(String productID, int quantityChange) throws OutOfStockException {
        lock.lock();
        try {
            Product product = products.get(productID);
            if (product != null) {
                int newQuantity = product.getQuantity() + quantityChange;
                if (newQuantity < 0) {
                    throw new OutOfStockException("Product " + productID + " out of stock.");
                }
                product.setQuantity(newQuantity);
                logger.info("Updated quantity of " + productID + " to " + newQuantity);
            } else {
                logger.warning("Product " + productID + " not found for quantity update.");
            }
        } finally {
            lock.unlock();
        }
    }

    public void submitOrder(Order order) {
        orderQueue.offer(order);
        logger.info("Order submitted: " + order);
    }

    public void processOrders() {
        //This can be modified to use a thread pool
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.poll();
            processOrder(order);
        }

    }

    private void processOrder(Order order) {
        logger.info("Processing order: " + order.getOrderID());
        try {
            for (String productID : order.getProductIDs()) {
                updateProductQuantity(productID, -1); // Assuming each product ID means quantity -1
            }
            logger.info("Order " + order.getOrderID() + " processed successfully.");
        } catch (OutOfStockException e) {
            logger.severe("Could not process order " + order.getOrderID() + ": " + e.getMessage());
        }
    }

    // Method to save inventory data to a file
    public void saveInventoryData(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(products);
            logger.info("Inventory data saved to " + filename);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error saving inventory data: " + e.getMessage(), e);
        }
    }

    // Method to load inventory data from a file
    public void loadInventoryData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Map<String, Product> loadedProducts = (Map<String, Product>) ois.readObject();
            products.putAll(loadedProducts);
            logger.info("Inventory data loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            logger.log(Level.WARNING, "Error loading inventory data: " + e.getMessage(), e);
        }
    }

    public void simulateConcurrentOrders(int numberOfThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executor.submit(() -> {
                try {
                    // Simulate placing orders
                    List<String> productIds1 = Arrays.asList("P100", "P101");
                    Order order1 = new Order("Order-" + Thread.currentThread().getId(), productIds1, Order.Priority.EXPEDITED);
                    submitOrder(order1);

                    List<String> productIds2 = Arrays.asList("P200", "P201", "P202");
                    Order order2 = new Order("Order-" + Thread.currentThread().getId(), productIds2, Order.Priority.STANDARD);
                    submitOrder(order2);

                    processOrders();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error in thread: " + e.getMessage(), e);
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Executor termination interrupted: " + e.getMessage(), e);
        }
    }

    //Other Methods
    public void printInventory() {
        System.out.println("Current Inventory:");
        products.forEach((key, product) -> System.out.println("Product ID: " + key + ", Details: " + product));
    }
}