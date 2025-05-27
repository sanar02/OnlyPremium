package es.burgueses.aplicacion.dominio;

import org.junit.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class CancionTest {
    // datos de ejemplo para el constructor parametrizado
    private final String TITULO     = "Cancion";
    private final List<String> GEN_VALID   = Arrays.asList("ROCK", "POP");
    private final List<String> GEN_INVALID = Arrays.asList("INVALID");
    private final String PATH       = "/ruta/file.mp3";
    private final Usuario USUARIO   = new Usuario();
    private final String AUTOR      = "Autor";
    private final String DESC       = "Descripcion";
    private final LocalDate HOY     = LocalDate.now();
    private final boolean PUB       = true;
    private final int REPS          = 7;
    private final List<String> MG   = Arrays.asList("MG");
    private final List<String> NMG  = Arrays.asList("NOME");

    // — Tests existentes (positivos) — //

    @Test
    public void defaultTitleEmpty() {
        // Comprueba que el título por defecto es cadena vacía
        Cancion c = new Cancion();
        assertEquals("Titulo por defecto vacio", "", c.getTitulo());
    }

    @Test
    public void defaultPathEmpty() {
        // Comprueba que el path por defecto es cadena vacía
        Cancion c = new Cancion();
        assertEquals("Path por defecto vacio", "", c.getPath());
    }

    @Test
    public void defaultPublicaFalse() {
        // Comprueba que isPublica() es false al crear la canción
        Cancion c = new Cancion();
        assertFalse("Por defecto la canción no debe ser pública", c.isPublica());
    }

    @Test
    public void defaultNumeroReproduccionesCero() {
        // Comprueba que el contador de reproducciones inicia en 0
        Cancion c = new Cancion();
        assertEquals("Reproducciones por defecto en 0", 0, c.getNumeroReproducciones());
    }

    @Test
    public void defaultGenerosEmpty() {
        // Comprueba que la lista de géneros existe y comienza vacía
        Cancion c = new Cancion();
        assertNotNull("Lista de generos no debe ser null", c.getGeneros());
        assertTrue("Lista de generos por defecto vacía", c.getGeneros().isEmpty());
    }

    @Test
    public void defaultFechaAltaIsToday() {
        // Comprueba que la fecha de alta inicial es la fecha de ejecución
        Cancion c = new Cancion();
        assertEquals("FechaAlta por defecto deber ser hoy", HOY, c.getFechaAlta());
    }

    // — Tests del constructor parametrizado (positivos) — //

    @Test
    public void paramConstructorSetsTitulo() {
        // Verifica que el constructor asigna el título correctamente
        Cancion c = new Cancion(TITULO, GEN_VALID, PATH, USUARIO, AUTOR, DESC,
                HOY, PUB, REPS, MG, NMG);
        assertEquals("Constructor debe asignar el título", TITULO, c.getTitulo());
    }

    @Test
    public void paramConstructorValidGeneros() {
        // Verifica la conversión de géneros válidos
        Cancion c = new Cancion(TITULO, GEN_VALID, PATH, USUARIO, AUTOR, DESC,
                HOY, PUB, REPS, MG, NMG);
        List<String> out = c.getGeneros();
        assertEquals("Debe tener 2 géneros", 2, out.size());
        assertTrue("Debe contener ROCK", out.contains("ROCK"));
        assertTrue("Debe contener POP",  out.contains("POP"));
    }

    @Test
    public void paramConstructorSetsPath() {
        // Verifica que el constructor asigna la ruta correctamente
        Cancion c = new Cancion(TITULO, GEN_VALID, PATH, USUARIO, AUTOR, DESC,
                HOY, PUB, REPS, MG, NMG);
        assertEquals("Constructor debe asignar path", PATH, c.getPath());
    }

    @Test
    public void paramConstructorSetsAutorDescripcion() {
        // Verifica la asignación de autor y descripción
        Cancion c = new Cancion(TITULO, GEN_VALID, PATH, USUARIO, AUTOR, DESC,
                HOY, PUB, REPS, MG, NMG);
        assertEquals("Constructor debe asignar autor",       AUTOR, c.getAutor());
        assertEquals("Constructor debe asignar descripción", DESC,  c.getDescripcion());
    }

    @Test
    public void paramConstructorSetsFechaAltaAndPublicaAndReproducciones() {
        // Verifica fechaAlta, flag publica y reproducciones
        Cancion c = new Cancion(TITULO, GEN_VALID, PATH, USUARIO, AUTOR, DESC,
                HOY, PUB, REPS, MG, NMG);
        assertEquals("FechaAlta asignada mal", HOY,   c.getFechaAlta());
        assertTrue("Flag pública mal asignada",       c.isPublica());
        assertEquals("Reproducciones asignadas mal", REPS, c.getNumeroReproducciones());
    }

    // — Tests que DEBEN DAR ERROR (casos conflictivos) — //

    @Test(expected = IllegalArgumentException.class)
    public void testSetTituloNullThrows() {
        // ¿Qué? Llamar a setTitulo(null)
        // ¿Por qué? No se debe permitir título null
        // ¿Qué esperamos? IllegalArgumentException
        Cancion c = new Cancion();
        c.setTitulo(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetPathNullThrows() {
        // ¿Qué? Llamar a setPath(null)
        // ¿Por qué? No se debe permitir ruta null
        // ¿Qué esperamos? IllegalArgumentException
        Cancion c = new Cancion();
        c.setPath(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetAutorNullThrows() {
        // ¿Qué? Llamar a setAutor(null)
        // ¿Por qué? El autor no puede ser null
        // ¿Qué esperamos? IllegalArgumentException
        Cancion c = new Cancion();
        c.setAutor(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetDescripcionNullThrows() {
        // ¿Qué? Llamar a setDescripcion(null)
        // ¿Por qué? La descripción no puede ser null
        // ¿Qué esperamos? IllegalArgumentException
        Cancion c = new Cancion();
        c.setDescripcion(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetFechaAltaNullThrows() {
        // ¿Qué? Llamar a setFechaAlta(null)
        // ¿Por qué? La fecha de alta debe existir
        // ¿Qué esperamos? IllegalArgumentException
        Cancion c = new Cancion();
        c.setFechaAlta(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetReproduccionesNegativeThrows() {
        // ¿Qué? Llamar a setNumeroReproducciones(-1)
        // ¿Por qué? No tiene sentido valor negativo para reproducciones
        // ¿Qué esperamos? IllegalArgumentException
        Cancion c = new Cancion();
        c.setNumeroReproducciones(-1);
    }
}
