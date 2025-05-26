package ies.sequeros.dam.presentacion.main;


import fr.brouillard.oss.cssfx.CSSFX;

import io.github.palexdev.materialfx.theming.JavaFXThemes;
import io.github.palexdev.materialfx.theming.MaterialFXStylesheets;
import io.github.palexdev.materialfx.theming.UserAgentBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {


    private void initAdmin(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));

        loader.setControllerFactory(c -> new MainController(primaryStage));
        Parent root ;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.DECORATED);//.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DAM Sequeros App");
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/images/logo.png"))));
        primaryStage.setMaximized(true);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        CSSFX.start();
        //.themes(JavaFXThemes.CASPIAN)
        UserAgentBuilder.builder()
                .themes(JavaFXThemes.MODENA)
                .themes(MaterialFXStylesheets.forAssemble(true))
                .setDeploy(true)
                .setResolveAssets(true)
                .build()
                .setGlobal();
        this.initAdmin(primaryStage);
    }
}
