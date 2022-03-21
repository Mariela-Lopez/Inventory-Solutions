package InventorySystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.stream.Collectors;

/**
 * Product class
 */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Observable list for associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * constructor for an instance of a Product object.
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }

    /**
     * getter for id.
     * @return
     */
    public int getId() {

        return id;
    }

    /**
     * setter for id.
     * @param id
     */
    public void setId(int id) {

        this.id = id;
    }

    /**
     * getter for name.
     * @return
     */
    public String getName() {

        return name;
    }

    /**
     * setter for name.
     * @param name
     */
    public void setName(String name) {

        this.name = name;
    }

    /**
     * getter for price.
     * @return
     */
    public double getPrice() {

        return price;
    }

    /**
     * setter for price.
     * @param price
     */
    public void setPrice(double price) {

        this.price = price;
    }

    /**
     * getter for stock.
     * @return
     */
    public int getStock() {

        return stock;
    }

    /**
     * setter for stock.
     * @param stock
     */
    public void setStock(int stock) {

        this.stock = stock;
    }

    /**
     * getter for min.
     * @return
     */
    public int getMin() {

        return min;
    }

    /**
     * setter for min.
     * @param min
     */
    public void setMin(int min) {

        this.min = min;
    }

    /**
     * getter fpr max.
     * @return
     */
    public int getMax() {

        return max;
    }

    /**
     * setter for max.
     * @param max
     */
    public void setMax(int max) {

        this.max = max;
    }

    /**
     * getter for all associated parts.
     * @return
     */
    public ObservableList<Part> getAllAssociatedParts() {

        return associatedParts;
    }

    /**
     * method deletes associated parts based on selected part.
     * @param selectedPart
     * @return
     */
    public boolean deleteAssociatedPart(Part selectedPart) {
        associatedParts.remove(selectedPart);
        return true;

    }

    /**
     * method adds associated parts to array list.
     * @param part
     */
    public void addAssociatedPart(Part part) {

        associatedParts.add(part);
    }
}