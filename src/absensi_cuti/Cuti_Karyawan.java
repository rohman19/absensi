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
public class Cuti_Karyawan extends javax.swing.JInternalFrame {
koneksi kon =new koneksi();
private Object[][] datacuti=null;
private String[] label={"ID Cuti","Nik Karyawan","Tanggal Mulai Cuti","Tanggal Selesai Cuti","Jumlah Cuti","Sisa Cuti","Alasan Mengambil Cuti","Hak Cuti"};

    /**
     * Creates new form Cuti_Karyawan
     */
public Connection conn;
    public Cuti_Karyawan() throws SQLException {
        initComponents();
        conn = DriverManager.getConnection("jdbc:mysql://localhost/cuti","root","");
         kon.setKoneksi();
        BacaTabelKaryawan();
       
         awal();
    }
           public String Nik;
  public String getNik(){
        return Nik;
    }
    private void nonaktif()
    {
        tid.setEditable(false);
    }
    private void awal()
{
   
    nomor();
    
    tnik.requestFocus();
}
    public String nomor() 
 {
        String no=null;
    try{
        String sql = "Select right(id_cuti,3)+1 from cuti_karyawan ";
        ResultSet rs = kon.st.executeQuery(sql);
        if (rs.next()){
            rs.last();
            no = rs.getString(1);
            while (no.length()<3){
                no="00"+no;
                no="12"+no;
            tid.setText(no);    
            }
        }else{
            no="12001";
            tid.setText(no);    
        }
    }catch (Exception e){     
    }return no;
}
 private void BacaTabelKaryawan(){
        try{
            String sql="Select *From cuti_karyawan order by id_cuti";
            kon.rs=kon.st.executeQuery(sql);
             ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
                baris=kon.rs.getRow();
            }
            datacuti= new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
                datacuti[x][0]=kon.rs.getString("id_cuti");
                datacuti[x][1]=kon.rs.getString("nik_karyawan");
                datacuti[x][2]=kon.rs.getString("tgl_mulai_cuti");
                datacuti[x][3]=kon.rs.getString("tgl_selesai_cuti");
                datacuti[x][4]=kon.rs.getString("jumlah_cuti");
                datacuti[x][5]=kon.rs.getString("sisa_cuti");
                datacuti[x][6]=kon.rs.getString("alasan_mengambil_cuti");
                datacuti[x][7]=kon.rs.getString("hak_cuti");
                
                x++;
}
            tbl_cuti.setModel(new DefaultTableModel(datacuti,label));
        }
        catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
      private void HapusData()
    {
       try{
            String sql="delete from cuti_karyawan where id_cuti='"+tid.getText()+"'";
                   
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
        int row=tbl_cuti.getSelectedRow();
        tid.setText((String)tbl_cuti.getValueAt(row, 0));
        tnik.setText((String)tbl_cuti.getValueAt(row, 1));
        tgl_mulai.setDateFormatString((String)tbl_cuti.getValueAt(row, 2));
        tgl_selesai.setDateFormatString((String)tbl_cuti.getValueAt(row, 3));
        tjumlah.setText((String)tbl_cuti.getValueAt(row, 4));
        sisa.setText((String)tbl_cuti.getValueAt(row, 5));
        alasan.setText((String)tbl_cuti.getValueAt(row, 6));
        hak.setText((String)tbl_cuti.getValueAt(row, 7));
        
        
}
      private void ubah(){
try{
String Tanggal = ((JTextField)tgl_mulai.getDateEditor().getUiComponent()).getText();
String Tanggal1 = ((JTextField)tgl_selesai.getDateEditor().getUiComponent()).getText();
String sql = "update cuti_karyawan set id_cuti = '" + tid.getText() +"', " 
   + " nik_karyawan = '" + tnik.getText() +"', "
   + " tgl_mulai_cuti = '" + Tanggal +"', "
   + " tgl_selesai_cuti = '" + Tanggal1 + "',"
   + " jumlah_cuti = '" + tjumlah.getText() +"', "
   + " sisa_cuti = '" + sisa.getText() +"', "
   + " alasan_mengambil_cuti = '" + alasan.getText() +"',"
   + " hak_cuti = '" + hak.getText() +"' where id_cuti = '" + tid.getText()+"'";
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
        tid.setText("");
      tnik.setText("");
        tjumlah.setText("");
        sisa.setText("");
        
        alasan.setText("");
        
        hak.setText("");
    }
    private void BacaTabelCari()
    {
        try{
            String sql="Select *From cuti_karyawan where nik_karyawan like '%" +tcari.getText()+"%'";
            kon.rs=kon.st.executeQuery(sql);
            ResultSetMetaData m=kon.rs.getMetaData();
            int kolom=m.getColumnCount();
            int baris=0;
            while(kon.rs.next()){
            baris=kon.rs.getRow();
        }
            datacuti= new Object[baris][kolom];
            int x=0;
            kon.rs.beforeFirst();
            while(kon.rs.next()){
               datacuti[x][0]=kon.rs.getString("id_cuti");
                datacuti[x][1]=kon.rs.getString("nik_karyawan");
                datacuti[x][2]=kon.rs.getString("tgl_mulai_cuti");
                datacuti[x][3]=kon.rs.getString("tgl_selesai_cuti");
                datacuti[x][4]=kon.rs.getString("jumlah_cuti");
                datacuti[x][5]=kon.rs.getString("sisa_cuti");
                datacuti[x][6]=kon.rs.getString("alasan_mengambil_cuti");
                datacuti[x][7]=kon.rs.getString("hak_cuti");
                x++;
            
            }
            tbl_cuti.setModel(new DefaultTableModel(datacuti,label));
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

        jLabel10 = new javax.swing.JLabel();
        tjumlah = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        tgl_mulai = new com.toedter.calendar.JDateChooser();
        sisa = new javax.swing.JTextField();
        alasan = new javax.swing.JTextField();
        bt_simpan = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        hak = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        tcari = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_cuti = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tgl_selesai = new com.toedter.calendar.JDateChooser();
        tnik = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel10.setText("Alasan Mengambil Cuti");

        tjumlah.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tjumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tjumlahActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel4.setText("Tanggal Mulai Cuti");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel1.setText("Id Cuti");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel9.setText("Sisa Cuti");

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton2.setText("Ubah");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tgl_mulai.setDateFormatString("yyyy-MM-dd");

        sisa.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        sisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sisaActionPerformed(evt);
            }
        });

        alasan.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        bt_simpan.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        bt_simpan.setText("Simpan");
        bt_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_simpanActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton3.setText("Batal");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        hak.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton4.setText("Hapus");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel11.setText("Hak Cuti");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/test.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel12.setText("Cari");

        tcari.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel13.setText("Cuti Karyawan");

        tbl_cuti.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tbl_cuti.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_cuti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_cutiMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_cuti);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel5.setText("Tanggal Selesai Cuti");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel8.setText("Jumlah Cuti");

        tid.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        tid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jLabel2.setText("NIK Karyawan");

        tgl_selesai.setDateFormatString("yyyy-MM-dd");

        tnik.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N

        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton5.setText("Cetak");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
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
                                .addComponent(jLabel1)
                                .addGap(78, 78, 78)
                                .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(37, 37, 37)
                                .addComponent(tnik, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(69, 69, 69)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sisa, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(10, 10, 10)
                                .addComponent(tgl_mulai, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(alasan, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(4, 4, 4)
                                .addComponent(tgl_selesai, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hak, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bt_simpan)
                                .addGap(6, 6, 6)
                                .addComponent(jButton2)
                                .addGap(6, 6, 6)
                                .addComponent(jButton3)
                                .addGap(6, 6, 6)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(10, 10, 10)
                                .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(107, 107, 107)
                                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(tjumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel8)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(1, 1, 1)
                                        .addComponent(tnik, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jButton1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(sisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel9)))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tgl_mulai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLabel10))
                            .addComponent(alasan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tgl_selesai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(hak, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(jLabel11)))
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bt_simpan)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton4)
                        .addComponent(jButton5)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel12))
                    .addComponent(tcari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sisaActionPerformed

    private void bt_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_simpanActionPerformed
        // TODO add your handling code here:
        conn = null;
        Statement stmt;

        if (    tid.getText().equals("") ||
                 tnik.getText().equals("") ||
              tjumlah.getText().equals("")||
                sisa.getText().equals("")||
           
            alasan.getText().equals("") ||
                 hak.getText().equals(""))
        {
            JOptionPane.showMessageDialog (rootPane,"Data belum lengkap.");
        }
        else {
            String Tanggal = ((JTextField)tgl_mulai.getDateEditor().getUiComponent()).getText();
            String Tanggal1 = ((JTextField)tgl_selesai.getDateEditor().getUiComponent()).getText();
            String ID= tid.getText();
              String Nik= tnik.getText();
           String Jumlah = tjumlah.getText();
           String Sisa = sisa.getText();
            
            String Alasan = alasan.getText();
            String Hak = hak.getText();
            
            try {

                Date skrg=new Date();
                SimpleDateFormat frm=new SimpleDateFormat("yyyy-MM-dd");
                String tgl=frm.format(skrg);
                conn = DriverManager.getConnection("jdbc:mysql://localhost/cuti","root","");
                stmt = conn.createStatement();
                stmt.executeUpdate("insert into cuti_karyawan values('"+ID+"','"+Nik+"','"+Tanggal+"','"+Tanggal1+"','"+Jumlah+"','"+Sisa+"','"+Alasan+"','"+Hak+"')");
                JOptionPane.showMessageDialog(null,"Data Berhasil DiSimpan!");

            }
            catch(Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
        BacaTabelKaryawan();
        bersih();

    }//GEN-LAST:event_bt_simpanActionPerformed

    private void tidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidActionPerformed

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        boolean closable = true;
        Data_Karyawan dataKaryawan=new Data_Karyawan(null, closable);
        dataKaryawan.cuti = this;
        dataKaryawan.setVisible(true);
        dataKaryawan.setResizable(true);
        tnik.setText(Nik);
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tjumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tjumlahActionPerformed
        // TODO add your handling code here:
        int hakcuti,jumlahcuti,sisacuti;

        hakcuti=Integer.parseInt(hak.getText());
        jumlahcuti=Integer.parseInt(tjumlah.getText());
        sisacuti=hakcuti-jumlahcuti;
        sisa.setText(Integer.toString(sisacuti));
    }//GEN-LAST:event_tjumlahActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try{
            String path="src/laporan/LaporanCutiKaryawan.jasper";
            HashMap parameter = new HashMap();
            JasperPrint print = JasperFillManager.fillReport(path, parameter,kon.setKoneksi());
            JasperViewer.viewReport(print,false);
        }
        catch(Exception e){
            JOptionPane.showConfirmDialog(null, e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void tbl_cutiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cutiMouseClicked
        // TODO add your handling code here:
         SetTabel();
    }//GEN-LAST:event_tbl_cutiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alasan;
    private javax.swing.JButton bt_simpan;
    private javax.swing.JTextField hak;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField sisa;
    private javax.swing.JTable tbl_cuti;
    private javax.swing.JTextField tcari;
    private com.toedter.calendar.JDateChooser tgl_mulai;
    private com.toedter.calendar.JDateChooser tgl_selesai;
    private javax.swing.JTextField tid;
    private javax.swing.JTextField tjumlah;
    private javax.swing.JTextField tnik;
    // End of variables declaration//GEN-END:variables
}
