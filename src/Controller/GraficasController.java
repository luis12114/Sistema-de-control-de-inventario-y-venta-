package Controller;

import Dao.GrafiacasDAO_Productos;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

public class GraficasController implements Initializable {

    /*Volver*/
    @FXML
    private Button btnVolver;
    
    /*Combo Box*/
    @FXML
    private ComboBox<GrafiacasDAO_Productos> CMBProductos;
    
    /*Graficar*/
    @FXML
    private Button btnGraficar;
    
    /*Inpust tipo date*/
    @FXML
    private DatePicker dateFechaInicio;

    @FXML
    private DatePicker dateFechaFin;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GrafiacasDAO_Productos d= new GrafiacasDAO_Productos();
        ObservableList<GrafiacasDAO_Productos> obsDatos = d.getDatos();
        this.CMBProductos.setItems(obsDatos);
    }
    
    
    /*Accion para Graficar*/
    @FXML
    void btnGraficarOnAction(ActionEvent event) {
        GrafiacasDAO_Productos productos= CMBProductos.getSelectionModel().getSelectedItem();
        String fechaIni=dateFechaInicio.getValue().toString();
        String fechaFin=dateFechaFin.getValue().toString();
        System.out.println("Producto:"+productos+"Fecha de inicio:"+fechaIni+"Fecha fin:"+fechaFin);
    }

    /*Botones para Cerrar ventana*/
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
            Logger.getLogger( GraficasController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
