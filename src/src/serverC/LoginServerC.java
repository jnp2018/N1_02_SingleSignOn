/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverC;

import app.Constant;
import app.LoginServer;
import view.ServerView;

/**
 *
 * @author ledin
 */
public class LoginServerC extends LoginServer {
    public static void main(String args[]) {
        boolean isCentralize = false;
        new LoginServerC("Server C", new ServerView("Server C"), Constant.serverCPort, "ssoserverc", "root", "", isCentralize);
    }
    
    public LoginServerC(String serverName, ServerView view, int serverPort, String dbName, String dbUser, String dbPassword, boolean isCentralize) {
        super(serverName, view, serverPort, dbName, dbUser, dbPassword, isCentralize);
    }
}
