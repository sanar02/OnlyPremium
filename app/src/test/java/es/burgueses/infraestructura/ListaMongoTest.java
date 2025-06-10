package es.burgueses.infraestructura;

import es.burgueses.dominio.ListaReproduccion;
import es.burgueses.dominio.Usuario;
import org.junit.*;

import java.util.List;

import static org.junit.Assert.*;

public class ListaMongoTest {

    private static ListaMongo repo;
    private ListaReproduccion lista;

    @BeforeClass
    public static void setup() {
        repo = new ListaMongo();
    }

    @Before
    public void cleanUp() {
        repo.deleteAll();
        lista = new ListaReproduccion();
        lista.setIdLista("TestLista");
        lista.setNombre("Lista de Prueba");
        lista.setDescripcion("Descripción de prueba");
        Usuario propietario = new Usuario();
        propietario.setNombre("UsuarioPrueba");
        propietario.setApodo("user");
        propietario.setContrasena("1234567");
        propietario.setTipoUsuario(Usuario.TipoUsuario.USUARIO);
        propietario.setPathImagen("imagen.png"); // <-- Añade una imagen válida
        lista.setPropietario(propietario);
    }

    @Test
    public void testAddAndFindById() {
        repo.add(lista);
        ListaReproduccion found = repo.findById("TestLista");
        assertNotNull(found);
        assertEquals("Lista de Prueba", found.getNombre());
    }

    @Test
    public void testRemove() {
        repo.add(lista);
        Usuario admin = new Usuario();
        admin.setApodo("admin");
        admin.setTipoUsuario(Usuario.TipoUsuario.ADMINISTRADOR);
        repo.remove(lista, admin);
        ListaReproduccion found = repo.findById("TestLista");
        assertNull(found);
    }

    @Test
    public void testFindAll() {
        repo.add(lista);
        List<ListaReproduccion> all = repo.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    public void testUpdate() {
        repo.add(lista);
        lista.setDescripcion("Nueva descripción");
        repo.update(lista);
        ListaReproduccion updated = repo.findById("TestLista");
        assertEquals("Nueva descripción", updated.getDescripcion());
    }
}