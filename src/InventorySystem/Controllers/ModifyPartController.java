package InventorySystem.Controllers;

import InventorySystem.Model.InHouse;
import InventorySystem.Model.Inventory;
import InventorySystem.Model.OutSourced;
import InventorySystem.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {
    /**
     * inhouse radio button.
     */
    @FXML
    private RadioButton inHouseRadioButton;

    /**
     * outsource radio button.
     */
    @FXML
    private RadioButton OutSourcedRadioButton;

    /**
     * id text field.
     */
    @FXML
    private TextField idTextField;

    /**
     * name text field.
     */
    @FXML
    private TextField nameTextField;

    /**
     * inventory textfield.
     */
    @FXML
    private TextField inventoryTextField;

    /**
     * price text field.
     */
    @FXML
    private TextField priceTextField;

    /**
     * max text field.
     */
    @FXML
    private TextField maxTextField;

    /**
     * label for Machine Id.
     */
    @FXML
    private Label machineIdlabel;
    /**
     * machine id text field.
     */
    @FXML
    private TextField machineIdTextField;

    /**
     * min text field.
     */
    @FXML
    private TextField minTextField;

    /**
     * label for Company Name.
     */
    @FXML
    private Label companyNameLabel;

    /**
     * company name text field.
     */
    @FXML
    private TextField companyNameTextField;

    @FXML

    /**
     * when inhouse is clicked, the labels and fields for Outsourced are hidden
     */
    void onInHouseRadioButton(ActionEvent event) {

        System.out.println("on onhouse clicked");
        machineIdlabel.setVisible(true);
        machineIdTextField.setVisible(true);
        companyNameLabel.setVisible(false);
        companyNameTextField.setVisible(false);
    }

    /**
     * when Outsource radio button is selected, it will hide the values of the InHouse labels and fields
     * @param event
     */
    @FXML
    void onOutsourcedRadioButton(ActionEvent event) {

        System.out.println("on outsourced clicked");
        machineIdlabel.setVisible(false);
        machineIdTextField.setVisible(false);
        companyNameLabel.setVisible(true);
        companyNameTextField.setVisible(true);
    }

    @FXML
    /**
     * RUNTIME ERROR - if and else if nesting was incorrect and causing parts to be added with errors
     */
    void onSaveButton(ActionEvent event) throws IOException {

        //index is being obtained from the selected part of MainController
        int index = Inventory.getAllParts().indexOf(MainController.selectedPartModify);

        //textfield data is being obtained to update an object below

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
        }  else if (nameTextField.getText().isEmpty() || nameTextField.getText().matches("[0-9]+")){
            Alert alert4 = new Alert(Alert.AlertType.ERROR);
            alert4.setTitle("Error Dialog");
            alert4.setContentText("Part name cannot be empty or numeric!");
            alert4.showAndWait();

        } else {

            //If InHouse radio button is selected index of object will be updated based on the values

            if (inHouseRadioButton.isSelected()) {
                InHouse inHousePart = new InHouse(id, name, price, stock, min, max,
                        Integer.parseInt(machineIdTextField.getText()));
                Inventory.updatePart(index, inHousePart);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.close();
            } else {
                //If Outsource radio button is selected index of object will be updated based on the values
                OutSourced outSourcedPart = new OutSourced(id, name, price, stock, min, max,
                        companyNameTextField.getText());
                Inventory.updatePart(index, outSourcedPart);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                stage.close();
            }
        }
    }

    /**
     * takes user back to the main screen
     * @param event
     * @throws IOException
     */
    public void onCancelButtonModifyPart(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.close();
    }

    /**
     * determines if part is an instance of Inhouse or Outsource and will set fields based on that
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idTextField.setEditable(false);
        Part selectedPart = MainController.selectedPartModify;

        if ( selectedPart instanceof InHouse) {

            idTextField.setText(String.valueOf(selectedPart.getId()));
            nameTextField.setText(selectedPart.getName());
            inventoryTextField.setText(String.valueOf(selectedPart.getStock()));
            priceTextField.setText(String.valueOf(selectedPart.getPrice()));
            maxTextField.setText(String.valueOf(selectedPart.getMax()));
            minTextField.setText(String.valueOf(selectedPart.getMin()));

            inHouseRadioButton.setSelected(true);

            machineIdlabel.setVisible(true);
            machineIdTextField.setVisible(true);
            companyNameLabel.setVisible(false);
            companyNameTextField.setVisible(false);
            machineIdTextField.setText(Integer.toString(((InHouse) selectedPart).getMachineId()));
        }
        if (selectedPart instanceof OutSourced) {

            idTextField.setText(String.valueOf(selectedPart.getId()));
            nameTextField.setText(selectedPart.getName());
            inventoryTextField.setText(String.valueOf(selectedPart.getStock()));
            priceTextField.setText(String.valueOf(selectedPart.getPrice()));
            maxTextField.setText(String.valueOf(selectedPart.getMax()));
            minTextField.setText(String.valueOf(selectedPart.getMin()));

            OutSourcedRadioButton.setSelected(true);

            machineIdlabel.setVisible(false);
            machineIdTextField.setVisible(false);
            companyNameLabel.setVisible(true);
            companyNameTextField.setVisible(true);
            companyNameTextField.setText(((OutSourced) selectedPart).getCompanyName());
        }
    }
}