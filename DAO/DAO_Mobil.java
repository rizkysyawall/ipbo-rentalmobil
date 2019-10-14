/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Koneksi.Database;
import Model.Mobil;
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
 * @author panji
 */
public class DAO_Mobil implements Model_DAO<Mobil> {
    
    public DAO_Mobil(){
       connection= Database.KoneksiDB();
    }
    Connection connection;
    String INSERT = "INSERT INTO mobil(kdmobil,merek,model,thnbuat, nonpwp, nmcabang, jmlmobil, hrgsewa) values(?,?,?,?,?,?,?,?)";
    String UPDATE = "UPDATE mobil SET merek=?, model=?, thnbuat=?,jmlmobil=?, hrgsewa=?, nonpwp=?, nmcabang=? WHERE kdmobil=?";
    String DELETE = "DELETE FROM mobil WHERE kdmobil=?";
    String SELECT = "SELECT * FROM mobil";
    String CARI =   "SELECT * FROM mobil WHERE kdmobil LIKE ?";
    String COMBO =  "SELECT * FROM rental ORDER BY nonpwp";
    String COUNTER = "SELECT ifnull(max(convert(right(kdmobil,2),signed integer)),0) as kode,"
                    + "ifnull(length(max(convert(right(kdmobil,2),signed integer))),0) as panjang "
                    + "from mobil where nonpwp=?";

    @Override
    public int autonumber(Mobil object) {
   return 0;
    }

