/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.clsConexion;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.clsPedido;
import modelo.clsDetalle;
import modelo.clsProducto;

/**
 *
 * @author Sena CSET
 */
public class frmCrearPedidoVend extends javax.swing.JFrame {

    /**
     * Creates new form frmCrearPedidoVend
     */
    public frmCrearPedidoVend() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.creartabla();
        this.llenarCliente();

    }
   
    clsPedido create = new clsPedido();
    clsDetalle detalle = new clsDetalle();
    clsConexion objCon =new clsConexion();
    
    ArrayList<clsProducto> productos = new ArrayList<clsProducto>();
    
    private int cantidadProducto;
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
    String id_product;

    DefaultTableModel tabladatos;

    public void llamarRegresar() {
        frmInicioVendedor objIn = new frmInicioVendedor();
        objIn.setVisible(true);
        this.setVisible(false);
    }

    public void creartabla() {
    Object modelodata[][] = new Object[0][0];
    Object modelotitulos[] = {"N° Producto", "Producto", "Cantidad", "Precio Unitario", "Precio Total"};
    tabladatos = new DefaultTableModel();
    tabladatos = new DefaultTableModel(modelodata, modelotitulos) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

        @Override
        public Class getColumnClass(int column) {
            return getValueAt(0, column).getClass();
        }
    };

    this.tblProductos.setModel(this.tabladatos);
}


    public void borrartabla() {
        while (0 < this.tblProductos.getRowCount()) {
            tblProductos.setModel(new DefaultTableModel());
            this.creartabla();
        }
    }
      
