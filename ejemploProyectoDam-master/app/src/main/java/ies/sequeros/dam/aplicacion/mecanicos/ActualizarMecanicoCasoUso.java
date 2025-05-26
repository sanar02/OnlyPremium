package ies.sequeros.dam.aplicacion.mecanicos;

import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;

import java.io.IOException;

public class ActualizarMecanicoCasoUso {
    private IMecanicoRepositorio mecanicoRepositorio;
    public ActualizarMecanicoCasoUso(IMecanicoRepositorio mecanicoRepositorio) {
        this.mecanicoRepositorio = mecanicoRepositorio;
    }
    public void ejecutar(Mecanico item) throws NoSuchFieldException, IOException {
        this.mecanicoRepositorio.update(item);
    }
}
