package ies.sequeros.dam.aplicacion.mecanicos;

import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;
import ies.sequeros.dam.infraestructura.MecanicoRepositorioEnFichero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ObtenerMecanicoCasoUsoTest {

    @BeforeEach
    void setUp() {
        try {
            Files.deleteIfExists(Path.of("mecanicostest.json"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    @Test
    void ejecutar() {
        IMecanicoRepositorio repositorio= new MecanicoRepositorioEnFichero("mecanicotest.json");
        NuevoMecanicoCasoUso nuevoMecanicoCasoUso= new NuevoMecanicoCasoUso(repositorio);
        ObtenerMecanicoCasoUso obtenerMecanicoCasoUso= new ObtenerMecanicoCasoUso(repositorio);
        Mecanico m= new Mecanico();
        m.setNombre("paco");
        m.setEmail("paco@.es");
        m.setDescripcion("dfsfdsf");
        m.setActivo(true);
        try {
            nuevoMecanicoCasoUso.ejecutar(m);
            List<Mecanico> items=obtenerMecanicoCasoUso.ejecutar();
            assertTrue(items.size()==1);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}