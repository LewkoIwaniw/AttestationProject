package ua.inf.iwanoff.attestation.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ua.inf.iwanoff.attestation.model.OptionsData;

import java.util.Optional;

import static ua.inf.iwanoff.attestation.view.Strings.*;

public class OptionsWindow {
    private final CheckBox checkBoxVariancesEquality;
    private final CheckBox checkBoxSamplesHomogeneity;
    private final CheckBox checkBoxDrift;
    private final Label labelUseStudentsTest;
    private final RadioButton radioButtonOneSided;
    private final RadioButton radioButtonTwoSided;
    private OptionsData optionsData;

    public OptionsWindow(int language) {
        super();
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
    }

    public OptionsData show(OptionsData optionsData) {
        this.optionsData = optionsData;
        Dialog<OptionsData> dialog = new Dialog<>();
        dialog.setTitle(msOptions.toString());
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
        return optionsData;
    }

    private OptionsData getResult(ButtonType buttonType) {
        if (buttonType.getButtonData().isDefaultButton()) {
            optionsData.setVariancesEquality(checkBoxVariancesEquality.isSelected());
            optionsData.setSamplesHomogeneity(checkBoxSamplesHomogeneity.isSelected());
            optionsData.setDrift(checkBoxDrift.isSelected());
            optionsData.setSides(radioButtonOneSided.isSelected() ? OptionsData.OneTwo.ONE : OptionsData.OneTwo.TWO);
        }
        return optionsData;
    }
}
