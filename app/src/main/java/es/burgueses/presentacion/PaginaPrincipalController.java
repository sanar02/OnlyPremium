/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

package es.burgueses.presentacion; // Este es tu paquete, no necesitas importarlo de nuevo

import com.google.common.eventbus.EventBus; // Asegúrate de tener la dependencia para Guava (si la usas)
import com.google.common.eventbus.Subscribe; // Asegúrate de tener la dependencia para Guava (si la usas)

import java.io.IOException; // Esta importación puede ser necesaria si usas FXMLLoader.load()
import java.net.URL;
import java.util.Optional; // Necesario si usas Optional en Alert.showAndWait()
import java.util.ResourceBundle;

import javafx.application.Platform; // Solo una vez
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader; // Si lo usas para cargar otras vistas
import javafx.fxml.Initializable;
import javafx.geometry.Bounds; // Si lo usas con Bounds::getCenterX/Y
import javafx.geometry.Pos; // Si usas Pos.CENTER_LEFT
import javafx.scene.control.Alert; // Si usas Alert
import javafx.scene.control.ButtonType; // Si usas ButtonType
import javafx.scene.control.ListView; // Si usas ListView
import javafx.scene.control.TextField; // Si usas TextField
import javafx.scene.control.ToggleButton; // Si usas ToggleButton
import javafx.scene.control.ToggleGroup; // Si usas ToggleGroup
import javafx.scene.image.Image; // Si usas Image
import javafx.scene.image.ImageView; // Si usas ImageView
import javafx.scene.layout.AnchorPane; // Si usas AnchorPane
import javafx.scene.layout.StackPane; // Si usas StackPane
import javafx.scene.layout.VBox; // Si usas VBox
import javafx.scene.shape.Circle; // Si usas Circle
import javafx.stage.Stage; // Si usas Stage
import javafx.event.ActionEvent; // Si usas ActionEvent en tus métodos @FXML



/**
 * FXML Controller class
 *
 * @author aleja
 */
public class PaginaPrincipalController implements Initializable {

    @FXML
    private TextField textFieldBuscar;
    @FXML
    private ListView<?> listViewTop5;
    @FXML
    private ListView<?> listViewRecientes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void botonesCanciones(ActionEvent event) {
    }

    @FXML
    private void buscarTFOnAction(ActionEvent event) {
    }

}
