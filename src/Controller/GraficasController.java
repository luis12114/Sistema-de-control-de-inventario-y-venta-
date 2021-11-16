package Controller;

import Dao.GrafiacasDAO_Productos;
import Connection.ConnectionMsql;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GraficasController extends ConnectionMsql implements Initializable {

    /*Volver*/
    @FXML
    private Button btnVolver;

    /*Reporte*/
    @FXML
    private Button btnReporte;
    
    @FXML
    private Button btnAbrirReporte;

    /*Grafica*/
    @FXML
    PieChart graficos;
    ObservableList<PieChart.Data> pieChartData;

    /*Inpust tipo date*/
    @FXML
    private DatePicker dateFechaInicio;

    @FXML
    private DatePicker dateFechaFin;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        graficos.setData(pieChartData);
    }

    /*Accion para Generar reportes*/
    @FXML
    void btnReporteOnAction(ActionEvent event) throws FileNotFoundException, DocumentException {
        ResultSet res;
        try {
            FileOutputStream archivo = new FileOutputStream("Reporte" + ".pdf");
            Document documento = new Document();
            PdfWriter.getInstance(documento, archivo);
            documento.open();

            Paragraph parrafo = new Paragraph("Reporte de venta\n\n");
            parrafo.setAlignment(1);
            documento.add(parrafo);
            
            this.ConnectionMsql();
            String SQL = "SELECT * FROM ventas_p";
            PreparedStatement sentencia = this.getCon().prepareStatement(SQL);
            res = sentencia.executeQuery();
            while (res.next()) {
                documento.add(new Paragraph("Nombre del cliente:"+res.getString("NombreCliente")+"\n"));
                documento.add(new Paragraph("RFC:"+res.getString("RFC")+"\n"));
                documento.add(new Paragraph("Fecha de venta:"+res.getString("Fecha")+"\n"));
                documento.add(new Paragraph("Producto:"+res.getString("NombreProducto")+"\n"));
                documento.add(new Paragraph("Unidadades:"+ res.getInt("Cantidad")+"\n"));
                documento.add(new Paragraph("Precio:"+ res.getInt("Cantidad")+"\n"));
                documento.add(new Paragraph("Concepto:"+ res.getInt("Cantidad")+"\n"));
                documento.add(new Paragraph("----------------------------------------------------\n"));
            }
            documento.close();

            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Exito");
            alerta.setHeaderText(null);
            alerta.setContentText("Reporte Creado correctamente");
            alerta.initStyle(StageStyle.UTILITY);
            alerta.showAndWait();

        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Hubo un error  al crear el reporte");
            alerta.initStyle(StageStyle.UTILITY);
            alerta.showAndWait();
        }

    }
    
    @FXML
    void btnAbrirReporteOnAction(ActionEvent event) {
        try {
            File path=new File("Reporte" + ".pdf");
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error al abrir el archivo");
            alerta.setHeaderText(null);
            alerta.setContentText("Hubo un error  al abrir el archivo");
            alerta.initStyle(StageStyle.UTILITY);
            alerta.showAndWait();
        }
    }

    /*Cargar Datos de grafica*/
    public void cargarDatos() {
        ResultSet res;
        pieChartData = FXCollections.observableArrayList();
        try {
            this.ConnectionMsql();
            String SQL = "SELECT * FROM ventas_p";
            PreparedStatement sentencia = this.getCon().prepareStatement(SQL);
            res = sentencia.executeQuery();
            while (res.next()) {
                pieChartData.add(new PieChart.Data(res.getString("NombreProducto"), res.getInt("Cantidad")));
            }
        } catch (Exception e) {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText(null);
            alerta.setContentText("Hubo un error consulte con el administrador");
            alerta.initStyle(StageStyle.UTILITY);
            alerta.showAndWait();
        }
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
            Logger.getLogger(GraficasController.class.getName()).log(Level.SEVERE, null, ex);
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
