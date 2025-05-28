package es.burgueses.aplicacion.cancion;
import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;

public class AddSongUserCase {
    ICancionesRepositorio cancionesRepositorio;
    public AddSongUserCase(ICancionesRepositorio cancionesRepositorio) {
        this.cancionesRepositorio = cancionesRepositorio;
    }
    public void execute(Cancion cancion) {
        if (cancion == null || cancion.getTitulo() == null || cancion.getTitulo().isEmpty()) {
            cancionesRepositorio.add(cancion);
        } else {
            throw new IllegalArgumentException("La canción no puede ser nula o tener un título vacío");
        }
    }
}
