/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverPublic;

import app.Constant;
import app.LoginServer;
import view.ServerView;

/**
 *
 * @author ledin
 */
public class LoginServerPublic extends LoginServer {
    public static void main(String args[]) {
        boolean isCentralize = true;
        new LoginServerPublic("Server Public", new ServerView("Server Public"), Constant.serverPublicPort, "ssoserverpublic", "root", "", isCentralize);
    }
    
    public LoginServerPublic(String serverName, ServerView view, int serverPort, String dbName, String dbUser, String dbPassword, boolean isCentralize) {
        super(serverName, view, serverPort, dbName, dbUser, dbPassword, isCentralize);
    }
    
//    @Override
//    public void listening() {
//        try {
//            view.showInfo("Waiting message...");
//            Socket clientSocket = this.myServer.accept();
//            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
//            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
//            
//            view.showInfo("Received message.");
//            Object obj = ois.readObject();
//            if (obj instanceof SocketWebPackage) {
//                String clientId = ((SocketWebPackage) obj).getId();
//                String name = ((SocketWebPackage) obj).getName();
//                Object body = ((SocketWebPackage) obj).getBody();
//                String res = "error";
//                
//                if (name.equals("doLogin")) {
//                    Object[] obj_arr = (Object[]) body;
//                    User user = (User) obj_arr[0];
//                    
//                    if (this.doLogin(user) && this.saveSessionLogin(clientId)) {
//                        res = "yes";
//                    } else {
//                        res = "no";
//                    }
//
//                    oos.writeObject(new SocketWebPackage(clientId, name, res));
//                    view.showMessage("Answered message to "+ clientId +"@"+ name +": "+ res);
//                    oos.flush();
//                    
//                } else if (name.equals("checkCookie")) {
//                    String cookie = (String) body;
//                    if (this.isLogined(cookie)) {
//                        res = "yes";
//                    } else {
//                        res = "no";
//                    }
//
//                    oos.writeObject(new SocketWebPackage(clientId, name, res));
//                    view.showMessage("Answered message to "+ clientId +"@"+ name +": "+ res);
//                    oos.flush();
//                
//                } else if (name.equals("logout")) {
//                    if (this.doLogout(clientId)) {
//                        res = "yes";
//                    } else {
//                        res = "no";
//                    }
//
//                    oos.writeObject(new SocketWebPackage(clientId, name, res));
//                    view.showMessage("Answered message to "+ clientId +"@"+ name +": "+ res);
//                    oos.flush();
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            view.showError(e.toString());
//        }
//    }
}
