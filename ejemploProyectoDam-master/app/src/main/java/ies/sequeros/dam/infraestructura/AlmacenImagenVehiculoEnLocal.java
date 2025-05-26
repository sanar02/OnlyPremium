package ies.sequeros.dam.infraestructura;

import ies.sequeros.dam.dominio.IAlmacenImagenVehiculo;
import ies.sequeros.dam.infraestructura.utils.SistemaArchivos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implementación del almacenamiento de imágenes de vehículos en el sistema de archivos local.
 * Esta clase se encarga de guardar, eliminar y reemplazar imágenes de vehículos en una ruta específica.
 */
public class AlmacenImagenVehiculoEnLocal implements IAlmacenImagenVehiculo {
    private String ruta;
    public AlmacenImagenVehiculoEnLocal(String path) {
        this.ruta = path;
    }
    @Override
    public String save(String matricula, String path) {
        String nuevaRuta= this.ruta+"/"+matricula+"."+SistemaArchivos.getExtension(path);
        Path p=Path.of("imagenes");
        //en caso de que no exista el directorio
        if(!Files.exists(p)){
            try {
                Files.createDirectory(p);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        SistemaArchivos.copiar(path,nuevaRuta);
        return nuevaRuta;
    }

    @Override
    public void delete(String path) {
        SistemaArchivos.borrar(path);
    }

    @Override
    public String replace(String matricula, String path, String original) {
        String nuevaRuta= this.ruta+"/"+matricula+"."+SistemaArchivos.getExtension(path);
        //se borra el archivo anterior
        SistemaArchivos.borrar(original);
        //se copia el nuevo path
        SistemaArchivos.copiar(path,nuevaRuta);
        return nuevaRuta;
    }
}
