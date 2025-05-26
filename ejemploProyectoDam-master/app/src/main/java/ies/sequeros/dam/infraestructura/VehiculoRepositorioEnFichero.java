package ies.sequeros.dam.infraestructura;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ies.sequeros.dam.dominio.IVehiculoRepositorio;
import ies.sequeros.dam.dominio.Vehiculo;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class VehiculoRepositorioEnFichero implements IVehiculoRepositorio {
    private final ObjectMapper mapper;
    private final File archivo;
    public VehiculoRepositorioEnFichero(String path) {
        this.archivo = new File(path);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }
    @Override
    public void add(Vehiculo item) {
        var items=this.load();
        //se comprueba que la canción no existe
        if(!items.containsKey(item.getMatricula()) ) {
            items.put(item.getMatricula(),item);
            this.save(items);
        }else{
            throw new IllegalStateException("La matricula ya existe en el sistema");
        }
    }

    @Override
    public Optional<Vehiculo> findById(String id) {
        var items=this.load();
        if(items.containsKey(id)) {
            return Optional.of(items.get(id));
        }
        return null;
    }
    @Override
    public Optional<Vehiculo> findByMatricula(String matricula) {
        var items=this.load();
        return items.values().stream().filter(vehiculo -> vehiculo.getMatricula().equals(matricula)).findFirst();

    }
    @Override
    public void remove(Vehiculo vehiculo) throws NoSuchFieldException {
        HashMap<String,Vehiculo> items= load();
        if(items.containsKey(vehiculo.getMatricula())){
            items.remove(vehiculo.getMatricula());
            this.save(items);
            //return true;
        }
        else
            throw  new  NoSuchFieldException("La cancion no existe en el sistema");
    }

    @Override
    public void update(Vehiculo item) throws NoSuchFieldException {
        HashMap<String,Vehiculo> items= load();
        Vehiculo original= items.get(item.getMatricula());
        if(original != null) {
            original.setColor(item.getColor());
            original.setModelo(item.getModelo());
            original.setMarca(item.getMarca());
            original.setPathimagen(item.getPathimagen());
            original.setPeriodoRevision(item.getPeriodoRevision());
            original.setProximaRevision(item.getProximaRevision());
            original.setRevisiones(item.getRevisiones());

            this.save(items);

        }else
            throw  new NoSuchFieldException(" No existe exa matrícula en el sistema");

    }

    @Override
    public List<Vehiculo> findAll() {
        var items=this.load();
        return items.values().stream().toList();
    }
    private HashMap<String,Vehiculo>  load() {
        var items=new HashMap<String,Vehiculo>();
        if (archivo.exists()) {
            try {
               items=mapper.readValue(archivo,   new TypeReference<HashMap<String, Vehiculo>>() {});

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return items;

    }
    private void save(HashMap<String,Vehiculo> items) {
        try {

            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, items);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
