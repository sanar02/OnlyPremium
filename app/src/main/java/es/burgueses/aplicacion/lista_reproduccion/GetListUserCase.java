package es.burgueses.aplicacion.lista_reproduccion;

import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;
import es.burgueses.dominio.Usuario;

public class GetListUserCase {
    private IListaReproduccionRepositorio listaReproduccionRepositorio;

    public GetListUserCase(IListaReproduccionRepositorio listaReproduccionRepositorio) {
        this.listaReproduccionRepositorio = listaReproduccionRepositorio;
    }

    public ListaReproduccion getList(String tituloLista, Usuario usuario) {
        if (tituloLista == null || tituloLista.isEmpty()) {
            throw new IllegalArgumentException("El título de la lista de reproducción no puede ser nulo o vacío");
        }

        ListaReproduccion lista = listaReproduccionRepositorio.findByTitulo(tituloLista);
        if (lista == null) {
            throw new IllegalArgumentException("La lista de reproducción no existe");
        }

        return lista;
    }

}
