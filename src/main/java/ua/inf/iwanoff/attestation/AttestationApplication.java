// TODO:
// DONE 01 Horizontal scroll for the second table only
// DONE 01 equals sizes for the second table cells
// DONE 02 Bolded table headers
// DONE 03 "Requirements for the uncertainties of certified values of WRS" Satisfied / not Satisfied
// DONE 04 Show times
// DONE 05 Clean report window
// DONE 06 Show drift conclusions
// DONE 07 Update data before calculations
// 08 Add / Remove Sample
// 09 Add / Remove X
// DONE 10 Open / Save settings
// 11 Error messaging
// 12 Modification checking
// 13 Right Table restore column
// 14 Right Table resizable false
// DONE 15 Show correct common conclusions
// 16 Rollback of cell values
// DONE 17 Third button
// DONE 18 Generate and save pdf
// 19 Change icon
// 20 Customer setting mechanism
// 21 Empty "Statistical control" and "Conclusions" on third "tab"
// DONE 22 Delta character
// 23 Import TXT: UTF/Windows1251
// DONE 24 SS/WRS correct input
// DONE 25 Current file name disappears after switching language!
// DONE 26 Move customer to separate class
// 27 Print
// 28 Data edit errors (in main table)

package ua.inf.iwanoff.attestation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ua.inf.iwanoff.attestation.Controller;
import ua.inf.iwanoff.utils.GraphUtils;

import java.io.ByteArrayInputStream;
import java.util.Locale;

public class AttestationApplication extends Application {
    private Parent rootNode;

    @Override
    public void init() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        rootNode = loader.load();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Controller.setPrimaryStage(primaryStage);
        primaryStage.setOnShowing(Controller::loadOptions);
        Scene scene = new Scene(rootNode, 1295, 700);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.getIcons().add(GraphUtils.getImage("/MainIcon.png"));
        primaryStage.setOnCloseRequest(Controller::closeCheck);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        Controller.stop();
    }

    public static void main(String[] args) {
        Locale.setDefault(new Locale("EN"));
        launch(args);
    }
}
