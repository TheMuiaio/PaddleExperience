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

    private Member member;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        member = CurrentUser.getMembre();
    }    

    @FXML
    private void previousDay(MouseEvent event) {
    }

    @FXML
    private void nextDay(MouseEvent event) {
    }
    
}
