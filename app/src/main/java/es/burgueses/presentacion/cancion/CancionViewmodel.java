package es.burgueses.presentacion.cancion;

import java.io.IOException;

import es.burgueses.aplicacion.cancion.AddSongUserCase;
import es.burgueses.aplicacion.cancion.DeleteSongUserCase;
import es.burgueses.aplicacion.cancion.GetSongUserCase;
import es.burgueses.aplicacion.cancion.ModSongUserCase;
import es.burgueses.dominio.Cancion;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CancionViewmodel {

    // listado observable
    private ListProperty<Cancion> items;
    // casos de uso
    private AddSongUserCase addSongUserCase;
    private DeleteSongUserCase deleteSongUserCase;
    private GetSongUserCase getSongUserCase;
    private ModSongUserCase modSongUserCase;
    // mecanico actual, para los borrados, altas y modificaciones
    private SimpleObjectProperty<Cancion> current;

    public CancionViewmodel(AddSongUserCase addSongUserCase, DeleteSongUserCase deleteSongUserCase,
                GetSongUserCase getSongUserCase, ModSongUserCase modSongUserCase) {
            this.addSongUserCase = addSongUserCase;
            this.deleteSongUserCase = deleteSongUserCase;
            this.getSongUserCase = getSongUserCase;
            this.modSongUserCase = modSongUserCase;

            this.items = new SimpleListProperty<>(FXCollections.observableArrayList());
            this.current = new SimpleObjectProperty<>(new Cancion());
            this.load();

        }

    public void load() {
        if (this.getSongUserCase != null) {
            this.items.clear();
            this.items.addAll(this.getSongUserCase.getSong(""));
        } else
            throw new NullPointerException("No se ha definido el caso de uso");
    }

    public void reset() {
        this.items.clear();
        this.load();
    }

    public void addSong(Cancion item) throws NoSuchFieldException, IOException {
        if (this.addSongUserCase != null) {
            this.addSongUserCase.add(item);
            this.items.add(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public void deleteSong(Cancion item) throws NoSuchFieldException, IOException {
        if (this.deleteSongUserCase != null) {
            this.deleteSongUserCase.delete(String.valueOf(item.getIdCancion())); 
            this.items.remove(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public ListProperty<Cancion> getCancionProperty() {
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
     * 
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public void saveCurrent() throws NoSuchFieldException, IOException {
        boolean existe = this.items.stream().anyMatch(cancion -> {
            return cancion.getIdCancion() == this.current.get().getIdCancion();
        });
        if (!existe) {
            // se actualiza en el reposotio, pero no en le viewmodel por tema de hilos
            this.addSong(this.current.get());
            // para actualizar
            Cancion actual = this.current.get();
            this.current.set(new Cancion());
            this.current.set(actual);

        } else {
            // para indicar que se ha actualizado
            if (this.modSongUserCase != null) {
                this.modSongUserCase.modify(0, null, null, null, null);
                // se tiene que llamar a refresh de la lista
                // para que las modificaciones se vean en los liststados
                // la modificación de un objeto, no provoca la actualización de la lista
                this.refesh();
            } else
                throw new NullPointerException("Caso de uso para modificar nulo");
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
}
