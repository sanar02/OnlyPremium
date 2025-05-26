package ies.sequeros.dam.dominio;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio de vehículos.
 * Define las operaciones básicas para gestionar vehículos en el sistema.
 */
public interface IVehiculoRepositorio {
    public void add(Vehiculo vehiculo);
    public Optional<Vehiculo> findById(String id);
    public void remove(Vehiculo vehiculo) throws NoSuchFieldException;
    public void update(Vehiculo vehiculo) throws NoSuchFieldException;
    public List<Vehiculo> findAll();
    public Optional<Vehiculo> findByMatricula(String matricula);
}
