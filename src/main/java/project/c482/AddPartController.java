package project.c482;

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
 * Student ID: 011101737
 * Date Completed: August 9, 2023
 * Program Used: Intellij IDEA Community Edition 2023.2
 */

/**
 * AddPartController class initialized. The AddPartController adds Part data.
 */
public class AddPartController implements Initializable {

    /**
     * Stage, scene.
     */
    @FXML
    private Stage stage;
    private Scene scene;
    /**
     * TextField for Part name.
     */
    @FXML
    public TextField partName;
    /**
     * TextField for Part stock (quantity).
     */
    @FXML
    public TextField partStock;
    /**
     * TextField for Part price.
     */
    @FXML
    public TextField partPrice;
    /**
     * TextField for Part max (maximum number of part).
     */
    @FXML
    public TextField partMax;
    /**
     * TextField for Part min (minimum number of part).
     */
    @FXML
    public TextField partMin;
    /**
     * TextField for Part machine ID or company name.
     */
    @FXML
    public TextField part_MaIDCoName;
    /**
     * Text for machine ID or company name.
     */
    @FXML
    public Text maIDCoName;
    /**
     * RadioButton for InHouse.
     */
    @FXML
    public RadioButton partInHouseRadioButton;
    /**
     * RadioButton for Outsourced.
     */
    @FXML
    public RadioButton partOutSourcedRadioButton;

    /**
     * Depending on whether the inHouse or outsourced radio button is selected, the corresponding
     * text is set to either Machine ID (for in house) or Company Name (for outsourced).
     * @param event user clicks either the inHouse or outsourced button.
     */
    public void clickOutsourcedRadioButton(ActionEvent event) {
        maIDCoName.setText("Company Name");
    }
    public void clickInHouseRadioButton(ActionEvent event) {
        maIDCoName.setText("Machine ID");
    }

    /**
     * Depending on the data entered by the user, either a new part is added upon the user
     * selecting the "save" button (in which they are then returned to the Main Screen), or various
     * error messages will pop up, depending on what data is missing or is invalid.
     * @param event user selects the save button.
     * @throws IOException from FXMLLoader.
     */
    public void clickAddPartSaveButton(ActionEvent event) throws IOException {

        // If one of the fields (name, price, stock, min, max, machine ID or company name) is empty
        // (blank), user will have an error message pop up advising to complete all text fields.
        if ((partName.getText().isEmpty()) || (partPrice.getText().isEmpty()) || (partStock.getText().isEmpty()) || (partMin.getText().isEmpty()) || (partMax.getText().isEmpty()) || (part_MaIDCoName.getText().isEmpty())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error: Please ensure all text fields are filled out correctly.");
            alert.setContentText("Once all text fields are filled out, press Save to add part.");
            alert.showAndWait();
        }
        else {
            try {
                 // If the min (minimum) value is greater than max (maximum) value, then user will
                 // have an error message pop up advising that min value must be less than max value.
                if ((Integer.parseInt(partMin.getText())) >= (Integer.parseInt(partMax.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Error: Min and Max");
                    alert.setContentText("Min value must be less than Max value.");
                    alert.showAndWait();
                    return;
                }
                // If inventory amount is less than min or greater than max, the user will have an
                // error message pop up advising that inventory must be greater than or equal to min
                // and less than or equal to max.
                else if ((Integer.parseInt(partStock.getText())) < (Integer.parseInt(partMin.getText())) || (Integer.parseInt(partStock.getText())) > (Integer.parseInt(partMax.getText()))) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText("Error: Inventory Amount");
                    alert.setContentText("Inventory must follow: Min <= Inventory <= Max");
                    alert.showAndWait();
                    return;
                }
                // If no errors occur, then depending on whether the inHouse or outsourced button is
                // selected, a new inHouse or outsourced Part is added.
                else if (partInHouseRadioButton.isSelected()) {
                    InHouse inHousePart = new InHouse(Inventory.partIndex, partName.getText(), Double.parseDouble(partPrice.getText()), Integer.parseInt(partStock.getText()), Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()), Integer.parseInt(part_MaIDCoName.getText()));
                    Inventory.addPart(inHousePart);
                }
                else if (partOutSourcedRadioButton.isSelected()) {
                    Outsourced outSourcedPart = new Outsourced(Inventory.partIndex, partName.getText(), Double.parseDouble(partPrice.getText()), Integer.parseInt(partStock.getText()), Integer.parseInt(partMin.getText()), Integer.parseInt(partMax.getText()), part_MaIDCoName.getText());
                    Inventory.addPart(outSourcedPart);
                }

                // If Part is successfully added, user is brought back to Main Screen scene.
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
                alert.setContentText("Once all text fields are filled out, press Save to add part.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Bring user back to the Main Screen scene if they select the Cancel button in the Add Part controller.
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
     * AddPartController initialized.
     * @param url url.
     * @param resourceBundle resourceBundle.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}

