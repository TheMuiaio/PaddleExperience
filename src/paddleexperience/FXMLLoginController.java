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

import DBAcess.ClubDBAccess;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Member;

import paddleexperience.CurrentUser;
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
    
    private ClubDBAccess clubDBAccess;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        
        botoAcceptar.requestFocus();
        
    }    

    @FXML
    private void checkLogIn(ActionEvent event) throws IOException {
        userName.getScene().setCursor(Cursor.DEFAULT);
        
        String login, password;
        login = userName.getText();
        password = contrassenya.getText();
        
        // COMPROVAR
        if (login.length() != 0 && password.length() != 0) { //usuari o contrassenya buits

            //VICTOR: si t'avorreixes, fes que primer comprove una cosa i després l'altra
            //per a que ens diga quina de les dos ha fallat :)

            //GERMÀRMOL: el que he fet és canviar la condició, que haja de cumplir que no estam buits

            if (clubDBAccess.existsLogin(userName.getText())) { //comprovem que el login introduit existeix

                if (clubDBAccess.getMemberByCredentials(login, password) == null) { //comprovem que la contrassenya siga correcta
                    contrassenya.setText("");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Contrassenya incorrecta");
                    alert.setContentText("La contrassenya introduïda no es correspon amb l'usuari introduït.");
                    alert.showAndWait();
                    contrassenya.requestFocus();

                } else { // Usuari i contrassenya correctes.
                    //Inicialitzem l'usuari al login introduit
                    CurrentUser.setMembre(login, password);
                    //canviem al fxml d'usuari
                    //Tornem les coses al seu lloc
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(800);
                    ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(1100);
                    ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Usuari no trobat");
                alert.setContentText("El nom d'usuari introduit no existeix a la nostra base de dades.");
                alert.show();
            }
        } else { //un dels dos camps buit
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Credencials errònies");
            alert.setContentText("Emplena els camps d'usuari i contrassenya.");
            alert.show();
        }
    }

    @FXML
    private void toSignIn(MouseEvent event) throws IOException{
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(800);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(1100);
        userName.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLRegister.fxml")));
    }
    
    @FXML
    private void onBack(Event event) throws IOException {
        
        //Tornem les coses al seu lloc
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(800);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(1100);
        try{
            userName.getScene().setCursor(Cursor.DEFAULT);
            ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
        }
        
        catch(NullPointerException e){}
    }

    @FXML
    private void outLink(MouseEvent event) { 
        //te la nullPointer pq en canviar d'escena ho detecta com a un out del boto i, al no trobar l'escena, peta
        try{
            userName.getScene().setCursor(Cursor.DEFAULT); //Change cursor to default
        }
        catch(NullPointerException e){}
    }

    @FXML
    private void onLink(MouseEvent event) {
        try{
            userName.getScene().setCursor(Cursor.HAND); //Change cursor to hand
        }
        catch(NullPointerException e){}
    }

}
