/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import socket_helper.SocketWebServer;
import socket_helper.SocketWebPackage;
import view.ServerView;
import entity.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 *
 * @author ledin
 */
public class LoginServer extends SocketWebServer {
    protected final String tableUser = "user";
    protected final String tableSession = "session";
    protected final String sessionKeyLoginned = "isLogined";
    protected final long timeLoginAlive = 1000*60*60*24; // 1 day
    
    public LoginServer(String serverName, ServerView view, int serverPort, String dbName, String dbUser, String dbPassword, boolean isCentralize) {
        super(serverName, view, serverPort, dbName, dbUser, dbPassword, isCentralize);
    }
    
    public boolean isLogined(String clientId) {
        String query = "SELECT * FROM "+ this.tableSession +" WHERE `client_id` = '"
                + clientId + "' AND `key` = '"+ sessionKeyLoginned +"' AND `value` = 'true' AND "
                + "'"+ new Timestamp(System.currentTimeMillis() - timeLoginAlive) +"' < `expire`;";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            view.showError(e.toString());
        }
        return false;
    }
    
    public boolean checkCredential(User user) {
        String query = "SELECT * FROM "+ this.tableUser +" WHERE username = '"
                + user.getUsername() + "' AND password = '"+ user.getPassword() +"';";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            view.showError(e.toString());
        }
        return false;
    }
    
    public boolean saveSessionLogin(String clientId) {
        Timestamp ctime = new Timestamp(System.currentTimeMillis());
        String query = "INSERT INTO "+ this.dbName +"."+ this.tableSession +" (`client_id`, `key`, `value`, `expire`) VALUES ('"
                + clientId + "', '"+ this.sessionKeyLoginned +"', 'true', '"+ ctime +"')"
                + "ON DUPLICATE KEY UPDATE `expire` = VALUES(expire);";
        try {
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            view.showError(e.toString());
        }
        return false;
    }
    
    public boolean doLogout(String clientId) {
        String query = "DELETE FROM "+ this.tableSession +" WHERE `client_id` = '"+ clientId + "';";
        try {
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
            if (res > 0) {
                return true;
            }
        } catch (Exception e) {
            view.showError(e.toString());
        }
        return false;
    }
    
    @Override
    public void listening() {
        try {
            view.showInfo("Waiting message...");
            Socket clientSocket = this.myServer.accept();
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            
            view.showInfo("Received message.");
            Object obj = ois.readObject();
            if (obj instanceof SocketWebPackage) {
                String clientId = ((SocketWebPackage) obj).getId();
                String name = ((SocketWebPackage) obj).getName();
                Object body = ((SocketWebPackage) obj).getBody();
                String res = "error";
                
                if (name.equals("checkCredential")) {
                    User user = (User) body;
                    
                    if (this.checkCredential(user)) {
                        res = "yes";
                    } else {
                        res = "no";
                    }
                    
                } else if (name.equals("saveLoginSession")) {
                    if (this.saveSessionLogin(clientId)) {
                        res = "yes";
                    } else {
                        res = "no";
                    }
                    
                } else if (name.equals("checkServerIsCentralize")) {
                    if (this.isCentralize) {
                        res = "yes";
                    } else {
                        res = "no";
                    }
                                        
                } else if (name.equals("checkCookie")) {
                    String cookie = (String) body;
                    if (this.isLogined(cookie)) {
                        res = "yes";
                    } else {
                        res = "no";
                    }
                    
                } else if (name.equals("logout")) {
                    if (this.doLogout(clientId)) {
                        res = "yes";
                    } else {
                        res = "no";
                    }
                }

                oos.writeObject(new SocketWebPackage(clientId, name, res));
                view.showMessage("Answered message to "+ clientId +"@"+ name +": "+ res);
                oos.flush();
            }
        } catch (IOException | ClassNotFoundException e) {
            view.showError(e.toString());
        }
    }
}
