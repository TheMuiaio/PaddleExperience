/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

/*import DBAcess.ClubDBAccess;*/
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import DBAcess.ClubDBAccess;
import model.Member;

import paddleexperience.CurrentUser;
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
    private ImageView imageView;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField passwordAppears;
    @FXML
    private PasswordField repeatedPasswordField;
    @FXML
    private GridPane gridAvatar;
    
    
    private File avatar;
    
    private ClubDBAccess clubDBAccess;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        passwordAppears.setOpacity(0);
        info.setText("");
        
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

    @FXML
    private void passwordFieldInfo(MouseEvent event) {
        info.setTextFill(Color.BLACK);
        info.setText("Una combinació de lletres i números amb més de 6 caràcters.");
    }

    @FXML
    private void accioAcceptar(ActionEvent event) throws InterruptedException, IOException {
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
                            //estes dos línies no va a donar-nos temps de vore-les en pantalla
                            info.setTextFill(Color.GREEN);
                            info.setText("Tot correcte");
                            //creem el nou membre
                            Member member = new Member();
                            //afegim les dades introduides
                            member.setName(nomField.getText());
                            member.setSurname(cognomField.getText());
                            member.setTelephone(telfField.getText());
                            member.setLogin(loginField.getText());
                            member.setPassword(passwordField.getText());
                            if (imageView.getImage() != null) member.setImage(imageView.getImage());
                            //guardem el membre en la llista d'usuaris
                            clubDBAccess.getMembers().add(member);
                            //inicialitzem l'usuari actual amb el nou membre
                            CurrentUser.setMembre(loginField.getText(), passwordField.getText());
                            //canviem la finestra al formulari de targeta de crèdit per acabar amb el registre
                            ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLPaymentCard.fxml")));
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
    private void afegirImatge(MouseEvent event) throws MalformedURLException {
        //aci he de fer que el camp imatge valga el que m'ha dit la persona eixa
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecciona una imatge");
        avatar = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files","*.bmp", "*.png", "*.jpg", "*.gif"));
        if(avatar != null){
            String path = avatar.toURI().toURL().toString();
            Image image = new Image(path);
            imageView.setImage(image);
            gridAvatar.setGridLinesVisible(false);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Diàleg d'informació");
            alert.setHeaderText("No has elegit cap imatge.");
            alert.setContentText("Selecciona una imatge.");
            alert.showAndWait();
        }
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
    private void onBack(MouseEvent event) throws IOException {
        info.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")));
    }

    @FXML
    private void outLink() {
        info.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void onLink() throws NullPointerException{
        info.getScene().setCursor(Cursor.HAND);
    }

    @FXML
    private void infoDisappear(MouseEvent event) {
    }

    @FXML
    private void avatarSugerenciaDesapareix(MouseEvent event) {
        info.getScene().setCursor(Cursor.DEFAULT);
        imageView.setOpacity(1);
    }

    @FXML
    private void avatarSugerenciaApareix(MouseEvent event) {
        info.getScene().setCursor(Cursor.HAND);
        imageView.setOpacity(0.4);
    }

}
