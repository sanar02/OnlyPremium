package es.burgueses.aplicacion.cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.Cancion;

public class ModSongUserCase {
    private final ICancionesRepositorio cancionesRepositorio;

    public ModSongUserCase(ICancionesRepositorio cancionesRepositorio) {
        this.cancionesRepositorio = cancionesRepositorio;
    }

    public void modify(String idCancion, String nuevoTitulo, String nuevoAutor, String nuevaDescripcion, String nuevoPath) {
        java.util.UUID uuid = java.util.UUID.fromString(idCancion);
        Cancion cancion = cancionesRepositorio.findById(uuid);
        if (cancion == null) {
            throw new IllegalArgumentException("No se encontró la canción con el ID proporcionado");
        }
        if (nuevoTitulo != null && !nuevoTitulo.isEmpty()) {
            cancion.setTitulo(nuevoTitulo);
        }
        if (nuevoAutor != null && !nuevoAutor.isEmpty()) {
            cancion.setAutor(nuevoAutor);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.isEmpty()) {
            cancion.setDescripcion(nuevaDescripcion);
        }
        if (nuevoPath != null && !nuevoPath.isEmpty()) {
            cancion.setPath(nuevoPath);
        }
        cancionesRepositorio.update(cancion);
    }
}
