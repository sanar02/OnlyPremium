package ies.sequeros.dam.aplicacion.vehiculos;

import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Vehiculo;

import java.util.List;
/**
 * Caso de uso para obtener todos los vehículos.
 * Este caso de uso se encarga de recuperar una lista de todos los vehículos del repositorio.
 */
public class ObtenerVehiculosCasoUso {
    private IVehiculoRepositorio vehiculoRepositorio;
    public ObtenerVehiculosCasoUso(IVehiculoRepositorio vehiculoRepositorio) {
        this.vehiculoRepositorio = vehiculoRepositorio;
    }
    public List<Vehiculo> ejecutar()  {
        return this.vehiculoRepositorio.findAll();
    }
}
