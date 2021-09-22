
package supermercado;

import java.net.URI;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class SuperMercado extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       URI uri = Paths.get("src/Vistas/").toAbsolutePath().toUri();
        
        System.out.println("URI"+ uri.toString());
        Parent root = FXMLLoader.load(uri.toURL());
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }
    
}
