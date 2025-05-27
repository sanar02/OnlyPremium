package es.burgueses.aplicacion.infraestructura;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.burgueses.aplicacion.dominio.Cancion;
import es.burgueses.aplicacion.dominio.ICancionesRepositorio;
import es.burgueses.aplicacion.dominio.Voto;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CancionRepositorioEnFichero implements ICancionesRepositorio {

    private final ObjectMapper mapper;
    private final File archivo;
    private final Map<String, Cancion> canciones;

    public CancionRepositorioEnFichero(String rutaArchivo) {
        this.mapper = new ObjectMapper();
        this.archivo = new File(rutaArchivo);
        this.canciones = new HashMap<>();
        cargar();
    }

    private void cargar() {
        if (archivo.exists()) {
            try {
                List<Cancion> lista = mapper.readValue(archivo, new TypeReference<List<Cancion>>() {});
                for (Cancion c : lista) {
                    canciones.put(c.getTitulo(), c);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al cargar canciones", e);
            }
        }
    }

    private void guardar() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, canciones.values());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar canciones", e);
        }
    }

    @Override
    public void add(Cancion cancion) {
        if (canciones.containsKey(cancion.getTitulo())) {
            throw new IllegalStateException("Ya existe una canción con ese título");
        }
        canciones.put(cancion.getTitulo(), cancion);
        guardar();
    }

    @Override
    public void remove(Cancion cancion) {
        canciones.remove(cancion.getTitulo());
        guardar();
    }

    @Override
    public Cancion findByTitulo(String titulo) {
        return canciones.get(titulo);
    }

    @Override
    public List<Cancion> findAll() {
        return new ArrayList<>(canciones.values());
    }

    @Override
    public void update(Cancion cancion) {
        canciones.put(cancion.getTitulo(), cancion);
        guardar();
    }

    @Override
    public void addVotoMeGusta(String titulo, Voto voto) {
        Cancion c = canciones.get(titulo);
        if (c != null) {
            c.getMeGusta().add(voto);
            guardar();
        } else {
            throw new NoSuchElementException("Canción no encontrada");
        }
    }

    @Override
    public void addVotoNoMeGusta(String titulo, Voto voto) {
        Cancion c = canciones.get(titulo);
        if (c != null) {
            c.getNoMeGusta().add(voto);
            guardar();
        } else {
            throw new NoSuchElementException("Canción no encontrada");
        }
    }

    @Override
    public List<Voto> getVotosMeGusta(String titulo) {
        Cancion c = canciones.get(titulo);
        if (c != null) {
            return c.getMeGusta();
        } else {
            throw new NoSuchElementException("Canción no encontrada");
        }
    }

    @Override
    public List<Voto> getVotosNoMeGusta(String titulo) {
        Cancion c = canciones.get(titulo);
        if (c != null) {
            return c.getNoMeGusta();
        } else {
            throw new NoSuchElementException("Canción no encontrada");
        }
    }

    public List<Cancion> findByIdPropietario(String apodoUsuario) {
        List<Cancion> resultado = new ArrayList<>();
        for (Cancion c : canciones.values()) {
            if (c.getIdPropietario().equals(apodoUsuario)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
}


