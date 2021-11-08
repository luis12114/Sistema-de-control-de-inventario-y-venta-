package Controller;

import Dao.ProductosDao;
import Models.Productos;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProductosController implements Initializable {

    @FXML
    private Button btnVolver;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField TextNP;

    @FXML
    private TextField TextPU;

    @FXML
    private TextField TextUn;

    @FXML
    private TextArea TextDesc;

    @FXML
    private TableView<Productos> TVproductos;

    private ProductosDao productosDao;

    /*Menu contextual*/
    private ContextMenu cmOpciones;

    private Productos productoSelect;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.productosDao = new ProductosDao();
        cargarTareas();//cargar tabla con datos


        /*Menu contextual*/
        cmOpciones = new ContextMenu();
        MenuItem miEditar = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");
        cmOpciones.getItems().addAll(miEditar, miEliminar);
        TVproductos.setContextMenu(cmOpciones);


        /*Editar*/
        miEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = TVproductos.getSelectionModel().getSelectedIndex();
                productoSelect = TVproductos.getItems().get(index);
                System.out.println(productoSelect);
                TextNP.setText(productoSelect.getNombre());
                TextPU.setText(Float.toString(productoSelect.getPrecio()));
                TextDesc.setText(productoSelect.getDescripcion());
                TextUn.setText(Integer.toString(productoSelect.getUnidades()));

                btnCancelar.setDisable(false);
            }
        });

        /*Eliminar*/
        miEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = TVproductos.getSelectionModel().getSelectedIndex();
                Productos ProductoEliminar = TVproductos.getItems().get(index);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Realmente desea eliminar la tarea:" + ProductoEliminar.getNombre() + "?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.OK) {
                    boolean rsp = productosDao.eliminar(ProductoEliminar.getId());
                    if (rsp) {
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Exito");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Se Elimino correctamente el producto");
                        alerta.initStyle(StageStyle.UTILITY);
                        alerta.showAndWait();
                        cargarTareas();
                    } else {
                        Alert alerta = new Alert(Alert.AlertType.ERROR);
                        alerta.setTitle("Error");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Hubo un error consulte con el administrador");
                        alerta.initStyle(StageStyle.UTILITY);
                        alerta.showAndWait();
                    }
                }
            }
        });

    }

    /*Boton Guardar*/
    @FXML
    private void btnGuardarOnAction(ActionEvent event) throws SQLException {

        /*String nombre = TextNP.getText();
         String Descripcion = TextDesc.getText();
         float precio = Float.parseFloat(TextPU.getText());
         int unidades = Integer.parseInt(TextUn.getText());*/
        if (productoSelect == null) {

            Productos producto = new Productos();
            producto.setNombre(TextNP.getText());
            producto.setDescripcion(TextDesc.getText());
            producto.setPrecio(Float.parseFloat(TextPU.getText()));
            producto.setUnidades(Integer.parseInt(TextUn.getText()));

            System.out.println(producto.toString());

            boolean rsp = this.productosDao.Insert(producto);
            if (rsp) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Alerta");
                alerta.setHeaderText(null);
                alerta.setContentText("Datos ingresados correctamente");
                alerta.initStyle(StageStyle.UTILITY);
                alerta.showAndWait();
                limpiarCampos();
                cargarTareas();

            } else {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Alerta");
                alerta.setHeaderText(null);
                alerta.setContentText("Error al ingresar los datos");
                alerta.initStyle(StageStyle.UTILITY);
                alerta.showAndWait();
                limpiarCampos();

            }

        } else {
            productoSelect.setNombre(TextNP.getText());
            productoSelect.setPrecio(Float.parseFloat(TextPU.getText()));
            productoSelect.setDescripcion(TextDesc.getText());
            productoSelect.setUnidades(Integer.parseInt(TextUn.getText()));

            boolean rsp = this.productosDao.editar(productoSelect);
            if (rsp) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Exito");
                alerta.setHeaderText(null);
                alerta.setContentText("Se guardo correctamente el producto");
                alerta.initStyle(StageStyle.UTILITY);
                alerta.showAndWait();
                limpiarCampos();
                cargarTareas();

                productoSelect = null;
                btnCancelar.setDisable(true);
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

    }

    /*Tabla*/
    public void cargarTareas() {
        TVproductos.getItems().clear();
        TVproductos.getColumns().clear();
        List<Productos> productos = this.productosDao.listar();

        ObservableList<Productos> data = FXCollections.observableArrayList(productos);

        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn nombreCol = new TableColumn("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory("nombre"));

        TableColumn precioCol = new TableColumn("Precio");
        precioCol.setCellValueFactory(new PropertyValueFactory("precio"));

        TableColumn descripcionCol = new TableColumn("Descripcion");
        descripcionCol.setCellValueFactory(new PropertyValueFactory("descripcion"));

        TableColumn unidadesCol = new TableColumn("Unidades");
        unidadesCol.setCellValueFactory(new PropertyValueFactory("unidades"));

        TVproductos.setItems(data);
        TVproductos.getColumns().addAll(idCol, nombreCol, precioCol, descripcionCol, unidadesCol);
        System.out.println("prueba controller tabla");
    }

    /*Boton Cancelar*/
    @FXML
    private void btnCancelarOnAction(ActionEvent event) {

        productoSelect = null;
        limpiarCampos();
        btnCancelar.setDisable(true);

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
            Logger.getLogger(ProductosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Limpiar Campos*/
    private void limpiarCampos() {
        TextNP.setText("");
        TextPU.setText("");
        TextUn.setText("");
        TextDesc.setText("");

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
