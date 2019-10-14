/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Me
 */
public class Rental {
   private String kdnpwp;
   private String nmcabang;
   private String almtrental;
   private String telprental;
   private Integer jmlmobil;

    public String getNmcabang() {
        return nmcabang;
    }

    public void setNmcabang(String nmcabang) {
        this.nmcabang = nmcabang;
    }

    public String getKdnpwp() {
        return kdnpwp;
    }

    public void setKdnpwp(String kdnpwp) {
        this.kdnpwp = kdnpwp;
    }

    public String getAlmtrental() {
        return almtrental;
    }

    public void setAlmtrental(String almtrental) {
        this.almtrental = almtrental;
    }

    public String getTelprental() {
        return telprental;
    }

    public void setTelprental(String telprental) {
        this.telprental = telprental;
    }

    public Integer getJmlmobil() {
        return jmlmobil;
    }

    public void setJmlmobil(Integer jmlmobil) {
        this.jmlmobil = jmlmobil;
    }
}
