package es.burgueses.infraestructura;

import org.junit.Before;
import org.junit.Test;

import es.burgueses.dominio.Cancion;
import es.burgueses.infraestructura.CancionMongo;

public class CancionMongoTest {

    private CancionMongo cancionRepositorio;

    @Before
    public void setUp() {
        cancionRepositorio = new CancionMongo();

    }

    @Test
    public void testAñadirCancion() {
        // Implementar prueba para guardar una canción en MongoDB
    }

    @Test
    public void testBuscarCancion() {
        // Implementar prueba para buscar una canción por su ID en MongoDB
    }

    @Test
    public void testModificarCancion() {
        // Implementar prueba para modificar una canción en MongoDB
    }

    @Test
    public void testEliminarCancion() {
        // Implementar prueba para eliminar una canción de MongoDB
    }

}
