/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Koneksi.Database;
import Model.BuktiSewa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DAO_BuktiSewa implements Model_DAO<BuktiSewa>{
        Connection connection;

    public DAO_BuktiSewa() {
        connection = Database.KoneksiDB();
}

    @Override
    public void insert(BuktiSewa object) {
        PreparedStatement statement;
        try{
            String SELECT = "select * from buktisewa where nobsm=?";
            statement = connection.prepareStatement(SELECT);
            statement.setString(1, object.getNosewa());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                JOptionPane.showMessageDialog(null,"Data sudah pernah disimpan","Error",JOptionPane.WARNING_MESSAGE);
            }else{
                //proses insert
                PreparedStatement statement2;
                String insert = "insert into buktisewa(nobsm,nmcabang,kdmobil,merek,hrgsewa,qty,total,tglsewa,tglkembali,nonpwp,noidplg) values(?,?,?,?,?,?,?,?,?,?,?)";
                statement2 = connection.prepareStatement(insert);
                statement2.setString(1, object.getNosewa());
                statement2.setString(2, object.getNmcabang());
                statement2.setString(3, object.getKdmobil());
                statement2.setString(4, object.getNmmobil());
                statement2.setInt(5, object.getHargasewa());
                statement2.setInt(6, object.getQty());
                statement2.setInt(7, object.getTotal());
                statement2.setString(8, object.getTglsewa());
                statement2.setString(9, object.getTglkembali());
                statement2.setString(10, object.getKdnpwp());
                statement2.setString(11, object.getNoidplg());
                statement2.executeUpdate();
                JOptionPane.showMessageDialog(null,"Data transaksi berhasil disimpan","Message",JOptionPane.INFORMATION_MESSAGE);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO Insert",JOptionPane.ERROR_MESSAGE);
        }
    }

    //untuk insert ke tabel detilpesan
    public void insert_detiltransaksi(BuktiSewa object){
        PreparedStatement statement;
        try{
            String insertdetil = "insert into detilsewa(nobsm,kdmobil,hrgsewa,jmlsewa) values(?,?,?,?)";
            statement = connection.prepareStatement(insertdetil);
            statement.setString(1, object.getNosewa());
            statement.setString(2, object.getKdmobil());
            statement.setInt(3, object.getHargasewa());
            statement.setInt(4, object.getQty());
            statement.executeUpdate();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error insert detiltransaksi",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //untuk update stok barang ke dalam tabel barang
    public void update_stok(BuktiSewa object){
        PreparedStatement statement;
        int stok_akhir = 0;
        try{
            String SELECT = "SELECT jmlmobil FROM mobil where kdmobil=?";
            statement = connection.prepareStatement(SELECT);
            statement.setString(1,object.getKdmobil());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                if(rs.getInt("jmlmobil")<0){
                    JOptionPane.showMessageDialog(null,"jmlmobil Kode Mobil: "+object.getKdmobil()+"Kosong!");
                    stok_akhir = 0;
                }else{
                    //hitung stok akhir berdasarkan qty masing2 kode barang yg ada di tabel
                    stok_akhir = rs.getInt("jmlmobil") - object.getQty();
                    
                    //update stok barang ke dalam tabel barang
                    PreparedStatement statement2;
                    String updatestok = "update mobil set jmlmobil=? where kdmobil=?";
                    statement2 = connection.prepareStatement(updatestok);
                    statement2.setInt(1, stok_akhir);
                    statement2.setString(2,object.getKdmobil());
                    statement2.executeUpdate();
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error update stok",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //method untuk membuat penomoran buktipesan secara otomatis
    public String autonumber2(){
        PreparedStatement statement;
        int nomor_berikutnya = 0;
        String urutan = "";
        try{
            String COUNTER = "SELECT ifnull(max(convert(right(nobsm,5),signed integer)),0) as kode,"
                            +"ifnull(length(max(convert(right(nobsm,5)+1,signed integer))),0) as panjang from buktisewa";
            statement = connection.prepareStatement(COUNTER);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                nomor_berikutnya = rs.getInt("kode")+1;
                if(rs.getInt("kode")!=0){
                    urutan = "0000" + nomor_berikutnya;
                    urutan = "BP" + urutan.substring(urutan.length()-5);
                }else{
                    urutan = "BP" + "00001";
                }
            }else{//jika kode kategori belum pernah ada
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan","Error rs autonumber",JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error autonumber",JOptionPane.ERROR_MESSAGE);
        }return urutan;
    }
    
    //==========================Combo Pelanggan==========================
    //method untuk menampilkan nama pelanggan kedalam combo nama pelanggan
    public List<BuktiSewa> isicomboplg(){
        PreparedStatement statement;
        List<BuktiSewa> list = null;
        try{
            list = new ArrayList();
            String TAMPILPELANGGAN = "select * from pelanggan order by nmplg";
            statement = connection.prepareStatement(TAMPILPELANGGAN);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                BuktiSewa b = new BuktiSewa();
                b.setNamaplg(rs.getString("nmplg"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error DAO List isicomboplg",JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    //method untuk menampilkan kode pelanggan berdasarkan inputan combo nama pelanggan
    public List<BuktiSewa> getkdplg(String namaplg){
        PreparedStatement statement;
        List<BuktiSewa> list = null;
        try{
            list = new ArrayList();
            String CARIPELANGGAN = "select * from pelanggan where nmplg = ?";
            statement = connection.prepareStatement(CARIPELANGGAN);
            statement.setString(1,namaplg);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                BuktiSewa b = new BuktiSewa();
                b.setNoidplg(rs.getString("noidplg"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error DAO getnoidplg",JOptionPane.ERROR_MESSAGE);
        }return list;
    }
    
    //method untuk menampilkan nama pelanggan berdasarkan inputan text kode pelanggan
    public List<BuktiSewa> getnmplg(Integer kode){
        PreparedStatement statement;
        List<BuktiSewa> list = null;
        try{
            list = new ArrayList();
            String CARIPELANGGAN = "SELECT * FROM pelanggan where noidplg = ?";
            statement = connection.prepareStatement(CARIPELANGGAN);
            statement.setInt(1, kode);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(null,"Data tidak ditemukan","Warning",JOptionPane.WARNING_MESSAGE);
            }else{
                BuktiSewa b = new BuktiSewa();
                b.setNamaplg(rs.getString("nmplg"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO getnmplg",JOptionPane.ERROR_MESSAGE);
        }return list;
    }
    
    //=========================Combo Cabang=========================
    //method untuk menampilkan nama Cabang kedalam combo nama Cabang
    public List<BuktiSewa> isicombocabang(){
        PreparedStatement statement;
        List<BuktiSewa> list = null;
        try{
            list = new ArrayList();
            String TAMPILCABANG = "SELECT * FROM rental order by nmcabang";
            statement = connection.prepareStatement(TAMPILCABANG);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                BuktiSewa b = new BuktiSewa();
                b.setNmcabang(rs.getString("nmcabang"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO isicombocabang",JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
    
    //method ini untuk nampilin kode npwp berdasarkan inputan combo nama cabang
    public List<BuktiSewa> getnonpwp(String namacab){
        PreparedStatement statement;
        List<BuktiSewa> list = null;
        try{
            list = new ArrayList();
            String CARICABANG = "SELECT * FROM rental where nmcabang = ?";
            statement = connection.prepareStatement(CARICABANG);
            statement.setString(1, namacab);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                BuktiSewa b = new BuktiSewa();
                b.setKdnpwp(rs.getString("nonpwp"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO getnonpwp",JOptionPane.ERROR_MESSAGE);
        }return list;
    }
    
    //method untuk nampilin nama cabang berdasarkan inputan text kode npwp
    public List<BuktiSewa> getnmcabang(String kode){
        PreparedStatement statement;
        List<BuktiSewa> list = null;
        try{
            list = new ArrayList();
            String CARICABANG = "SELECT * FROM rental where nonpwp=?";
            statement = connection.prepareStatement(CARICABANG);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            if(!rs.next()){
                JOptionPane.showMessageDialog(null,"Data tidak ditemukan","Warning",JOptionPane.WARNING_MESSAGE);
            }else{
                BuktiSewa b = new BuktiSewa();
                b.setNmcabang(rs.getString("nmcabang"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(),"Error DAO getnmcabang",JOptionPane.ERROR_MESSAGE);
        }return list;
        
    }
    
    //==========================Combo Mobil==========================
    //ngisi data kedlm combo nama mobil berdasarkan inputan kode rental
    public List<BuktiSewa> isicombomobil(String kode){
        PreparedStatement statement;
        List<BuktiSewa> list =  null;
        try{
            list = new ArrayList();
            String TAMPILMOBIL = "SELECT * FROM mobil where nonpwp = ? order by merek";
            statement = connection.prepareStatement(TAMPILMOBIL);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                BuktiSewa b = new BuktiSewa();
                b.setNmmobil(rs.getString("merek"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO isicombomobil",JOptionPane.ERROR_MESSAGE);
        }return list;
    }
    
    //==========================Combo Mobil==========================
    //ngisi data kedlm combo nama mobil berdasarkan inputan kode mobil
    public List<BuktiSewa> isicombomobil2(String kode){
        PreparedStatement statement;
        List<BuktiSewa> list =  null;
        try{
            list = new ArrayList();
            String TAMPILMOBIL = "SELECT * FROM mobil where kdmobil = ? order by merek";
            statement = connection.prepareStatement(TAMPILMOBIL);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                BuktiSewa b = new BuktiSewa();
                b.setNmmobil(rs.getString("merek"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO isicombomobil2",JOptionPane.ERROR_MESSAGE);
        }return list;
    }
    
    //method ini untuk nampilin data mobil berdasarkan inputan nama merek
    public List<BuktiSewa> getkdmobil(String nmmobil){
        PreparedStatement statement;
        List<BuktiSewa> list = null;
        try{
            list = new ArrayList();
            String CARIMOBIL2 = " SELECT * FROM mobil where merek = ?";
            statement = connection.prepareStatement(CARIMOBIL2);
            statement.setString(1, nmmobil);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                BuktiSewa b = new BuktiSewa();
                b.setKdmobil(rs.getString("kdmobil"));
                b.setHargasewa(rs.getInt("hrgsewa"));
                b.setJmlmobil(rs.getInt("jmlmobil"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error DAO getkdmobil",JOptionPane.ERROR_MESSAGE);
        }return list;
    }
    
    //method ini untuk nampilin data mobil beserta cabang bedasarkan inputan kode mobil
    public List<BuktiSewa> getnmmobil(String kode){
        PreparedStatement statement;
        List<BuktiSewa> list = null;
        try{
            list = new ArrayList();
            String CARIMOBIL = "SELECT a.*,b.* FROM mobil a,rental b where a.nonpwp=b.nonpwp and kdmobil = ?";
            statement = connection.prepareStatement(CARIMOBIL);
            statement.setString(1, kode);
            ResultSet rs = statement.executeQuery();
            BuktiSewa b = new BuktiSewa();
            if(!rs.next()){
                JOptionPane.showMessageDialog(null,"Data tidak ditemukan","Warning",JOptionPane.WARNING_MESSAGE);
                b.setKdnpwp("");
                b.setNmcabang("-Pilih-");
                b.setKdmobil("");
                b.setNmmobil("");
                b.setHargasewa(0);
                b.setJmlmobil(0);
                list.add(b);
            }else{
                b.setKdnpwp(rs.getString("a.nonpwp"));
                b.setNmcabang(rs.getString("nmcabang"));
                b.setNmmobil(rs.getString("merek"));
                b.setHargasewa(rs.getInt("hargasewa"));
                b.setJmlmobil(rs.getInt("jmlmobil"));
                list.add(b);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage(),"Error getnmbrg",JOptionPane.ERROR_MESSAGE);
        }return list;
    }
    

    @Override
    public int autonumber(BuktiSewa object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(BuktiSewa object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BuktiSewa> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<BuktiSewa> getCari(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}