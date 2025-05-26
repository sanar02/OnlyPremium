package ies.sequeros.dam.presentacion.vehiculos;

import ies.sequeros.dam.dominio.Mecanico;
import ies.sequeros.dam.dominio.Revision;
import ies.sequeros.dam.dominio.Vehiculo;
import ies.sequeros.dam.presentacion.mecanicos.MecanicosViewModel;
import ies.sequeros.dam.presentacion.navegacion.AWindows;
import ies.sequeros.dam.presentacion.revisiones.RevisionDialogo;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.File;
import java.io.IOException;

public class VehiculoControlador extends AWindows {
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colKilometros;
    @FXML
    private TableColumn<Revision, String>  colMecanico;
    @FXML
    private TableView revisones;
    @FXML
    private Button imagenBtn;
    @FXML
    private MFXFontIcon nuevoMantenimientoIcon;
    @FXML
    private Button nuevoMantenimientoBtn;
    @FXML
    private TextField marcaField;
    @FXML
    private TextField modeloField;
    @FXML
    private TextField matriculaField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField pathImagenField;
    @FXML
    private Text titulo;

    @FXML
    private Button guardarBtn;
    @FXML
    private Button cancelarBtn;
    private VehiculosViewModel vehiculosViewModel;
    private MecanicosViewModel mecanicosViewModel;
    private ChangeListener<Vehiculo> escuchadorViewModel;
    private ObservableList<Revision> listarevisiones;
    public VehiculoControlador() {
        this.listarevisiones = FXCollections.observableArrayList();

    }

    public void setMecanicosViewModel(MecanicosViewModel mecanicosViewModel) {
        this.mecanicosViewModel = mecanicosViewModel;
    }

    public void setViewModel(VehiculosViewModel vehiculosViewModel) {

        this.vehiculosViewModel = vehiculosViewModel;
        this.escuchadorViewModel = (observableValue, item, t1) -> {
            if (this.vehiculosViewModel.currentProperty().get().getMatricula().isBlank()) {
                this.titulo.setText("Alta");
                this.matriculaField.setEditable(true);

            } else {
                this.titulo.setText("Modificar");
                this.matriculaField.setEditable(false);

            }
            this.colorField.setText(t1.getColor());
            this.pathImagenField.setText(t1.getPathimagen());
            this.marcaField.setText(t1.getMarca());
            this.modeloField.setText(t1.getModelo());
            this.matriculaField.setText(t1.getMatricula());
            this.listarevisiones.clear();
            this.listarevisiones.addAll(this.vehiculosViewModel.currentProperty().get().getRevisiones());

        };
        this.listarevisiones.clear();
        this.listarevisiones.addAll(this.vehiculosViewModel.currentProperty().get().getRevisiones());
        this.vehiculosViewModel.currentProperty().addListener(this.escuchadorViewModel);
    }

    @FXML
    public void initialize() {
        //configurar el table vielw
        this.colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        this.colKilometros.setCellValueFactory(new PropertyValueFactory<>("kilometros"));
       // this.colMecanico.setCellValueFactory(new PropertyValueFactory<>("mecanicoId"));

        this.colMecanico.setCellValueFactory(cellData -> {
            int mecanicoId = cellData.getValue().getMecanicoId();
            Mecanico m=this.mecanicosViewModel.getMecanicosProperty().get().filtered(mecanico ->
            {
                return mecanicoId==mecanico.getId();
            }).get(0);
            return new ReadOnlyStringWrapper(m != null ? m.getNombre() : "");
        });
        this.revisones.setItems(this.listarevisiones);
        //validación y botones
        ValidationSupport validationSupport = new ValidationSupport();
        imagenBtn.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar imagen");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("imágenes", "*.jpg", "*.jpeg", "*.png")
            );
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {
                // this.imageView.setImage(new Image(file.toURI().toString()));
                this.pathImagenField.textProperty().set(file.getAbsolutePath());
            }
        });
        // Desactivar botón hasta que sea válido
        guardarBtn.disableProperty().bind(validationSupport.invalidProperty());

        guardarBtn.setOnAction(e -> {
            this.vehiculosViewModel.currentProperty().get().setMatricula(this.matriculaField.getText());
            this.vehiculosViewModel.currentProperty().get().setColor(this.colorField.getText());
            this.vehiculosViewModel.currentProperty().get().setPathimagen(this.pathImagenField.getText());
            this.vehiculosViewModel.currentProperty().get().setMarca(this.marcaField.getText());
            this.vehiculosViewModel.currentProperty().get().setModelo(this.modeloField.getText());
            try {
                this.vehiculosViewModel.saveCurrent();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText(ex.getMessage());

            }
            this.router.pop();
        });

        this.cancelarBtn.setOnMouseClicked(event -> {
            this.router.pop();
        });
        validationSupport.registerValidator(marcaField, Validator.createEmptyValidator("Marca requerida"));
        validationSupport.registerValidator(modeloField, Validator.createEmptyValidator("Modelo requerido"));
        validationSupport.registerValidator(matriculaField, Validator.createEmptyValidator("Matrícula requerida"));
        validationSupport.registerValidator(colorField, Validator.createEmptyValidator("Color requerido"));
        validationSupport.registerValidator(pathImagenField, Validator.createEmptyValidator("Imagen requerida"));

        this.nuevoMantenimientoBtn.setGraphic(nuevoMantenimientoIcon);
        this.nuevoMantenimientoBtn.setOnMouseClicked(event -> {
            RevisionDialogo dialogo = null;
            try {
                dialogo = new RevisionDialogo();
                dialogo.setMecanicoViewModel(this.mecanicosViewModel);
            } catch (IOException e) {
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText(e.getMessage());
                alerta.show();

            }

            assert dialogo != null;
            dialogo.showAndWait().ifPresent(revision -> {
                    Vehiculo v=this.vehiculosViewModel.getCurrent();
                    v.addRevision(revision);
                    this.listarevisiones.add(revision);


            });

        });
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void reset() {

    }
}