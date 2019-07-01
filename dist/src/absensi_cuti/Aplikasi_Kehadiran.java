/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package absensi_cuti;

import java.sql.DriverManager;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author user
 */
public class Aplikasi_Kehadiran extends javax.swing.JInternalFrame {
koneksi kon =new koneksi();
private Object[][] datakaryawan=null;
private String[] label={"ID Kehadiran","NIK","Nama","Golongan","Kehadiran","Tidak Hadir","Izin","Sakit","Hak Cuti","Keterangan"};
    /**
     * Creates new form Aplikasi_Kehadiran
     */
public Connection conn;
    public Aplikasi_Kehadiran() throws SQLException {
        initComponents();
         conn = DriverManager.getConnection("jdbc:mysql://localhost/cuti","root","");
         kon.setKoneksi();
        BacaTabelKaryawan();
         tnik.setVisible(true);
         nomor();
      
    }
    public String nomor() 
 {
        String no=null;
    try{
        String sql = "Select right(id_kehadiran,3)+1 from kehadiran ";
        ResultSet rs = kon.st.executeQuery(sql);
        if (rs.next()){
            rs.last();
            no = rs.getString(1);
            while (no.length()<3){
                no="00"+no;
                no="K"+no;
            tkhd.setText(no);    
            }
        }else{
            no="K001";
            tkhd.setText(no);    
        }
    }catch (Exception e){     
    }return no;
}
               public String Nik;
  public String getNik(){
        return Nik;
    }
             public String Nama;
  public String getNama(){
        return Nama;
    }
             public String Golongan;
  public String getGolongan(){
        return Golongan;
    }
private void BacaTabelKaryawan(){
        try{
            String sql="Select *From kehadiran order by nik";
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
                datakaryawan[x][0]=kon.rs.getString("id_kehadiran");
                datakaryawan[x][1]=kon.rs.getString("nik");
                datakaryawan[x][2]=kon.rs.getString("nama");
                datakaryawan[x][3]=kon.rs.getString("golongan");
                datakaryawan[x][4]=kon.rs.getString("kehadiran");
                datakaryawan[x][5]=kon.rs.getString("tidakhadir");
                datakaryawan[x][6]=kon.rs.getString("izin");
                datakaryawan[x][7]=kon.rs.getString("sakit");
                datakaryawan[x][8]=kon.rs.getString("hakcuti");
                datakaryawan[x][9]=kon.rs.getString("keterangan");
                x++;
}
            tbl_karyawan.setModel(new DefaultTableModel(datakaryawan,label));
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
private void HapusData()
    {
       try{
            String sql="delete from kehadiran where nik='"+tnik.getText()+"'";
                   
            kon.st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Data Berhasil Dihapus");
            bersih();
            BacaTabelKaryawan();
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, e); 
    }
    }
private void SetTabel()
    {
        int row=tbl_karyawan.getSelectedRow();
        tkhd.setText((String)tbl_karyawan.getValueAt(row, 0));
        tnik.setText((String)tbl_karyawan.getValueAt(row, 1));
        tnm.setText((String)tbl_karyawan.getValueAt(row, 2));
        tgol.setText((String)tbl_karyawan.getValueAt(row, 3));
        khd.setText((String)tbl_karyawan.getValueAt(row, 4));
        thd.setText((String)tbl_karyawan.getValueAt(row, 5));
        ijin.setText((String)tbl_karyawan.getValueAt(row, 6));
        skt.setText((String)tbl_karyawan.getValueAt(row, 7));
        hc.setText((String)tbl_karyawan.getValueAt(row, 8));
        ktg.setText((String)tbl_karyawan.getValueAt(row, 9));
        
        
}
private void ubah(){
try{

String sql = "update kehadiran set id_kehadiran = '" + tkhd.getText() +"', " 
   + " nik = '" + tnik.getText() +"', "
   + " nama = '" + tnm.getText() +"', "
   + " golongan = '" + tgol.getText() +"'," 
   + " kehadiran = '" + khd.getText() +"'," 
   + " tidakhadir = '" + thd.getText() +"'," 
   + " izin = '" + ijin.getText() +"'," 
   + " sakit = '" + skt.getText() +"', "
   + " hakcuti = '" + hc.getText() +"', "
   + " keterangan = '" + ktg.getText() + "' where id_kehadiran = '" + tkhd.getText()+"'";
kon.st.executeUpdate(sql);
JOptionPane.showMessageDialog(null,"Data berhasil diubah");
bersih();
BacaTabelKaryawan();
}
catch(SQLException e){
JOptionPane.showMessageDialog(null,e);
}
}
private void bersih()
    {
        tnik.setText("");
      tkhd.setText("");
        tnm.setText("");
        thd.setText("");
        khd.setText("");
        tgol.setText("");
        skt.setText("");
        ktg.setText("");
        hc.setText("");
        ijin.setText("");
    }
  private void BacaTabelCari()
    {
        try{
            String sql="Select *From kehadiran where nik like '%" +tcari.getText()+"%'";
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
            datakaryawan[x][0]=kon.rs.getString("id_kehadiran");
                datakaryawan[x][1]=kon.rs.getString("nik");
                datakaryawan[x][2]=kon.rs.getString("nama");
                datakaryawan[x][3]=kon.rs.getString("golongan");
                datakaryawan[x][4]=kon.rs.getString("kehadiran");
                datakaryawan[x][5]=kon.rs.getString("tidakhadir");
                datakaryawan[x][6]=kon.rs.getString("izin");
                datakaryawan[x][7]=kon.rs.getString("sakit");
                datakaryawan[x][8]=kon.rs.getString("hakcuti");
                datakaryawan[x][9]=kon.rs.getString("keterangan");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        thd = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        tnm = new javax.swing.JTextField();
        tgol = new javax.swing.JTextField();
        hc = new javax.swing.JTextField();
        ktg = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        tnik = new javax.swing.JTextField();
        tcari = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_karyawan = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ijin = new javax.swing.JTextField();
        khd = new javax.swing.JTextField();
        skt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tkhd = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Aplikasi_Kehadiran");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel14.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/test.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Hak Cuti");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Keterangan");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel13.setText("Kehadiran Karyawan");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Sakit");

