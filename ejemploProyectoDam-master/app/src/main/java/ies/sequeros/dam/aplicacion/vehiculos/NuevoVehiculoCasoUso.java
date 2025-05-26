package ies.sequeros.dam.aplicacion.vehiculos;

import ies.sequeros.dam.dominio.*;

/**
 * Caso de uso para añadir un nuevo vehículo.
 * Este caso de uso se encarga de añadir un nuevo vehículo al repositorio y almacenar su imagen.
 */
public class NuevoVehiculoCasoUso {
    private IVehiculoRepositorio repositorio;
    private IAlmacenImagenVehiculo almacen;
    public NuevoVehiculoCasoUso(IVehiculoRepositorio repositorio, IAlmacenImagenVehiculo almacen) {
        this.repositorio= repositorio;
        this.almacen = almacen;
    }
    public void ejecutar(Vehiculo item) throws NoSuchFieldException {
        this.repositorio.add(item);
        //se alacena la imagen
        String nuevoPath=this.almacen.save(item.getMatricula(),item.getPathimagen());
        item.setPathimagen(nuevoPath);
        try {
            this.repositorio.update(item);
        } catch (NoSuchFieldException e) {
            //si da fallo se borra el fichero
            this.almacen.delete(nuevoPath);
            throw new NoSuchFieldException(e.getMessage());
        }
    }
}
