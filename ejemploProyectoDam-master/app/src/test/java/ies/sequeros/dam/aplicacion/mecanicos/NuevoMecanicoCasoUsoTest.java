package ies.sequeros.dam.aplicacion.mecanicos;

import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;
import ies.sequeros.dam.infraestructura.MecanicoRepositorioEnFichero;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NuevoMecanicoCasoUsoTest {

    @BeforeAll
    static void setUp() {
        try {
            Files.deleteIfExists(Path.of("mecanicostest.json"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(1)
    void nuevoCorrector() {
        IMecanicoRepositorio repositorio= new MecanicoRepositorioEnFichero("mecanicostest.json");
        NuevoMecanicoCasoUso nuevo = new NuevoMecanicoCasoUso(repositorio);
        Mecanico m= new Mecanico();
        m.setActivo(true);
        m.setNombre("Mecanico");
        m.setDescripcion("Test");
        m.setEmail("Correo@es");
        try {
            nuevo.ejecutar(m);
            assertEquals(1, m.getId());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void nuevoDuplicado() {
        IMecanicoRepositorio repositorio= new MecanicoRepositorioEnFichero("mecanicostest.json");
        NuevoMecanicoCasoUso nuevo = new NuevoMecanicoCasoUso(repositorio);
        Mecanico m= new Mecanico();
        m.setActivo(true);
        m.setNombre("Mecanico");
        m.setDescripcion("Test");
        m.setEmail("Correo@es");
        assertThrows(IllegalStateException.class, () -> {
            nuevo.ejecutar(m);
        });
    }
}