    @Override
    public void insert(Mobil object) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CARI);
            statement.setString(1, object.getKdmobil());
            ResultSet rs = statement.executeQuery();
            if(rs.next())
                JOptionPane.showMessageDialog(null, "Data sudah pernah di simpan");
            else{
                PreparedStatement statement2 = null;
                statement2 = connection.prepareStatement(INSERT);
                statement2.setString(1, object.getKdmobil());
                statement2.setString(2, object.getMerek());
                statement2.setString(3, object.getModel());
                statement2.setInt(4, object.getThnmobil());
                statement2.setString(5, object.getNonpwp());
                statement2.setString(6, object.getNmcabang());
                statement2.setInt(7, object.getJmlmobil());
                statement2.setInt(8, object.getHargasewa());
                statement2.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data berhasil di simpan");
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO Insert",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally{
            try{
                statement.close();
            } catch (SQLException ex){
                Logger.getLogger(DAO_Mobil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(Mobil object) {
        PreparedStatement statement = null;
        try{
            statement = connection.prepareStatement(CARI);
            statement.setString(1,object.getKdmobil());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                statement = connection.prepareStatement(UPDATE);
                statement.setString(1, object.getMerek());
                statement.setString(2, object.getModel());
                statement.setInt(3, object.getThnmobil());
                statement.setInt(4, object.getJmlmobil());
                statement.setInt(5, object.getHargasewa());
                statement.setString(6, object.getNonpwp());
                statement.setString(7, object.getNmcabang());
                statement.setString(8, object.getKdmobil());
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data berhasil diubah");
            }else{
                JOptionPane.showMessageDialog(null,"Data belum pernah disimpan");
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO Update",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }finally{
            try{
                statement.close();
            }catch(SQLException err){
               Logger.getLogger(DAO_Mobil.class.getName()).log(Level.SEVERE,null,err);
            }
        }
    }

    @Override
    public List<Mobil> getAll() {
         List<Mobil> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Mobil>();
            statement = connection.prepareStatement(SELECT);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Mobil M = new Mobil();
                M.setNonpwp(rs.getString("nonpwp"));
                M.setNmcabang(rs.getString("nmcabang"));
                M.setKdmobil(rs.getString("kdmobil"));
                M.setMerek(rs.getString("merek"));
                M.setModel(rs.getString("model"));
                M.setThnmobil(rs.getInt("thnbuat"));
                M.setJmlmobil(rs.getInt("jmlmobil"));
                M.setHargasewa(rs.getInt("hrgsewa"));
                
                list.add(M);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO getAll",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Mobil> getCari(String key) {
        List<Mobil> list = null;
        PreparedStatement statement = null;
        try{
            list = new ArrayList<Mobil>();
            statement = connection.prepareStatement(CARI);
            statement.setString(1, "%"+key+"%");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Mobil M = new Mobil();
                M.setNonpwp(rs.getString("nonpwp"));
                M.setNmcabang(rs.getString("nmcabang"));
                M.setKdmobil(rs.getString("kdmobil"));
                M.setMerek(rs.getString("merek"));
                M.setModel(rs.getString("model"));
                M.setThnmobil(rs.getInt("thnbuat"));
                M.setJmlmobil(rs.getInt("jmlmobil"));
                M.setHargasewa(rs.getInt("hrgsewa"));
                list.add(M);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO getCari",JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    public List<Mobil> getComboNPWP(){
        PreparedStatement statement = null;
        List<Mobil> list = null;
        try{
            list = new ArrayList<Mobil>();
            statement = connection.prepareStatement(COMBO);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Mobil M = new Mobil();
                M.setNonpwp(rs.getString("nonpwp"));
                list.add(M);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO getComboNPWP",JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    public List<Mobil> getNamaCabang(String id){
        PreparedStatement statement = null;
        List<Mobil> list = null;
        try{
            list = new ArrayList<Mobil>();
            String CARIDATA = "SELECT * FROM rental WHERE nonpwp=?";
            statement = connection.prepareStatement(CARIDATA);
            statement.setString(1,id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Mobil M = new Mobil();
                M.setNmcabang(rs.getString("nmcabang"));
                list.add(M);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO get Nama Cabang",JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    public List<Mobil> getStokMobil(String id){
        PreparedStatement statement = null;
        List<Mobil> list = null;
        try{
            list = new ArrayList<Mobil>();
            String CARIDATA="SELECT * FROM rental WHERE nonpwp=?";
            statement = connection.prepareStatement(CARIDATA);
            statement.setString(1,id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Mobil M = new Mobil();
                M.setJmlstok(rs.getInt("jmlstok"));
                list.add(M);
            }
        }catch(Exception e){
          JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO get stok mobil",JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    public String autonumber2 (String id){
        PreparedStatement statement = null;
        int nomor_berikutnya = 0;
        String urutan = "";
        try {
         
        statement = connection.prepareStatement(COUNTER);
        statement.setString(1,id);
        ResultSet rs2 = statement.executeQuery();
        if (rs2.next()) {
            nomor_berikutnya = rs2.getInt("kode")+1;
            if (rs2.getInt("kode") != 0){
                urutan = "0" + nomor_berikutnya;
                urutan = "KM" + id + urutan.substring(urutan.length()-2);      
            }
            else {
                urutan = "KM" + id + "01";
            }
        }else
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO autonumber2",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return urutan;
        }
    
    public void update_stok(Mobil object){
        PreparedStatement statement;
        int stokfinal = 0;
        try{
            String UPDATE_STOK = "SELECT jmlstok FROM rental WHERE nonpwp=?";
            statement = connection.prepareStatement(UPDATE_STOK);
            statement.setString(1,object.getNonpwp());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                if(rs.getInt("jmlstok")<1){
                    JOptionPane.showMessageDialog(null,"Stok Mobil NO NPWP: "+object.getNonpwp()+"Kosong");
                    stokfinal=0;
                }else{
                    //hitung stok akhir berdasarkan qty masing2 kode barang yg ada di tabel
                    stokfinal = rs.getInt("jmlstok") - object.getJmlmobil();
                    
                    //update stok barang ke dalam tabel barang
                    PreparedStatement statement2;
                    String updatestok = "UPDATE rental SET jmlstok=? WHERE nonpwp=?";
                    statement2 = connection.prepareStatement(updatestok);
                    statement2.setInt(1, stokfinal);
                    statement2.setString(2,object.getNonpwp());
                    statement2.executeUpdate();
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error update stok",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete(String id) {
        PreparedStatement statement = null;
        String confirm="";
        try{
            statement = connection.prepareStatement(DELETE);
            statement.setString(1, id);
            confirm = JOptionPane.showInputDialog(null,"Apakah anda yakin untuk menghapus data?(Y/T)","Konfirmasi Hapus Data",JOptionPane.QUESTION_MESSAGE);
            if(confirm.equals("Y") || confirm.equals("y")){
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data berhasil dihapus");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO delete",JOptionPane.ERROR_MESSAGE);
        }finally{
            try{
                statement.close();
            }catch(SQLException err){
               Logger.getLogger(DAO_Mobil.class.getName()).log(Level.SEVERE,null,err);
            }
        }
    }
}
