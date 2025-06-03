package es.burgueses.presentacion.controladores;

import net.bytebuddy.dynamic.DynamicType.Builder.MethodDefinition.ParameterDefinition.Initial;

import java.io.IOException;
import java.util.ResourceBundle;
import java.net.URL;

import es.burgueses.aplicacion.cancion.AddSongUserCase;
import es.burgueses.aplicacion.cancion.DeleteSongUserCase;
import es.burgueses.aplicacion.cancion.GetSongUserCase;
import es.burgueses.aplicacion.cancion.ModSongUserCase;
import es.burgueses.aplicacion.listaReproduccion.AddListUserCase;
import es.burgueses.aplicacion.listaReproduccion.AddSongToListUserCase;
import es.burgueses.aplicacion.listaReproduccion.DeleteListUserCase;
import es.burgueses.aplicacion.listaReproduccion.DeleteSongFromListUserCase;
import es.burgueses.aplicacion.listaReproduccion.GetListUserCase;
import es.burgueses.aplicacion.listaReproduccion.ModListUserCase;
import es.burgueses.aplicacion.usuario.AddUserUserCase;
import es.burgueses.aplicacion.usuario.DeleteUserUserCase;
import es.burgueses.aplicacion.usuario.GetUserUserCase;
import es.burgueses.aplicacion.usuario.ModUserUserCase;
import es.burgueses.infraestructura.CancionMongo;
import es.burgueses.infraestructura.CancionRepositorioEnMemoria;
import es.burgueses.infraestructura.ListaMongo;
import es.burgueses.infraestructura.UsuarioMongo;
import es.burgueses.presentacion.cancion.CancionViewmodel;
import es.burgueses.presentacion.navegacion.Router;
import es.burgueses.presentacion.usuario.UsuarioViewmodel;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import java.util.Optional;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.application.Platform;

public class MainController implements Initializable {
    private final Stage stage;

    // Add repository fields
    private CancionMongo cancionRepositorio;
    private UsuarioMongo usuarioRepositorio;
    private ListaMongo listaRepositorio;

    private double xOffset;
    private double yOffset;
    private final ToggleGroup toggleGroup;
    private Router router;
    private CancionViewmodel cancionViewmodel;
    private AddSongUserCase addSongUserCase;
    private DeleteSongUserCase deleteSongUserCase;
    private GetSongUserCase getSongUserCase;
    private ModSongUserCase modSongUserCase;

    private UsuarioViewmodel usuarioViewmodel;
    private AddUserUserCase addUserUserCase;
    private DeleteUserUserCase deleteUserUserCase;
    private GetUserUserCase getUserUserCase;
    private ModUserUserCase modUserUserCase;

    // Aquí iría el viewmodel de lista de reproduccion
    private AddListUserCase addListUserCase;
    private AddSongToListUserCase addSongToListUserCase;
    private DeleteListUserCase deleteListUserCase;
    private DeleteSongFromListUserCase deleteSongFromListUserCase;
    private GetListUserCase getListUserCase;
    private ModListUserCase modListUserCase;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private MFXScrollPane scrollPane;
    @FXML
    private VBox navBar;
    @FXML
    private StackPane contentPane;
    @FXML
    private StackPane logoContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.router = new Router();
        this.router.setMain(this.contentPane);
        ScrollUtils.addSmoothScrolling(scrollPane);
        Image image = new Image(String.valueOf(getClass().getResource("/images/logo.png")), 32, 32, true, true);
        ImageView logo = new ImageView(image);
        Circle clip = new Circle(30);
        clip.centerXProperty().bind(logo.layoutBoundsProperty().map(Bounds::getCenterX));
        clip.centerYProperty().bind(logo.layoutBoundsProperty().map(Bounds::getCenterY));
        logo.setClip(clip);
        logoContainer.getChildren().add(logo);
        // inicialización de los repositorios
        this.iniciarRepositorios();
        // iniciar los casos de uso
        this.iniciarCasosDeUso();
        // inicializar los viewmodels
        this.iniciarViewModels();

        // inicializar los controles que todavía no están creados
        // this.initListadoMecanicos();
        // this.initFormularioMecanico();
        // this.initListadoVehiculos();
        // this.initFormularioVehiculo();

