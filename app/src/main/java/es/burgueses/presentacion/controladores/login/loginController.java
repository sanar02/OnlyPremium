package es.burgueses.presentacion.controladores.login;

import es.burgueses.presentacion.controladores.principal.PaginaPrincipalController;
import es.burgueses.presentacion.utils.AppViewmodel;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private AppViewmodel appViewModel;

    public void setAppViewmodel(AppViewmodel appViewModel) {
        this.appViewModel = appViewModel;
    }

    @FXML
    private void handleLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        if(appViewModel!=null) {
            boolean validation=appViewModel.login(user, pass);
            if (validation) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/principal.fxml"));
                    Parent root = loader.load();
                    PaginaPrincipalController pc=loader.getController();
                    //se le pasa el viewmodel
                    
                    pc.setAppViewModel(appViewModel);
                    Stage stage = new Stage();
                    stage.setTitle("SpotyDAM");
                    // stage.setMaximized(true);
                    stage.setScene(new Scene(root));
                    stage.show();

                    // Cerrar ventana de login
                    ((Stage) usernameField.getScene().getWindow()).close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                errorLabel.setText("Invalid credentials");
            }
        }
    }
}
