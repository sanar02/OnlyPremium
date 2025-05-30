package es.burgueses.dominio.infraestructura;

import es.burgueses.dominio.*;
import es.burgueses.infraestructura.UsuarioMongo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

public class IUsuarioMongoTest {

    private UsuarioMongo usuarioMongo;

    @BeforeEach
    void setUp() {
        usuarioMongo = new UsuarioMongo();
        // Limpia usuarios de pruebas antes de cada test
        for (String apodo : List.of("juanito", "duanitod", "ruanitod", "tuanito")) {
            Usuario existente = usuarioMongo.findByApodo(apodo);
            if (existente != null) {
                usuarioMongo.remove(existente);
            }
        }
    }

    @AfterEach
    void tearDown() {
        // Limpia usuarios de pruebas después de cada test
        for (String apodo : List.of("juanito", "duanitod", "ruanitod", "tuanito")) {
            Usuario existente = usuarioMongo.findByApodo(apodo);
            if (existente != null) {
                usuarioMongo.remove(existente);
            }
        }
    }

    @Test
    void testAdd() {
       Usuario usuario = new Usuario(
            "clave123",
            "Pedro",
            "pedrito",
            "ruta/pedro.png",
            true,
            LocalDate.now(),
            Usuario.TipoUsuario.USUARIO
        );
        usuarioMongo.add(usuario);
        Usuario encontrado = usuarioMongo.findByApodo("anita");
        assertNotNull(encontrado);
        assertEquals("Ana", encontrado.getNombre());
    }

    @Test
    void testFindByApodo() {
        Usuario usuario = new Usuario(
            "clave123",
            "Luis",
            "luisito",
            "ruta/luis.png",
            true,
            LocalDate.now(),
            Usuario.TipoUsuario.USUARIO
        );
        usuarioMongo.add(usuario);
        Usuario encontrado = usuarioMongo.findByApodo("luisito");
        assertNotNull(encontrado);
        assertEquals("Luis", encontrado.getNombre());
    }

    @Test
    void testFindAll() {
        Usuario usuario1 = new Usuario(
            "clave1",
            "Mario",
            "mariito",
            "ruta/mario.png",
            true,
            LocalDate.now(),
            Usuario.TipoUsuario.USUARIO
        );
        Usuario usuario2 = new Usuario(
            "clave2",
            "Sara",
            "sarita",
            "ruta/sara.png",
            true,
            LocalDate.now(),
            Usuario.TipoUsuario.USUARIO
        );
        usuarioMongo.add(usuario1);
        usuarioMongo.add(usuario2);

        List<Usuario> usuarios = usuarioMongo.findAll();
        assertTrue(usuarios.stream().anyMatch(u -> "mariito".equals(u.getApodo())));
        assertTrue(usuarios.stream().anyMatch(u -> "sarita".equals(u.getApodo())));
    }

    @Test
    void testRemove() {
        Usuario usuario = new Usuario(
            "clave123",
            "Pepe",
            "pepeito",
            "ruta/pepe.png",
            true,
            LocalDate.now(),
            Usuario.TipoUsuario.USUARIO
        );
        usuarioMongo.add(usuario);
        usuarioMongo.remove(usuario);
        Usuario encontrado = usuarioMongo.findByApodo("pepeito");
        assertNull(encontrado);
    }

    @Test
    void testRemoveNoExistente() {
        Usuario usuario = new Usuario(
            "clave123",
            "NoExiste",
            "noexiste",
            "ruta/noexiste.png",
            true,
            LocalDate.now(),
            Usuario.TipoUsuario.USUARIO
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioMongo.remove(usuario);
        });
        assertEquals("El usuario no existe", exception.getMessage());
    }

    @Test
    void testAddDuplicado() {
        Usuario usuario = new Usuario(
            "clave123",
            "Dupi",
            "dupitest",
            "ruta/dupi.png",
            true,
            LocalDate.now(),
            Usuario.TipoUsuario.USUARIO
        );
        usuarioMongo.add(usuario);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioMongo.add(usuario);
        });
        assertEquals("El usuario ya existe", exception.getMessage());
    }

    @Test
    void testModificarUsuario() {
        Usuario usuario = new Usuario(
            "clave123",
            "Pedro",
            "pedrito",
            "ruta/pedro.png",
            true,
            LocalDate.now(),
            Usuario.TipoUsuario.USUARIO
        );
        usuarioMongo.add(usuario);
        usuarioMongo.modificarUsuario("pedrito", "PedroMod", "nuevaClave");
        Usuario modificado = usuarioMongo.findByApodo("pedrito");
        assertNotNull(modificado);
        assertEquals("PedroMod", modificado.getNombre());
        assertEquals("nuevaClave", modificado.getContraseña());
    }

    @Test
    void testModificarUsuarioNoExistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioMongo.modificarUsuario("noexiste", "Pedro", "nuevaClave");
        });
        assertEquals("No existe un usuario con ese apodo", exception.getMessage());
    }
}