package es.burgueses.presentacion.controladores.principal;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import es.burgueses.aplicacion.cancion.AddSongUseCase;
import es.burgueses.aplicacion.cancion.DeleteSongUserCase;
import es.burgueses.aplicacion.cancion.GetAllSongsUserCase;
import es.burgueses.aplicacion.cancion.UpdateSongUserCase;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.IFilesRepositorio;
import es.burgueses.infraestructura.files.LocalFilesRepository;
import es.burgueses.infraestructura.files.SongRepositoryInFile;
import es.burgueses.presentacion.controladores.cancion.CancionController;
import es.burgueses.presentacion.controladores.cancion.CancionViewmodel;
import es.burgueses.presentacion.controladores.login.LoginController;
import es.burgueses.presentacion.controladores.navegacion.AWindows;
import es.burgueses.presentacion.controladores.navegacion.Router;
import es.burgueses.presentacion.utils.AppViewmodel;
import es.burgueses.presentacion.utils.Configuracion;
import es.burgueses.presentacion.utils.ReproductorViewmodel;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import javafx.fxml.FXML;

import java.io.IOException;

public class PaginaPrincipalController {
    @FXML
    private StackPane mainStackPane;
    @FXML
    private JFXDrawer drawer;

    @FXML
    private StackPane contentPane;

    private IFilesRepositorio filesRepository;
    private IFilesRepositorio songsFilesRepository;
    private ICancionesRepositorio songsRepository;
    // casos de uso general
    // caso de uso de user

    // casos de uso de cancion
    private AddSongUseCase addSongUseCase;
    private DeleteSongUserCase deleteSongUseCase;

    private GetAllSongsUserCase listAllSongsUseCase;

    private UpdateSongUserCase updateSongUseCase;
    // viewmodel

    private CancionViewmodel cancionViewmodel;
    private AppViewmodel appViewModel;
    private ReproductorViewmodel reproductorViewModel;
    // varios
    private VBox listaOpciones;
    private Router router;

    @FXML
    public void initialize() {
        AppViewmodel appViewModel = new AppViewmodel();
        try {
            this.configRouter();

            this.listaOpciones = new VBox();
            this.listaOpciones.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            this.listaOpciones.setSpacing(10);

            Region region = new Region();

            region.setMinHeight(40);
            this.listaOpciones.getChildren().add(region);
            drawer.setSidePane(listaOpciones);

            drawer.open();
            appViewModel.login("admin", "1234");
            this.setAppViewModel(appViewModel);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRepositories() {
        // var c = Configuracion.getInstancia();

        /*
         * this.filesRepository = new LocalFilesRepository(c.getImagesPath());
         * this.songsRepository = new SongRepositoryInFile(c.getSonsJsongsPath());
         * this.songsFilesRepository = new LocalFilesRepository(c.getSongsPath());
         */
        this.filesRepository = new LocalFilesRepository("imagenes");
        this.songsRepository = new SongRepositoryInFile("canciones.json");
        this.songsFilesRepository = new LocalFilesRepository("canciones");
    }

    private void initUseCases() {
        // song
        this.addSongUseCase = new AddSongUseCase(this.songsRepository, this.filesRepository, this.songsFilesRepository);
        this.deleteSongUseCase = new DeleteSongUserCase(this.songsRepository, this.filesRepository,
                this.songsFilesRepository);

        this.listAllSongsUseCase = new GetAllSongsUserCase(this.songsRepository);
        this.updateSongUseCase = new UpdateSongUserCase(this.songsRepository, this.songsRepository,
                this.filesRepository);

    }

    private void initViewModels() {

        this.cancionViewmodel = new CancionViewmodel(this.addSongUseCase,
                this.updateSongUseCase,
                this.deleteSongUseCase,
                this.listAllSongsUseCase);
        this.reproductorViewModel = new ReproductorViewmodel();
    }

    private void initHome() {
        JFXButton homeButton = new JFXButton("Home");
        FontIcon icon = new FontIcon("fa-home"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);

        homeButton.setGraphic(icon);
        homeButton.setMaxWidth(Double.MAX_VALUE);
        VBox.setVgrow(homeButton, Priority.ALWAYS);
        homeButton.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(homeButton, new Insets(10, 10, 10, 10));
        homeButton.setOnMouseClicked(mouseEvent -> {
            this.router.clear();
        });
        this.listaOpciones.getChildren().add(homeButton);

    }

    private void initSongsList() {
        JFXButton button = new JFXButton("Songs");
        FontIcon icon = new FontIcon("fa-music"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);

        button.setGraphic(icon);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(button, new Insets(10, 10, 10, 10));

        this.listaOpciones.getChildren().add(button);
        // cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/songs/list.fxml"));
        // anaydiar al enrutador para poder navegar
        this.router.add("songs", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CancionController cc = loader.getController();
        cc.setRouter(this.router);
        cc.setViewModels(this.cancionViewmodel, this.appViewModel, this.reproductorViewModel);
        button.setOnMouseClicked(mouseEvent -> {
            this.router.push("songs");
        });
    }

    private void initExit() {
        JFXButton button = new JFXButton("Exit");
        FontIcon icon = new FontIcon("fa-window-close"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);
        button.setGraphic(icon);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(button, new Insets(10, 10, 10, 10));
        this.listaOpciones.getChildren().add(button);
        button.setOnMouseClicked(mouseEvent -> {
            // se cierran
            if (this.reproductorViewModel != null) {
                this.reproductorViewModel.dispose();
            }
            if (this.songsRepository != null) {
                this.songsRepository.close();
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            LoginController lc = loader.getController();
            lc.setAppViewmodel(appViewModel);

            Stage stage = new Stage();
            stage.setTitle("SpotyDAM");
            // stage.setMaximized(true);
            stage.setScene(new Scene(root));
            stage.show();

            // Cerrar ventana de login
            ((Stage) this.contentPane.getScene().getWindow()).close();

            // javafx.application.Platform.exit();
        });
    }

    private void initSongForm() {

        // cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/songs/form.fxml"));
        // anaydiar al enrutador para poder navegar
        this.router.add("song", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CancionController cc = loader.getController();
        cc.setRouter(this.router);
        cc.setViewModels(this.cancionViewmodel, this.appViewModel, this.reproductorViewModel);
    }

    private void initConfig() {
        JFXButton button = new JFXButton("Settings");
        FontIcon icon = new FontIcon("fa-cogs"); // Usando Ikonli: https://kordamp.org/ikonli/
        icon.setIconSize(18);
        button.setGraphic(icon);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        VBox.setMargin(button, new Insets(10, 10, 10, 10));

        this.listaOpciones.getChildren().add(button);
        // cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/config/form.fxml"));
        // anaydiar al enrutador para poder navegar
        this.router.add("config", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        var cc = (AWindows) loader.getController();
        cc.setRouter(this.router);

        button.setOnMouseClicked(mouseEvent -> {
            this.router.push("config");
        });
    }

    private void configRouter() {
        this.router = new Router();
        this.router.setMain(this.contentPane);
    }

    public AppViewmodel getAppViewModel() {
        return appViewModel;
    }

    public void setAppViewModel(AppViewmodel appViewModel) {
        this.appViewModel = appViewModel;
        // this.initRepositories();
        // this.initUseCases();

        // this.initViewModels();
        this.initHome();
        // this.initSongsList();
        // this.initSongForm();

        // this.initConfig();
        this.initExit();

    }
}