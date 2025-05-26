package ies.sequeros.dam.dominio;
// Interfaz para el almacenamiento de imágenes de vehículos
// Esta interfaz define los métodos necesarios para guardar, eliminar y reemplazar imágenes de vehículos
// en un sistema de almacenamiento.
// Se espera que las implementaciones manejen la lógica específica de almacenamiento, como el uso de un sistema de archivos,
// una base de datos o un servicio en la nube.
// La interfaz permite la separación de preocupaciones, facilitando la prueba y el mantenimiento del código relacionado con
// la gestión de imágenes de vehículos.
// Esta interfaz es parte del dominio de la aplicación y se utiliza en los casos de uso relacionados con vehículos,
// como la creación, actualización y eliminación de vehículos, donde las imágenes asociadas deben ser gestionadas adecuadamente.

public interface IAlmacenImagenVehiculo {
    public String save(String matricula,String path);
    public void delete(String path);

    /*
    sustituye la imagen del vehiculo, se le pasa la matricula, el path en que
    se almacenará y el original para ser borrado
     */
    public String replace(String matricula,String path,String original);

}
