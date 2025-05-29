package es.burgueses.dominio.dominio;

import org.junit.Before;
import org.junit.Test;

import es.burgueses.dominio.ListaReproduccion;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ListaReproduccionTest {

    private ListaReproduccion lr;
    private final LocalDate HOY = LocalDate.now();

    @Before
    public void setUp() {
        // Creamos una instancia limpia antes de cada prueba
        lr = new ListaReproduccion();
    }

    // --- nombre ---------------------------------------------------------

    @Test
    public void testSetNombreNullThrows() {
        // ¿Qué? Llamar a setNombre(null)
        // ¿Por qué? El nombre no puede ser null
        // ¿Esperamos? IllegalArgumentException
        boolean thrown = false;
        try {
            lr.setNombre(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setNombre(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetNombreBlankThrows() {
        // ¿Qué? Llamar a setNombre con solo espacios
        // ¿Por qué? El nombre no puede estar en blanco
        boolean thrown = false;
        try {
            lr.setNombre("    ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setNombre(\"    \") debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetNombreTrimmedValid() {
        // ¿Qué? Llamar a setNombre con espacios delante/detrás
        // ¿Por qué? Debe recortar automáticamente
        lr.setNombre("  Mi Lista  ");
        assertEquals("Mi Lista", lr.getNombre());
    }

    @Test
    public void testSetNombreInternalMultipleSpacesThrows() {
        // ¿Qué? Llamar a setNombre con varios espacios internos
        // ¿Por qué? No se permiten más de un espacio seguido
        boolean thrown = false;
        try {
            lr.setNombre("Mi    Lista");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setNombre(\"Mi    Lista\") debe lanzar IllegalArgumentException", thrown);
    }

    // --- descripcion ----------------------------------------------------

    @Test
    public void testSetDescripcionNullThrows() {
        // ¿Qué? Llamar a setDescripcion(null)
        // ¿Por qué? La descripción no puede ser null
        boolean thrown = false;
        try {
            lr.setDescripcion(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setDescripcion(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetDescripcionBlankThrows() {
        // ¿Qué? Llamar a setDescripcion con solo espacios
        // ¿Por qué? La descripción no puede estar en blanco
        boolean thrown = false;
        try {
            lr.setDescripcion("    ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setDescripcion(\"    \") debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetDescripcionTrimmedValid() {
        // ¿Qué? Llamar a setDescripcion con espacios delante/detrás
        lr.setDescripcion("  Descripción de prueba  ");
        assertEquals("Descripción de prueba", lr.getDescripcion());
    }

    @Test
    public void testSetDescripcionInternalMultipleSpacesThrows() {
        // ¿Qué? Llamar a setDescripcion con varios espacios internos
        boolean thrown = false;
        try {
            lr.setDescripcion("Desc    de prueba");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setDescripcion(\"Desc    de prueba\") debe lanzar IllegalArgumentException", thrown);
    }

    // --- propietario ----------------------------------------------------

    @Test
    public void testSetPropietarioNullThrows() {
        // ¿Qué? Llamar a setPropietario(null)
        // ¿Por qué? Debe existir siempre un propietario válido
        boolean thrown = false;
        try {
            lr.setPropietario(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setPropietario(null) debe lanzar IllegalArgumentException", thrown);
    }

    // --- canciones ------------------------------------------------------

    @Test
    public void testSetCancionesNullThrowsOnAccess() {
        // ¿Qué? Asignar null y luego acceder a la lista
        boolean thrown = false;
        try {
            lr.setCanciones(null);
            lr.getCanciones().size();
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue("setCanciones(null) + getCanciones() debe lanzar NullPointerException", thrown);
    }

    @Test
    public void testSetCancionesEmptyAllowed() {
        // ¿Qué? Asignar lista vacía
        lr.setCanciones(Collections.emptyList());
        assertTrue("Lista de canciones vacía debe aceptarse", lr.getCanciones().isEmpty());
    }

    // --- fechaCreacion --------------------------------------------------

    @Test
    public void testSetFechaCreacionNullThrows() {
        // ¿Qué? Llamar a setFechaCreacion(null)
        boolean thrown = false;
        try {
            lr.setFechaCreacion(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setFechaCreacion(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetFechaCreacionFutureThrows() {
        // ¿Qué? Llamar con fecha en el futuro
        boolean thrown = false;
        try {
            lr.setFechaCreacion(HOY.plusDays(1));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setFechaCreacion(fecha futura) debe lanzar IllegalArgumentException", thrown);
    }

    // --- meGusta --------------------------------------------------------

    @Test
    public void testSetMeGustaNullThrowsOnAccess() {
        // ¿Qué? Asignar null y luego acceder
        boolean thrown = false;
        try {
            lr.setMeGusta(null);
            lr.getMeGusta().size();
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue("setMeGusta(null) + getMeGusta() debe lanzar NullPointerException", thrown);
    }

    @Test
    public void testSetMeGustaEmptyAllowed() {
        // ¿Qué? Asignar lista vacía de votos
        lr.setMeGusta(Collections.emptyList());
        assertTrue("meGusta vacía debe aceptarse", lr.getMeGusta().isEmpty());
    }

    // --- noMeGusta ------------------------------------------------------

    @Test
    public void testSetNoMeGustaNullThrowsOnAccess() {
        // ¿Qué? Asignar null y luego acceder
        boolean thrown = false;
        try {
            lr.setNoMeGusta(null);
            lr.getNoMeGusta().size();
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue("setNoMeGusta(null) + getNoMeGusta() debe lanzar NullPointerException", thrown);
    }

    @Test
    public void testSetNoMeGustaEmptyAllowed() {
        // ¿Qué? Asignar lista vacía de votos negativos
        lr.setNoMeGusta(Collections.emptyList());
        assertTrue("noMeGusta vacía debe aceptarse", lr.getNoMeGusta().isEmpty());
    }
}
