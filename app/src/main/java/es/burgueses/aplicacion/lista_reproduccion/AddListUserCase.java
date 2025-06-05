package es.burgueses.aplicacion.lista_reproduccion;

import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;

public class AddListUserCase {
    IListaReproduccionRepositorio listaReproduccionRepositorio;

    public AddListUserCase(IListaReproduccionRepositorio listaReproduccionRepositorio) {
        this.listaReproduccionRepositorio = listaReproduccionRepositorio;
    }

    public void addList(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre de la lista de reproducción no puede ser nulo o vacío");
        }
        ListaReproduccion lista = new ListaReproduccion();
        listaReproduccionRepositorio.add(lista);
    }
}
