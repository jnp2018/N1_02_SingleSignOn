/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import socket_helper.SocketWebPackage;
import view.ClientView;
import socket_helper.SocketWebClient;
import entity.User;
import java.io.IOException;

/**
 *
 * @author ledin
 */
public class LoginClient extends SocketWebClient {    
    public boolean checkServerIsCentralize() {
        try {
            connectServer();
            
//            send data logout
            oos.writeObject(new SocketWebPackage(clientId, "checkServerIsCentralize", clientId));
            view.showInfo("sent data check server is centralize.");
            
//            receive data
            view.showInfo("waiting for server answer...");
            Object obj = ois.readObject();
            closeConnect();
            view.showInfo("received answer.");
            if (obj instanceof SocketWebPackage) {
                String clientIdRes = ((SocketWebPackage) obj).getId();
                String name = ((SocketWebPackage) obj).getName();
                Object body = ((SocketWebPackage) obj).getBody();
                
                if (clientIdRes.equals(clientId) && name.equals("checkServerIsCentralize")) {
                    String result = (String) body;
                    if (result.equals("yes")) {
                        return true;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            view.showError(e.toString());
        }
        return false;
    }
    
    public boolean logout() {
        try {
            connectServer();
            
//            send data logout
            oos.writeObject(new SocketWebPackage(clientId, "logout", clientId));
            view.showInfo("sent data logout.");
            
//            receive data
            view.showInfo("waiting for server answer...");
            Object obj = ois.readObject();
            closeConnect();
            view.showInfo("received answer.");
            if (obj instanceof SocketWebPackage) {
                String clientIdRes = ((SocketWebPackage) obj).getId();
                String name = ((SocketWebPackage) obj).getName();
                Object body = ((SocketWebPackage) obj).getBody();
                
                if (clientIdRes.equals(clientId) && name.equals("logout")) {
                    String result = (String) body;
                    if (result.equals("yes")) {
                        return true;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            view.showError(e.toString());
        }
        return false;
    }
    
    public boolean checkCookie(String clientCookie) {
        try {
            connectServer();
            
//            send data login
            oos.writeObject(new SocketWebPackage(clientId, "checkCookie", clientCookie));
            view.showInfo("sent data cookie.");
            
//            receive data
            view.showInfo("waiting for server answer...");
            Object obj = ois.readObject();
            closeConnect();
            view.showInfo("received answer.");
            if (obj instanceof SocketWebPackage) {
                String clientIdRes = ((SocketWebPackage) obj).getId();
                String name = ((SocketWebPackage) obj).getName();
                Object body = ((SocketWebPackage) obj).getBody();
                
                if (clientIdRes.equals(clientId) && name.equals("checkCookie")) {
                    String result = (String) body;
                    if (result.equals("yes")) {
                        return true;
                    } else {
                        this.clearClientCookie();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            view.showError(e.toString());
        }
        return false;
    }
    
    public boolean checkCredential(User user) {
        try {
            connectServer();
            
//            send data login
            view.showInfo("sent data credential login.");
            oos.writeObject(new SocketWebPackage(clientId, "checkCredential", user));
            
//            receive data
            view.showInfo("waiting for server answer...");
            Object obj = ois.readObject();
            closeConnect();
            view.showInfo("received answer.");
            
            if (obj instanceof SocketWebPackage) {
                String clientIdRes = ((SocketWebPackage) obj).getId();
                String name = ((SocketWebPackage) obj).getName();
                Object body = ((SocketWebPackage) obj).getBody();
                
                if (clientIdRes.equals(clientId) && name.equals("checkCredential")) {
                    String result = (String) body;
                    if (result.equals("yes")) {
                        return true;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            view.showError(e.toString());
        }
        return false;
    }
    
    public boolean saveLoginSession() {
        try {
            connectServer();
            
//            send data login
            view.showInfo("sent data request save login session.");
            oos.writeObject(new SocketWebPackage(clientId, "saveLoginSession", clientId));
            
//            receive data
            view.showInfo("waiting for server answer...");
            Object obj = ois.readObject();
            closeConnect();
            view.showInfo("received answer.");
            
            if (obj instanceof SocketWebPackage) {
                String clientIdRes = ((SocketWebPackage) obj).getId();
                String name = ((SocketWebPackage) obj).getName();
                Object body = ((SocketWebPackage) obj).getBody();
                
                if (clientIdRes.equals(clientId) && name.equals("saveLoginSession")) {
                    String result = (String) body;
                    if (result.equals("yes")) {
                        writeClientCookie(clientId);
                        return true;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            view.showError(e.toString());
        }
        return false;
    }
        
    public LoginClient(String clientId, String fileCookie, String serverHost, int serverPort, ClientView view) {
        super(clientId, fileCookie, serverHost, serverPort, view);
    }
}
