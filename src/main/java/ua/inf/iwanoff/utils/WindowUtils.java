package ua.inf.iwanoff.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.swing.table.TableModel;
import java.util.Arrays;
import java.util.Optional;

/**
 * Utility class providing helper methods for user interface alerts, windows, and table management in JavaFX[cite: 10].
 */
public class WindowUtils {
    
    /**
     * Displays an error alert dialog with the specified message and error title[cite: 10].
     * 
     * @param message the error message content[cite: 10]
     * @param error the error title representation[cite: 10]
     */
    public static void showError(String message, MultiString error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(error.toString());
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Displays an information message alert dialog[cite: 10].
     * 
     * @param message the message content to display[cite: 10]
     */
    public static void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
 
    /**
     * Displays a warning alert dialog[cite: 10].
     * 
     * @param message the warning message content to display[cite: 10]
     */
    public static void showWarning(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation prompt dialog and returns user consent[cite: 10].
     * 
     * @param prompt the prompt message to display[cite: 10]
     * @return true if the user clicks OK, false otherwise[cite: 10]
     */
    public static boolean showPrompt(String prompt) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(prompt);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    /**
     * Displays an error alert dialog using MultiString objects[cite: 10].
     * 
     * @param message the error message content[cite: 10]
     * @param error the error title representation[cite: 10]
     */
    public static void showError(MultiString message, MultiString error) {
        showError(message.toString(), error);
    }

    /**
     * Displays an information message alert dialog using a MultiString object[cite: 10].
     * 
     * @param message the message content to display[cite: 10]
     */
    public static void showMessage(MultiString message) {
        showMessage(message.toString());
    }

    /**
     * Displays a warning alert dialog using a MultiString object[cite: 10].
     * 
     * @param message the warning message content to display[cite: 10]
     */
    public static void showWarning(MultiString message) {
        showWarning(message.toString());
    }

    /**
     * Displays a confirmation prompt dialog using a MultiString object[cite: 10].
     * 
     * @param prompt the prompt message to display[cite: 10]
     * @return true if the user clicks OK, false otherwise[cite: 10]
     */
    public static boolean showPrompt(MultiString prompt) {
        return showPrompt(prompt.toString());
    }

    /**
     * Displays a confirmation dialog with YES, NO, and CANCEL options[cite: 10].
     * 
     * @param title the dialog title[cite: 10]
     * @param message the dialog message content[cite: 10]
     * @return true for YES, false for NO, and null for CANCEL or dismissal[cite: 10]
     */
    public static Boolean showConfirmDialog(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setHeaderText(message);
        alert.setTitle(title);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get().equals(ButtonType.YES)) {
            return true;
        }
        if (result.isPresent() && result.get().equals(ButtonType.NO)) {
            return false;
        }
        return null;
    }

    /**
     * Displays a confirmation dialog using MultiString parameters[cite: 10].
     * 
     * @param title the dialog title[cite: 10]
     * @param message the dialog message content[cite: 10]
     * @return true for YES, false for NO, and null for CANCEL or dismissal[cite: 10]
     */
    public static Boolean showConfirmDialog(MultiString title, MultiString message) {
        return showConfirmDialog(title.toString(), message.toString());
    }

    /**
     * A modal-like editor window stage used for text input[cite: 10].
     */
    public static class Editor extends Stage {
        private String text;
        
        /**
         * Constructs an Editor stage with custom titles and button labels[cite: 10].
         * 
         * @param title the window title[cite: 10]
         * @param txt the initial text value[cite: 10]
         * @param ok the label for the OK button[cite: 10]
         * @param cancel the label for the Cancel button[cite: 10]
         */
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

        /**
         * Retrieves the final entered text content[cite: 10].
         * 
         * @return the entered text, or null if canceled[cite: 10]
         */
        public String getText() {
            return text;
        }

    }

    /**
     * Sets fixed minimum and maximum heights for the specified regions[cite: 10].
     * 
     * @param value the height value to set[cite: 10]
     * @param regions the target regions[cite: 10]
     */
    public static void setHeights(double value, Region... regions) {
        for (Region r : regions) {
            r.setMinHeight(value);
            r.setMaxHeight(value);
        }
    }

    /**
     * Makes the specified JavaFX nodes visible[cite: 10].
     * 
     * @param nodes the nodes to make visible[cite: 10]
     */
    public static void nowVisible(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(true);
        }
    }

