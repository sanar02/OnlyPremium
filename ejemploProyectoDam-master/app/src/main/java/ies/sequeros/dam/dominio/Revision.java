package ies.sequeros.dam.dominio;

import java.time.LocalDate;
import java.util.Date;

/**
 * no tiene id ya que se identifica por
 * el vehículo al que pertenece y la fecha
 */
/**
 * Clase que representa una revisión de un vehículo.
 * Contiene información sobre la fecha, kilómetros, comentario y el ID del mecánico.
 */
public class Revision {
    private LocalDate fecha;
    private int kilometros;
    private String comentario;
    private int mecanicoId;
    public Revision() {
        this.fecha = LocalDate.now();
        this.kilometros = 0;
        this.comentario = "";
        this.mecanicoId = -1;
    }
    public Revision(int kilometros, String comentario, int mecanicoId) {
        this.fecha = LocalDate.now();
        if(kilometros < 0)
            throw  new IllegalArgumentException("Kilometros no pueden ser negativo");
        if(mecanicoId < 0)
            throw  new IllegalArgumentException("Mecanico id no pueden ser negativo");
        this.kilometros = kilometros;
        this.comentario = comentario;
        this.mecanicoId = mecanicoId;

    }
    public Revision(LocalDate fecha,int kilometros, String comentario, int mecanicoId) {

        if(kilometros < 0)
            throw  new IllegalArgumentException("Kilometros no pueden ser negativo");
        if(mecanicoId < 0)
            throw  new IllegalArgumentException("Mecanico id no pueden ser negativo");
        this.kilometros = kilometros;
        this.comentario = comentario;
        this.mecanicoId = mecanicoId;
        this.fecha = fecha;

    }
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        if(fecha == null){
            throw  new IllegalArgumentException("Fecha no puede ser nulo");
        }
        this.fecha = fecha;
    }

    public int getKilometros() {
        return kilometros;
    }

    public void setKilometros(int kilometros) {
        if(kilometros < 0)
            throw new IllegalArgumentException("Kilometros no pueden ser negativo");
        this.kilometros = kilometros;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getMecanicoId() {
        return mecanicoId;
    }

    public void setMecanicoId(int mecanicoId) {
        if(mecanicoId < 0)
            throw  new IllegalArgumentException("Mecanico id no pueden ser negativo");

        this.mecanicoId = mecanicoId;
    }

}
