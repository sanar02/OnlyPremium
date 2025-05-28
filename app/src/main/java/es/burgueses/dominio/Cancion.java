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
        generos.add(Genero.POP); // Asignar un género por defecto
        meGusta = new ArrayList<>();
        noMeGusta = new ArrayList<>();
        fechaAlta = LocalDate.now();
    }

    public Cancion(String titulo, List<String> generos, String path, Usuario propietario, String autor,
            String descripcion, LocalDate fechaAlta, boolean publica, int numeroReproducciones, List<Voto> meGusta,
            List<Voto> noMeGusta) {
        this.titulo = titulo;
        this.generos = generos != null && !generos.isEmpty()
                ? generos.stream().map(Genero::valueOf).toList()
                : List.of(Genero.POP);
        this.path = path;
        this.idPropietario = propietario != null ? propietario.getApodo() : "";
        this.autor = autor;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta != null ? fechaAlta : LocalDate.now();
        this.publica = publica;
        this.numeroReproducciones = numeroReproducciones;
        this.meGusta = meGusta != null ? meGusta : new ArrayList<>();
        this.noMeGusta = noMeGusta != null ? noMeGusta : new ArrayList<>();
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isEmpty() || titulo.trim().equals(" ")) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío");
        }
        this.titulo = titulo;
    }

    public List<String> getGeneros() {
        return generos.stream()
                .map(Genero::name)
                .toList();
    }

    public void setGeneros(List<String> generos) {
        if (generos == null || generos.isEmpty()) {
            throw new IllegalArgumentException("La lista de géneros no puede estar vacía");
        }

        for (String g : generos) {
            try {
                Genero.valueOf(g);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El género '" + g + "' no es válido");
            }
        }
        // El género no puede estar escrito en minúsculas
        if (generos.stream().anyMatch(g -> g.chars().anyMatch(Character::isLowerCase))) {
            throw new IllegalArgumentException("Los géneros deben estar en mayúsculas");
        }

        this.generos = generos.stream()
                .map(Genero::valueOf)
                .toList();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        // Extensiones válidas de audio
        String[] extensionesValidas = { ".mp3", ".wav", ".ogg", ".flac", ".aac", ".wma", ".m4a" };
        boolean extensionValida = false;
        for (String ext : extensionesValidas) {
            if (path.toLowerCase().endsWith(ext)) {
                extensionValida = true;
                break;
            }
        }
        if (!extensionValida) {
            throw new IllegalArgumentException(
                    "La ruta debe tener una extensión de audio válida: .mp3, .wav, .ogg, .flac, .aac, .wma, .m4a");
        } else if (path == null || path.isEmpty() || path.trim().equals("")) {
            throw new IllegalArgumentException("La ruta no puede ser nula o vacía");
        } else {

            this.path = path;
        }
    }

    public String getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(String idPropietario) {
        if(idPropietario == null) {
            throw new IllegalArgumentException("El ID del propietario no puede ser nulo");
        }
        this.idPropietario = idPropietario;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if(autor == null || autor.isEmpty() || autor.trim().equals(" ")) {
            throw new IllegalArgumentException("El autor no puede ser nulo, vacío o llevar espacios antes o después");
        }
        this.autor = autor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if(descripcion == null || descripcion.isEmpty() || descripcion.trim().equals(" ")) {
            throw new IllegalArgumentException("La descripción no puede ser nula, vacía o llevar espacios antes o después");
        } else if (descripcion.length() > 400) {
            throw new IllegalArgumentException("La descripción no puede tener más de 400 caracteres");
        }
        this.descripcion = descripcion;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        if (fechaAlta == null) {
            throw new IllegalArgumentException("La fecha de alta no puede ser nula");
        } else if (fechaAlta!=(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser futura");
        }
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
        if(numeroReproducciones < 0) {
            throw new IllegalArgumentException("El número de reproducciones no puede ser negativo");
        }
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