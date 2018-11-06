/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverB;

import app.Constant;
import app.LoginServer;
import view.ServerView;

/**
 *
 * @author ledin
 */
public class LoginServerB extends LoginServer {
    public static void main(String args[]) {
        boolean isCentralize = true;
        new LoginServerB("Server B", new ServerView("Server B"), Constant.serverBPort, "ssoserverb", "root", "", isCentralize);
    }
    
    public LoginServerB(String serverName, ServerView view, int serverPort, String dbName, String dbUser, String dbPassword, boolean isCentralize) {
        super(serverName, view, serverPort, dbName, dbUser, dbPassword, isCentralize);
    }
}
