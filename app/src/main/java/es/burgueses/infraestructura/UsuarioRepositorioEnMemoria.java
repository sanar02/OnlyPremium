package es.burgueses.infraestructura;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

import es.burgueses.dominio.Usuario;
import es.burgueses.dominio.IUsuarioRepositorio;

public class UsuarioRepositorioEnMemoria implements IUsuarioRepositorio {

    private final Map<String, Usuario> usuarios; // Mapa para almacenar usuarios por su apodo

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

    @Override
    public void remove(Usuario usuario) {
        usuarios.remove(usuario.getApodo());
    }

    @Override
    public Usuario findByApodo(String apodo) {
        return usuarios.get(apodo);
    }

    @Override
    public List<Usuario> findAll() {
        return new ArrayList<>(usuarios.values());
    }

    @Override
    public void update(Usuario usuario) {
        usuarios.put(usuario.getApodo(), usuario);
    }

    @Override
    public Usuario findById(String _id) { // Cambiado a String
        if (_id == null) return null;
        for (Usuario u : usuarios.values()) {
            if (_id.equals(u.getId())) {
                return u;
            }
        }
        return null;
    }

    @Override
    public void deleteAll() {
        usuarios.clear();
    }

}
