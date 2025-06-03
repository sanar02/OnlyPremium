package es.burgueses.aplicacion.usuario;

import es.burgueses.dominio.IUsuarioRepositorio;
import es.burgueses.dominio.Usuario;

public class DeleteUserUserCase {

    private final IUsuarioRepositorio usuarioRepositorio;

    public DeleteUserUserCase(IUsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public void delete(Usuario usuario) {
        if (usuario == null || usuario.getApodo() == null) {
            throw new IllegalArgumentException("Usuario o ID no pueden ser nulos");
        }
        usuarioRepositorio.remove(usuario);
    }
}