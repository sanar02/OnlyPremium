package es.burgueses.infraestructura;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ListaReproduccion;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class ListaMongoTest {

    private ListaMongo listaMongo;
    private ListaReproduccion lista;
    private Cancion cancion;

    @Before
    public void setUp() {
        listaMongo = new ListaMongo();
        lista = new ListaReproduccion();
        lista.setIdLista(UUID.randomUUID().toString());
        lista.setTitulo("ListaTest");
        lista.setNombre("ListaTest");
        lista.setDescripcion("Descripción de prueba");
        lista.setCanciones(new java.util.ArrayList<>());

        // Inicializa un propietario válido
        es.burgueses.dominio.Usuario propietario = new es.burgueses.dominio.Usuario();
        propietario.setId(UUID.randomUUID().toString());
        propietario.setNombre("PropietarioTest");
        propietario.setApodo("propTest");
        propietario.setContrasena("123456");
        propietario.setFechaAlta(java.time.LocalDate.now());
        propietario.setPathImagen("/img/user.png"); // <-- Añade esta línea
        lista.setPropietario(propietario);

        cancion = new Cancion();
        cancion.setId(UUID.randomUUID());
        cancion.setTitulo("CancionTest");
        cancion.setDescripcion("Canción de prueba");
        cancion.setAutor("AutorTest");
        cancion.setPath("/test.mp3"); // <-- Añade esta línea
    }


    @Test
    public void testAdd() {
        listaMongo.add(lista);
        // Si no lanza excepción, la inserción fue exitosa
        ListaReproduccion encontrada = listaMongo.findById(UUID.fromString(lista.getIdLista()));
        assertNotNull(encontrada);
    }

    @Test
    public void testFindById() {
        listaMongo.add(lista);
        ListaReproduccion encontrada = listaMongo.findById(UUID.fromString(lista.getIdLista()));
        assertNotNull(encontrada);
        assertEquals(lista.getNombre(), encontrada.getNombre());
    }

    @Test
    public void testFindByTitulo() {
        listaMongo.add(lista);
        ListaReproduccion encontrada = listaMongo.findByTitulo("ListaTest");
        assertNotNull(encontrada);
        assertEquals("ListaTest", encontrada.getNombre());
    }

    @Test
    public void testRemove() {
        listaMongo.add(lista);
        listaMongo.remove(lista);
        ListaReproduccion encontrada = listaMongo.findById(UUID.fromString(lista.getIdLista()));
        assertNull(encontrada);
    }

    @Test
    public void testUpdate() {
        listaMongo.add(lista);
        lista.setDescripcion("Nueva descripción");
        listaMongo.update(lista);
        ListaReproduccion actualizada = listaMongo.findById(UUID.fromString(lista.getIdLista()));
        assertEquals("Nueva descripción", actualizada.getDescripcion());
    }

    @Test
    public void testFindAll() {
        listaMongo.add(lista);
        List<ListaReproduccion> todas = listaMongo.findAll();
        assertFalse(todas.isEmpty());
    }

    @Test
    public void testAddCancion() {
        listaMongo.add(lista);
        listaMongo.addCancion(lista.getNombre(), cancion);
        ListaReproduccion encontrada = listaMongo.findByTitulo(lista.getNombre());
        assertTrue(encontrada.getCanciones().stream()
                .anyMatch(c -> c.getTitulo().equals("CancionTest")));
    }

    @Test
    public void testRemoveCancion() {
        listaMongo.add(lista);
        listaMongo.addCancion(lista.getNombre(), cancion);
        listaMongo.removeCancion(lista.getNombre(), cancion);
        ListaReproduccion encontrada = listaMongo.findByTitulo(lista.getNombre());
        assertTrue(encontrada.getCanciones().isEmpty());
    }

    @Test
    public void testModifyList() {
        listaMongo.add(lista);
        listaMongo.modifyList(lista.getNombre(), "NuevoNombre", "NuevaDesc", "");
        ListaReproduccion modificada = listaMongo.findByTitulo("NuevoNombre");
        assertNotNull(modificada);
        assertEquals("NuevaDesc", modificada.getDescripcion());
    }
}