package ies.sequeros.dam.dominio;

public interface IAlmacenImagenVehiculo {
    public String save(String matricula,String path);
    public void delete(String path);

    /*
    sustituye la imagen del vehiculo, se le pasa la matricula, el path en que
    se almacenará y el original para ser borrado
     */
    public String replace(String matricula,String path,String original);

}
