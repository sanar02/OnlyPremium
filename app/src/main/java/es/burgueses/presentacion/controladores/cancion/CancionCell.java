package es.burgueses.presentacion.controladores.cancion;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.GridCell;
import org.kordamp.ikonli.javafx.FontIcon;

import es.burgueses.dominio.Cancion;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class CancionCell extends GridCell<Cancion>{
    
    private Label label;
    private VBox vbox;
    private ImageView imageView;
    private Label titleLabel;
    private Consumer<Cancion> onView;
    private Consumer<Cancion> ondelete;
    private Consumer<Cancion> onedit;
    private Consumer<Cancion> onplay;

    Label descLabel;

    FontIcon deleteIcon, viewIcon, editIcon;
    FontIcon playIcon;
    private MFXButton playBtn,editBtn,viewBtn,deleteBtn;
    public CancionCell() {

        vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        setAlignment(Pos.TOP_CENTER);
        setPrefWidth(200);
        setStyle("-fx-background-color:rgb(95, 16, 86);");
        vbox.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(1))));
        vbox.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(10), Insets.EMPTY)));

        // Imagen
        imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/No_image.png"), 1120, 120, true, true));
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);

        // Título
        titleLabel = new Label("");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Descripción
        descLabel = new Label("");
        descLabel.setWrapText(true);
        descLabel.setStyle("-fx-text-fill: #666666;");

        // Botones
        viewBtn = new MFXButton("");
        viewIcon = new FontIcon("fa-eye");//new MFXFontIcon("fas-eye", 12);
        viewIcon.setIconSize(12);
        viewBtn.setGraphic(viewIcon);


        editBtn = new MFXButton("");
        editIcon = new FontIcon("fa-pencil");//;new MFXFontIcon("fas-pencil", 12);
        editIcon.setIconSize(12);
        editBtn.setGraphic(editIcon);


        deleteBtn = new MFXButton("");
        deleteIcon = new FontIcon("fa-trash");
        deleteIcon.setIconSize(12);//new MFXFontIcon("fas-trash", 12);

        deleteBtn.setGraphic(deleteIcon);


        deleteBtn.setPickOnBounds(true); // permite recibir eventos si el nodo tiene bounds
        deleteBtn.setMouseTransparent(false);
        deleteBtn.setStyle(null);


        playBtn = new MFXButton("");
        playIcon = new FontIcon("fa-play");
        playIcon.setIconSize(12);

        playBtn.setGraphic(new FontIcon("fa-play"));


        playBtn.setPickOnBounds(true); // permite recibir eventos si el nodo tiene bounds
        playBtn.setMouseTransparent(false);
        playBtn.setStyle(null);
        HBox buttonBox = new HBox(5, playBtn, viewBtn, editBtn, deleteBtn);
        buttonBox.setAlignment(Pos.CENTER);

        vbox.getChildren().addAll(imageView, titleLabel, descLabel, buttonBox);
        //esto es una chapuza, pero no funciona de ninguna otra forma

        this.setOnMouseClicked(mouseEvent -> {

            var t = mouseEvent.getPickResult().getIntersectedNode();
            if (t instanceof FontIcon) {
                FontIcon tempoicon = (FontIcon) t;
                Cancion c = getItem();
                if (tempoicon == this.deleteIcon && this.ondelete != null) {

                    this.ondelete.accept(getItem());
                }
                if (tempoicon == this.editIcon && this.onedit != null) {
                    this.onedit.accept(getItem());
                }
                if (tempoicon == this.viewIcon && this.onView != null) {
                    this.onView.accept(c);
                }

                if (tempoicon.getIconLiteral().equals("fa-play") && this.onplay != null) {
                    this.onplay.accept(c);
                    ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-stop-circle:12");

                } else if (tempoicon.getIconLiteral().equals("fa-stop-circle") && this.onplay != null) {
                    this.onplay.accept(c);

                    ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-play:12");

                }


            }
        });

    }

    public void stop(){
        ((FontIcon) playBtn.getGraphic()).setIconLiteral("fa-play:12");
    }
    public void setOnView(Consumer<Cancion> onView) {
        this.onView = onView;

    }

    public void setOnDelete(Consumer<Cancion> onDelete) {
        this.ondelete = onDelete;
    }

    public void setOnPlay(Consumer<Cancion> onPlay) {
        this.onplay = onPlay;
    }

    public void setOnEdit(Consumer<Cancion> onEdit) {
        this.onedit = onEdit;
    }

    protected void updateItem(Cancion item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null); // Si no hay contenido, no se muestra nada
        } else {


            this.vbox.setStyle("-fx-border-color:rgb(95, 16, 86);-fx-border-radius: 5;-fx-border-insets: 5;-fx-border-width: 3;-fx-padding: 10 10 10 10;");//"-fx-border-style: dashed;");
            vbox.setAlignment(Pos.CENTER);
            this.descLabel.setText(item.getAutor());
            this.titleLabel.setFont(Font.font("Segoe UI Emoji", 16));
            this.titleLabel.setText(item.getTitulo() );
            if (Files.exists(Path.of(item.getPath())))
                this.imageView.setImage(new Image(Path.of(item.getPath()).toUri().toString(), 1120, 120, true, true));
            setGraphic(this.vbox); // Mostrar la imagen en la celda
        }
    }
}
