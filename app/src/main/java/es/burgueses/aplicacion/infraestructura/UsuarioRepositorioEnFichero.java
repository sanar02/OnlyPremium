package es.burgueses.aplicacion.infraestructura;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.burgueses.aplicacion.dominio.IUsuarioRepositorio;
import es.burgueses.aplicacion.dominio.Usuario;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class UsuarioRepositorioEnFichero implements IUsuarioRepositorio {

    private final ObjectMapper mapper; // Utilizado para serializar y deserializar objetos a JSON
    private final File archivo; // Fichero donde se almacenan los usuarios
    private final Map<String, Usuario> usuarios; // Mapa para almacenar usuarios por su apodo


    /**
     * Constructor que inicializa el repositorio de usuarios desde un fichero.
     * @param rutaArchivo Ruta del fichero donde se almacenan los usuarios.
     */
    public UsuarioRepositorioEnFichero(String rutaArchivo) {
        this.archivo = new File(rutaArchivo);
        this.mapper = new ObjectMapper();
        this.usuarios = new HashMap<>();
        cargar();
    }

    /**
     * Carga los usuarios desde el fichero al inicio.
     * Si el fichero no existe, se crea uno nuevo.
     */
    private void cargar() {
        if (archivo.exists()) {
            try {
                List<Usuario> lista = mapper.readValue(archivo, new TypeReference<List<Usuario>>() {});
                lista.forEach(u -> usuarios.put(u.getApodo(), u));
            } catch (IOException e) {
                throw new RuntimeException("Error al cargar usuarios", e);
            }
        }
    }

    /**
     * Guarda los usuarios en el fichero.
     * Se utiliza para persistir los cambios realizados en el repositorio.
     */
    private void guardar() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, usuarios.values());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar usuarios", e);
        }
    }


    @Override 
    public void add(Usuario usuario) { // Añade un nuevo usuario al repositorio
        if (usuarios.containsKey(usuario.getApodo())) {
            throw new IllegalStateException("El usuario ya existe");
        }
        usuarios.put(usuario.getApodo(), usuario); // Añade el usuario al mapa
        guardar(); // Guarda los cambios en el fichero
    }


    @Override
    public void remove(Usuario usuario) { // Elimina un usuario del repositorio
        if (usuarios.remove(usuario.getApodo()) == null) {
            throw new NoSuchElementException("Usuario no encontrado");
        }
        guardar(); // Guarda los cambios después de eliminar el usuario
    }

    @Override
    public Usuario findByApodo(String apodo) { // Busca un usuario por su apodo
        return usuarios.get(apodo); // Devuelve el usuario si existe, o null si no se encuentra
    }

    @Override
    public List<Usuario> findAll() { // Devuelve una lista con todos los usuarios del repositorio
        return new ArrayList<>(usuarios.values()); // Convierte el mapa de usuarios a una lista
    }

    @Override
    public void update(Usuario usuario) { // Actualiza un usuario existente en el repositorio
        if (!usuarios.containsKey(usuario.getApodo())) { // Comprueba si el usuario existe
            throw new NoSuchElementException("Usuario no encontrado"); // Lanza una excepción si no se encuentra el usuario
        }
        usuarios.put(usuario.getApodo(), usuario); // Actualiza el usuario en el mapa
        guardar(); // Guarda los cambios en el fichero
    }
}
