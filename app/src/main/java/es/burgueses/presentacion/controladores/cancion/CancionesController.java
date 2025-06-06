package es.burgueses.presentacion.controladores.cancion;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxcore.controls.Label;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.media.MediaPlayer;
import org.controlsfx.control.GridView;

import es.burgueses.dominio.Cancion;
import es.burgueses.presentacion.controladores.navegacion.AWindows;
import es.burgueses.presentacion.utils.AppViewmodel;
import es.burgueses.presentacion.utils.ReproductorViewmodel;

import java.util.Set;

public class CancionesController extends AWindows{
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
    private GridView<Cancion> grid;

    private FilteredList<Cancion> filtrados;
    private CancionViewmodel viewModel;
    private AppViewmodel appViewModel;
    private ReproductorViewmodel playerViewModel;

    public CancionesController() {
        super();
    }

    public void setViewModels(CancionViewmodel viewModel, AppViewmodel appViewModel, ReproductorViewmodel reproductorViewModel) {
        this.viewModel = viewModel;
        this.appViewModel = appViewModel;
        this.playerViewModel = reproductorViewModel;
        filtrados = new FilteredList<Cancion>(this.viewModel.getCancionProperty());
        // El predicado se actualiza cada vez que cambia el texto del filtro
        this.searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrados.setPredicate(item -> {
                if (newVal == null || newVal.isEmpty()) return true;
                return item.getTitulo().toLowerCase().contains(newVal.toLowerCase());
            });
        });
        this.grid.setItems(filtrados);
        this.up.setOnMouseClicked(event -> {
            //se va hacia atras
            if (this.router != null) {
                this.playerViewModel.stop();
                this.router.pop();
            }
        });
        this.add.setOnMouseClicked(event -> {
            if (this.router != null) {
                this.viewModel.setEmptyCurrent();
                this.viewModel.setEditMode(true);
                this.playerViewModel.stop();
                this.router.push("song");
            }
        });
        this.initGrid();
    }

    @FXML
    public void initialize() {

    }

    private void initGrid() {
        grid.setCellFactory(gridViewCell -> {
            CancionCell c = new CancionCell();
            c.setOnDelete(item -> {
                boolean resultado = this.showMessageBooleano("¿Está seguro/a de borrar el elemento?", "Confirmar borrado");
                if (resultado) {
                    try {
                        this.viewModel.removeSong(item);
                    } catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);

                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Error al borrar el elemento:" + e.getMessage());
                    }
                }
            });

            c.setOnView(item -> {
                //solo ver
                this.viewModel.setCurrent(item);
                this.viewModel.setEditMode(false);
                this.router.push("song");
            });
            c.setOnEdit(item -> {
                //modod edición

                //se coloca el actual y se pasa
                this.viewModel.setCurrent(item);
                this.viewModel.setEditMode(true);
                this.router.push("song");
            });
            c.setOnPlay( item->{
                if( this.playerViewModel.currentTrackProperty().get()==null || !this.playerViewModel.currentTrackProperty().get().equals(item.getPath()))
                    this.playerViewModel.setSong(item.getPath());
                var status=this.playerViewModel.getStatus();
                disablePlayButtonfromGrid();
                if(this.playerViewModel.getStatus()== MediaPlayer.Status.READY || this.playerViewModel.getStatus()== MediaPlayer.Status.UNKNOWN || this.playerViewModel.getStatus()== MediaPlayer.Status.PAUSED || this.playerViewModel.getStatus()== MediaPlayer.Status.STOPPED)
                    this.playerViewModel.play();
                else
                    this.playerViewModel.pause();
            });
            return c;
        });
    }

    /**
     * para desactivar todos los botonoes de play
     */
    private void disablePlayButtonfromGrid(){
        Set<Node> nodos = this.grid.lookupAll(".grid-cell");
        for (Node n : nodos) {
            CancionCell c = (CancionCell) n;
            c.stop();

        }
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {
        this.playerViewModel.stop();
    }

    @Override
    public void reset() {

    }
}
