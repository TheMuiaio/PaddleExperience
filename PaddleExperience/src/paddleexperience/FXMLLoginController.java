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
import javafx.scene.Node;
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
        backImg.setImage(new Image(getClass().getResource("/resources/toBack2.png").toExternalForm()));
        String hola = "\"hola\"";
    }    

    @FXML
    private void checkLogIn(ActionEvent event) throws IOException {
        // COMPROVAR
        
        toInitial(event);
    }

    @FXML
    private void toSignIn(MouseEvent event) {
    }
    
    private void toInitial(Event event) throws IOException {
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
    }
}
