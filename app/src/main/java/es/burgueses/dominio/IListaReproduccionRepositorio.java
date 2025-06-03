package es.burgueses.dominio;

import java.util.List;
import java.util.UUID;

public interface IListaReproduccionRepositorio {
    void add(ListaReproduccion listaReproduccion);
    void remove(ListaReproduccion listaReproduccion);
    ListaReproduccion findById(UUID idLista);
    List<ListaReproduccion> findAll();
    void update(ListaReproduccion listaReproduccion);
    void addCancion(UUID idLista, UUID idCancion);
    void removeCancion(UUID idLista, UUID idCancion);
    void modifyList(UUID idLista, String nuevoTitulo, String nuevaDescripcion, String descripcion);

    List<UUID> getCanciones(UUID idLista);

    ListaReproduccion findByTitulo(String tituloLista);

    // Métodos extra para compatibilidad con la interfaz (por título)
    List<Cancion> getCanciones(String tituloLista);

    void addCancion(String tituloLista, Cancion cancion);

    void removeCancion(String tituloLista, Cancion cancion);

    void modifyList(String tituloLista, String nuevoTitulo, String nuevaDescripcion, String descripcion);
}