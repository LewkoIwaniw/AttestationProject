package ua.inf.iwanoff.utils;

//import com.sun.javafx.scene.control.skin.TableHeaderRow;
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

public class WindowUtils {
    
    public static void showError(String message, MultiString error) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(error.toString());
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

    public static boolean showPrompt(String prompt) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText(prompt);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    public static void showError(MultiString message, MultiString error) {
        showError(message.toString(), error);
    }

    public static void showMessage(MultiString message) {
        showMessage(message.toString());
    }

    public static void showWarning(MultiString message) {
        showWarning(message.toString());
    }

    public static boolean showPrompt(MultiString prompt) {
        return showPrompt(prompt.toString());
    }

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

    public static Boolean showConfirmDialog(MultiString title, MultiString message) {
        return showConfirmDialog(title.toString(), message.toString());
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
    public static void setHeights(double value, Region... regions) {
        for (Region r : regions) {
            r.setMinHeight(value);
            r.setMaxHeight(value);
        }
    }

    public static void nowVisible(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(true);
        }
    }

    public static void nowInvisible(Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(false);
        }
    }

    public static void clearText(TextInputControl... controls) {
        for (TextInputControl control : controls) {
            control.setText("");
        }
    }

    public static void setText(MenuItem menuItem, MultiString multiString) {
        menuItem.setText(multiString.toString());
    }

    public static void rollback(TableView view) {
        ((TableColumn) view.getColumns().get(0)).setVisible(false);
        ((TableColumn) view.getColumns().get(0)).setVisible(true);
    }

    public static CellData[][] getArray(TableModel model) {
        CellData[][] arr = new CellData[model.getRowCount()][model.getColumnCount()];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = new CellData(model.getValueAt(i, j) + "");
            }
        }
        return arr;
    }

    public static void clearTable(TableView tableView) {
        tableView.setItems(null);
        TableColumn<CellData[], String> tableColumn = new TableColumn<>();
        tableView.getColumns().add(tableColumn);
    }

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
                tableColumn.setOnEditCommit(eventHandler); ////////////////////
//                observableList = FXCollections.observableArrayList();
//                observableList.addAll(Arrays.asList(getArray(model)));
//                tableView.setItems(observableList);
            }
            tableView.getColumns().add(tableColumn);
        }
        // prevent TableView from doing TableColumn re-order:
//        tableView.skinProperty().addListener((obs, oldSkin, newSkin) -> {
//            final TableHeaderRow header = (TableHeaderRow) tableView.lookup("TableHeaderRow");
//            header.reorderingProperty().addListener((o, oldVal, newVal) -> header.setReordering(false));
//        });
    }

    public static void synchronizeTables(TableView one, TableView two) {
        int index = one.getSelectionModel().getSelectedIndex();
        //System.out.println(index);
        if (index >= 0) {
            try {
                two.getSelectionModel().select(index);
                //one.getSelectionModel().clearSelection();
            }
            catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addListener(ChangeListener<? super String> listener, TextInputControl ... controls) {
        for (TextInputControl control: controls) {
            control.textProperty().addListener(listener);
        }
    }
}
