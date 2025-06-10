package es.burgueses.infraestructura.files;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import es.burgueses.dominio.IFilesRepositorio;
import es.burgueses.infraestructura.utils.SistemaArchivos;


public class LocalFilesRepository implements IFilesRepositorio {
    private String ruta;
    public LocalFilesRepository(String path) {
        this.ruta = path;
    }
    @Override
    public String save(String matricula, String path) {
        String nuevaRuta= this.ruta+"/"+matricula+"."+ SistemaArchivos.getExtension(path);
        Path p=Path.of(this.ruta);
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

    /**
     * Remplaza un fichero por otro
     * @param nuevoNombre  nombre que tomará el fichero
     * @param path  ruta en que se encuentra el fichero a mover
     * @param original ruta del original, que se borrará
     * @return
     */
    @Override
    public String replace(String nuevoNombre, String path, String original) {
        String nuevaRuta= this.ruta+"/"+nuevoNombre+"."+SistemaArchivos.getExtension(path);
        //se borra el archivo anterior
        SistemaArchivos.borrar(original);
        //se copia el nuevo path
        SistemaArchivos.copiar(path,nuevaRuta);
        return nuevaRuta;
    }
    public void close(){

    }
    @Override
    public String replace(String nuevoNombre, UUID path, UUID original) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }
}
