/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author ledin
 */
public class ConsoleView implements ConsoleColors {
    protected String viewName;
    
    public ConsoleView(String viewName) {
        this.viewName = viewName;
    }
    
    public void showMessage(String msg) {
        System.out.println(GREEN + msg + RESET);
    }
    
    public void showError(String msg) {
        System.out.println(RED + msg + RESET);
    }
    
    public void showInfo(String msg) {
        System.out.println(msg);
    }    
}
