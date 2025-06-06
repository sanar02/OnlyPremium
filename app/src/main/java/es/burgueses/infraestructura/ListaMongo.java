package es.burgueses.infraestructura;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.Updates;
import es.burgueses.dominio.*;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class ListaMongo implements IListaReproduccionRepositorio {

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<ListaReproduccion> collection;
    private final ICancionesRepositorio cancionRepositorio = new CancionMongo();

    public ListaMongo() {
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
        this.collection = database.getCollection("Lista", ListaReproduccion.class);
    }

    @Override
    public void add(ListaReproduccion listaReproduccion) {
        ListaReproduccion existing = collection.find(Filters.eq("_id", listaReproduccion.getIdLista())).first();
        if (existing != null) {
            throw new IllegalArgumentException("La lista ya existe");
        }
        collection.insertOne(listaReproduccion);
    }

    @Override
    public void remove(ListaReproduccion lista, Usuario usuarioActual) {
        if (usuarioActual == null) {
            throw new IllegalArgumentException("No hay usuario autenticado");
        }
        if (!usuarioActual.getTipoUsuario().equals(Usuario.TipoUsuario.ADMINISTRADOR)
                && (lista.getPropietario() == null || !usuarioActual.getApodo().equals(lista.getPropietario().getApodo()))) {
            throw new IllegalStateException("No puedes eliminar una lista que no es tuya");
        }
        collection.deleteOne(Filters.eq("_id", lista.getIdLista()));
    }

    @Override
    public ListaReproduccion findById(String idLista) {
        return collection.find(Filters.eq("_id", idLista)).first();
    }

    @Override
    public ListaReproduccion findByTitulo(String titulo) {
        return collection.find(Filters.eq("nombre", titulo)).first();
    }

    @Override
    public List<ListaReproduccion> findAll() {
        return collection.find().into(new ArrayList<>());
    }

    @Override
    public void update(ListaReproduccion listaReproduccion) {
        collection.replaceOne(
                Filters.eq("_id", listaReproduccion.getIdLista()),
                listaReproduccion,
                new ReplaceOptions().upsert(false)
        );
    }

  @Override
  public void addCancion(String idLista, String idCancion) {
      // Buscar la lista de reproducción por su ID
      ListaReproduccion lista = findById(idLista);
      if (lista == null) {
          throw new IllegalArgumentException("Lista de reproducción no encontrada: " + idLista);
      }
      // Buscar la canción por su ID (debes tener acceso a un repositorio de canciones)
      Cancion cancion = cancionRepositorio.findById(idCancion.toString());
       if (cancion == null) {
           throw new IllegalArgumentException("Canción no encontrada: " + idCancion);
       }
       collection.updateOne(Filters.eq("_id", idLista.toString()), Updates.push("canciones", cancion));
      throw new UnsupportedOperationException("Debes implementar la lógica para obtener el objeto Cancion y añadirlo a la lista");
  }

    @Override
    public void removeCancion(String idLista, String idCancion) {
        collection.updateOne(
                Filters.eq("_id", idLista.toString()),
                Updates.pull("canciones", Filters.eq("idCancion", idCancion.toString()))
        );
    }

    // Métodos extra para compatibilidad con la interfaz (por título)
    @Override
    public List<Cancion> getCanciones(String tituloLista) {
        ListaReproduccion lista = findByTitulo(tituloLista);
        if (lista != null) {
            return lista.getCanciones();
        }
        return new ArrayList<>();
    }

    @Override
    public void addCancion(String tituloLista, Cancion cancion) {
        collection.updateOne(
                Filters.eq("nombre", tituloLista),
                Updates.push("canciones", cancion)
        );
    }

    @Override
    public void removeCancion(String tituloLista, Cancion cancion) {
        collection.updateOne(
            Filters.eq("nombre", tituloLista),
            Updates.pull("canciones", new org.bson.Document("titulo", cancion.getTitulo()))
        );
    }

    @Override
    public void modifyList(String tituloLista, String nuevoTitulo, String nuevaDescripcion, String descripcion) {
        collection.updateOne(
                Filters.eq("nombre", tituloLista),
                Updates.combine(
                        Updates.set("nombre", nuevoTitulo),
                        Updates.set("descripcion", nuevaDescripcion)
                )
        );
    }

    @Override
    public void deleteAll() {
        collection.deleteMany(new org.bson.Document());
    }
}