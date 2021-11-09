package Dao;

import Connection.ConnectionMsql;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GrafiacasDAO_Productos {
    private ConnectionMsql fabricaConexion;
    
    private int id;
    private String nombre;
    private float precio;
    private String descripcion;
    private int unidades;
    
    public GrafiacasDAO_Productos (){
     this.fabricaConexion = new ConnectionMsql();
    }
    
    public GrafiacasDAO_Productos (int id1, String nombre1, float precio1, String descripcion1, int unidades1) {
        this.id=id1;
        this.nombre=nombre1;
        this.precio=precio1;
        this.descripcion=descripcion1;
        this.unidades=unidades1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
    
    
    public ObservableList<GrafiacasDAO_Productos> getDatos() {
     ObservableList<GrafiacasDAO_Productos> obs = FXCollections.observableArrayList(); 
        try {
            String SQL = "SELECT * FROM db_crud_java_fx.productos;";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            while(data.next()){
               int id1=data.getInt(1);
               String nombre1=data.getString(2);
               float precio1=data.getFloat(3);
               String descripcion1=data.getString(4);
               int unidades1=data.getInt(5);
               
               GrafiacasDAO_Productos D= new GrafiacasDAO_Productos(id1,nombre1,precio1,descripcion1,unidades1);
               obs.add(D);
            }
        } catch (Exception e) {
            System.err.println("Error de conexion");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
        }
        return obs;
    }
}
