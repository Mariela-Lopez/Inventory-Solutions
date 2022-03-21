package InventorySystem.Controllers;

import InventorySystem.Model.InHouse;
import InventorySystem.Model.Inventory;
import InventorySystem.Model.OutSourced;
import InventorySystem.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class AddPartController implements Initializable {

    /**
     * the label for Company Name.
     */
    @FXML
    public Label companyName;

    /**
     * the textfield for Company Name.
     */
    @FXML
    public TextField companyNameTextField;

    /**
     * the label for Machine Id.
     */
    @FXML
    public Label machineId;

    /**
     * the radiobutton for InHouse.
     */
    @FXML
    private RadioButton inHouseRadioButton;

    /**
     * the textfield for Id.
     */
    @FXML
    private TextField idTextField;

    /**
     * the textfield for Name.
     */
    @FXML
    private TextField nameTextField;

    /**
     * the textfield for Stock.
     */
    @FXML
    private TextField inventoryTextField;

    /**
     * the textfield for Cost/Price.
     */
    @FXML
    private TextField priceTextField;

    /**
     * the textfield for Max.
     */
    @FXML
    private TextField maxTextField;

    /**
     * the textfield for Min.
     */
    @FXML
    private TextField minTextField;

    /**
     * the textfield for Machine Id.
     */
    @FXML
    private TextField machineIdTextField;

    /**
     * the boolean variable for the autoidGenerator method.
     */
    boolean numberMatch = true;

    /**
     * the randomId variable being declared to the Random() number method.
     */
    Random randomId = new Random();

    /**
     * varible for automaticId.
     */
    int automaticId;

    /**
     * allows the user to go back to the Mainscreen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onCancelButton(ActionEvent event) throws IOException {

        //Loader creating new scene going to Mainscreen
        Parent add_part = FXMLLoader.load(getClass().getResource("../Views/MainForm.fxml"));
        add_part.setStyle("-fx-font-family: sans-serif");
        Scene add_part_scene = new Scene(add_part);
        Stage add_part_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        add_part_stage.setScene(add_part_scene);
        add_part_stage.show();

    }

    /**
     * RUNTIME ERROR - if and else if nesting was incorrect and causing parts to be added with errors
     * @param event
     * @throws IOException
     */
    @FXML
    void onSaveButton(ActionEvent event) throws IOException {

        //textfield data is being obtained to make a new object below
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        int stock = Integer.parseInt(inventoryTextField.getText());
        double price = Double.parseDouble(priceTextField.getText());
        int min = Integer.parseInt(minTextField.getText());
        int max = Integer.parseInt(maxTextField.getText());

        // will give errors when input is wrong
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
            //If InHouse radio button is selected a new object will be created and will forward user to mainscrren
            if (inHouseRadioButton.isSelected()) {
                Inventory.addPart(new InHouse(id, name, price, stock, min, max,
                        Integer.parseInt(machineIdTextField.getText())));
                Parent add_part = FXMLLoader.load(getClass().getResource("../Views/MainForm.fxml"));
                add_part.setStyle("-fx-font-family: sans-serif");
                Scene add_part_scene = new Scene(add_part);

                Stage add_part_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                add_part_stage.setScene(add_part_scene);
                add_part_stage.show();
            } else {
                //if Inhouse is not selected, then Outsourced object will be created and user will be sent to new screen
                Inventory.addPart(new OutSourced(id, name, price, stock, min, max,
                        companyNameTextField.getText()));
                Parent add_part = FXMLLoader.load(getClass().getResource("../Views/MainForm.fxml"));
                add_part.setStyle("-fx-font-family: sans-serif");
                Scene add_part_scene = new Scene(add_part);

                Stage add_part_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                add_part_stage.setScene(add_part_scene);
                add_part_stage.show();
            }
        }
    }

    /**
     * generates a new random id and checks if it's a number match, if not it will return the value.
     * @return
     */
    public int autoIdGenerator() {

        automaticId = 1 + randomId
                .nextInt(1000);

        for (Part a : Inventory.getAllParts()) {

            if (a.getId() == automaticId) {

                numberMatch = true;
                autoIdGenerator();
            }
        }
        return automaticId;
    }

    /**
     * when inHouse radio button is selected, it will hide the values of the Outsource labels and fields
     * @param event
     */
    @FXML
    void onInHouseEvent(ActionEvent event) {

        System.out.println("on onhouse clicked");
        machineId.setVisible(true);
        machineIdTextField.setVisible(true);
        companyName.setVisible(false);
        companyNameTextField.setVisible(false);

    }

    /**
     * when Outsource radio button is selected, it will hide the values of the InHouse labels and fields
     * @param event
     */
    @FXML
    void onOutSourcedEvent(ActionEvent event) {

        System.out.println("on outsourced clicked");
        machineId.setVisible(false);
        machineIdTextField.setVisible(false);
        companyName.setVisible(true);
        companyNameTextField.setVisible(true);

    }

    /**
     * id text field is set to noneditable
     * automatic id is being set into the id textfield as the generated part id
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idTextField.setEditable(false);
        automaticId = autoIdGenerator();
        idTextField.setText(Integer.toString(automaticId));

    }
}
