package es.burgueses.aplicacion.cancion;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.IAlmacenImagen;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.IFilesRepositorio;

public class UpdateSongUserCase {
    private ICancionesRepositorio repository;
    private IAlmacenImagen imagesRepository;
    private IFilesRepositorio songsRepository;

    public UpdateSongUserCase(ICancionesRepositorio repository,
                              IAlmacenImagen imagesRepository,
                              IFilesRepositorio songRepository) {
        this.repository = repository;

        //gestión de ficheros
        this.songsRepository = songRepository;
        this.imagesRepository = imagesRepository;
    }

    public void execute(Cancion item) {
        Cancion original = repository.findById(item.getId());
        if (original == null) {
            throw new IllegalArgumentException("Song with id " + item.getId() + " not found");
        }
        // se ha modifica la canción, se borra la anterior
        if (!original.getPath().equals(item.getPath())) {
            String nuevoPath = this.songsRepository.replace("Song" + item.getId(), item.getPath(),
                    original.getPath());
            item.setPath(nuevoPath);
        }
        // se ha modifica la imagen, se borra la anterior
        if (!original.getPath().equals(item.getPath())) {
            String nuevoPath = this.imagesRepository.replace("SongImg" + item.getId().toString(), item.getPath(),
                    original.getPath());
            item.setPathImage(nuevoPath);
        }
        repository.update(item);
    }
}
