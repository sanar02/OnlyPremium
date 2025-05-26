package ies.sequeros.dam.aplicacion.vehiculos;


import ies.sequeros.dam.dominio.IAlmacenImagenVehiculo;
import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Vehiculo;
import ies.sequeros.dam.infraestructura.AlmacenImagenVehiculoEnLocal;
import ies.sequeros.dam.infraestructura.VehiculoRepositorioEnFichero;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BorrarVehiculoCasoUsoTest {


    @BeforeAll
    static void setUp() {
        IVehiculoRepositorio repositorio = new VehiculoRepositorioEnFichero("vehiculostest.json");

        try {
            Files.deleteIfExists(Path.of("vehiculostest.json"));
            //se borran los ficheros
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of("imagenes"))) {
                for (Path archivo : stream) {
                    if (Files.isRegularFile(archivo)) {
                        Files.delete(archivo);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Vehiculo m = new Vehiculo();
        m.setMarca("marca");
        m.setModelo("modelo");
        m.setMatricula("1234ABC");
        m.setColor("Red");
        m.setProximaRevision(10000);
        m.setPeriodoRevision(5000);
        m.setPathimagen("C:/batmovil.jpg");

        repositorio.add(m);

        m = new Vehiculo();
        m.setMarca("marca");
        m.setModelo("modelo");
        m.setMatricula("2222ABC");
        m.setColor("Blue");
        m.setProximaRevision(10000);
        m.setPeriodoRevision(5000);
        m.setPathimagen("C:/ecto1.jpg");

        repositorio.add(m);


    }

    @Test
    void borrarExistente() {
        IVehiculoRepositorio repositorio = new VehiculoRepositorioEnFichero("vehiculostest.json");
        IAlmacenImagenVehiculo almacenImagenVehiculo = new AlmacenImagenVehiculoEnLocal();
        BorrarVehiculoCasoUso borrar = new BorrarVehiculoCasoUso(repositorio, almacenImagenVehiculo);
        Vehiculo m = new Vehiculo();
        m.setMarca("marca");
        m.setModelo("modelo");
        m.setMatricula("1234ABC");
        m.setColor("Red");
        m.setProximaRevision(10000);
        m.setPeriodoRevision(5000);
        m.setPathimagen("imagenes/batmovil.jpg");
        try {
            borrar.ejecutar(m);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        ObtenerVehiculosCasoUso obtenerVehiculoCasoUso = new ObtenerVehiculosCasoUso(repositorio);
        List<Vehiculo> vehiculos = obtenerVehiculoCasoUso.ejecutar();
        assertEquals(1, vehiculos.size());


    }

    @Test
    void borrarNoExistente() {
        IVehiculoRepositorio repositorio = new VehiculoRepositorioEnFichero("vehiculostest.json");
        IAlmacenImagenVehiculo almacenImagenVehiculo = new AlmacenImagenVehiculoEnLocal();
        BorrarVehiculoCasoUso borrar = new BorrarVehiculoCasoUso(repositorio, almacenImagenVehiculo);
        Vehiculo m = new Vehiculo();
        m.setMarca("marca");
        m.setModelo("modelo");
        m.setMatricula("3333ABC");
        m.setColor("Red");
        m.setProximaRevision(10000);
        m.setPeriodoRevision(5000);


        assertThrows(NoSuchFieldException.class, () -> borrar.ejecutar(m));


    }
}