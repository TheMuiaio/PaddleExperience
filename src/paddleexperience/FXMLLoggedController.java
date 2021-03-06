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
import javafx.scene.paint.Color;
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
    @FXML
    private Label hola;
    
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
        hola.setText("Hola, " + member.getName() + "!");
        
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
    
    private boolean checkHalf(int hour) {
        return hour == 10 || hour == 13 || hour == 16 || hour == 19;
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
    private void onProfile(ActionEvent event) throws IOException {
        dateLabel.getScene().setCursor(Cursor.DEFAULT);
        
        //fem que la de user alhora de redimensionar tinga altres mins
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(600);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(730);
        
        ((Node) event.getSource()).getScene().setRoot(FXMLLoader.load(getClass().getResource("FXMLUserInfo.fxml")));
    }
    
    @FXML
    private void newBooking(MouseEvent event) {
        Label pos = ((Label)event.getSource());
        if (pos.getText().equals("Lliure")) { //comprovem que no hi haja una reserva existent
            LocalTime lt = fromRow(taula.getRowIndex((Label)event.getSource()));
            if ((dia.compareTo(LocalDate.now()) == 0 && lt.compareTo(LocalTime.now()) > 0) || dia.compareTo(LocalDate.now()) > 0) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Diàleg de confirmació");
                alert.setHeaderText("Vas a realitzar una reserva");
                alert.setContentText("Vols continuar? Recorda que només pots anul·lar-la amb 24h d'antelació.");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) { 
                    //per a que canvie el CSS
                    pos.setId("reservatPolsable");
                    
                    if("".equals(CurrentUser.getMembre().getCreditCard())){ //no te targeta, ha de pagar al club
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Confimació");
                        alert.setHeaderText("No tens una targeta vinculada");
                        alert.setContentText("Hauràs de pagar-la al club");
                        alert.show();
                    }
                    
                    //afegim la reserva a la llista total de reserves
                    clubDBAccess.getBookings().add(new Booking(LocalDateTime.now(), dia, lt, member.getCreditCard() != null, clubDBAccess.getCourt(fromCourt(event)), member));
                    //marquem la casella com a reservada
                    pos.setText(member.getLogin());
                    //actualitzem la llista de reserves diària
                    bookForDay = clubDBAccess.getForDayBookings(dia);

                } else {
                    System.out.println("CANCEL");
                }
            } else {
                
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Diàleg d'error");
                alert.setHeaderText("No pots realitzar aquesta reserva");
                alert.setContentText("No pots reservar una pista anterior a l'hora actual.");
                alert.showAndWait();
            }
        } else if (pos.getText().equals(member.getLogin())) {
            Booking b = searchBooking(fromRow(taula.getRowIndex(pos)), fromCourt(event));
            LocalDate ld = LocalDate.now();
            LocalTime lt = LocalTime.now();
            
            if (ld.compareTo(b.getMadeForDay()) == 0) {
            //NO ES POT PERQUÈ RESERVA I DATA SÓN EL MATEIX DIA
            } else if (ld.compareTo(b.getMadeForDay().minusDays(1)) == 0 && lt.compareTo(b.getFromTime().minusHours(24)) > 0) {
            //NO ES POT PERQUÈ HI HA MENYS DE 24H ENTRE ARA I L'HORA DE RESERVA
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Diàleg de confirmació");
                alert.setHeaderText("Cancel·lar reserva");
                alert.setContentText("Vols cancel·lar la teua reserva?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    clubDBAccess.getBookings().remove(b);
                    bookForDay = clubDBAccess.getForDayBookings(dia);
                    pos.setId("lliurePolsable");
                    pos.setText("Lliure");
                    
                    placeBookings();
                }
            }
        }
        
    }
    
    private Booking searchBooking(LocalTime hora, String nom) {
        for (Booking b : bookForDay) {
            if (b.getFromTime().equals(hora) && b.getCourt().getName().equals(nom)) {
                return b;
            }
        }
        return null;
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
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinWidth(1100);
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).setMinHeight(800);
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
            if(((Label)event.getSource()).getText().equals(member.getLogin()) || ((Label)event.getSource()).getText().equals("Lliure"))
            {
                dateLabel.getScene().setCursor(Cursor.HAND);
            } 
            else dateLabel.getScene().setCursor(Cursor.DEFAULT);
        }
        catch(Exception e) {}
    }

    @FXML
    private void pickDate(ActionEvent event) {
        dia = datePicker.getValue();
        datePicker.hide();
        changeDateLabel();
        System.out.println(dateLabel.getText());
        cleangrid();
        bookForDay = clubDBAccess.getForDayBookings(dia);
        placeBookings();
        disablePast(dia);
    }
    
    private void placeBookings() {
        Label pos;
        
        for(Booking b : bookForDay) {
            //FORMULA: POSICIÓ = 13 + (PISTA * 9) + HORA
            
            System.out.println();
            System.out.println("FILA: " + translateCourt(b.getCourt().getName()));
            System.out.println("COLUMNA: " + translateHour(b.getFromTime().getHour()));
            
            //apliquem funció
            pos = ((Label)taula.getChildren().get(13 + ((translateCourt(b.getCourt().getName()) * 9) + translateHour(b.getFromTime().getHour()))));
            pos.setText(b.getMember().getLogin());
            
            //id de CSS
            pos.setId(pos.getText() == member.getLogin() ? "reservatPolsable" : "reservat");
            
//            if(((Label)taula.getChildren().get(13 + ((translateCourt(b.getCourt().getName()) * 9) + translateHour(b.getFromTime().getHour())))).getText().equals(member.getLogin())){
//                ((Label)taula.getChildren().get(13 + ((translateCourt(b.getCourt().getName()) * 9) + translateHour(b.getFromTime().getHour())))).setId("reservatPolsable");
//            }
//            else {
//                ((Label)taula.getChildren().get(13 + ((translateCourt(b.getCourt().getName()) * 9) + translateHour(b.getFromTime().getHour())))).setId("reservat");
//            }
            
        }
    }
    
    public void cleangrid() {
        Label pos;
        for(Booking b : bookForDay) {
            //System.out.println(b.getCourt().getName().equals("Court 4") ? "": "");
            pos = ((Label)taula.getChildren().get(13 + ((translateCourt(b.getCourt().getName()) * 9) + translateHour(b.getFromTime().getHour()))));
            //Text de la label
            pos.setText("Lliure");
            //id de CSS
            pos.setId("lliurePolsable");
//            ((Label)taula.getChildren().get(13 + ((translateCourt(b.getCourt().getName()) * 9) + translateHour(b.getFromTime().getHour())))).setId("lliurePolsable");
//            ((Label)taula.getChildren().get(13 + ((translateCourt(b.getCourt().getName()) * 9) + translateHour(b.getFromTime().getHour())))).setText("Lliure");
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
        return translateHour(i - 1);
    }
    
    private int translateCourt(String name) {
        String substring = name.substring(name.length() - 1);
        System.out.println(substring);
        return Integer.parseInt(substring) - 1;
    }
}