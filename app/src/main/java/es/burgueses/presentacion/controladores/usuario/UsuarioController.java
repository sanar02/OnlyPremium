package es.burgueses.presentacion.controladores.usuario;

import es.burgueses.dominio.Usuario;
import es.burgueses.presentacion.controladores.navegacion.AWindows;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class UsuarioController extends AWindows {

    @FXML
    private TextField apodoField;
    @FXML
    private TextField nombreField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private TextField pathImagenField;
    @FXML
    private CheckBox activoCheck;
    @FXML
    private CheckBox Admini;
    @FXML
    private Button crearButton;

    private UsuarioViewmodel usuarioViewmodel;

    public void setViewModel(UsuarioViewmodel usuarioViewmodel) {
        this.usuarioViewmodel = usuarioViewmodel;
    }

    @FXML
    public void initialize() {
        crearButton.setOnAction(this::crearUsuario);
    }

    private void crearUsuario(ActionEvent event) {
        String contrasena = contrasenaField.getText();
        String pathImagen = pathImagenField.getText();

        if (contrasena == null || contrasena.isEmpty() || contrasena.length() < 6) {
            mostrarError("La contraseña no puede ser nula, vacía o menor de 6 caracteres.");
            return;
        }
        if (pathImagen == null || pathImagen.isEmpty()) {
            mostrarError("La ruta de la imagen no puede estar vacía.");
            return;
        }

        Usuario.TipoUsuario tipo = Admini.isSelected() ? Usuario.TipoUsuario.ADMINISTRADOR : Usuario.TipoUsuario.USUARIO;

        try {
            // Crear usuario directamente usando el caso de uso
            usuarioViewmodel.getCasoUsoAgregarUsuario().addUser(
                contrasena,
                nombreField.getText(),
                apodoField.getText(),
                pathImagen,
                activoCheck.isSelected(),
                null, // fechaAlta (null para que se ponga LocalDate.now())
                tipo
            );
            limpiarCampos();
        } catch (Exception e) {
            mostrarError("Error al crear usuario: " + e.getMessage());
        }
    }

    private void limpiarCampos() {
        apodoField.clear();
        nombreField.clear();
        contrasenaField.clear();
        pathImagenField.clear();
        activoCheck.setSelected(true);
        Admini.setSelected(false);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}