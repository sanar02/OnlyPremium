package ies.sequeros.dam.aplicacion.mecanicos;

import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;
import ies.sequeros.dam.infraestructura.MecanicoRepositorioEnFichero;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BorrarMecanicoCasoUsoTest {


    @BeforeAll
    static void setUp() {
        try {
            Files.deleteIfExists(Path.of("mecanicostest.json"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        IMecanicoRepositorio repositorio= new MecanicoRepositorioEnFichero("mecanicostest.json");
        NuevoMecanicoCasoUso nuevo = new NuevoMecanicoCasoUso(repositorio);
        Mecanico m= new Mecanico();
        m.setActivo(true);
        m.setNombre("Mecanico");
        m.setDescripcion("Test");
        m.setEmail("Correo@es");

        try {
            nuevo.ejecutar(m);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        m= new Mecanico();
        m.setActivo(true);
        m.setNombre("Mecanico2");
        m.setDescripcion("Test");
        m.setEmail("Correo@es");

        try {
            nuevo.ejecutar(m);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
         m= new Mecanico();
        m.setActivo(true);
        m.setNombre("Mecanico3");
        m.setDescripcion("Test");
        m.setEmail("Correo@es");

        try {
            nuevo.ejecutar(m);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    void borrarExistente() {
        IMecanicoRepositorio repositorio= new MecanicoRepositorioEnFichero("mecanicostest.json");
        BorrarMecanicoCasoUso borrar = new BorrarMecanicoCasoUso(repositorio);
        Mecanico m= new Mecanico();
        m.setActivo(true);
        m.setNombre("Mecanico");
        m.setDescripcion("Test");
        m.setEmail("Correo@es");
        m.setId(1);

        try {
            borrar.ejecutar(m);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        ObtenerMecanicoCasoUso obtenerMecanicoCasoUso = new ObtenerMecanicoCasoUso(repositorio);
        List<Mecanico> mecanicos= obtenerMecanicoCasoUso.ejecutar();
        assertEquals(2, mecanicos.size());


    }

    @Test
    void borrarNoExistente() {
        IMecanicoRepositorio repositorio= new MecanicoRepositorioEnFichero("mecanicostest.json");
        BorrarMecanicoCasoUso borrar = new BorrarMecanicoCasoUso(repositorio);
        Mecanico m= new Mecanico();
        m.setActivo(true);
        m.setNombre("MecanicoFalso");
        m.setDescripcion("Test");
        m.setEmail("Correo@es");
        m.setId(10);

        assertThrows(NoSuchFieldException.class, () -> borrar.ejecutar(m));




    }
}