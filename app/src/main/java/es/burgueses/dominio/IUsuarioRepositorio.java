package es.burgueses.aplicacion.dominio;

import java.util.List;

public interface IUsuarioRepositorio {
    void add(Usuario usuario);
    void remove(Usuario usuario);
    Usuario findByApodo(String apodo);
    List<Usuario> findAll();
    void update(Usuario usuario);
}
