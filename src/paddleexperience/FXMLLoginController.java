/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Gerard
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField userName;
    @FXML
    private TextField contrassenya;
    @FXML
    private Button botoAcceptar;
    @FXML
    private Label signIn;
    @FXML
    private ImageView backImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String hola = "\"hola\"";
    }    

    @FXML
    private void checkLogIn(ActionEvent event) throws IOException {
       userName.getScene().setCursor(Cursor.DEFAULT);
        // COMPROVAR
        if(false /*user + pssw ok*/){
            ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Usuari no trobat");
            alert.setContentText("El login i contrasenya introduit no coincideix amb cap usuari enregistrat. ");
            alert.showAndWait();
        }
    }

    @FXML
    private void toSignIn(MouseEvent event) throws IOException{
        userName.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLRegister.fxml")));
    }
    
    @FXML
    private void onBack(Event event) throws IOException {
        userName.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
    }

    @FXML
    private void outLink(MouseEvent event) throws NullPointerException { 
        //te la nullPointer pq en canviar d'escena ho detecta com a un out del boto i, al no trobar l'escena, peta
        userName.getScene().setCursor(Cursor.DEFAULT); //Change cursor to default
    }

    @FXML
    private void onLink(MouseEvent event) {
        userName.getScene().setCursor(Cursor.HAND); //Change cursor to hand
    }

}
