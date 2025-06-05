package es.burgueses.infraestructura;

import es.burgueses.dominio.Usuario;

import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class UsuarioMongoTest {

    private static UsuarioMongo repo;
    private Usuario usuario;

    @BeforeClass
    public static void setup() {
        // Initialize the repository before all tests
        repo = new UsuarioMongo();
    }

    @Before
    public void cleanUp() {
        // Remove all users before each test to ensure a clean state
        repo.deleteAll();
        // Create a new user for each test
        usuario = new Usuario();
        usuario.setNombre("TestUser");
        usuario.setApodo("TestNick");
        usuario.setContrasena("123456");
        usuario.setPathImagen("imagen.png");
    }

    @Test
    public void testAddUsuario() {
        repo.add(usuario);
        System.out.println("Inserted id: " + usuario.getId());
        Usuario found = repo.findById(usuario.getId());
        assertNotNull(found);
        assertEquals("TestUser", found.getNombre());
    }

    @Test
    public void testFindById() {
        // Test finding a user by id
        repo.add(usuario);
        Usuario foundByNombre = repo.findById(usuario.getId());
        assertNotNull(foundByNombre);
        Usuario foundById = repo.findById(foundByNombre.getId());
        assertNotNull(foundById);
        assertEquals(foundByNombre.getId(), foundById.getId());
    }

    @Test
    public void testRemove() {
        // Test removing a user and ensuring it no longer exists
        repo.add(usuario);
        repo.remove(usuario);
        try {
            repo.findById(usuario.getId());
            fail("Expected IllegalArgumentException when searching for a removed user");
        } catch (IllegalArgumentException e) {
            // Expected: user does not exist
        }
    }

    @Test
    public void testFindAll() {
        // Test retrieving all users
        repo.add(usuario);
        List<Usuario> all = repo.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    public void testUpdate() {
        // Test updating a user's nickname and verifying the change
        repo.add(usuario);
        usuario.setApodo("NuevoApo"); // <= 8 chars
        repo.update(usuario);
        Usuario updated = repo.findById(usuario.getId());
        assertEquals("NuevoApo", updated.getApodo());
    }

    @Test
    public void testFindByApodo() {
        // Test finding a user by nickname
        repo.add(usuario);
        Usuario found = repo.findByApodo("TestNick");
        assertNotNull(found);
        assertEquals("TestNick", found.getApodo());
    }
}