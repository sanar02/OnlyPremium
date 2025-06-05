package es.burgueses.aplicacion.lista_reproduccion;

import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;

import java.util.List;

public class GetAllListUserCase {
    private IListaReproduccionRepositorio listaReproduccionRepositorio;

    public GetAllListUserCase(IListaReproduccionRepositorio listaReproduccionRepositorio) {
        this.listaReproduccionRepositorio = listaReproduccionRepositorio;
    }

    public List<ListaReproduccion> getAllLists() {
        return listaReproduccionRepositorio.findAll();
    }
}
