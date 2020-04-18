/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class FXMLReservesController implements Initializable {


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void outLink(MouseEvent event) {
        try{
            ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
        } 
        catch(NullPointerException e){}
    }

    @FXML
    private void onLink(MouseEvent event) {
        try{
            ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
        }
        catch(NullPointerException e){}
    }

    @FXML
    private void onBack(MouseEvent event) throws IOException {
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
        ((Node)event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
    }

    @FXML
    private void onCancelarReserva(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diàleg de confirmació");
        alert.setHeaderText("Vas a anul·lar aquesta reserva");
        alert.setContentText("Vols continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //Cancelem la reserva
            System.out.println("OK");
        } else {
            //ps no la cancelem 
            System.out.println("CANCEL");
        }
    }

    
}
