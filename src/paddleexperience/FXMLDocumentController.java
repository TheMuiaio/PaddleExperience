/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import static DBAcess.ClubDBAccess.getSingletonClubDBAccess;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;

        
/**
 *
 * @author Gerard
 */
public class FXMLDocumentController implements Initializable {
    
    //hola
    //altreola
    
    @FXML
    private ImageView backDate;
    @FXML
    private Label dateLabel;
    @FXML
    private ImageView forwardDate;
    @FXML
    private TableView<?> pista1;
    @FXML
    private TableView<?> pista2;
    @FXML
    private TableView<?> pista3;
    @FXML
    private TableView<?> pista4;
    @FXML
    private Button login;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void toLogIn(ActionEvent event) throws IOException {
        login.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")));
    }

    @FXML
    private void previousDay(MouseEvent event) {
    }

    @FXML
    private void nextDay(MouseEvent event) {
    }

    @FXML
    private void outLink(MouseEvent event) {
        login.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void onLink(MouseEvent event) throws NullPointerException{
        login.getScene().setCursor(Cursor.HAND);
        
    }
    
}
