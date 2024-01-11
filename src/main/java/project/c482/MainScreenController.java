package project.c482;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author Joshua James
 * Student ID: 011101737
 * Date Completed: August 9, 2023 (edited August 11, 2023)
 * Program Used: Intellij IDEA Community Edition 2023.2
 */


/**
 * MainScreenController class initialized. The MainScreenController class controls the functionality
 * and features on MainScreen scene.
 */
public class MainScreenController implements Initializable {

    /**
     * Stage, scene.
     */
    @FXML
    private Stage stage;
    private Scene scene;

    /**
     * Table view for Part.
     */
    public TableView<Part> tablePart;
    /**
     * Table column for Part ID.
     */
    public TableColumn idColumnPart;
    /**
     * Table column for Part name.
     */
    public TableColumn nameColumnPart;
    /**
     * Table column for Part price.
     */
    public TableColumn priceColumnPart;
    /**
     * Table column for Part Stock (quantity of part).
     */
    public TableColumn stockColumnPart;
    /**
     * Index of the inventory
     */
    public static int index;
    /**
     * TextField for part company name or machine ID.
     */
    public TextField partName_or_ID;

    /**
     * Table view for Product.
     */
    public TableView<Product> tableProduct;
    /**
     * Table column for Product ID.
     */
    public TableColumn idColumnProduct;
    /**
     * Table column for Product name.
     */
    public TableColumn nameColumnProduct;
    /**
     * Table column for Product price.
     */
    public TableColumn priceColumnProduct;
    /**
     * Table column for Product stock (quantity of product).
     */
    public TableColumn stockColumnProduct;
    /**
     * TextField for product company name or machine ID.
     */
    public TextField productName_or_ID;

