package es.burgueses.presentacion.usuario;

import java.io.IOException;

import es.burgueses.aplicacion.usuario.AddUserUserCase;
import es.burgueses.aplicacion.usuario.DeleteUserUserCase;
import es.burgueses.aplicacion.usuario.GetUserUserCase;
import es.burgueses.aplicacion.usuario.ModUserUserCase;
import es.burgueses.dominio.Usuario;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuarioViewmodel {
    // listado observable
    private ListProperty<Usuario> usuarios;
    // casos de uso
    private AddUserUserCase addUserUserCase;
    private DeleteUserUserCase deleteUserUserCase;
    private GetUserUserCase getUserUserCase;
    private ModUserUserCase modUserUserCase;
    // usuario actual, para los borrados, altas y modificaciones
    private SimpleObjectProperty<Usuario> current;

    public UsuarioViewmodel(AddUserUserCase addUserUserCase,
                            DeleteUserUserCase deleteUserUserCase, GetUserUserCase getUserUserCase,
                            ModUserUserCase modUserUserCase) {
        this.addUserUserCase = addUserUserCase;
        this.deleteUserUserCase = deleteUserUserCase;
        this.getUserUserCase = getUserUserCase;
        this.modUserUserCase = modUserUserCase;

        this.usuarios = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.current = new SimpleObjectProperty<>(new Usuario());
        this.load();

    }

    public void load() {
        if (this.getUserUserCase!= null) {
            this.usuarios.clear();
            this.usuarios.addAll(this.getUserUserCase.getAllUsers());
        } else
            throw new NullPointerException("No se ha definido el caso de uso");
    }

    public void reset() {
        this.usuarios.clear();
        this.load();
    }

    public void addMecanico(Usuario item) throws NoSuchFieldException, IOException {
        if (this.addUserUserCase != null) {
            this.addUserUserCase.addUser(null, null, null, null, null);
            this.usuarios.add(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public void removeMecanico(Usuario item) throws NoSuchFieldException, IOException {
        if (this.deleteUserUserCase != null) {
            this.deleteUserUserCase.delete(item);
            this.usuarios.remove(item);
        } else
            throw new NullPointerException("Caso de uso para añadir nulo");

    }

    public ListProperty<Usuario> getMecanicosProperty() {
        return this.usuarios;
    }

    public Usuario getCurrent() {
        return this.current.get();
    }

    public ObjectProperty<Usuario> currentProperty() {
        return this.current;
    }

    public void setCurrent(Usuario item) {
        this.current.set(item);
    }

    public void clearCurrent() {
        this.current.set(new Usuario());
    }

    /**
     * si el id es 0 significa que es nuevo
     * 
     * @throws NoSuchFieldException
     * @throws IOException
     */
    public void saveCurrent() throws NoSuchFieldException, IOException {
        if (this.current.get() != null && (this.current.get().getApodo() == null || this.current.get().getApodo().isEmpty())) {
            // se actualiza en el reposotio, pero no en le viewmodel por tema de hilos
            this.addMecanico(this.current.get());

        } else {
            // para indicar que se ha actualizado
            if (this.modUserUserCase != null) {
                this.modUserUserCase.modificarUsuario(null, null, null, null);
                // se tiene que llamar a refresh de la lista
                // para que las modificaciones se vean en los liststados
                // la modificación de un objeto, no provoca la actualización de la lista
                this.refesh();
            } else
                throw new NullPointerException("Caso de uso para modificar nulo");
        }
    }

    public void setEmptyCurrent() {
        this.current.set(new Usuario());
    }

    /**
     * cuando se modifica un item de forma interna
     * se refresca la lista.
     */
    public void refesh() {
        this.current.set(new Usuario());
        ObservableList<Usuario> oldList = FXCollections.observableArrayList(this.usuarios);
        this.usuarios.clear();
        this.usuarios.addAll(oldList);
    }

}
