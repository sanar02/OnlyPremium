package es.burgueses.dominio;

import java.util.List;

public interface IListaReproduccionRepositorio {
    void add(ListaReproduccion listaReproduccion);
    void remove(ListaReproduccion lista, Usuario usuarioActual);

    ListaReproduccion findById(String idLista);
    List<ListaReproduccion> findAll();
    void update(ListaReproduccion listaReproduccion);

    // Métodos para manejar canciones en la lista
    void addCancion(String idLista, String idCancion);
    void removeCancion(String idLista, String idCancion);

    // Buscar por nombre/título
    ListaReproduccion findByTitulo(String tituloLista);

    // Métodos extra para compatibilidad (por título)
    List<Cancion> getCanciones(String tituloLista);
    void addCancion(String tituloLista, Cancion cancion);
    void removeCancion(String tituloLista, Cancion cancion);
    void modifyList(String tituloLista, String nuevoTitulo, String nuevaDescripcion, String descripcion);

    void deleteAll();
}