package Controller;

import Dao.UsuarioDao;
import Models.Usuarios;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UsuariosController implements Initializable {

    /*Campos*/
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellidos;

    @FXML
    private TextField txtUserName;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField passConstraseña;

    /*Botones*/
    @FXML
    private Button btnVolver;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnCancelar;

    private UsuarioDao usuarioDao;

    /*Table*/
    @FXML
    private TableView<Usuarios> tvUsuarios;

    /*Menu contextual*/
    private ContextMenu cmOpciones;

    private Usuarios usuarioSelect;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.usuarioDao = new UsuarioDao();//opciones de sql
        cargarTareas();//cargar tabla con datos

        /*Menu contextual*/
        cmOpciones = new ContextMenu();
        MenuItem miEditar = new MenuItem("Editar");
        MenuItem miEliminar = new MenuItem("Eliminar");
        cmOpciones.getItems().addAll(miEditar, miEliminar);
        tvUsuarios.setContextMenu(cmOpciones);

        /*Editar*/
        miEditar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = tvUsuarios.getSelectionModel().getSelectedIndex();
                usuarioSelect = tvUsuarios.getItems().get(index);
                System.out.println(usuarioSelect);
                txtNombre.setText(usuarioSelect.getNombre());
                txtApellidos.setText(usuarioSelect.getApellido());
                txtUserName.setText(usuarioSelect.getUsario());
                txtCorreo.setText(usuarioSelect.getCorreo());
                passConstraseña.setText(usuarioSelect.getContraseña());

                btnCancelar.setDisable(false);
            }
        });
        
        /*Eliminar*/
        miEliminar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = tvUsuarios.getSelectionModel().getSelectedIndex();
                Usuarios UsuarioEliminar = tvUsuarios.getItems().get(index);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setHeaderText(null);
                alert.setContentText("Realmente desea eliminar la tarea:" + UsuarioEliminar.getNombre() + "?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();
                
                if (result.get() == ButtonType.OK) {
                    boolean rsp = usuarioDao.eliminar(UsuarioEliminar.getId());
                    if (rsp) {
                        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                        alerta.setTitle("Exito");
                        alerta.setHeaderText(null);
                        alerta.setContentText("Se Elimino correctamente el usuario");
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

    /*Boton Guardar */
    @FXML
    void btnGuardarOnAction(ActionEvent event) throws SQLException {
        if (usuarioSelect == null) {

            Usuarios usuario = new Usuarios();
            usuario.setNombre(txtNombre.getText());
            usuario.setApellido(txtApellidos.getText());
            usuario.setUsario(txtUserName.getText());
            usuario.setCorreo(txtCorreo.getText());
            usuario.setContraseña(passConstraseña.getText());

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
                cargarTareas();
            } else {

                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setTitle("Error");
                alerta.setHeaderText(null);
                alerta.setContentText("Hubo un error, consulte el adminsitrador");
                alerta.initStyle(StageStyle.UTILITY);
                alerta.showAndWait();
                limpiarCampos();

            }
        } else {
            usuarioSelect.setNombre(txtNombre.getText());
            usuarioSelect.setApellido(txtApellidos.getText());
            usuarioSelect.setUsario(txtUserName.getText());
            usuarioSelect.setCorreo(txtCorreo.getText());
            usuarioSelect.setContraseña(passConstraseña.getText());

            boolean rsp = this.usuarioDao.editar(usuarioSelect);
            if (rsp) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Exito");
                alerta.setHeaderText(null);
                alerta.setContentText("Se guardo correctamente el usuario");
                alerta.initStyle(StageStyle.UTILITY);
                alerta.showAndWait();
                limpiarCampos();
                cargarTareas();
                usuarioSelect = null;
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

    @FXML
    void btnCancelarOnAction(ActionEvent event) {
        usuarioSelect = null;
        limpiarCampos();
        btnCancelar.setDisable(true);
    }

    
    /*Tabla*/
    public void cargarTareas() {
        tvUsuarios.getItems().clear();
        tvUsuarios.getColumns().clear();
        List<Usuarios> usuarios = this.usuarioDao.listar();

        ObservableList<Usuarios> data = FXCollections.observableArrayList(usuarios);

        TableColumn idCol = new TableColumn("Id");
        idCol.setCellValueFactory(new PropertyValueFactory("id"));

        TableColumn nombreCol = new TableColumn("Nombre");
        nombreCol.setCellValueFactory(new PropertyValueFactory("nombre"));

        TableColumn apellidoCol = new TableColumn("Apellido");
        apellidoCol.setCellValueFactory(new PropertyValueFactory("apellido"));

        TableColumn usuarioCol = new TableColumn("Nombre de usuario");
        usuarioCol.setCellValueFactory(new PropertyValueFactory("usario"));

        TableColumn correoCol = new TableColumn("Correo");
        correoCol.setCellValueFactory(new PropertyValueFactory("correo"));

        TableColumn contraseñaCol = new TableColumn("Contraseña");
        contraseñaCol.setCellValueFactory(new PropertyValueFactory("contraseña"));

        tvUsuarios.setItems(data);
        tvUsuarios.getColumns().addAll(idCol, nombreCol, apellidoCol, usuarioCol, correoCol, contraseñaCol);
    }

    
    /*Limpiar Campos*/
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtUserName.setText("");
        txtCorreo.setText("");
        passConstraseña.setText("");
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
            Logger.getLogger(UsuariosController.class.getName()).log(Level.SEVERE, null, ex);
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
