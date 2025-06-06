package es.burgueses.infraestructura;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;
import es.burgueses.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListaReproduccionRepositorioEnMemoria implements IListaReproduccionRepositorio {

    private final List<ListaReproduccion> listasReproduccion;
    private final CancionRepositorioEnMemoria cancionRepositorio; // Referencia al repositorio de canciones

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
    public void remove(ListaReproduccion lista) {
        Usuario usuarioActual = lista.getPropietario();
        if (usuarioActual == null) {
            throw new IllegalStateException("No hay usuario autenticado");
        }
        if (!usuarioActual.getTipoUsuario().equals(Usuario.TipoUsuario.ADMINISTRADOR)
                && !usuarioActual.getApodo().equals(lista.getPropietario().getApodo())) {
            throw new IllegalStateException("No puedes eliminar una lista que no es tuya");
        }
        listasReproduccion.remove(lista);
    }

    @Override
    public ListaReproduccion findById(UUID idLista) {
        return listasReproduccion.stream()
                .filter(lista -> lista.getIdLista().equals(idLista.toString()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public ListaReproduccion findByTitulo(String titulo) {
        return listasReproduccion.stream()
                .filter(lista -> lista.getNombre().equals(titulo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Cancion> getCanciones(String tituloLista) {
        return List.of();
    }

    @Override
    public void addCancion(String tituloLista, Cancion cancion) {

    }

    @Override
    public void removeCancion(String tituloLista, Cancion cancion) {

    }

    @Override
    public void modifyList(String tituloLista, String nuevoTitulo, String nuevaDescripcion, String descripcion) {

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
    public void addCancion(UUID idLista, UUID idCancion) {
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
    public void removeCancion(UUID idLista, UUID idCancion) {

        ListaReproduccion lista = findById(idLista);
        if (lista != null) {
            lista.getCanciones().removeIf(c -> c.getId().equals(idCancion.toString()));
        } else {
            throw new IllegalArgumentException("Lista de reproducción no encontrada: " +
                    idLista);
        }

    }

    @Override
    public void modifyList(UUID idLista, String nuevoTitulo, String nuevaDescripcion, String descripcion) {
        ListaReproduccion lista = findById(idLista);
        if (lista != null) {
            lista.setTitulo(nuevoTitulo);
            lista.setDescripcion(nuevaDescripcion);
        }
    }

    @Override
    public List<UUID> getCanciones(UUID idLista) {
        ListaReproduccion lista = findById(idLista);
        List<UUID> ids = new ArrayList<>();
        if (lista != null) {
            for (Cancion c : lista.getCanciones()) {
                ids.add(c.getId());
            }

        }
        return ids;
    }
}