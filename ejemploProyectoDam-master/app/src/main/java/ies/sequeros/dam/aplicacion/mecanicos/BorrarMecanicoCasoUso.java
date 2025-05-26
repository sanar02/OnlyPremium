package ies.sequeros.dam.aplicacion.mecanicos;

import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;

import java.io.IOException;

public class BorrarMecanicoCasoUso {
    private IMecanicoRepositorio mecanicoRepositorio;
    public BorrarMecanicoCasoUso(IMecanicoRepositorio mecanicoRepositorio) {
        this.mecanicoRepositorio = mecanicoRepositorio;
    }
    public void ejecutar(Mecanico item) throws NoSuchFieldException, IOException {
        this.mecanicoRepositorio.remove(item);
    }
}
