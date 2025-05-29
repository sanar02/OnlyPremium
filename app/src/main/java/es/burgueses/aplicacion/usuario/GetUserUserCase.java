package es.burgueses.aplicacion.usuario;

import es.burgueses.dominio.Usuario;
import es.burgueses.dominio.IUsuarioRepositorio;

public class GetUserUserCase {
    private final IUsuarioRepositorio usuarioRepositorio;

    public GetUserUserCase(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Usuario getUserByApodo(String apodo) {
        Usuario usuario = usuarioRepositorio.findByApodo(apodo);
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ese apodo");
        }
        return usuario;
    }
}
