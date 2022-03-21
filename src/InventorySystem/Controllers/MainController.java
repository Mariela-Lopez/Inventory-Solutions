package InventorySystem.Controllers;

import InventorySystem.Model.Inventory;
import InventorySystem.Model.Part;
import InventorySystem.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class MainController implements Initializable {
    /**
     * variable for selected part that will be modified.
     */
    public static Part selectedPartModify;

    /**
     * variable for the selected product that will be modified.
     */
    public static Product selectedProductModify;

    /**
     * search field for parts.
     */
    @FXML
    public TextField searchPartField;

    /**
     * search text field for products.
     */
    @FXML
    public TextField searchProductField;

    /**
     * tableveiw for parts.
     */
    @FXML
    private TableView<Part> partsTableMain;

    /**
     * id for parts in tableview.
     */
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    /**
     * name for parts in tableview.
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;

    /**
     * inventory column for parts in tableview.
     */
    @FXML
    private TableColumn<Part, Integer> partStockColumn;

    /**
     * price column for parts in tableveiw.
     */
    @FXML
    private TableColumn<Part, Integer> partPriceColumn;

    /**
     * tableview for products.
     */
    @FXML
    private TableView<Product> productsTableMain;

    /**
     * id column for products in tableveiw.
     */
    @FXML
    private TableColumn<Product, Integer> productIdColumn;

    /**
     * name column for products in tableview.
     */
    @FXML
    private TableColumn<Product, String> productNameColumn;

    /**
     * inventory column for products in tableveiw.
     */
    @FXML
    private TableColumn<Product, Integer> productStockColumn;

    /**
     * price column for tableview products.
     */
    @FXML
    private TableColumn<Product, Integer> productPriceColumn;

    /**
     * takes user to add part screen
     */
    @FXML
    void onAddPartButtonMain(ActionEvent event) throws IOException {

        Parent add_part = FXMLLoader.load(getClass().getResource("../Views/AddPartForm.fxml"));
        add_part.setStyle("-fx-font-family: sans-serif");
        Scene add_part_scene = new Scene(add_part);

        Stage add_part_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        add_part_stage.setScene(add_part_scene);
        add_part_stage.show();

    }

    /**
     * takes user to add product screen
     * @param event
     * @throws IOException
     */
    @FXML
    void onAddProductButtonMain(ActionEvent event) throws IOException {

        Parent add_product = FXMLLoader.load(getClass().getResource("../Views/AddProductForm.fxml"));
        add_product.setStyle("-fx-font-family: sans-serif");
        Scene add_product_scene = new Scene(add_product);
        Stage add_product_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        add_product_stage.setScene(add_product_scene);
        add_product_stage.show();

    }

    /**
     * deletes part based on selection
     * alerts user to confirm if part is to be deleted
     * @param event
     */
    @FXML
    void onDeletePartMain(ActionEvent event) {
        //This will get the highlighted part
        Part partSelection = partsTableMain.getSelectionModel().getSelectedItem();

        if (partSelection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part was selected!");
            alert.showAndWait();

        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to delete the selected part?");

        Optional<ButtonType> result = alert.showAndWait();
        System.out.println(result);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("hello");
            Inventory.deletePart(partSelection);
            partsTableMain.setItems(Inventory.getAllParts());
        }


    }

    /**
     * deletes part based on selection
     * alerts user to confirm if part is to be deleted if no associated parts are attached
     * if associated parts are attached, there is an error
     * @param event
     */
    @FXML
    void onDeleteProductMain(ActionEvent event) {
        //This will get the highlighted part
        Product productSelection = productsTableMain.getSelectionModel().getSelectedItem();

        if (productSelection == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No product was selected!");
            alert.showAndWait();

        }

        if (!productSelection.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The product you are trying to delete has associated parts!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete this product?");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("hello");
                Inventory.deleteProduct(productSelection);
                productsTableMain.setItems(Inventory.getAllProducts());

            }
        }
    }

    /**
     * closes out application
     * @param event
     */
    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);

    }

    /**
     * selects item and loads onto the next controller
     * @param event
     * @throws IOException
     */
    @FXML
    void onModifyPartButtonMain(ActionEvent event) throws IOException {

        selectedPartModify = partsTableMain.getSelectionModel().getSelectedItem();

        if (selectedPartModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part was selected!");
            alert.showAndWait();

        } else {

            Parent addPartScreenParent = FXMLLoader.load(getClass().getResource("../Views/ModifyPartForm.fxml"));
            Scene addPartScreenScene = new Scene(addPartScreenParent);
            Stage stage = new Stage();
            stage.setScene(addPartScreenScene);
            stage.show();
        }
    }

    /**
     * selects item and load onto the next controller
     * @param event
     * @throws IOException
     */
    @FXML
    void onModifyProductButtonMain(ActionEvent event) throws IOException {

        selectedProductModify = productsTableMain.getSelectionModel().getSelectedItem();
        if (selectedProductModify == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No part was selected!");
            alert.showAndWait();

        } else {

            Parent addPartScreenParent = FXMLLoader.load(getClass().getResource("../Views/ModifyProductForm.fxml"));
            addPartScreenParent.setStyle("-fx-font-family: sans-serif");
            Scene addPartScreenScene = new Scene(addPartScreenParent);
            Stage stage = new Stage();
            stage.setScene(addPartScreenScene);
            stage.show();
        }
    }

    /**
     * RUNTIME ERROR - It was filtering out individual searches for part id's.
     * new array was created for id searching only which would then highlight selected item
     * @param event
     */
    public void onPartSearch(ActionEvent event) {

        if (searchPartField.getText().isEmpty()) {
            partsTableMain.setItems(Inventory.getAllParts());
        }

        ObservableList<Part> idMatchingParts = FXCollections.observableArrayList();
        try {
            Part idMatchingPart = Inventory.lookupPart(Integer.parseInt(searchPartField.getText()));
            if (idMatchingPart != null) {
                idMatchingParts.add(idMatchingPart);
                int partIndex = IntStream.range(0, partsTableMain.getItems().size())
                        .filter(i -> idMatchingPart.getId() == partsTableMain.getItems().get(i).getId())
                        .findFirst().getAsInt();
                partsTableMain.requestFocus();
                partsTableMain.getSelectionModel().select(partIndex);
                partsTableMain.getFocusModel().focus(partIndex);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No matching part was found");
                alert.showAndWait();
            }
        } catch (NumberFormatException error) {
            ObservableList<Part> nameMatchingParts = Inventory.lookupPart(searchPartField.getText());

            partsTableMain.setItems(nameMatchingParts);
            if (nameMatchingParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No matching part was found");
                alert.showAndWait();
            }
        }
    }

    /**
     * sets parts and products on tableview
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTableMain.setItems(Inventory.getAllParts());

        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableMain.setItems(Inventory.getAllProducts());

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * RUNTIME ERROR - It was filtering out individual searches for part id's.
     * new array was created for id searching only which would then highlight selected item
     * @param event
     */
    public void onSearchProduct(ActionEvent event) {

        if (searchProductField.getText().isEmpty()) {
            productsTableMain.setItems(Inventory.getAllProducts());
        }
        ObservableList<Product> idMatchingProducts = FXCollections.observableArrayList();
        try {
            Product idMatchingProduct = Inventory.lookupProduct(Integer.parseInt(searchProductField.getText()));
            if (idMatchingProduct != null) {
                idMatchingProducts.add(idMatchingProduct);
                int productIndex = IntStream.range(0, productsTableMain.getItems().size())
                        .filter(i -> idMatchingProduct.getId() == productsTableMain.getItems().get(i).getId())
                        .findFirst().getAsInt();
                productsTableMain.requestFocus();
                productsTableMain.getSelectionModel().select(productIndex);
                productsTableMain.getFocusModel().focus(productIndex);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No matching product was found");
                alert.showAndWait();
            }
        } catch (NumberFormatException error) {
            ObservableList<Product> nameMatchingProducts = Inventory.lookupProduct(searchProductField.getText());

            productsTableMain.setItems(nameMatchingProducts);
            if (nameMatchingProducts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No matching product was found");
                alert.showAndWait();
            }

        }
    }
}




