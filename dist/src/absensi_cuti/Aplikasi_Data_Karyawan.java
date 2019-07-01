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
public class Aplikasi_Data_Karyawan extends javax.swing.JInternalFrame {
koneksi kon =new koneksi();
private Object[][] datakaryawan=null;
private String[] label={"NIK","Nama","Golongan","Tanggal Lahir","Umur","Jenis Kelamin","Agama","Kewarganegaraan","Alamat","No Identitas Karyawan","No Telepon"};
    /**
     * Creates new form Aplikasi_Data_Karyawan
     */
public Connection conn;
    public Aplikasi_Data_Karyawan() throws SQLException {
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
        String sql = "Select right(nik_karyawan,3)+1 from karyawan ";
        ResultSet rs = kon.st.executeQuery(sql);
        if (rs.next()){
            rs.last();
            no = rs.getString(1);
            while (no.length()<3){
                no="00"+no;
                no="11"+no;
            tnik.setText(no);    
            }
        }else{
            no="11001";
            tnik.setText(no);    
        }
    }catch (Exception e){     
    }return no;
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
      private void HapusData()
    {
       try{
            String sql="delete from karyawan where nik_karyawan='"+tnik.getText()+"'";
                   
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
        tnik.setText((String)tbl_karyawan.getValueAt(row, 0));
        tnm.setText((String)tbl_karyawan.getValueAt(row, 1));
        tgol.setText((String)tbl_karyawan.getValueAt(row, 2));
        tgl.setDateFormatString((String)tbl_karyawan.getValueAt(row, 3));
        tumur.setText((String)tbl_karyawan.getValueAt(row, 4));
        cmb.setSelectedItem((String)tbl_karyawan.getValueAt(row, 5));
        cmb1.setSelectedItem((String)tbl_karyawan.getValueAt(row, 6));
        tkwg.setText((String)tbl_karyawan.getValueAt(row, 7));
        talm.setText((String)tbl_karyawan.getValueAt(row, 8));
        tno.setText((String)tbl_karyawan.getValueAt(row, 9));
        ttlp.setText((String)tbl_karyawan.getValueAt(row, 10));
        
}
      private void ubah(){
try{
String Tanggal = ((JTextField)tgl.getDateEditor().getUiComponent()).getText();
String sql = "update karyawan set nik_karyawan = '" + tnik.getText() +"', " 
   + " nama_karyawan = '" + tnm.getText() +"', "
   + " golongan = '" + tgol.getText() +"', "
   + " tanggal_lahir = '" + Tanggal + "',"
   + " umur = '" + tumur.getText() +"', "
   + " jenis_kelamin = '" + cmb.getSelectedItem() +"', "
   + " agama = '" + cmb1.getSelectedItem() +"',"
   + " kewarganegaraan = '" + tkwg.getText() +"', "
   + " alamat = '" + talm.getText() +"', "
   + " no_identitas = '" + tno.getText() +"', "
   + " no_tlp = '" + ttlp.getText() + "' where nik_karyawan = '" + tnik.getText()+"'";
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
      tno.setText("");
        talm.setText("");
        tumur.setText("");
        tgl.setDateFormatString("");
        tgol.setText("");
        
        ttlp.setText("");
        tkwg.setText("");
        tnm.setText("");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tnik = new javax.swing.JTextField();
        tnm = new javax.swing.JTextField();
        tgol = new javax.swing.JTextField();
        tgl = new com.toedter.calendar.JDateChooser();
        tumur = new javax.swing.JTextField();
        cmb = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        tkwg = new javax.swing.JTextField();
        cmb1 = new javax.swing.JComboBox();
        talm = new javax.swing.JTextField();
        tno = new javax.swing.JTextField();
        ttlp = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        tcari = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_karyawan = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Aplikasi Data Karyawan");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("NIK");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel3.setText("Golongan");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Tanggal Lahir");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Umur");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel6.setText("Jenis Kelamin");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("Nama");

        tnik.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tnik.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnikActionPerformed(evt);
            }
        });

        tnm.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        tgol.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        tgl.setDateFormatString("yyyy-MM-dd");

        tumur.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        cmb.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Pilihan-", "Laki-Laki", "Perempuan" }));
        cmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel7.setText("Agama");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Kewarganegaraan");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Alamat");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("No. Identitas");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setText("No. Telepon");

        tkwg.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        cmb1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        cmb1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-PILIHAN-", "Islam", "Kristen", "Budha", "Hindu" }));

        talm.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        tno.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        ttlp.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

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

        tcari.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setText("Cari");

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

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel13.setText("DATA KARYAWAN");

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

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton5.setText("Cetak");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(tumur, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(tgl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(cmb, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 317, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(97, 97, 97)
                                        .addComponent(jLabel13)
                                        .addGap(68, 68, 68))))
                            .addComponent(jScrollPane2))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tnm, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tnik, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(93, 93, 93)
                                            .addComponent(tgol, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(56, 56, 56)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(ttlp, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(talm, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addGap(62, 62, 62)
                                            .addComponent(cmb1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel8)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(tkwg, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(tno, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)
                                .addGap(4, 4, 4)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4)
                                .addGap(10, 10, 10)
                                .addComponent(jButton5)))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tnik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel7)
                    .addComponent(cmb1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tnm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(tkwg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tgol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(talm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tgl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(tno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tumur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(ttlp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(99, 99, 99))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton4)
                            .addComponent(jButton5)
                            .addComponent(jButton7))))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tnikActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnikActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnikActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        conn = null;
        Statement stmt;

        if (    tnik.getText().equals("") ||
                 tnm.getText().equals("") ||
              tgol.getText().equals("")||
                tumur.getText().equals("")||
           cmb.getSelectedItem().equals("") ||
           cmb1.getSelectedItem().equals("") ||
            tkwg.getText().equals("") ||
                 talm.getText().equals("") ||
                 tno.getText().equals("") ||
            ttlp.getText().equals(""))
        {
            JOptionPane.showMessageDialog (rootPane,"Data belum lengkap.");
        }
        else {
            String Tanggal = ((JTextField)tgl.getDateEditor().getUiComponent()).getText();
            String Nik= tnik.getText();
              String Nama= tnm.getText();
           String Golongan = tgol.getText();
           String Umur = tumur.getText();
            Object Jenis = cmb.getSelectedItem();
          Object Agama = cmb1.getSelectedItem();
            String Warga = tkwg.getText();
            String Alamat = talm.getText();
            String Nomor = tno.getText();
            String Telepon = ttlp.getText();
            try {

                Date skrg=new Date();
                SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
                String tgl=frm.format(skrg);
                conn = DriverManager.getConnection("jdbc:mysql://localhost/cuti","root","");
                stmt = conn.createStatement();
                stmt.executeUpdate("insert into karyawan values('"+Nik+"','"+Nama+"','"+Golongan+"','"+Tanggal+"','"+Umur+"','"+Jenis+"','"+Agama+"','"+Warga+"','"+Alamat+"','"+Nomor+"','"+Telepon+"')");
                JOptionPane.showMessageDialog(null,"Data Berhasil DiSimpan!");

            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        BacaTabelKaryawan();
        bersih();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        bersih();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        HapusData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
       ubah();
             
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tbl_karyawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_karyawanMouseClicked
        // TODO add your handling code here:
        SetTabel();
    }//GEN-LAST:event_tbl_karyawanMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
 try{
            String path="src/laporan/LaporanKaryawan.jasper";
            HashMap parameter = new HashMap();
            JasperPrint print = JasperFillManager.fillReport(path, parameter,kon.setKoneksi());
            JasperViewer.viewReport(print,false);
        }
        catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        tnik.requestFocus();
        nomor();
        String Tanggal = ((JTextField)tgl.getDateEditor().getUiComponent()).getText();
    }//GEN-LAST:event_jButton7ActionPerformed
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox cmb;
    private javax.swing.JComboBox cmb1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField talm;
    private javax.swing.JTable tbl_karyawan;
    private javax.swing.JTextField tcari;
    private com.toedter.calendar.JDateChooser tgl;
    private javax.swing.JTextField tgol;
    private javax.swing.JTextField tkwg;
    private javax.swing.JTextField tnik;
    private javax.swing.JTextField tnm;
    private javax.swing.JTextField tno;
    private javax.swing.JTextField ttlp;
    private javax.swing.JTextField tumur;
    // End of variables declaration//GEN-END:variables
}