    /**
     * When the user clicks the Add button in the Part table, the following brings up the
     * add part scene.
     * @param event user clicks the add button.
     * @throws IOException from FXMLLoader.
     */
    public void clickPartAddButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddPart.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Part");
        stage.show();
    }

    /**
     * Similar to the Add part button above, if user clicks the Modify button in the Part
     * table, the following brings up the Modify Part scene. Note: If user selects the Modify
     * button before selecting a part, they will see an error message pop up prompting them
     * to select a part to modify.
     * @param event user clicks the modify button.
     * @throws IOException from FXMLLoader.
     */
    public void clickPartModifyButton(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPart.fxml"));
        loader.load();

        ModifyPartController modifyPartController = loader.getController();

        Part part = tablePart.getSelectionModel().getSelectedItem();
        index = tablePart.getSelectionModel().getSelectedIndex();

        // Pop up error message if user selects Modify button before selecting a part,
        // otherwise (else) will be brought to Modify Part scene.
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error: No part was selected to be modified.");
            alert.setContentText("Please ensure a part is selected first.");
            alert.showAndWait();
        }
        else {
            modifyPartController.partData(part);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.setTitle("Modify Part");
            stage.show();
        }
    }

    /**
     * Remove Part from Part table.
     * 1. User first picks the part they wish to remove.
     * 2. User then selects the Delete button.
     * 3. User is prompted with a pop-up warning to confirm they wish to delete part.
     * 4. Once user clicks "OK", Part is then deleted, otherwise if they select "cancel" the part
     * and part table will remain unchanged.
     * Note: If user clicks the Delete button before picking a part, they will see an error message
     * pop up prompting them to pick a part to be deleted first.
     * @param event user selects Delete button in the part table.
     */
    public void clickPartDeleteButton(ActionEvent event) throws IOException {

        Part part = tablePart.getSelectionModel().getSelectedItem();

        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error: No Part was selected to be deleted.");
            alert.setContentText("A part must be selected in order to be deleted.");
            alert.showAndWait();
        }
        else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation");
            alert2.setHeaderText("Confirmation: Delete Part?");
            alert2.setContentText("Are you sure you want to delete part?");

            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                // If user selects OK, the Part is then deleted from the Inventory.
                Inventory.deletePart(part);
            } else {
                // If the user selects "Cancel" or closes the pop-up window, then the Part is
                // not deleted and the Inventory remains unchanged.
            }
        }
    }

    /**
     * Search Part by ID or partial or full name.
     * @param event user presses the enter key on their keyboard to perform part search.
     * 1. User types partial or full Part name, or Part ID, of the part they wish to search for in
     * the "Search by Part ID or Name" text field in the Part table.
     * 2. User presses the enter key on their keyboard.
     * 3. If a match or multiple matches are found, then the matching search results are displayed.
     * Note: If no matching search results are found, then a warning message box will display stating
     * that there were no matching results found.
     */
    public void searchBy_PartName_or_ID(ActionEvent event) {

        // String text to lower case.
        String s = partName_or_ID.getText().toLowerCase();

        // Search by Part partial or full Name. String s (created above) passed to lookupPart.
        ObservableList<Part> part = Inventory.lookupPart(s);

        // If searching by name is empty (no results), then will try if/else to search by Part ID.
        if (part.isEmpty()) {
            try {
                int index = Integer.parseInt(s);
                Part part2 = Inventory.lookupPart(index);
                if (part2 != null) {
                    part.add(part2);
                }
                else {
                    // Pop up warning message is displayed if no results were found.
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Warning: Sorry! No results were found for Part ID or Name.");
                    alert.setContentText("Please enter a different Part ID or Name to search.");
                    alert.showAndWait();
                }
            }
            // Catch used as a "catch all" to display pop up warning message if user enters data
            // that is different from what is expected and not caught by earlier "if" and
            // "else" statements.
            catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Warning: Sorry! No results were found for Part ID or Name.");
                alert.setContentText("Please enter a different Part ID or Name to search.");
                alert.showAndWait();
            }
        }
        // Data is then set in part table (tablePart).
        tablePart.setItems(part);
    }

    /**
     * When the user clicks the Modify button in the Product table, the following brings up
     * add product scene.
     * @param event user clicks the add button.
     * @throws IOException from FMXLLoader.
     */
    public void clickProductAddButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AddProduct.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Add Product");
        stage.show();
    }

    /**
     * Similar to the Add Product button above, if user clicks the Modify button in the Product
     * table, the following brings up the Modify Product scene. Note: If user selects the
     * Modify button before selecting a product, they will see an error message pop up prompting
     * them to select a product to modify.
     * @param event user clicks the modify button.
     * @throws IOException from FMXLLoader.
     */
    public void clickProductModifyButton(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProduct.fxml"));
        loader.load();

        ModifyProductController modifyProductController = loader.getController();

        Product product = tableProduct.getSelectionModel().getSelectedItem();

        // Pop up error message if user selects Modify button before selecting a product,
        // otherwise (else) will be brought to Modify Product scene
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error: No product was selected to be modified");
            alert.setContentText("A product must be selected in order to be modified.");
            alert.showAndWait();
        }
        else {
            // Selected product is passed to ModifyProductController.
            modifyProductController.productData(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            stage.setScene(new Scene(root));
            stage.setTitle("Modify Product");
            stage.show();
        }
    }

    /**
     * Remove Product from Product table.
     * 1. User first picks the product they wish to remove.
     * 2. User then selects the Delete button.
     * 3. User is prompted with a pop-up warning to confirm they wish to delete product.
     * 4. Once user clicks "OK", product is then deleted, otherwise if they select "cancel"
     * the product and product table will remain unchanged.
     * Note: If user clicks the Delete button before picking a Product, or the product has
     * associated part, they will see a pop-up error message.
     * @param event user selects Delete button in the product table.
     * @throws IOException from FMXLLoader.
     */
    public void clickProductDeleteButton(ActionEvent event) throws IOException {

        Product product = tableProduct.getSelectionModel().getSelectedItem();

        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error: No Product was selected to be deleted.");
            alert.setContentText("A product must be selected in order to be deleted.");
            alert.showAndWait();
        }
        else {
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation");
            alert2.setHeaderText("Confirmation: Delete Product?");
            alert2.setContentText("Are you sure you want to delete product?");

            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                // If user selects OK, the product is then deleted from the inventory
                if (product.getAllProductParts().isEmpty()) {
                    Inventory.deleteProduct(product);
                }
                // If a product has associated parts, then the product cannot be deleted until
                // the associated parts are removed. If a user tries to delete a product before
                // removing associated parts, an error pop up message will display.
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Error: Product contains associated parts.");
                    alert.setContentText("A product must not have any associated parts to be deleted.");
                    alert.showAndWait();
                }
            }
            else {
                // If the user selects cancel or closes the pop-up window, then the product is
                // not deleted.
            }
        }
    }

    /**
     * Search Product by ID or partial or full name.
     * @param event user presses the enter key on their keyboard to perform product search.
     * 1. User types partial or full Product name, or Product ID, of the product they wish to search
     * for in the "Search by Part ID or Name" text field in the Product table.
     * 2. User presses the enter key on their keyboard.
     * 3. If a match or multiple matches are found, then the matching search results are displayed.
     * Note: If no matching search results are found, then a warning message box will display stating
     * that there were no matching results were found.
     */
    public void searchBy_ProductName_or_ID(ActionEvent event){

        // String text to lower case.
        String s = productName_or_ID.getText().toLowerCase();

        // Search by Part partial or full Name. String s (created above) passed to lookupPart.
        ObservableList<Product> product = Inventory.lookupProduct(s);

        // If searching by name is empty (no results), then will try if/else to search by Part ID.
        if (product.isEmpty()) {
            try {
                int index = Integer.parseInt(s);
                Product product2 = Inventory.lookupProduct(index);
                if (product2 != null) {
                    product.add(product2);
                }
                else {
                    // Pop up warning message is displayed if no results were found.
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning!");
                    alert.setHeaderText("Warning: Sorry! No results were found for Product ID or Name.");
                    alert.setContentText("Please enter a different Part ID or Name to search.");
                    alert.showAndWait();
                }
            }
            // Catch used as a "catch all" to display pop up warning message if user enters data that
            // is different from what is expected and not caught by earlier "if" and
            // "else" statements.
            catch (NumberFormatException e)
            {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Warning: Sorry! No results were found for Product ID or Name.");
                alert.setContentText("Please enter a different Part ID or Name to search.");
                alert.showAndWait();
            }
        }
        // Data is then set in Product table (tableProduct).
        tableProduct.setItems(product);
    }

    /**
     * Exits application if user clicks exit button.
     * @param event user selects the exit button.
     * @throws IOException from FXMLLoader.
     */
    public void clickExitButton(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    // The following checks to ensure that test data is loaded to MainScreen only if it
    // is the first time the MainScreen scene has been accessed.
    private static boolean firstTime = true;
    private void addTestData() {
        if(!firstTime) {
            return;
        }
        firstTime = false;

        // Part test data.
        InHouse inHousePart1 = new InHouse(Inventory.partIndex, "Fuel Cap", 3.52, 9, 0, 19, 234);
        Inventory.addPart(inHousePart1);

        Outsourced outsourcedPart1 = new Outsourced(Inventory.partIndex, "Battery", 49.75, 7, 0, 19, "Battery-Plus");
        Inventory.addPart(outsourcedPart1);

        Outsourced outsourcedPart2 = new Outsourced(Inventory.partIndex, "Muffler", 175.99, 3, 0, 6, "Best Auto Parts");
        Inventory.addPart(outsourcedPart2);

        // Product test data.
        Product product1 = new Product(Inventory.productIndex, "Fuel Tank", 899.12, 2, 0, 6);
        Inventory.addProduct(product1);

        Product product2 = new Product(Inventory.productIndex, "Radiator", 675.55, 4, 0, 14);
        Inventory.addProduct(product2);

        Product product3 = new Product(Inventory.productIndex, "Transmission", 3250.25, 3, 1, 10);
        Inventory.addProduct(product3);
    }

    /**
     * MainScreenController initialized.
     * @param url url.
     * @param resourceBundle resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Populate Part and Product tables.
        addTestData();

        tablePart.setItems(Inventory.getAllParts());
        tableProduct.setItems(Inventory.getAllProducts());

        idColumnPart.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnPart.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumnPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumnPart.setCellValueFactory(new PropertyValueFactory<>("price"));

        idColumnProduct.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnProduct.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumnProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumnProduct.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}