package es.burgueses.aplicacion.cancion.listaReproduccion;

import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;
import es.burgueses.dominio.Usuario;
import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;

public class AddSongToListUserCase {
    private IListaReproduccionRepositorio listaReproduccionRepositorio;
    private ICancionesRepositorio cancionRepositorio;

    public AddSongToListUserCase(IListaReproduccionRepositorio listaReproduccionRepositorio, ICancionesRepositorio cancionRepositorio) {
        this.listaReproduccionRepositorio = listaReproduccionRepositorio;
        this.cancionRepositorio = cancionRepositorio;
    }

    public void addSongToList(String tituloLista, String tituloCancion, Usuario usuario) {
        if (tituloLista == null || tituloLista.isEmpty()) {
            throw new IllegalArgumentException("El título de la lista de reproducción no puede ser nulo o vacío");
        }
        if (tituloCancion == null || tituloCancion.isEmpty()) {
            throw new IllegalArgumentException("El título de la canción no puede ser nulo o vacío");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        ListaReproduccion lista = listaReproduccionRepositorio.findByTitulo(tituloLista);
        if (lista == null) {
            throw new IllegalArgumentException("La lista de reproducción no existe");
        }

        Cancion cancion = cancionRepositorio.findByTitulo(tituloCancion);
        if (cancion == null) {
            throw new IllegalArgumentException("La canción no existe");
        }

        lista.getCanciones().add(cancion);

        // Actualizar la lista en el repositorio
        listaReproduccionRepositorio.update(lista);
    }
}
