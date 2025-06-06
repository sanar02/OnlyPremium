package es.burgueses.aplicacion.cancion;
import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.IFilesRepositorio;

public class AddSongUseCase {
    private ICancionesRepositorio repository;

    private IFilesRepositorio imagesRepository;
    private IFilesRepositorio songsRepository;
    public AddSongUseCase(ICancionesRepositorio repository, IFilesRepositorio imagesRepository, IFilesRepositorio songsRepository) {
        this.repository = repository;
        this.imagesRepository = imagesRepository;
        this.songsRepository= songsRepository;
    }
    public void execute(Cancion item) {
        //se almacena la cancion y la imagen
        String nuevoPath=songsRepository.save(item.getId().toString(),item.getPath());
        item.setPath(nuevoPath);
        String nuevoPathImagen=imagesRepository.save(item.getId().toString(),item.getPath());
        item.setPathImage(nuevoPathImagen);
        repository.add(item);
    }
}
