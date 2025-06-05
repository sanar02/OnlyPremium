package es.burgueses.aplicacion.lista_reproduccion;

import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;

public class DeleteListUserCase {
    private IListaReproduccionRepositorio listaReproduccionRepositorio;

    public DeleteListUserCase(IListaReproduccionRepositorio listaReproduccionRepositorio) {
        this.listaReproduccionRepositorio = listaReproduccionRepositorio;
    }

    public void deleteList(String tituloLista) {
        if (tituloLista == null || tituloLista.isEmpty()) {
            throw new IllegalArgumentException("El título de la lista de reproducción no puede ser nulo o vacío");
        }

        ListaReproduccion lista = listaReproduccionRepositorio.findByTitulo(tituloLista);
        if (lista == null) {
            throw new IllegalArgumentException("La lista de reproducción no existe");
        }

        listaReproduccionRepositorio.remove(lista);
    }
}
