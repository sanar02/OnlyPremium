package es.burgueses.infraestructura;

import es.burgueses.dominio.Usuario;
import junit.framework.TestCase;

import java.util.List;
import java.util.UUID;

public class UsuarioRepositorioEnMemoriaTest extends TestCase {
    private UsuarioRepositorioEnMemoria ur;
    private Usuario usuarioBase;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ur = new UsuarioRepositorioEnMemoria();
        usuarioBase = new Usuario();
        usuarioBase.setId(UUID.randomUUID().toString());
        usuarioBase.setApodo("apodo");
        ur.add(usuarioBase);
    }

    public void testAdd() {
        Usuario u = new Usuario();
        u.setId(UUID.randomUUID().toString());
        u.setApodo("apodo1");
        ur.add(u);
    }

    public void testGet() {
        Usuario u2 = new Usuario();
        u2.setId(UUID.randomUUID().toString());
        u2.setApodo("apodo2");
        ur.add(u2);

        Usuario encontrado = ur.findById(u2.getId());
        assertNotNull(encontrado);
        assertEquals(u2.getId(), encontrado.getId());
    }

    public void testRemove() {
        int size = ur.findAll().size();
        ur.remove(usuarioBase);
        Usuario eliminado = ur.findById(usuarioBase.getId());
        assertNull(eliminado);

        List<Usuario> total = ur.findAll();
        assertEquals(size - 1, total.size());
    }

    public void testGetAll() {
        assertEquals(ur.findAll().size(), 1);
        Usuario u2 = new Usuario();
        u2.setId(UUID.randomUUID().toString());
        u2.setApodo("apodo2");
        ur.add(u2);
        assertEquals(ur.findAll().size(), 2);
    }

    public void testFindById() {
        Usuario encontrado = ur.findById(usuarioBase.getId());
        assertNotNull(encontrado);
        assertEquals(usuarioBase.getId(), encontrado.getId());
        assertNull(ur.findById(UUID.randomUUID().toString()));
    }

    public void testFindAll() {
        assertEquals(ur.findAll().size(), ur.findAll().size());
    }

    public void testUpdate() {
        Usuario mod = new Usuario();
        mod.setId(usuarioBase.getId());
        mod.setApodo("apodoModificado");
        ur.update(mod);

        Usuario actualizado = ur.findById(usuarioBase.getId());
        assertEquals("apodoModificado", actualizado.getApodo());
    }
}