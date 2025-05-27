package es.burgueses.aplicacion.infraestructura;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import es.burgueses.aplicacion.dominio.Usuario;
import es.burgueses.aplicacion.dominio.IUsuarioRepositorio;

public class UsuarioRepositorioEnMemoria implements IUsuarioRepositorio {

    private final Map<String, Usuario> usuarios; // Mapa para almacenar usuarios por su apodo

    /**
     * Constructor que inicializa el repositorio de usuarios en memoria.
     */
    public UsuarioRepositorioEnMemoria() {
        this.usuarios = new HashMap<>();
    }

    @Override
    public void add(Usuario usuario) {
        if (usuarios.containsKey(usuario.getApodo())) {
            throw new IllegalArgumentException("El usuario ya existe");
        }
        usuarios.put(usuario.getApodo(), usuario);
    }

    public Usuario get(String apodo) {
        return usuarios.get(apodo);
    }

    public void remove(String apodo) {
        usuarios.remove(apodo);
    }

    public List<Usuario> getAll() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public void remove(Usuario usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Usuario findByApodo(String apodo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByApodo'");
    }

    @Override
    public List<Usuario> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void update(Usuario usuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
