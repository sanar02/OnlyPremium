package es.burgueses.aplicacion.infraestructura;

import es.burgueses.aplicacion.dominio.Cancion;
import es.burgueses.aplicacion.dominio.ICancionesRepositorio;
import es.burgueses.aplicacion.dominio.Voto;

import java.util.ArrayList;
import java.util.List;

public class CancionRepositorioEnFichero implements ICancionesRepositorio {

    // Aquí iría la implementación del repositorio de canciones en un fichero.
    // Por ejemplo, podrías usar un archivo JSON para almacenar las canciones.

    @Override
    public void add(Cancion cancion) {
        // Implementación para añadir una canción al repositorio
    }

    public Cancion get(String id) {
        // Implementación para obtener una canción por su ID
        return null; // Placeholder
    }

    @Override
    public void remove(Cancion cancion) {
        // Implementación para eliminar una canción del repositorio
    }

    public List<Cancion> getAll() {
        // Implementación para obtener todas las canciones
        return new ArrayList<>(); // Placeholder
    }

    @Override
    public Cancion findByTitulo(String titulo) {
        // Implementación para encontrar una canción por su título
        return null; // Placeholder
    }

    @Override
    public void update(Cancion cancion) {
        // Implementación para actualizar una canción en el repositorio
    }

    @Override
    public void addVotoMeGusta(String titulo, Voto voto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addVotoMeGusta'");
    }

    @Override
    public void addVotoNoMeGusta(String titulo, Voto voto) {
        // Implementación para añadir un voto "no me gusta" a una canción
    }

    @Override
    public List<Cancion> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
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
