/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paddleexperience;


import DBAcess.ClubDBAccess;
import model.Member;
/**
 *
 * @author Gerard
 */
public class CurrentUser {
    private static ClubDBAccess clubDBAccess;
    private static String user = "";
    private static String password = "";
    
    public static Member getMembre() {
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        return clubDBAccess.getMemberByCredentials(user, password) == null ? null : clubDBAccess.getMemberByCredentials(user, password);
    }
    
    public static void setMembre(String newUser, String newPassword) {
        clubDBAccess = ClubDBAccess.getSingletonClubDBAccess();
        user = newUser;
        password = newPassword;
    }
}
