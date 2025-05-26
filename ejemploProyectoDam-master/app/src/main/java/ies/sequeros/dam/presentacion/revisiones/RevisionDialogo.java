package ies.sequeros.dam.presentacion.revisiones;

import ies.sequeros.dam.dominio.Mecanico;
import ies.sequeros.dam.dominio.Revision;
import ies.sequeros.dam.presentacion.mecanicos.MecanicosViewModel;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.io.IOException;
import java.time.LocalDate;

public class RevisionDialogo extends Dialog<Revision> {
    private final ValidationSupport validationSupport = new ValidationSupport();

    private RevisionDialogoController controller;

    public RevisionDialogo() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/revisiones/RevisionDialogo.fxml"));

        DialogPane dialogPane = loader.load(); // Carga el DialogPane como root
        controller = loader.getController();

        // Configura este Dialog usando el DialogPane del FXML
        setDialogPane(dialogPane);
        setTitle("Nueva Revisión");

        // Botón "Guardar"
        ButtonType guardarButtonType = dialogPane.getButtonTypes().stream()
                .filter(bt -> ButtonBar.ButtonData.OK_DONE.equals(bt.getButtonData()))
                .findFirst().orElse(null);

        Node guardarButton = dialogPane.lookupButton(guardarButtonType);
        guardarButton.setDisable(true);
        //se tiene que hacer esto para que no de fallo, ya que no se encuentra
        //cargados los nodos
        Platform.runLater(() -> {
            validationSupport.registerValidator(controller.getCampoFecha(), Validator.createPredicateValidator(
                    o -> {
                        return o != null && !((LocalDate) o).isBefore(LocalDate.now());
                    }, "La fecha ha de ser posterior"));
            validationSupport.registerValidator(
                    controller.kilometrosField,
                    Validator.createPredicateValidator(
                            input -> {
                                try {
                                    int value = Integer.parseInt(input.toString());
                                    return value >= 1 && value <= 5000000;
                                } catch (NumberFormatException e) {
                                    return false;
                                }
                            },
                            "Debe ser un número entre 1 y 100"
                    )
            );
            validationSupport.registerValidator(controller.comentarioArea, Validator.createEmptyValidator("Matrícula requerida"));
guardarButton.disableProperty().bind(validationSupport.invalidProperty());
        });


        setResultConverter(dialogButton -> {
            if (dialogButton == guardarButtonType) {
                return controller.construirRevision();
            }
            return null;
        });
    }
    public void setMecanicoViewModel(MecanicosViewModel viewmodel){
        this.controller.setMecanicoViewModel(viewmodel);
    }
    public void inicializarCon(Revision revision) {
        controller.cargarRevision(revision);
    }
}
