package ies.sequeros.dam.presentacion.revisiones;

import ies.sequeros.dam.dominio.Mecanico;
import ies.sequeros.dam.presentacion.mecanicos.MecanicosViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ies.sequeros.dam.dominio.Revision;
import javafx.util.StringConverter;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import java.time.LocalDate;

public class RevisionDialogoController {
    public TextField kilometrosField;
    public TextArea comentarioArea;
    public ComboBox mecanicoComboBox;
    public ButtonType guardarButton;

    @FXML
    private DatePicker campoFecha;

    @FXML
    public void initialize() {
        campoFecha.setValue(LocalDate.now());


    }

    public DatePicker getCampoFecha() {
        return campoFecha;
    }

    public Revision construirRevision() {

        return new Revision(campoFecha.getValue(),Integer.parseInt(kilometrosField.getText()),
                comentarioArea.getText(),((Mecanico)mecanicoComboBox.getValue()).getId());
    }

    public void cargarRevision(Revision r) {
        //en principio no es necesario, ya que no se puede modificar
        campoFecha.setValue(r.getFecha());
        comentarioArea.setText(r.getComentario());
        kilometrosField.setText(String.valueOf(r.getKilometros()));

       // mecanicoComboBox.setValue();
    }
    public void setMecanicoViewModel(MecanicosViewModel viewModel){
        this.mecanicoComboBox.setConverter(new StringConverter<Mecanico>() {
            @Override
            public String toString(Mecanico m) {
                return (m != null) ? m.getNombre() : "";
            }

            @Override
            public Mecanico fromString(String string) {
                // No necesario si no editas texto directamente
                return null;
            }
        });
        this.mecanicoComboBox.setItems(viewModel.getMecanicosProperty());

    }
}
