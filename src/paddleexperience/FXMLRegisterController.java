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
import java.util.Optional;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    @FXML
    private ImageView eye;
    @FXML
    private Button botoAcceptar;
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
    
    
    private File avatar;
    private ClubDBAccess clubDBAccess;
    private Member member;
    
    
    private boolean comprovacioNom;
    private boolean comprovacioCognom;
    private boolean comprovacioTelf;
    private boolean comprovacioLogin;
    private boolean comprovacioPssw;
    private boolean comprovacioPssw2;
    
    private boolean comprovacioCreditOne;
    private boolean comprovacioCreditTwo;
    private boolean comprovacioCreditThree;
    private boolean comprovacioCreditFour;
    private boolean comprovacioCreditSecret;
    
    private boolean comprovacioBuitCreditOne;
    private boolean comprovacioBuitCreditTwo;
    private boolean comprovacioBuitCreditThree;
    private boolean comprovacioBuitCreditFour;
    private boolean comprovacioBuitCreditSecret;
    
    private boolean comprovacioCredit;
        
    
    private String creditCard;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        
        botoAcceptar.setDisable(true);
        
        
        //listeners camps obligatoris
        
        nomField.textProperty().addListener((property, oldValue, newValue) -> {
            comprovacioNom = !newValue.isEmpty();
            if(!comprovacioNom){
                info.setTextFill(Color.RED);
                info.setText("No deixes en blanc el teu nom");
            }
            else info.setText("");
            
            comprovacions();
        });
        
        
        cognomField.textProperty().addListener((property, oldValue, newValue) -> {
            comprovacioCognom = !newValue.isEmpty();
            if(!comprovacioCognom){
                info.setTextFill(Color.RED);
                info.setText("No deixes en blanc el teu cognom.");
            }
            else info.setText("");
            
            comprovacions();
        });
        
        
        telfField.textProperty().addListener((property, oldValue, newValue) -> {
            
           comprovacioTelf = newValue.length() == 9;
           if(!comprovacioTelf){
                info.setTextFill(Color.RED);
                info.setText("Posa un número de telèfon vàlid.");
            }
           else info.setText("");
           
           //nomes numeros
           if (!newValue.matches("\\d*")) {
                telfField.setText(newValue.replaceAll("[^\\d]", ""));
            }
           
           comprovacions();
        });
        
        
        loginField.textProperty().addListener((property, oldValue, newValue) -> {
            comprovacioLogin = !newValue.isEmpty() && !clubDBAccess.existsLogin(newValue);
            //mirem si ja existeix algun membre amb eixe login
            if(clubDBAccess.existsLogin(newValue)){
                info.setTextFill(Color.RED);
                info.setText("Login en ús. Elegeix altre.");
            }  
            else if(!comprovacioLogin){
                info.setTextFill(Color.RED);
                info.setText("Emplena el teu Login.");
            }
            else{
                info.setTextFill(Color.GREEN);
                info.setText("Login disponible.");
            }
            
            comprovacions();
        });
        
        
        passwordField.textProperty().addListener((property, oldValue, newValue) -> {
            comprovacioPssw = newValue.length() > 5;
            if(!comprovacioPssw){
                info.setTextFill(Color.RED);
                info.setText("Posa una contrassenya vàlida.");
            }
            else info.setText("");
            
            comprovacions();
        });
        
        
        repeatedPasswordField.textProperty().addListener((property, oldValue, newValue) -> {
            comprovacioPssw2 = newValue.equals(passwordField.getText());
            
            if(!comprovacioPssw2){
                info.setTextFill(Color.RED);
                info.setText("Les contrassenyes no coincideixen.");
            }
            else info.setText("");
            
            comprovacions();
        });
        
        
        
        //Listeners targeta de crèdit
        numberOne.textProperty().addListener((poperty, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberOne.setText(newValue.replaceAll("[^\\d]", ""));
            }
            
            comprovacioCreditOne = newValue.length() == 4;
            comprovacioBuitCreditOne = newValue.isEmpty();
            
            if(!comprovacionsCredit()){
                info.setTextFill(Color.RED);
                info.setText("Introdueix correctament la targeta de crèdit.");
            }
            else info.setText("");
            comprovacions();
        });
        
        numberTwo.textProperty().addListener((poperty, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberTwo.setText(newValue.replaceAll("[^\\d]", ""));
            }
            
            comprovacioCreditTwo = newValue.length() == 4;
            comprovacioBuitCreditTwo = newValue.isEmpty();
            
            if(!comprovacionsCredit()){
                info.setTextFill(Color.RED);
                info.setText("Introdueix correctament la targeta de crèdit.");
            }
            else info.setText("");
            comprovacions();
        });
        
        numberThree.textProperty().addListener((poperty, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberThree.setText(newValue.replaceAll("[^\\d]", ""));
            }
            comprovacioCreditThree = newValue.length() == 4;
            comprovacioBuitCreditThree = newValue.isEmpty();
            
            if(!comprovacionsCredit()){
                info.setTextFill(Color.RED);
                info.setText("Introdueix correctament la targeta de crèdit.");
            }
            else info.setText("");
            System.out.println(comprovacioCreditThree + " " + comprovacioBuitCreditThree + " " + comprovacioCredit);
            comprovacions();
        });
        
        numberFour.textProperty().addListener((poperty, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberFour.setText(newValue.replaceAll("[^\\d]", ""));
            }
            comprovacioCreditFour = newValue.length() == 4;
            comprovacioBuitCreditFour = newValue.isEmpty();
            
            if(!comprovacionsCredit()){
                info.setTextFill(Color.RED);
                info.setText("Introdueix correctament la targeta de crèdit.");
            }
            else info.setText("");
            comprovacions();
        });
        
        secretNumber.textProperty().addListener((property, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                secretNumber.setText(newValue.replaceAll("[^\\d]", ""));
            }
            comprovacioCreditSecret = newValue.length() == 3;
            comprovacioBuitCreditSecret = newValue.isEmpty();
            
            if(!comprovacionsCredit()){
                info.setTextFill(Color.RED);
                info.setText("Introdueix correctament la targeta de crèdit.");
            }
            else info.setText("");
            comprovacions();
        });
        
        
        passwordAppears.visibleProperty().bind(eye.pressedProperty());
        passwordAppears.textProperty().bind(passwordField.textProperty());
        
        info.setText("");
        
    }    
    
    //Per a posar el button disable o not
    private void comprovacions(){
        comprovacioCredit = (comprovacioCreditOne && comprovacioCreditTwo && comprovacioCreditThree && comprovacioCreditFour && comprovacioCreditSecret) ||
                            (comprovacioBuitCreditOne && comprovacioBuitCreditTwo && comprovacioBuitCreditThree && comprovacioBuitCreditFour && comprovacioBuitCreditSecret);
        
        botoAcceptar.setDisable(!(comprovacioNom && comprovacioCognom && comprovacioLogin && comprovacioTelf && comprovacioPssw && comprovacioPssw2 && comprovacioCredit));
    }
    
    
    private boolean comprovacionsCredit(){
        return comprovacioCredit = (comprovacioCreditOne && comprovacioCreditTwo && comprovacioCreditThree && comprovacioCreditFour && comprovacioCreditSecret) ||
                            (comprovacioBuitCreditOne && comprovacioBuitCreditTwo && comprovacioBuitCreditThree && comprovacioBuitCreditFour && comprovacioBuitCreditSecret);
    }
    
    @FXML
    private void loginFieldInfo(MouseEvent event) {
        info.setTextFill(Color.WHITESMOKE);
        info.setText("Serà el teu nom d'usuari a l'aplicació. No poses espais.");
    }

    @FXML
    private void passwordFieldInfo(MouseEvent event) {
        info.setTextFill(Color.WHITESMOKE);
        info.setText("Una combinació de lletres i números amb més de 6 caràcters.");
    }

    @FXML
    private void accioAcceptar(ActionEvent event) throws InterruptedException, IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diàleg de confirmació");
        alert.setHeaderText("Estàs segur de registrar-te?");
        alert.setContentText("No podràs canviar les dades més tard");
        Optional<ButtonType> result = alert.showAndWait();
            
        if (result.isPresent() && result.get() == ButtonType.OK) {    
            if (member == null) {// NO EXISTEIX EL MEMBRE I HEM DE CREAR UN DE NOU
                //creem el nou membre
                member = new Member();
                //afegim les dades introduides
                member.setName(nomField.getText());
                member.setSurname(cognomField.getText());
                member.setTelephone(telfField.getText());
                member.setLogin(loginField.getText());
                member.setPassword(passwordField.getText());
                //de fet, no fa falta comprovar si image de imageView és null perque
                //tens user.png. Passe el que passe l'afegirem
                member.setImage(imageView.getImage());
                //guardem el membre en la llista d'usuaris
                creditCard = numberOne.getText() + numberTwo.getText() + numberThree.getText() + numberFour.getText();
                member.setCreditCard(creditCard); //fixa la targeta de crèdit
                member.setSvc(secretNumber.getText()); //fixa el nombre secret
                clubDBAccess.getMembers().add(member);
            }
            
        
            CurrentUser.setMembre(loginField.getText(), passwordField.getText());
            //canviem la finestra al formulari de targeta de crèdit per acabar amb el registre
            ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
        }
        
        /*
        
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
                            
                            
                            //TOT CORRECTE
                            
                            
                            
                            if (member == null) {// NO EXISTEIX EL MEMBRE I HEM DE CREAR UN DE NOU
                                //creem el nou membre
                                member = new Member();
                                //afegim les dades introduides
                                member.setName(nomField.getText());
                                member.setSurname(cognomField.getText());
                                member.setTelephone(telfField.getText());
                                member.setLogin(loginField.getText());
                                member.setPassword(passwordField.getText());
                                //de fet, no fa falta comprovar si image de imageView és null perque
                                //tens user.png. Passe el que passe l'afegirem
                                member.setImage(imageView.getImage());
                                //guardem el membre en la llista d'usuaris
                                clubDBAccess.getMembers().add(member);
                                
                            } else {// EXISTEIX EL MEMBRE I L'HEM DE MODIFICAR
                                CurrentUser.getMembre().setName(nomField.getText());
                                //member.setName(nomField.getText());
                                CurrentUser.getMembre().setSurname(cognomField.getText());
                                //member.setSurname(cognomField.getText());
                                CurrentUser.getMembre().setTelephone(telfField.getText());
                                //member.setTelephone(telfField.getText());
                                CurrentUser.getMembre().setLogin(loginField.getText());
                                //member.setLogin(loginField.getText());
                                CurrentUser.getMembre().setPassword(passwordField.getText());
                                //member.setPassword(passwordField.getText());
                                //de fet, no fa falta comprovar si image de imageView és null perque
                                //tens user.png. Passe el que passe l'afegirem
                                CurrentUser.getMembre().setImage(imageView.getImage());
                                //member.setImage(imageView.getImage());
                            }
                            CurrentUser.setMembre(loginField.getText(), passwordField.getText());
                            //canviem la finestra al formulari de targeta de crèdit per acabar amb el registre
                            ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
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
        */
    }



    @FXML
    private void afegirImatge(MouseEvent event) throws MalformedURLException {
        //aci he de fer que el camp imatge valga el que m'ha dit l'usuari
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
    private void onBack(MouseEvent event) throws IOException {
        try{
            info.getScene().setCursor(Cursor.DEFAULT);
            ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
        }
        catch(NullPointerException e){}
    }

    @FXML
    private void outLink() {
        try{
            info.getScene().setCursor(Cursor.DEFAULT);
        }
        catch(NullPointerException e){}
    }

    @FXML
    private void onLink() {
        try{
            info.getScene().setCursor(Cursor.HAND);
        } 
        catch(NullPointerException e){}
    }

    @FXML
    private void infoDisappear(MouseEvent event) {
        info.setText("");
    }

    @FXML
    private void avatarSugerenciaDesapareix(MouseEvent event) {
        try{
            info.getScene().setCursor(Cursor.DEFAULT);
            imageView.setOpacity(1);
        }
        catch(NullPointerException e){}
    }

    @FXML
    private void avatarSugerenciaApareix(MouseEvent event) {
        try{
            info.getScene().setCursor(Cursor.HAND);
            imageView.setOpacity(0.4);
        }
        catch(NullPointerException e){}
    }

}
