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
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import DBAcess.ClubDBAccess;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
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
    private TableView<?> pista1;
    @FXML
    private TableView<?> pista2;
    @FXML
    private TableView<?> pista3;
    @FXML
    private TableView<?> pista4;
    
    private LocalDate dia;

    private Member member;
    
    private ClubDBAccess clubDBAccess;
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

    @FXML
    private void onProfile(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLUserInfo.fxml")));
    }

    @FXML
    private void newBooking(ActionEvent event) {
        DatePicker dp = new DatePicker();
        dp.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0);
                }
            };
        });
        clubDBAccess.getBookings().add(new Booking(LocalDateTime.now(), dp.getValue(), LocalTime.now(), member.getCreditCard() != null, clubDBAccess.getCourt("Court 1"), member));
        //clubDBAccess.getBookings().add()
        
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
    private void pickDate(MouseEvent event) {
        DatePicker dp = new DatePicker();
        dp.setDayCellFactory((DatePicker picker) -> {
            return new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate today = LocalDate.now();
                    setDisable(empty || date.compareTo(today) < 0);
                }
            };
        });
        dp.show();
        dia = dp.getValue();
        changeDateLabel();
        //CANVIAR EL CONTINGUT DE LES PISTES
    }
    
}
