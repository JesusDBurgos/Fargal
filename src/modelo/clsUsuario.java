/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Jhonny
 */
import controlador.clsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class clsUsuario {
    //Delaración de variables

    private int Id;
    private String Zona;
    private String Nombre;
    private String Telefono;
    private String Contrasena;

    public ResultSet datos;
    clsConexion conexion = new clsConexion();

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getZona() {
        return Zona;
    }

    public void setZona(String zona) {
        Zona = zona;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String contrasena) {
        Contrasena = contrasena;
    }

    public void login(){
        try {
            conexion.conectar();
            conexion.sql=conexion.con.prepareStatement("SELECT name FROM udemy_delivery.users WHERE zone = ? AND password2 = ? ");
            conexion.sql.setString(1,getZona());
            conexion.sql.setString(2,getContrasena());
            conexion.sql.executeQuery();

            datos = conexion.sql.getResultSet();
             
       } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Lo siento, usuario no existe " +  ex);
       }
   }

    
}