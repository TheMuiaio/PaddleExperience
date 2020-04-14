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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
        
        stage.setScene(scene);
        stage.show();
        
        //Guardem els canvis efectuats en la base de dades al tancar la finestra
        stage.setOnCloseRequest((WindowEvent event) -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(clubDBAccess.getClubName());
            alert.setHeaderText("Saving data in DB...");
            alert.setContentText("Saving changes in DataBase. This process may expend some minutes...");
            alert.show();
            clubDBAccess.saveDB();
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
