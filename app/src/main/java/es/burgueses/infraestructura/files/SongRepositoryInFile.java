package es.burgueses.infraestructura.files;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import es.burgueses.dominio.Cancion;
import es.burgueses.dominio.Cancion.Genero;
import es.burgueses.dominio.ICancionesRepositorio;
import es.burgueses.dominio.Voto;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SongRepositoryInFile implements ICancionesRepositorio {
    protected final ObjectMapper mapper;
    protected  final File file;

    public SongRepositoryInFile(String path) {
        this.file = new File(path);
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

    }
    @Override
    public void add(Cancion item) {
        var items=this.load();
        //se comprueba que el mecanico no existe
        if(!items.containsKey(item.getId() )
                && !items.containsValue(item)
        ) {
            items.put(item.getId(),item);
            try {
                this.save(items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            throw  new IllegalArgumentException("Item  exists");
        }
    }



    public void delete(Cancion item)  {
        HashMap<UUID,Cancion> items= load();
        if(items.containsKey(item.getId())){
            items.remove(item.getId());
            try {
                this.save(items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        //else
        //    throw  new IllegalArgumentException("Item don´t exists");

    }

    @Override
    public void update(Cancion item) {
        HashMap<UUID,Cancion> items= load();
        Cancion original= items.get(item.getId());
        if(original != null) {
            items.remove(original.getId());
            items.put(item.getId(),item);
            try {
                this.save(items);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else
            throw  new IllegalArgumentException("Item don't exists");

    }

    public List<Cancion> getAll() {
        var items=this.load();
        return items.values().stream().toList();
    }


    public List<Cancion> findByPublicByGenre(Genero genero){
        var items=this.load().values();
        return items.stream().filter(s -> {
            return s.getGeneros().stream().anyMatch(genre -> genre.equals(genero));
        }).toList();
    }
    public List<Cancion> findByPublic(){
        var items=this.load().values();
        return items.stream().filter(c -> c.isPublica()).toList();
    }
    @Override
    public Cancion findById(UUID id) {
        var items=this.load().values();
        var item=items.stream().filter(pl -> {
            return pl.getId().equals(id);
        }).findFirst();


        return item.orElse(null);
    }

    public void close(){

    }

    private HashMap<UUID, Cancion> load() {
        var items=new HashMap<UUID,Cancion>();
        if (file.exists()) {
            try {
                items=mapper.readValue(file,   new TypeReference<HashMap<UUID, Cancion>>() {});

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return items;

    }
    private void save(HashMap<UUID,Cancion> items) throws IOException {

        mapper.writerWithDefaultPrettyPrinter().writeValue(file, items);

    }
    public void removeAll()  {
        var items=this.load();
        items.clear();
        try {
            this.save(items);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void remove(Cancion cancion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remove'");
    }
    @Override
    public Cancion findByTitulo(String titulo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByTitulo'");
    }
    @Override
    public List<Cancion> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    @Override
    public void addVotoMeGusta(String titulo, Voto voto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addVotoMeGusta'");
    }
    @Override
    public void addVotoNoMeGusta(String titulo, Voto voto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addVotoNoMeGusta'");
    }
    @Override
    public List<Voto> getVotosMeGusta(String titulo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVotosMeGusta'");
    }
    @Override
    public List<Voto> getVotosNoMeGusta(String titulo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getVotosNoMeGusta'");
    }
    @Override
    public String replace(String string, UUID id, String path) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'replace'");
    }

}