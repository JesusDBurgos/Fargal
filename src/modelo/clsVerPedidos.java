/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.clsConexion;
import java.sql.ResultSet;

/**
 *
 * @author Sena CSET
 */
public class clsVerPedidos {
    //variables
    private String numeroPedido;
    private String vendedor;
    private String cliente;
    private String cantidad;
    private String precioTotal;
    private String estado;
    private String fecha;
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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(String precioTotal) {
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
    
    
    //metodos
    
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
        } catch (Exception e) {
        }
    }
}
