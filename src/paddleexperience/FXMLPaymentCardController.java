/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class FXMLPaymentCardController implements Initializable {

    @FXML
    private TextField numberOne;
    @FXML
    private TextField numberTwo;
    @FXML
    private TextField numberThree;
    @FXML
    private TextField numberFour;
    @FXML
    private TextField secretNumber;
    @FXML
    private Label infoLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onHome(MouseEvent event) {
    }

    @FXML
    private void onOmetre(MouseEvent event) {
    }

    @FXML
    private void onAfegirTargeta(MouseEvent event) {
    }
    
    /*cardFieldBig.textProperty().addListener((poperty, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                cardFieldBig.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        cardFieldSmall.textProperty().addListener((property, oldValue,newValue) -> {
            if (!newValue.matches("\\d*")) {
                cardFieldSmall.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });*/
}
