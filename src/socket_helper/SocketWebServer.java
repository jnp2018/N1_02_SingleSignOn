/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket_helper;

import view.ServerView;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ledin
 */
public abstract class SocketWebServer {
    protected ServerView view;
    protected String serverName;
    protected Connection conn;
    protected ServerSocket myServer;
    protected int serverPort;
    protected boolean isCentralize;
    
    public String dbName;
    protected String dbUser;
    protected String dbPassword;
        
    public SocketWebServer(String serverName, ServerView view, int serverPort, String dbName, String dbUser, String dbPassword, boolean isCentralize) {
        this.view = view;
        this.serverPort = serverPort;
        this.serverName = serverName;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.isCentralize = isCentralize;
        
        getDBConnection();
        openServer();
        showMessageStart();
        while(true)
            listening();
    }
    
    abstract protected void listening();
    
    public void showMessageStart() {
        view.showInfo("Server ("+ this.serverName +") started on port: "+ this.serverPort);
    }
    
    public void openServer() {
        try {
            this.myServer = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            view.showError(e.toString());
        }
    }
    
    public void closeServer() {
        try {
            this.myServer.close();
        } catch (IOException e) {
            view.showError(e.toString());
        }
    }
    
    public void getDBConnection() {
        String dbUrl = "jdbc:mysql://localhost:3306/"+ this.dbName;
        String dbClass = "com.mysql.jdbc.Driver";
        
        try {
            Class.forName(dbClass);
            this.conn = DriverManager.getConnection(dbUrl, this.dbUser, this.dbPassword);
            view.showInfo("Connected db: "+ this.dbName);
        } catch (Exception e) {
            System.out.println("Unable to connect db: "+ dbUrl);
            view.showError(e.toString());
        }
    }
}
