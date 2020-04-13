/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author victo
 */
public class FXMLRegisterController implements Initializable {
    
    @FXML
    private Label info;
    @FXML
    private TextField nomField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField telfField;
    @FXML
    private TextField cognomField;
    @FXML
    private Label afegirDalt;
    @FXML
    private Label afegirBaix;
    @FXML
    private ImageView imageView;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordAppears;
    Image avatar;
    @FXML
    private PasswordField repeatedPasswordField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afegirDalt.setOpacity(0);
        afegirBaix.setOpacity(0);
        passwordAppears.setOpacity(0);
        
        // force the field to be numeric only
        telfField.textProperty().addListener((property, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telfField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        
    }    

    @FXML
    private void loginFieldInfo(MouseEvent event) {
        info.setTextFill(Color.BLACK);
        info.setText("Serà el teu nom d'usuari a l'aplicació. No poses espais.");
    }

    private void cardFieldInfo(MouseEvent event) {
        info.setTextFill(Color.BLACK);
        info.setText("Posa els 16 números de la targeta i els 3 del codi de seguretat.");
    }

    @FXML
    private void passwordFieldInfo(MouseEvent event) {
        info.setTextFill(Color.BLACK);
        info.setText("Una combinació de lletres i números. Ha de tenir més de 6 caràcters i no tenir espais al principi i al final.");
    }

    @FXML
    private void accioAcceptar(ActionEvent event) {
        //Comprobe nom i cognom
        if(!nomField.getText().trim().isEmpty() &&
                !cognomField.getText().trim().isEmpty())
        {
            //Comprobe el telèfon
            if(telfField.getText().length() == 9) {
                
                //Comprobe el login
                if(!loginField.getText().trim().isEmpty() &&
                    loginField.getText().trim().replaceAll(" ", "").length() == loginField.getText().length())
                {
                    //Comprobe la contrasenya
                    if(passwordField.getText().trim().length() > 5){
                        
                        //Comprobe que coincidisquen les contrasenyes
                        if(passwordField.getText().equals(repeatedPasswordField.getText())){
                            info.setTextFill(Color.GREEN);
                            info.setText("Tot correcte"); 
                        }
                        
                        
                        else {
                            info.setTextFill(Color.RED);
                            info.setText("Les contrasenyes no coincideixen.");}
                        }  

                    else {
                        info.setTextFill(Color.RED);
                        info.setText("Posa una contrasenya vàlida.");
                    }
                }
                else {
                    info.setTextFill(Color.RED);
                    info.setText("Posa un Login correcte.");
                }
            }
            else {
                info.setTextFill(Color.RED);
                info.setText("Introdueix un número de telèfon vàlid.");
            }
        }
        
        else {
            info.setTextFill(Color.RED);
            info.setText("Emplena el teu nom i cognom.");       
        }
        
    }

    @FXML
    private void avatarSugerenciaDesapareix() {
        afegirDalt.setOpacity(0);
        afegirBaix.setOpacity(0);
    }

    @FXML
    private void avatarSugerenciaApareix() {
        if(avatar == null){
            afegirDalt.setOpacity(1);
            afegirBaix.setOpacity(1);
        }
    }

    @FXML
    private void afegirImatge(MouseEvent event) {
        //aci he de fer que el camp imatge valga el que m'ha dit la persona eixa
        
        avatar = new Image("file:src"+File.separator+"images"+File.separator+"pala.png");
        imageView.imageProperty().setValue(avatar); 
        afegirDalt.setOpacity(0);
        afegirBaix.setOpacity(0);
    }

    @FXML
    private void vorePassword() {
        passwordAppears.setOpacity(1);
        passwordField.setOpacity(0);
        passwordAppears.setText(passwordField.getText());
    }

    @FXML
    private void noVorePassword(MouseEvent event) {
        passwordField.setOpacity(1);
        passwordAppears.setOpacity(0);
    }

    @FXML
    private void infoDisappear(MouseEvent event) {
        info.setText("");
    }

}