        // opcion de salir
        ToggleButton toggle = createToggle("antf-account-book", "Salir");
        // se cambia el elemento
        toggle.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Salir");
            alert.setHeaderText("Salir");
            alert.setContentText("Confirmar salir de la aplicacion");
            Optional<ButtonType> resultado = alert.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                Platform.exit();
            }
        });
        // añadir
        navBar.getChildren().add(toggle);
    }

    public MainController(Stage stage) {
        this.stage = stage;
        this.toggleGroup = new ToggleGroup();
        ToggleButtonsUtil.addAlwaysOneSelectedSupport(toggleGroup);
    }

    private ToggleButton createToggle(String icon, String text) {
        return createToggle(icon, text, 0);
    }

    private ToggleButton createToggle(String icon, String text, double rotate) {
        MFXIconWrapper wrapper = new MFXIconWrapper("fas-user", 24, 32);
        MFXRectangleToggleNode toggleNode = new MFXRectangleToggleNode(text, wrapper);
        toggleNode.setAlignment(Pos.CENTER_LEFT);
        toggleNode.setMaxWidth(Double.MAX_VALUE);
        toggleNode.setToggleGroup(toggleGroup);
        // if (rotate != 0) wrapper.getIcon().setRotate(rotate);
        return toggleNode;
    }

    // Necesito a Pomares
    private void iniciarRepositorios() {
        this.cancionRepositorio = new CancionMongo();
        this.usuarioRepositorio = new UsuarioMongo();
        this.listaRepositorio = new ListaMongo();

    }

    private void iniciarCasosDeUso() {
        this.addSongUserCase = new AddSongUserCase(this.cancionRepositorio);
        this.deleteSongUserCase = new DeleteSongUserCase(this.cancionRepositorio);
        this.getSongUserCase = new GetSongUserCase(this.cancionRepositorio);
        this.modSongUserCase = new ModSongUserCase(this.cancionRepositorio);

        this.addUserUserCase = new AddUserUserCase(this.usuarioRepositorio);
        this.deleteUserUserCase = new DeleteUserUserCase(this.usuarioRepositorio);
        this.getUserUserCase = new GetUserUserCase(this.usuarioRepositorio);
        this.modUserUserCase = new ModUserUserCase(this.usuarioRepositorio);

        this.addListUserCase = new AddListUserCase(this.listaRepositorio);
        this.addSongToListUserCase = new AddSongToListUserCase(this.listaRepositorio, this.cancionRepositorio);
        this.deleteListUserCase = new DeleteListUserCase(this.listaRepositorio);
        this.deleteSongFromListUserCase = new DeleteSongFromListUserCase(this.listaRepositorio);
        this.getListUserCase = new GetListUserCase(this.listaRepositorio);
        this.modListUserCase = new ModListUserCase(this.listaRepositorio);
    }

    // Tengo primero que crear los viewmodels
    private void iniciarViewModels(){
        this.cancionViewmodel= new CancionViewmodel(this.addSongUserCase,
                this.deleteSongUserCase,
                this.getSongUserCase,
                this.modSongUserCase);
      //se cargan los datos
        this.cancionViewmodel.load();
        this.usuarioViewmodel = new UsuarioViewmodel(this.addUserUserCase,
                                                    this.deleteUserUserCase,
                                                    this.getUserUserCase,
                                                    this.modUserUserCase);
        this.usuarioViewmodel.load();
    }

// Tengo que crear los viewmodels
/*
 * private void initFormularioMecanico(){
 * //cargar el fxml
 * FXMLLoader loader = new
 * FXMLLoader(getClass().getResource("/fxml/mecanicos/formulario.fxml"));
 * //anaydiar al enrutador para poder navegar
 * this.router.add("mecanico", loader);
 * try {
 * loader.load();
 * } catch (IOException e) {
 * e.printStackTrace();
 * 
 * }
 * MecanicoControlador cc= loader.getController();
 * cc.setRouter(this.router);
 * cc.setMecanicosViewModel(this.mecanicosViewModel);
 * cc.setParent(contentPane);
 * 
 * }
 */
}

/*
 * private void initListadoMecanicos(){
 * //cargar el fxml
 * FXMLLoader loader = new
 * FXMLLoader(getClass().getResource("/fxml/mecanicos/listado.fxml"));
 * //añadir al enrutador para poder navegar
 * this.router.add("mecanicos", loader);
 * try {
 * loader.load();
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * MecanicosControlador cc= loader.getController();
 * cc.setRouter(this.router);
 * cc.setViewModel(this.mecanicosViewModel);
 * //configurar el menuitem
 * ToggleButton toggle = createToggle("antf-account-book", "Mecanicos");
 * //se cambia el elemento
 * toggle.setOnAction(event -> {
 * router.push("mecanicos");
 * toggle.setSelected(true);
 * 
 * });
 * //añadir
 * navBar.getChildren().add(toggle);
 * }
 */

/*
 * private void initFormularioVehiculo(){
 * //cargar el fxml
 * FXMLLoader loader = new
 * FXMLLoader(getClass().getResource("/fxml/vehiculos/formulario.fxml"));
 * //anaydiar al enrutador para poder navegar
 * this.router.add("vehiculo", loader);
 * try {
 * loader.load();
 * } catch (IOException e) {
 * e.printStackTrace();
 * 
 * }
 * VehiculoControlador cc= loader.getController();
 * // cc.setMecanicosViewModel(this.mecanicosViewModel);
 * cc.setMecanicosViewModel(this.mecanicosViewModel);
 * cc.setViewModel(this.vehiculosViewModel);
 * cc.setRouter(this.router);
 * cc.setParent(contentPane);
 * 
 * }
 */

/*
 * private void initListadoVehiculos(){
 * //cargar el fxml
 * FXMLLoader loader = new
 * FXMLLoader(getClass().getResource("/fxml/vehiculos/listado.fxml"));
 * //anaydiar al enrutador para poder navegar
 * this.router.add("vehiculos", loader);
 * try {
 * loader.load();
 * } catch (IOException e) {
 * e.printStackTrace();
 * }
 * VehiculosControlador cc= loader.getController();
 * cc.setMecanicosViewModel(this.mecanicosViewModel);
 * cc.setViewModel(this.vehiculosViewModel);
 * 
 * cc.setRouter(this.router);
 * //configurar el menuitem
 * ToggleButton toggle = createToggle("antf-account-book", "Vehiculos");
 * //se cambia el elemento
 * toggle.setOnAction(event -> {
 * router.push("vehiculos");
 * toggle.setSelected(true);
 * 
 * });
 * //añadir
 * navBar.getChildren().add(toggle);
 * }
 * }
 */
