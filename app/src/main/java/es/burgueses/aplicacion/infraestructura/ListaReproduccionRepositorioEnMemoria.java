package es.burgueses.aplicacion.infraestructura;
import java.util.ArrayList;
import java.util.List;
import es.burgueses.aplicacion.dominio.Cancion;
import es.burgueses.aplicacion.dominio.IListaReproduccionRepositorio;
import es.burgueses.aplicacion.dominio.ListaReproduccion;
import es.burgueses.aplicacion.dominio.Voto;
import es.burgueses.aplicacion.dominio.Usuario;

public class ListaReproduccionRepositorioEnMemoria implements IListaReproduccionRepositorio {

    private final List<ListaReproduccion> listasReproduccion;

    public ListaReproduccionRepositorioEnMemoria() {
        this.listasReproduccion = new ArrayList<>();
    }

    @Override
    public void add(ListaReproduccion lista) {
        listasReproduccion.add(lista);
    }

    @Override
    public void remove(ListaReproduccion lista) {
        listasReproduccion.remove(lista);
    }

    public List<ListaReproduccion> getAll() {
        return new ArrayList<>(listasReproduccion);
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

    public Object getTitulo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTitulo'");
    }

    @Override
    public void addCancion(String tituloLista, Cancion cancion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void removeCancion(String tituloLista, Cancion cancion) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}