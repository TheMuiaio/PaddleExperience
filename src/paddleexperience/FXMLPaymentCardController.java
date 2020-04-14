/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import DBAcess.ClubDBAccess;
import model.Member;

import paddleexperience.CurrentUser;

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

    private ClubDBAccess clubDBAccess;
    private String creditCard;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        infoLabel.setTextFill(Color.RED);
        infoLabel.setText("");
        
        //Posem que només puguem gastar números
        numberOne.textProperty().addListener((poperty, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberOne.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        numberTwo.textProperty().addListener((poperty, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberTwo.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        numberThree.textProperty().addListener((poperty, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberThree.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        numberFour.textProperty().addListener((poperty, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberFour.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        secretNumber.textProperty().addListener((property, oldValue,newValue) -> {
            if (!newValue.matches("\\d*")) {
                secretNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }    

    @FXML
    private void onBack(MouseEvent event) throws IOException {
        //tenim un problemeta, que es borraran totes les dades introduides abans
        // ja no jjj
        secretNumber.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLRegister.fxml")));
    }

    @FXML
    private void onOmetre(MouseEvent event) throws IOException {
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
    }

    @FXML
    private void onAfegirTargeta(MouseEvent event) throws IOException {
        if(numberOne.getText().length() == 4 && numberTwo.getText().length()== 4 &&
                numberThree.getText().length() == 4 && numberFour.getText().length() == 4 &&
                secretNumber.getText().length() == 3)
        {
            creditCard = numberOne.getText() + numberTwo.getText() + numberThree.getText() + numberFour.getText();
            Member membre = CurrentUser.getMembre();
            membre.setCreditCard(creditCard); //fixa la targeta de crèdit
            membre.setSvc(secretNumber.getText()); //fixa el nombre secret
            //canviem la pantalla al fxml d'usuari
            ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
        }
        else{
            infoLabel.setText("Introdueix correctament la teua targeta.");
        }
    }

    @FXML
    private void outLink(MouseEvent event) {
        secretNumber.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void onLink(MouseEvent event) throws NullPointerException {
        secretNumber.getScene().setCursor(Cursor.HAND);
    }
}
