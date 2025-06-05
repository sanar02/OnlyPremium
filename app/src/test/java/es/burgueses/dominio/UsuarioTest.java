package es.burgueses.dominio;

import org.junit.Before;
import org.junit.Test;

import es.burgueses.dominio.Usuario;
import es.burgueses.dominio.Usuario.TipoUsuario;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class UsuarioTest {

    private Usuario u;
    private final LocalDate hoyDate = LocalDate.now();

    @Before
    public void setUp() {
        // Creamos un Usuario limpio antes de cada test
        u = new Usuario();
    }

    // --- nombre ---------------------------------------------------------

    @Test
    public void testSetNombreNullThrows() {
        // ¿Qué? Llamar a setNombre(null)
        // ¿Por qué? El nombre no puede ser null
        // ¿Qué esperamos? IllegalArgumentException
        boolean thrown = false;
        try {
            u.setNombre(null);
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
            u.setNombre("    ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setNombre(\"    \") debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetNombreTrimmedValid() {
        // ¿Qué? Llamar a setNombre con espacios delante/detrás
        // ¿Por qué? El setter debe recortar los espacios en los extremos
        u.setNombre("  Juan Perez  ");
        assertEquals("Debe recortar espacios en los extremos",
                "Juan Perez", u.getNombre());
    }

    @Test
    public void testSetNombreInternalMultipleSpacesThrows() {
        // ¿Qué? Llamar a setNombre con múltiples espacios internos
        // ¿Por qué? No se permiten más de un espacio seguido entre palabras
        boolean thrown = false;
        try {
            u.setNombre("Juan   Perez");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setNombre(\"Juan   Perez\") debe lanzar IllegalArgumentException", thrown);
    }

    // --- apodo ----------------------------------------------------------

    @Test
    public void testSetApodoNullThrows() {
        // ¿Qué? Llamar a setApodo(null)
        // ¿Por qué? El apodo no puede ser null
        boolean thrown = false;
        try {
            u.setApodo(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setApodo(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetApodoBlankThrows() {
        // ¿Qué? Llamar a setApodo con solo espacios
        // ¿Por qué? El apodo no puede estar en blanco
        boolean thrown = false;
        try {
            u.setApodo("   ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setApodo(\"   \") debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetApodoTrimmedValid() {
        // ¿Qué? Llamar a setApodo con espacios delante/detrás
        u.setApodo("  alias  ");
        assertEquals("Debe recortar espacios en los extremos",
                "alias", u.getApodo());
    }

    @Test
    public void testSetApodoInternalMultipleSpacesThrows() {
        // ¿Qué? Llamar a setApodo con múltiples espacios internos
        boolean thrown = false;
        try {
            u.setApodo("ali   as");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setApodo(\"ali   as\") debe lanzar IllegalArgumentException", thrown);
    }

    // --- pathImagen -----------------------------------------------------

    @Test
    public void testSetPathImagenNullThrows() {
        // ¿Qué? Llamar a setPathImagen(null)
        // ¿Por qué? La ruta de imagen no puede ser null
        boolean thrown = false;
        try {
            u.setPathImagen(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setPathImagen(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetPathImagenBlankThrows() {
        // ¿Qué? Llamar a setPathImagen con solo espacios
        // ¿Por qué? La ruta no puede estar en blanco
        boolean thrown = false;
        try {
            u.setPathImagen("    ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setPathImagen(\"    \") debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetPathImagenTrimmedValid() {
        // ¿Qué? Llamar a setPathImagen con espacios delante/detrás
        u.setPathImagen("  /img/user.png  ");
        assertEquals("Debe recortar espacios en los extremos",
                "/img/user.png", u.getPathImagen());
    }

    @Test
    public void testSetPathImagenInternalMultipleSpacesThrows() {
        // ¿Qué? Llamar a setPathImagen con múltiples espacios internos
        boolean thrown = false;
        try {
            u.setPathImagen("/img//user.png");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setPathImagen(\"/img//user.png\") debe lanzar IllegalArgumentException", thrown);
    }

    // --- activo ---------------------------------------------------------

    @Test
    public void testSetActivoValid() {
        // ¿Qué? Llamar a setActivo(true) y luego false
        // ¿Por qué? Booleans no requieren validación compleja
        u.setActivo(true);
        assertTrue("setActivo(true) debe reflejarse en isActivo()", u.isActivo());
        u.setActivo(false);
        assertFalse("setActivo(false) debe reflejarse en isActivo()", u.isActivo());
    }

    // --- fechaAlta ------------------------------------------------------

    @Test
    public void testSetFechaAltaNullThrows() {
        // ¿Qué? Llamar a setFechaAlta(null)
        // ¿Por qué? La fecha de alta no puede ser null
        boolean thrown = false;
        try {
            u.setFechaAlta(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setFechaAlta(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetFechaAltaFutureThrows() {
        // ¿Qué? Llamar a setFechaAlta con fecha futura
        boolean thrown = false;
        try {
            u.setFechaAlta(hoyDate.plusDays(1));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setFechaAlta(fecha futura) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetFechaAltaValid() {
        // ¿Qué? Llamar a setFechaAlta con fecha pasada o actual
        LocalDate pasado = hoyDate.minusDays(10);
        u.setFechaAlta(pasado);
        assertEquals("setFechaAlta debe aceptar fechas pasadas", pasado, u.getFechaAlta());
    }

    // --- tipoUsuario ----------------------------------------------------

    @Test
    public void testSetTipoUsuarioNullThrows() {
        // ¿Qué? Llamar a setTipoUsuario(null)
        // ¿Por qué? El tipo de usuario no puede ser null
        boolean thrown = false;
        try {
            u.setTipoUsuario(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setTipoUsuario(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetTipoUsuarioValid() {
        // ¿Qué? Llamar a setTipoUsuario con ADMINISTRADOR
        u.setTipoUsuario(Usuario.TipoUsuario.ADMINISTRADOR);
        assertEquals("setTipoUsuario debe aceptar ADMINISTRADOR",
                Usuario.TipoUsuario.ADMINISTRADOR, u.getTipoUsuario());
    }
}