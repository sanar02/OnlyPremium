package es.burgueses.infraestructura;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;
import es.burgueses.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ListaReproduccionRepositorioEnMemoria implements IListaReproduccionRepositorio {

    private final List<ListaReproduccion> listasReproduccion;
    private final CancionRepositorioEnMemoria cancionRepositorio;

    public ListaReproduccionRepositorioEnMemoria(CancionRepositorioEnMemoria cancionRepositorio) {
        this.listasReproduccion = new ArrayList<>();
        this.cancionRepositorio = cancionRepositorio;
    }

    @Override
    public void add(ListaReproduccion lista) {
        boolean exists = listasReproduccion.stream()
                .anyMatch(l -> l.getNombre().equals(lista.getNombre()));
        if (exists) {
            throw new IllegalStateException("Ya existe una lista con ese nombre");
        }
        listasReproduccion.add(lista);
    }

    @Override
    public void remove(ListaReproduccion lista, Usuario usuarioActual) {
        if (lista.getPropietario() == null) {
            throw new IllegalArgumentException("La lista no tiene propietario");
        }
        if (usuarioActual == null) {
            throw new IllegalStateException("No hay usuario autenticado");
        }
        boolean esAdmin = usuarioActual.getTipoUsuario().equals(Usuario.TipoUsuario.ADMINISTRADOR);
        boolean esPropietario = usuarioActual.getApodo().equals(lista.getPropietario().getApodo());
        if (!esAdmin && !esPropietario) {
            throw new IllegalStateException("No puedes eliminar una lista que no es tuya");
        }
        listasReproduccion.remove(lista);
    }

    @Override
    public ListaReproduccion findById(String idLista) {
        return listasReproduccion.stream()
                .filter(lista -> lista.getIdLista().equals(idLista))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<ListaReproduccion> findAll() {
        return new ArrayList<>(listasReproduccion);
    }

    @Override
    public void update(ListaReproduccion listaReproduccion) {
        for (int i = 0; i < listasReproduccion.size(); i++) {
            if (listasReproduccion.get(i).getIdLista().equals(listaReproduccion.getIdLista())) {
                listasReproduccion.set(i, listaReproduccion);
                return;
            }
        }
    }

    @Override
    public void addCancion(String idLista, String idCancion) {
        ListaReproduccion lista = findById(idLista);
        if (lista != null) {
            Cancion cancion = cancionRepositorio.findById(idCancion);
            if (cancion != null) {
                lista.getCanciones().add(cancion);
            } else {
                throw new IllegalArgumentException("Canción no encontrada: " + idCancion);
            }
        } else {
            throw new IllegalArgumentException("Lista de reproducción no encontrada: " + idLista);
        }
    }

    @Override
    public void removeCancion(String idLista, String idCancion) {
        ListaReproduccion lista = findById(idLista);
        if (lista != null) {
            lista.getCanciones().removeIf(c -> c.getIdCancion().equals(idCancion));
        } else {
            throw new IllegalArgumentException("Lista de reproducción no encontrada: " + idLista);
        }
    }

    @Override
    public ListaReproduccion findByTitulo(String tituloLista) {
        return listasReproduccion.stream()
                .filter(lista -> lista.getTitulo().equals(tituloLista))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cancion> getCanciones(String tituloLista) {
        ListaReproduccion lista = findByTitulo(tituloLista);
        return lista != null ? new ArrayList<>(lista.getCanciones()) : new ArrayList<>();
    }

    @Override
    public void addCancion(String tituloLista, Cancion cancion) {
        ListaReproduccion lista = findByTitulo(tituloLista);
        if (lista != null) {
            lista.getCanciones().add(cancion);
        }
    }

    @Override
    public void removeCancion(String tituloLista, Cancion cancion) {
        ListaReproduccion lista = findByTitulo(tituloLista);
        if (lista != null) {
            lista.getCanciones().removeIf(c -> c.getIdCancion().equals(cancion.getIdCancion()));
        }
    }

    @Override
    public void modifyList(String tituloLista, String nuevoTitulo, String nuevaDescripcion, String descripcion) {
        ListaReproduccion lista = findByTitulo(tituloLista);
        if (lista != null) {
            lista.setTitulo(nuevoTitulo);
            lista.setDescripcion(nuevaDescripcion);
        }
    }

    @Override
    public void deleteAll() {
        listasReproduccion.clear();
    }
}