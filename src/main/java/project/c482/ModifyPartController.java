package project.c482;

import javafx.scene.control.ToggleGroup;
import project.c482.InHouse;
import project.c482.Inventory;
import project.c482.Outsourced;
import project.c482.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * @author Joshua James
 *  * Student ID: 011101737
 *  * Date Completed: August 9, 2023
 *  * Program Used: Intellij IDEA Community Edition 2023.2
 */

/**
 * ModifyPartController class initialized. The ModifyPartController class modifies Part data.
 */
public class ModifyPartController implements Initializable {

    /**
     * Stage, scene.
     */
    @FXML
    private Stage stage;
    private Scene scene;

    /**
     * Text field for part ID.
     */
    @FXML
    private TextField modifyPartId;
    /**
     * Text field for part name.
     */
    @FXML
    private TextField modifyPartName;
    /**
     * Text field for part stock (quantity, inventory).
     */
    @FXML
    private TextField modifyPartStock;
    /**
     * Text field for part price.
     */
    @FXML
    private TextField modifyPartPrice;
    /**
     * Text field for part min (maximum number of part).
     */
    @FXML
    private TextField modifyPartMin;
    /**
     * Text field for part max (maximum number of part).
     */
    @FXML
    private TextField modifyPartMax;
    /**
     * Text field for machine ID or company name.
     */
    @FXML
    private TextField modifyPart_MaIDCoName;
    /**
     * Text for machine ID or company name.
     */
    public Text maIDCoName;
    /**
     * Modify part screen radio toggle group
     */
    public ToggleGroup InHouse_or_outsourced;
    /**
     * Radio button for InHouse.
     */
    @FXML
    private RadioButton partInHouseRadioButton;
    /**
     * Radio button for Outsourced.
     */
    @FXML
    private RadioButton partOutsourcedRadioButton;

    /**
     * Depending on whether outsourced or inHouse radio button is selected, Text is set to
     * Company Name (for outsourced) or Machine ID (for in house).
     *
     * @param actionEvent user selects either the inHouse or outsourced button.
     */
    public void clickOutsourcedRadioButton(ActionEvent actionEvent) {
        maIDCoName.setText("Company Name");
    }

    public void clickInHouseRadioButton(ActionEvent actionEvent) {
        maIDCoName.setText("Machine ID");
    }

    /**
     * Selected part data sent and set from MainScreenController to ModifyPartController.
     *
     * @param part part data.
     */
    public void partData(Part part) {
        modifyPartId.setText(String.valueOf(part.getId()));
        modifyPartName.setText(part.getName());
        modifyPartStock.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMin.setText(String.valueOf(part.getMin()));
        modifyPartMax.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse) {
            partInHouseRadioButton.setSelected(true);
            maIDCoName.setText("Machine ID");
            modifyPart_MaIDCoName.setText(String.valueOf(((InHouse) part).getMachineId()));
        } else {
            partOutsourcedRadioButton.setSelected(true);
            maIDCoName.setText("Company Name");
            modifyPart_MaIDCoName.setText(((Outsourced) part).getCompanyName());
        }
    }
    /**
     * Depending on the data entered by the user, either the part is modified upon the user
     * selecting the save button (in which they are returned to the Main Screen scene), or various
     * error messages will pop up, depending on what data is missing or is invalid.
     *
     * @param event user selects the save button.
     * @throws IOException from FXMLLoader.
     */
    @FXML
    void clickModifyPartSaveButton(ActionEvent event) throws IOException {
        try {
            int partId = Integer.parseInt(modifyPartId.getText());
            String name = modifyPartName.getText();
            int inventoryStock = Integer.parseInt(modifyPartStock.getText());
            double price = Double.parseDouble(modifyPartPrice.getText());
            int maximum = Integer.parseInt(modifyPartMax.getText());
            int minimum = Integer.parseInt(modifyPartMin.getText());
            int machineId;
            String companyName;

            // If one of the fields (name, price, stock, min, max, machine ID or company name) is empty (blank),
            // user will have an error message pop up advising to complete all text fields.
            if ((modifyPartName.getText().isEmpty()) || (modifyPartPrice.getText().isEmpty()) || (modifyPartStock.getText().isEmpty()) || (modifyPartMin.getText().isEmpty()) || (modifyPartMax.getText().isEmpty()) || (modifyPart_MaIDCoName.getText().isEmpty())) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Error: Please ensure all text fields are filled out correctly.");
                alert.setContentText("Once all text fields are filled out, press Save to modify part.");
                alert.showAndWait();
            }
            // If the min (minimum) value is greater than max (maximum) value, then user will
            // have an error message pop up advising that min value must be less than max value.
            else if ((Integer.parseInt(modifyPartMin.getText())) >= (Integer.parseInt(modifyPartMax.getText()))) {
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
            else if ((Integer.parseInt(modifyPartStock.getText())) < (Integer.parseInt(modifyPartMin.getText())) || (Integer.parseInt(modifyPartStock.getText())) > (Integer.parseInt(modifyPartMax.getText()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setHeaderText("Error: Inventory Amount");
                alert.setContentText("Inventory must follow: Min <= Inventory <= Max");
                alert.showAndWait();
                return;
            }
            if (partInHouseRadioButton.isSelected()) {
                machineId = Integer.parseInt(modifyPart_MaIDCoName.getText());

                Inventory.updatePart(partId, new InHouse(partId, name, price, inventoryStock, minimum, maximum, machineId));
            }
            if (partOutsourcedRadioButton.isSelected()) {
                companyName = modifyPart_MaIDCoName.getText();

                Inventory.updatePart(partId, new Outsourced(partId, name, price, inventoryStock, minimum, maximum, companyName));
            }

            // If Part is successfully modified, user is brought back to Main Screen scene.
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inventory Management System");
            stage.show();
        }
        // Catch used as a "catch all" to display pop up error message if user enters data that
        // is different from what is expected and not caught by earlier "if" and
        // "else if" statements.
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error: Please ensure all text fields are filled out correctly.");
            alert.setContentText("Once all text fields are filled out, press Save to modify part.");
            alert.showAndWait();
        }
    }

    /**
     * Brings user back to the Main Screen scene if they select the cancel button on the Modify Part scene.
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
     * ModifyPartController initialized.
     * @param url url.
     * @param resourceBundle resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}