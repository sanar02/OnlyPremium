package ies.sequeros.dam.aplicacion.mecanicos;

import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;

import java.util.List;

/**
 * Caso de uso para obtener todos los mecánicos.
 * Este caso de uso se encarga de recuperar una lista de todos los mecánicos del repositorio.
 */
 //

public class ObtenerMecanicoCasoUso {
    private IMecanicoRepositorio mecanicoRepositorio;
    public ObtenerMecanicoCasoUso(IMecanicoRepositorio mecanicoRepositorio) {
        this.mecanicoRepositorio = mecanicoRepositorio;
    }
    public List<Mecanico> ejecutar()  {
        return this.mecanicoRepositorio.findAll();
    }
}
