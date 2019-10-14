/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.JOptionPane;



public class MenuUtama extends javax.swing.JFrame {

    /**
     * Creates new form MenuUtama
     */
    public MenuUtama() {
        initComponents();                 
        this.setSize(1000, 600);
        setLocationRelativeTo(this);
        Koneksi.Database.KoneksiDB();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MMenu = new javax.swing.JMenu();
        MMobil = new javax.swing.JMenuItem();
        MRental = new javax.swing.JMenuItem();
        MPelanggan = new javax.swing.JMenuItem();
        MTransaksi = new javax.swing.JMenu();
        TBuktiSewa = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        LMobil = new javax.swing.JMenuItem();
        LPelanggan = new javax.swing.JMenuItem();
        MExit = new javax.swing.JMenu();

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(4000, 3000));
        setResizable(false);
        getContentPane().setLayout(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(2717, 343, 100, 100);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 304, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 295, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2);
        jPanel2.setBounds(2717, 0, 304, 295);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/gambaruas.jpg"))); // NOI18N
        getContentPane().add(jLabel2);
        jLabel2.setBounds(0, 0, 1170, 600);

        MMenu.setBorder(null);
        MMenu.setText("File Master");

        MMobil.setText("Entry Data Mobil");
        MMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MMobilActionPerformed(evt);
            }
        });
        MMenu.add(MMobil);

        MRental.setText("Entry Data Rental");
        MRental.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MRentalActionPerformed(evt);
            }
        });
        MMenu.add(MRental);

        MPelanggan.setText("Entry Data Pelanggan");
        MPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MPelangganActionPerformed(evt);
            }
        });
        MMenu.add(MPelanggan);

        jMenuBar1.add(MMenu);

        MTransaksi.setBorder(null);
        MTransaksi.setText("Transaksi");

        TBuktiSewa.setText("Sewa Mobil");
        TBuktiSewa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TBuktiSewaActionPerformed(evt);
            }
        });
        MTransaksi.add(TBuktiSewa);

        jMenuBar1.add(MTransaksi);

        jMenu2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu2.setText("Laporan");

        LMobil.setText("Laporan Data Mobil");
        LMobil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LMobilActionPerformed(evt);
            }
        });
        jMenu2.add(LMobil);

        LPelanggan.setText("Laporan Data Pelanggan");
        LPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LPelangganActionPerformed(evt);
            }
        });
        jMenu2.add(LPelanggan);

        jMenuBar1.add(jMenu2);

        MExit.setBorder(null);
        MExit.setText("Exit System");
        MExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MExitMouseClicked(evt);
            }
        });
        jMenuBar1.add(MExit);

        setJMenuBar(jMenuBar1);

        setSize(new java.awt.Dimension(498, 391));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void MExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MExitMouseClicked
        int pilih = JOptionPane.showConfirmDialog(null, "Apakah anda yakin ingin keluar ?","warning !",JOptionPane.OK_CANCEL_OPTION);
        if (pilih == JOptionPane.OK_OPTION){
        System.exit(0);
        } else {};
    }//GEN-LAST:event_MExitMouseClicked

    private void MMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MMobilActionPerformed
         View.MMobil P = new View.MMobil();
         P.setVisible(true);   
       //  setLocationRelativeTo(this);
        // P.getTxtmerek().requestFocus();
    }//GEN-LAST:event_MMobilActionPerformed

    private void MRentalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MRentalActionPerformed
         View.MRental P = new View.MRental();
         P.setVisible(true);   
         setLocationRelativeTo(this);  
    }//GEN-LAST:event_MRentalActionPerformed

    private void MPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MPelangganActionPerformed
         View.MPelanggan P = new View.MPelanggan();
         P.setVisible(true);   
         setLocationRelativeTo(this); 
    }//GEN-LAST:event_MPelangganActionPerformed

    private void TBuktiSewaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TBuktiSewaActionPerformed
         View.TBuktiSewa P = new View.TBuktiSewa();
         P.setVisible(true);   
         setLocationRelativeTo(this); 
    }//GEN-LAST:event_TBuktiSewaActionPerformed

    private void LMobilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LMobilActionPerformed
         View.Lap_Mobil P = new View.Lap_Mobil();
         P.setVisible(true);   
         setLocationRelativeTo(this);
    }//GEN-LAST:event_LMobilActionPerformed

    private void LPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LPelangganActionPerformed
         View.Lap_Pelanggan P = new View.Lap_Pelanggan();
         P.setVisible(true);   
         setLocationRelativeTo(this);
    }//GEN-LAST:event_LPelangganActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuUtama.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem LMobil;
    private javax.swing.JMenuItem LPelanggan;
    private javax.swing.JMenu MExit;
    private javax.swing.JMenu MMenu;
    private javax.swing.JMenuItem MMobil;
    private javax.swing.JMenuItem MPelanggan;
    private javax.swing.JMenuItem MRental;
    private javax.swing.JMenu MTransaksi;
    private javax.swing.JMenuItem TBuktiSewa;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
