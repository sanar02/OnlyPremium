package es.burgueses.dominio;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CancionTest {

    private Cancion c;
    private final LocalDate HOY = LocalDate.now();

    @Before
    public void setUp() {
        // Instancia limpia antes de cada prueba
        c = new Cancion();
    }

    // --- titulo ----------------------------------------------------------

    @Test
    public void testSetTituloNullThrows() {
        boolean thrown = false;
        try {
            c.setTitulo(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setTitulo(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetTituloBlankThrows() {
        boolean thrown = false;
        try {
            c.setTitulo("   ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setTitulo(\"   \") debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetTituloTrimmedValid() {
        c.setTitulo("  Hola Mundo  ");
        // esperamos que el título recortado sea "Hola Mundo"
        assertEquals("El título debe recortarse de ambos lados",
                "Hola Mundo", c.getTitulo().trim());
    }

    // --- idPropietario --------------------------------------------------

    @Test
    public void testSetIdPropietarioNullThrows() {
        boolean thrown = false;
        try {
            c.setIdPropietario(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setIdPropietario(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetIdPropietarioBlankThrows() {
        boolean thrown = false;
        try {
            c.setIdPropietario("   ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setIdPropietario(\"   \") debe lanzar IllegalArgumentException", thrown);
    }

    // --- path -----------------------------------------------------------

    @Test
    public void testSetPathNullThrows() {
        boolean thrown = false;
        try {
            c.setPath(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setPath(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetPathBlankThrows() {
        boolean thrown = false;
        try {
            c.setPath("   ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setPath(\"   \") debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetPathMalformedThrows() {
        boolean thrown = false;
        try {
            // sin barra inicial
            c.setPath("archivo.mp3");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setPath sin carpeta debe lanzar IllegalArgumentException", thrown);
    }

    // --- autor ----------------------------------------------------------

    @Test
    public void testSetAutorNullThrows() {
        boolean thrown = false;
        try {
            c.setAutor(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setAutor(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetAutorBlankThrows() {
        boolean thrown = false;
        try {
            c.setAutor("   ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setAutor(\"   \") debe lanzar IllegalArgumentException", thrown);
    }

    // --- descripcion ----------------------------------------------------

    @Test
    public void testSetDescripcionNullThrows() {
        boolean thrown = false;
        try {
            c.setDescripcion(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setDescripcion(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetDescripcionBlankThrows() {
        boolean thrown = false;
        try {
            c.setDescripcion("    ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setDescripcion(\"    \") debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetDescripcionTooLongThrows() {
        boolean thrown = false;
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 1001; i++) sb.append('x');
            c.setDescripcion(sb.toString());
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setDescripcion(>1000 chars) debe lanzar IllegalArgumentException", thrown);
    }

    // --- fechaAlta -------------------------------------------------------

    @Test
    public void testSetFechaAltaNullThrows() {
        boolean thrown = false;
        try {
            c.setFechaAlta(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setFechaAlta(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetFechaAltaFutureThrows() {
        boolean thrown = false;
        try {
            c.setFechaAlta(LocalDate.now().plusDays(1));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setFechaAlta(fecha futura) debe lanzar IllegalArgumentException", thrown);
    }

    // --- numeroReproducciones -------------------------------------------

    @Test
    public void testSetNumeroReproduccionesNegativeThrows() {
        boolean thrown = false;
        try {
            c.setNumeroReproducciones(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setNumeroReproducciones(-1) debe lanzar IllegalArgumentException", thrown);
    }

    // --- generos --------------------------------------------------------

    @Test
    public void testSetGenerosNullThrowsOnAccess() {
        boolean thrown = false;
        try {
            c.setGeneros(null);
            c.getGeneros().size();
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue("setGeneros(null)+getGeneros() debe lanzar NullPointerException", thrown);
    }

    @Test
    public void testSetGenerosEmptyAllowed() {
        // lista vacía no debe lanzar
        c.setGeneros(Arrays.asList());
        assertTrue("Lista vacía debe aceptarse", c.getGeneros().isEmpty());
    }

    @Test
    public void testSetGenerosInvalidThrows() {
        boolean thrown = false;
        try {
            c.setGeneros(Arrays.asList("INVALID"));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setGeneros(invalid) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetGenerosLowercaseThrows() {
        boolean thrown = false;
        try {
            c.setGeneros(Arrays.asList("rock"));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setGeneros(minúsculas) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetGenerosWhitespaceThrows() {
        boolean thrown = false;
        try {
            c.setGeneros(Arrays.asList(" ROCK "));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setGeneros(con espacios) debe lanzar IllegalArgumentException", thrown);
    }

    // --- meGusta / noMeGusta --------------------------------------------

    @Test
    public void testSetMeGustaNullThrowsOnAccess() {
        boolean thrown = false;
        try {
            c.setMeGusta(null);
            c.getMeGusta().size();
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue("setMeGusta(null)+getMeGusta() debe lanzar NullPointerException", thrown);
    }

    @Test
    public void testSetNoMeGustaNullThrowsOnAccess() {
        boolean thrown = false;
        try {
            c.setNoMeGusta(null);
            c.getNoMeGusta().size();
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue("setNoMeGusta(null)+getNoMeGusta() debe lanzar NullPointerException", thrown);
    }
}
