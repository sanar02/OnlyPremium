package es.burgueses.infraestructura;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import es.burgueses.dominio.IUsuarioRepositorio;
import es.burgueses.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class UsuarioMongo implements IUsuarioRepositorio {
    // Cliente de MongoDB, base de datos y colección de objetos Usuario
    private final MongoClient cliente;
    private final MongoDatabase database;
    private final MongoCollection<Usuario> coleccion;

    public UsuarioMongo() {
        String url = "mongodb://app:123456789Aa@10.2.1.191:27017/OnlyPremium"; // Copia la cadena de Compass aquí
        ConnectionString connectionString = new ConnectionString(url);

        // Configura el registro de codecs para mapear POJOs automáticamente
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        // Configura los ajustes del cliente de MongoDB
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(pojoCodecRegistry)
                .build();

        // Crea el cliente de MongoDB y obtiene la base de datos y la colección
        cliente = MongoClients.create(settings);
        database = cliente.getDatabase("OnlyPremium");
        coleccion = database.getCollection("Usuario", Usuario.class);
    }

    @Override
    public void add(Usuario usuario) {
        // Añade un nuevo usuario si el apodo no existe
        if (coleccion.find(Filters.eq("apodo", usuario.getApodo())).first() != null) {
            throw new IllegalArgumentException("El usuario ya existe");
        } else {
            System.out.println("Usuario añadido: " + usuario.getApodo());
            coleccion.insertOne(usuario);
        }
    }

    @Override
    public void remove(Usuario usuario) {
        // Elimina un usuario si el apodo existe
        if (coleccion.find(Filters.eq("apodo", usuario.getApodo())).first() == null) {
            throw new IllegalArgumentException("El usuario no existe");
        } else {
            coleccion.deleteOne(Filters.eq("apodo", usuario.getApodo()));
        }
    }

    @Override
    public Usuario findByApodo(String apodo) {
        // Busca un usuario por su apodo
        return coleccion.find(Filters.eq("apodo", apodo)).first();
    }

    @Override
    public List<Usuario> findAll() {
        // Obtiene todos los usuarios de la colección
        return coleccion.find().into(new ArrayList<>());
    }

    @Override
    public void update(Usuario usuario) {
        // Actualiza un usuario por apodo (reemplaza el documento)
        coleccion.replaceOne(
            Filters.eq("apodo", usuario.getApodo()),
            usuario,
            new ReplaceOptions().upsert(false)
        );
    }

    @Override
    public Usuario get(String apodo) {
        // Método no implementado
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

    @Override
    public void modificarUsuario(String apodo, String nuevoNombre, String nuevaContrasena) {
        Usuario usuario = coleccion.find(Filters.eq("apodo", apodo)).first();
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ese apodo");
        }
        usuario.setNombre(nuevoNombre);
        usuario.setContraseña(nuevaContrasena);
        coleccion.replaceOne(Filters.eq("apodo", apodo), usuario, new ReplaceOptions().upsert(false));
    }
}
