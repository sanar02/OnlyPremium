package ies.sequeros.dam.aplicacion.vehiculos;

import ies.sequeros.dam.dominio.IAlmacenImagenVehiculo;
import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Vehiculo;

/**
 * Caso de uso para borrar un vehículo.
 * Este caso de uso se encarga de eliminar un vehículo del repositorio y borrar su imagen asociada.
 */

public class BorrarVehiculoCasoUso {
    private IVehiculoRepositorio vehiculoRepositorio;
    private IAlmacenImagenVehiculo almacenImagenVehiculo;
    public BorrarVehiculoCasoUso(IVehiculoRepositorio vehiculoRepositorio, IAlmacenImagenVehiculo almacenImagenVehiculo) {
        this.vehiculoRepositorio = vehiculoRepositorio;
        this.almacenImagenVehiculo = almacenImagenVehiculo;
    }
    public void ejecutar(Vehiculo item) throws NoSuchFieldException {
        this.almacenImagenVehiculo.delete(item.getPathimagen());
        this.vehiculoRepositorio.remove(item);
    }
}
