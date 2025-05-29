package es.burgueses.dominio;

import java.util.List;

public interface IUsuarioRepositorio {
    void add(Usuario usuario);
    void remove(Usuario usuario);
    Usuario findByApodo(String apodo);
    List<Usuario> findAll();
    Usuario get(String apodo);
    void update(Usuario usuario);
}
