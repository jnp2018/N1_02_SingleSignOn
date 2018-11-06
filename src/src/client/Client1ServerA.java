/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import app.Constant;
import app.LoginClient;
import static socket_helper.SocketWebClient.makeCookieFileName;
import view.ClientView;

/**
 *
 * @author ledin
 */
public class Client1ServerA {
    public static void main(String args[]) {
        int serverPort = Constant.serverAPort;
        LoginClient client1 = new LoginClient("client1", makeCookieFileName("localhost", serverPort), "localhost", serverPort, new ClientView("client1", serverPort));
        client1.view.initLogin(client1);
    }
}
