package ua.inf.iwanoff.attestation.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import ua.inf.iwanoff.attestation.utils.MultiString;
import static ua.inf.iwanoff.attestation.view.Strings.msError;

public class WindowUtils {
    
    public static void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(msError.toString());
        alert.setHeaderText(message);
        //alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
 
    public static void showWarning(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void showError(MultiString message) {
        showError(message.toString());
    }

    public static void showMessage(MultiString message) {
        showMessage(message.toString());
    }

    public static void showWarning(MultiString message) {
        showWarning(message.toString());
    }
   

    public static class Editor extends Stage {
        private String text;
        
        public Editor(String title, String txt, String ok, String cancel) {
            text = txt;
            setTitle(title);
            BorderPane borderPane = new BorderPane();
            Scene scene = new Scene(borderPane, 400, 300);
            setScene(scene);
            TextArea textArea = new TextArea(text);
            FlowPane flowPane = new FlowPane(10, 10);
            flowPane.setPadding(new Insets(10, 10, 10, 10));
            flowPane.setAlignment(Pos.CENTER);
            Button buttonOK = new Button(ok);
            buttonOK.setOnAction(event -> { text = textArea.getText(); close(); });
            Button buttonCancel = new Button(cancel);
            buttonCancel.setOnAction(event -> { text = null; close(); });
            flowPane.getChildren().addAll(buttonOK, buttonCancel);
            borderPane.setCenter(textArea);
            borderPane.setBottom(flowPane);        
        }

        public String getText() {
            return text;
        }
        
        
    }

    public static void clearText(TextInputControl... controls) {
        for (TextInputControl control : controls) {
            control.setText("");
        }
    }
    
}
