/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_Rental;
import DAO.Model_DAO;
import Model.Rental;
import View.MRental;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Me
 */
public class Controller_Rental {
     MRental form;
    Model_DAO<Rental> model;
    List<Rental> list; // deklarasi method "List" yang sudah dibuat pada interface MODEL_DAO
    String[]header; // deklarasi variable untuk membuat judul kolom pada objek JTable
    
    //konstruktor
    public Controller_Rental (MRental form){
        this.form = form;
        model = new DAO_Rental();
        list = model.getAll();
        header = new String[]{"NO NPWP", "NAMA CABANG","ALAMAT RENTAL","TELEPON RENTAL","JUMLAH STOK MOBIL"};
      
        form.getTblrental().setShowGrid(true);
        form.getTblrental().setShowVerticalLines(true);
        form.getTblrental().setGridColor(Color.blue);
    }
    
    
    public void reset(){
        form.getTxtkdnpwp().setText("");
        form.getTxtnmcabang().setText("");
        form.getTxtalmtrental().setText("");
        form.getTxttlprental().setText("");
        form.getTxtjmlmobil().setText("");
        form.getTxtnmcabang().requestFocus();
        isiTable();
    }
    
        public void isiTable(){
        list = model.getAll();
        
        // script agar JTable tidak bisa diedit
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header){
            public boolean isCellEdittable(int rowIndex, int columnIndex){
                return false;
            }
        };
        
        Object[] data = new Object[header.length];
        for (Rental p : list){
            data[0] = p.getKdnpwp();
            data[1] = p.getNmcabang();
            data[2] = p.getAlmtrental();
            data[3] = p.getTelprental();
            data[4] = p.getJmlmobil();
            tblModel.addRow(data);
        }
        form.getTblrental().setModel(tblModel);
    }
        
         public void isiField(int row){
        form.getTxtkdnpwp().setText(String.valueOf(list.get(row).getKdnpwp()));
        form.getTxtnmcabang().setText(list.get(row).getNmcabang());
        form.getTxtalmtrental().setText(list.get(row).getAlmtrental());
        form.getTxttlprental().setText(list.get(row).getTelprental());
        form.getTxtjmlmobil().setText(String.valueOf(list.get(row).getJmlmobil()));
    }
    
    //method untuk simpan data
    public void insert(){
        Rental p = new Rental();
        p.setKdnpwp(form.getTxtkdnpwp().getText());
        p.setNmcabang(form.getTxtnmcabang().getText());
        p.setAlmtrental(form.getTxtalmtrental().getText());
        p.setTelprental(form.getTxttlprental().getText());
        p.setJmlmobil(Integer.parseInt(form.getTxtjmlmobil().getText()));
        
        model.insert(p);
    }
    
    //method untuk mengubah data
    public void update(){
        Rental p = new Rental();
        p.setKdnpwp(form.getTxtkdnpwp().getText());
        p.setNmcabang(form.getTxtnmcabang().getText());
        p.setAlmtrental(form.getTxtalmtrental().getText());
        p.setTelprental(form.getTxttlprental().getText());
        p.setJmlmobil(Integer.parseInt(form.getTxtjmlmobil().getText()));
        
        model.update(p);
    }
    
        // method untuk menghapus data
    public void delete(){
        if(!form.getTxtkdnpwp().getText().trim().isEmpty()){
            String kode = (form.getTxtkdnpwp().getText());
            model.delete(kode);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih Data Yang Akan Dihapus");
        }
    }
    
    //method ini akan dipakai untuk memfilter data berdasarkan inputan yang ada pada text kode petugas
    public void isiTableCari(){
        list = model.getCari(form.getTxtkatakunci().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{}, header);
        Object[] data = new Object[header.length];
        for (Rental p : list){
            data[0] = p.getKdnpwp();
            data[1] = p.getNmcabang();
            data[2] = p.getAlmtrental();
            data[3] = p.getTelprental();
            data[4] = p.getJmlmobil();
            tblModel.addRow(data);
        }
        form.getTblrental().setModel(tblModel);
    }
}
