package es.burgueses.dominio;

public interface IAlmacenImagenUsuario {

    public String save(String nombre, String pathImagen);

    public void delete(String path);

    public String replace(String nombre, String pathImagen, String original);

}


