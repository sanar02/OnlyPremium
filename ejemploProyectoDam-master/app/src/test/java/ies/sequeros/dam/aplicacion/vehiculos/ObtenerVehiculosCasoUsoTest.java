package ies.sequeros.dam.aplicacion.vehiculos;

import ies.sequeros.dam.aplicacion.vehiculos.NuevoVehiculoCasoUso;

import ies.sequeros.dam.dominio.IAlmacenImagenVehiculo;
import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Vehiculo;
import ies.sequeros.dam.infraestructura.AlmacenImagenVehiculoEnLocal;
import ies.sequeros.dam.infraestructura.VehiculoRepositorioEnFichero;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ObtenerVehiculosCasoUsoTest {

    @BeforeEach
    void setUp() {

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
    void ejecutar() {
        IVehiculoRepositorio repositorio= new VehiculoRepositorioEnFichero("vehiculotest.json");
        IAlmacenImagenVehiculo almacen= new AlmacenImagenVehiculoEnLocal();
        NuevoVehiculoCasoUso nuevoVehiculoCasoUso= new NuevoVehiculoCasoUso(repositorio,almacen);
        ObtenerVehiculosCasoUso obtenerVehiculoCasoUso= new ObtenerVehiculosCasoUso(repositorio);
        Vehiculo m= new Vehiculo();
        m.setMarca("marca");
        m.setModelo("modelo");
        m.setMatricula("1234ABC");
        m.setColor("Red");
        m.setProximaRevision(10000);
        m.setPeriodoRevision(5000);
        m.setPathimagen("C:/batmovil.jpg");

        try {
            nuevoVehiculoCasoUso.ejecutar(m);
            List<Vehiculo> items=obtenerVehiculoCasoUso.ejecutar();
            assertTrue(items.size()==1);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}