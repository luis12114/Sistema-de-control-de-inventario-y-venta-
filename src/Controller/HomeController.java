package Controller;

import Dao.UsuarioDao;
import Models.Usuarios;
import java.io.File;

import java.io.IOException;
import java.net.URL;
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
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

public class HomeController implements Initializable {

    @FXML
    private Button btnRegistro;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField textUserName;

    @FXML
    private PasswordField textConstrseña;

    @FXML
    private ImageView ImageLogo;

    private UsuarioDao usuarioDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioDao = new UsuarioDao();
    }

    /*Boton de login*/
    @FXML
    private void btnLoginOnAction(ActionEvent event) {
        String usuario = textUserName.getText();
        String constraseña = textConstrseña.getText();

        boolean rsp = this.usuarioDao.Login(usuario, constraseña);
        if (rsp) {

            try {
                // Cargo la vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Menu.fxml"));

                // Cargo el padre
                Parent root = loader.load();

                // Obtengo el controlador
                MenuController controlador = loader.getController();

                // Creo la scene y el stage
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                // Asocio el stage con el scene
                stage.setScene(scene);
                stage.show();

                //Indico que debe hacer al cerrar
                stage.setOnCloseRequest(e -> controlador.closeWindows());

                // Ciero la ventana donde estoy
                Stage myStage = (Stage) this.btnLogin.getScene().getWindow();
                myStage.close();

            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            if (usuario == null ||"".equals(usuario)) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Alerta");
                alerta.setHeaderText(null);
                alerta.setContentText("Ingrese tu usario");
                alerta.initStyle(StageStyle.UTILITY);
                alerta.showAndWait();
            } else if ("".equals(constraseña) || constraseña == null) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Alerta");
                alerta.setHeaderText(null);
                alerta.setContentText("Ingrese tu contraseña");
                alerta.initStyle(StageStyle.UTILITY);
                alerta.showAndWait();
            } 
            else if(usuario!=null && constraseña!=null){
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("Usuario o Contraseña incorrectos");
                alerta.initStyle(StageStyle.UTILITY);
                alerta.showAndWait();
                limpiarCampos();
            }
        }

    }

    /*Limpiar Campos*/
    private void limpiarCampos() {
        textUserName.setText("");
        textConstrseña.setText("");
    }

    /*Boton de registrar*/
    @FXML
    private void btnRegistroOnAction(ActionEvent event) {

        try {
            // Cargo la vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Register.fxml"));

            // Cargo el padre
            Parent root = loader.load();

            // Obtengo el controlador
            RegisterController controlador = loader.getController();

            // Creo la scene y el stage
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            // Asocio el stage con el scene
            stage.setScene(scene);
            stage.show();

            // Indico que debe hacer al cerrar
            stage.setOnCloseRequest(e -> controlador.closeWindows());

            // Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnRegistro.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Botones para Cerrar ventana*/
    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Home.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnLogin.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
