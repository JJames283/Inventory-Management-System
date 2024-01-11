package project.c482;

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
 * ModifyProductController class initialized. The ModifyProductController class modifies Product data.
 */
public class ModifyProductController implements Initializable {

    /**
     * Stage, scene.
     */
    @FXML
    private Stage stage;
    private Scene scene;

    /**
     * Table view for tablePart table.
     */
    public TableView<Part> tablePart;
    /**
     * Table columns for Part ID.
     */
    public TableColumn idColumnPart;
    /**
     * Table columns for Part name.
     */
    public TableColumn nameColumnPart;
    /**
     * Table columns for Part price.
     */
    public TableColumn priceColumnPart;
    /**
     * Table columns for Part stock.
     */
    public TableColumn stockColumnPart;

    /**
     * Text field for product ID.
     */
    @FXML
    private TextField modifyProductId;
    /**
     * Text field for product name.
     */
    @FXML
    private TextField modifyProductName;
    /**
     * Text field for product stock (quantity, inventory).
     */
    @FXML
    private TextField modifyProductStock;
    /**
     * Text field for product price.
     */
    @FXML
    private TextField modifyProductPrice;
    /**
     * Text field for product min (minimum number of product).
     */
    @FXML
    private TextField modifyProductMin;
    /**
     * Text field for product max (maximum number of product).
     */
    @FXML
    private TextField modifyProductMax;
    /**
     * Text field for search by part name or ID.
     */
    @FXML
    public TextField partName_or_ID;

    /**
     * table view, and table columns for tableProductPart table.
     */
    private final ObservableList<Part> productPart = FXCollections.observableArrayList();
    /**
     * table view for tableProductPart table.
     */
    public TableView<Part> tableProductPart;
    /**
     * Table column for Product Part (associated part to product) ID.
     */
    public TableColumn idColumnProductPart;
    /**
     * Table column for Product Part (associated part to product) name.
     */
    public TableColumn nameColumnProductPart;
    /**
     * Table column for Product Part (associated part to product) price.
     */
    public TableColumn priceColumnProductPart;
    /**
     * Table column for Product Part (associated part to product) stock.
     */
    public TableColumn stockColumnProductPart;

    /**
     * Part product data (passed from MainScreenController) and associated part data (passed from
     * AddProductController) is passed and set to ModifyPartController.
     * @param product product data.
     */
    public void productData(Product product) {
        modifyProductId.setText(String.valueOf(product.getId()));
        modifyProductName.setText(product.getName());
        modifyProductStock.setText(String.valueOf(product.getStock()));
        modifyProductPrice.setText(String.valueOf(product.getPrice()));
        modifyProductMin.setText(String.valueOf(product.getMin()));
        modifyProductMax.setText(String.valueOf(product.getMax()));

        // Product Part table is initialized and populated with associated parts.
        tableProductPart.setItems(product.getAllProductParts());
        productPart.addAll(product.getAllProductParts());
    }


