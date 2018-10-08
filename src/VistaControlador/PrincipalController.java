package VistaControlador;

import Modelos.Thompson.ExpresionRegular;
import Modelos.Thompson.Thompson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    Stage stage;
    Rectangle2D rec2;
    Double w, h;
    
    ExpresionRegular expresion;

	
	/*Evento de cierre para la ventana principal*/
    @FXML
    void closeAction(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
	
	/*Evento que construye el AFD*/
    @FXML
    void construirAFDAction(ActionEvent event) {  
            String expresionStr = expresionRegularTField.getText();
            expresion = new ExpresionRegular(expresionStr);
        if (expresion.esCorrecta()){
            expresionRegularTField.setEditable(false);
            expresionRegularButton.setDisable(true);
            String[][] expresionArray = expresion.crearAFD(expresion);
            String[] columnNames = {"TRANSICIÓN", "0", "1", "ESTADO"};     
            createScene(columnNames, expresionArray);
            hileraButton.setDisable(false);
            hileraTField.setEditable(true);
        } else {
            dialog("EXPRESION NO SOPORTADA", "La expresión ingresada no es permitida por la aplicación, "
                    + "por favor modifiquela.");
        }
    }
 
	/*Evento para el botón que determina si una hilera pertenece al AFD*/
    @FXML
    void hileraPerteneceAction(ActionEvent event) { 
        String hilera = hileraTField.getText();
        Thompson thompson = expresion.getTh();
        boolean bool = thompson.pertenece(hilera);
        String body = "NO IMPLEMENTED";
        if (bool == true) {
            body = "Pertenece";
        } else {
            body = "No Pertenece";
        }
        dialog("¿La hilera pertenece al Autómata Finito?", body);
    }
    
	/*Método que genera los diálogos, o ventanas de alerta, en pantalla*/
    public void dialog(String head, String body) {
        StackPane stackPane = new StackPane();
        //stackPane.autosize();
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(head));
        content.setBody(new Text(body));
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Okay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });
        content.setActions(button); 
        mainPane.getChildren().add(stackPane);
        AnchorPane.setTopAnchor(stackPane, (500 - content.getPrefHeight()) / 2);
	AnchorPane.setLeftAnchor(stackPane, (544 - content.getPrefWidth()) / 2);
        dialog.show();
    }

	/*Evento de minimizar para la ventana principal*/
    @FXML
    void minAction(ActionEvent event) {
        stage = (Stage) minButton.getScene().getWindow();
        if (stage.isMaximized()) {
            w = rec2.getWidth();
            h = rec2.getHeight();
            stage.setMaximized(false);
            stage.setHeight(h);
            stage.setWidth(w);
            stage.centerOnScreen();
            Platform.runLater(() -> {
                stage.setIconified(true);
            });
        } else {
            stage.setIconified(true);
        }
    }
    
	/*Método que genera la tabla en pantalla del AFD*/
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

