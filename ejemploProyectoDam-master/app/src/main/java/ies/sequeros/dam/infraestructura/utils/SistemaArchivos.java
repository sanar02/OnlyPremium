package ies.sequeros.dam.infraestructura.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
