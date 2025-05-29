/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.burgueses.presentacion;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author aleja
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            // Ruta al FXML de la PaginaPrincipal.
            // Aseg�rate de que la ruta sea correcta seg�n la ubicaci�n de tu FXML.
            // Si PaginaPrincipal.fxml est� en es.burgueses.presentacion.View:
           URL fxmlLocation = getClass().getResource("/fxml/paginaPrincipal.fxml");

          /*  if (fxmlLocation == null) {
                System.err.println("Error: No se pudo encontrar PaginaPrincipal.fxml. "
                        + "Verifica la ruta en getClass().getResource().");
                return;
            }
*/
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

            // Carga el nodo ra�z desde el FXML.
            // �IMPORTANTE!: Reemplaza 'AnchorPane' si tu nodo ra�z en PaginaPrincipal.fxml es diferente.
            Pane root = fxmlLoader.load();

            // Crea la escena con el nodo ra�z.
            Scene scene = new Scene(root); // Puedes ajustar el tama�o: new Scene(root, 1024, 768);

            // Configura el Stage (ventana principal).
            stage.setTitle("");
            stage.setScene(scene);
            stage.show(); // Muestra la ventana.

        } catch (IOException e) {
            System.err.println("Error al cargar la vista de la P�gina Principal: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Llama al metodo launch() de Application, que a su vez llama a start(Stage)
        launch(args);
    }

}


