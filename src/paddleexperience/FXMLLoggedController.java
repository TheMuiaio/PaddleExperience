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
    
    private LocalDate dia;

    private Member member;
    
    private ClubDBAccess clubDBAccess;
    @FXML
    private DatePicker datePicker;
    @FXML
    private GridPane taula;
    
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
    }    

    @FXML
    private void previousDay(ActionEvent event) {
        LocalDate aux = dia.minusDays(1);
        if (aux.compareTo(LocalDate.now()) >= 0) {
            dia = aux;
            changeDateLabel();
            bookForDay = clubDBAccess.getForDayBookings(dia);
        }
    }

    @FXML
    private void nextDay(ActionEvent event) {
        dia = dia.plusDays(1);
        changeDateLabel();
        bookForDay = clubDBAccess.getForDayBookings(dia);
    }

    @FXML
    private void onProfile(ActionEvent event) throws IOException {
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
    
    
    
    
    
    //Germarmol modify2
    
    
    
    

    @FXML
    private void newBooking(MouseEvent event) {
        LocalTime lt = fromRow(taula.getRowIndex((Label)event.getSource()));
        clubDBAccess.getBookings().add(new Booking(LocalDateTime.now(), dia, lt, member.getCreditCard() != null, clubDBAccess.getCourt(fromCourt(event)), member));
        ((Label)event.getSource()).setText(member.getLogin());
        bookForDay = clubDBAccess.getForDayBookings(dia);
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
        String res = "court ";
        res += taula.getColumnIndex((Label)event.getSource());
        System.out.println(res);
        return "";
    }
    
    @FXML
    private void toMyBookings(ActionEvent event) throws IOException {
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
        bookForDay = clubDBAccess.getForDayBookings(dia);
    }
    
    private void placeBookings() {
        
        for(Booking b : bookForDay) {
            //FORMULA: POSICIÓ = 12 + (HORA * 4) + PISTA
            ((Label)taula.getChildren().get(12 + ((translateHour(b.getFromTime().getHour()) * 4) + translateCourt(b.getCourt().getName())))).setText(b.getMember().getLogin());
        }
    }
    
    public void cleangrid() {
        for(Booking b : bookForDay) {
            ((Label)taula.getChildren().get(12 + ((translateHour(b.getFromTime().getHour()) * 4) + translateCourt(b.getCourt().getName())))).setText("Lliure");
        }
    }
     
    private int translateHour(int i) {
        switch (i) {
            case 9:
                return 1;
            
            case 10:
                return 2;
            
            case 12: 
                return 3;
            
            case 13:
                return 4;
                
            case 15:
                return 5;
                
            case 16:
                return 6;
                
            case 18:
                return 7;
                
            case 19:
                return 8;
                
            case 21:
                return 9;
        }
        return -1;
    }
    
    private int translateCourt(String name) {
        String substring = name.substring(name.length() - 1);
        return Integer.parseInt(substring);
    }
}