package ua.inf.iwanoff.attestation.controller;

// TODO:
// dynamically change table sizeSorted so scroll bars will be unnecessary
// rename multistrings

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ua.inf.iwanoff.attestation.model.*;
import ua.inf.iwanoff.attestation.view.OptionsWindow;
import ua.inf.iwanoff.utils.CellData;
import ua.inf.iwanoff.utils.ColumnData;
import ua.inf.iwanoff.utils.MultiString;

import jakarta.xml.bind.JAXBException;

import static ua.inf.iwanoff.attestation.view.Strings.*;
import static ua.inf.iwanoff.utils.GraphUtils.getImageView;
import static ua.inf.iwanoff.utils.StringUtils.dashAndName;
import static ua.inf.iwanoff.utils.WindowUtils.*;
import static ua.inf.iwanoff.utils.MultiString.*;
import static ua.inf.iwanoff.utils.StringUtils.getPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public static final boolean OPEN_DIALOG = true;
    public static final boolean SAVE_DIALOG = false;

    private ContextMenu contextMenu;

    public static final int FIXED_ROWS = 4;

    private static Stage primaryStage;
    private static Controller controller;
    private static Options options = new Options();

    @FXML private Menu menuFile;
    @FXML private MenuItem menuItemFileNew;
    @FXML private MenuItem menuItemFileOpen;
    @FXML private MenuItem menuItemFileImportTxt;
    @FXML private MenuItem menuItemFileSave;
    @FXML private MenuItem menuItemFileSaveAs;
    @FXML private MenuItem menuItemFileSaveReport;
    @FXML private MenuItem menuItemFileClose;

    @FXML private Menu menuEdit;
    @FXML private MenuItem menuItemEditCut;
    @FXML private MenuItem menuItemEditCopy;
    @FXML private MenuItem menuItemEditPaste;

    @FXML private Menu menuRun;
    @FXML private MenuItem menuItemRunA;
    @FXML private MenuItem menuItemRunB;
    @FXML private MenuItem menuItemRunC;

    @FXML private Menu menuOptions;
    @FXML private Menu menuOptionsLanguage;
    @FXML private RadioMenuItem menuItemOptionsLanguageEnglish;
    @FXML private RadioMenuItem menuItemOptionsLanguageUkrainian;
    @FXML private RadioMenuItem menuItemOptionsLanguageRussian;
    @FXML private MenuItem menuItemOptionsPreferences;

    @FXML private Menu menuHelp;
    @FXML private MenuItem menuItemHelpAbout;

    @FXML private Label labelWRSName;
    @FXML private Label labelOperator;
    @FXML private Label labelStuff;
    @FXML private Label labelAmount;
    @FXML private Label labelOfficialStandardSample;
    @FXML private Label labelWrsDocument;
    @FXML private Label labelDate;
    @FXML private Label labelXPSS;
    @FXML private Label labelDeltaWRS;
    @FXML private Label labelNumber;

    @FXML private TextField textFieldWRSName;
    @FXML private TextField textFieldOperator;
    @FXML private TextField textFieldStuff;
    @FXML private TextField textFieldAmount;
    @FXML private TextField textFieldOfficialStandardSample;
    @FXML private TextField textFieldWrsDocument;
    @FXML private TextField textFieldDate;
    @FXML private TextField textFieldXPSS;
    @FXML private TextField textFieldDeltaWRS;
    @FXML private TextField textFieldNumber;

    @FXML private Label labelWeightsDilutions;
    @FXML private TextArea textAreaWeightsDilutions;
    @FXML private Label labelSamples;
    @FXML private ScrollPane scrollPaneTables;
    @FXML private AnchorPane anchorPaneTables;

    @FXML private TableView tableViewLeft;
    @FXML private TableView tableViewRight;

    @FXML private Pane paneRight;
    @FXML private Button buttonC;
    @FXML private Button buttonB;
    @FXML private Button buttonA;
    @FXML private AnchorPane webViewPane;
    private WebView webView;

    @FXML private CheckBox checkBoxTimes;
    @FXML private Button buttonSaveReport;

    private static final AttestationProcessor processor = AttestationProcessor.getInstance();
    private LeftTableModel leftTableModel = new LeftTableModel(processor);
    private RightTableModel rightTableModel = new RightTableModel(processor);
    public static String currentFileName = "";
    private final InvalidationListener leftSelectListener = this::synchronizeWithRight;
    private final InvalidationListener rightSelectListener = this::synchronizeWithLeft;

    public static void setPrimaryStage(Stage primaryStage) {
        Controller.primaryStage = primaryStage;
        controller.switchLanguage();
    }

    private void switchLanguage() {
        setText(menuFile, msMFile);
        setText(menuItemFileNew, msMNew);
        setText(menuItemFileOpen, msMOpen);
        setText(menuItemFileImportTxt, msMImport);
        setText(menuItemFileSave, msMSave);
        setText(menuItemFileSaveAs, msMSaveAs);
        setText(menuItemFileSaveReport, msMReport);
        setText(menuItemFileClose, msMExit);
        setText(menuEdit, msMEdit);
        setText(menuItemEditCut, msMCut);
        setText(menuItemEditCopy, msMCopy);
        setText(menuItemEditPaste, msMPaste);
        menuRun.setText(msMRun.toString());
        menuItemRunA.setText(msUAZRSO.toString());
        menuItemRunB.setText(msUAZPORSO.toString());
        menuItemRunC.setText(msPORSO.toString());
        menuOptions.setText(msMOptions.toString());
        menuOptionsLanguage.setText(msMLanguage.toString());
        menuItemOptionsPreferences.setText(msMPreferences.toString());
        menuHelp.setText(msMHelp.toString());
        menuItemHelpAbout.setText(msMAbout.toString());
        labelWRSName.setText(msName.toString());
        labelOperator.setText(msOperator.toString());
        labelStuff.setText(msStuff.toString());
        labelAmount.setText(msAmountOfSubstance.toString());
        labelOfficialStandardSample.setText(msOfficialReferenceSample.toString());
        labelWrsDocument.setText(msWrsDocument.toString());
        labelDate.setText(msTestDate.toString());
        labelXPSS.setText(msCertifiedValueOfRS.toString());
        labelDeltaWRS.setText(msDeltaWRS.toString());
        labelNumber.setText(msProtocolNumber.toString());
        labelWeightsDilutions.setStyle("-fx-font-weight: bold;");
        labelWeightsDilutions.setText(msNO.toString());
        labelSamples.setStyle("-fx-font-weight: bold;");
        labelSamples.setText(msSamples.toString());
        buttonA.setText(msUAZRSO.toString());
        buttonB.setText(msUAZPORSO.toString());
        buttonC.setText(msPORSO.toString());
        checkBoxTimes.setText(msShowTimeValues.toString());
        buttonSaveReport.setText(msSaveReport.toString());
        if (primaryStage != null) {
            if (currentFileName != null && !currentFileName.isEmpty()) {
                primaryStage.setTitle(msWrsAttestation + " - " + currentFileName);
            }
            else {
                primaryStage.setTitle(msWrsAttestation.toString());
            }
        }
        createPopupMenu();
        //createTables();
        updateTables();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        processor.setReactFunc(this::updateState);
        buttonA.prefWidthProperty().bind(paneRight.widthProperty().divide(3).subtract(5));
        buttonB.layoutXProperty().bind(paneRight.widthProperty().divide(3).add(3));
        buttonB.prefWidthProperty().bind(paneRight.widthProperty().divide(3).subtract(5));
        buttonC.layoutXProperty().bind(paneRight.widthProperty().divide(1.5).add(1));
        buttonC.prefWidthProperty().bind(paneRight.widthProperty().divide(3).subtract(5));
        addListener(this::modifyText, textFieldWRSName, textFieldOperator, textFieldStuff, textFieldAmount,
                textFieldOfficialStandardSample, textFieldWrsDocument, textFieldDate, textFieldXPSS,
                textFieldDeltaWRS, textFieldNumber, textAreaWeightsDilutions);
        controller = this;
        tableViewLeft.setFixedCellSize(22);
        tableViewRight.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY); // No influence!
        tableViewLeft.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY); // No influence!
        scrollPaneTables.widthProperty().addListener(this::changeScrollBarWidth);
        Platform.runLater(this::addWebView);
    }

    private void modifyText(Observable observable, String oldValue, String newValue) {
        processor.removeState(AttestationProcessor.STATE_SAVED);
        StringProperty property = (StringProperty) observable;
        if (property.getBean().equals(textFieldWRSName)) {
            data().setSSName(newValue);
            return;
        }
        if (property.getBean().equals(textFieldOperator)) {
            data().setOperator(newValue);
            return;
        }
        if (property.getBean().equals(textFieldStuff)) {
            data().setStuff(newValue);
            return;
        }
        if (property.getBean().equals(textFieldAmount)) {
            data().setAmount(newValue);
            return;
        }
        if (property.getBean().equals(textFieldOfficialStandardSample)) {
            data().setOfficialStandardSample(newValue);
            return;
        }
        if (property.getBean().equals(textFieldWrsDocument)) {
            data().setDocument(newValue);
            return;
        }
        if (property.getBean().equals(textFieldDate)) {
            data().setDate(newValue);
            return;
        }
        if (property.getBean().equals(textFieldXPSS)) {
            data().setXPSS(newValue);
            return;
        }
        if (property.getBean().equals(textFieldXPSS)) {
            if (!processor.setXPSS(newValue)) {
                showError(msWrongValueOfXPSS, msError);
                textFieldXPSS.setText(oldValue);
            }
            return;
        }
        if (property.getBean().equals(textFieldDeltaWRS)) {
            if (!processor.setDeltaWRS(newValue)) {
                showError(msWrongValueOfDeltaWRS, msError);
                textFieldDeltaWRS.setText(oldValue);
            }
            return;
        }
        if (property.getBean().equals(textAreaWeightsDilutions)) {
            data().setWeightsDilutions(newValue);
        }
        if (property.getBean().equals(textFieldNumber)) {
            data().setProtocol(newValue);
        }
    }

    private void updateState() {
        menuItemFileSaveReport.setDisable(!processor.isCalculated());
        buttonSaveReport.setDisable(!processor.isCalculated());
    }

    private void createPopupMenu() {
        MenuItem addSampleItem = new MenuItem(msAddSample.toString());
        addSampleItem.setOnAction(this::addSample);
        MenuItem removeSampleItem = new MenuItem(msRemoveSample.toString());
        removeSampleItem.setOnAction(this::removeSample);
        MenuItem addXItem = new MenuItem(msAdd + " X");
        addXItem.setOnAction(event -> addRow());
        MenuItem removeXItem = new MenuItem(msRemove + " X");
        removeXItem.setOnAction(this::removeRow);
        contextMenu = new ContextMenu();
        contextMenu.getItems().addAll(addSampleItem, removeSampleItem, addXItem, removeXItem);
        tableViewLeft.setContextMenu(contextMenu);
    }

    private void addSample(ActionEvent actionEvent) {
        processor.addSample(processor.getData().size());
        updateTables();
    }

    private void removeSample(ActionEvent actionEvent) {
        if (!showPrompt(msLastSampleWillBeDeleted)) {
            return;
        }
        processor.removeSample(processor.getData().size() - 1);
        updateTables();
    }

    private void addSelectionListeners() {
        tableViewLeft.getSelectionModel().selectedItemProperty().addListener(leftSelectListener);
        tableViewRight.getSelectionModel().selectedItemProperty().addListener(rightSelectListener);
    }

    private void removeSelectionListeners() {
        tableViewLeft.getSelectionModel().selectedItemProperty().removeListener(leftSelectListener);
        tableViewRight.getSelectionModel().selectedItemProperty().removeListener(rightSelectListener);
    }

    private void synchronizeWithRight(Observable observable) {
        removeSelectionListeners();
        synchronizeTables(tableViewLeft, tableViewRight);
        addSelectionListeners();
    }

    private void synchronizeWithLeft(Observable observable) {
        removeSelectionListeners();
        synchronizeTables(tableViewRight, tableViewLeft);
        addSelectionListeners();

    }

    private void removeRow(ActionEvent actionEvent) {
        int index = tableViewLeft.getSelectionModel().getSelectedIndex();
        if (index < FIXED_ROWS) {
            showError(msRowDoesNotSelected, msError);
            return;
        }
        index = checkBoxTimes.isSelected() ? (index - FIXED_ROWS) / 2 : index - FIXED_ROWS;
        if (processor.getData().commonXLength(index) > 0) {
            if (!showPrompt(msCurrentRowWillBeDeleted)) {
                return;
            }
        }
        processor.removeDataRow(index);
        updateTables();
    }

    private void addRow() {
        int index = tableViewLeft.getSelectionModel().getSelectedIndex();
        if (index < FIXED_ROWS) {
            index = leftTableModel.getRowCount();
        }
        index = checkBoxTimes.isSelected() ? (index - FIXED_ROWS) / 2 : index - FIXED_ROWS;
        processor.addDataRow(index);
        updateTables();
    }

    private void changeScrollBarWidth(Observable observable) {
        tableViewRight.setMinWidth(scrollPaneTables.getWidth() - 255);
        tableViewRight.setMaxWidth(scrollPaneTables.getWidth() - 255);
    }

    private void addWebView() {
        webView = new WebView();
        AnchorPane.setLeftAnchor(webView, 10.0);
        AnchorPane.setTopAnchor(webView, 10.0);
        AnchorPane.setRightAnchor(webView, 10.0);
        AnchorPane.setBottomAnchor(webView, 10.0);
        webViewPane.getChildren().add(webView);
    }

    @FXML
    private void newFile() {
        processor.doNew();
        leftTableModel = new LeftTableModel(processor);
        rightTableModel = new RightTableModel(processor);
        currentFileName = "";
        createTables();
        clearText(
                textFieldAmount,
                textFieldDate,
                textFieldOfficialStandardSample,
                textFieldOperator,
                textFieldStuff,
                textFieldNumber,
                textAreaWeightsDilutions,
                textFieldWrsDocument,
                textFieldWRSName,
                textFieldXPSS,
                textFieldDeltaWRS);
        webView.getEngine().loadContent("");
        primaryStage.setTitle(msWrsAttestation.toString());
    }

    @FXML
    private void open() {
        File file = getFileFromCurrentDir(getFileChooser(null,
                msOpen, "XML" + msFilter + " (*.xml)", "*.xml", "TXT" + msFilter + " (*.txt)", "*.txt"), OPEN_DIALOG);
        if (file != null) {
            try {
                processor.doNew();
                createTables();
                if (file.getCanonicalPath().endsWith(".txt") || file.getCanonicalPath().endsWith(".TXT")) {
                    processor.importFromTxt(file.getCanonicalPath());
                }
                else {
                    processor.readFromXML(file.getCanonicalPath());
                    textFieldWRSName.setText(data().getSSName());
                    textFieldOperator.setText(data().getOperator());
                    textFieldStuff.setText(data().getStuff());
                    textFieldAmount.setText(data().getAmount());
                    textFieldOfficialStandardSample.setText(data().getOfficialStandardSample());
                    textFieldWrsDocument.setText(data().getDocument());
                    textFieldDate.setText(data().getDate());
                    textFieldXPSS.setText(data().getXPSS());
                    textFieldDeltaWRS.setText(data().getDeltaWRS());
                    textFieldNumber.setText(data().getProtocol());
                    textAreaWeightsDilutions.setText(data().getWeightsDilutions());
                    processor.addState(AttestationProcessor.STATE_SAVED);
                }
                currentFileName = file.getCanonicalPath();
                options.setCurrentDir(getPath(file.getPath()));
                primaryStage.setTitle(msWrsAttestation + " - " + currentFileName);
                updateTables();
                webView.getEngine().loadContent("");
            }
            catch (IOException e) {
                showError(msFileNotFound, msError);
            }
            catch (JAXBException e) {
                e.printStackTrace();
                showError(msWrongFormat, msError);
            }

        }
    }

    private static File getFileFromCurrentDir(FileChooser fileChooser, boolean open) {
        File file;
        try {
            if (open) {
                file = fileChooser.showOpenDialog(primaryStage);
            }
            else {
                file = fileChooser.showSaveDialog(primaryStage);
            }
        }
        catch (IllegalArgumentException e) {
            options.setCurrentDir(".");
            fileChooser.setInitialDirectory(new File(options.getCurrentDir()));
            if (open) {
                file = fileChooser.showOpenDialog(primaryStage);
            }
            else {
                file = fileChooser.showSaveDialog(primaryStage);
            }
        }
        return file;
    }

    @FXML
    private void importFromTxt() {
        File file = getFileFromCurrentDir(getFileChooser(null, msImportTxt,
                "TXT" + msFilter + " (*.txt)", "*.txt"), OPEN_DIALOG);
        if (file != null) {
            try {
                processor.doNew();
                createTables();
                processor.importFromTxt(file.getCanonicalPath());
                currentFileName = file.getCanonicalPath();
                options.setCurrentDir(getPath(file.getPath()));
                //textFieldXPSS.setText(data().getXPSS());
                //textFieldDeltaWRS.setText(data().getDeltaWRS());
                primaryStage.setTitle(msWrsAttestation + " - " + currentFileName);
                updateTables();
                webView.getEngine().loadContent("");
            }
            catch (DataException.WrongFileFormat ex) {
                ex.fillInStackTrace();
                showError(msWrongFormat, msError);
            }
            catch (IOException e) {
                showError(msFileNotFound, msError);
            }
        }
    }

    @FXML
    private void save() {
        if (currentFileName.isEmpty() || currentFileName.endsWith(".txt")) {
            saveAs();
        }
        else {
            try {
                processor.saveToXML(currentFileName);
                showMessage(msSaveSuccess);
            }
            catch (Exception e) {
                e.printStackTrace();
                showError(msFileWriteError, msError);
            }
        }
    }

    private static FileChooser getFileChooser(String fileName, final MultiString title, final String description,
                                              final String extension) {
        return getFileChooser(fileName, title, description, extension, null, null);
    }

    private static FileChooser getFileChooser(String fileName, final MultiString title, final String description,
                                              final String extension, final String secondDescription,
                                              final String secondExtension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(options.getCurrentDir()));
        if (fileName != null && fileName.endsWith(extension)) {
            fileChooser.setInitialFileName(fileName);
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extension));
        if (secondDescription != null && secondExtension != null) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(secondDescription, secondExtension));
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(msFilterAll + " (*.*)", "*.*"));
        fileChooser.setTitle(title.toString());
        return fileChooser;
    }

    @FXML
    private void saveAs() {
        String name = dashAndName(currentFileName);
        File file = getFileFromCurrentDir(getFileChooser(name, msSaveXMLFile,
                "XML" + msFilter + " (*.xml)", "*.xml"), SAVE_DIALOG);
        if (file != null) {
            try {
                processor.saveToXML(file.getCanonicalPath());
                currentFileName = file.getCanonicalPath();
                options.setCurrentDir(getPath(file.getPath()));
                primaryStage.setTitle(msWrsAttestation + " - " + currentFileName);
                showMessage(msSaveSuccess);
            }
            catch (Exception e) {
                e.printStackTrace();
                showError(msFileWriteError, msError);
            }
        }
    }

    @FXML
    private void saveReport() {
        if (processor.attestationWasDone()) {
            File file = getFileFromCurrentDir(getFileChooser(null, msSaveReport, "PDF" + msFilter + " (*.pdf)", "*.pdf"), SAVE_DIALOG);
            if (file != null) {
                try {
                    if (processor.saveReport(file.getCanonicalPath(), textFieldNumber.getText())) {
                        showMessage(msSaveResultsSuccess);
                    }
                    else
                        showError(msAttestationWasNotDone, msError);
                }
                catch (Exception e) {
                    e.printStackTrace();
                    showError(msFileWriteError, msError);
                }
            }
        }
        else {
            showError(msAttestationWasNotDone, msError);
        }
    }

    @FXML
    private void close() {
        Platform.exit();
    }

    private void calcABC(char schema) {
        webView.getEngine().loadContent("");
        if (schema != 'C' && (textFieldXPSS.getText().isEmpty() || !processor.setXPSS(textFieldXPSS.getText()))) {
            showError(msWrongValueOfXPSS, msError);
            return;
        }
        if (textFieldDeltaWRS.getText().isEmpty() || !processor.setDeltaWRS(textFieldDeltaWRS.getText())) {
            showError(msWrongValueOfDeltaWRS, msError);
            return;
        }
        try {
            byte[] report = processor.calcABC(schema, null, textFieldNumber.getText());
            webView.getEngine().loadContent(new String(report));
        }
        catch (DataException.WrongXPSS e) {
            showError(msWrongValueOfXPSS, msError);
        }
        catch (DataException.WrongX e) {
            showError(msWrongValueOfX, msError);
        }
        catch (DataException.WrongConcentration e) {
            showError(msWrongValueOfConcentration, msError);
        }
        catch (DataException.WrongUncertainty e) {
            showError(msWrongValueOfUncertainty, msError);
        }
        catch (DataException.ReportException e) {
            showError(e.getMessage(), msError);
        }
        catch (DataException.InsufficientDataException e) {
            showError(msInsufficientCountOfParallelMeasurements, msError);
        }
        catch (DataException.NoXException e) {
            showError(msNoParallelMeasurements, msError);
        }
    }

    @FXML
    private void calcA() {
        calcABC('A');
    }

    @FXML
    private void calcB() {
        calcABC('B');
    }

    @FXML
    private void calcC() {
        calcABC('C');
    }

    @FXML
    private void chooseLanguage() {
        if (menuItemOptionsLanguageEnglish.isSelected()) {
            lang = EN;
        }
        if (menuItemOptionsLanguageUkrainian.isSelected()) {
            lang = UA;
        }
        if (menuItemOptionsLanguageRussian.isSelected()) {
            lang = RU;
        }
        switchLanguage();
        //primaryStage.setTitle(msAboutHeader + dashAndName(currentFileName));
    }

    @FXML
    private void showOptions() {
        new OptionsWindow(lang).show(processor.getOptionsData());
    }

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(getImageView("/MainIcon.png"));
        alert.setTitle(msAboutTitle.toString());
        alert.setHeaderText(msWrsAttestation + "\n\n" + msAboutText + AttestationProcessor.VERSION);
        alert.setContentText(msAllRightsReserved + " " + processor.getCustomer().getName());
        alert.showAndWait();
    }

    @FXML
    private void checkBoxTimesClick() {
        updateTables();
    }

    private void createTables() {
        leftTableModel.setShowTimes(checkBoxTimes.isSelected());
        updateTable(tableViewLeft, false, leftTableModel, "table-view-left", null, new ColumnData("", 0.99));
        anchorPaneTables.getChildren().clear();
        anchorPaneTables.getChildren().add(tableViewLeft);
        prepareRightTable();
        updateTable(tableViewRight, true, rightTableModel, "table-view", this::updateData, new ColumnData("1", 0.992));
    }

    private void prepareRightTable() {
        tableViewRight = new TableView();
        AnchorPane.setRightAnchor(tableViewRight, 10.0);
        AnchorPane.setLeftAnchor(tableViewRight, 230.0);
        anchorPaneTables.getChildren().add(tableViewRight);
        tableViewRight.setMinHeight(tableViewLeft.getMinHeight());
        tableViewRight.setFixedCellSize(22);
        tableViewRight.setContextMenu(contextMenu);
        tableViewRight.getSelectionModel().selectedItemProperty().addListener(rightSelectListener);
        rightTableModel.setShowTimes(checkBoxTimes.isSelected());
    }

    private void updateTables() {
        leftTableModel.setShowTimes(checkBoxTimes.isSelected());
        tableViewLeft.getSelectionModel().selectedItemProperty().removeListener(leftSelectListener);
        updateTable(tableViewLeft, false, leftTableModel, "table-view-left", null, new ColumnData("", 0.992));
        tableViewLeft.getSelectionModel().selectedItemProperty().addListener(leftSelectListener);
        anchorPaneTables.getChildren().clear();
        anchorPaneTables.getChildren().add(tableViewLeft);
        rightTableModel.setShowTimes(checkBoxTimes.isSelected());
        ColumnData[] titles = new ColumnData[processor.getSampleCount() + 1];
        for (int i = 0; i < titles.length; i++) {
            titles[i] = new ColumnData((i + 1) + "", 0);
        }
        titles[processor.getSampleCount()] = new ColumnData("", 0);
        prepareRightTable();
        tableViewRight.getSelectionModel().selectedItemProperty().removeListener(rightSelectListener);
        updateTable(tableViewRight, true, rightTableModel,  "table-view", this::updateData, titles);
        tableViewRight.getSelectionModel().selectedItemProperty().addListener(rightSelectListener);
    }

    private void mouseMoved(MouseEvent mouseEvent) {
        TablePosition<?, ?> pos = (TablePosition<?, ?>)tableViewLeft.getSelectionModel().getSelectedCells().getFirst();
        int row = pos.getRow();
        int col = pos.getColumn();
        //System.out.println(row + " " + col);
    }

    public static<T> T getValue(T k, T defaultValue) {
        return k == null ? defaultValue : k;
    }

    private void updateData(TableColumn.CellEditEvent<CellData[], String> event) {
        int col = event.getTablePosition().getColumn();
        int row = event.getTablePosition().getRow();
        if (new CellData(rightTableModel.getValueAt(row, col) + "").getValueTypeCheck().test(event.getNewValue())) {
            rightTableModel.setValueAt(event.getNewValue(), row, col);
//            anchorPaneTables.getChildren().remove(tableViewRight);
//            prepareRightTable();
//            updateTable(tableViewRight, true, rightTableModel, "table-view", this::updateData, new ColumnData("1", 0.992));

            updateTables();
            processor.removeState(AttestationProcessor.STATE_SAVED);
        }
        else {
            rollback(event.getTableView());
        }
    }

    public static void loadOptions(WindowEvent windowEvent) {
        try {
            options = Options.readOptions(System.getenv("USERPROFILE") + "\\WRS_ini.options");
        }
        catch (FileNotFoundException e) {
            try {
                options = Options.readOptions("WRS_ini.options");
            }
            catch (FileNotFoundException e1) {
                options = new Options();
            }
        }
        Options.Window window = options.getWindow();
        primaryStage.setX(getValue(window.getLeft(), 200));
        primaryStage.setY(getValue(window.getTop(), 100));
        primaryStage.setWidth(getValue(window.getWidth(), 1295));
        primaryStage.setHeight(getValue(window.getHeight(), 700));
        switch (options.getLanguage()) {
            case "ENGLISH":
                lang = EN;
                controller.menuItemOptionsLanguageEnglish.setSelected(true);
                break;
            case "UKRAINIAN":
                lang = UA;
                controller.menuItemOptionsLanguageUkrainian.setSelected(true);
                break;
            case "RUSSIAN":
                lang = RU;
                controller.menuItemOptionsLanguageRussian.setSelected(true);
                break;
        }
        if (options.getReportOptions() != null) {
            Options.ReportOptions reportOptions = options.getReportOptions();
            processor.setOptionsData(new OptionsData(reportOptions.getCalcDisp().equals("true"),
                    reportOptions.getCalcHomo().equals("true"),
                    reportOptions.getCalcDrift().equals("true"),
                    (options.getReportOptions().getStudentSides() == 1 ?
                            OptionsData.OneTwo.ONE : OptionsData.OneTwo.TWO)));
        }
        controller.switchLanguage();
    }

    public static void stop() {
        String lan = switch (lang) {
            case EN -> "ENGLISH";
            case UA -> "UKRAINIAN";
            case RU -> "RUSSIAN";
            default -> "";
        };
        options.setLanguage(lan);
        //options.setCurrentDir(controller.getCurrentPath());
        if (primaryStage.getX() > 0 && primaryStage.getY() > 0) {
            Options.Window window = options.getWindow();
            window.setLeft((int) (primaryStage.getX()));
            window.setTop((int) (primaryStage.getY()));
            window.setWidth((int) (primaryStage.getWidth()));
            window.setHeight((int) (primaryStage.getHeight()));
        }
        OptionsData optionsData = processor.getOptionsData();
        options.getReportOptions().setCalcDisp((optionsData.isVariancesEquality() + "").toLowerCase());
        options.getReportOptions().setCalcHomo((optionsData.isSamplesHomogeneity() + "").toLowerCase());
        options.getReportOptions().setCalcDrift((optionsData.isDrift() + "").toLowerCase());
        options.getReportOptions().setStudentSides(optionsData.getSides().ordinal());
        options.saveOptions(System.getenv("USERPROFILE") + "\\WRS_ini.options");
    }

    private WrsData data() {
        return processor.getData();
    }

    public static void closeCheck(WindowEvent event) {
        if (!processor.isDataSaved()) {
            Boolean result = showConfirmDialog(msAttention, msDataNotSaved);
            if (result != null && result) {
                controller.save();
                if (!processor.isDataSaved()) {
                    result = null;
                }
            }
            if (result == null) {
                event.consume();
                return;
            }
        }
        if (!processor.isReportSaved()) {
            Boolean result = showConfirmDialog(msAttention, msReportNotSaved);
            if (result != null && result) {
                controller.saveReport();
                if (!processor.isReportSaved()) {
                    result = null;
                }
            }
            if (result == null) {
                event.consume();
                return;
            }
        }
        Platform.exit();
    }
}
