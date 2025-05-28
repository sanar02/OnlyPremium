package es.burgueses.infraestructura;
import java.util.ArrayList;
import java.util.List;
import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;
import es.burgueses.dominio.Voto;
import es.burgueses.dominio.Usuario;

public class ListaReproduccionRepositorioEnMemoria implements IListaReproduccionRepositorio {

    private final List<ListaReproduccion> listasReproduccion;

    public ListaReproduccionRepositorioEnMemoria() {
        this.listasReproduccion = new ArrayList<>();
    }

    @Override
    public void add(ListaReproduccion lista) {
        boolean exists = listasReproduccion.stream()
            .anyMatch(l -> l.getNombre().equals(lista.getNombre()));
        if (exists) {
            throw new IllegalStateException("Ya existe una lista con ese nombre");
        }
        listasReproduccion.add(lista);
    }

    @Override
    public void remove(ListaReproduccion lista) {
        listasReproduccion.remove(lista);
    }

    @Override
    public ListaReproduccion findByTitulo(String titulo) {
        return listasReproduccion.stream()
                .filter(lista -> lista.getNombre().equals(titulo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ListaReproduccion> findAll() {
        return new ArrayList<>(listasReproduccion);
    }

    @Override
    public void update(ListaReproduccion listaReproduccion) {
        // Implementación para actualizar una lista de reproducción en memoria
        int index = listasReproduccion.indexOf(listaReproduccion);
        if (index != -1) {
            listasReproduccion.set(index, listaReproduccion);
        }
    }

    @Override
    public List<Cancion> getCanciones(String tituloLista) {
        ListaReproduccion lista = findByTitulo(tituloLista);
        if (lista != null) {
            return lista.getCanciones();
        }
        return new ArrayList<>();
    }
}