package ies.sequeros.dam.aplicacion.mecanicos;

import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;

import java.io.IOException;

/**
 * Caso de uso para actualizar un mecánico.
 * Este caso de uso se encarga de actualizar la información de un mecánico en el repositorio.
 */
public class BorrarMecanicoCasoUso {
    private IMecanicoRepositorio mecanicoRepositorio;
    public BorrarMecanicoCasoUso(IMecanicoRepositorio mecanicoRepositorio) {
        this.mecanicoRepositorio = mecanicoRepositorio;
    }
    public void ejecutar(Mecanico item) throws NoSuchFieldException, IOException {
        this.mecanicoRepositorio.remove(item);
    }
}
