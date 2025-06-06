package es.burgueses.dominio;

import java.util.UUID;

public interface IAlmacenImagen {

    public String save(String nombre, String pathImagen);

    public void delete(String path);

    public String replace(String nombre, UUID pathImagen, UUID original);

    public String replace(String nombre, String path, String path2);

}


