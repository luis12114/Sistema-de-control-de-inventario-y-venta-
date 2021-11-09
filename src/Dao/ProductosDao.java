
package Dao;

import Connection.ConnectionMsql;
import Models.Productos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductosDao {

    private ConnectionMsql fabricaConexion;

    public ProductosDao() {
        this.fabricaConexion = new ConnectionMsql();
    }

    public boolean Insert(Productos producto) throws SQLException {
        try {
            String SQL = "INSERT INTO productos (nomP, Precio,DescP,unidades) VALUES (?,?,?,?)";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setString(1, producto.getNombre());
            sentencia.setFloat(2, producto.getPrecio());
            sentencia.setString(3, producto.getDescripcion());
            sentencia.setInt(4, producto.getUnidades());
            //int data = sentencia.executeUpdate();
            
             sentencia.executeUpdate();
            sentencia.close();
            return true;
               
            
        } catch (Exception e) {
            System.err.println("Ocurrio un error al intentar acceder");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
            return false;
        }
    }


      public List<Productos> listar() {
        List<Productos> listarProductos = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM db_crud_java_fx.productos;";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            while (data.next()) {
                Productos producto = new Productos();
                 producto.setId(data.getInt(1));
                producto.setNombre(data.getString(2));
                producto.setPrecio(data.getFloat(3));
                producto.setDescripcion(data.getString(4));
                producto.setUnidades(data.getInt(5));
                
                listarProductos.add(producto);
            }
            data.close();
            sentencia.close();
        } catch (Exception e) {
            System.err.println("Ocurrio un error al listar  los productos");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
        }
        return listarProductos;
    }

    public boolean editar(Productos producto) {
        try {
            String SQL = "UPDATE productos SET nomP=? ,Precio=? ,DescP=? ,unidades=?  WHERE id_P=?";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);

            sentencia.setString(1, producto.getNombre());
            sentencia.setFloat(2, producto.getPrecio());
            sentencia.setString(3, producto.getDescripcion());
            sentencia.setInt(4, producto.getUnidades());
            
            sentencia.setInt(5, producto.getId());

            sentencia.executeUpdate();
            sentencia.close();
            return true;

        } catch (Exception e) {
            System.err.println("Ocurrio un error al editar  el producto");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            String SQL = "DELETE FROM productos WHERE id_P=?";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setInt(1, id);
            sentencia.executeUpdate();
            sentencia.close();
            return true;
        } catch (Exception e) {
            System.err.println("Ocurrio un error al eliminar  el usuario");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
            return false;
        }
    }

    


}
