module ua.inf.iwanoff.attestation {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jakarta.xml.bind;
    requires java.datatransfer;
    requires itext;
    requires java.desktop;


    opens ua.inf.iwanoff.attestation to javafx.fxml, jakarta.xml.bind;
    opens ua.inf.iwanoff.attestation.model to javafx.fxml, jakarta.xml.bind;
    exports ua.inf.iwanoff.attestation;
}