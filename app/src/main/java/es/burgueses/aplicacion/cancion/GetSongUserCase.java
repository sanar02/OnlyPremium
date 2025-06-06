package es.burgueses.aplicacion.cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.Cancion;

public class GetSongUserCase {
    ICancionesRepositorio cancionesRepositorio;

    public GetSongUserCase(ICancionesRepositorio cancionesRepositorio) {
        this.cancionesRepositorio = cancionesRepositorio;
    }

    public Cancion getSong(String cancionId) {
        try {
            java.util.UUID uuid = java.util.UUID.fromString(cancionId);
            var cancion = cancionesRepositorio.findById(uuid);
            if (cancion == null) {
                throw new IllegalArgumentException("No se encontró la canción con el ID proporcionado");
            }
            return cancion;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El ID de la canción debe ser un UUID válido", e);
        }
    }
    
}
