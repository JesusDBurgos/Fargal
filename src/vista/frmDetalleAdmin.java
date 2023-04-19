package vista;

import controlador.clsConexion;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.clsDetalle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Sena CSET
 */
public class frmDetalleAdmin extends javax.swing.JFrame {

    /**
     * Creates new form frmDetalle
     */
    public frmDetalleAdmin() {
        initComponents();    
        this.setLocationRelativeTo(null);
        creartabla();
    }
    
    DefaultTableModel tabladatos;
    clsConexion objCon = new clsConexion();
    clsDetalle objDet = new clsDetalle();
 
 
     public void llamarRegresar() {
        frmVerPedidosAdmin objIn = new frmVerPedidosAdmin();
        objIn.setVisible(true);
        this.setVisible(false);
    }
    public void creartabla() {
        Object modelodata[][] = new Object[0][0];
        Object modelotitulos[] = {"Producto", "Cantidad", "Valor productos"};
        tabladatos = new DefaultTableModel();
        tabladatos = new DefaultTableModel(modelodata, modelotitulos);
        this.tblDetalle.setModel(this.tabladatos);
    }
    
    public void setNumeroPedido(String numeroPedido) {
     lblNumPed.setText(numeroPedido);
     this.llenarDetalle();
     this.llenarTablaDetalle();
}

    public void borrartabla() {
        while (0 < this.tblDetalle.getRowCount()) {
            tblDetalle.setModel(new DefaultTableModel());
            this.creartabla();
        }
    }
    
    public void llenarDetalle(){
        try {
  
                objDet.setOrder(lblNumPed.getText());
                objDet.llenarDatosDetalle();
                if (objDet.datos.next() == true) {
                    //lblNumPed.setText(lblNumPed.getText()+"   " +objDet.datos.getString(1));
                    lblFec.setText(lblFec.getText()+""+objDet.datos.getString(2));
                    lblNomCli.setText(lblNomCli.getText()+"   " +objDet.datos.getString(3));
                    lblNomVen.setText(lblNomVen.getText()+"   " +objDet.datos.getString(4));
                    cboEst.setSelectedItem(objDet.datos.getString(5));
                    lblValPed.setText(lblValPed.getText()+"   "+objDet.datos.getString(6));
                }else{
                JOptionPane.showMessageDialog(null, "No se ha podido buscar, lo siento" );
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "No se ha podido mostrar, lo siento" + ex);
            }
    }
    public void llenarTablaDetalle(){
        try {
                borrartabla();
                objDet.setOrder(lblNumPed.getText());
                objDet.llenarTablaDetalle();
                while (objDet.datos.next() == true) {
                    String Producto = objDet.datos.getString(1);
                    String Cantidad = objDet.datos.getString(2);
                    String ValorProductos = objDet.datos.getString(3);
                    Object fila[] = {Producto,Cantidad, ValorProductos};
                    tabladatos.addRow(fila);
                }
            } catch (SQLException ex) {
                System.out.println("error al llenar la tabla" + ex);
            }
    }

    
    public void editarEstado(){
        if (cboEst.getSelectedIndex()== 0) {
                cboEst.grabFocus();
                JOptionPane.showMessageDialog(null, "Debe seleccionar el nuevo estado que quiere asignar al pedido");
            }
        else {
            objDet.setOrder(lblNumPed.getText());
            objDet.setEstado((String) cboEst.getSelectedItem());
            objDet.editarEstado();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDetalle = new javax.swing.JTable();
        lblNumPed = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnReg = new javax.swing.JButton();
        lblNomVen = new javax.swing.JLabel();
        lblFec = new javax.swing.JLabel();
        cboEst = new javax.swing.JComboBox<>();
        lblValPed = new javax.swing.JLabel();
        btnMod = new javax.swing.JButton();
        lblEst = new javax.swing.JLabel();
        lblNomCli = new javax.swing.JLabel();
        lblNumPed1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Detalle de pedido");
        setMinimumSize(new java.awt.Dimension(896, 626));
        setResizable(false);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(null);

        tblDetalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Producto", "Cantidad", "Valor Productos"
            }
        ));
        jScrollPane1.setViewportView(tblDetalle);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(130, 310, 640, 190);

        lblNumPed.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel1.add(lblNumPed);
        lblNumPed.setBounds(170, 90, 70, 17);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Detalle del pedido");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(60, 40, 141, 22);

        btnReg.setText("Regresar");
        btnReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegActionPerformed(evt);
            }
        });
        jPanel1.add(btnReg);
        btnReg.setBounds(760, 20, 90, 22);

        lblNomVen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNomVen.setText("Nombre vendedor:");
        jPanel1.add(lblNomVen);
        lblNomVen.setBounds(40, 180, 320, 17);

        lblFec.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblFec.setText(" Fecha:");
        jPanel1.add(lblFec);
        lblFec.setBounds(110, 120, 210, 17);

        cboEst.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione...", "APROBADO", "PENDIENTE", "CANCELADO" }));
        jPanel1.add(cboEst);
        cboEst.setBounds(170, 210, 120, 22);

        lblValPed.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblValPed.setText("Valor total:");
        jPanel1.add(lblValPed);
        lblValPed.setBounds(90, 240, 270, 17);

        btnMod.setText("Modificar");
        btnMod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModActionPerformed(evt);
            }
        });
        jPanel1.add(btnMod);
        btnMod.setBounds(680, 230, 90, 22);

        lblEst.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEst.setText("Estado:");
        jPanel1.add(lblEst);
        lblEst.setBounds(110, 210, 47, 17);

        lblNomCli.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNomCli.setText("Nombre cliente:");
        jPanel1.add(lblNomCli);
        lblNomCli.setBounds(60, 150, 320, 17);

        lblNumPed1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNumPed1.setText("N° Pedido:");
        jPanel1.add(lblNumPed1);
        lblNumPed1.setBounds(90, 90, 70, 17);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 900, 580);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModActionPerformed
    this.editarEstado();// TODO add your handling code here:
    }//GEN-LAST:event_btnModActionPerformed

    private void btnRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegActionPerformed
    this.llamarRegresar();    // TODO add your handling code here:
    }//GEN-LAST:event_btnRegActionPerformed

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
            java.util.logging.Logger.getLogger(frmDetalleAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDetalleAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDetalleAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDetalleAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmDetalleAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnMod;
    private javax.swing.JButton btnReg;
    private javax.swing.JComboBox<String> cboEst;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEst;
    private javax.swing.JLabel lblFec;
    private javax.swing.JLabel lblNomCli;
    private javax.swing.JLabel lblNomVen;
    private javax.swing.JLabel lblNumPed;
    private javax.swing.JLabel lblNumPed1;
    private javax.swing.JLabel lblValPed;
    private javax.swing.JTable tblDetalle;
    // End of variables declaration//GEN-END:variables
}
