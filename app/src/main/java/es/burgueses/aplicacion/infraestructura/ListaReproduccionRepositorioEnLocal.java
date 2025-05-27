package es.burgueses.aplicacion.infraestructura;
import java.util.ArrayList;
import java.util.List;

import es.burgueses.aplicacion.dominio.Cancion;
import es.burgueses.aplicacion.dominio.IListaReproduccionRepositorio;
import es.burgueses.aplicacion.dominio.ListaReproduccion;

public class ListaReproduccionRepositorioEnLocal implements IListaReproduccionRepositorio {

    // Aquí iría la implementación del repositorio de listas de reproducción en local.
    // Por ejemplo, podrías usar una base de datos SQLite o un archivo JSON para almacenar las listas de reproducción.

    @Override
    public void add(ListaReproduccion lista) {
        // Implementación para añadir una lista de reproducción al repositorio
    }

    // Método eliminado porque no está declarado en la interfaz IListaReproduccionRepositorio

    @Override
    public void remove(ListaReproduccion lista) {
        // Implementación para eliminar una lista de reproducción del repositorio
    }

    public List<ListaReproduccion> getAll() {
        // Implementación para obtener todas las listas de reproducción
        return new ArrayList<>(); // Placeholder
    }

    @Override
    public ListaReproduccion findByTitulo(String titulo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTitulo'");
    }

    @Override
    public List<ListaReproduccion> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void update(ListaReproduccion listaReproduccion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Cancion> getCanciones(String tituloLista) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCanciones'");
    }
    
}
