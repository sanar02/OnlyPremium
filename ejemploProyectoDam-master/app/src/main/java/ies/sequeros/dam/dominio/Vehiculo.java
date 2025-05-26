package ies.sequeros.dam.dominio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vehiculo {
    private String marca;
    private String modelo;
    private String matricula;
    private String color;
    private int proximaRevision;
    private int periodoRevision;
    private List<Revision> revisiones;
    private String pathimagen;
    public Vehiculo() {
        revisiones = new ArrayList<Revision>();
        marca="";
        modelo="";
        matricula="";
        color="";
        proximaRevision=0;
        periodoRevision=0;
        pathimagen="";
    }
    public Vehiculo(String marca, String modelo, String matricula, String color,String pathimagen) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.color = color;
        this.proximaRevision = 0;
        this.periodoRevision = 0;
        this.pathimagen="";
        this.revisiones=new ArrayList<>();
    }
    public void addRevision(Revision revision) {
        revisiones.add(revision);
    }
    public void removeRevision(Revision revision) {
        revisiones.remove(revision);
    }
    public void removeRevision(Date fecha){
        revisiones.removeIf( revision -> revision.getFecha().equals(fecha) );
    }
    public void updateRevison(Revision revision) {

    }
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getProximaRevision() {
        return proximaRevision;
    }

    public void setProximaRevision(int proximaRevision) {
        this.proximaRevision = proximaRevision;
    }

    public int getPeriodoRevision() {
        return periodoRevision;
    }

    public void setPeriodoRevision(int periodoRevision) {
        this.periodoRevision = periodoRevision;
    }

    public String getPathimagen() {
        return pathimagen;
    }

    public void setPathimagen(String pathimagen) {
        this.pathimagen = pathimagen;
    }

    /**
     * se devuelve una copia, las revisiones solo
     * se pueden modificar desde el vehículo
     * @return
     */
    public List<Revision> getRevisiones() {
        return List.copyOf(revisiones);
    }

    public void setRevisiones(List<Revision> revisiones) {
        this.revisiones = revisiones;
    }
}
