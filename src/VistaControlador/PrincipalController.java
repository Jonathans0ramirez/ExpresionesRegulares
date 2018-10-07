package VistaControlador;

import Modelos.Thompson.AFNDLambda;
import Modelos.Thompson.ExpresionRegular;
import Modelos.Thompson.NodeLambda;
import Modelos.Thompson.Thompson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class PrincipalController {

    @FXML
    private AnchorPane mainPane;
    @FXML
    private AnchorPane expresionRegularPane;
    @FXML
    private JFXTextField expresionRegularTField;
    @FXML
    private JFXButton expresionRegularButton;
    @FXML
    private AnchorPane hileraPane;
    @FXML
    private JFXTextField hileraTField;
    @FXML
    private JFXButton hileraButton;
    @FXML
    private AnchorPane AFDPane;
    @FXML
    private TableView <List<String>> AFDTable;
    @FXML
    private TableColumn<String[], String> transicionColumn = new TableColumn<>("TRANSICIÓN");;
    @FXML
    private TableColumn<String[], String> zeroColumn = new TableColumn<>("0");
    @FXML
    private TableColumn<String[], String> oneColumn = new TableColumn<>("1");;
    @FXML
    private TableColumn<String[], String> estadoColumn = new TableColumn<>("ESTADO");;
    @FXML
    private JFXButton minButton;
    @FXML
    private JFXButton maxButton;
    @FXML
    private JFXButton closeButton;

    @FXML
    void closeAction(ActionEvent event) {

    }

    @FXML
    void construirAFDAction(ActionEvent event) {
        expresionRegularTField.setEditable(false);
        expresionRegularButton.setDisable(true);
        String expresionStr = expresionRegularTField.getText();
        ExpresionRegular expresion = new ExpresionRegular(expresionStr);
        String[][] expresionArray = expresion.crearAFD();
        String[] columnNames = {"TRANSICIÓN", "0", "1", "ESTADO"};     
        createScene(columnNames, expresionArray);
        hileraButton.setDisable(false);
        hileraTField.setEditable(true);
    }
        
 

    @FXML
    void hileraPerteneceAction(ActionEvent event) {

    }

    @FXML
    void minAction(ActionEvent event) {

    }
    
    private void createScene(String[] columnNames, String[][] inputData) {
        AFDTable.setEditable(false);
        AFDTable.setDisable(false);
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn<List<String>, String> column = new TableColumn<>(columnNames[i]);
            final int colIndex = i;
            column.setCellValueFactory(cellData
                    -> new SimpleStringProperty(cellData.getValue().get(colIndex)));
            AFDTable.getColumns().add(column);
            column.setSortable(false);
        }
        List<List<String>> data = new ArrayList<List<String>>();
        for (int i = 0; i < inputData.length; i++) {
            List<String> row = new ArrayList<String>();
            for (int j = 0; j < inputData[0].length; j++) {
                row.add(inputData[i][j]);
            }
            data.add(row);
        }
        ObservableList<List<String>> list = FXCollections.observableArrayList(data);
        AFDTable.setItems(list);
        AFDTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}

