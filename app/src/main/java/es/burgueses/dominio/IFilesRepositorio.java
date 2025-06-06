package es.burgueses.dominio;

import java.util.UUID;

public interface IFilesRepositorio {
    public String save(String nuevoNombre,String path);
    public void delete(String path);

    /*
    sustituye la imagen del vehiculo, se le pasa la matricula, el path en que
    se almacenará y el original para ser borrado
     */
    public String replace(String nuevoNombre,UUID path,UUID original);
    public void close();
    public String replace(String nuevoNombre, String path, String path2);
}
