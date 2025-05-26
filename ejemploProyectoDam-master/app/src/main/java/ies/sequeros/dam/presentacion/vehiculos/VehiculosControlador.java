package ies.sequeros.dam.presentacion.vehiculos;



import ies.sequeros.dam.dominio.Vehiculo;
import ies.sequeros.dam.presentacion.mecanicos.MecanicosViewModel;
import ies.sequeros.dam.presentacion.navegacion.AWindows;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.controlsfx.control.GridView;

public class VehiculosControlador extends AWindows {
    @FXML
    private Label titulo;
    @FXML
    private MFXButton add;
    @FXML
    private MFXButton up;
    @FXML
    private MFXTextField searchField;
    @FXML
    private MFXButton search;
    @FXML
    private GridView<Vehiculo> grid;
    private FilteredList<Vehiculo> filtrados;
    private VehiculosViewModel vehiculosViewModel;
    private MecanicosViewModel mecanicosViewModel;
    public VehiculosControlador() {
        super();

    }
    @FXML
    public void initialize() {

    }
    public void setMecanicosViewModel(MecanicosViewModel mecanicosViewModel) {
        this.mecanicosViewModel = mecanicosViewModel;
    }
    public void setViewModel(VehiculosViewModel viewModel) {
        this.vehiculosViewModel = viewModel;
        filtrados = new FilteredList<>(this.vehiculosViewModel.getVehiculosProperty());
        // El predicado se actualiza cada vez que cambia el texto del filtro
        this.searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrados.setPredicate(item -> {
                if (newVal == null || newVal.isEmpty()) return true;
                return item.getMatricula().toLowerCase().contains(newVal.toLowerCase())
                        ||  item.getMarca().toLowerCase().contains(newVal.toLowerCase())
                        ||  item.getModelo().toLowerCase().contains(newVal.toLowerCase());
            });
        });
        this.grid.setItems(filtrados);
        this.up.setOnMouseClicked(event -> {
            //se va hacia atras
            if (this.router != null) {
                this.router.pop();
            }
        });
        this.add.setOnMouseClicked(event -> {
            if (this.router != null) {
                this.vehiculosViewModel.setEmptyCurrent();
                this.router.push("vehiculo");
            }
        });
        this.initGrid();
    }


    private void initGrid() {
        grid.setCellFactory(gridViewCell -> {
            VehiculoCell c = new VehiculoCell();
            c.setOnDelete(item -> {
                        boolean resultado = this.showMessageBooleano("¿Está seguro/a de borrar el elemento?", "Confirmar borrado");
                        if (resultado) {
                            try {
                                this.vehiculosViewModel.removeVehiculo(item);
                            } catch (Exception e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);

                                alert.setTitle("Error");
                                alert.setHeaderText(null);
                                alert.setContentText("Error al borrar el elemento:" + e.getMessage());
                            }
                        }
                    });
        
         c.setOnView( item -> {
            this.router.push("vehiculo");
         });
            c.setOnEdit( item -> {
                this.vehiculosViewModel.setCurrent(item);
               this.router.push("vehiculo");
            });
            return c;
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
