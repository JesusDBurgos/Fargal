/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.clsConexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Sena CSET
 */
public class clsDetalle {
    //Declaracion de variables
    private String tokenUser;
    private String order;

    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice_unit() {
        return price_unit;
    }

    public void setPrice_unit(String price_unit) {
        this.price_unit = price_unit;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    private String estado;
    private String id_order;
    private String id_product;
    private String quantity;
    private String price_unit;
    private String total;
    public ResultSet datos;
    clsConexion objCon = new clsConexion();
    //Encapsulacion

    public String getTokenUser() {
        return tokenUser;
    }

    public void setTokenUser(String tokenUser) {
        this.tokenUser = tokenUser;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    
    //Declaración de métodos
    
    public void llenarDatosDetalle(){
         try {
            objCon.conectar();
            objCon.sql=objCon.con.prepareStatement("SELECT orders.id,"
                                                + " orders.updated_at, "
                                                + " clients.name,"
                                                + " users.name,"
                                                + " orders.status,"
                                                + " orders.total "
                                                + "FROM orders"
                                                + " INNER JOIN clients "
                                                + "ON orders.id_client = clients.id "
                                                + "INNER JOIN users"
                                                + " ON orders.id_user = users.id "
                                                + "WHERE orders.id = ?");
            objCon.sql.setString(1, getOrder());
            objCon.sql.executeQuery();
            datos = objCon.sql.getResultSet();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar" +  ex);
        }
    }
    public void llenarTablaDetalle(){
        try {
            objCon.conectar();
            objCon.sql=objCon.con.prepareStatement("select products.name, "
                                                + "order_has_products.quantity, "
                                                + "order_has_products.total "
                                                + "from order_has_products "
                                                + "INNER JOIN products "
                                                + "ON order_has_products.id_product = products.id "
                                                + " and id_order=?");
            objCon.sql.setString(1, getOrder());
            objCon.sql.executeQuery();
            datos = objCon.sql.getResultSet();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar" +  ex);
        }
    }
    public void editarEstado(){
        try {
            objCon.conectar();
            objCon.sql=objCon.con.prepareStatement("UPDATE orders SET orders.status=? WHERE id=?");
            objCon.sql.setString(2, getOrder());
            objCon.sql.setString(1, getEstado());
            objCon.sql.executeUpdate();
            JOptionPane.showMessageDialog(null, "Actualización de estado exitosa");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar" +  ex);
        }
       
    }
    
     public void insertarDetallePedido(String id_order,String id_product,int cantidad,double precio, double total){
         //clsCrearPedidos pedido = new clsCrearPedidos();
         //clsProducto producto = new clsProducto();
        try {
            objCon.conectar();
             objCon.sql=objCon.con.prepareStatement("INSERT INTO " +
                                            "udemy_delivery.order_has_products " +
                                            "(id_order," +
                                            "id_product," +
                                            "quantity," +
                                            "price_unit," +
                                            "total," +
                                            "created_at," +
                                            "updated_at)  " +
                                            "VALUES(?, ?, ?, ?, ?, '2023-04-19', '2023-04-19')");
     
            objCon.sql.setString(1, id_order);
            objCon.sql.setString(2, id_product);
            objCon.sql.setInt(3, cantidad);
            objCon.sql.setDouble(4, precio);
            objCon.sql.setDouble(5, total);
            //objCon.sql.setString(5, getEmail());
            objCon.sql.executeUpdate(); 
        } catch (SQLException e) {
            System.out.println("excepcion --> " + e);
        }
        
        
    }
}
