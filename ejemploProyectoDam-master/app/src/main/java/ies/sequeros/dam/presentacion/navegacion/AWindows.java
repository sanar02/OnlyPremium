package ies.sequeros.dam.presentacion.navegacion;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;

import java.util.Optional;

public abstract class AWindows implements IWindows {

    protected Router router;
    protected StackPane parent;
    protected StackPane overlay;
    public AWindows() {

    }
    public Router getRouter() {
        return router;
    }


    public void setRouter(Router router) {
        this.router = router;
    }

    public StackPane getParent() {
        return parent;
    }

    public void setParent(StackPane parent) {
        this.parent = parent;
    }

    protected void showMessage(String message, String head, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(head);
        alert.setHeaderText(null);  // Puedes establecer un encabezado aquí si lo deseas
        alert.setContentText(message);

        alert.showAndWait();
    }
    protected boolean showMessageBooleano(String message,String head) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(head);
        alert.setHeaderText(null);  // Puedes establecer un encabezado aquí si lo deseas
        alert.setContentText(message);
        Optional<ButtonType> resultado = alert.showAndWait();

        return resultado.isPresent() && resultado.get() == ButtonType.OK;

    }




}
