package es.burgueses.presentacion.controladores.cancion;


import es.burgueses.aplicacion.cancion.AddSongUseCase;
import es.burgueses.aplicacion.cancion.DeleteSongUserCase;
import es.burgueses.aplicacion.cancion.GetAllSongsUserCase;
import es.burgueses.aplicacion.cancion.GetSongUserCase;
import es.burgueses.aplicacion.cancion.ModSongUserCase;
import es.burgueses.aplicacion.cancion.UpdateSongUserCase;
import es.burgueses.dominio.Cancion;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.UUID;

public class CancionViewmodel {
    //listado observable
    private ListProperty<Cancion> items;
    //Song actual, para los borrados, altas y modificaciones
    private SimpleObjectProperty<Cancion> current;
    //modo edicion o no
    private BooleanProperty editMode;
    //casos de uso
    private AddSongUseCase addSongUseCase;
    private DeleteSongUserCase deleteSongUseCase;

    private GetAllSongsUserCase getAllSongUseCase;

    private UpdateSongUserCase updateSongUseCase;
    


    public CancionViewmodel(AddSongUseCase addSongUseCase, UpdateSongUserCase updateSongUseCase,
                          DeleteSongUserCase deleteSongUseCase
    , GetAllSongsUserCase listAllSongUseCase) {
       

        this.items = new SimpleListProperty<>(FXCollections.observableArrayList());
this.editMode= new SimpleBooleanProperty(false);
        this.current = new SimpleObjectProperty<>(new Cancion());
        this.addSongUseCase = addSongUseCase;

        this.deleteSongUseCase = deleteSongUseCase;

        this.getAllSongUseCase = listAllSongUseCase;
        this.updateSongUseCase = updateSongUseCase;

        this.load();


    }
    public void load() {
        if (this.getAllSongUseCase != null) {
            this.items.clear();
            this.items.addAll(this.getAllSongUseCase.execute());
        } else
            throw new NullPointerException("No se ha definido el caso de uso");
    }

    public void reset() {
        this.items.clear();
        this.load();
    }

    public void addSong(Cancion item) throws NoSuchFieldException, IOException {
        if (this.addSongUseCase != null) {
            this.addSongUseCase.execute(item);
            this.items.add(item);
            this.current.set(new Cancion());
            this.current.set(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public void removeSong(Cancion item) throws NoSuchFieldException, IOException {
        if (this.deleteSongUseCase != null) {
            this.deleteSongUseCase.execute(item);
            this.items.remove(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");


    }

    public boolean isEditMode() {
        return editMode.get();
    }

    public BooleanProperty editModeProperty() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode.set(editMode);
    }

    public ListProperty<Cancion> getSongsProperty() {
        return this.items;
    }

    public Cancion getCurrent() {
        return this.current.get();
    }

    public ObjectProperty<Cancion> currentProperty() {
        return this.current;
    }

    public void setCurrent(Cancion item) {
        this.current.set(item);
    }

    public void clearCurrent() {
        this.current.set(new Cancion());
    }

    /**
     * si el id es 0 significa que es nuevo
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public void saveCurrent() throws NoSuchFieldException, IOException {
        if (this.current.get() != null && this.current.get().getId()==null) {
            this.current.get().setId(UUID.randomUUID());
            //se actualiza en el reposotio, pero no en le viewmodel por tema de hilos
            this.addSong(this.current.get());

        } else {
            //para indicar que se ha actualizado
            if (this.updateSongUseCase != null) {
                this.updateSongUseCase.execute(this.current.get());
                //se tiene que llamar a refresh de la lista
                //para que las modificaciones se vean en los liststados
                //la modificación de un objeto, no provoca la actualización de la lista
                this.refesh();
            }
        }
    }
    public void setEmptyCurrent() {
        this.current.set(new Cancion());
    }

    /**
     * cuando se modifica un item de forma interna
     * se refresca la lista.
     */
    public void refesh() {
        this.current.set(new Cancion());
        ObservableList<Cancion> oldList = FXCollections.observableArrayList(this.items);
        this.items.clear();
        this.items.addAll(oldList);
    }
    public ObservableList<Cancion> getCancionProperty() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCancionProperty'");
    }

}