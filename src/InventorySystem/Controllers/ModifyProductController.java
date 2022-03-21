package InventorySystem.Controllers;

import InventorySystem.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class ModifyProductController implements Initializable {

    /**
     * array for associated parts.
     */
    @FXML
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * textfield for id.
     */
    @FXML
    private TextField idTextField;

    /**
     * textfield for name.
     */
    @FXML
    private TextField nameTextField;

    /**
     * textfield for inventory.
     *
     */
    @FXML
    private TextField inventoryTextField;

    /**
     * textfield for price.
     */
    @FXML
    private TextField priceTextField;

    /**
     * textfield for max.
     */
    @FXML
    private TextField maxTextField;

    /**
     * textfield for min.
     */
    @FXML
    private TextField minTextField;

    /**
     * textfield for searching parts.
     */
    @FXML
    private TextField searchIdLabel;

    /**
     * tableveiw for all parts.
     */
    @FXML
    private TableView<Part> tableviewAllParts;

    /**
     * part id column for all parts in tableveiw.
     */
    @FXML
    private TableColumn<Part, Integer> partIdColumn;

    /**
     * name column for all parts in tableview.
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;

    /**
     * inventory column for all parts in tableveiw.
     */
    @FXML
    private TableColumn<Part, Integer> inventoryLevelColumn;

    /**
     * price column for all parts in tableveiw.
     */
    @FXML
    private TableColumn<Part, Integer> priceColumn;

    /**
     * tableview for associated parts.
     */
    @FXML
    private TableView<Part> associatedPartsTableView;

    /**
     * associated parts id column for tableview.
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartIdColumn;

    /**
     * associated parts name column for tableveiw.
     */
    @FXML
    private TableColumn<Part, String> associatedPartNameColumn;

    /**
     * associated part inventory column for tableveiw.
     */
    @FXML
    private TableColumn<Part, Integer> associatedInventoryColumn;

    /**
     * associated part price column for tableveiw.
     */
    @FXML
    private TableColumn<Part, Double> associatedCostColumn;

    /**
     * takes user back to the mainscreen
     * @param event
     * @throws IOException
     */
    @FXML
    void onCancelButton(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.close();
    }

    /**
     * RUNTIME ERROR - It was filtering out individual searches for part id's.
     * new array was created for id searching only which would then highlight selected item
     * @param event
     */
    @FXML
    void onSearch(ActionEvent event) {
        //When user erases input data and presses enter, all parts are loaded again

        if (searchIdLabel.getText().isEmpty()) {
            tableviewAllParts.setItems(Inventory.getAllParts());
        }

        //New array for id matching parts to be added during search
        ObservableList<Part> idMatchingParts = FXCollections.observableArrayList();
        try {
            Part idMatchingPart = Inventory.lookupPart(Integer.parseInt(searchIdLabel.getText()));
            if (idMatchingPart != null) {
                idMatchingParts.add(idMatchingPart);
                int partIndex = IntStream.range(0, tableviewAllParts.getItems().size())
                        .filter(i -> idMatchingPart.getId() == tableviewAllParts.getItems().get(i).getId())
                        .findFirst().getAsInt();
                tableviewAllParts.requestFocus();
                tableviewAllParts.getSelectionModel().select(partIndex);
                tableviewAllParts.getFocusModel().focus(partIndex);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No matching part was found");
                alert.showAndWait();
            }
        } catch (NumberFormatException error) {
            //lookup method is used from inventory to get part names
            ObservableList<Part> nameMatchingParts = Inventory.lookupPart(searchIdLabel.getText());

            //all parts that match are set in the tableview
            tableviewAllParts.setItems(nameMatchingParts);
            if (nameMatchingParts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText("No matching part was found");
                alert.showAndWait();
            }
        }
    }

    /**
     * RUNTIME ERROR - if and else if nesting was incorrect and causing parts to be added with errors
     * @param event
     * @throws IOException
     */
    @FXML
    void onSaveButton(ActionEvent event) {

        int index = Inventory.getAllProducts().indexOf(MainController.selectedProductModify);

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
            //product is updated and associated parts are added to product
            Product product = new Product(id, name, price, stock, min, max);
            Inventory.updateProduct(index, product);
            for (Part part : associatedParts) {
                product.addAssociatedPart(part);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            stage.close();
        }
    }

    /**
     * gets highlights associated part from tableveiw and remove if user oks the alert.
     * @param event
     */
    @FXML
    void onRemoveAssocatedPart(ActionEvent event) {

        //highlighted part is being declared
        Part highlightedPart = associatedPartsTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to remove the selected part?");

        //part gets removed after user oks.
        Optional<ButtonType> result = alert.showAndWait();
        System.out.println(result);
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("hello");
            associatedParts.remove(highlightedPart);
        }
    }

    /**
     * RUNTIME ERROR - when user clicked on add there was no array to store the associated parts
     * partsToAdd was created and I used stream to set filter out the parts that were being added
     * @param event
     */
    @FXML
    void onAddPart(ActionEvent event) {

        //adds parts to new array and sets it in the tableveiw
        Part highlightedPart = tableviewAllParts.getSelectionModel().getSelectedItem();
        associatedParts.add(highlightedPart);
        associatedPartsTableView.setItems(associatedParts);
        tableviewAllParts.setItems(tableviewAllParts.getItems().filtered(part -> !associatedParts.stream().anyMatch(partToAdd -> partToAdd.getId() == part.getId())));
    }

    /**
     * the tableview values are being set from the Inventory getAllParts method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Product selectedProduct = MainController.selectedProductModify;
        associatedParts = selectedProduct.getAllAssociatedParts();


        tableviewAllParts.setItems(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(associatedParts);
        associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        idTextField.setText(String.valueOf(selectedProduct.getId()));
        nameTextField.setText(selectedProduct.getName());
        inventoryTextField.setText(String.valueOf(selectedProduct.getStock()));
        priceTextField.setText(String.valueOf(selectedProduct.getPrice()));
        maxTextField.setText(String.valueOf(selectedProduct.getMax()));
        minTextField.setText(String.valueOf(selectedProduct.getMin()));

        idTextField.setEditable(false);
    }
}