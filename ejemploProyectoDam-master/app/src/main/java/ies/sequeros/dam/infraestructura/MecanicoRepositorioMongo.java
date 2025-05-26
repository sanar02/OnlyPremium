package ies.sequeros.dam.infraestructura;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.Updates;
import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;

import org.bson.Document;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.bson.Document;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/**
 * Implementación del repositorio de mecánicos utilizando MongoDB.
 * Esta clase maneja la persistencia de los mecánicos en una base de datos MongoDB.
 * Proporciona métodos para agregar, buscar, eliminar y actualizar mecánicos.
 * Utiliza el patrón de repositorio para abstraer la lógica de acceso a datos.
 * Se utiliza la biblioteca MongoDB Java Driver para interactuar con la base de datos.
 * La clase también maneja la configuración de la conexión a la base de datos, incluyendo credenciales y dirección del servidor.
 * Se define un codec personalizado para convertir entre objetos Java (POJOs) y documentos BSON de MongoDB.
 * Esta implementación permite una fácil integración con la base de datos MongoDB y proporciona una interfaz clara para la gestión de mecánicos.
 * Se espera que esta clase sea utilizada en el contexto de una aplicación que requiera la gestión de mecánicos, como un sistema de gestión de flotas o un taller mecánico.
 * Esta implementación sigue el patrón de diseño de repositorio, que es una práctica común en aplicaciones que utilizan bases de datos NoSQL como MongoDB.
 * La clase MecanicoRepositorioMongo es responsable de la interacción con la base de datos MongoDB para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre los objetos Mecanico.*/

    public class MecanicoRepositorioMongo implements IMecanicoRepositorio {
        private MongoClient cliente;
        private MongoDatabase database;
        private final MongoCollection<Mecanico> coleccion;

        public MecanicoRepositorioMongo() {
            // Configura credenciales y dirección
            String usuario = "admin";
            String contrasena = "adminpassword";
            String baseDatos = "flota";
            String host = "127.0.0.1";
            int puerto = 27017;
            //se crea la conexion
            //mongodb://localhost:27017/
            String uri = String.format("mongodb://%s:%s@%s:%d/",usuario, contrasena, host, puerto);
            ConnectionString connectionString = new ConnectionString(uri);//"mongodb://admin:adminpassword@localhost:27017/");
           //definir cómo convertir entre objetos Java (POJOs) y documentos BSON de MongoDB
            CodecRegistry pojoCodecRegistry = fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build())
            );
            //se configura la conexión
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .codecRegistry(pojoCodecRegistry)
                    .build();

            cliente = MongoClients.create(settings);
            database = cliente.getDatabase(baseDatos);
            coleccion = database.getCollection("mecanicos", Mecanico.class);

        }
        @Override
        public void add(Mecanico value) throws IOException {
            //se ha de establecer un sistema para hacer el autoincremento
            value.setId(value.hashCode());
           coleccion.insertOne(value);
        }

        @Override
        public Mecanico findById(int id) {
           return coleccion.find(Filters.eq("id", id)).first();
        }

        @Override
        public void remove(Mecanico item) throws NoSuchFieldException, IOException {
                coleccion.deleteOne(Filters.eq("_id", item.getId()));
             }

        @Override
        public void update(Mecanico item) throws NoSuchFieldException, IOException {
            Bson filtro = Filters.eq("_id", item.getId());
            Bson actualizacion = Updates.combine(
                    Updates.set("descripcion", item.getDescripcion()),
                    Updates.set("email",item.getEmail()),
                    Updates.set("activo",item.isActivo()),
                    Updates.set("nombre",item.getNombre())
            );
            coleccion.findOneAndUpdate(
                    filtro,
                    actualizacion,
                    new FindOneAndUpdateOptions()
            );
        }
        @Override
        public List<Mecanico> findAll() {
           return  coleccion.find().into(new ArrayList<Mecanico>());
        }
    }
