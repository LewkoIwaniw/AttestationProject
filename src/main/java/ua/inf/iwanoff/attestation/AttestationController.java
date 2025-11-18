package ua.inf.iwanoff.attestation;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AttestationController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
