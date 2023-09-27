package projekt.entitet;

public class Povrce extends Biljka{
    //private String naziv; // Rajčica,Paprika,Krastavac, Tikvica, Zelena Salata, Rukola
    private String mjesecSjetve;
    private String mjeseciBerbe; //travanj-listopad
    private String mjesto; //sjena, polusjena, sunce

    public Povrce(Long id, String naziv, String mjesecSjetve, String mjeseciBerbe, String mjesto) {
        super(id, naziv);
        this.mjesecSjetve = mjesecSjetve;
        this.mjeseciBerbe = mjeseciBerbe;
        this.mjesto = mjesto;
    }

    public String getMjesecSjetve() {
        return mjesecSjetve;
    }

    public void setMjesecSjetve(String mjesecSjetve) {
        this.mjesecSjetve = mjesecSjetve;
    }

    public String getMjeseciBerbe() {
        return mjeseciBerbe;
    }

    public void setMjeseciBerbe(String mjeseciBerbe) {
        this.mjeseciBerbe = mjeseciBerbe;
    }

    public String getMjesto() {
        return mjesto;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }

    @Override
    public String toString() {
        return super.getNaziv();
    }
}
