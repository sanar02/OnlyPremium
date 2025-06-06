package es.burgueses.infraestructura;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import es.burgueses.dominio.IUsuarioRepositorio;
import es.burgueses.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class UsuarioMongo implements IUsuarioRepositorio {
    // MongoDB client, database, and collection for Usuario objects
    private final MongoClient cliente;
    private final MongoDatabase database;
    private final MongoCollection<Usuario> coleccion;

    public UsuarioMongo() {
        // Conexión directa usando la URL proporcionada
        String url = "mongodb://app:123456789Aa@10.2.1.191:27017/OnlyPremium";
        ConnectionString connectionString = new ConnectionString(url);

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(pojoCodecRegistry)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();

        cliente = MongoClients.create(settings);
        database = cliente.getDatabase("OnlyPremium");
        coleccion = database.getCollection("Usuario", Usuario.class);
    }

    /*
    public UsuarioMongo() {
        // MongoDB credentials and connection info
        String usuario = "app";
        String contrasena = "123456789Aa";
        String baseDatos = "OnlyPremiun";
        String host = "10.1.2.191";
        int puerto = 27017;

        // Build the MongoDB URI with authentication
        String uri = String.format("mongodb://%s:%s@%s:%d/", usuario, contrasena, host, puerto);
        ConnectionString connectionString = new ConnectionString(uri);

        // Set up POJO codec registry for automatic mapping
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        // Configure MongoDB client settings
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(pojoCodecRegistry)
                .build();

        // Create the MongoDB client and get the database and collection
        cliente = MongoClients.create(settings);
        database = cliente.getDatabase(baseDatos);
        coleccion = database.getCollection("usuario", Usuario.class);
    }
    */

    @Override
    public void add(Usuario usuario) {
        if (usuario == null) throw new IllegalArgumentException("El usuario no puede ser nulo");
        if (coleccion.find(Filters.eq("_id", usuario.getId())).first() != null) {
            throw new IllegalArgumentException("Ya existe un usuario con ese ID");
        }
        coleccion.insertOne(usuario);
    }

    @Override
    public void remove(Usuario usuario) {
        if (coleccion.find(Filters.eq("_id", usuario.getId())).first() == null) {
            throw new IllegalArgumentException("No existe un usuario con ese ID");
        }
        coleccion.deleteOne(Filters.eq("_id", usuario.getId()));
    }
    
    @Override
    public List<Usuario> findAll() {

        // Get all users from the collection
        return coleccion.find().into(new ArrayList<>());
    }

    @Override
public void update(Usuario usuario) {
    if (usuario == null || usuario.getId() == null) {
        throw new IllegalArgumentException("El usuario o el ID no pueden ser nulos");
    }
    if (coleccion.find(Filters.eq("_id", usuario.getId())).first() == null) {
        throw new IllegalArgumentException("No existe un usuario con ese ID");
    }
    ReplaceOptions options = new ReplaceOptions().upsert(true);
    coleccion.replaceOne(Filters.eq("_id", usuario.getId()), usuario, options);
}

    @Override
    public Usuario findById(String id) {
        Usuario usuario = coleccion.find(Filters.eq("_id", id)).first();
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ese ID");
        }
        return usuario;
    }

    @Override
    public Usuario findByApodo(String apodo) {
        // Validate the apodo input
        if (apodo == null || apodo.isEmpty()) {
            throw new IllegalArgumentException("El apodo no puede ser nulo o vacío");
        }

        // Check if the user exists by apodo
        Usuario usuario = coleccion.find(Filters.eq("apodo", apodo)).first();
        if (usuario == null) {
            throw new IllegalArgumentException("No existe un usuario con ese apodo");
        }

        // Return the user if found
        return usuario;
    }
    
    @Override
    public void deleteAll() {
        coleccion.deleteMany(new org.bson.Document());
    }

}

