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
            if(data.next()){
               return true;
            }else{
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
}
