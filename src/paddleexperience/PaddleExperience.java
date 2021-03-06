/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import DBAcess.ClubDBAccess;
import com.sun.javafx.css.StyleManager;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.WindowEvent;


/**
 *
 * @author Gerard
 */
public class PaddleExperience extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        ClubDBAccess clubDBAccess;
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("Paddle Experience");
        
        String css = this.getClass().getResource("/resources/darkTheme.css") .toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.getIcons().add(new Image("/resources/pala.png"));
        stage.setScene(scene);
        
        stage.setMinHeight(900);
        stage.setMinWidth(1350);
        
        stage.setMaximized(true);
        stage.show();
        
        //Guardem els canvis efectuats en la base de dades al tancar la finestra
        
        //Et canvie l'idioma de la finestra emergent4
        
        stage.setOnCloseRequest((WindowEvent event) -> {
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Tancar aplicació");
            alert.setHeaderText("Estàs segur que vols tancar?");
            alert.setContentText("Es guardaran els canvis efectuats.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) { 
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle(clubDBAccess.getClubName());
                alert.setHeaderText("Guardant dades a la base de dades.");
                alert.setContentText("Guardant canvis a la base de dades. Aquest procés pot tardar uns minuts...");
                alert.show();
                clubDBAccess.saveDB();
                alert.hide();
            } else {
                event.consume();
            }
        });
    }
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
