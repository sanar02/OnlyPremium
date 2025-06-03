package es.burgueses.aplicacion.cancion;

import es.burgueses.dominio.ICancionesRepositorio;

public class DeleteSongUserCase {
    ICancionesRepositorio cancionesRepositorio;

    public DeleteSongUserCase(ICancionesRepositorio cancionesRepositorio) {
        this.cancionesRepositorio = cancionesRepositorio;
    }
    public void delete(String cancionId) {
        try {
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID de la canción debe ser un número entero", e);
        }
        var cancion = cancionesRepositorio.findById(cancionId);
        if (cancion == null) {
            throw new IllegalArgumentException("No se encontró la canción con el ID proporcionado");
        }
        cancionesRepositorio.remove(cancion);
    }
}
