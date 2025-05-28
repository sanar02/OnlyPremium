package es.burgueses.infraestructura;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Updates;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.Voto;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class CancionMongo implements ICancionesRepositorio {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Cancion> collection;

    public CancionMongo () {
        ConnectionString connectionString = new ConnectionString("mongodb://10.2.1.191:27017");

        CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(codecRegistry)
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("burgueses");
        this.collection = database.getCollection("canciones", Cancion.class);
    }

    @Override
    public void add(Cancion cancion) {
        Cancion existingCancion = collection.find(Filters.eq("titulo", cancion.getTitulo())).first();
        if (existingCancion != null) {
            collection.replaceOne(Filters.eq("titulo", cancion.getTitulo()), cancion, new ReplaceOptions().upsert(true));
        } else {
            collection.insertOne(cancion);
        }
    }

    @Override
    public void remove(Cancion cancion) {
        collection.deleteOne(Filters.eq("titulo", cancion.getTitulo()));
    }

    @Override
    public Cancion findByTitulo(String titulo) {
        return collection.find(Filters.eq("titulo", titulo)).first();
    }

    @Override
    public List<Cancion> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public void update(Cancion cancion) {
        collection.replaceOne(Filters.eq("titulo", cancion.getTitulo()), cancion, new ReplaceOptions().upsert(false));
    }

    @Override
    public void addVotoMeGusta(String titulo, Voto voto) {
        collection.updateOne(
            Filters.eq("titulo", titulo),
            Updates.push("meGusta", voto)
        );
    }

    @Override
    public void addVotoNoMeGusta(String titulo, Voto voto) {
        collection.updateOne(
            Filters.eq("titulo", titulo),
            Updates.push("noMeGusta", voto)
        );
    }

    @Override
    public List<Voto> getVotosMeGusta(String titulo) {
        Cancion cancion = findByTitulo(titulo);
        if (cancion != null && cancion.getMeGusta() != null) {
            return cancion.getMeGusta();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Voto> getVotosNoMeGusta(String titulo) {
        Cancion cancion = findByTitulo(titulo);
        if (cancion != null && cancion.getNoMeGusta() != null) {
            return cancion.getNoMeGusta();
        }
        return new ArrayList<>();
    }
}