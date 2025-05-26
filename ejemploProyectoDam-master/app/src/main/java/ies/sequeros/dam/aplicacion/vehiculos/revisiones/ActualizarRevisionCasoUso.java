package ies.sequeros.dam.aplicacion.vehiculos.revisiones;

import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Revision;
import ies.sequeros.dam.dominio.Vehiculo;

import java.util.Optional;

public class ActualizarRevisionCasoUso {
    private IVehiculoRepositorio vehiculoRepositorio;
    public ActualizarRevisionCasoUso(IVehiculoRepositorio vehiculoRepositorio) {
        this.vehiculoRepositorio = vehiculoRepositorio;
    }
    public void ejecutar(String matricula, Revision revision) throws NoSuchFieldException {
       Optional<Vehiculo> vehiculo= vehiculoRepositorio.findByMatricula(matricula);
       Optional<Revision> original;
       if(vehiculo.isPresent()) {
           //se obtiene el original
           original=vehiculo.get().getRevisiones().stream().filter(revision1 -> {
               if(revision1.getFecha().equals(revision.getFecha())) {
                   return true;
               }
                return false;
                   }).findFirst();
           //si existe con esa fecha
           if(original.isPresent()) {
               vehiculo.get().removeRevision(original.get());
               vehiculo.get().addRevision(revision);
               vehiculoRepositorio.update(vehiculo.get());
           }else
               throw new NoSuchFieldException(" No existe una revisión con esa fecha");
       }
        else
            throw new NoSuchFieldException(" No existe un vehiculo con esa matricula");
    }
}
