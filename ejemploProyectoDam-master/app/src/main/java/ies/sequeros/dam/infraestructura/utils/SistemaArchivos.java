package ies.sequeros.dam.infraestructura.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// Utilidades para la gestión de archivos en el sistema.
// Proporciona métodos para copiar, borrar y obtener información de archivos.
// Esta clase utiliza la API de NIO de Java para realizar operaciones de archivos de manera eficiente y segura.
// La clase SistemaArchivos es una utilidad que proporciona métodos para realizar operaciones comunes en archivos, como copiar, borrar y obtener la extensión o el nombre de un archivo.
// Se utiliza en la aplicación para manejar archivos de manera abstracta, permitiendo que otras partes del sistema interactúen con los archivos sin preocuparse por los detalles de implementación.
// Esta clase es parte de la infraestructura de la aplicación y se utiliza en varios casos de uso relacionados con la gestión de archivos, como la carga y descarga de imágenes de vehículos o la manipulación de documentos asociados a mecánicos o vehículos.
public class SistemaArchivos {
    public static void copiar(String origen,String destino){
        Path pathDestino=Path.of(destino);
        Path pathOrigen=Path.of(origen);
        if(!Files.exists(pathOrigen)){
           throw new RuntimeException("No existe el fichero origen");
        }

        try {
            Files.copy(pathOrigen,pathDestino);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void borrar(String  pathFichero) {
        Path ruta=Path.of(pathFichero);
        if(!pathFichero.isBlank()) {
            try {
                Files.deleteIfExists(ruta);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static String getExtension(String path) {
        int punto= path.lastIndexOf('.');
        if (punto > 0 && punto < path.length() - 1) {
            return path.substring(punto + 1);
        } else {
            return "";
        }
    }
    public static  String getName(String path) {
        Path ruta = Paths.get(path);
        String nombreArchivo = ruta.getFileName().toString();
        return nombreArchivo.replaceFirst("[.][^.]+$", "");

    }

}
