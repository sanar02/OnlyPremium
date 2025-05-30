package es.burgueses.dominio;

import java.util.List;

public interface IListaReproduccionRepositorio {
    void add(ListaReproduccion listaReproduccion);
    void remove(ListaReproduccion listaReproduccion);
    ListaReproduccion findByTitulo(String titulo);
    List<ListaReproduccion> findAll();
    void update(ListaReproduccion listaReproduccion);
    void addCancion(String tituloLista, Cancion cancion);
    void removeCancion(String tituloLista, Cancion cancion);
    void modifyList(String tituloLista, String nuevoTitulo, String nuevaDescripcion, String descripcion);
    
    List<Cancion> getCanciones(String tituloLista);
}