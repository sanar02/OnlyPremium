package es.burgueses.aplicacion.usuario;

import es.burgueses.dominio.Usuario;
import es.burgueses.dominio.IUsuarioRepositorio;

public class GetUserUserCase {
    private final IUsuarioRepositorio usuarioRepositorio;

    public GetUserUserCase(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Usuario getUserById(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }
        Usuario usuario = usuarioRepositorio.findById(id); // id es String
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con el ID proporcionado");
        }
        return usuario;
    }
}
