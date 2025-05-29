package es.burgueses.aplicacion.cancion.listaReproduccion;

import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;
import es.burgueses.dominio.Usuario;


public class ModListUserCase {
    private IListaReproduccionRepositorio listaReproduccionRepositorio;

    public ModListUserCase(IListaReproduccionRepositorio listaReproduccionRepositorio) {
        this.listaReproduccionRepositorio = listaReproduccionRepositorio;
    }


    public void modList(Usuario usuarioActual, String tituloLista, String nuevoTitulo, String nuevaDescripcion, String descripcion) {
        if(!usuarioActual.getApodo().equals(listaReproduccionRepositorio.findByTitulo(tituloLista).getPropietario().getApodo()) || usuarioActual.getTipoUsuario() != Usuario.TipoUsuario.ADMINISTRADOR) {
            throw new IllegalArgumentException("No tienes permiso para modificar esta lista de reproducción");
        }

        if (tituloLista == null || tituloLista.isEmpty()) {
            throw new IllegalArgumentException("El título de la lista de reproducción no puede ser nulo o vacío");
        }
        if (nuevoTitulo == null || nuevoTitulo.isEmpty()) {
            throw new IllegalArgumentException("El nuevo título de la lista de reproducción no puede ser nulo o vacío");
        }

        ListaReproduccion lista = listaReproduccionRepositorio.findByTitulo(tituloLista);
        if (lista == null) {
            throw new IllegalArgumentException("La lista de reproducción no existe");
        }

        if (nuevaDescripcion != null && !nuevaDescripcion.isEmpty()) {
            lista.setDescripcion(nuevaDescripcion);
        } else {
            lista.setDescripcion(descripcion);
        }

        lista.setTitulo(nuevoTitulo);
        listaReproduccionRepositorio.update(lista);
    }
}
