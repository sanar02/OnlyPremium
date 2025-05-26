package ies.sequeros.dam.aplicacion.vehiculos.revisiones;

import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Revision;
import ies.sequeros.dam.dominio.Vehiculo;

import java.util.Optional;

public class NuevaRevisionCasoUso {
    private IVehiculoRepositorio vehiculoRepositorio;
    public NuevaRevisionCasoUso(IVehiculoRepositorio vehiculoRepositorio) {
        this.vehiculoRepositorio = vehiculoRepositorio;
    }
    public void ejecutar(String matricula, Revision revision) throws NoSuchFieldException {
       Optional<Vehiculo> vehiculo= vehiculoRepositorio.findByMatricula(matricula);
       if(vehiculo.isPresent()) {
           vehiculo.get().addRevision(revision);
           vehiculoRepositorio.update(vehiculo.get());

       }
        else
            throw new NoSuchFieldException(" No existe un vehiculo con esa matricual");
    }
}
