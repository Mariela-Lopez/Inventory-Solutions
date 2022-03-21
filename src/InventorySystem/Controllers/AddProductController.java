package InventorySystem.Controllers;

import InventorySystem.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class AddProductController implements Initializable {
    /**
     * the boolean variable for the autoidGenerator method.
     */
    boolean numMatch = true;

    /**
     * the randomId variable being declared to the Random() number method.
     */
    Random randomNumber = new Random();

    /**
     * varible for automaticId.
     */
    int autoId;

    /**
     * the textfield for searching parts.
     */
    @FXML
    private TextField searchPartTextField;

    /**
     * The associated parts array.
     */
    @FXML
    private ObservableList<Part> partsToAdd = FXCollections.observableArrayList();

    /**
     * the textfield for id.
     */
    @FXML
    private TextField idTextField;

    /**
     * the textfield for name.
     */
    @FXML
    private TextField nameTextField;

    /**
     * the textfield for stock.
     */
    @FXML
    private TextField inventoryTextField;

    /**
     * the textfield for price.
     */
    @FXML
    private TextField priceTextField;

    /**
     * the textfield for max.
     */

    @FXML
    private TextField maxTextField;

    /**
     * the textfield for min.
     */
    @FXML
    private TextField minTextField;

    /**
     * the tableview for all parts.
     */
    @FXML
    private TableView<Part> allPartsTableView;

    /**
     * the part id column in tableveiw for all parts.
     */
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    /**
     * the name column in tableveiw for all parts.
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;

    /**
     * the inventory column for all parts in tableview.
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;

    /**
     * the price column for all parts in tableveiw.
     */
    @FXML
    private TableColumn<Part, Double> partPriceColumn;

    /**
     * the associated parts tableveiw.
     */
    @FXML
    private TableView<Part> associatedPartTableView;

    /**
     * the part id column for associated parts in tableveiw.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;

    /**
     * the name column for associated parts in tableveiw.
     */
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;

    /**
     * the inventory column for associated parts in tableveiw.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryColumn;

    /**
     * the price column for associated parts in tableveiw.
     */
    @FXML
    private TableColumn<Part, Double> associatedPartPriceColumn;

    /**
     * the tableview values are being set from the Inventory getAllParts method
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //sets part for the first tableview
        allPartsTableView.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //sets parts for the second tableveiw
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        idTextField.setEditable(false);
        autoId = autoIdGenerator();
        idTextField.setText(Integer.toString(autoId));

    }

    /**
     * generates a new random id and checks if it's a number match, if not it will return the value.
     * @return
     */
    public int autoIdGenerator() {

        autoId = 2 + randomNumber
                .nextInt(1000000);

        for (Product a : Inventory.getAllProducts()) {
            if (a.getId() == autoId) {
                numMatch = true;
                autoIdGenerator();
            }
        }
        return autoId;
    }

    /**
     * RUNTIME ERROR - if and else if nesting was incorrect and causing parts to be added with errors
     * @param event
     * @throws IOException
     */
    public void onSaveProduct(ActionEvent event) throws IOException {

        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        int stock = Integer.parseInt(inventoryTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        int min = Integer.parseInt(minTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());

        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Your min stock cannot be greater than your max!");
            alert.showAndWait();
        } else if (stock < min | stock > max) {
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error Dialog");
            alert2.setContentText("Your stock cannot be less than minimum or greater than maximum inventory!");
            alert2.showAndWait();
        } else if (price < 0) {
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("Error Dialog");
            alert3.setContentText("your price cannot be less than 0!");
            alert3.showAndWait();
        } else if (nameTextField.getText().isEmpty() || nameTextField.getText().matches("[0-9]+")) {
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("Error Dialog");
            alert4.setContentText("Part name cannot be empty or numeric!");
            alert4.showAndWait();

        } else {
            //new product is created and associated parts are added to product
            Product product = new Product(id, name, price, stock, min, max);
            for (Part part : partsToAdd) {
                product.addAssociatedPart(part);
            }
            Inventory.addProduct(product);

            Parent add_part = FXMLLoader.load(getClass().getResource("../Views/MainForm.fxml"));
            add_part.setStyle("-fx-font-family: sans-serif");
            Scene add_part_scene = new Scene(add_part);

            Stage add_part_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            add_part_stage.setScene(add_part_scene);
            add_part_stage.show();
        }
    }

    /**
     * when canceled it will allow the user back to the mainscreen
     * @param event
     * @throws IOException
     */
    public void onCancelButtonProduct(ActionEvent event) throws IOException {

        Parent add_part = FXMLLoader.load(getClass().getResource("../Views/MainForm.fxml"));
        add_part.setStyle("-fx-font-family: sans-serif");
        Scene add_part_scene = new Scene(add_part);

        Stage add_part_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        add_part_stage.setScene(add_part_scene);
        add_part_stage.show();
    }

    /**
     * gets highlights associated part from tableveiw and remove if user oks the alert.
     * @param actionEvent
     */
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {

        //highlighted part is being declared
        Part highlightedPart = associatedPartTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to remove the selected part?");

        //part gets removed after user oks.
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println(result);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            partsToAdd.remove(highlightedPart);
        }
    }

    /**
     * RUNTIME ERROR - when user clicked on add there was no array to store the associated parts
     * partsToAdd was created and I used stream to set filter out the parts that were being added
     * @param actionEvent
     */
    public void onAddAssociatedPart(ActionEvent actionEvent) {

        Part highlightedPart = allPartsTableView.getSelectionModel().getSelectedItem();

        //adds parts to new array and sets it in the tableveiw
        partsToAdd.add(highlightedPart);
        associatedPartTableView.setItems(partsToAdd);

        allPartsTableView.setItems(allPartsTableView.getItems().filtered(part -> !partsToAdd.stream().anyMatch(partToAdd -> partToAdd.getId() == part.getId())));
    }

    /**
     * RUNTIME ERROR - It was filtering out individual searches for part id's.
     * new array was created for id searching only which would then highlight selected item
     * @param event
     */
    public void onPartSearch(ActionEvent event) {

        //When user erases input data and presses enter, all parts are loaded again
        if (searchPartTextField.getText().isEmpty()) {
            allPartsTableView.setItems(Inventory.getAllParts());
        }

        //New array for id matching parts to be added during search
        ObservableList<Part> idMatchingParts = FXCollections.observableArrayList();
        try {
            Part idMatchingPart = Inventory.lookupPart(Integer.parseInt(searchPartTextField.getText()));
            if (idMatchingPart != null) {
                idMatchingParts.add(idMatchingPart);
                int partIndex = IntStream.range(0, allPartsTableView.getItems().size())
                        .filter(i -> idMatchingPart.getId() == allPartsTableView.getItems().get(i).getId())
                        .findFirst().getAsInt();
                allPartsTableView.requestFocus();
                allPartsTableView.getSelectionModel().select(partIndex);
                allPartsTableView.getFocusModel().focus(partIndex);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No matching part was found");
                alert.showAndWait();
            }
        } catch (NumberFormatException error) {
            //lookup method is used from inventory to get part names
            ObservableList<Part> nameMatchingParts = Inventory.lookupPart(searchPartTextField.getText());

            //all parts that match are set in the tableview
            allPartsTableView.setItems(nameMatchingParts);
            if (nameMatchingParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No matching part was found");
                alert.showAndWait();
            }
        }
    }
}