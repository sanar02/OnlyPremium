package ies.sequeros.dam.aplicacion.mecanicos;

import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;

import java.io.IOException;

/**
 * Caso de uso para actualizar un mecánico.
 * Este caso de uso se encarga de actualizar la información de un mecánico en el repositorio.
 */
// Se utiliza el patrón de diseño Caso de Uso para encapsular la lógica de negocio relacionada con la actualización de mecánicos.
// Este patrón ayuda a mantener el código organizado y facilita la reutilización de la lógica de negocio en diferentes partes de la aplicación.
// Además, se sigue el principio de inversión de dependencias, donde la clase depende de una interfaz (IMecanicoRepositorio) en lugar de una implementación concreta.SS

public class ActualizarMecanicoCasoUso {
    private IMecanicoRepositorio mecanicoRepositorio;
    public ActualizarMecanicoCasoUso(IMecanicoRepositorio mecanicoRepositorio) {
        this.mecanicoRepositorio = mecanicoRepositorio;
    }
    public void ejecutar(Mecanico item) throws NoSuchFieldException, IOException {
        this.mecanicoRepositorio.update(item);
    }
}
