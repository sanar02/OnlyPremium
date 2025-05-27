package es.burgueses.aplicacion.dominio;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CancionTest {

    private Cancion c;

    @Before
    public void setUp() {
        // Creamos una canción nueva antes de cada prueba
        c = new Cancion();
    }

    @Test
    public void testTituloPorDefecto() {
        // ¿Qué probamos? El título justo al crear la canción sin hacer nada.
        // ¿Por qué? Para asegurarnos de que no hay “null” ni caracteres extraños.
        // ¿Qué esperamos? Que sea una cadena vacía.
        String titulo = c.getTitulo();
        assertEquals("El título por defecto debe estar vacío", "", titulo);
    }

    @Test
    public void testNoEsPublicaPorDefecto() {
        // ¿Qué probamos? Si la canción comienza como pública o privada.
        // ¿Por qué? El requisito es que por defecto no sea pública.
        // ¿Qué esperamos? isPublica() retorne false.
        assertFalse("Por defecto la canción no debe ser pública", c.isPublica());
    }

    @Test
    public void testSetPublicaFuncion() {
        // ¿Qué probamos? Cambiar la canción a pública.
        // ¿Por qué? Para comprobar que setPublica(true) realmente funciona.
        // ¿Qué esperamos? isPublica() sea true después de ponerla pública.
        c.setPublica(true);
        assertTrue("Después de setPublica(true) debe ser pública", c.isPublica());
    }

    @Test
    public void testGenerosValidos() {
        // ¿Qué probamos? Poner una lista de géneros válidos.
        // ¿Por qué? Para ver que el código convierte bien los nombres a enum.
        // ¿Qué esperamos? Que getGeneros() devuelva exactamente esos nombres.
        List<String> input = Arrays.asList("ROCK", "POP");
        c.setGeneros(input);
        List<String> salida = c.getGeneros();
        assertEquals("Debe haber 2 géneros", 2, salida.size());
        assertTrue("Debe contener 'ROCK'", salida.contains("ROCK"));
        assertTrue("Debe contener 'POP'",  salida.contains("POP"));
    }

    @Test
    public void testGenerosInvalidos() {
        // ¿Qué probamos? Poner un género que no existe.
        // ¿Por qué? Para asegurarnos de que detecta datos erróneos.
        // ¿Qué esperamos? Que lance IllegalArgumentException.
        try {
            c.setGeneros(Arrays.asList("INVALID"));
            fail("Debería haber lanzado IllegalArgumentException al usar un género inválido");
        } catch (IllegalArgumentException e) {
            // Si entra aquí, está bien: el error fue lanzado.
        }
    }

    @Test
    public void testToStringMuestraTitulo() {
        // ¿Qué probamos? Que al llamar toString() aparezca el título.
        // ¿Por qué? Para facilitar la depuración y el logging.
        // ¿Qué esperamos? Que la cadena resultante incluya el título que le ponemos.
        c.setTitulo("MiCancion");
        String text = c.toString();
        assertTrue(
                "toString() debe contener 'MiCancion'",
                text.contains("MiCancion")
        );
    }
}
