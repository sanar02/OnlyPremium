package es.burgueses.aplicacion.cancion;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.IFilesRepositorio;

public class DeleteSongUserCase {
    private ICancionesRepositorio repository;
    private IFilesRepositorio imagesRepository;
    private IFilesRepositorio songsRepository;

    public DeleteSongUserCase(ICancionesRepositorio repository,
                              IFilesRepositorio imagesRepository,
                              IFilesRepositorio songsRepository) {
        this.repository = repository;
        this.imagesRepository = imagesRepository;
        this.songsRepository = songsRepository;
    }

    public void execute(Cancion item) {
        imagesRepository.delete(item.getPath());
        songsRepository.delete(item.getPath());
        repository.remove(item);
    }
}
