package es.burgueses.dominio.infraestructura;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.infraestructura.CancionRepositorioEnMemoria;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class ICancionesRepositorioTest {
    private ICancionesRepositorio ic;

    @Before
    public void setUp() throws Exception {
        ic=new CancionRepositorioEnMemoria();
        Cancion c=new Cancion();
        c.setTitulo("titulo");
        c.setDescripcion("descripcion");
        c.setIdCancion(1);
        ic.add(c);
    }

    @Test
    public void testAdd() {
        String titulo = "titulo";
        
        Cancion c=new Cancion();
        c.setTitulo(titulo);
        c.setDescripcion("descripcion2");
        c.setIdCancion(2);
        ic.add(c);

        Cancion c2=ic.findById(c.getIdCancion());
        assertNotNull(c2);
        assertEquals(titulo, c2.getTitulo());
    }
    @Test
    public void testRemove() {
        int size=ic.findAll().size();
        ic.remove(ic.findById(1));
        try{
            ic.findById(1);
        } catch (NoSuchElementException e) {
            assertEquals(e.getClass(), NoSuchElementException.class);

        }

        List<Cancion> total = ic.findAll();
        assertEquals(size-1, total.size());
    }

    @Test
    public void testFindByTitulo() {
        Cancion encontrada=ic.findByTitulo("titulo");
        assertNotNull(encontrada);
        assertEquals(1,encontrada.getIdCancion());

        assertNull(ic.findByTitulo("invalido"));
    }

    public void testFindById() {
    }

    public void testFindAll() {
    }

    public void testUpdate() {
    }
}