public void agregarFila() {
    
    
    boolean validacion = true;
    if (cboMarca.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(null, "Seleccione una marca");
        validacion = false;
    }
    if (cboProducto.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(null, "Seleccione un producto");
        validacion = false;
    }
    if ((int) spnCantidad.getValue() <= 0) {
        JOptionPane.showMessageDialog(null, "Ingrese una cantidad válida");
        validacion = false;
    }
    if (validacion) {
        try {
          
           
            create.setProductoSelecccionado(cboProducto.getSelectedItem().toString());
            create.setCliente(cboCliente.getSelectedItem().toString());
            
            //busco el ID del cliente
            create.EncontrarIdClienteSeleccionado();
            while (create.datos.next() == true) {
                create.setId_cliente(create.datos.getString(1));//Setear id_cliente
            }
            //Busco el ID y precio del producto
            create.Buscar_Id_Precio_Product();
            while (create.datos.next() == true) {
                
                cantidadProducto = Integer.parseInt(spnCantidad.getValue().toString());
                String precioUnitario = format.format(create.datos.getInt(3));
                String precioTotal = format.format(create.datos.getInt(3) * cantidadProducto);

                Object[] fila = new Object[5];
                fila[0] = create.datos.getString(1);
                fila[1] = cboProducto.getSelectedItem().toString();
                fila[2] = spnCantidad.getValue();
                fila[3] = precioUnitario;
                fila[4] = precioTotal;
                tabladatos.addRow(fila);
                
                productos.add(new clsProducto(create.datos.getString(1),
                        cboProducto.getSelectedItem().toString(),
                        create.datos.getInt(3),
                        cantidadProducto,
                        create.datos.getInt(3) * cantidadProducto));
            }
                
            limpiar();
             //System.out.print(create.getProductoSelecccionado());
             
             
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se ha podido mostrar, lo siento" + e);
        }
    }
    
    
}

    public void listarProductos(){
        ArrayList<clsProducto> productos = new ArrayList<clsProducto>();
        
        for (clsProducto producto : productos) {
                System.out.println("id del producto: " + producto.getId());
                System.out.println("Nombre del producto: " + producto.getNombre());
                System.out.println("Precio del producto: " + producto.getPrecio());
                System.out.println("Cantidad del producto: " + producto.getCantidad());
                System.out.println("Total del producto: " + producto.getTotal());
            }
    }
    public void borrarFila() {
        int a = tblProductos.getSelectedRow();
        if (a < 0) {

            JOptionPane.showMessageDialog(null,
                    "Debe seleccionar una fila de la tabla");

        } else {
            JOptionPane.showMessageDialog(null, "Producto Eliminado");
            tabladatos.removeRow(a);
        }
    }

    public void limpiar() {
        cboMarca.setSelectedIndex(0);
        cboProducto.removeAllItems();
        cboProducto.addItem("Seleccionar...");
        cboProducto.setSelectedItem("Seleccionar...");
        spnCantidad.setValue(0);
    }

    public void limpiarcb() {
        if (cboProducto.getSelectedIndex() != 0) {
            cboProducto.setSelectedIndex(0);
        }
        cboProducto.removeAllItems();
        cboProducto.addItem("Seleccionar");

    }

    public void llenarProducto() {
        try {
            create.setMarca(cboMarca.getSelectedItem().toString());
            create.VerProductos();
            while (create.datos.next() == true) {
                cboProducto.addItem(create.datos.getString(2));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error " + e);
        }
    }
    

    public void llenarCliente() {
        try {
          
            
            // create.setMarca(cboMarca.getSelectedItem().toString());
            create.VerClientesXVendedor();
            while (create.datos.next() == true) {
                
                cboCliente.addItem(create.datos.getString(2));
               
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error " + e);
        }
    }

    public void Buscar_Id_Precio_Producto() {
        try {
            create.setProductoSelecccionado(cboProducto.getSelectedItem().toString());
            create.Buscar_Id_Precio_Product();

            if (create.datos.next() == true) {
                cboProducto.addItem(create.datos.getString(1));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "error " + e);
        }
    }
    
    public void crearPedido (){
        try{

            //se crea el pedido
            create.crearPedido();
            //me traigo el id del pedido que acaba de crearse
            create.EncontrarUltimoIdPedido();
            String id_order = create.getId();
            
            System.out.println("id_order --> " + id_order);
            // se inserta en el detalle del producto
            
             for (clsProducto producto : productos) {
                 
                detalle.insertarDetallePedido(id_order,producto.getId(),producto.getCantidad(),producto.getPrecio(),producto.getTotal());
            }
            int cantidadProductos1 = create.getCantidadProductos(create.getId());
            double precioTotal1 = create.getPrecioTotal(create.getId());
            objCon.conectar();
            objCon.sql = objCon.con.prepareStatement("UPDATE orders SET quantitytotal=?,total=? WHERE id=?;");
            objCon.sql.setInt(1, cantidadProductos1);
            objCon.sql.setDouble(2, precioTotal1);
            objCon.sql.setString(3, create.getId());
            objCon.sql.executeUpdate();
            this.llamarRegresar();
            
        }catch (SQLException e){
          System.out.println("error --> " + e);
        }   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cboCliente = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cboMarca = new javax.swing.JComboBox<>();
        cboProducto = new javax.swing.JComboBox<>();
        btnAñadir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        spnCantidad = new javax.swing.JSpinner();
        btnRegresar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblNumeroPedido = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1051, 663));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("Nuevo pedido");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(430, 40, 160, 29);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Cliente :");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(46, 122, 49, 17);

        cboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar..." }));
        cboCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboClienteActionPerformed(evt);
            }
        });
        jPanel1.add(cboCliente);
        cboCliente.setBounds(126, 122, 190, 20);

        jLabel3.setText("Nuevo Producto :");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(85, 180, 100, 14);

        jLabel4.setText("Marca :");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(160, 210, 50, 14);

        jLabel5.setText("Producto :");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(370, 210, 60, 14);

        jLabel6.setText("Cantidad :");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(610, 210, 60, 14);

        cboMarca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar...", "NARBATT", "PIONEIRO" }));
        cboMarca.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboMarcaItemStateChanged(evt);
            }
        });
        cboMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(cboMarca);
        cboMarca.setBounds(210, 210, 140, 20);

        cboProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar..." }));
        jPanel1.add(cboProducto);
        cboProducto.setBounds(440, 210, 150, 20);

        btnAñadir.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        btnAñadir.setForeground(new java.awt.Color(58, 155, 220));
        btnAñadir.setText("+");
        btnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirActionPerformed(evt);
            }
        });
        jPanel1.add(btnAñadir);
        btnAñadir.setBounds(870, 200, 60, 35);

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "N° Producto", "Producto", "Cantidad", "Precio Unidad", "Precio total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(160, 303, 730, 260);

        btnGuardar.setBackground(new java.awt.Color(58, 155, 220));
        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Realizar Pedido");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar);
        btnGuardar.setBounds(860, 600, 140, 32);

        spnCantidad.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        jPanel1.add(spnCantidad);
        spnCantidad.setBounds(680, 210, 59, 24);

        btnRegresar.setBackground(new java.awt.Color(255, 89, 89));
        btnRegresar.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jPanel1.add(btnRegresar);
        btnRegresar.setBounds(930, 30, 93, 32);

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 0, 51));
        btnEliminar.setText("-");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar);
        btnEliminar.setBounds(940, 200, 60, 35);
        jPanel1.add(lblNumeroPedido);
        lblNumeroPedido.setBounds(487, 122, 62, 20);
        jPanel1.add(jLabel7);
        jLabel7.setBounds(940, 370, 70, 40);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1050, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirActionPerformed
        this.agregarFila();// TODO add your handling code here:
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.llamarRegresar();    // TODO add your handling code here:
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        this.borrarFila();
        this.limpiar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        this.crearPedido();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cboMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMarcaActionPerformed
        /*       if(cboMarca.getSelectedIndex() == 1){
           create.setMarca("NARBATT");
           this.llenarProducto();
       } else{
           create.setMarca("PIONEIRO");
           this.llenarProducto();
       }*/
    }//GEN-LAST:event_cboMarcaActionPerformed

    private void cboMarcaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboMarcaItemStateChanged
        if (cboMarca != null) {
            // create.setMarca("NARBATT");
            this.limpiarcb();
            this.llenarProducto();
        } else {
            // create.setMarca("PIONEIRO");
            this.limpiarcb();
            this.llenarProducto();
        }
    }//GEN-LAST:event_cboMarcaItemStateChanged

    private void cboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboClienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmCrearPedidoVend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCrearPedidoVend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCrearPedidoVend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCrearPedidoVend.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCrearPedidoVend().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAñadir;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cboCliente;
    private javax.swing.JComboBox<String> cboMarca;
    private javax.swing.JComboBox<String> cboProducto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblNumeroPedido;
    private javax.swing.JSpinner spnCantidad;
    private javax.swing.JTable tblProductos;
    // End of variables declaration//GEN-END:variables
}
