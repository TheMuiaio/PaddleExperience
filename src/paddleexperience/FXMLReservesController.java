/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import javafx.scene.layout.GridPane;
import model.Member;
import paddleexperience.CurrentUser;
import DBAcess.ClubDBAccess;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Booking;

/**
 * FXML Controller class
 *
 * @author victo
 */
public class FXMLReservesController implements Initializable {

    @FXML
    private GridPane taula;
    
    private Member member;
    private ArrayList<Booking> bookForMember;
    private ClubDBAccess clubDBAccess;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        member = CurrentUser.getMembre();
        bookForMember = clubDBAccess.getUserBookings(member.getLogin());
        
        placeBookings();
    }    

    private void placeBookings() {
        String time;
        for(int i = 3; i - 3 < 10 && i - 3 < bookForMember.size(); i++) {
            time = "";
            time += bookForMember.get(i - 3).getMadeForDay().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)) + " ";
            time += bookForMember.get(i - 3).getFromTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            ((Label)taula.getChildren().get(i)).setText(time);
//            ((Label)taula.getChildren().get(i)).setText(bookForMember.get(i - 3).getMadeForDay().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
//            ((Label)taula.getChildren().get(i + 10)).setText(bookForMember.get(i - 3).getFromTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            ((Label)taula.getChildren().get(i + 10)).setText(bookForMember.get(i - 3).getCourt().getName());
            //System.out.println("index = " + i);
        }
    }
    
    @FXML
    private void outLink(MouseEvent event) {
        try{
            ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
        } 
        catch(NullPointerException e){}
    }

    @FXML
    private void onLink(MouseEvent event) {
        try{
            ((Node)event.getSource()).getScene().setCursor(Cursor.HAND);
        }
        catch(NullPointerException e){}
    }

    @FXML
    private void onBack(MouseEvent event) throws IOException {
        ((Node)event.getSource()).getScene().setCursor(Cursor.DEFAULT);
        ((Node)event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLLogged.fxml")));
    }

    @FXML
    private void cancelarReserva(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Diàleg de confirmació");
        alert.setHeaderText("Vas a anul·lar aquesta reserva");
        alert.setContentText("Vols continuar?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //Cancelem la reserva
            System.out.println("index = " + (taula.getRowIndex(((Button)event.getSource())) - 1));
            Booking b = bookForMember.get(taula.getRowIndex(((Button)event.getSource())) - 1);
            clubDBAccess.getBookings().remove(b);
            bookForMember = clubDBAccess.getUserBookings(member.getLogin());
            placeBookings();
            System.out.println("Adeu");
            System.out.println("OK");
        } else {
            //ps no la cancelem 
            System.out.println("CANCEL");
        }
    }
    
}
