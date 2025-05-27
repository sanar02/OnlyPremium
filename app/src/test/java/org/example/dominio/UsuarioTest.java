package es.burgueses.aplicacion.dominio;

import org.junit.Before;
import org.junit.Test;

import javax.swing.text.html.ImageView;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class UsuarioTest {

    private Usuario u;
    private final LocalDate HOY = LocalDate.now();

    @Before
    public void setUp() {
        // Antes de cada prueba, creamos un nuevo Usuario usando el constructor por defecto
        u = new Usuario();
    }

    // [1] Constructor por defecto
    @Test
    public void defaultConstructor() {
        // ¿Qué hacemos? Comprobamos los valores iniciales inmediatamente tras crear el objeto.
        // ¿Por qué? Para asegurarnos de que el objeto arranca en un estado consistente.
        // ¿Qué esperamos?
        //   - nombre vacío ("")
        //   - apodo vacío ("")
        //   - pathImagen vacío ("")
        //   - activo == false
        //   - fechaAlta == null
        //   - tipoUsuario == USUARIO (por defecto)
        assertEquals("nombre por defecto", "", u.getNombre());
        assertEquals("apodo por defecto",   "", u.getApodo());
        assertEquals("pathImagen por defecto", "", u.getPathImagen());
        assertFalse("activo por defecto",    u.isActivo());
        assertNull("fechaAlta por defecto",  u.getFechaAlta());
        assertEquals("tipoUsuario por defecto",
                Usuario.TipoUsuario.USUARIO, u.getTipoUsuario());
    }

    // [2] Setters / getters básicos
    @Test
    public void settersAndGettersValid() {
        // ¿Qué hacemos? Asignamos valores válidos a cada campo usando los setters,
        //             y luego leemos esos mismos valores con los getters.
        // ¿Por qué? Para verificar que cada setter modifica correctamente el estado interno
        //           y que cada getter devuelve el valor esperado.
        // ¿Qué esperamos? Que getX() devuelva exactamente lo que establecimos con setX().

        u.setNombre("A");
        assertEquals("getNombre debe devolver 'A'", "A", u.getNombre());

        u.setApodo("B");
        assertEquals("getApodo debe devolver 'B'", "B", u.getApodo());

        u.setPathImagen("/img/ruta.png");
        assertEquals("getPathImagen debe devolver '/img/ruta.png'",
                "/img/ruta.png", u.getPathImagen());

        u.setActivo(true);
        assertTrue("isActivo debe devolver true tras setActivo(true)", u.isActivo());

        u.setFechaAlta(HOY);
        assertEquals("getFechaAlta debe devolver la fecha asignada",
                HOY, u.getFechaAlta());

        u.setTipoUsuario(Usuario.TipoUsuario.ADMINISTRADOR);
        assertEquals("getTipoUsuario debe devolver ADMINISTRADOR",
                Usuario.TipoUsuario.ADMINISTRADOR, u.getTipoUsuario());
    }

    // [3] toString incluye todos los campos clave
    @Test
    public void toStringContainsAll() {
        // ¿Qué hacemos? Poblamos todos los campos y luego llamamos a toString().
        // ¿Por qué? Para asegurarnos de que la representación textual incluye
        //           cada campo importante para facilitar debugging/logging.
        // ¿Qué esperamos? Que la cadena contenga subcadenas con los valores asignados.

        u.setNombre("Alice");
        u.setApodo("Ali");
        u.setPathImagen("/img/a.png");
        u.setActivo(true);
        u.setFechaAlta(HOY);
        u.setTipoUsuario(Usuario.TipoUsuario.ADMINISTRADOR);

        String s = u.toString();
        assertTrue("toString debe contener nombre='Alice'", s.contains("nombre='Alice'"));
        assertTrue("toString debe contener apodo='Ali'",     s.contains("apodo='Ali'"));
        assertTrue("toString debe contener imagen=/img/a.png", s.contains("imagen=/img/a.png"));
        assertTrue("toString debe contener activo=true",      s.contains("activo=true"));
        assertTrue("toString debe contener fechaAlta=" + HOY,  s.contains("fechaAlta=" + HOY));
        assertTrue("toString debe contener tipoUsuario=ADMINISTRADOR",
                s.contains("tipoUsuario=ADMINISTRADOR"));
    }

    // [4] setNombre(null) → IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void setNombreNullThrows() {
        // ¿Qué hacemos? Llamamos a setNombre(null).
        // ¿Por qué? Para validar que no se permita un nombre null.
        // ¿Qué esperamos? Que lance IllegalArgumentException.
        u.setNombre(null);
    }

    // [5] setApodo(null) → IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void setApodoNullThrows() {
        // ¿Qué hacemos? Llamamos a setApodo(null).
        // ¿Por qué? Para validar que no se permita un apodo null.
        // ¿Qué esperamos? Que lance IllegalArgumentException.
        u.setApodo(null);
    }

    // [6] setPathImagen(null) → IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void setPathImagenNullThrows() {
        // ¿Qué hacemos? Llamamos a setPathImagen(null).
        // ¿Por qué? Para validar que no se permita una ruta de imagen null.
        // ¿Qué esperamos? Que lance IllegalArgumentException.
        u.setPathImagen(null);
    }

    // [7] setFechaAlta(null) → IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void setFechaAltaNullThrows() {
        // ¿Qué hacemos? Llamamos a setFechaAlta(null).
        // ¿Por qué? Para validar que no se permita una fecha de alta null.
        // ¿Qué esperamos? Que lance IllegalArgumentException.
        u.setFechaAlta(null);
    }

    // [8] setTipoUsuario(null) → IllegalArgumentException
    @Test(expected = IllegalArgumentException.class)
    public void setTipoUsuarioNullThrows() {
        // ¿Qué hacemos? Llamamos a setTipoUsuario(null).
        // ¿Por qué? Para validar que no se permita un tipo de usuario null.
        // ¿Qué esperamos? Que lance IllegalArgumentException.
        u.setTipoUsuario(null);
    }

    // [9] Constructor parametrizado no debe dejar pathImagen en null
    @Test
    public void paramConstructorPathImagenNotNull() {
        // ¿Qué hacemos? Creamos un Usuario con el constructor que recibe ImageView (aquí pasamos null).
        // ¿Por qué? Para comprobar que, incluso si el parámetro es null,
        //           el campo pathImagen se inicializa a un valor no nulo.
        // ¿Qué esperamos? Que getPathImagen() no devuelva null.
        ImageView vacio = null;
        Usuario p = new Usuario(
                "N", "P", vacio,
                true, HOY,
                Usuario.TipoUsuario.ADMINISTRADOR
        );
        assertNotNull(
                "Después del constructor parametrizado, pathImagen NO debe ser null",
                p.getPathImagen()
        );
    }
}
