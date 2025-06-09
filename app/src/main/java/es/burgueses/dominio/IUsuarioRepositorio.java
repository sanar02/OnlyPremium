package es.burgueses.dominio;

import java.util.List;

public interface IUsuarioRepositorio {
    void add(Usuario usuario);
    void remove(Usuario usuario);
    Usuario findById(String _id); 
    List<Usuario> findAll();
    void update(Usuario usuario);
    Usuario findByApodo(String apodo);
    void deleteAll();
}

