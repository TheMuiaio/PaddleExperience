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

import DBAcess.ClubDBAccess;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.stage.Stage;

        
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
    
    private LocalDate dia;
    
    private ClubDBAccess clubDBAccess;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        dia = LocalDate.now();
        changeDateLabel();
    }    
    
    @FXML
    private void toLogIn(ActionEvent event) throws IOException {
        login.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")));
    }

    @FXML
    private void previousDay(ActionEvent event) {
        LocalDate aux = dia.minusDays(1);
        if (aux.compareTo(LocalDate.now()) >= 0) {
            dia = aux;
            changeDateLabel();
        }
    }

    @FXML
    private void nextDay(ActionEvent event) {
        dia = dia.plusDays(1);
        changeDateLabel();
    }

    // Assegura't de capturar les excepcions per a no provocar errors raros :c
    @FXML
    private void outLink(MouseEvent event) {
        try {
        login.getScene().setCursor(Cursor.DEFAULT);
        } catch (NullPointerException e) { }
    }

    // Same ací persi
    @FXML
    private void onLink(MouseEvent event) {
        try {
        login.getScene().setCursor(Cursor.HAND);
        } catch (NullPointerException e) { }
    }
    
    //Vaig a canviar tots els que veja, però quan et poses a treballar repassa
    //tots els botons que tinguen canvis en l'aparença del ratolí i afegeix el
    //bloc try-catch als que no ho tinguen jjj
    
    //els que donen error en principi ja està
    
    private void changeDateLabel() {
        String formatted = dia.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        formatted = formatted.replace("/s", "");
        dateLabel.setText(formatted.replace("/", ", "));
    }
}
