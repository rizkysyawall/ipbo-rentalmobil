/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_Pelanggan;
import DAO.Model_DAO;
import Model.Pelanggan;
import View.MPelanggan;
import java.awt.Color;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Me
 */
public class Controller_Pelanggan {
    MPelanggan form;
    Model_DAO<Pelanggan> model;
    List<Pelanggan> list; // deklarasi method "List" yang sudah dibuat pada interface MODEL_DAO
    String[]header; // deklarasi variable untuk membuat judul kolom pada objek JTable
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String tanggal;
    
    //konstruktor
    public Controller_Pelanggan (MPelanggan form){
        this.form = form;
        model = new DAO_Pelanggan();
        list = model.getAll();
        header = new String[]{"NO ID", "JENIS ID","NAMA","ALAMAT","TANGGAL LAHIR","NO TELPON"};
      
        form.getTblplg().setShowGrid(true);
        form.getTblplg().setShowVerticalLines(true);
        form.getTblplg().setGridColor(Color.blue);

    }
    public void reset(){
        form.getTxtnoidplg().setText("");
        form.getCmbjenisidplg().setSelectedItem("");
        form.getTxtnmplg().setText("");
        form.getTxtalamatplg().setText("");
        
        //form.getJtgllahir().setDate(null);
        form.getTxtnotlp().setText("");
        form.getTxtnoidplg().requestFocus();
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
        for (Pelanggan p : list){
            data[0] = p.getNoidplg();
            data[1] = p.getJenisidplg();
            data[2] = p.getNamaplg();
            data[3] = p.getAlamatplg();
            data[4] = p.getTgllahir();
            data[5] = p.getNotlpplg();
            tblModel.addRow(data);
        }
        form.getTblplg().setModel(tblModel);
    }
        
         public void isiField(int row){
        form.getTxtnoidplg().setText(list.get(row).getNoidplg());
        form.getCmbjenisidplg().setSelectedItem(list.get(row).getJenisidplg());
        form.getTxtnmplg().setText(list.get(row).getNamaplg());
        form.getTxtalamatplg().setText(list.get(row).getAlamatplg());
//        form.getJtgllahir().setDate(Date.valueOf(list.get(row).getTanggallhr()));
        form.getTxtnotlp().setText(String.valueOf(list.get(row).getNotlpplg()));
    }
    
    //method untuk simpan data
    public void insert(){
        Pelanggan p = new Pelanggan();
        tanggal = sdf.format(form.getTgllahir().getDate());
        p.setNoidplg(form.getTxtnoidplg().getText());
        p.setJenisidplg(form.getCmbjenisidplg().getSelectedItem().toString());
        p.setNamaplg(form.getTxtnmplg().getText());
        p.setAlamatplg(form.getTxtalamatplg().getText());
        p.setTgllahir(tanggal);
        p.setNotlpplg(form.getTxtnotlp().getText());
        
        model.insert(p);
    }
    
    //method untuk mengubah data
    public void update(){
        Pelanggan p = new Pelanggan();
        tanggal = sdf.format(form.getTgllahir().getDate());
        p.setNoidplg(form.getTxtnoidplg().getText());
        p.setJenisidplg(form.getCmbjenisidplg().getSelectedItem().toString());
        p.setNamaplg(form.getTxtnmplg().getText());
        p.setAlamatplg(form.getTxtalamatplg().getText());
        p.setTgllahir(tanggal);
        p.setNotlpplg(form.getTxtnotlp().getText());
        
        model.update(p);
    }
    
        // method untuk menghapus data
    public void delete(){
        if(!form.getTxtnoidplg().getText().trim().isEmpty()){
            String kode = (form.getTxtnoidplg().getText());
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
        for (Pelanggan p : list){
            data[0] = p.getNoidplg();
            data[1] = p.getJenisidplg();
            data[2] = p.getNamaplg();
            data[3] = p.getAlamatplg();
            data[4] = p.getTgllahir();
            data[5] = p.getNotlpplg();
            tblModel.addRow(data);
        }
        form.getTblplg().setModel(tblModel);
    }
    

}