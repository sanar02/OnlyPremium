package es.burgueses.dominio;

import java.util.List;

public interface IListaReproduccionRepositorio {
    void add(ListaReproduccion listaReproduccion);
    void remove(ListaReproduccion listaReproduccion);
    ListaReproduccion findByTitulo(String titulo);
    List<ListaReproduccion> findAll();
    void update(ListaReproduccion listaReproduccion);
    
    // Métodos adicionales para manejar canciones en listas de reproducción
    void addCancion(String tituloLista, Cancion cancion);
    void removeCancion(String tituloLista, Cancion cancion);
    List<Cancion> getCanciones(String tituloLista);
}