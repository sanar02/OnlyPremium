package es.burgueses.presentacion.controladores.usuario;

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
    private AddUserUserCase casoUsoAgregarUsuario;
    private DeleteUserUserCase casoUsoEliminarUsuario;
    private GetUserUserCase casoUsoObtenerUsuarios;
    private ModUserUserCase casoUsoModificarUsuario;
    // usuario actual, para los borrados, altas y modificaciones
    private SimpleObjectProperty<Usuario> actual;

    public UsuarioViewmodel(AddUserUserCase casoUsoAgregarUsuario,
                            DeleteUserUserCase casoUsoEliminarUsuario, GetUserUserCase casoUsoObtenerUsuarios,
                            ModUserUserCase casoUsoModificarUsuario) {
        this.casoUsoAgregarUsuario = casoUsoAgregarUsuario;
        this.casoUsoEliminarUsuario = casoUsoEliminarUsuario;
        this.casoUsoObtenerUsuarios = casoUsoObtenerUsuarios;
        this.casoUsoModificarUsuario = casoUsoModificarUsuario;

        this.usuarios = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.actual = new SimpleObjectProperty<>(new Usuario());
        this.cargar();

    }

    public void cargar() {
        if (this.casoUsoObtenerUsuarios != null) {
            this.usuarios.clear();
            this.usuarios.addAll(this.casoUsoObtenerUsuarios.getAllUsers());
        } else
            throw new NullPointerException("No se ha definido el caso de uso para obtener usuarios");
    }

    public void reiniciar() {
        this.usuarios.clear();
        this.cargar();
    }

    public void addUsuario(Usuario usuario) throws NoSuchFieldException, IOException {
        if (this.casoUsoAgregarUsuario != null) {
            this.casoUsoAgregarUsuario.addUser(
                usuario.getContrasena(),
                usuario.getNombre(),
                usuario.getApodo(),
                usuario.getPathImagen(),
                usuario.isActivo(),
                usuario.getFechaAlta(),
                usuario.getTipoUsuario()
            );
            this.usuarios.add(usuario);
        } else
            throw new NullPointerException("Caso de uso para añadir usuario nulo");
    }

    public void eliminarUsuario(Usuario usuario) throws NoSuchFieldException, IOException {
        if (this.casoUsoEliminarUsuario != null) {
            this.casoUsoEliminarUsuario.delete(usuario);
            this.usuarios.remove(usuario);
        } else
            throw new NullPointerException("Caso de uso para eliminar usuario nulo");
    }

    public ListProperty<Usuario> usuariosProperty() {
        return this.usuarios;
    }

    public Usuario getActual() {
        return this.actual.get();
    }

    public ObjectProperty<Usuario> actualProperty() {
        return this.actual;
    }

    public void setActual(Usuario usuario) {
        this.actual.set(usuario);
    }

    public void limpiarActual() {
        this.actual.set(new Usuario());
    }

    /**
     * Guarda el usuario actual si es nuevo, o lo modifica si ya existe.
     */
    public void guardarActual() throws NoSuchFieldException, IOException {
        if (this.actual.get() != null && this.actual.get().getApodo() != null && !this.actual.get().getApodo().isEmpty()) {
            // Añadir usuario nuevo
            this.addUsuario(this.actual.get());
        } else {
            // para indicar que se ha actualizado
            if (this.casoUsoModificarUsuario != null) {
                this.casoUsoModificarUsuario.modificarUsuario(null, null, null, null);
                // se tiene que llamar a refresh de la lista
                // para que las modificaciones se vean en los listados
            } else
                throw new NullPointerException("Caso de uso para modificar usuario nulo");
        }
    }

    public void setActualVacio() {
        this.actual.set(new Usuario());
    }

    /**
     * cuando se modifica un item de forma interna
     * se refresca la lista.
     */
    public void refrescar() {
        this.actual.set(new Usuario());
        ObservableList<Usuario> listaAntigua = FXCollections.observableArrayList(this.usuarios);
        this.usuarios.clear();
        this.usuarios.addAll(listaAntigua);
    }

    public AddUserUserCase getCasoUsoAgregarUsuario() {
        return this.casoUsoAgregarUsuario;
    }
}
