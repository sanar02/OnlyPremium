package es.burgueses.infraestructura;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.Usuario;
import es.burgueses.infraestructura.UsuarioRepositorioEnMemoria;
import junit.framework.TestCase;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

public class UsuarioRepositorioEnMemoriaTest extends TestCase {
    private UsuarioRepositorioEnMemoria ur;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ur = new UsuarioRepositorioEnMemoria();
        Usuario usuario = new Usuario();
        usuario.setApodo("apodo");
        ur.add(usuario);
    }

    public void testAdd() {
        Usuario u = new Usuario();
        u.setApodo("apodo1");
        ur.add(u);
    }

    public void testGet() {
        String apodo = "apodo2";
        Usuario u2 = new Usuario();
        u2.setApodo(apodo);
        ur.add(u2);

        Usuario encontrado = ur.get(apodo);
        assertNotNull(encontrado);
        assertEquals(apodo, encontrado.getApodo());
    }

    public void testRemove() {
        String apodo = "apodo";
        int size=ur.findAll().size();
        ur.remove(ur.findByApodo(apodo));
        try{
            ur.findByApodo(apodo);
        } catch (NoSuchElementException e) {
            assertEquals(e.getClass(), NoSuchElementException.class);

        }

        List<Usuario> total = ur.findAll();
        assertEquals(size-1, total.size());
    }

    public void testGetAll() {
        assertEquals(ur.findAll().size(), 1);
        Usuario u2 = new Usuario();
        u2.setApodo("apodo2");
        ur.add(u2);
        assertEquals(ur.findAll().size(), 2);
    }

    public void testFindByApodo() {
        Usuario f = ur.findByApodo("apodo");
        assertNotNull(f);
        assertEquals("apodo", f.getApodo());
        assertNull(ur.findByApodo("apodo3"));
    }

    public void testFindAll() {
        assertEquals(ur.findAll().size(), ur.findAll().size());
    }

    public void testUpdate() {
        String apodo = "apodo2";
        Usuario mod = new Usuario();
        mod.setApodo(apodo);
        ur.update(mod);

        assertEquals(apodo, ur.findByApodo(apodo).getApodo());
    }
}