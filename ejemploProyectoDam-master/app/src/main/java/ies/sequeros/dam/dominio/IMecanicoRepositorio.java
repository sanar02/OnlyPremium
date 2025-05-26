package ies.sequeros.dam.dominio;

import java.io.IOException;
import java.util.List;

/**
 * Interfaz del repositorio de mecánicos.
 * Define las operaciones básicas para gestionar mecánicos en el sistema.
 */

public interface IMecanicoRepositorio {
    public void add(Mecanico mecanico) throws NoSuchFieldException, IOException;
    public Mecanico findById(int id);
    public void remove(Mecanico mecanico) throws NoSuchFieldException, IOException;
    public void update(Mecanico mecanico) throws NoSuchFieldException, IOException;
    public List<Mecanico> findAll();
}
