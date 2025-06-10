package es.burgueses.presentacion.controladores.principal;

import es.burgueses.aplicacion.cancion.GetAllSongsUserCase;
import es.burgueses.dominio.Cancion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.Arrays;

public class MainController extends es.burgueses.presentacion.controladores.navegacion.AWindows {

    @FXML
    private Button buttonHome;
    @FXML
    private Button btnPlay;

    @FXML
    private Button btnBefore;
    @FXML
    private Button btnAfter;
    @FXML
    private Button btnLike;
    @FXML
    private Button btnDislike;

    @FXML
    private TextField textFieldSearch;

    @FXML
    private TableView<Cancion> tableView; 

    @FXML
    private TableColumn<Cancion, String> tableColumn1; // Por ejemplo, título
    @FXML
    private TableColumn<Cancion, String> tableColumn2; // Por ejemplo, autor

    private GetAllSongsUserCase getAllSongsUserCase;
    private ObservableList<Cancion> cancionesList = FXCollections.observableArrayList();

    // Métodos para los botones
    @FXML
    private void homeOnAction() {
        // Acción para el botón Home
    }

    @FXML
    private void cancionOnAction() {
        // Acción para el botón Canciones
    }

    @FXML
    private void ajustesOnAction() {
        // Acción para el botón Ajustes
    }

    @FXML
    private void exitOnAction() {
        // Acción para el botón Salir
    }

    @FXML
    private void playOnAction() {
        Cancion seleccionada = tableView.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            String path = seleccionada.getPath(); // Asegúrate de tener getPath() en Cancion
            File file = new File(path);
            if (file.exists()) {
                Media media = new Media(file.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
            } else {
                System.out.println("Archivo no encontrado: " + path);
            }
        } else {
            System.out.println("No hay canción seleccionada.");
        }
    }

    @FXML
    private void beforeOnAction() {
        // Acción para el botón Anterior
    }

    @FXML
    private void afterOnAction() {
        // Acción para el botón Siguiente
    }

    @FXML
    private void likeOnAction() {
        // Acción para el botón Like
    }

    @FXML
    private void dislikeOnAction() {
        // Acción para el botón Dislike
    }

    @FXML
    private void searchOnAction() {
        // Acción para el botón de búsqueda
    }

    @FXML
    private void textFieldSearch() {
        // Acción al escribir en el campo de búsqueda
    }

    public void setGetAllSongsUserCase(GetAllSongsUserCase getAllSongsUserCase) {
        this.getAllSongsUserCase = getAllSongsUserCase;
        cargarCanciones();
    }

    @FXML
    public void initialize() {
        // Configura las columnas (ajusta los nombres según tu clase Cancion)
        tableColumn1.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitulo()));
        tableColumn2.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAutor()));
        tableView.setItems(cancionesList);

        cargarCanciones();
    }

    private void cargarCanciones() {
        cancionesList.clear();
        try {
            // Obtén la URL de la carpeta "Canciones" en resources
            URL folderUrl = getClass().getResource("/Canciones");
            if (folderUrl != null) {
                File folder = new File(folderUrl.toURI());
                File[] files = folder.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".wav"));
                if (files != null) {
                    for (File file : files) {
                        // Crea un objeto Cancion por cada archivo
                        Cancion cancion = new Cancion();
                        cancion.setTitulo(file.getName());
                        cancion.setAutor("Desconocido"); // O extrae el autor si lo tienes
                        cancion.setPath(file.getAbsolutePath());
                        cancionesList.add(cancion);
                    }
                }
            } else {
                System.out.println("No se encontró la carpeta Canciones en resources.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
