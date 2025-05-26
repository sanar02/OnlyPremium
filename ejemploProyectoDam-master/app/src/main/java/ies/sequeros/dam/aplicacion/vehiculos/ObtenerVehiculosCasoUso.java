package ies.sequeros.dam.aplicacion.vehiculos;

import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Vehiculo;

import java.util.List;

public class ObtenerVehiculosCasoUso {
    private IVehiculoRepositorio vehiculoRepositorio;
    public ObtenerVehiculosCasoUso(IVehiculoRepositorio vehiculoRepositorio) {
        this.vehiculoRepositorio = vehiculoRepositorio;
    }
    public List<Vehiculo> ejecutar()  {
        return this.vehiculoRepositorio.findAll();
    }
}
