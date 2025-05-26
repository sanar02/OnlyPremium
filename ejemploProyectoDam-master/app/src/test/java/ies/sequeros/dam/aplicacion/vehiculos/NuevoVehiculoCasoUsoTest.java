package ies.sequeros.dam.aplicacion.vehiculos;

import ies.sequeros.dam.aplicacion.vehiculos.NuevoVehiculoCasoUso;
import ies.sequeros.dam.dominio.IAlmacenImagenVehiculo;
import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Vehiculo;
import ies.sequeros.dam.infraestructura.AlmacenImagenVehiculoEnLocal;
import ies.sequeros.dam.infraestructura.VehiculoRepositorioEnFichero;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NuevoVehiculoCasoUsoTest {

    @BeforeAll
    static void setUp() {
        try {
            Files.deleteIfExists(Path.of("vehiculotest.json"));
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
    }

    @Test
    @Order(1)
    void nuevoCorrecto() {
        IVehiculoRepositorio repositorio= new VehiculoRepositorioEnFichero("vehiculostest.json");
        IAlmacenImagenVehiculo almacenImagenVehiculo= new AlmacenImagenVehiculoEnLocal();
        NuevoVehiculoCasoUso nuevo = new NuevoVehiculoCasoUso(repositorio,almacenImagenVehiculo);
        Vehiculo m= new Vehiculo();
        m.setMarca("marca");
        m.setModelo("modelo");
        m.setMatricula("1234ABC");
        m.setColor("Red");
        m.setProximaRevision(10000);
        m.setPeriodoRevision(5000);
        m.setPathimagen("C:/batmovil.jpg");

        try {
            nuevo.ejecutar(m);
            assertEquals("1234ABC", m.getMatricula());
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    void nuevoDuplicado() {
        IVehiculoRepositorio repositorio= new VehiculoRepositorioEnFichero("vehiculostest.json");
        IAlmacenImagenVehiculo almacenImagenVehiculo= new AlmacenImagenVehiculoEnLocal();
        NuevoVehiculoCasoUso nuevo = new NuevoVehiculoCasoUso(repositorio,almacenImagenVehiculo);
        Vehiculo m= new Vehiculo();
        m.setMarca("marca");
        m.setModelo("modelo");
        m.setMatricula("1234ABC");
        m.setColor("Red");
        m.setProximaRevision(10000);
        m.setPeriodoRevision(5000);
        m.setPathimagen("C:/ecto1.jpg");
        assertThrows(IllegalStateException.class, () -> {
            nuevo.ejecutar(m);
        });
    }
}