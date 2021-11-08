package Controller;

import Dao.UsuarioDao;
import Models.Usuarios;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegisterController implements Initializable {

    /*Boton Volver*/
    @FXML
    private Button btnIniciarSesion;

    /*Boton Guardar*/
    @FXML
    private Button btnRegistrar;

    /*Textareas*/
    @FXML
    private TextField textNombre;

    @FXML
    private TextField textApellidos;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField PassContraseña;

    /*base de datos*/
    private UsuarioDao usuarioDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioDao = new UsuarioDao();//opciones de sql
    }

    /*Crear Usuario*/
    @FXML
    void btnRegistrarOnAction(ActionEvent event) throws SQLException {
        Usuarios usuario = new Usuarios();
        usuario.setNombre(textNombre.getText());
        usuario.setApellido(textApellidos.getText());
        usuario.setUsario(txtUserName.getText());
        usuario.setCorreo(txtCorreo.getText());
        usuario.setContraseña(PassContraseña.getText());

        System.out.println(usuario.toString());

        boolean rsp = this.usuarioDao.register(usuario);
        if (rsp) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Exito");
            alerta.setHeaderText(null);
            alerta.setContentText("Se regístro correctamente el usuario");
            alerta.initStyle(StageStyle.UTILITY);
            alerta.showAndWait();
            limpiarCampos();
            

        } else {

            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Hubo un error, consulte el adminsitrador");
            alerta.initStyle(StageStyle.UTILITY);
            alerta.showAndWait();
            limpiarCampos();
        }
    }

    /*Limpiar campos*/
    private void limpiarCampos() {
       textNombre.setText("");
       textApellidos.setText("");
       txtUserName.setText("");
       txtCorreo.setText("");
       PassContraseña.setText("");
    }
    
    /*Botones para Cerrar ventana*/
    @FXML
    private void btnIniciarSesionOnAction(ActionEvent event) {
        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Home.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            HomeController controlador = loader.getController();

            // Creo la scene y el stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // Asocio el stage con el scene
            stage.setScene(scene);
            stage.show();

            // Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnIniciarSesion.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeWindows() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Home.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnIniciarSesion.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
