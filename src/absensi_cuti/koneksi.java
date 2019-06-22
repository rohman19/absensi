/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package absensi_cuti;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author user
 */
public class koneksi {
Connection conn;
Statement st;
ResultSet rs;

    public Connection setKoneksi()
    {
            try{
        Class.forName("com.mysql.jdbc.Driver");
        conn=DriverManager.getConnection("jdbc:mysql://localhost/cuti","root","");
        st=conn.createStatement();
    }
catch(Exception e){
    JOptionPane.showMessageDialog(null,"koneksi gagal : "+e);
}
return conn;
}
}