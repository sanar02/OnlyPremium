package es.burgueses.infraestructura;

import es.burgueses.dominio.Cancion;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class CancionMongoTest {

    private static CancionMongo repo;
    private Cancion cancion;

    @BeforeClass
    public static void setup() {
        repo = new CancionMongo();
    }

    @Before
    public void cleanUp() {
        // Elimina todas las canciones antes de cada test
        for (Cancion c : repo.findAll()) {
            repo.remove(c);
        }
        cancion = new Cancion();
        cancion.setTitulo("TestSong");
        cancion.setAutor("AutorTest");
        cancion.setDescripcion("Descripción de prueba");
        cancion.setPath("test.mp3");
        cancion.setPublica(true);
    }

    @Test
    public void testAddAndFindByTitulo() {
        repo.add(cancion);
        Cancion found = repo.findByTitulo("TestSong");
        assertNotNull(found);
        assertEquals("TestSong", found.getTitulo());
    }

    @Test
    public void testFindById() {
        repo.add(cancion);
        Cancion foundByTitulo = repo.findByTitulo("TestSong");
        assertNotNull(foundByTitulo);
        Cancion foundById = repo.findById(foundByTitulo.getId());
        assertNotNull(foundById);
        assertEquals(foundByTitulo.getId(), foundById.getId());
    }

    @Test
    public void testRemove() {
        repo.add(cancion);
        repo.remove(cancion);
        Cancion found = repo.findByTitulo("TestSong");
        assertNull(found);
    }

    @Test
    public void testFindAll() {
        repo.add(cancion);
        List<Cancion> all = repo.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    public void testUpdate() {
        repo.add(cancion);
        cancion.setDescripcion("Nueva descripción");
        repo.update(cancion);
        Cancion updated = repo.findByTitulo("TestSong");
        assertEquals("Nueva descripción", updated.getDescripcion());
    }

}