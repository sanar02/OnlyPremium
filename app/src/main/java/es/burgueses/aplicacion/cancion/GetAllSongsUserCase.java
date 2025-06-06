package es.burgueses.aplicacion.cancion;

import java.util.List;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;

public class GetAllSongsUserCase {

    private ICancionesRepositorio repository;

    public GetAllSongsUserCase(ICancionesRepositorio repository) {
        this.repository = repository;
    }

    public List<Cancion> execute() {
        return repository.findAll();
    }

}
