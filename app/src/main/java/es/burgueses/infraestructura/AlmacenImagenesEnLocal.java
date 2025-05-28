package es.burgueses.infraestructura;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.burgueses.dominio.IAlmacenImagenUsuario;
import es.burgueses.infraestructura.utils.SistemaArchivos;

public class AlmacenImagenesEnLocal implements IAlmacenImagenUsuario {

    private String ruta;

    public AlmacenImagenesEnLocal(String path) {
        this.ruta = path;
    }

    public String save(String nombre, String pathImagen) {

        String nuevaRuta = this.ruta + "/" + nombre + "." + SistemaArchivos.getExtension(pathImagen);
        Path p = Path.of("imagenes");
        if (!Files.exists(p)) {
            try {
                Files.createDirectory(p);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
        SistemaArchivos.copiar(pathImagen, nuevaRuta);
        return nuevaRuta;
    }

    @Override
    public void delete(String pathImagen) {
                SistemaArchivos.borrar(pathImagen);

    }

    @Override
    public String replace(String nombre, String pathImagen,String original) {
        String nuevaRuta = this.ruta + "/" + nombre + "." + SistemaArchivos.getExtension(pathImagen);
        SistemaArchivos.borrar(original);
        SistemaArchivos.copiar(pathImagen, nuevaRuta);
        return nuevaRuta;
    }
}
