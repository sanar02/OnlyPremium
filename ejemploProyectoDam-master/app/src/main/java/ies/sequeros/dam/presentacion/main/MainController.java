package ies.sequeros.dam.presentacion.main;


import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import ies.sequeros.dam.aplicacion.mecanicos.ActualizarMecanicoCasoUso;
import ies.sequeros.dam.aplicacion.mecanicos.BorrarMecanicoCasoUso;
import ies.sequeros.dam.aplicacion.mecanicos.NuevoMecanicoCasoUso;
import ies.sequeros.dam.aplicacion.mecanicos.ObtenerMecanicoCasoUso;
import ies.sequeros.dam.aplicacion.vehiculos.ActualizarVehiculoCasoUso;
import ies.sequeros.dam.aplicacion.vehiculos.BorrarVehiculoCasoUso;
import ies.sequeros.dam.aplicacion.vehiculos.NuevoVehiculoCasoUso;
import ies.sequeros.dam.aplicacion.vehiculos.ObtenerVehiculosCasoUso;
import ies.sequeros.dam.dominio.IAlmacenImagenVehiculo;
import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.infraestructura.AlmacenImagenVehiculoEnLocal;
import ies.sequeros.dam.infraestructura.MecanicoRepositorioEnFichero;
import ies.sequeros.dam.infraestructura.MecanicoRepositorioMongo;
import ies.sequeros.dam.infraestructura.VehiculoRepositorioEnFichero;
import ies.sequeros.dam.presentacion.mecanicos.MecanicoControlador;
import ies.sequeros.dam.presentacion.mecanicos.MecanicosControlador;
import ies.sequeros.dam.presentacion.mecanicos.MecanicosViewModel;
import ies.sequeros.dam.presentacion.navegacion.Router;
import ies.sequeros.dam.presentacion.vehiculos.VehiculoControlador;
import ies.sequeros.dam.presentacion.vehiculos.VehiculosControlador;
import ies.sequeros.dam.presentacion.vehiculos.VehiculosViewModel;
import io.github.palexdev.materialfx.controls.MFXIconWrapper;
import io.github.palexdev.materialfx.controls.MFXRectangleToggleNode;
import io.github.palexdev.materialfx.controls.MFXScrollPane;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import io.github.palexdev.materialfx.utils.ToggleButtonsUtil;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final Stage stage;

    private double xOffset;
    private double yOffset;
    private final ToggleGroup toggleGroup;
    private Router router;
    private MecanicosViewModel mecanicosViewModel;
    private ActualizarMecanicoCasoUso actualizarMecanicoCasoUso;
    private BorrarMecanicoCasoUso borrarMecanicoCasoUso;
    private ObtenerMecanicoCasoUso obtenerMecanicoCasoUso;
    private NuevoMecanicoCasoUso nuevoMecanicoCasoUso;

    private VehiculosViewModel vehiculosViewModel;
    private ActualizarVehiculoCasoUso actualizarVehiculoCasoUso;
    private BorrarVehiculoCasoUso borrarVehiculoCasoUso;
    private ObtenerVehiculosCasoUso obtenerVehiculosCasoUso;
    private NuevoVehiculoCasoUso nuevoVehiculoCasoUso;

    private IMecanicoRepositorio mecanicoRepositorio;
    private IVehiculoRepositorio vehiculoRepositorio;
    private IAlmacenImagenVehiculo almacenImagenVehiculo;
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

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        this.router= new Router();
        this.router.setMain(this.contentPane);
        ScrollUtils.addSmoothScrolling(scrollPane);
        Image image = new Image(String.valueOf(getClass().getResource("/images/logo.png")), 32, 32, true, true);
        ImageView logo = new ImageView(image);
        Circle clip = new Circle(30);
        clip.centerXProperty().bind(logo.layoutBoundsProperty().map(Bounds::getCenterX));
        clip.centerYProperty().bind(logo.layoutBoundsProperty().map(Bounds::getCenterY));
        logo.setClip(clip);
        logoContainer.getChildren().add(logo);
        //inicialización de los repositorios
        this.iniciarRepositorios();
        //iniciar los casos de us
        this.iniciarCasosDeUso();
        //iniciar los viewmodels
        this.iniciarViewModels();

        this.initListadoMecanicos();
        this.initFormularioMecanico();
        this.initListadoVehiculos();
        this.initFormularioVehiculo();

        //opcion de salir
        ToggleButton toggle = createToggle("antf-account-book", "Salir");
        //se cambia el elemento
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
        //añadir
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
        //if (rotate != 0) wrapper.getIcon().setRotate(rotate);
        return toggleNode;
    }
    private void iniciarRepositorios() {
        //this.mecanicoRepositorio= new MecanicoRepositorioEnFichero("data/mecanicos.json");
        this.vehiculoRepositorio= new VehiculoRepositorioEnFichero("data/vehiculos.json");
        this.almacenImagenVehiculo= new AlmacenImagenVehiculoEnLocal("imagenes");
        this.mecanicoRepositorio= new MecanicoRepositorioMongo();

    }
    private void iniciarCasosDeUso(){
        this.nuevoMecanicoCasoUso= new NuevoMecanicoCasoUso(this.mecanicoRepositorio);
        this.borrarMecanicoCasoUso=new BorrarMecanicoCasoUso(this.mecanicoRepositorio);
        this.actualizarMecanicoCasoUso=new ActualizarMecanicoCasoUso(this.mecanicoRepositorio);
        this.obtenerMecanicoCasoUso=new ObtenerMecanicoCasoUso(this.mecanicoRepositorio);

        this.nuevoVehiculoCasoUso= new NuevoVehiculoCasoUso(this.vehiculoRepositorio,this.almacenImagenVehiculo);
        this.actualizarVehiculoCasoUso= new ActualizarVehiculoCasoUso(this.vehiculoRepositorio,this.almacenImagenVehiculo);
        this.borrarVehiculoCasoUso=new BorrarVehiculoCasoUso(this.vehiculoRepositorio,this.almacenImagenVehiculo);
        this.obtenerVehiculosCasoUso=new ObtenerVehiculosCasoUso(this.vehiculoRepositorio);

    }
    private void iniciarViewModels(){
        this.mecanicosViewModel= new MecanicosViewModel(this.actualizarMecanicoCasoUso,
                this.borrarMecanicoCasoUso,this.nuevoMecanicoCasoUso,this.obtenerMecanicoCasoUso);
      //se cargan los datos
        this.mecanicosViewModel.load();
        this.vehiculosViewModel= new VehiculosViewModel(this.actualizarVehiculoCasoUso,this.borrarVehiculoCasoUso,this.nuevoVehiculoCasoUso,this.obtenerVehiculosCasoUso);
        this.vehiculosViewModel.load();
    }
    private void initFormularioMecanico(){
        //cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mecanicos/formulario.fxml"));
        //anaydiar al enrutador para poder navegar
        this.router.add("mecanico", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();

        }
        MecanicoControlador cc= loader.getController();
        cc.setRouter(this.router);
        cc.setMecanicosViewModel(this.mecanicosViewModel);
        cc.setParent(contentPane);

    }
    private void initListadoMecanicos(){
        //cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mecanicos/listado.fxml"));
        //anaydiar al enrutador para poder navegar
        this.router.add("mecanicos", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        MecanicosControlador cc= loader.getController();
        cc.setRouter(this.router);
        cc.setViewModel(this.mecanicosViewModel);
        //configurar el menuitem
        ToggleButton toggle = createToggle("antf-account-book", "Mecanicos");
        //se cambia el elemento
        toggle.setOnAction(event -> {
            router.push("mecanicos");
            toggle.setSelected(true);

        });
        //añadir
        navBar.getChildren().add(toggle);
    }
    private void initFormularioVehiculo(){
        //cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vehiculos/formulario.fxml"));
        //anaydiar al enrutador para poder navegar
        this.router.add("vehiculo", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();

        }
        VehiculoControlador cc= loader.getController();
      //  cc.setMecanicosViewModel(this.mecanicosViewModel);
cc.setMecanicosViewModel(this.mecanicosViewModel);
cc.setViewModel(this.vehiculosViewModel);
        cc.setRouter(this.router);
        cc.setParent(contentPane);

    }
    private void initListadoVehiculos(){
        //cargar el fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/vehiculos/listado.fxml"));
        //anaydiar al enrutador para poder navegar
        this.router.add("vehiculos", loader);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        VehiculosControlador cc= loader.getController();
        cc.setMecanicosViewModel(this.mecanicosViewModel);
        cc.setViewModel(this.vehiculosViewModel);

        cc.setRouter(this.router);
        //configurar el menuitem
        ToggleButton toggle = createToggle("antf-account-book", "Vehiculos");
        //se cambia el elemento
        toggle.setOnAction(event -> {
            router.push("vehiculos");
            toggle.setSelected(true);

        });
        //añadir
        navBar.getChildren().add(toggle);
    }
}
