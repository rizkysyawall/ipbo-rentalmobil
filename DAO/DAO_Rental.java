/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Koneksi.Database;
import Model.Rental;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Me
 */
public class DAO_Rental implements Model_DAO<Rental>{
//konstruktor
    public DAO_Rental(){
        connection = Database.KoneksiDB();
    }
    Connection connection;
    String INSERT = "INSERT INTO rental(nonpwp,nmcabang,alamatrental,notlprental,jmlstok) values(?,?,?,?,?)";
    String UPDATE = "UPDATE rental SET nmcabang=?, alamatrental=?, notlprental=?, jmlstok=? WHERE nonpwp=?";
    String DELETE = "DELETE FROM rental WHERE nonpwp=?";
    String SELECT = "SELECT * FROM rental";
    String CARI = "SELECT * FROM rental WHERE nonpwp LIKE ? or nmcabang LIKE ? or alamatrental LIKE ?";
    String COUNTER = "SELECT max(nonpwp)as kode FROM rental";
    String CARIRENT =  "SELECT * FROM rental WHERE nonpwp LIKE ?";


    @Override
    public void insert(Rental object) {
            PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CARIRENT);
            statement.setString(1, object.getKdnpwp());
          //  statement.setString(2, object.getNmcabang());
            //statement.setString(3, object.getAlmtrental());
            ResultSet rs = statement.executeQuery();
            if (rs.next()){ //jika data sudah pernah disimpan
                JOptionPane.showMessageDialog(null, "Data Sudah Pernah Disimpan!");
            }else{ // jika data belum pernah disimpan
                PreparedStatement statement2 = null;
                statement2 = connection.prepareStatement(INSERT);
                statement2.setString(1, object.getKdnpwp());
                statement2.setString(2, object.getNmcabang());
                statement2.setString(3, object.getAlmtrental());
                statement2.setString(4, object.getTelprental());
                statement2.setInt(5, object.getJmlmobil());
                statement2.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO Insert",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }finally{
            try{
                statement.close();
            }catch(SQLException ex){
                Logger.getLogger(DAO_Rental.class.getName()).log(Level.SEVERE,null,ex);
                
            }
        }
    }

    @Override
    public void update(Rental object) {
            PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, object.getNmcabang());
            statement.setString(2, object.getAlmtrental());
            statement.setString(3, object.getTelprental());
            statement.setInt(4, object.getJmlmobil());
            statement.setString(5, object.getKdnpwp());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
            statement.close();
        }catch (SQLException ex){
                Logger.getLogger(DAO_Rental.class.getName()).log(Level.SEVERE,null,ex);
                }
         }
    }

    @Override
    public List<Rental> getAll() {
        List<Rental> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Rental>();
            statement = connection.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Rental p = new Rental();
                p.setKdnpwp(rs.getString("nonpwp"));
                p.setNmcabang(rs.getString("nmcabang"));
                p.setAlmtrental(rs.getString("alamatrental"));
                p.setTelprental(rs.getString("notlprental"));
                p.setJmlmobil(rs.getInt("jmlstok"));
                list.add(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Rental> getCari(String key) {
        List<Rental> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Rental>();
            statement = connection.prepareStatement(CARI);
            statement.setString(1, "%"+key+"%");
            statement.setString(2, "%"+key+"%");
             statement.setString(3, "%"+key+"%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Rental p = new Rental();
                p.setKdnpwp(rs.getString("nonpwp"));
                p.setNmcabang(rs.getString("nmcabang"));
                p.setAlmtrental(rs.getString("alamatrental"));
                p.setTelprental(rs.getString("notlprental"));
                p.setJmlmobil(rs.getInt("jmlstok"));
                list.add(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int autonumber(Rental object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String id) {
     PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(DELETE);
            statement.setString(1, id);
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                statement.close();
            }catch (SQLException ex){
              Logger.getLogger(DAO_Rental.class.getName()).log(Level.SEVERE,null, ex);
            }
        }
    }
    
}