    /**
     * Depending on the data entered by the user, either the product is modified upon the user
     * selecting the save button (in which they are returned to the Main Screen scene), or various
     * error messages will pop up, depending on what data is missing or is invalid.
     * @param event user selects the save button.
     * @throws IOException from FXMLLoader.
     */
    public void clickModifyProductSaveButton(ActionEvent event) throws IOException {

        // If one of the fields (name, price, stock, min, max, machine ID or company name) is empty (blank),
        // user will have an error message pop up advising to complete all text fields.
        if ((modifyProductName.getText().isEmpty()) || (modifyProductPrice.getText().isEmpty()) || (modifyProductStock.getText().isEmpty()) || (modifyProductMin.getText().isEmpty()) || (modifyProductMax.getText().isEmpty())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error: Please ensure all text fields are filled out correctly.");
            alert.setContentText("Once all text fields are filled out, press Save to modify product.");
            alert.showAndWait();
        }
        else {
            try {
                // If the min (minimum) value is greater than max (maximum) value, then user will
                // have an error message pop up advising that min value must be less than max value.
                if ((Integer.parseInt(modifyProductMin.getText())) >= (Integer.parseInt(modifyProductMax.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Error: Min and Max");
                    alert.setContentText("Min must be less than Max.");
                    alert.showAndWait();
                    return;
                }
                // If inventory amount is less than min or greater than max, the user will have an
                // error message pop up advising that inventory must be greater than or equal to min
                // and less than or equal to max.
                else if ((Integer.parseInt(modifyProductStock.getText())) < (Integer.parseInt(modifyProductMin.getText())) || (Integer.parseInt(modifyProductStock.getText())) > (Integer.parseInt(modifyProductMax.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Error: Inventory Amount");
                    alert.setContentText("Inventory must follow: Min <= Inventory <= Max");
                    alert.showAndWait();
                    return;
                }

                // Ensures Product data is added.
                Product product = new Product(Integer.parseInt(modifyProductId.getText()), modifyProductName.getText(), Double.parseDouble(modifyProductPrice.getText()), Integer.parseInt(modifyProductStock.getText()), Integer.parseInt(modifyProductMin.getText()), Integer.parseInt(modifyProductMax.getText()));
                Inventory.updateProduct(Integer.parseInt(modifyProductId.getText()), product);

                // The observable list from this controller (ModifyProductController) is copied to
                // the Product's controller observable list.
                for (Part productPart : productPart) {
                    product.addProductPart(productPart);
                }

                // If product is successfully modified, user is brought back to Main Screen scene.
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Inventory Management System");
                stage.show();
            }
            // Catch used as a "catch all" to display pop up error message if user enters data that
            // is different from what is expected and not caught by earlier "if" and
            // "else if" statements
            catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Error: Please ensure all text fields are filled out correctly!");
                alert.setContentText("Once all text fields are filled out, press Save to modify product.");
                alert.showAndWait();
            }
        }
    }


    /**
     * Add part to product (associated part table).
     * @param event user selects the add button to add associated part to product.
     * 1. User first picks a part to add.
     * 2. User then presses the Add button.
     * 3. The part that the user picked is then added to the product's associated part table.
     * Note: If user presses the Remove Associated Part button before picking a part first, a pop-up
     * error message will display.
     */
    public void clickAssociatedPartAddButton(ActionEvent event) {

        Part part = tablePart.getSelectionModel().getSelectedItem();

        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error: No part was selected to be added.");
            alert.setContentText("Please ensure a part is selected first.");
            alert.showAndWait();
        }
        else {
            productPart.add(part);
            tableProductPart.setItems(productPart);
        }
    }

    /**
     * Remove part from product (associated part table).
     * @param event user selects the remove button to remove associated part from product.
     * 1. User first picks an associated part to remove.
     * 2. User then presses the Remove Associated Part button.
     * 3. The part that the user picked is then removed from the product's associated parts table.
     * Note: If user presses the Remove Associated Part button before picking a part first, a pop-up
     * error message will occur.
     */
    public void clickAssociatedPartRemoveButton(ActionEvent event) {

        Part part = tableProductPart.getSelectionModel().getSelectedItem();

        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error: No Part was selected to be removed");
            alert.setContentText("A part must be selected in order to be removed.");
            alert.showAndWait();
        }
        else {
            // User must confirm they wish to remove part before part is removed.
            Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
            alert2.setTitle("Confirmation");
            alert2.setHeaderText("Confirmation: Remove Part?");
            alert2.setContentText("Are you sure you want to remove this part?");

            Optional<ButtonType> result = alert2.showAndWait();
            if (result.get() == ButtonType.OK) {
                // If user confirms by selecting "OK", the Part is then removed from the product's
                // associated part table.
                productPart.remove(part);
                tableProductPart.setItems(productPart);
            }
            else {
                // If the user selects cancel or closes the confirmation pop-up window, then the
                // associated part is not removed from the product and the product's
                // associated part table is not changed.
            }
        }
    }


    /**
     * Search Part by ID or partial or full name.
     * @param event user presses the enter key on their keyboard to perform part search.
     * 1. User types partial or full Part name, or Part ID, of the part they wish to search for in
     * the "Search by Part ID or Name" text field.
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
                    alert.setHeaderText("Warning: Sorry! No results were found for Part ID or Name");
                    alert.setContentText("Please enter a different Part ID or Name to search.");
                    alert.showAndWait();
                }
            }
            // Catch used as a "catch all" to display pop up warning message if user enters data that
            // is different from what is expected and not caught by earlier "if" and
            // "else" statements.
            catch(NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Warning: Sorry! No results were found for Part ID or Name");
                alert.setContentText("Please enter a different Part ID or Name to search.");
                alert.showAndWait();
            }
        }
        // Data is set in tablePart table.
        tablePart.setItems(part);
    }

    /**
     * Brings user back to the Main Screen scene if they select the cancel button on the Modify
     * Products scene.
     * @param event user selects the cancel button.
     * @throws IOException from FXMLLoader.
     */
    public void clickCancelButtonGotoMainScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Management System");
        stage.show();
    }

    /**
     * ModifyProductController initialized.
     * @param url url.
     * @param resourceBundle resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Populates Part table.
        tablePart.setItems(Inventory.getAllParts());

        idColumnPart.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnPart.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumnPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumnPart.setCellValueFactory(new PropertyValueFactory<>("price"));

        idColumnProductPart.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnProductPart.setCellValueFactory(new PropertyValueFactory<>("name"));
        stockColumnProductPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceColumnProductPart.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
