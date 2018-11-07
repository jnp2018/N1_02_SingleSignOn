/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import app.Constant;
import app.LoginClient;
import entity.User;
import java.util.Scanner;

/**
 *
 * @author ledin
 */
public class ClientView extends ConsoleView {
    private int serverPort;
    
    public ClientView(String clientName, int serverPort) {
        super(clientName);
        this.serverPort = serverPort;
    }
    
    public void initLogin(LoginClient client) {
        client.changeServer("localhost", this.serverPort);
        String clientCookie = client.readClientCookie();
        User user = null;
        boolean isServerCentralize = client.checkServerIsCentralize();
        if (isServerCentralize) {
            client.view.showMessage("This server on port "+ serverPort +" is in centralize login system.");
        } else {
            client.view.showError("This server on port "+ serverPort +" is NOT in centralize login system.");
        }
        
        if (clientCookie != null && !clientCookie.equals("") && client.checkCookie(clientCookie)) {
            client.view.showMessage("Session local valid. Login success.");
            actionAfterLoginLocal(client);
        } else {
            client.view.showError("Session local invalid.");
            
            if (isServerCentralize) {
                client.view.showInfo("Trying to checking login session site centralize.");
                client.changeServer("localhost", Constant.serverPublicPort);

                String clientCookieCentralize = client.readClientCookie();
                if (clientCookieCentralize != null && !clientCookieCentralize.equals("") && client.checkCookie(clientCookieCentralize)) {
                    client.view.showMessage("Session centralize valid. Site centralize logined before.");
                    
                    actionAfterLoginedCentralizeBefore(client);
                } else {
                    client.view.showError("Session centralize invalid. Trying to logining to site centralize.");
                    user = enterCredentials(client);
                    if (client.checkCredential(user)) {
                        doSaveLoginSession(client);
                    } else {
                        client.view.showError("Login error with credentials : "+ user.getUsername() +"@"+ user.getPassword());
                    }
                }
            } else {
                client.view.showInfo("Trying to logining to site local.");
                user = enterCredentials(client);
                if (client.checkCredential(user) && client.saveLoginSession()) {
                    client.view.showMessage("Login site local successfully.");

                    client.view.showInfo("Trying to logining to site local.");
                    client.changeServer("localhost", this.serverPort);
                    doSaveLoginSession(client);
                } else {
                    client.view.showError("Login error with credentials : "+ user.getUsername() +"@"+ user.getPassword());
                }
            }
        }
    }
    
    public void actionAfterLoginLocal(LoginClient client) {
        int choice = -1;
        try (Scanner reader = new Scanner(System.in)) {
            while (choice != 0) {
                client.view.showInfo("- Choose action :");
                client.view.showInfo("0. Exit");
                client.view.showInfo("1. Refresh");
                client.view.showInfo("2. Logout this site");
                client.view.showInfo("3. Logout other site");
                client.view.showInfo("4. Logout all site");
                
                choice = 0;
                if(reader.hasNextInt()) {
                   choice = reader.nextInt();
                }

                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        initLogin(client);
                        break;
                    case 2:
                        client.changeServer("localhost", this.serverPort);
                        if (client.logout()) {
                            client.view.showMessage("logout local successfully.");
                            initLogin(client);
                        } else {
                            client.view.showError("logout local error.");
                        }
                        break;
                    case 3:
                        client.changeServer("localhost", Constant.serverPublicPort);
                        client.logout();
                        client.changeServer("localhost", this.serverPort);
                        initLogin(client);
                        break;
                    case 4:
                        client.changeServer("localhost", Constant.serverPublicPort);
                        client.logout();
                        client.changeServer("localhost", this.serverPort);
                        client.logout();
                        initLogin(client);
                        break;
                }
            }
        }
    }
    
    public void actionAfterLoginedCentralizeBefore(LoginClient client) {
        int choice = -1;
        try (Scanner reader = new Scanner(System.in)) {
            while (choice != 0) {
                client.view.showInfo("- Choose action :");
                client.view.showInfo("0. Exit");
                client.view.showInfo("1. Refresh");
                client.view.showInfo("2. Login local using account centralize");
                client.view.showInfo("3. Logout account centralize");
                
                choice = 0;
                if(reader.hasNextInt()) {
                   choice = reader.nextInt();
                }

                switch (choice) {
                    case 0:
                        break;
                    case 1:
                        initLogin(client);
                        break;
                    case 2:
                        client.view.showInfo("Trying to logining to site local.");
                        client.changeServer("localhost", this.serverPort);
                        doSaveLoginSession(client);
                        break;
                    case 3:
                        client.changeServer("localhost", Constant.serverPublicPort);
                        client.logout();
                        client.changeServer("localhost", this.serverPort);
                        initLogin(client);
                        break;
                }
            }
        }
    }
    
    public void doSaveLoginSession(LoginClient client) {
        if (client.saveLoginSession()) {
            client.view.showMessage("Login site local successfully.");

            initLogin(client);
        } else {
            client.view.showError("Save session login site local error.");
        }
    }
    
    public User enterCredentials(LoginClient client) {
        client.view.showInfo("- Enter credentials :");
        String account = null;
        String password = null;
        try (Scanner reader = new Scanner(System.in)) {
            while (account == null || password == null || "".equals(account) || "".equals(password)) {
                if (reader.hasNext()) {
                    account = reader.nextLine();
                    password = reader.nextLine();
                }
            }
        }
        
        return new User(account, password);
    }
}
