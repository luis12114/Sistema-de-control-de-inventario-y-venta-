package Controller;

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
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ProductosController implements Initializable {

    @FXML
    private Button btnVolver;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
   
    /*Boton volver*/
    @FXML
    private void btnVolverOnAction(ActionEvent event) {
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

            // Indico que debe hacer al cerrar
            stage.setOnCloseRequest(e -> controlador.closeWindows());
            //Ciero la ventana donde estoy
            Stage myStage = (Stage) this.btnVolver.getScene().getWindow();
            myStage.close();

        } catch (IOException ex) {
            Logger.getLogger( ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Botones para Cerrar ventana*/
    public void closeWindows() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Menu.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setScene(scene);
            stage.show();

            Stage myStage = (Stage) this.btnVolver.getScene().getWindow();
            myStage.close();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
