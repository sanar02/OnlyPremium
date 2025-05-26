package ies.sequeros.dam.presentacion.mecanicos;

import ies.sequeros.dam.dominio.Mecanico;
import ies.sequeros.dam.presentacion.navegacion.AWindows;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import javafx.beans.value.ChangeListener;


public class MecanicoControlador extends AWindows {
    @FXML
    private Text titulo;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField emailField;
    @FXML
    private CheckBox activoCheck;
    @FXML
    private TextArea descripcionArea;
    @FXML
    private Button guardarBtn;
    @FXML
    private Button cancelarBtn;
    private ValidationSupport validationSupport;
    private MecanicosViewModel mecanicosViewModel;
    private ChangeListener<Mecanico> escuchadorViewModel;
    public MecanicoControlador() {


    }
    public void setMecanicosViewModel(MecanicosViewModel mecanicosViewModel) {

        this.mecanicosViewModel = mecanicosViewModel;
        this.escuchadorViewModel= (observableValue, item, t1) -> {
            if (this.mecanicosViewModel.currentProperty().get().getId() == -1) {
                this.titulo.setText("Alta");
            } else {
                this.titulo.setText("Modificar");
            }
            this.emailField.setText(t1.getEmail());
            this.activoCheck.setSelected(t1.isActivo());
            this.descripcionArea.setText(t1.getDescripcion());
            this.nombreField.setText(t1.getNombre());

        };
        this.mecanicosViewModel.currentProperty().addListener(this.escuchadorViewModel);
    }


    @FXML
    public void initialize() {
        validationSupport = new ValidationSupport();
        validationSupport.registerValidator(nombreField,
                Validator.createEmptyValidator("El nombre es obligatorio"));
        validationSupport.registerValidator(emailField,
                Validator.createRegexValidator("Email inválido","[^@]+@[^\\.]+\\..+", org.controlsfx.validation.Severity.ERROR));

        // Desactivar botón hasta que sea válido
        guardarBtn.disableProperty().bind(validationSupport.invalidProperty());

        guardarBtn.setOnAction(e -> {
            this.mecanicosViewModel.currentProperty().get().setEmail(this.emailField.getText());
            this.mecanicosViewModel.currentProperty().get().setActivo(this.activoCheck.isSelected());
            this.mecanicosViewModel.currentProperty().get().setDescripcion(this.descripcionArea.getText());
            this.mecanicosViewModel.currentProperty().get().setNombre(this.nombreField.getText());
            try {
                this.mecanicosViewModel.saveCurrent();
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
    }

    @Override
    public void init() {

    }

    @Override
    public void stop() {
        //se
        //this.mecanicosViewModel.currentProperty().removeListener(this.escuchadorViewModel);
    }

    @Override
    public void reset() {

    }
}