package es.burgueses.aplicacion.infraestructura;

import es.burgueses.aplicacion.dominio.Cancion;
import es.burgueses.aplicacion.dominio.ICancionesRepositorio;
import es.burgueses.aplicacion.dominio.Voto;

import java.util.ArrayList;
import java.util.List;

public class CancionRepositorioEnMemoria implements ICancionesRepositorio {

    // Aquí iría la implementación del repositorio de canciones en local.
    // Por ejemplo, podrías usar una base de datos SQLite o un archivo JSON para almacenar las canciones.

    @Override
    public void add(Cancion cancion) {
        // Implementación para añadir una canción al repositorio
    }

    // Método get(String id) eliminado porque no está declarado en la interfaz ICancionesRepositorio

    // Método remove(Cancion cancion) implementado más abajo

    public List<Cancion> getAll() {
        // Implementación para obtener todas las canciones
        return new ArrayList<>(); // Placeholder
    }

    @Override
    public void remove(Cancion cancion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }

    @Override
    public Cancion findByTitulo(String titulo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTitulo'");
    }

    @Override
    public List<Cancion> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public void update(Cancion cancion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void addVotoMeGusta(String titulo, Voto voto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addVotoMeGusta'");
    }

    @Override
    public void addVotoNoMeGusta(String titulo, Voto voto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addVotoNoMeGusta'");
    }

    @Override
    public List<Voto> getVotosMeGusta(String titulo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVotosMeGusta'");
    }

    @Override
    public List<Voto> getVotosNoMeGusta(String titulo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVotosNoMeGusta'");
    }
    
}
