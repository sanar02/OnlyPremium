package es.burgueses.aplicacion.cancion;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.IFilesRepositorio;


public class UpdateSongUserCase {
    private ICancionesRepositorio repository;
    private IFilesRepositorio imagesRepository;
    private IFilesRepositorio songsRepository;

    public UpdateSongUserCase(ICancionesRepositorio repository,
                              IFilesRepositorio imagesRepository,
                              IFilesRepositorio songsRepository) {
        this.repository = repository;

        //gestión de ficheros
        this.songsRepository = songsRepository;
        this.imagesRepository = imagesRepository;
    }

    public UpdateSongUserCase(ICancionesRepositorio songsRepository2, ICancionesRepositorio songsRepository3,
            IFilesRepositorio filesRepository) {
        //TODO Auto-generated constructor stub
    }

    public void execute(Cancion item) {
        Cancion original = repository.findById(item.getId());
        if (original == null) {
            throw new IllegalArgumentException("Song with id " + item.getId() + " not found");
        }
        // se ha modifica la canción, se borra la anterior
        if (!original.getPath().equals(item.getPath())) {
            String nuevoPath = this.songsRepository.replace("Song" + item.getId().toString(), item.getPath(),
                    original.getPath());
            item.setPath(nuevoPath);
        }
        // se ha modifica la imagen, se borra la anterior
        if (!original.getPath().equals(item.getPath())) {
            String nuevoPath = this.imagesRepository.replace("SongImg" + item.getId().toString(), item.getPath(),
                    original.getPath());
            item.setPath(nuevoPath);
        }
        repository.update(item);
    }
}
