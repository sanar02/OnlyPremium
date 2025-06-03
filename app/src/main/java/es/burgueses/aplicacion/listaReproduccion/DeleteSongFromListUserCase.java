package es.burgueses.aplicacion.listaReproduccion;

import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;
import es.burgueses.dominio.Cancion;

public class DeleteSongFromListUserCase {
    private IListaReproduccionRepositorio listaReproduccionRepositorio;

    public DeleteSongFromListUserCase(IListaReproduccionRepositorio listaReproduccionRepositorio) {
        this.listaReproduccionRepositorio = listaReproduccionRepositorio;
    }

    public void deleteSongFromList(String tituloLista, String tituloCancion) {
        if (tituloLista == null || tituloLista.isEmpty()) {
            throw new IllegalArgumentException("El título de la lista de reproducción no puede ser nulo o vacío");
        }
        if (tituloCancion == null || tituloCancion.isEmpty()) {
            throw new IllegalArgumentException("El título de la canción no puede ser nulo o vacío");
        }

        ListaReproduccion lista = listaReproduccionRepositorio.findByTitulo(tituloLista);
        if (lista == null) {
            throw new IllegalArgumentException("La lista de reproducción no existe");
        }

        Cancion cancion = null;
        for (Cancion c : lista.getCanciones()) {
            if (c.getTitulo().equals(tituloCancion)) {
                cancion = c;
                break;
            }
        }
        if (cancion == null) {
            throw new IllegalArgumentException("La canción no existe en la lista de reproducción");
        }

        listaReproduccionRepositorio.removeCancion(tituloLista, cancion);
    }
}