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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.GridPane;

import DBAcess.ClubDBAccess;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Booking;
import model.Member;
import paddleexperience.CurrentUser;

/**
 * FXML Controller class
 *
 * @author Gerard
 */
public class FXMLLoggedController implements Initializable {

    @FXML
    private ImageView backDate;
    @FXML
    private Label dateLabel;
    @FXML
    private ImageView forwardDate;
    @FXML
    private DatePicker datePicker;
    @FXML
    private GridPane taula;
    @FXML
    private Button perfil;
    @FXML
    private ImageView imageViewPerfil;
    
    private LocalDate dia;
    private Member member;
    private ClubDBAccess clubDBAccess;
    private ArrayList<Booking> bookForDay;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dia = LocalDate.now();
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        member = CurrentUser.getMembre();
        changeDateLabel();
        datePicker.getEditor().setEditable(false);
        datePicker.getEditor().setVisible(false);
        
        member = CurrentUser.getMembre();
        perfil.setText(member.getLogin());
        imageViewPerfil.setImage(member.getImage());
        
        //agafem la llista de reserves per al dia de hui
        bookForDay = clubDBAccess.getForDayBookings(dia);
        
        //coloquem les reserves en la posició corresponent de la taula
        placeBookings();
        
        
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
        //bucle per vore l'ordre dels fills en la taula
//        for(int i = 1; i < taula.getChildren().size() - 1; i++) {
//            ((Label)taula.getChildren().get(i)).setText("Hola " + i);
//        }
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
        }
    }

    @FXML
    private void nextDay(ActionEvent event) {
        dia = dia.plusDays(1);
        changeDateLabel();
        cleangrid();
        bookForDay = clubDBAccess.getForDayBookings(dia);
        placeBookings();
    }

    @FXML
    private void onProfile(ActionEvent event) throws IOException {
        dateLabel.getScene().setCursor(Cursor.DEFAULT);
        
        //fem que la de user alhora de redimensionar tinga altres mins
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(600);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(730);
        
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLUserInfo.fxml")));
    }
    
//    @FXML
//    private void newBooking(ActionEvent event) {
//        //Booking(LocalDateTime bookingDate, LocalDate madeForDay, LocalTime fromHour, boolean paid, Court court, Member member)
//        //LocalTime lt = ((Label)event.getSource()).getColumnIndex();
//        LocalTime lt = fromRow(taula.getRowIndex((Label)event.getSource()));
//        clubDBAccess.getBookings().add(new Booking(LocalDateTime.now(), dia, lt, member.getCreditCard() != null, clubDBAccess.getCourt("Court 1"), member));
//        
//    }
    
    
    @FXML
    private void newBooking(MouseEvent event) {
        LocalTime lt = fromRow(taula.getRowIndex((Label)event.getSource()));
        if ((dia.compareTo(LocalDate.now()) == 0 && lt.compareTo(LocalTime.now()) > 0) || dia.compareTo(LocalDate.now()) > 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Diàleg de confirmació");
            alert.setHeaderText("Vas a realitzar una reserva");
            alert.setContentText("Vols continuar? Recorda que només pots anul·lar-la amb 24h d'antelació.");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) { 
                
                if(CurrentUser.getMembre().getCreditCard() == null){ //no te targeta, ha de pagar al club
                    alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Confimació");
                    alert.setHeaderText("No tens una targeta vinculada");
                    alert.setContentText("Hauràs de pagar-la al club");
                    alert.show();
                }
                
                clubDBAccess.getBookings().add(new Booking(LocalDateTime.now(), dia, lt, member.getCreditCard() != null, clubDBAccess.getCourt(fromCourt(event)), member));
                ((Label)event.getSource()).setText(member.getLogin());
                bookForDay = clubDBAccess.getForDayBookings(dia);
                
            } else {
                System.out.println("CANCEL");
            }
        }
        else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Diàleg d'error");
            alert.setHeaderText("No pots realitzar aquesta reserva");
            alert.setContentText("No pots reservar una pista anterior a l'hora actual.");
            alert.show();
        }
    }
    
    private LocalTime fromRow(int row) {
        switch (row) {
            case 1:
                return LocalTime.of(9, 0);
                
            case 2:
                return LocalTime.of(10, 30);
                
            case 3:
                return LocalTime.of(12, 0);
                
            case 4:
                return LocalTime.of(13, 30);
                
            case 5:
                return LocalTime.of(15, 0);
                
            case 6:
                return LocalTime.of(16, 30);
                
            case 7:
                return LocalTime.of(18, 0);
                
            case 8:
                return LocalTime.of(19, 30);
                
            case 9:
                return LocalTime.of(21, 0);
        }
        return null;
    }
    
    private String fromCourt(MouseEvent event) {
        String res = "Court ";
        res += taula.getColumnIndex((Label)event.getSource());
        System.out.println(res);
        return res;
    }
    
    @FXML
    private void toMyBookings(ActionEvent event) throws IOException {
        dateLabel.getScene().setCursor(Cursor.DEFAULT);
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLReserves.fxml")));
    }
    
    private void changeDateLabel() {
        dateLabel.setText(dia.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
    }

    @FXML
    private void outLink(MouseEvent event) {
        try {
            dateLabel.getScene().setCursor(Cursor.DEFAULT);
        }
        catch(NullPointerException e){}
    }

    @FXML
    private void onLink(MouseEvent event) {
        try {
            dateLabel.getScene().setCursor(Cursor.HAND);
        }
        catch(NullPointerException e){}
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
    
    private void placeBookings() {
        
        for(Booking b : bookForDay) {
            //FORMULA: POSICIÓ = 12 + (HORA * 4) + PISTA
            
            System.out.println();
            System.out.println("FILA: " + translateCourt(b.getCourt().getName()));
            System.out.println("COLUMNA: " + translateHour(b.getFromTime().getHour()));
            
            ((Label)taula.getChildren().get(13 + ((translateCourt(b.getCourt().getName()) * 9) + translateHour(b.getFromTime().getHour())))).setText(b.getMember().getLogin());
        }
    }
    
    public void cleangrid() {
        for(Booking b : bookForDay) {
            //System.out.println(b.getCourt().getName().equals("Court 4") ? "": "");
            ((Label)taula.getChildren().get(13 + ((translateCourt(b.getCourt().getName()) * 9) + translateHour(b.getFromTime().getHour())))).setText("Lliure");
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