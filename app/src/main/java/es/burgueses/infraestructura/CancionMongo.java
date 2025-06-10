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
import java.util.UUID;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class CancionMongo implements ICancionesRepositorio {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Cancion> collection;

    public CancionMongo() {
        String uri = "mongodb://app:123456789Aa@10.2.1.191:27017/OnlyPremium";
        ConnectionString connectionString = new ConnectionString(uri);

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(pojoCodecRegistry)
                .build();

        this.mongoClient = MongoClients.create(settings);
        this.database = mongoClient.getDatabase("OnlyPremium");
        this.collection = database.getCollection("Cancion", Cancion.class);
    }

    @Override
    public void add(Cancion cancion) {
        Cancion existingCancion = collection.find(Filters.eq("titulo", cancion.getTitulo())).first();
        if (existingCancion != null) {
            collection.replaceOne(Filters.eq("titulo", cancion.getTitulo()), cancion,
                    new ReplaceOptions().upsert(true));
        } else {
            collection.insertOne(cancion);
        }
    }

    @Override
    public void remove(Cancion cancion) {
        collection.deleteOne(Filters.eq("_id", cancion.getId()));
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
        collection.replaceOne(Filters.eq("_id", cancion.getId()), cancion, new ReplaceOptions().upsert(false));
    }

    @Override
    public void addVotoMeGusta(String id, Voto voto) {
        collection.updateOne(
                Filters.eq("_id", id),
                Updates.push("meGusta", voto));
    }

    @Override
    public void addVotoNoMeGusta(String id, Voto voto) {
        collection.updateOne(
                Filters.eq("_id", id),
                Updates.push("noMeGusta", voto));
    }

    @Override
    public List<Voto> getVotosMeGusta(String id) {
        Cancion cancion = findById(id);
        if (cancion != null && cancion.getMeGusta() != null) {
            return cancion.getMeGusta();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Voto> getVotosNoMeGusta(String id) {
        Cancion cancion = findById(id);
        if (cancion != null && cancion.getNoMeGusta() != null) {
            return cancion.getNoMeGusta();
        }
        return new ArrayList<>();
    }

    public Cancion findById(String id) {
        return collection.find(Filters.eq("_id", id)).first();
    }

    @Override
    public Cancion findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public String replace(String string, UUID id, String path) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}