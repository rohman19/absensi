/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package absensi_cuti;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author user
 */
public class Data_Karyawan extends javax.swing.JDialog {
koneksi kon =new koneksi();
 public Cuti_Karyawan cuti = null;
private Object[][] datakaryawan=null;
private String[] label={"NIK","Nama","Golongan","Tanggal Lahir","Umur","Jenis Kelamin","Agama","Kewarganegaraan","Alamat","No Identitas Karyawan","No Telepon"};

    /**
     * Creates new form Data_Karyawan
     */
    public Data_Karyawan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
     ImageIcon ico = new ImageIcon("src/gambar/Eiger.png");
        setIconImage(ico.getImage());
         kon.setKoneksi();
        BacaTabelKaryawan();
       
        
    }
     private void BacaTabelKaryawan(){
        try{
            String sql="Select *From karyawan order by nik_karyawan";
            kon.rs=kon.st.executeQuery(sql);
             ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            datakaryawan= new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                datakaryawan[x][0]=kon.rs.getString("nik_karyawan");
                datakaryawan[x][1]=kon.rs.getString("nama_karyawan");
                datakaryawan[x][2]=kon.rs.getString("golongan");
                datakaryawan[x][3]=kon.rs.getString("tanggal_lahir");
                datakaryawan[x][4]=kon.rs.getString("umur");
                datakaryawan[x][5]=kon.rs.getString("jenis_kelamin");
                datakaryawan[x][6]=kon.rs.getString("agama");
                datakaryawan[x][7]=kon.rs.getString("kewarganegaraan");
                datakaryawan[x][8]=kon.rs.getString("alamat");
                datakaryawan[x][9]=kon.rs.getString("no_identitas");
                datakaryawan[x][10]=kon.rs.getString("no_tlp");
                x++;
}
            tbl_karyawan.setModel(new DefaultTableModel(datakaryawan,label));
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
     private void BacaTabelCari()
    {
        try{
            String sql="Select *From karyawan where nik_karyawan like '%" +tcari.getText()+"%'";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
            baris=kon.rs.getRow();
        }
            datakaryawan= new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                datakaryawan[x][0]=kon.rs.getString("nik_karyawan");
                datakaryawan[x][1]=kon.rs.getString("nama_karyawan");
                datakaryawan[x][2]=kon.rs.getString("golongan");
                datakaryawan[x][3]=kon.rs.getString("tanggal_lahir");
                datakaryawan[x][4]=kon.rs.getString("umur");
                datakaryawan[x][5]=kon.rs.getString("jenis_kelamin");
                datakaryawan[x][6]=kon.rs.getString("agama");
                datakaryawan[x][7]=kon.rs.getString("kewarganegaraan");
                datakaryawan[x][8]=kon.rs.getString("alamat");
                datakaryawan[x][9]=kon.rs.getString("no_identitas");
                datakaryawan[x][10]=kon.rs.getString("no_tlp");
                x++;
            
            }
            tbl_karyawan.setModel(new DefaultTableModel(datakaryawan,label));
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_karyawan = new javax.swing.JTable();
        tcari = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Data Karyawan");

        tbl_karyawan.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tbl_karyawan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_karyawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_karyawanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_karyawan);

        tcari.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setText("Cari");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(878, 327));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_karyawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_karyawanMouseClicked
        // TODO add your handling code here:
        int tabelKaryawan = tbl_karyawan.getSelectedRow();
      cuti.Nik =tbl_karyawan.getValueAt(tabelKaryawan, 0).toString();
    this.dispose();
    }//GEN-LAST:event_tbl_karyawanMouseClicked

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
            java.util.logging.Logger.getLogger(Data_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Data_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Data_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Data_Karyawan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Data_Karyawan dialog = new Data_Karyawan(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_karyawan;
    private javax.swing.JTextField tcari;
    // End of variables declaration//GEN-END:variables
}
