package es.burgueses.infraestructura;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class CancionRepositorioEnMemoriaTest {
    private ICancionesRepositorio ic;

    @Before
    public void setUp() throws Exception {
        ic = new CancionRepositorioEnMemoria();
        Cancion c = new Cancion();
        c.setTitulo("titulo");
        c.setDescripcion("descripcion");
        // Deja que el constructor genere el id o asígnalo manualmente:
        // c.setIdCancion(UUID.randomUUID().toString());
        ic.add(c);
    }

    @Test
    public void testAdd() {
        String titulo = "titulo2";
        Cancion c = new Cancion();
        c.setTitulo(titulo);
        c.setDescripcion("descripcion2");
        c.setId(UUID.randomUUID());
        ic.add(c);

        Cancion c2 = ic.findById(c.getId());
        assertNotNull(c2);
        assertEquals(titulo, c2.getTitulo());
    }

    @Test
    public void testRemove() {
        int size = ic.findAll().size();
        Cancion c = ic.findAll().get(0);
        ic.remove(c);
        List<Cancion> total = ic.findAll();
        assertEquals(size - 1, total.size());
    }

    @Test
    public void testFindByTitulo() {
        Cancion encontrada = ic.findByTitulo("titulo");
        assertNotNull(encontrada);
        assertEquals("titulo", encontrada.getTitulo());

        assertNull(ic.findByTitulo("invalido"));
    }

    @Test
    public void testFindById() {
        Cancion c = ic.findAll().get(0);
        assertNotNull(ic.findById(c.getId()));
        assertEquals("titulo", ic.findById(c.getId()).getTitulo());

        assertNull(ic.findById(UUID.randomUUID()));
    }

    @Test
    public void testFindAll() {
        assertNotNull(ic.findAll());
        assertEquals(1, ic.findAll().size());
    }

    @Test
    public void testUpdate() {
        // Implementa si es necesario
    }
}