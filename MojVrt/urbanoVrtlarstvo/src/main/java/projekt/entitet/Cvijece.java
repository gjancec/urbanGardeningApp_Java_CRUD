package projekt.entitet;

import java.util.Objects;

public class Cvijece extends Biljka {

    private String mjesecSjetve; //travanj
    private String bojaCvijeta;
    private String mjeseciCvatnje; //kolovoz-rujan
    private Vrsta vrsta; //jednogodisnje, dvogodisnje, trajnica
    private String mjesto; //sjena, polusjena, sunce

    public Cvijece(Long id, String naziv, String bojaCvijeta, String mjesecSjetve,  String mjeseciCvatnje, Vrsta vrsta, String mjesto) {
        super(id, naziv);
        this.mjesecSjetve = mjesecSjetve;
        this.bojaCvijeta = bojaCvijeta;
        this.mjeseciCvatnje = mjeseciCvatnje;
        this.vrsta = vrsta;
        this.mjesto=mjesto;
    }

    public String getMjesto() {
        return mjesto;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }

    public String getMjesecSjetve() {
        return mjesecSjetve;
    }

    public void setMjesecSjetve(String mjesecSjetve) {
        this.mjesecSjetve = mjesecSjetve;
    }

    public String getBojaCvijeta() {
        return bojaCvijeta;
    }

    public void setBojaCvijeta(String bojaCvijeta) {
        this.bojaCvijeta = bojaCvijeta;
    }

    public String getMjeseciCvatnje() {
        return mjeseciCvatnje;
    }

    public void setMjeseciCvatnje(String mjeseciCvatnje) {
        this.mjeseciCvatnje = mjeseciCvatnje;
    }

    public Vrsta getVrsta() {
        return vrsta;
    }

    public void setVrsta(Vrsta vrsta) {
        this.vrsta = vrsta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cvijece cvijece)) return false;
        return Objects.equals(getMjesecSjetve(), cvijece.getMjesecSjetve()) && Objects.equals(getBojaCvijeta(), cvijece.getBojaCvijeta()) && Objects.equals(getMjeseciCvatnje(), cvijece.getMjeseciCvatnje()) && getVrsta() == cvijece.getVrsta() && Objects.equals(getMjesto(), cvijece.getMjesto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMjesecSjetve(), getBojaCvijeta(), getMjeseciCvatnje(), getVrsta(), getMjesto());
    }

    @Override
    public String toString() {
        return super.getNaziv();

    }
}
