package es.burgueses.dominio;

import java.util.List;

public interface ICancionesRepositorio {
    public void add(Cancion cancion);
    public void remove(Cancion cancion);
    public Cancion findByTitulo(String titulo);
    public Cancion findById(int id);
    public List<Cancion> findAll();
    public void update(Cancion cancion);
    
    // Métodos adicionales para manejar votos
    public void addVotoMeGusta(String titulo, Voto voto);
    public void addVotoNoMeGusta(String titulo, Voto voto);
    public List<Voto> getVotosMeGusta(String titulo);
    public List<Voto> getVotosNoMeGusta(String titulo);
}