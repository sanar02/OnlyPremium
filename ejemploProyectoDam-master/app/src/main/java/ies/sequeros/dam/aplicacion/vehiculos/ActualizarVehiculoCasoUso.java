package ies.sequeros.dam.aplicacion.vehiculos;

import ies.sequeros.dam.dominio.IAlmacenImagenVehiculo;
import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Vehiculo;

import java.util.Optional;

public class ActualizarVehiculoCasoUso {
    private IVehiculoRepositorio vehiculoRepositorio;
    private IAlmacenImagenVehiculo almacenImagenVehiculo;
    public ActualizarVehiculoCasoUso(IVehiculoRepositorio vehiculoRepositorio, IAlmacenImagenVehiculo almacenImagenVehiculo) {
        this.vehiculoRepositorio = vehiculoRepositorio;
        this.almacenImagenVehiculo = almacenImagenVehiculo;
    }
    public void ejecutar(Vehiculo item) throws NoSuchFieldException {
        Optional<Vehiculo> original=vehiculoRepositorio.findByMatricula(item.getMatricula());
        //cambiar la imagen si es diferente
        if(original.isPresent() && !original.get().getPathimagen().equals(item.getPathimagen())){
            String nuevoPath=almacenImagenVehiculo.replace(item.getMatricula(),item.getPathimagen(),original.get().getPathimagen());
            item.setPathimagen(nuevoPath);
        }
        this.vehiculoRepositorio.update(item);
    }
}
