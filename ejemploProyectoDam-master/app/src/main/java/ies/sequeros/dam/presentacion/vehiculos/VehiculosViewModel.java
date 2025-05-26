package ies.sequeros.dam.presentacion.vehiculos;



import ies.sequeros.dam.aplicacion.vehiculos.ActualizarVehiculoCasoUso;
import ies.sequeros.dam.aplicacion.vehiculos.BorrarVehiculoCasoUso;
import ies.sequeros.dam.aplicacion.vehiculos.NuevoVehiculoCasoUso;
import ies.sequeros.dam.aplicacion.vehiculos.ObtenerVehiculosCasoUso;
import ies.sequeros.dam.dominio.Vehiculo;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class VehiculosViewModel {
    //listado observable
    private ListProperty<Vehiculo> items;
    //casos de uso
    private ActualizarVehiculoCasoUso actualizarVehiculoCasoUso;
    private BorrarVehiculoCasoUso borrarVehiculoCasoUso;
    private NuevoVehiculoCasoUso nuevoVehiculoCasoUso;
    private ObtenerVehiculosCasoUso obtenerVehiculoCasoUso;
    //mecanico actual, para los borrados, altas y modificaciones
    private SimpleObjectProperty<Vehiculo> current;


    public VehiculosViewModel(ActualizarVehiculoCasoUso actualizarVehiculoCasoUso,
                              BorrarVehiculoCasoUso borrarVehiculoCasoUso, NuevoVehiculoCasoUso nuevoVehiculoCasoUso,
                              ObtenerVehiculosCasoUso obtenerVehiculoCasoUso) {
        this.actualizarVehiculoCasoUso = actualizarVehiculoCasoUso;
        this.borrarVehiculoCasoUso = borrarVehiculoCasoUso;
        this.nuevoVehiculoCasoUso = nuevoVehiculoCasoUso;
        this.obtenerVehiculoCasoUso = obtenerVehiculoCasoUso;

        this.items = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.current = new SimpleObjectProperty<>(new Vehiculo());
        this.load();

    }

    public void load() {
        if (this.obtenerVehiculoCasoUso != null) {
            this.items.clear();
            this.items.addAll(this.obtenerVehiculoCasoUso.ejecutar());
        } else
            throw new NullPointerException("No se ha definido el caso de uso");
    }

    public void reset() {
        this.items.clear();
        this.load();
    }

    public void addVehiculo(Vehiculo item) throws NoSuchFieldException, IOException {
        if (this.nuevoVehiculoCasoUso != null) {
            this.nuevoVehiculoCasoUso.ejecutar(item);
            this.items.add(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public void removeVehiculo(Vehiculo item) throws NoSuchFieldException, IOException {
        if (this.borrarVehiculoCasoUso != null) {
            this.borrarVehiculoCasoUso.ejecutar(item);
            this.items.remove(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");


    }

    public ListProperty<Vehiculo> getVehiculosProperty() {
        return this.items;
    }

    public Vehiculo getCurrent() {
        return this.current.get();
    }

    public ObjectProperty<Vehiculo> currentProperty() {
        return this.current;
    }

    public void setCurrent(Vehiculo item) {
        this.current.set(item);
    }

    public void clearCurrent() {
        this.current.set(new Vehiculo());
    }

    /**
     * si el id es 0 significa que es nuevo
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public void saveCurrent() throws NoSuchFieldException, IOException {
        boolean existe= this.items.stream().anyMatch(vehiculo -> {
            return vehiculo.getMatricula().equals(this.current.get().getMatricula());
        });
        if (!existe) {
            //se actualiza en el reposotio, pero no en le viewmodel por tema de hilos
            this.addVehiculo(this.current.get());
            //para actualizar
            Vehiculo actual=this.current.get();
            this.current.set(new Vehiculo());
            this.current.set(actual);

        } else {
            //para indicar que se ha actualizado
            if (this.actualizarVehiculoCasoUso != null) {
                this.actualizarVehiculoCasoUso.ejecutar(this.current.get());
                //se tiene que llamar a refresh de la lista
                //para que las modificaciones se vean en los liststados
                //la modificación de un objeto, no provoca la actualización de la lista
                this.refesh();
            } else
                throw new NullPointerException("Caso de uso para modificar nulo");
        }
    }
    public void setEmptyCurrent() {
        this.current.set(new Vehiculo());
    }

    /**
     * cuando se modifica un item de forma interna
     * se refresca la lista.
     */
    public void refesh() {
        this.current.set(new Vehiculo());
        ObservableList<Vehiculo> oldList = FXCollections.observableArrayList(this.items);
        this.items.clear();
        this.items.addAll(oldList);
    }

}
