package es.burgueses.dominio;

import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cancion {
    private String titulo;

    public enum Genero {
        ROCK, POP, JAZZ, REGGAETON, CLASSICAL, ELECTRONICA, BLUES, SALSA, METAL
    }

    // Lista de géneros como objetos Genero
    private List<String> generos;
    private String path;
    private String idPropietario;
    @BsonId
    private String autor;
    private String descripcion;
    private LocalDate fechaAlta;
    private boolean publica;
    private int numeroReproducciones;
    private List<Voto> meGusta;
    private List<Voto> noMeGusta;
    private UUID id;
    private String pathImage;
    private boolean isPublic;
    private String author;
    private String description;

    // Constructores
    public Cancion() {
        titulo = "";
        path = "";
        idPropietario = ""; // Asignar un usuario por defecto
        this.id = UUID.randomUUID();
        autor = "";
        descripcion = "";
        publica = false;
        numeroReproducciones = 0;
        generos = new ArrayList<>();
        generos.add("POP"); // Asignar un género por defecto
        meGusta = new ArrayList<>();
        noMeGusta = new ArrayList<>();
        fechaAlta = LocalDate.now();
    }

    public Cancion(String titulo, List<String> generos, String path, Usuario propietario, String idCancion, String autor,
            String descripcion, LocalDate fechaAlta, boolean publica, int numeroReproducciones, List<Voto> meGusta,
            List<Voto> noMeGusta) {
        if (titulo == null || titulo.isEmpty() || titulo.trim().equals(" ")) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío");
        }
        this.generos = getGeneros();
        this.path = path;
        this.idPropietario = propietario != null ? propietario.getApodo() : "";
        this.id = (idCancion != null) ? id : UUID.randomUUID();
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
        return generos;
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
            this.generos = generos;
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
        if (idPropietario == null) {
            throw new IllegalArgumentException("El ID del propietario no puede ser nulo");
        } else {
            this.idPropietario = idPropietario;
        }

    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        if (autor == null || autor.isEmpty() || autor.trim().equals(" ")) {
            throw new IllegalArgumentException("El autor no puede ser nulo, vacío o llevar espacios antes o después");
        } else {
            this.autor = autor;
        }

    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        if (descripcion == null || descripcion.isEmpty() || descripcion.trim().equals(" ")) {
            throw new IllegalArgumentException(
                    "La descripción no puede ser nula, vacía o llevar espacios antes o después");
        } else if (descripcion.length() > 400) {
            throw new IllegalArgumentException("La descripción no puede tener más de 400 caracteres");
        } else {
            this.descripcion = descripcion;
        }

    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        // Permite nulos (para deserialización)
        if (fechaAlta == null) {
            this.fechaAlta = null;
            return;
        }
        // Solo valida si no es deserialización (opcional: puedes usar un flag si quieres)
        if (fechaAlta.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de alta no puede ser futura");
        }
        this.fechaAlta = fechaAlta;
    }

    public boolean isPublica() {
        return publica;
    }

    public void setPublica(boolean publica) {
        if (publica != true && publica != false) {
            throw new IllegalArgumentException("El estado de publicación debe ser verdadero o falso");
        } else {
            this.publica = publica;
        }

    }

    public int getNumeroReproducciones() {
        return numeroReproducciones;
    }

    public void setNumeroReproducciones(int numeroReproducciones) {
        if (numeroReproducciones < 0) {
            throw new IllegalArgumentException("El número de reproducciones no puede ser negativo");
        } else {
            this.numeroReproducciones = numeroReproducciones;
        }

    }

    public List<Voto> getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(List<Voto> meGusta) {
        if (meGusta == null) {
            throw new IllegalArgumentException("La lista de 'me gusta' no puede ser nula");
        } else {
            this.meGusta = meGusta;
        }

    }

    public List<Voto> getNoMeGusta() {
        return noMeGusta;
    }

    public void setNoMeGusta(List<Voto> noMeGusta) {
        if (noMeGusta == null) {
            throw new IllegalArgumentException("La lista de 'no me gusta' no puede ser nula");
        } else {
            this.noMeGusta = noMeGusta;
        }

    }

    public void setPathImage(String pathImage) { this.pathImage = pathImage; }
    public void setDescription(String description) { this.description = description; }
    public void setPublic(boolean isPublic) { this.isPublic = isPublic; }
    public void setAuthor(String author) { this.author = author; }

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



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    }

