package es.burgueses.infraestructura;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ListaReproduccion;
import es.burgueses.dominio.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ListaReproduccionRepositorioEnMemoriaTest {

    private ListaReproduccionRepositorioEnMemoria repo;
    private ListaReproduccion lista;
    private Usuario propietario;
    private Cancion cancion;

    @Before
    public void setUp() {
        repo = new ListaReproduccionRepositorioEnMemoria(new CancionRepositorioEnMemoria());
        propietario = new Usuario();
        propietario.setApodo("user1");
        propietario.setTipoUsuario(Usuario.TipoUsuario.USUARIO);
        lista = new ListaReproduccion();
        lista.setIdLista("1");
        lista.setNombre("MiLista");
        lista.setTitulo("MiLista");
        lista.setDescripcion("desc");
        lista.setPropietario(propietario);
        cancion = new Cancion();
        cancion.setIdCancion("c1");
        cancion.setTitulo("Cancion1");
        cancion.setAutor("Autor1");
        cancion.setDescripcion("desc");
        cancion.setPath("path.mp3");
        cancion.setPublica(true);
    }

    @Test
    public void testAddAndFindById() {
        repo.add(lista);
        ListaReproduccion found = repo.findById("1");
        assertNotNull(found);
        assertEquals("MiLista", found.getNombre());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddDuplicada() {
        repo.add(lista);
        repo.add(lista); // Debe lanzar excepción
    }

    @Test
    public void testRemovePropietario() {
        repo.add(lista);
        repo.remove(lista, propietario); // Pasa el propietario
        assertNull(repo.findById("1"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveSinPropietario() {
        lista.setPropietario(null);
        repo.add(lista);
        repo.remove(lista, propietario); // Pasa el propietario, pero la lista no tiene propietario
    }

@Test(expected = IllegalStateException.class)
public void testRemoveNoEsPropietarioNiAdmin() {
    repo.add(lista);
    Usuario propietario = new Usuario();
    propietario.setApodo("prop");
    propietario.setTipoUsuario(Usuario.TipoUsuario.USUARIO);
    lista.setPropietario(propietario);

    Usuario otro = new Usuario();
    otro.setApodo("otro");
    otro.setTipoUsuario(Usuario.TipoUsuario.USUARIO);

    repo.remove(lista, otro); // Pasa ambos argumentos
}

    @Test
    public void testRemoveAdmin() {
        repo.add(lista);
        Usuario admin = new Usuario();
        admin.setApodo("admin");
        admin.setTipoUsuario(Usuario.TipoUsuario.ADMINISTRADOR);
        lista.setPropietario(admin);
        repo.remove(lista, admin);
        assertNull(repo.findById("1"));
    }

    @Test
    public void testFindAll() {
        repo.add(lista);
        List<ListaReproduccion> all = repo.findAll();
        assertEquals(1, all.size());
    }

    @Test
    public void testUpdate() {
        repo.add(lista);
        lista.setDescripcion("nueva");
        repo.update(lista);
        assertEquals("nueva", repo.findById("1").getDescripcion());
    }

//    @Test
//    public void testAddAndRemoveCancionPorId() {
//        repo.add(lista);
//        //repo.cancionRepositorio.add(cancion);
//        repo.getCanciones().add(cancion);
//
//        repo.addCancion("1", "c1");
//        assertEquals(1, repo.findById("1").getCanciones().size());
//        repo.removeCancion("1", "c1");
//        assertEquals(0, repo.findById("1").getCanciones().size());
//    }
//
//    @Test
//    public void testAddAndRemoveCancionPorTitulo() {
//        repo.add(lista);
//        repo.addCancion("MiLista", cancion);
//        assertEquals(1, repo.getCanciones("MiLista").size());
//        repo.removeCancion("MiLista", cancion);
//        assertEquals(0, repo.getCanciones("MiLista").size());
//    }

    @Test
    public void testFindByTitulo() {
        repo.add(lista);
        assertNotNull(repo.findByTitulo("MiLista"));
    }

    @Test
    public void testModifyList() {
        repo.add(lista);
        repo.modifyList("MiLista", "NuevoTitulo", "NuevaDesc", "desc");
        ListaReproduccion mod = repo.findByTitulo("NuevoTitulo");
        assertNotNull(mod);
        assertEquals("NuevaDesc", mod.getDescripcion());
    }
}