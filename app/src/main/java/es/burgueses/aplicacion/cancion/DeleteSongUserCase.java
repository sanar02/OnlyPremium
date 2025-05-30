package es.burgueses.aplicacion.cancion;

import es.burgueses.dominio.ICancionesRepositorio;

public class DeleteSongUserCase {
    ICancionesRepositorio cancionesRepositorio;

    public DeleteSongUserCase(ICancionesRepositorio cancionesRepositorio) {
        this.cancionesRepositorio = cancionesRepositorio;
    }
    public void delete(String cancionId) {
        int id;
        try {
            id = Integer.parseInt(cancionId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El ID de la canción debe ser un número entero", e);
        }
        var cancion = cancionesRepositorio.findById(id);
        if (cancion == null) {
            throw new IllegalArgumentException("No se encontró la canción con el ID proporcionado");
        }
        cancionesRepositorio.remove(cancion);
    }
}
