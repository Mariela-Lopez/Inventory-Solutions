package InventorySystem;



import InventorySystem.Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This application is an inventory system that keeps track of parts as they are modified and associated with products.
 * FUTURE ENHANCEMENT: adding a functionality to the tableview where the user can view the details of all changes
 * and provide user id's to veiw the complete history of each part and product.
 */

public class Main extends Application {

    @Override
/**
 * start method creates the first scene and laods all information.
 */
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Views/MainForm.fxml"));
        root.setStyle("-fx-font-family: sans-serif");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);



        // primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();



    }

    /**
     * the main method is used to create objects to load onto the screen.
     * the folder is located directly inside Inventory System folder. It is called Javadocs.
     * @param args
     */
    public static void main(String[] args) {

        //Inhouse parts displayed in main
        InHouse part1 = new InHouse(1,"adapter",25.99,45,10,100,455);
        InHouse part2 = new InHouse(2,"earphones",10.99,56,15,78,900);
        OutSourced part3 = new OutSourced(3,"charger",20.99,40,19,65,"Charg Company");
        OutSourced part4 = new OutSourced(4,"Screen Protector",15.99,45,12,55,"Screen company");


        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addPart(part4);

        Product product1 = new Product(1,"OnePlus",300.99,20,5,22);
        Product product2 = new Product(2,"Iphone",599.00,18,10,26);
        Product product3 = new Product(3,"Galaxy Note",699.00,16,7,20);
        Product product4 = new Product(4,"Google Phone",419.99,8,3,14);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        Inventory.addProduct(product3);
        Inventory.addProduct(product4);



        launch(args);
    }

}