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
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

        
/**
 *
 * @author Gerard
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label dateLabel;
    @FXML
    private Button login;
    
    private LocalDate dia;
    
    private boolean resized;
    
    private ClubDBAccess clubDBAccess;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button signin;
    @FXML
    private GridPane taula;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        resized = false;
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        dia = LocalDate.now();
        changeDateLabel();
        datePicker.getEditor().setEditable(false);
        datePicker.getEditor().setVisible(false);
        datePicker.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0);
                }
            };
        });
        
        for (int i = 14; i < taula.getChildren().size() - 1; i++) {
            if (true) {
                System.out.println("hola " + (i - 14));
                ((Label)taula.getChildren().get(i)).setText("hola " + (i - 14));
            }
        }
        
    }    
    
    @FXML
    private void toLogIn(ActionEvent event) throws IOException {
        login.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")));
    }
    
    @FXML
    private void toSignin(ActionEvent event) throws IOException {
        login.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLRegister.fxml")));
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
        } catch (NullPointerException e) {}
    }
    
    //Vaig a canviar tots els que veja, però quan et poses a treballar repassa
    //tots els botons que tinguen canvis en l'aparença del ratolí i afegeix el
    //bloc try-catch als que no ho tinguen jjj
    
    //els que donen error en principi ja està
    
    private void changeDateLabel() {
        String formatted = dia.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        formatted = formatted.replace("\\s", "");
        dateLabel.setText(formatted.replace("/", ", "));
    }

    @FXML
    private void pickDate(ActionEvent event) {
        dia = datePicker.getValue();
        datePicker.hide();
        changeDateLabel();
        System.out.println(dateLabel.getText());
        //CANVIAR EL CONTINGUT DE LES PISTES
    }

    
    //perdó per tindre que fer esto, però és lo que se m'ha ocurrit
    //si ho pose al initialize no funciona, pq com no hi ha capwindow, salta nullpointer
    //necessite el stage per fer minWidth i minHeight
    //el resized es per a fer que nomes actue una vegada
    //funciona ja per a totes les finestres, així que o fem totes les finestres per a un minim de tamany gran
    //o en cada finestra posem este codi (que si, que cada finestra deu estar feta per a q quede guai en tamany gran mimimimi)
    
    //el problema es no podem redimensionar estirant la finestra, només si polsem el botó de maximitzar.
    //mira a vore si conseguim que poguem redimensionar la finestra estirant-la dels costats pls :c
    
    @FXML
    private void redimensionament(MouseEvent event) {
        if(!resized){
            try {
                Stage stage =(Stage)login.getScene().getWindow();
                stage.setMinWidth(stage.getWidth());
                stage.setMinHeight(stage.getHeight());
            }
            catch(NullPointerException e){}
        }
    }

    
    //No tinc ni puta idea de per què no funciona esto de baix, ni per què crea el pickDate. Crec que hi ha porblemes per compartir controlador
    //Era perquè tenia un pickdate onMousePressed i ahi no va. L'he llevat i ja funciona
    @FXML
    private void onBack() throws IOException { //dona error
        login.getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
    }
    
}
