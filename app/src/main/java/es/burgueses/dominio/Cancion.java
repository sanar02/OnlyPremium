package es.burgueses.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cancion {
    private String titulo;
    public enum Genero {
        ROCK, POP, JAZZ, REGGAETON, CLASSICAL, ELECTRONICA, BLUES, SALSA, METAL
    }
    // Lista de géneros como objetos Genero
    private List<Genero> generos;
    private String path;
    private String idPropietario;
    private String autor;
    private String descripcion;
    private LocalDate fechaAlta;
    private boolean publica;
    private int numeroReproducciones;
    private List<Voto> meGusta;
    private List<Voto> noMeGusta;

    // Constructores
    public Cancion() {
        titulo = "";
        path = "";
        idPropietario = ""; // Asignar un usuario por defecto
        autor = "";
        descripcion = "";
        publica = false;
        numeroReproducciones = 0;
        generos = new ArrayList<>();
        meGusta = new ArrayList<>();
        noMeGusta = new ArrayList<>();
        fechaAlta = LocalDate.now();
    }

    public Cancion(String titulo, List<String> generos, String path, Usuario propietario, String autor,
            String descripcion, LocalDate fechaAlta, boolean publica, int numeroReproducciones, List<String> meGusta,
            List<String> noMeGusta) {
        this.titulo = titulo;
        this.generos = generos.stream()
                .map(Genero::valueOf)
                .toList(); // Convertir de String a Genero
        this.path = path;
        this.idPropietario = idPropietario;
        this.autor = autor;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.publica = publica;
        this.numeroReproducciones = numeroReproducciones;
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getGeneros() {
        return generos.stream()
                .map(Genero::name)
                .toList(); // Convertir de Genero a String
    }

    public void setGeneros(List<String> generos) {
        this.generos = generos.stream()
                .map(Genero::valueOf)
                .toList(); // Convertir de String a Genero
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIdPropietario() {
        return idPropietario;
    }
    public void setIdPropietario(String idPropietario) {
        this.idPropietario = idPropietario;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public boolean isPublica() {
        return publica;
    }

    public void setPublica(boolean publica) {
        this.publica = publica;
    }

    public int getNumeroReproducciones() {
        return numeroReproducciones;
    }

    public void setNumeroReproducciones(int numeroReproducciones) {
        this.numeroReproducciones = numeroReproducciones;
    }

    public List<Voto> getMeGusta() {
        return meGusta;
    }
    public void setMeGusta(List<Voto> meGusta) {
        this.meGusta = meGusta;
    }
    public List<Voto> getNoMeGusta() {
        return noMeGusta;
    }
    public void setNoMeGusta(List<Voto> noMeGusta) {
        this.noMeGusta = noMeGusta;
    }

    @Override
    public String toString() {
        return "Cancion{" +
                "titulo='" + titulo + '\'' +
                ", generos=" + generos +
                ", path='" + path + '\'' +
                ", propietario='" + idPropietario + '\'' +
                ", autor='" + autor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaAlta=" + fechaAlta +
                ", publica=" + publica +
                ", numeroReproducciones=" + numeroReproducciones +
                ", meGusta=" + meGusta +
                ", noMeGusta=" + noMeGusta +
                '}';
    }

}