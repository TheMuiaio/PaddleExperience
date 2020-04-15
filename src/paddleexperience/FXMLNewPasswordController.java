/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Member;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class FXMLNewPasswordController implements Initializable {

    @FXML
    private PasswordField oldPssw;
    @FXML
    private PasswordField newPssw;
    @FXML
    private PasswordField confirmNewPssw;
    @FXML
    private Label info;
    
    Member user;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        user = CurrentUser.getMembre();
        info.setText("");
    }    

    @FXML
    private void onNew(ActionEvent event) {
        info.setTextFill(Color.BLACK);
        info.setText("Ha de ser una combinació de lletres i números amb més de 6 caràcters.");
    }

    @FXML
    private void onCanviar(ActionEvent event) throws InterruptedException, IOException {
        if(user.getPassword().equals(oldPssw.getText())){ //la oldPssw es bona
            if(newPssw.getText().length() > 5){ //cumpleix la condicio de >=6
                if(confirmNewPssw.getText().equals(newPssw.getText())){ //les dos pssw son iguals
                    if(!newPssw.getText().equals(user.getPassword())){ //mire que no canvie la pssw a la que tenia
                        
                        info.setTextFill(Color.GREEN);
                        info.setText("Contrassenya canviada amb èxit.");
                        user.setPassword(newPssw.getText());
                        
                        //actualitzem el currentUser
                        CurrentUser.setMembre(user.getLogin(), user.getPassword());
                        
                        //avisem a l'usuari que ha canviat la contrassenya
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Tot ha anat bé");
                        alert.setContentText("Has canviat amb èxit la contrassenya.");
                        alert.show();
                        
                        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLUserInfo.fxml")));
                        
                    } else {
                        info.setTextFill(Color.RED);
                        info.setText("La nova contrassenya no pot coincidir amb l'anterior.");
                    }
                    
                } else {
                info.setTextFill(Color.RED);
                info.setText("La contrassenya nova no coincideix.");
                }
                
            } else {
                info.setTextFill(Color.RED);
                info.setText("Escriu una nova contrassenya vàlida.");
            }
            
        } else {
            info.setTextFill(Color.RED);
            info.setText("La contrassenya actual no és correcta.");
        }
    }

    @FXML
    private void onBack(MouseEvent event) throws IOException {
        //Dóna error pq intenta agafar l'usuari i contrassenya ANTERIOR, així que cal fer de nou el user. Pero el faig altra begada, no entenc pq no ho fa be
        //si no fas cap canvi de login/pssw, tot va guai
        
        //edit: val, en CurrentUser el user i el password el fas a l'iniciar sessió. Cal tornar a fer un setMembre en canviar login/pssw
        
        info.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLUserInfo.fxml")));
    }

    @FXML
    private void infoDissapear(ActionEvent event) {
        info.setText("");
    }

    @FXML
    private void outLink(MouseEvent event) {
        try{
            info.getScene().setCursor(Cursor.DEFAULT);
        }
        catch(NullPointerException e){}
        
    }

    @FXML
    private void onLink(MouseEvent event) {
        try{
            info.getScene().setCursor(Cursor.HAND);
        }
        catch(NullPointerException e){}
    }
    
}
