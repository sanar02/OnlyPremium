package es.burgueses.presentacion.controladores.navegacion;

import java.util.HashMap;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class Router {
    private Stack<FXMLLoader> stack;
    private HashMap<String,FXMLLoader> windows;
    private StackPane main;
    public  Router(){
        this.stack = new Stack<>();
        this.windows = new HashMap<>();
    }
    public void setMain(StackPane main){
        this.main = main;
    }
    public StackPane getMain(){
        return this.main;
    }
    public void push(FXMLLoader item){

        this.stack.push(item);
        this.main.getChildren().forEach(node -> node.setVisible(false));
        //ya existe se pasa a la cima
        if(this.main.getChildren().size()>0 && this.main.getChildren().stream().anyMatch(node -> node.equals(item.getRoot()))){
            this.main.getChildren().remove(item.getRoot());
            ((Node)item.getRoot()).setVisible(true);

        }
        //se pone en la cima
        this.main.getChildren().add(item.getRoot());
        ((AWindows)item.getController()).init();
    }
    public void push(String name){
        if(this.windows.containsKey(name)) {
            this.push(this.windows.get(name));
        }
    }
    public FXMLLoader pop(){

        FXMLLoader item = null;
        //en el caso de no estar vacia
        if(!this.stack.isEmpty()){
            item = this.stack.pop();
            //se para la ventana
            ((AWindows) item.getController()).stop();

            this.main.getChildren().remove(item.getRoot());
            //si queda alguno
            if(this.main.getChildren().size()>0)
                this.main.getChildren().get(this.main.getChildren().size()-1).setVisible(true);
        }
        return item;
    }
    public FXMLLoader getCurrent(){
        return this.stack.peek();
    }
    public void add(String name,FXMLLoader item){
        this.windows.put(name,item);
    }
    public FXMLLoader get(String name){
        return this.windows.get(name);
    }
    public boolean isEmpty(){
        return this.stack.isEmpty();
    }
    public void clear(){
        while(!this.stack.isEmpty()){
            this.pop();
        }
    }
}
