/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Koneksi.Database;
import Model.Pelanggan;
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
public class DAO_Pelanggan implements Model_DAO<Pelanggan> {
    //konstruktor
    public DAO_Pelanggan(){
        connection = Database.KoneksiDB();
    }
    Connection connection;
    String INSERT = "INSERT INTO pelanggan(noidplg,jenisidplg,nmplg,alamatplg,tgllahirplg,notlpplg) values(?,?,?,?,?,?)";
    String UPDATE = "UPDATE pelanggan SET jenisidplg=?, nmplg=?, alamatplg=?, tgllahirplg=?, notlpplg=? WHERE noidplg=?";
    String DELETE = "DELETE FROM pelanggan WHERE noidplg=?";
    String SELECT = "SELECT * FROM pelanggan";
    String CARI = "SELECT * FROM pelanggan WHERE noidplg LIKE ? or jenisidplg LIKE ? or nmplg LIKE ?";
    String COUNTER = "SELECT max(noidplg)as kode FROM pelanggan";
    String CARIPEL = "SELECT * FROM pelanggan WHERE noidplg LIKE ?";

    @Override
    public int autonumber(Pelanggan object) {
    return 0;
    }

    @Override
    public void insert(Pelanggan object) {
    PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CARIPEL);
            statement.setString(1, object.getNoidplg());
            ResultSet rs = statement.executeQuery();
            if (rs.next()){ //jika data sudah pernah disimpan
                JOptionPane.showMessageDialog(null, "Data Sudah Pernah Disimpan!");
            }else{ // jika data belum pernah disimpan
                PreparedStatement statement2 = null;
                statement2 = connection.prepareStatement(INSERT);
                statement2.setString(1, object.getNoidplg());
                statement2.setString(2, object.getJenisidplg());
                statement2.setString(3, object.getNamaplg());
                statement2.setString(4, object.getAlamatplg());
                statement2.setString(5, object.getTgllahir());
                statement2.setString(6, object.getNotlpplg());
                statement2.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                statement.close();
            }catch(SQLException ex){
                Logger.getLogger(DAO_Pelanggan.class.getName()).log(Level.SEVERE,null,ex);
                
            }
        }
    }

    @Override
    public void update(Pelanggan object) {
 PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(UPDATE);
                statement.setString(1, object.getJenisidplg());
                statement.setString(2, object.getNamaplg());
                statement.setString(3, object.getAlamatplg());
                statement.setString(4, object.getTgllahir());
                statement.setString(5, object.getNotlpplg());
                statement.setString(6, object.getNoidplg());
            statement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
            statement.close();
        }catch (SQLException ex){
                Logger.getLogger(DAO_Pelanggan.class.getName()).log(Level.SEVERE,null,ex);
                }
         }
    }

    @Override
    public List<Pelanggan> getAll() {
        List<Pelanggan> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Pelanggan>();
            statement = connection.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Pelanggan p = new Pelanggan();
                p.setNoidplg(rs.getString("noidplg"));
                p.setJenisidplg(rs.getString("jenisidplg"));
                p.setNamaplg(rs.getString("nmplg"));
                p.setAlamatplg(rs.getString("alamatplg"));
                p.setTgllahir(rs.getString("tgllahirplg"));
                p.setNotlpplg(rs.getString("notlpplg"));
                list.add(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Pelanggan> getCari(String key) {
        List<Pelanggan> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Pelanggan>();
            statement = connection.prepareStatement(CARI);
            statement.setString(1, "%"+key+"%");
            statement.setString(2, "%"+key+"%");
             statement.setString(3, "%"+key+"%");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                Pelanggan p = new Pelanggan();
                p.setNoidplg(rs.getString("noidplg"));
                p.setJenisidplg(rs.getString("jenisidplg"));
                p.setNamaplg(rs.getString("nmplg"));
                p.setAlamatplg(rs.getString("alamatplg"));
                p.setTgllahir(rs.getString("tgllahirplg"));
                p.setNotlpplg(rs.getString("notlpplg"));
                list.add(p);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
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
              Logger.getLogger(DAO_Pelanggan.class.getName()).log(Level.SEVERE,null, ex);
            }
        }
    }
    
}