    /**
     * Makes the specified JavaFX nodes invisible[cite: 10].
     * 
     * @param nodes the nodes to make invisible[cite: 10]
     */
    public static void nowInvisible(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(false);
        }
    }

    /**
     * Clears text content for the specified text input controls[cite: 10].
     * 
     * @param controls the text input controls to clear[cite: 10]
     */
    public static void clearText(TextInputControl... controls) {
        for (TextInputControl control : controls) {
            control.setText("");
        }
    }

    /**
     * Sets the text of a menu item using a MultiString object[cite: 10].
     * 
     * @param menuItem the target menu item[cite: 10]
     * @param multiString the multi-string text value[cite: 10]
     */
    public static void setText(MenuItem menuItem, MultiString multiString) {
        menuItem.setText(multiString.toString());
    }

    /**
     * Refreshes a TableView component by toggling the visibility of its first column[cite: 10].
     * 
     * @param view the table view to rollback[cite: 10]
     */
    public static void rollback(TableView view) {
        ((TableColumn) view.getColumns().get(0)).setVisible(false);
        ((TableColumn) view.getColumns().get(0)).setVisible(true);
    }

    /**
     * Converts a Swing TableModel into a 2D array of CellData objects[cite: 10].
     * 
     * @param model the Swing TableModel[cite: 10]
     * @return a 2D array of CellData representing the model data[cite: 10]
     */
    public static CellData[][] getArray(TableModel model) {
        CellData[][] arr = new CellData[model.getRowCount()][model.getColumnCount()];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = new CellData(model.getValueAt(i, j) + "");
            }
        }
        return arr;
    }

    /**
     * Clears a TableView component and resets its columns[cite: 10].
     * 
     * @param tableView the table view to clear[cite: 10]
     */
    public static void clearTable(TableView tableView) {
        tableView.setItems(null);
        TableColumn<CellData[], String> tableColumn = new TableColumn<>();
        tableView.getColumns().add(tableColumn);
    }

    /**
     * Updates and populates a JavaFX TableView using data from a Swing TableModel[cite: 10].
     * 
     * @param tableView the target JavaFX table view[cite: 10]
     * @param editable flag indicating if the table is editable[cite: 10]
     * @param model the data table model[cite: 10]
     * @param style the CSS style class to add[cite: 10]
     * @param eventHandler the event handler for cell edits[cite: 10]
     * @param columnData metadata for columns[cite: 10]
     */
    public static void updateTable(TableView tableView, boolean editable, TableModel model, String style,
                                   EventHandler<TableColumn.CellEditEvent<CellData[], String>> eventHandler,
                                   ColumnData... columnData) {
        if (model == null) {
            return;
        }
        if (!tableView.getColumns().isEmpty()) {
            rollback(tableView);
            tableView.getColumns().clear();
        }
        tableView.setItems(null);
        ObservableList<CellData[]> observableList = FXCollections.observableArrayList();
        observableList.addAll(Arrays.asList(getArray(model)));
        tableView.setEditable(editable);
        tableView.getStyleClass().add(style);
        tableView.getColumns().clear();
        tableView.setItems(observableList);
        tableView.prefHeightProperty().bind(Bindings.size(
                tableView.getItems()).multiply(tableView.getFixedCellSize()).add(40));
        for (int i = 0; i < model.getColumnCount(); i++) {
            TableColumn<CellData[], String> tableColumn;
            if (i < columnData.length && columnData[i] != null) {
                tableColumn = new TableColumn<>(columnData[i].getTitle());
                double width = columnData[i].getWidth();
                if (width > 0) {
                    tableColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(width));
                }
            }
            else {
                tableColumn = new TableColumn<>();
            }
            tableColumn.setEditable(true);
            tableColumn.setSortable(false);
            tableColumn.setReorderable(false);
            final int colNo = i;
            tableColumn.setCellValueFactory(p -> new SimpleStringProperty((p.getValue()[colNo]).getValue()));
            Callback<TableColumn<CellData[], String>, TableCell<CellData[], String>> defaultTextFieldCellFactory
                    = TextFieldTableCell.forTableColumn();
            tableColumn.setCellFactory(column -> {
                TableCell<CellData[], String> cell = defaultTextFieldCellFactory.call(column);
                cell.itemProperty().addListener((observableValue, oldValue, newValue) -> {
                    TableRow row = cell.getTableRow();
                    if (row == null || colNo >= model.getColumnCount()) {
                        cell.setEditable(false);
                    }
                    else {
                        CellData[] item = (CellData[]) cell.getTableRow().getItem();
                        if (item == null) {
                            cell.setEditable(false);
                        }
                        else {
                            cell.setEditable(item[colNo].isEditable());
                        }
                    }
                });
                return cell;
            });
            if (eventHandler != null) {
                tableColumn.setOnEditCommit(eventHandler);
            }
            tableView.getColumns().add(tableColumn);
        }
    }

    /**
     * Synchronizes row selections between two TableView components[cite: 10].
     * 
     * @param one the first table view source selection[cite: 10]
     * @param two the second table view to synchronize selection to[cite: 10]
     */
    public static void synchronizeTables(TableView one, TableView two) {
        int index = one.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            try {
                two.getSelectionModel().select(index);
            }
            catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds a change listener to the text property of multiple text input controls[cite: 10].
     * 
     * @param listener the change listener to add[cite: 10]
     * @param controls the text input controls to attach the listener to[cite: 10]
     */
    public static void addListener(ChangeListener<? super String> listener, TextInputControl ... controls) {
        for (TextInputControl control: controls) {
            control.textProperty().addListener(listener);
        }
    }
}