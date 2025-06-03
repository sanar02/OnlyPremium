package es.burgueses.dominio;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class VotoTest {

    private Voto v;
    private final LocalDate HOY = LocalDate.now();

    @Before
    public void setUp() {
        // Creamos un Voto limpio antes de cada test
        v = new Voto();
    }

    // --- constructor por defecto ----------------------------------------

    @Test
    public void defaultConstructor_initialValues() {
        // ¿Qué? Instanciamos con new Voto().
        // ¿Por qué? Verificar valores por defecto.
        // ¿Esperamos?
        //   usuario == "" (cadena vacía)
        //   fecha == hoy
        //   tipo == true (me gusta por defecto)
        assertEquals("usuario por defecto debe ser cadena vacía", "", v.getUsuario());
        assertEquals("fecha por defecto debe ser hoy", HOY, v.getFecha());
        // No hay getter para tipo, asumiendo que true por defecto (no testeable aquí)
    }

    // --- constructor parametrizado -------------------------------------

    @Test
    public void paramConstructor_usesUsuarioAndResetsFechaYTipo() {
        // ¿Qué? Creamos con parámetros pero el código ignora fecha y tipo.
        // ¿Por qué? Detectar bug: siempre pone fecha=now, tipo=true.
        Voto vx = new Voto("userX", LocalDate.of(2000,1,1), false);
        assertEquals("Constructor debe asignar usuario", "userX", vx.getUsuario());
        assertEquals("Constructor parametrizado ignora 'fecha' y debe usar hoy", HOY, vx.getFecha());
        // tipo siempre true por defecto (no testeable sin getter)
    }

    // --- usuario --------------------------------------------------------

    @Test
    public void testSetUsuarioNullThrows() {
        // ¿Qué? Llamar a setUsuario(null)
        // ¿Por qué? Un voto siempre debe referir un usuario válido
        boolean thrown = false;
        try {
            v.setUsuario(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setUsuario(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetUsuarioBlankThrows() {
        // ¿Qué? Llamar a setUsuario con solo espacios
        // ¿Por qué? El nombre de usuario no puede estar en blanco
        boolean thrown = false;
        try {
            v.setUsuario("   ");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setUsuario(\"   \") debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetUsuarioTrimmedValid() {
        // ¿Qué? Llamar a setUsuario con espacios en extremos
        // ¿Por qué? El setter debe recortar espacios exteriores
        v.setUsuario("  pedro123  ");
        assertEquals("Debe recortar espacios en los extremos", "pedro123", v.getUsuario());
    }

    @Test
    public void testSetUsuarioInternalMultipleSpacesThrows() {
        // ¿Qué? Llamar a setUsuario con múltiples espacios internos
        // ¿Por qué? No se permiten más de un espacio seguido en el identificador
        boolean thrown = false;
        try {
            v.setUsuario("pedro   123");
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setUsuario(\"pedro   123\") debe lanzar IllegalArgumentException", thrown);
    }

    // --- fecha ----------------------------------------------------------

    @Test
    public void testSetFechaNullThrows() {
        // ¿Qué? Llamar a setFecha(null)
        // ¿Por qué? Un voto debe tener fecha válida
        boolean thrown = false;
        try {
            v.setFecha(null);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setFecha(null) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetFechaFutureThrows() {
        // ¿Qué? Llamar a setFecha con fecha futura
        // ¿Por qué? No tiene sentido un voto fechado en el futuro
        boolean thrown = false;
        try {
            v.setFecha(HOY.plusDays(1));
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue("setFecha(fecha futura) debe lanzar IllegalArgumentException", thrown);
    }

    @Test
    public void testSetFechaValid() {
        // ¿Qué? Llamar a setFecha con fecha pasada o hoy
        // ¿Por qué? Debe aceptarse fechas válidas
        LocalDate pasada = HOY.minusDays(5);
        v.setFecha(pasada);
        assertEquals("setFecha debe aceptar fechas pasadas", pasada, v.getFecha());
    }
}