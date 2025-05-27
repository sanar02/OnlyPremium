package es.burgueses.dominio;

import java.util.List;

public interface ICancionesRepositorio {
    void add(Cancion cancion);
    void remove(Cancion cancion);
    Cancion findByTitulo(String titulo);
    List<Cancion> findAll();
    void update(Cancion cancion);
    
    // Métodos adicionales para manejar votos
    void addVotoMeGusta(String titulo, Voto voto);
    void addVotoNoMeGusta(String titulo, Voto voto);
    List<Voto> getVotosMeGusta(String titulo);
    List<Voto> getVotosNoMeGusta(String titulo);
}