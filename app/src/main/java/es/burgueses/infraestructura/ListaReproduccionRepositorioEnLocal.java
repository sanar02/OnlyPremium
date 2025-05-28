package es.burgueses.aplicacion.infraestructura;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.burgueses.aplicacion.dominio.Cancion;
import es.burgueses.aplicacion.dominio.IListaReproduccionRepositorio;
import es.burgueses.aplicacion.dominio.ListaReproduccion;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ListaReproduccionRepositorioEnLocal implements IListaReproduccionRepositorio {

    private final ObjectMapper mapper;
    private final File archivo;
    private final Map<String, ListaReproduccion> listas;

    public ListaReproduccionRepositorioEnLocal() {
        this.mapper = new ObjectMapper();
        this.archivo = new File("listas_reproduccion.json");
        this.listas = new HashMap<>();
    }

    private void cargar() {
        if (archivo.exists()) {
            try {
                List<ListaReproduccion> lista = mapper.readValue(archivo, new TypeReference<List<ListaReproduccion>>() {
                });
                for (ListaReproduccion lr : lista) {
                    listas.put(lr.getNombre(), lr);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al cargar listas de reproducción", e);
            }
        }
    }

    private void guardar() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, listas.values());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar listas de reproducción", e);
        }
    }

    @Override
    public void add(ListaReproduccion lista) {
        if (listas.containsKey(lista.getNombre())) {
            throw new IllegalStateException("Ya existe una lista con ese nombre");
        }
        listas.put(lista.getNombre(), lista);
        guardar();
    }

    @Override
    public void remove(ListaReproduccion lista) {
        listas.remove(lista.getNombre());
        guardar();
    }

    @Override
    public ListaReproduccion findByTitulo(String titulo) {
        return listas.get(titulo);
    }

    @Override
    public List<ListaReproduccion> findAll() {
        return new ArrayList<>(listas.values());
    }

    @Override
    public void update(ListaReproduccion listaReproduccion) {
        listas.put(listaReproduccion.getNombre(), listaReproduccion);
        guardar();
    }

    @Override
    public List<Cancion> getCanciones(String tituloLista) {
        cargar();
        ListaReproduccion lista = listas.get(tituloLista);
        if (lista != null) {
            return lista.getCanciones();
        }
        return Collections.emptyList();
    }
}
