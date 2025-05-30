package es.burgueses.infraestructura;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class CancionRepositorioEnMemoriaTest {
    private CancionRepositorioEnMemoria ic;

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
    @Test
    public void testFindById() {
        assertNotNull(ic.findById(1));
        assertEquals("titulo",ic.findById(1).getTitulo());

        try {
            ic.findById(2);
        } catch (NoSuchElementException e) {
            assertEquals(e.getClass(), NoSuchElementException.class);
        }
    }
    @Test
    public void testFindAll() {
        assertNotNull(ic.findAll());
        assertEquals(1,ic.findAll().size());

    }
    @Test
    public void testUpdate() {
        /**
         * No funciona porque en principio en CancionRepositorioEnMEmoria no se hace
         * un borrado de la canción origial y se añade simplemente otra
         * */
        // Se comprueba estado inicial
        Cancion original=ic.findById(1);
        assertEquals("descripcion", original.getDescripcion());

        // Se cambia el titulo
        Cancion modificar = new Cancion();
        modificar.setIdCancion(1);
        modificar.setTitulo("titulo2");
        ic.update(modificar);

        // comprobar el cambio
        Cancion update = ic.findById(1);
        assertEquals("titulo2", update.getTitulo());

        // no debe añadir ni borrar, tiene que tener el mismo numero de objetos
        assertEquals(1,ic.findAll().size());
    }
}