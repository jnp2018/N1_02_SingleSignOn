/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_helper;

import view.ClientView;
import app.LoginClient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ledin
 */
public class SocketWebClient {
    public Socket mySocket;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    
    public String clientId;
    public String fileCookie;
    public String serverHost;
    public int serverPort;
    public ClientView view;
    
    public SocketWebClient(String clientId, String fileCookie, String serverHost, int serverPort, ClientView view) {
        this.clientId = clientId;
        this.fileCookie = fileCookie;
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.view = view;
    }
    
    public void changeServer(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.fileCookie = makeCookieFileName(serverHost, serverPort);
        
        view.showInfo("- changed server to "+ this.serverHost +":"+ serverPort +" on "+ this.clientId);
    }
    
    static public String makeCookieFileName(String serverHost, int serverPort) {
        return serverHost +"_"+ serverPort +"_cookie.txt";
    }
    
    public void connectServer() {
        try {
            mySocket = new Socket(serverHost, serverPort);
            oos = new ObjectOutputStream(mySocket.getOutputStream());
            ois = new ObjectInputStream(mySocket.getInputStream());
            view.showInfo("- connected to "+ this.serverHost +":"+ serverPort +" on "+ this.clientId);
        } catch (IOException ex) {
            Logger.getLogger(LoginClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnect() {
        try {
            mySocket.close();
            view.showInfo("disconnected server.");
        } catch (IOException ex) {
            Logger.getLogger(LoginClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String readClientCookie() {
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileCookie))) {
            return br.readLine();
        } catch (IOException e) {
            System.err.println("client cookie not found.");
        }
        return "";
    }
    
    public void writeClientCookie(String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileCookie))) {
            bw.write(content);
            view.showInfo("wrote on "+ fileCookie +": "+ content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void clearClientCookie() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileCookie))) {
            bw.write("");
            view.showInfo("cleared client cookie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
