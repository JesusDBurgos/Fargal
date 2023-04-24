/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.clsConexion;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author Sena CSET
 */
public class clsPedido {

   
    private String id;
    private String numeroPedido;
    private String id_vendedor;
    private String vendedor;
    private String id_cliente;
    private String cliente;
    private int cantidad;
    private int precioTotal;
    private String estado;
    private String fecha;
    private String marca;

    public String getProductoSelecccionado() {
        return productoSelecccionado;
    }

    public void setProductoSelecccionado(String productoSelecccionado) {
        this.productoSelecccionado = productoSelecccionado;
    }
    
    private String productoSelecccionado;
    private Array producto;
    
    
    public ResultSet datos;
    
    
    clsConexion objCon =new clsConexion();
    
    
    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(int precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(String id_vendedor) {
        this.id_vendedor = id_vendedor;
    }

    public String getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(String id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Array getProducto() {
        return producto;
    }

    public void setProducto(Array producto) {
        this.producto = producto;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
     public String getId() {
        return id;
    }

    //variables
    public void setId(String id) {
        this.id = id;
    }
    
    //metodos
    
    clsSession session = clsSession.getInstance();      
    String data = session.getData();
    String id_usuario = session.getId_usuario();
    
    public void VerPedidosAdmin(){
        try {
            objCon.conectar();
             objCon.sql=objCon.con.prepareStatement("SELECT\n" +
                                                "		od.id, \n" +
                                                "		od. quantitytotal, \n" +
                                                "        us.name,\n" +
                                                "        cl.name,\n" +
                                                "		od.total,\n" +
                                                "		od.status,\n" +
                                                "		od.created_at\n" +
                                                " FROM orders od  \n" +
                                                "INNER JOIN users us \n" +
                                                "	ON us.id  = od.id_user \n" +
                                                "INNER JOIN clients cl \n" +
                                                "	ON cl.id = od.id_client order by od.id asc");
            objCon.sql.executeQuery();
            datos = objCon.sql.getResultSet(); 
        } catch (SQLException e) {
        }
    }
    
    public void VerPedidosVendedor(){
        try {
            objCon.conectar();
             objCon.sql=objCon.con.prepareStatement("SELECT\n" +
                                                "		od.id, \n" +
                                                "		od. quantitytotal, \n" +
                                                "        us.name,\n" +
                                                "        cl.name,\n" +
                                                "		od.total,\n" +
                                                "		od.status,\n" +
                                                "		od.created_at\n" +
                                                " FROM orders od  \n" +
                                                "INNER JOIN users us \n" +
                                                "	ON us.id  = od.id_user \n" +
                                                "INNER JOIN clients cl \n" +
                                                "	ON cl.id = od.id_client"
                                                + " WHERE us.name = ?"
                                                + " ORDER BY od.id asc");
            objCon.sql.setString(1,data);
            objCon.sql.executeQuery();
            datos = objCon.sql.getResultSet(); 
        } catch (SQLException e) {
        }
    }
    
    public void VerProductos(){
        
        
        try {
            objCon.conectar();
             objCon.sql=objCon.con.prepareStatement("SELECT id, name, price\n" +
                                                    "FROM udemy_delivery.products\n" +
                                                    "WHERE name LIKE '%"+getMarca()+"%'\n" +
                                                    "ORDER BY id DESC;");
     
            
            objCon.sql.executeQuery();
            datos = objCon.sql.getResultSet(); 
        } catch (SQLException e) {
        }
    }
    
    public void Buscar_Id_Precio_Product(){
        
        
        try {
            objCon.conectar();
             objCon.sql=objCon.con.prepareStatement("SELECT id, name ,price\n" +
                                                    "FROM udemy_delivery.products\n" +
                                                    "WHERE name = ? \n" +
                                                    "ORDER BY id DESC;");
     
            objCon.sql.setString(1,getProductoSelecccionado());
            objCon.sql.executeQuery();
            datos = objCon.sql.getResultSet(); 
            
        } catch (SQLException e) {
        }
    }
    
    public void crearPedido(){
         
        try {
            objCon.conectar();
             objCon.sql=objCon.con.prepareStatement("INSERT INTO udemy_delivery.orders\n" +
                                                        "(id_user," +
                                                        "id_client," +
                                                        "quantitytotal," +
                                                        "total," +
                                                        "status," +
                                                        "timestamp," +
                                                        "created_at," +
                                                        "updated_at)" +
                                                        "VALUES" +
                                                        "(?,?,?,?,'PENDIENTE',167448022,'2023-04-19','2023-04-19');");
     
            objCon.sql.setString(1, id_usuario);
            objCon.sql.setString(2, getId_cliente());
            objCon.sql.setInt(3, 3);
            objCon.sql.setInt(4, 250000);
            objCon.sql.executeUpdate();
            datos = objCon.sql.getResultSet(); 
            JOptionPane.showMessageDialog(null,"Se ha creado el pedido");
            
        } catch (SQLException e) {
            System.out.println("excepcion --> " + e);
        }

    }
    
   
    
    public void VerClientesXVendedor(){
        
         clsSession session = clsSession.getInstance();      
         String data = session.getData();
         String id_usuario = session.getId_usuario();
         
        try {
            objCon.conectar();
             objCon.sql=objCon.con.prepareStatement("SELECT \n" +
                                                    "	 id,\n" +
                                                    "	 name,\n" +
                                                    "	 id_user\n" +
                                                    " FROM udemy_delivery.clients\n" +
                                                    " WHERE id_user = ?");
            objCon.sql.setString(1,id_usuario);
            
            objCon.sql.executeQuery();
            datos = objCon.sql.getResultSet(); 
        } catch (SQLException e) {
        }
    }
    
    public void EncontrarIdClienteSeleccionado(){
        
         clsSession session = clsSession.getInstance();      
         String data = session.getData();
         String id_usuario = session.getId_usuario();
             
        try {
            objCon.conectar();
             objCon.sql=objCon.con.prepareStatement("SELECT \n" +
                                                    "	 id,\n" +
                                                    "	 name,\n" +
                                                    "	 id_user\n" +
                                                    " FROM udemy_delivery.clients\n" +
                                                    " WHERE id_user = ? and name = ?");
            objCon.sql.setString(1,id_usuario);
            objCon.sql.setString(2,getCliente());
            
            objCon.sql.executeQuery();
            datos = objCon.sql.getResultSet(); 
        } catch (SQLException e) {
        }
    }
    
    public void EncontrarUltimoIdPedido(){
             System.out.println("entro al max -->" );
        try {
            objCon.conectar();
            objCon.sql=objCon.con.prepareStatement("SELECT max(id) FROM udemy_delivery.orders;");
            objCon.sql.executeQuery();
            datos = objCon.sql.getResultSet(); 
            if(datos.next()){
            setId(datos.getString(1));
            System.out.println("resp sql  max --> " + datos.getString(1));
            }// Asignar el valor del último ID de pedido
        } catch (SQLException e) {
            System.out.println("eerr -->" + e);
        }
    }
}
