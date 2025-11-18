module ua.inf.iwanoff.attestation {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires jakarta.xml.bind;
    requires java.datatransfer;
    requires itext;
    requires java.desktop;


    opens ua.inf.iwanoff.attestation to javafx.fxml;
    exports ua.inf.iwanoff.attestation;
}