package ua.inf.iwanoff.attestation.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ua.inf.iwanoff.attestation.model.OptionsData;

import java.util.Optional;

import static ua.inf.iwanoff.attestation.view.Strings.*;

public class OptionsWindow {
    private int language;
    private CheckBox checkBoxVariancesEquality;
    private CheckBox checkBoxSamplesHomogeneity;
    private CheckBox checkBoxDrift;
    private HBox paneButtons;
    private Button buttonOK;
    private Button buttonCancel;
    private Label labelUseStudentsTest;
    private RadioButton radioButtonOneSided;
    private RadioButton radioButtonTwoSided;
    OptionsData optionsData;

    public OptionsWindow(int language) {
        super();
        this.language = language;
        checkBoxVariancesEquality = new CheckBox(msCalcVariancesEquality.get(language));
        checkBoxVariancesEquality.setPadding(new Insets(5, 10, 5, 10));
        checkBoxSamplesHomogeneity = new CheckBox(msCalcSamplesHomogeneity.get(language));
        checkBoxSamplesHomogeneity.setPadding(new Insets(5, 10, 5, 10));
        checkBoxDrift = new CheckBox(msCalcDrift.get(language));
        checkBoxDrift.setPadding(new Insets(5, 10, 5, 10));
        labelUseStudentsTest = new Label(msUseStudent_sTest.get(language));
        labelUseStudentsTest.setPadding(new Insets(20, 10, 5, 10));
        radioButtonOneSided = new RadioButton(msOKS.get(language));
        radioButtonOneSided.setPadding(new Insets(5, 10, 5, 30));
        radioButtonTwoSided = new RadioButton(msTwoSidedStudent_sTTest.get(language));
        radioButtonTwoSided.setPadding(new Insets(5, 10, 5, 30));
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButtonOneSided.setToggleGroup(toggleGroup);
        radioButtonTwoSided.setToggleGroup(toggleGroup);
        paneButtons = new HBox();
        buttonOK = new Button("");
        buttonCancel = new Button("Cancel");
    }

    public OptionsData show1(OptionsData optionsData) {
        VBox pane = new VBox();
        pane.getChildren().add(checkBoxVariancesEquality);
        pane.getChildren().add(checkBoxSamplesHomogeneity);
        pane.getChildren().add(checkBoxDrift);
        Stage stage = new Stage();
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setMinWidth(440);
        stage.setMinHeight(240);
        stage.initOwner(null);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setTitle(msOptions.get(language));
        stage.showAndWait();
        return optionsData;
    }

    public OptionsData show(OptionsData optionsData) {
        this.optionsData = optionsData;
        Dialog<OptionsData> dialog = new Dialog<>();
        VBox pane = new VBox();
        checkBoxVariancesEquality.setSelected(optionsData.isVariancesEquality());
        checkBoxSamplesHomogeneity.setSelected(optionsData.isSamplesHomogeneity());
        checkBoxDrift.setSelected(optionsData.isDrift());
        radioButtonOneSided.setSelected(optionsData.getSides() == OptionsData.OneTwo.ONE);
        radioButtonTwoSided.setSelected(optionsData.getSides() == OptionsData.OneTwo.TWO);
        pane.getChildren().add(checkBoxVariancesEquality);
        pane.getChildren().add(checkBoxSamplesHomogeneity);
        pane.getChildren().add(checkBoxDrift);
        pane.getChildren().add(labelUseStudentsTest);
        pane.getChildren().add(radioButtonOneSided);
        pane.getChildren().add(radioButtonTwoSided);
        dialog.getDialogPane().setContent(pane);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.setResultConverter(this::getResult);
        Optional<OptionsData> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Variances Equality : " + result.get().isVariancesEquality());
            System.out.println("Samples Homogeneity: " + result.get().isSamplesHomogeneity());
            System.out.println("Drift              : " + result.get().isDrift());
            System.out.println("Sides              : " + result.get().getSides());
        }
        return optionsData;
    }

    private OptionsData getResult(ButtonType buttonType) {
        if (buttonType.getButtonData().isDefaultButton()) {
            optionsData.setVariancesEquality(checkBoxVariancesEquality.isSelected());
            optionsData.setSamplesHomogeneity(checkBoxSamplesHomogeneity.isSelected());
            optionsData.setDrift(checkBoxDrift.isSelected());
            optionsData.setSides(radioButtonOneSided.isSelected() ? OptionsData.OneTwo.ONE : OptionsData.OneTwo.TWO);
            /////
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Look, an Information Dialog");
                alert.setContentText("Drift " + optionsData.isDrift());
                alert.showAndWait();
            }
            /////
        }
        return optionsData;
    }

    public boolean isVariancesEquality() {
        return checkBoxVariancesEquality.isSelected();
    }

    public void setVariancesEquality(boolean variancesEquality) {
        checkBoxVariancesEquality.setSelected(variancesEquality);
    }

    public boolean isSamplesHomogeneity() {
        return checkBoxSamplesHomogeneity.isSelected();
    }

    public void setSamplesHomogeneity(boolean samplesHomogeneity) {
        checkBoxSamplesHomogeneity.setSelected(samplesHomogeneity);
    }

    public boolean isDrift() {
        return checkBoxDrift.isSelected();
    }

    public void setDrift(boolean drift) {
        checkBoxDrift.setSelected(drift);
    }
}
