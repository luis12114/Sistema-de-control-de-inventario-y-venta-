package Dao;

import Connection.ConnectionMsql;
import Models.Usuarios;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {

    private ConnectionMsql fabricaConexion;

    public UsuarioDao() {
        this.fabricaConexion = new ConnectionMsql();
    }

    public boolean Login(String Usuario, String Password) {
        try {
            String SQL = "SELECT id_User FROM  usuarios WHERE  user_Name=? AND contraseña=?";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setString(1, Usuario);
            sentencia.setString(2, Password);
            ResultSet data = sentencia.executeQuery();
            if (data.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Ocurrio un error al intentar acceder");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean register(Usuarios usuario) throws SQLException {
        try {
            String SQL = "INSERT INTO usuarios(nombre,apellidos,user_Name,correo,contraseña) VALUES(?,?,?,?,?)";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getApellido());
            sentencia.setString(3, usuario.getUsario());
            sentencia.setString(4, usuario.getCorreo());
            sentencia.setString(5, usuario.getContraseña());

            sentencia.executeUpdate();
            sentencia.close();
            return true;
        } catch (Exception e) {
            System.err.println("Ocurrio un error al intentar dar de alta  un usuario");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuarios> listar() {
        List<Usuarios> listarUsurios = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM db_crud_java_fx.usuarios;";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            while (data.next()) {
                Usuarios usuario = new Usuarios();
                usuario.setId(data.getInt(1));
                usuario.setNombre(data.getString(2));
                usuario.setApellido(data.getString(3));
                usuario.setUsario(data.getString(4));
                usuario.setCorreo(data.getString(5));
                usuario.setContraseña(data.getString(6));
                listarUsurios.add(usuario);
            }
            data.close();
            sentencia.close();
        } catch (Exception e) {
            System.err.println("Ocurrio un error al listar  los usurios");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
        }
        return listarUsurios;
    }

    public boolean editar(Usuarios usuario) {
        try {
            String SQL = "UPDATE usuarios SET nombre=? ,apellidos=? ,user_Name=? ,correo=? ,contraseña=? WHERE id_User=?";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);

            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getApellido());
            sentencia.setString(3, usuario.getUsario());
            sentencia.setString(4, usuario.getCorreo());
            sentencia.setString(5, usuario.getContraseña());

            sentencia.setInt(6, usuario.getId());

            sentencia.executeUpdate();
            sentencia.close();
            return true;

        } catch (Exception e) {
            System.err.println("Ocurrio un error al editar  el usuario");
            System.err.println("Mensaje del erro:" + e.getMessage());
            System.err.println("Detalle del error:");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        try {
            String SQL = "DELETE FROM usuarios WHERE id_User=?";
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
