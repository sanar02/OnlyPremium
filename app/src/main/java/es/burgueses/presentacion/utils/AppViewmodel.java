package es.burgueses.presentacion.utils;

import java.util.UUID;

import es.burgueses.infraestructura.UsuarioMongo;
import es.burgueses.presentacion.controladores.configuracion.configuracionController;

public class AppViewmodel {

    // Add the user field (assuming JavaFX property, adjust type if needed)
    private javafx.beans.property.ObjectProperty<UsuarioMongo> user = new javafx.beans.property.SimpleObjectProperty<>();

    public AppViewmodel(){

    }
   public boolean isRoot(){
        return true; //user!=null && user.get()!=null && user.get().getId().equals(new UUID(0L, 0L));
    }
    public boolean isAdmin(){
        return isRoot(); //|| (user!=null && user.get()!=null && user.get().getRole().equals(User.Role.ADMIN));
    }
    public boolean login(String username, String password) {
       // configuracionController c=configuracionController.getInstancia();
        //if(c.getAdminName().equals(username) && c.getAdminPassword().equals(password)) {
           /* User u= new User();
            u.setId(new UUID(0L, 0L));
            u.setName(rootName);
            u.setPassword("");
            u.setRole(User.Role.ADMIN);
            user.set(u);*/
            return true;
       // }else{
            /*User u= userRepository.findByNickAndPassword(username, password);
            if(u != null) {
                user.set(u);
                return true;
            }*/
          //  return false;
       //}
    }
   /* public ReadOnlyObjectProperty<User> getUser() {
        return user;
    }
    public void logout(){
        user.set(null);
    }*/

}
