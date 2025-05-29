package es.burgueses.aplicacion.cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.Cancion;

public class GetSongUserCase {
    ICancionesRepositorio cancionesRepositorio;

    public GetSongUserCase(ICancionesRepositorio cancionesRepositorio) {
        this.cancionesRepositorio = cancionesRepositorio;
    }

    public Cancion getSong(String cancionId) {
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
        return cancion;
    }
    
}
