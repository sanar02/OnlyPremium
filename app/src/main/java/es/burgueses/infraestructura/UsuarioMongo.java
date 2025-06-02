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
    // MongoDB client, database, and collection for Usuario objects
    private final MongoClient cliente;
    private final MongoDatabase database;
    private final MongoCollection<Usuario> coleccion;

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

    @Override
    public void add(Usuario usuario) {
        // Add a new user if the nickname does not exist
        if (coleccion.find(Filters.eq("apodo", usuario.getApodo())).first() != null) {
            throw new IllegalArgumentException("El usuario ya existe");
        } else {
            coleccion.insertOne(usuario);
        }
    }

    @Override
    public void remove(Usuario usuario) {
        // Remove a user if the nickname exists
        if (coleccion.find(Filters.eq("apodo", usuario.getApodo())).first() == null) {
            throw new IllegalArgumentException("El usuario no existe");
        } else {
            coleccion.deleteOne(Filters.eq("apodo", usuario.getApodo()));
        }
    }

    @Override
    public Usuario findByApodo(String apodo) {
        // Find a user by nickname
        return coleccion.find(Filters.eq("apodo", apodo)).first();
    }

    @Override
    public List<Usuario> findAll() {
        // Get all users from the collection
        return coleccion.find().into(new ArrayList<>());
    }

    @Override
    public void update(Usuario usuario) {
        // Update a user by nickname (replace the document)
        coleccion.replaceOne(
            Filters.eq("apodo", usuario.getApodo()),
            usuario,
            new ReplaceOptions().upsert(false)
        );
    }

    @Override
    public Usuario get(String apodo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
}
