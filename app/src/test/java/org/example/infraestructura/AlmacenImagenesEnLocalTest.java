package org.example.infraestructura;

import es.burgueses.aplicacion.infraestructura.AlmacenImagenesEnLocal;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;

import static org.junit.Assert.assertTrue;

public class AlmacenImagenesEnLocalTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private AlmacenImagenesEnLocal almacenImagenesEnLocal;
    private File original;

    @Before
    public void setUp() throws Exception {
        // Crea un directorio temporal
        almacenImagenesEnLocal = new AlmacenImagenesEnLocal(folder.getRoot().getAbsolutePath());
        // Crea un archivo de prueba
        original = folder.newFile("original.jpg");
        Files.writeString(original.toPath(),"contenido");
    }

    @Test
    public void creaDirectorioImagenesYCopiaFichero() {
        String nuevaRuta = almacenImagenesEnLocal.save("usuario1", original.getAbsolutePath());
        File destino = new File(nuevaRuta);

        // debe crearse la carpeta imagenes bajo tempFolder
        assertTrue(new File(folder.getRoot(), "imagenes").isDirectory())
    }
}
