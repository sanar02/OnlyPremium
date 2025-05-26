package ies.sequeros.dam.aplicacion.mecanicos;

import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;

import java.util.List;

public class ObtenerMecanicoCasoUso {
    private IMecanicoRepositorio mecanicoRepositorio;
    public ObtenerMecanicoCasoUso(IMecanicoRepositorio mecanicoRepositorio) {
        this.mecanicoRepositorio = mecanicoRepositorio;
    }
    public List<Mecanico> ejecutar()  {
        return this.mecanicoRepositorio.findAll();
    }
}
