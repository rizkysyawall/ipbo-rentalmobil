/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.DAO_BuktiSewa;
import Model.BuktiSewa;
import View.TBuktiSewa;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Controller_BuktiSewa {
    
    TBuktiSewa form;
    DAO_BuktiSewa model;
    
    //deklarasi var utk bikin judul kolom pada objek JTable
    String[] header = new String[]{"Kode NPWP","Nama CABANG","Kode MOBIL","Nama MOBIL","TANGGALSEWA","TANGGAL KEMBALI","Harga SEWA","Qty","Total"};
    
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String tanggalSewa;
    String tanggalKembali;
    long lamaSewa;
    DefaultTableModel tblModel = new DefaultTableModel(new Object[][]{},header){    
        @Override
        //supaya grid JTable tidak bs diedit
        public boolean isCellEditable(int rowIndex, int columnIndex){
            return false;
        }        
    };
    
    public Controller_BuktiSewa(TBuktiSewa form) {
        this.form = form;
        model = new DAO_BuktiSewa();   
        form.getTblbuktisewa().setModel(tblModel );
        form.getTblbuktisewa().setShowGrid(true);
        form.getTblbuktisewa().setShowVerticalLines(true);
        form.getTblbuktisewa().setGridColor(Color.blue);
    }

    
    public void tampilurutankode(){
        form.getTxtnobsm().setText(model.autonumber2());
    }
   
    SimpleDateFormat tgl = new SimpleDateFormat("yyyy-MM-dd");
    String tanggal;
    public void reset(){
        
        form.getjDatesewa().setDate(null);
        form.getjDatekembali().setDate(null);
        form.getTxttotal().setText("Rp. 0");
        form.getCmbnmplg().setSelectedIndex(0);
        form.getCmbnmcabang().setSelectedIndex(0);
        form.getCmbnmmobil().setSelectedIndex(0);
        form.getTxtkdplg().setText("");
        form.getTxtnonpwp().setText("");
        form.getTxtkdmobil().setText("");
        form.getTxthrgsewa().setText("");
        form.getTxtqty().setText("");
        form.getTxtjmlmobil().setText("");
        tampilurutankode();
        isicombopelanggan();
        isicombocabang();
        tblModel.setRowCount(0);
        form.getTxtkdplg().requestFocus();
        form.getTxtnobsm().setEditable(false);
        //form.getTxttglbsm().setEditable(false);
        form.getTxtjmlmobil().setEditable(false);
    }
    
    public void reset2(){
        form.getTxthrgsewa().setText("");
        form.getTxtqty().setText("");
        form.getTxtjmlmobil().setText("");
        form.getTxtkdmobil().requestFocus();
        form.getTxtjmlmobil().setEditable(false);
    }
    
    public void reset3(){
        form.getCmbnmcabang().setSelectedIndex(0);
        form.getCmbnmmobil().setSelectedIndex(0);
        form.getTxtkdmobil().setText("");
        form.getTxtnonpwp().setText("");
        form.getTxthrgsewa().setText("");
        form.getTxtqty().setText("");
        form.getTxtjmlmobil().setText("");
        form.getTxtnonpwp().requestFocus();
        form.getTxtjmlmobil().setEditable(false);
    }
    
    public void resetrow(){
        int[] selectedRows = form.getTblbuktisewa().getSelectedRows();
        if(selectedRows.length > 0){
            for(int i = selectedRows.length-1; i>=0; i--){
                tblModel.removeRow(selectedRows[i]);
            }
        }
        form.getTxtkdmobil().requestFocus();
    }
    
    //method untuk menambahkan data ke dalam jTable
    public void isiTable(){
        int jmlbaris = tblModel.getRowCount();
        int i,datasama = 0;
        int stok = 1;
        
        tanggalSewa = format.format(form.getjDatesewa().getDate());
        tanggalKembali = format.format(form.getjDatekembali().getDate());
        
       
        //cek inputan qty/jml sewa
        if((form.getTxtqty().getText().isEmpty()==true) || (Integer.parseInt(form.getTxtqty().getText())>Integer.parseInt(form.getTxtjmlmobil().getText()))){
            JOptionPane.showMessageDialog(null, "Quantity/Jumlah tidak boleh kosong atau melebihi stok yang tersedia","Warning",JOptionPane.WARNING_MESSAGE);
            stok=0;
        }
        if(!form.getTxtnonpwp().getText().isEmpty() && stok>0){
            if(jmlbaris==0){
                //jika posisi jTable belum ada isi samasekali
                tblModel.addRow(new Object[]{
                    form.getTxtnonpwp().getText(),
                    form.getCmbnmcabang().getSelectedItem().toString(),
                    form.getTxtkdmobil().getText(),
                    form.getCmbnmmobil().getSelectedItem().toString(),
                    tanggalSewa,
                    tanggalKembali,
                    form.getTxthrgsewa().getText(),
                    form.getTxtqty().getText(),
                    (Integer.parseInt(form.getTxthrgsewa().getText()) * Integer.parseInt(form.getTxtqty().getText()))
                });
            }else{
                //jika sudah ada di dalam jTable, maka cek validasi
                //tidak boleh add kode barang yang sama 
                for(i=0; i<jmlbaris; i++){
                    if(form.getTxtkdmobil().getText().equals(tblModel.getValueAt(i,2).toString())){
                        datasama = 1;
                        JOptionPane.showMessageDialog(null, "Kode mobil "+tblModel.getValueAt(i,2).toString() + 
                                                      " sudah pernah ada, dan akan diupdate dengan data terbaru","Message",JOptionPane.INFORMATION_MESSAGE);
                        tblModel.setValueAt(form.getTxtnonpwp().getText(),i,0);
                        tblModel.setValueAt(form.getCmbnmcabang().getSelectedItem().toString(),i,1);
                        tblModel.setValueAt(form.getTxtkdmobil().getText(),i,2);
                        tblModel.setValueAt(form.getCmbnmmobil().getSelectedItem().toString(),i,3);
                        tblModel.setValueAt(tanggalSewa,i,4);
                        tblModel.setValueAt(tanggalKembali,i,5);
                        tblModel.setValueAt(form.getTxthrgsewa().getText(),i,6);
                        tblModel.setValueAt(form.getTxtqty().getText(),i,7);
                        tblModel.setValueAt((Integer.parseInt(form.getTxthrgsewa().getText())
                                            * Integer.parseInt(form.getTxtqty().getText())),i,6);
                        break;
                    }else{
                        datasama = 0;
                    }
                }
                
                //jika kode barang belum pernah ada di dalam list, maka add data ke dalam jTable
                if(datasama==0){
                    tblModel.addRow(new Object[]{
                        form.getTxtnonpwp().getText(),
                        form.getCmbnmcabang().getSelectedItem().toString(),
                        form.getTxtkdmobil().getText(),
                        form.getCmbnmmobil().getSelectedItem().toString(),
                        tanggalSewa,
                        tanggalKembali,
                        form.getTxthrgsewa().getText(),
                        form.getTxtqty().getText(),
                        (Integer.parseInt(form.getTxthrgsewa().getText()) * Integer.parseInt(form.getTxtqty().getText()))
                    });
                }
            }
        }
    }
    
    public void selisihwaktu(){
        if (form.getjDatesewa().getDate() != null && form.getjDatekembali().getDate() != null)
        {
            java.util.Date datesewa = null;
            java.util.Date datekembali = null;

            datesewa = form.getjDatesewa().getDate();
            datekembali = form.getjDatekembali().getDate();
            
            long sewa = datesewa.getTime();
            long kembali = datekembali.getTime();
            long diff = kembali - sewa;
            long hasil = diff / (24*60*60*1000);
            
            lamaSewa = hasil;
        }
//    
    }
    
    public void hitung_grandtotal(){
        int jmlbaris = tblModel.getRowCount();
        int total = 0;
        int amount = 0;
        DecimalFormat konversi = new DecimalFormat("###,###,###.00");
        for(int i=0; i<tblModel.getRowCount(); i++){
            amount = Integer.parseInt(String.valueOf(tblModel.getValueAt(i,6).toString()));
            total+=amount;
        }
        //jmlbaris!=0 >> jika jTable berisi data
        if(jmlbaris!=0){
            form.getTxttotal().setText("Rp. "+konversi.format(Integer.parseInt(String.valueOf(total))));
        }else{
            form.getTxttotal().setText("Rp. 0");
        }
    }
    
    //method untuk meletakkan data ke dalam text berdasarkan data yang dipilih dr tabel
    public void isiField(int row){
        

        form.getTxtnonpwp().setText(String.valueOf(tblModel.getValueAt(row, 0)));
        form.getCmbnmcabang().setSelectedItem(tblModel.getValueAt(row, 1).toString());
        form.getTxtkdmobil().setText(String.valueOf(tblModel.getValueAt(row, 2)));
        form.getCmbnmmobil().setSelectedItem(tblModel.getValueAt(row, 3).toString());
        tanggalSewa = tblModel.getValueAt(row, 4).toString();
        tanggalKembali = tblModel.getValueAt(row, 5).toString();
        java.util.Date dateSewa = null;
        java.util.Date dateKembali = null;
        
        try
        {
            dateSewa = format.parse(tanggalSewa);
            dateKembali = format.parse(tanggalKembali);
            form.getjDatesewa().setDate(dateSewa);
            form.getjDatekembali().setDate(dateKembali);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        form.getTxthrgsewa().setText(String.valueOf(tblModel.getValueAt(row, 6)));
        form.getTxtqty().setText(String.valueOf(tblModel.getValueAt(row, 7)));        
    }
    
    //=========================Combo pelanggan=========================
    //memanggil method isicombopelanggan pada DAO_BuktiSewa
    //method ini akan digunakan untuk mengisikan data kedalam combo nama pelanggan
    public void isicombopelanggan(){
        form.getCmbnmplg().removeAllItems();
        form.getCmbnmplg().addItem("-Pilih-");
        for(BuktiSewa b : model.isicomboplg()){
            form.getCmbnmplg().addItem(b.getNamaplg());
        }
    }
    
    //memanggil method getkdplg pada DAO_BuktiSewa
    //method ini akan digunakan untuk menampilkan kode pelanggan berdasarkan inputan combo nama pelanggan
    public void tampilkdplg(){
        if(form.getCmbnmplg().getSelectedIndex()!=0){
            for(BuktiSewa b : model.getkdplg(form.getCmbnmplg().getSelectedItem().toString())){
                form.getTxtkdplg().setText(String.valueOf(b.getNoidplg()));
            }
        }
    }
    
    //memanggil method getnmplg pada DAO_BuktiPesan
    //method ini akan digunakan untuk menampilkan nama pelanggan berdasarkan inputan text kode pelanggan
    public void tampilnmplg(){
        for(BuktiSewa b : model.getnmplg(Integer.parseInt(form.getTxtkdplg().getText()))){
            form.getCmbnmplg().setSelectedItem(b.getNamaplg());
        }
    }
    
    //manggil method isicombokategori pada DAO_BuktiPesan
    //method ini digunakan untuk mengisikan data ke dalam combo kode kategori
    public void isicombocabang(){
        form.getCmbnmcabang().removeAllItems();
        form.getCmbnmcabang().addItem("-Pilih-");
        for(BuktiSewa b : model.isicombocabang()){
            form.getCmbnmcabang().addItem(b.getNmcabang());
        }
    }
    
    //manggil method getkdkategori pada DAO_BuktiPesan
    //method ini akan digunakan untuk menampilkan kode kategori berdasarkan inputan combo nama kategori
    public void tampilnocabang(){
        if(form.getCmbnmcabang().getSelectedIndex()!=0){
            for(BuktiSewa b : model.getnonpwp(form.getCmbnmcabang().getSelectedItem().toString())){
                form.getTxtnonpwp().setText(String.valueOf(b.getKdnpwp()));
            }
        }
    }
    
    //manggil method getnmkategori pada DAO_BuktiPesan
    //method ini akan digunakan untuk menampilkan nama kategori berdasarkan inputan text kode kategori
    public void tampilnmcabang(){
        for(BuktiSewa b : model.getnmcabang(form.getTxtnonpwp().getText())){
            form.getCmbnmcabang().setSelectedItem(b.getNmcabang());
        }
    }
    
    //manggil method isicombobarang pada DAO_BuktiPesan
    //method ini digunakan mengisikan data ke dalam combo kode barang berdasarkan parameter kode kategori
    public void isicombomobil(){
        form.getCmbnmmobil().removeAllItems();
        form.getCmbnmmobil().addItem("-Pilih-");
        for(BuktiSewa b : model.isicombomobil(form.getTxtnonpwp().getText())){
            form.getCmbnmmobil().addItem(b.getNmmobil());
        }
    }
    
    //manggil method isicombobarang2 pada DAO_BuktiPesan
    //method ini digunakan mengisikan data ke dalam combo kode barang berdasarkan parameter kode barang
    public void isicombomobil2(){
        form.getCmbnmmobil().removeAllItems();
        form.getCmbnmmobil().addItem("-Pilih-");
        //for(BuktiSewa b : model.isicombomobil(Integer.parseInt(form.getTxtkdmobil().getText()))){
        for(BuktiSewa b : model.isicombomobil(form.getTxtkdmobil().getText())){
            form.getCmbnmmobil().addItem(b.getNmmobil());
            form.getCmbnmmobil().setSelectedItem(b.getNmmobil());
        }
    }
    
    //manggil method getkdbrg pada DAO_BuktiPesan
    //method ini akan digunakan untuk menampilkan data barang berdasarkan inputan combo nama barang
    public void tampilkdmobil(){
        if(form.getCmbnmcabang().getSelectedIndex()!=0){
            for(BuktiSewa b : model.getkdmobil(form.getCmbnmmobil().getSelectedItem().toString())){
                int harga = Integer.parseInt(b.getHargasewa().toString());
                long totalSewa = harga * lamaSewa;
                
                form.getTxtkdmobil().setText(String.valueOf(b.getKdmobil()));
                form.getTxthrgsewa().setText("" + totalSewa);
                form.getTxtjmlmobil().setText(String.valueOf(b.getJmlmobil()));
            }
        }
    }
    
    //manggil method getnmbarang pada DAO_BuktiPesan
    //method ini akan digunakan untuk menampilkan data barang berdasarkan inputan text kode barang
    public void tampilnmmobil(){
        for(BuktiSewa b : model.getnmcabang(form.getTxtnonpwp().getText())){
            form.getCmbnmmobil().setSelectedItem(b.getNmmobil());
            form.getCmbnmcabang().setSelectedItem(b.getNmcabang());
            form.getTxtnonpwp().setText(String.valueOf(b.getKdnpwp()));
            form.getTxthrgsewa().setText(String.valueOf(b.getHargasewa()));
            form.getTxtjmlmobil().setText(String.valueOf(b.getJmlmobil()));
            
            if(b.getKdmobil().equals("")){
                form.getTxtjmlmobil().setText("");
            }
        }
    }
    
    //manggil method simpan_transaksi pada DAO_BuktiPesan
    //method ini digunakan untuk simpan data pada tabel BuktiPesan
    public void simpan_transaksi(){
        int jmlbaris = tblModel.getRowCount();
        BuktiSewa b = new BuktiSewa();
        b.setNosewa(form.getTxtnobsm().getText());
        b.setKdmobil(form.getTxtkdmobil().getText());
        b.setNmcabang(form.getCmbnmcabang().getSelectedItem().toString());
        b.setNoidplg(form.getTxtkdplg().getText());
        b.setNmmobil(form.getCmbnmmobil().getSelectedItem().toString());
        b.setHargasewa(Integer.parseInt(form.getTxthrgsewa().getText()));
        b.setQty(Integer.parseInt(form.getTxtqty().getText()));
        int i = 0;
        for(i=0; i<jmlbaris; i++){
            b.setTotal(Integer.parseInt(String.valueOf(tblModel.getValueAt(i,8).toString())));
        }
        tanggalSewa = format.format(form.getjDatesewa().getDate());
        tanggalKembali = format.format(form.getjDatekembali().getDate());
        b.setTglsewa(tanggalSewa);
        b.setTglkembali(tanggalKembali);
        b.setKdnpwp(form.getTxtnonpwp().getText());
        b.setNoidplg(form.getTxtkdplg().getText());
        model.insert(b);
    }
    
    //manggil method simpan_detiltransaksi pada DAO_BuktiPesan
    //method ini digunakan untuk simpan data pada tabel detilpesan dan mengubah stok pd tabel barang
    public void simpan_detiltransaksi(){
        int jmlbaris = tblModel.getRowCount();
        int simpan = 0;
        int i = 0;
        for(i=0; i<jmlbaris; i++){
            BuktiSewa b = new BuktiSewa();
            b.setNosewa(form.getTxtnobsm().getText());
            b.setKdmobil(tblModel.getValueAt(i,2).toString());
            b.setHargasewa(Integer.parseInt(tblModel.getValueAt(i, 6).toString()));
            b.setQty(Integer.parseInt(tblModel.getValueAt(i, 7).toString()));
            model.insert_detiltransaksi(b);
            model.update_stok(b);
            simpan+=1;
        }
        if(simpan>0){
            JOptionPane.showMessageDialog(null, "Detil sewa berhasil disimpan dan stok mobil sudah diubah","Message",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void cetak(){
        try{
            Connection conn = Koneksi.Database.KoneksiDB();
            String path = "src/Report/LapBuktiSewa.jasper";
            HashMap parameter = new HashMap();
            parameter.put("nobsm",(form.getTxtnobsm().getText()));
            
            JasperReport jp=(JasperReport)JRLoader.loadObject(path);
            JasperPrint print = JasperFillManager.fillReport(jp, parameter, conn);
            JasperViewer.viewReport(print,false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            
            OutputStream output = new FileOutputStream(new File("D:/BuktiSewa"+(form.getTxtnobsm().getText())+".pdf"));
            JasperExportManager.exportReportToPdfStream(print,output);
            
            File xlsx = new File("D:/BuktiSewa"+(form.getTxtnobsm().getText())+".xlsx");
            JRXlsxExporter Xlsxexporter = new JRXlsxExporter();
            Xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
            Xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE,xlsx);
            Xlsxexporter.exportReport();        
           }
        catch(Exception ex){
               JOptionPane.showMessageDialog(null, "Data Tidak Dapat Dicetak!!"+ex.getMessage(),
               "Cetak Data",JOptionPane.ERROR_MESSAGE);
           }
    }
    
}
