package ies.sequeros.dam.dominio;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;

public class Mecanico {

    private int id;
    private String nombre;

    private String email;

    private boolean activo;

    private String descripcion;
    public Mecanico(){
        id=-1;
        nombre="";
        email="";
        activo=false;
        descripcion="";
    }
    public Mecanico(int id, String nombre, String email, boolean activo, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.activo = activo;
        this.descripcion = descripcion;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public boolean equals(Object obj){
        if(obj instanceof Mecanico){
            Mecanico m = (Mecanico)obj;
            if(m.activo==this.activo && m.descripcion.equals(this.descripcion)
                    && m.nombre.equals(this.nombre) && m.email.equals(this.email)){
                return true;
            }

        }
        return false;
    }
}
