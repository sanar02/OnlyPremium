package es.burgueses.infraestructura;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.Voto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

public class CancionRepositorioEnMemoria implements ICancionesRepositorio {

    private final Map<String, Cancion> canciones = new HashMap<>();

    @Override
    public void add(Cancion cancion) {
        canciones.put(cancion.getTitulo(), cancion);
    }

    @Override
    public void remove(Cancion cancion) {
        canciones.remove(cancion.getTitulo());
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
    }

    @Override
    public void addVotoMeGusta(String titulo, Voto voto) {
        Cancion c = canciones.get(titulo);
        if (c != null) {
            c.getMeGusta().add(voto);
        } else {
            throw new NoSuchElementException("Canción no encontrada");
        }
    }

    @Override
    public void addVotoNoMeGusta(String titulo, Voto voto) {
        Cancion c = canciones.get(titulo);
        if (c != null) {
            c.getNoMeGusta().add(voto);
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

    @Override
    public Cancion findById(UUID id) {
        for (Cancion c : canciones.values()) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String replace(String string, UUID id, String path) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

}
