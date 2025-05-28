package es.burgueses.aplicacion.dominio;

import java.util.List;

public interface IListaReproduccionRepositorio {
    void add(ListaReproduccion listaReproduccion);
    void remove(ListaReproduccion listaReproduccion);
    ListaReproduccion findByTitulo(String titulo);
    List<ListaReproduccion> findAll();
    void update(ListaReproduccion listaReproduccion);
    
    List<Cancion> getCanciones(String tituloLista);
}