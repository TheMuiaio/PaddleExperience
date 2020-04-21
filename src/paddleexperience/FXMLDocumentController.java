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
import javafx.scene.input.MouseEvent;
import DBAcess.ClubDBAccess;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Booking;

        
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
    
    private ClubDBAccess clubDBAccess;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button signin;
    @FXML
    private GridPane taula;
    
    private ArrayList<Booking> bookForDay;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        dia = LocalDate.now();
        changeDateLabel();
        datePicker.getEditor().setEditable(false);
        datePicker.getEditor().setVisible(false);
        
        //agafem la llista de reserves per al dia de hui
        bookForDay = clubDBAccess.getForDayBookings(dia);
        
        //coloquem les reserves en la posició corresponent de la taula
        placeBookings();
        
        //deshabilitem les caselles anteriors a l'hora actual amb translateHour(b.getFromTime().getHour())
        disablePast(dia);
        
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
    }
    
    
    @FXML
    private void toLogIn(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(630);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(520);
        login.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogin.fxml")));
    }
    
    @FXML
    private void toSignin(ActionEvent event) throws IOException {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(810);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(730);
        login.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLRegister.fxml")));
    }
    
    
    private void disablePast(LocalDate dia) {
        Label pos;
        if (dia.compareTo(LocalDate.now()) == 0) {
            int stop = nextHour(LocalTime.now());
            System.out.println("stop = " + stop);
            int iter = 13;

            while(iter < taula.getChildren().size() - 1) {
                pos = ((Label)taula.getChildren().get(iter));
                
                if(taula.getRowIndex(pos) < stop) {
                    pos.setDisable(true);
                    System.out.println("Disabling " + taula.getRowIndex(pos));
                } 
                iter++;
            }
        } else {
            System.out.println("hola");
            for (int i = 13; i < taula.getChildren().size() - 1; i++) {
                pos = ((Label)taula.getChildren().get(i));
                pos.setDisable(false);
            }
        }
    }
    
    private int nextHour(LocalTime lt) {
        LocalTime[] hores = {LocalTime.of(9, 0), LocalTime.of(10, 30), LocalTime.of(12, 0), LocalTime.of(13, 30), LocalTime.of(15, 0),
                             LocalTime.of(16, 30), LocalTime.of(18, 0), LocalTime.of(19, 30), LocalTime.of(21, 0)};
        
        for (int i = 0; i < hores.length; i++) {
            if(lt.compareTo(hores[i]) <= 0) return i + 1;
        }
        return hores.length + 1;
    }
    
    @FXML
    private void previousDay(ActionEvent event) {
        LocalDate aux = dia.minusDays(1);
        if (aux.compareTo(LocalDate.now()) >= 0) {
            dia = aux;
            changeDateLabel();
            cleangrid();
            bookForDay = clubDBAccess.getForDayBookings(dia);
            placeBookings();
            disablePast(dia);
        }
    }

    @FXML
    private void nextDay(ActionEvent event) {
        dia = dia.plusDays(1);
        changeDateLabel();
        cleangrid();
        bookForDay = clubDBAccess.getForDayBookings(dia);
        placeBookings();
        disablePast(dia);
    }
    
    @FXML
    private void outLink(MouseEvent event) {
        try {
        login.getScene().setCursor(Cursor.DEFAULT);
        } catch (NullPointerException e) { }
    }
    
    @FXML
    private void onLink(MouseEvent event) {
        try {
        login.getScene().setCursor(Cursor.HAND);
        } catch (NullPointerException e) {}
    }
    
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
        cleangrid();
        bookForDay = clubDBAccess.getForDayBookings(dia);
        placeBookings();
    }
    
    private void onBack() throws IOException {
        login.getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
    }
    
    private void placeBookings() {
        
        for(Booking b : bookForDay) {
            //FORMULA: POSICIÓ = (13) + (PISTA * HORA) - 1
            //mentida xd
            //FORMULA . POSICIÓ = 12 + (HORA * 4) + PISTA
            System.out.println();
            System.out.println("FILA: " + translateHour(b.getFromTime().getHour()));
            System.out.println("COLUMNA: " + translateCourt(b.getCourt().getName()));
            
            ((Label)taula.getChildren().get(13 + ((translateHour(b.getFromTime().getHour()) * 4) + translateCourt(b.getCourt().getName())))).setId("reservat");
            ((Label)taula.getChildren().get(13 + ((translateHour(b.getFromTime().getHour()) * 4) + translateCourt(b.getCourt().getName())))).setText(b.getMember().getLogin());
        }
    }
    
    public void cleangrid() {
        for(Booking b : bookForDay) {
            ((Label)taula.getChildren().get(13 + ((translateHour(b.getFromTime().getHour()) * 4) + translateCourt(b.getCourt().getName())))).setId("lliure");
            ((Label)taula.getChildren().get(13 + ((translateHour(b.getFromTime().getHour()) * 4) + translateCourt(b.getCourt().getName())))).setText("Lliure");
        }
    }
    
    private int translateHour(int i) {
        switch (i) {
            case 9:
                return 0;
            
            case 10:
                return 1;
            
            case 12: 
                return 2;
            
            case 13:
                return 3;
                
            case 15:
                return 4;
                
            case 16:
                return 5;
                
            case 18:
                return 6;
                
            case 19:
                return 7;
                
            case 21:
                return 8;
        }
        return -1;
    }
    
    private int translateCourt(String name) {
        String substring = name.substring(name.length() - 1);
        System.out.println(substring);
        return Integer.parseInt(substring) - 1;
    }
}