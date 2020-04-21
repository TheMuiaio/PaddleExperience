/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Member;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class FXMLUserInfoController implements Initializable {

    @FXML
    private TextField telfField;
    @FXML
    private TextField nomField;
    @FXML
    private TextField cognomField;
    @FXML
    private ImageView profilePhoto;
    @FXML
    private TextField loginField;

    
    private Member user;
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        /////////////////////////////////
        // Usuari: prova               //
        // Pssw: provaprova            //
        /////////////////////////////////
        
        user = CurrentUser.getMembre();
        
        System.out.println("esticAci");
        
        nomField.setText(user.getName());
        cognomField.setText(user.getSurname());
        telfField.setText(user.getTelephone());
        profilePhoto.setImage(user.getImage());
        loginField.setText(user.getLogin());
    }   

    @FXML
    private void outLink(MouseEvent event) {
        try{
            nomField.getScene().setCursor(Cursor.DEFAULT);
        } 
        catch(NullPointerException e){}
    }

    @FXML
    private void onLink(MouseEvent event) {
        try{
            nomField.getScene().setCursor(Cursor.HAND);
        }
        catch(NullPointerException e){}
    }

    private void onNewImg(MouseEvent event) throws MalformedURLException {
        //aci he de fer que el camp imatge valga el que m'ha dit l'usuari
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imatge");
        File avatar = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.bmp", "*.png", "*.jpg", "*.gif"));
        if(avatar != null){
            String path = avatar.toURI().toURL().toString();
            Image image = new Image(path);
            profilePhoto.setImage(image);
            ((GridPane)event.getSource()).setGridLinesVisible(false);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Diàleg d'informació");
            alert.setHeaderText("No has elegit cap imatge.");
            alert.setContentText("Selecciona una imatge.");
            alert.showAndWait();
        }
        
        user.setImage(profilePhoto.getImage());
    }

    @FXML
    private void onLogOut(ActionEvent event) throws IOException {
        CurrentUser.setMembre(null, null);
        
        //Tornem les coses al seu lloc
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(630);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(520);
        
        //Avisem a l'usuari que ha tancat sessió
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Sessió tancada.");
        alert.setContentText("Has tancat la teua sessió correctament.");
        alert.show();
                
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")));
    }

    @FXML
    private void onBack(MouseEvent event) throws IOException {
        nomField.getScene().setCursor(Cursor.DEFAULT);
        
        //Tornem les coses al seu lloc
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(900);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(1350);
        
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
    }
        
}