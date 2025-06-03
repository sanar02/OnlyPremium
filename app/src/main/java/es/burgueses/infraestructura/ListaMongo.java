package es.burgueses.infraestructura;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.IListaReproduccionRepositorio;
import es.burgueses.dominio.ListaReproduccion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ListaMongo implements IListaReproduccionRepositorio  {

    private final MongoClient cliente;
    private final MongoDatabase database;
    private final MongoCollection<ListaReproduccion> coleccion;

    public ListaMongo() {
        // Conexión directa usando la URI proporcionada
        String url = "mongodb://app:123456789Aa@10.2.1.191:27017/OnlyPremium";
        ConnectionString connectionString = new ConnectionString(url);

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(pojoCodecRegistry)
                .build();

        cliente = MongoClients.create(settings);
        database = cliente.getDatabase("OnlyPremium");
        coleccion = database.getCollection("Lista", ListaReproduccion.class);
        }

//        String usuario = "app";
//        String contrasena = "1234568789Aa";
//        String baseDatos = "OnlyPremiun";
//        String host = "10.1.2.191";
//        int puerto = 27017;
//
//        String uri = String.format("mongodb://%s:%s@%s:%d/", usuario, contrasena, host, puerto);
//        ConnectionString connectionString = new ConnectionString(uri);
//
//        CodecRegistry pojoCodecRegistry = fromRegistries(
//                MongoClientSettings.getDefaultCodecRegistry(),
//                fromProviders(PojoCodecProvider.builder().automatic(true).build())
//        );

//        MongoClientSettings settings = MongoClientSettings.builder()
//                .applyConnectionString(connectionString)
//                .codecRegistry(pojoCodecRegistry)
//                .build();
//
//        cliente = MongoClients.create(settings);
//        database = cliente.getDatabase(baseDatos);
//        coleccion = database.getCollection("listas", ListaReproduccion.class);
   // }

    @Override
    public void add(ListaReproduccion listaReproduccion) {
        if (coleccion.find(Filters.eq("titulo", listaReproduccion.getTitulo())).first() != null) {
            throw new IllegalArgumentException("La lista ya existe");
        }
        coleccion.insertOne(listaReproduccion);
    }

    @Override
    public void remove(ListaReproduccion listaReproduccion) {
        coleccion.deleteOne(Filters.eq("titulo", listaReproduccion.getTitulo()));
    }

    @Override
    public ListaReproduccion findById(UUID idLista) {
        return null;
    }

    @Override
    public List<ListaReproduccion> findAll() {
        return coleccion.find().into(new ArrayList<>());
    }

    @Override
    public void update(ListaReproduccion listaReproduccion) {
        coleccion.replaceOne(
            Filters.eq("titulo", listaReproduccion.getTitulo()),
            listaReproduccion,
            new ReplaceOptions().upsert(false)
        );
    }

    @Override
    public void addCancion(UUID idLista, UUID idCancion) {
        ListaReproduccion lista = findById(idLista);
        if (lista != null) {
            lista.getCanciones().add(cancion);
            update(lista);
        }
    }

    @Override
    public void removeCancion(UUID idLista, UUID idCancion) {
        ListaReproduccion lista = findByTitulo(tituloLista);
        if (lista != null) {
            lista.getCanciones().removeIf(c -> c.getTitulo().equals(cancion.getTitulo()));
            update(lista);
        }
    }

    @Override
    public void modifyList(UUID idLista, String nuevoTitulo, String nuevaDescripcion, String descripcion) {
        ListaReproduccion lista = findByTitulo(tituloLista);
        if (lista != null) {
            return lista.getCanciones();
        }
        return new ArrayList<>();

    }

    @Override
    public List<UUID> getCanciones(UUID idLista) {
        return List.of();
    }

}