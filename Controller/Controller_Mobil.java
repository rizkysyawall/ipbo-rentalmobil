/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_Mobil;
import Model.Mobil;
import View.MMobil;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author panji
 */
public class Controller_Mobil {
    MMobil form;
    DAO_Mobil model;
    List<Mobil> list;
    String[] header;
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    String tahun;
    
    public Controller_Mobil(MMobil form){
        this.form = form;
        model = new DAO_Mobil();
        list = model.getAll();
        header = new String[]{"NO NPWP","NAMA CABANG","KODE MOBIL","MEREK","MODEL","TAHUN BUAT","JUMLAH MOBIL","HARGA SEWA"};
   
        
        form.getTblmobil().setShowGrid(true);
        form.getTblmobil().setShowVerticalLines(true);
        form.getTblmobil().setGridColor(java.awt.Color.PINK);
    }
    
    public void reset(){
        form.getCmbnonpwp().setSelectedItem(0);
        form.getTxtnmcabang().setText("");
        form.getTxtjmlstok().setText("");
        form.getTxtkdmobil().setText("");
        form.getTxtmerek().setText("");
        form.getTxtjmlmobil().setText("");
        form.getTxthrgsewa().setText("");
        form.getCmbmodel().setSelectedIndex(0);
        form.getTxtthnbuat().setYear(2000);
        form.getCmbnonpwp().requestFocus();
        form.getTxtkdmobil().setEnabled(true);
        form.getTxtmerek().setEnabled(true);
        form.getCmbmodel().setEnabled(true);
        form.getTxtthnbuat().setEnabled(true);
        form.getTxtjmlmobil().setEnabled(true);
        form.getTxtnmcabang().setEnabled(true);
        form.getTxtjmlstok().setEnabled(true);
        isiTable();
        isiNPWP();
    }
    
    public void isiTable(){
        list = model.getAll();
        DefaultTableModel tblModel = new DefaultTableModel (new Object[][]{}, header){
            public boolean isCellEditor(int rowIndex, int columnIndex){
                return false;
            }
        };
        
        Object[] data = new Object[header.length];
        for(Mobil M : list){
            data[0] = M.getNonpwp();
            data[1] = M.getNmcabang();
            data[2] = M.getKdmobil();
            data[3] = M.getMerek();
            data[4] = M.getModel();
            data[5] = M.getThnmobil();
            data[6] = M.getJmlmobil();
            data[7] = M.getHargasewa();
            tblModel.addRow(data);
        }
        form.getTblmobil().setModel(tblModel);
    }
    
    public void isiField(int row){
        tahun = sdf.format(form.getTxtthnbuat().getYear());
        form.getCmbnonpwp().setSelectedItem(list.get(row).getNonpwp());
        form.getTxtnmcabang().setText(list.get(row).getNmcabang());
        form.getTxtkdmobil().setText(list.get(row).getKdmobil());
        form.getTxtmerek().setText(list.get(row).getMerek());
        form.getCmbmodel().setSelectedItem(list.get(row).getModel());
        form.getTxtthnbuat().setYear(Integer.valueOf(list.get(row).getThnmobil().toString()));
        form.getTxtjmlmobil().setText(String.valueOf(list.get(row).getJmlmobil()));
        form.getTxthrgsewa().setText(String.valueOf(list.get(row).getHargasewa()));
        form.getCmbnonpwp().setEnabled(false);
        form.getTxtkdmobil().setEnabled(false);
    }
    
    public void insert(){
        Mobil M = new Mobil();
        tahun = sdf.format(form.getTxtthnbuat().getYear());
        M.setKdmobil(form.getTxtkdmobil().getText());
        M.setMerek(form.getTxtmerek().getText());
        M.setModel(form.getCmbmodel().getSelectedItem().toString());
        M.setThnmobil(form.getTxtthnbuat().getYear());
        M.setNonpwp(form.getCmbnonpwp().getSelectedItem().toString());
        M.setNmcabang(form.getTxtnmcabang().getText());
        M.setJmlmobil(Integer.parseInt(form.getTxtjmlmobil().getText()));
        M.setHargasewa(Integer.parseInt(form.getTxthrgsewa().getText()));
        model.insert(M);
        model.update_stok(M);
    }
    
    public void update(){
        Mobil M = new Mobil();
        tahun = sdf.format(form.getTxtthnbuat().getYear());
        M.setKdmobil(form.getTxtkdmobil().getText());
        M.setMerek(form.getTxtmerek().getText());
        M.setModel(form.getCmbmodel().getSelectedItem().toString());
        M.setThnmobil(form.getTxtthnbuat().getYear());
        M.setNonpwp(form.getCmbnonpwp().getSelectedItem().toString());
        M.setJmlmobil(Integer.parseInt(form.getTxtjmlmobil().getText()));
        M.setHargasewa(Integer.parseInt(form.getTxthrgsewa().getText()));
        model.update(M);
    }
    
    public void delete(){
        if(!form.getTxtkdmobil().getText().trim().isEmpty()){
            String kdmobil = (form.getTxtkdmobil().getText());
            model.delete(kdmobil);
        }else{
            JOptionPane.showMessageDialog(form, "Pilih data yang akan dihapus");
        }
    }
    
    public void isiTableCari(){
        list = model.getCari(form.getTxtkatakunci().getText().trim());
        DefaultTableModel tblModel = new DefaultTableModel (new Object[][]{}, header);
        Object[] data = new Object[header.length];
        for(Mobil M : list){
            data[0] = M.getNonpwp();
            data[1] = M.getNmcabang();
            data[2] = M.getKdmobil();
            data[3] = M.getMerek();
            data[4] = M.getModel();
            data[5] = M.getThnmobil();
            data[6] = M.getJmlmobil();
            data[7] = M.getHargasewa();
            tblModel.addRow(data);
        }
        form.getTblmobil().setModel(tblModel);
    }
    
    public void isiNPWP(){
        form.getCmbnonpwp().removeAllItems();
        form.getCmbnonpwp().addItem("Pilih");
        for(Mobil M : model.getComboNPWP()){
            form.getCmbnonpwp().addItem(M.getNonpwp());
        }
    }
    public void tampilnamacabang(){
        if (form.getCmbnonpwp().getSelectedIndex() != 0){
            for (Mobil M : model.getNamaCabang(form.getCmbnonpwp().getSelectedItem().toString())){
                form.getTxtnmcabang().setText(M.getNmcabang());
            }
        }
    }
    public void tampilstokmobil(){
        if (form.getCmbnonpwp().getSelectedIndex() != 0){
            for (Mobil M : model.getStokMobil(form.getCmbnonpwp().getSelectedItem().toString())){
                form.getTxtjmlstok().setText(String.valueOf(M.getJmlstok()));
            }
        }
    }
    
    public void tampilurutankode(){
        if (form.getCmbnonpwp().getSelectedIndex() > 0){
            form.getTxtkdmobil().setText(String.valueOf(model.autonumber2
           (form.getCmbnonpwp().getSelectedItem().toString())));
        }
    }
}
