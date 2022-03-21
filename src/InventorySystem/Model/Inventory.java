package InventorySystem.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.stream.Collectors;

/**
 * Inventory class
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * method that adds parts to Array list.
     * @param part
     */
    //Add parts
    public static void addPart(Part part) {

        allParts.add(part);
    }

    /**
     * method that adds products to Array list.
     * @param product
     */
    //Add Products
    public static void addProduct(Product product) {

        allProducts.add(product);
    }

    /**
     * method that returns array list with all parts.
     * @return
     */
    //Get all parts
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * method that returns array list with all products.
     * @return
     */
    //Get all Products
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * method that deletes part from array list.
     * @param selectedPart
     * @return
     */
   public static boolean deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
        return true;

   }

    /**
     * method that deletes a product from array list.
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        return true;

    }

    /**
     * method that updates part according to index and selected part that is passed.
     * @param index
     * @param selectedPart
     */
   public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);

   }

    /**
     * method that updates product according to index and selected product that is passed.
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);
    }

    /**
     * method that looks up part in array list based on id.
     * @param id
     * @return
     */
    public static Part lookupPart(int id) {
        return allParts.stream()
                .filter(part -> part.getId() == id)
                .findFirst()
                .orElse(null);

    }

    /**
     * method that looks up part in array list based on part name.
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.stream()
                .filter(part -> part.getName().contains(partName))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> FXCollections.observableArrayList(list)));

    }

    /**
     * method that looks up product in array list based on id.
     * @param id
     * @return
     */
    public static Product lookupProduct(int id) {
        return allProducts.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);

    }

    /**
     * method that looks up product in array list based on product name.
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.stream()
                .filter(product -> product.getName().contains(productName))
                .collect(Collectors.collectingAndThen(Collectors.toList(), list -> FXCollections.observableArrayList(list)));

    }



}
