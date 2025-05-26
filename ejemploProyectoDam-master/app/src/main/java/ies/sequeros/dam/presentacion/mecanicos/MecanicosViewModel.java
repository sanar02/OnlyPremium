package ies.sequeros.dam.presentacion.mecanicos;


import ies.sequeros.dam.aplicacion.mecanicos.ActualizarMecanicoCasoUso;
import ies.sequeros.dam.aplicacion.mecanicos.BorrarMecanicoCasoUso;
import ies.sequeros.dam.aplicacion.mecanicos.NuevoMecanicoCasoUso;
import ies.sequeros.dam.aplicacion.mecanicos.ObtenerMecanicoCasoUso;
import ies.sequeros.dam.dominio.Mecanico;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class MecanicosViewModel {
    //listado observable
    private ListProperty<Mecanico> mecanicos;
    //casos de uso
    private ActualizarMecanicoCasoUso actualizarMecanicoCasoUso;
    private BorrarMecanicoCasoUso borrarMecanicoCasoUso;
    private NuevoMecanicoCasoUso nuevoMecanicoCasoUso;
    private ObtenerMecanicoCasoUso obtenerMecanicoCasoUso;
    //mecanico actual, para los borrados, altas y modificaciones
    private SimpleObjectProperty<Mecanico> current;


    public MecanicosViewModel(ActualizarMecanicoCasoUso actualizarMecanicoCasoUso,
                              BorrarMecanicoCasoUso borrarMecanicoCasoUso, NuevoMecanicoCasoUso nuevoMecanicoCasoUso,
                              ObtenerMecanicoCasoUso obtenerMecanicoCasoUso) {
        this.actualizarMecanicoCasoUso = actualizarMecanicoCasoUso;
        this.borrarMecanicoCasoUso = borrarMecanicoCasoUso;
        this.nuevoMecanicoCasoUso = nuevoMecanicoCasoUso;
        this.obtenerMecanicoCasoUso = obtenerMecanicoCasoUso;

        this.mecanicos = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.current = new SimpleObjectProperty<>(new Mecanico());
        this.load();

    }

    public void load() {
        if (this.obtenerMecanicoCasoUso != null) {
            this.mecanicos.clear();
            this.mecanicos.addAll(this.obtenerMecanicoCasoUso.ejecutar());
        } else
            throw new NullPointerException("No se ha definido el caso de uso");
    }

    public void reset() {
        this.mecanicos.clear();
        this.load();
    }

    public void addMecanico(Mecanico item) throws NoSuchFieldException, IOException {
        if (this.nuevoMecanicoCasoUso != null) {
            this.nuevoMecanicoCasoUso.ejecutar(item);
            this.mecanicos.add(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public void removeMecanico(Mecanico item) throws NoSuchFieldException, IOException {
        if (this.borrarMecanicoCasoUso != null) {
            this.borrarMecanicoCasoUso.ejecutar(item);
            this.mecanicos.remove(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");


    }

    public ListProperty<Mecanico> getMecanicosProperty() {
        return this.mecanicos;
    }

    public Mecanico getCurrent() {
        return this.current.get();
    }

    public ObjectProperty<Mecanico> currentProperty() {
        return this.current;
    }

    public void setCurrent(Mecanico item) {
        this.current.set(item);
    }

    public void clearCurrent() {
        this.current.set(new Mecanico());
    }

    /**
     * si el id es 0 significa que es nuevo
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public void saveCurrent() throws NoSuchFieldException, IOException {
        if (this.current.get() != null && this.current.get().getId() < 0) {
            //se actualiza en el reposotio, pero no en le viewmodel por tema de hilos
            this.addMecanico(this.current.get());

        } else {
            //para indicar que se ha actualizado
            if (this.actualizarMecanicoCasoUso != null) {
                this.actualizarMecanicoCasoUso.ejecutar(this.current.get());
                //se tiene que llamar a refresh de la lista
                //para que las modificaciones se vean en los liststados
                //la modificación de un objeto, no provoca la actualización de la lista
                this.refesh();
            } else
                throw new NullPointerException("Caso de uso para modificar nulo");
        }
    }
    public void setEmptyCurrent() {
        this.current.set(new Mecanico());
    }

    /**
     * cuando se modifica un item de forma interna
     * se refresca la lista.
     */
    public void refesh() {
        this.current.set(new Mecanico());
        ObservableList<Mecanico> oldList = FXCollections.observableArrayList(this.mecanicos);
        this.mecanicos.clear();
        this.mecanicos.addAll(oldList);
    }

}
