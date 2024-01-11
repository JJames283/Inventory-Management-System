package project.c482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * RUNTIME ERROR: I encountered a "InvocationTargetException" that prevented the application from
 * launching. After much searching, I found it was simply a small typo in the fx:controller
 * for one of the FXML files causing this error. Once the typo was fixed, application launched
 * as expected.
 * FUTURE ENHANCEMENT: Currently, the application only allows a user to delete one part or product
 * at a time, therefore a simple, but time saving future enhancement would be to allow for
 * multiple parts or products to be deleted all at once.
 * JAVADOC FILES LOCATION: After unzipping folder, the Javadoc files are at /c482/Javadoc.
 *
 * @author Joshua James
 * Student ID: 011101737
 * Date Completed: August 9, 2023
 * Program: Intellij IDEA Community Edition 2023.2
 */


/**
 * Main class extends Application class.
 */
public class Main extends Application {
    /**
     * Main class loads MainScreen scene when program is first opened.
     * @param stage main stage of the application.
     * @throws IOException from FXMLLoader.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainScreen.fxml")));
        Scene scene = new Scene(root, 1100, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * main method.
     * @param args arguments.
     */
    public static void main(String[] args) {launch();}
}