package es.burgueses.presentacion.controladores.login;

import com.mongodb.client.*;
import org.bson.Document;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import es.burgueses.presentacion.controladores.principal.PaginaPrincipalController;
import es.burgueses.presentacion.utils.AppViewmodel;

public class loginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    private AppViewmodel appViewModel;
    @FXML
    public void handleLogin() {
        System.out.println("Intentando login");
        String user = usernameField.getText().trim();
        String pass = passwordField.getText().trim();
        System.out.println("Usuario introducido: '" + user + "'");
        System.out.println("Contraseña introducida: '" + pass + "'");

        try (MongoClient mongoClient = MongoClients.create("mongodb://app:123456789Aa@10.2.1.191:27017/OnlyPremium")) {
            MongoDatabase database = mongoClient.getDatabase("OnlyPremium");
            MongoCollection<Document> collection = database.getCollection("Usuario");

            Document foundUser = collection.find(
                new Document("nombre", user).append("contrasena", pass)
            ).first();

            if (foundUser != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/principal/paginaPrincipal.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("SpotyDAM");
                stage.setScene(new Scene(root));
                stage.show();
                ((Stage) usernameField.getScene().getWindow()).close();
            } else {
                errorLabel.setText("Credenciales incorrectas");
            }
        } catch (Exception e) {
            errorLabel.setText("Error de conexión");
            e.printStackTrace();
        }
    }

    public AppViewmodel getAppViewModel() {
        return appViewModel;
    }

    public void setAppViewModel(AppViewmodel appViewModel) {
        this.appViewModel = appViewModel;
    }
}
