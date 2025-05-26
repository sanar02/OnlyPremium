package ies.sequeros.dam.aplicacion.vehiculos.revisiones;

import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Revision;
import ies.sequeros.dam.dominio.Vehiculo;

import java.util.Optional;

public class BorrarRevisionCasoUso {
    private IVehiculoRepositorio vehiculoRepositorio;
    public BorrarRevisionCasoUso(IVehiculoRepositorio vehiculoRepositorio) {
        this.vehiculoRepositorio = vehiculoRepositorio;
    }
    public void ejecutar(String matricula, Revision revision) throws NoSuchFieldException {
       Optional<Vehiculo> vehiculo= vehiculoRepositorio.findByMatricula(matricula);
       if(vehiculo.isPresent()) {
           if(vehiculo.get().getRevisiones().contains(revision)) {
               vehiculo.get().removeRevision(revision);
               vehiculoRepositorio.update(vehiculo.get());
           }else
               throw new NoSuchFieldException(" No existe la revisión en el vehiculo");

       }
        else
            throw new NoSuchFieldException(" No existe un vehiculo con esa matricual");
    }
}
