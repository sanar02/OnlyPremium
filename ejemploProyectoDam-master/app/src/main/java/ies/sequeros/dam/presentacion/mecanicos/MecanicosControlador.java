package ies.sequeros.dam.presentacion.mecanicos;



import ies.sequeros.dam.dominio.Mecanico;
import ies.sequeros.dam.presentacion.navegacion.AWindows;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import org.controlsfx.control.GridView;

import java.io.IOException;

public class MecanicosControlador extends AWindows {
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
    private GridView<Mecanico> grid;

    private FilteredList<Mecanico> filtrados;
    private MecanicosViewModel viewModel;
    public MecanicosControlador() {
        super();
    }
    public void setViewModel(MecanicosViewModel viewModel) {
        this.viewModel = viewModel;
       filtrados = new FilteredList<>(this.viewModel.getMecanicosProperty());
        // El predicado se actualiza cada vez que cambia el texto del filtro
        this.searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrados.setPredicate(item -> {
                if (newVal == null || newVal.isEmpty()) return true;
                return item.getNombre().toLowerCase().contains(newVal.toLowerCase());
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
                this.viewModel.setEmptyCurrent();
                this.router.push("mecanico");
            }
        });
        this.initGrid();
    }
    @FXML
    public void initialize() {

    }

    private void initGrid() {
        grid.setCellFactory(gridViewCell -> {
            MecanicoCell c = new MecanicoCell();
         c.setOnDelete(item -> {
             boolean resultado=this.showMessageBooleano("¿Está seguro/a de borrar el elemento?","Confirmar borrado");
             if(resultado) {
                 try {
                     this.viewModel.removeMecanico(item);
                 } catch (Exception e) {
                     Alert alert = new Alert(Alert.AlertType.ERROR);

                     alert.setTitle("Error");
                     alert.setHeaderText(null);
                     alert.setContentText("Error al borrar el elemento:"+e.getMessage());
                 }
             }
         });
        
         c.setOnView( item -> {
            this.router.push("mecanico");
         });
            c.setOnEdit( item -> {
                //se coloca el actual y se pasa
                this.viewModel.setCurrent(item);
               this.router.push("mecanico");
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
