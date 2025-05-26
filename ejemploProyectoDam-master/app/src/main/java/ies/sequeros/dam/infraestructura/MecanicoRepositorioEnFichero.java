package ies.sequeros.dam.infraestructura;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ies.sequeros.dam.dominio.IMecanicoRepositorio;
import ies.sequeros.dam.dominio.Mecanico;
import ies.sequeros.dam.dominio.Mecanico;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio de mecánicos utilizando un fichero.
 * Esta clase maneja la persistencia de los mecánicos en un archivo JSON.
 */
public class MecanicoRepositorioEnFichero implements IMecanicoRepositorio {
    private final ObjectMapper mapper;
    private final File archivo;
    public MecanicoRepositorioEnFichero(String path) {
        this.archivo = new File(path);
        mapper = new ObjectMapper();
    }
    @Override
    public void add(Mecanico value) throws IOException {

        var items=this.load();
        //se comprueba que el mecanico no existe
        if(!items.containsKey(value.getId() )
                && !items.containsValue(value)
        ) {
            Optional<Integer> max=items.keySet().stream().max((o1, o2) -> {
                int i = o1 - o2;
                return i;

            });
            if(max.isPresent()) {
                value.setId(max.get() + 1);
            }
            else{
                value.setId(1);
            }
            items.put(value.getId(),value);
            this.save(items);
        }else{
            throw new IllegalStateException("El mecánico ya existe en el sistema");
        }
    }

    @Override
    public Mecanico findById(int id) {
        var items=this.load();
        if(items.containsKey(id)) {
            return items.get(id);
        }
        return null;
    }

    @Override
    public void remove(Mecanico item) throws NoSuchFieldException, IOException {
        HashMap<Integer,Mecanico> items= load();
        if(items.containsKey(item.getId())){
            items.remove(item.getId());
            this.save(items);
            //return true;
        }
        else
            throw  new  NoSuchFieldException("El mecánico no existe en el sistema");
    }

    @Override
    public void update(Mecanico item) throws NoSuchFieldException, IOException {
        HashMap<Integer,Mecanico> items= load();
        Mecanico original= items.get(item.getId());
        if(original != null) {
            original.setActivo(item.isActivo());
            original.setDescripcion(item.getDescripcion());
            original.setEmail(item.getEmail());
            original.setNombre(item.getNombre());
            this.save(items);

        }else
            throw  new NoSuchFieldException(" No existe exa matrícula en el sistema");

    }

    @Override
    public List<Mecanico> findAll() {
        var items=this.load();
        return items.values().stream().toList();
    }
    private HashMap<Integer,Mecanico>  load() {
        var items=new HashMap<Integer,Mecanico>();
        if (archivo.exists()) {
            try {
                items=mapper.readValue(archivo,   new TypeReference<HashMap<Integer, Mecanico>>() {});

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return items;

    }
    private void save(HashMap<Integer,Mecanico> items) throws IOException {


            mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, items);

    }
}