        thd.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        thd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thdActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton5.setText("Cetak");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        tnm.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        tgol.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tgol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tgolActionPerformed(evt);
            }
        });

        hc.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        ktg.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton1.setText("Simpan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setText("Ubah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setText("Batal");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton4.setText("Hapus");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        tnik.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tnik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnikActionPerformed(evt);
            }
        });

        tcari.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Tidak Hadir");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Kehadiran");

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

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("Golongan");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setText("Cari");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("NIK");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Nama");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Izin");

        ijin.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        khd.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        khd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                khdActionPerformed(evt);
            }
        });

        skt.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("ID Kehadiran");

        tkhd.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tkhd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkhdActionPerformed(evt);
            }
        });

        jButton6.setText("...");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton7.setText("Tambah");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(tnm, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(tnik, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(42, 42, 42)
                                                .addComponent(tgol, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(khd, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(112, 112, 112))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(tkhd, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(118, 118, 118)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(ktg, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel8)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                            .addComponent(jLabel6))
                                        .addGap(10, 10, 10)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(ijin, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(hc, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(skt, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(thd, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addContainerGap(23, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tkhd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tnik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jButton6))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tgol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(khd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(thd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ijin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(80, 80, 80))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(skt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(hc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(ktg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton7))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
        );

        setBounds(0, 0, 693, 495);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try{
            String path="src/laporan/LaporanKehadiranKaryawan.jasper";
            HashMap parameter = new HashMap();
            JasperPrint print = JasperFillManager.fillReport(path, parameter,kon.setKoneksi());
            JasperViewer.viewReport(print,false);
        }
        catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        conn = null;
        Statement stmt;

        if (    tnik.getText().equals("") ||
            tnm.getText().equals("") ||
            tgol.getText().equals("")||
            thd.getText().equals("")||
            khd.getText().equals("") ||
           tkhd.getText().equals("") ||
            hc.getText().equals("") ||
            ktg.getText().equals("") ||
            ijin.getText().equals("") ||
            
            skt.getText().equals(""))
        {
            JOptionPane.showMessageDialog (rootPane,"Data belum lengkap.");
        }
        else {
            
            String Nik= tnik.getText();
            String Nama= tnm.getText();
            String Golongan = tgol.getText();
            String Tidak = thd.getText();
            String Hakcuti = hc.getText();
            String Keterangan = ktg.getText();
            String Izin = ijin.getText();
            String Sakit = skt.getText();
             String Hadir = khd.getText();
             String THadir = tkhd.getText();
            try {

                Date skrg=new Date();
                SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
                String tgl=frm.format(skrg);
                conn = DriverManager.getConnection("jdbc:mysql://localhost/cuti","root","");
                stmt = conn.createStatement();
                stmt.executeUpdate("insert into kehadiran values('"+THadir+"','"+Nik+"','"+Nama+"','"+Golongan+"','"+Hadir+"','"+Tidak+"','"+Izin+"','"+Sakit+"','"+Hakcuti+"','"+Keterangan+"')");
                JOptionPane.showMessageDialog(null,"Data Berhasil DiSimpan!");

            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        BacaTabelKaryawan();
        bersih();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ubah();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        bersih();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        HapusData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tnikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnikActionPerformed
        // TODO add your handling code here:
         if(tnik.getText().equals("11001"))
        {
            khd.setText("28");
            thd.setText("1");
            ijin.setText("1");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Cukup");
        }
        else if(tnik.getText().equals("11002"))
        {
            khd.setText("25");
            thd.setText("2");
            ijin.setText("2");
            skt.setText("1");
            hc.setText("12");
            ktg.setText("Kurang");
        }
            else if(tnik.getText().equals("11003"))
        {
            khd.setText("30");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Baik");
            
        }
          else if(tnik.getText().equals("11004"))
        {
            khd.setText("30");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Baik");
        }
          else if(tnik.getText().equals("11004"))
        {
            khd.setText("20");
            thd.setText("0");
            ijin.setText("5");
            skt.setText("5");
            hc.setText("12");
            ktg.setText("Kurang");
        }
           else if(tnik.getText().equals("11005"))
        {
            khd.setText("25");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("5");
            hc.setText("12");
            ktg.setText("Kurang");
        }
           else if(tnik.getText().equals("11006"))
        {
            khd.setText("29");
            thd.setText("0");
            ijin.setText("1");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Cukup");
        }
           else if(tnik.getText().equals("11007"))
        {
            khd.setText("30");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("0");
            hc.setText("0");
            ktg.setText("Baik");
        }
    }//GEN-LAST:event_tnikActionPerformed

    private void tbl_karyawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_karyawanMouseClicked
        // TODO add your handling code here:
        SetTabel();
    }//GEN-LAST:event_tbl_karyawanMouseClicked

    private void thdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thdActionPerformed

    private void khdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_khdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_khdActionPerformed

    private void tgolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tgolActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_tgolActionPerformed

    private void tkhdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkhdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tkhdActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        boolean closable = true;
        DataKaryawan dataKaryawan=new DataKaryawan(null, closable);
        dataKaryawan.hadir = this;
        dataKaryawan.setVisible(true);
        dataKaryawan.setResizable(true);
        tnik.setText(Nik);
        tnm.setText(Nama);
        tgol.setText(Golongan);
        if(tnik.getText().equals("11001"))
        {
            khd.setText("28");
            thd.setText("1");
            ijin.setText("1");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Cukup");
        }
        else if(tnik.getText().equals("11002"))
        {
            khd.setText("25");
            thd.setText("2");
            ijin.setText("2");
            skt.setText("1");
            hc.setText("12");
            ktg.setText("Kurang");
        }
            else if(tnik.getText().equals("11003"))
        {
            khd.setText("30");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Baik");
            
        }
          else if(tnik.getText().equals("11004"))
        {
            khd.setText("30");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Baik");
        }
          else if(tnik.getText().equals("11004"))
        {
            khd.setText("20");
            thd.setText("0");
            ijin.setText("5");
            skt.setText("5");
            hc.setText("12");
            ktg.setText("Kurang");
        }
           else if(tnik.getText().equals("11005"))
        {
            khd.setText("25");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("5");
            hc.setText("12");
            ktg.setText("Kurang");
        }
           else if(tnik.getText().equals("11006"))
        {
            khd.setText("29");
            thd.setText("0");
            ijin.setText("1");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Cukup");
        }
           else if(tnik.getText().equals("11007"))
        {
            khd.setText("30");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("0");
            hc.setText("0");
            ktg.setText("Baik");
        }
            else if(tnik.getText().equals("11008"))
        {
            khd.setText("30");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("0");
            hc.setText("0");
            ktg.setText("Baik");
        }
  else if(tnik.getText().equals("11009"))
        {
            khd.setText("29");
            thd.setText("0");
            ijin.setText("1");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Cukup");
        }
         else if(tnik.getText().equals("110010"))
        {
            khd.setText("28");
            thd.setText("0");
            ijin.setText("1");
            skt.setText("1");
            hc.setText("12");
            ktg.setText("Cukup");
        }
         else if(tnik.getText().equals("110011"))
        {
            khd.setText("27");
            thd.setText("0");
            ijin.setText("2");
            skt.setText("1");
            hc.setText("12");
            ktg.setText("Cukup");
        }
         else if(tnik.getText().equals("110012"))
        {
            khd.setText("20");
            thd.setText("8");
            ijin.setText("1");
            skt.setText("1");
            hc.setText("12");
            ktg.setText("Kurang");
        }
         else if(tnik.getText().equals("110013"))
        {
            khd.setText("20");
            thd.setText("8");
            ijin.setText("1");
            skt.setText("1");
            hc.setText("12");
            ktg.setText("Kurang");
        }
         else if(tnik.getText().equals("110014"))
        {
            khd.setText("30");
            thd.setText("0");
            ijin.setText("0");
            skt.setText("0");
            hc.setText("12");
            ktg.setText("Baik");
        }
         else if(tnik.getText().equals("110015"))
        {
            khd.setText("26");
            thd.setText("1");
            ijin.setText("1");
            skt.setText("2");
            hc.setText("12");
            ktg.setText("Kurang");
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        tkhd.requestFocus();
        nomor();
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField hc;
    private javax.swing.JTextField ijin;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField khd;
    private javax.swing.JTextField ktg;
    private javax.swing.JTextField skt;
    private javax.swing.JTable tbl_karyawan;
    private javax.swing.JTextField tcari;
    private javax.swing.JTextField tgol;
    private javax.swing.JTextField thd;
    private javax.swing.JTextField tkhd;
    private javax.swing.JTextField tnik;
    private javax.swing.JTextField tnm;
    // End of variables declaration//GEN-END:variables
}
