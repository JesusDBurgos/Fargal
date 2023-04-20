/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Jhonny
 */
public class clsSession {
  private static clsSession instance;
    private String data;

    private clsSession() {}

    public static clsSession getInstance() {
        if (instance == null) {
            instance = new clsSession();
        }
        return instance;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }  
}
