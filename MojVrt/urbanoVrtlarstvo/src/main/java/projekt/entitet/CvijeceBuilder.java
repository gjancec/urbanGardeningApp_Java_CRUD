package projekt.entitet;

public class CvijeceBuilder {
    private Long id;
    private String naziv;
    private String mjesecSjetve;
    private String bojaCvijeta;
    private String mjeseciCvatnje;
    private Vrsta vrsta;
    private String mjesto;

    public CvijeceBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CvijeceBuilder setNaziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public CvijeceBuilder setMjesecSjetve(String mjesecSjetve) {
        this.mjesecSjetve = mjesecSjetve;
        return this;
    }

    public CvijeceBuilder setBojaCvijeta(String bojaCvijeta) {
        this.bojaCvijeta = bojaCvijeta;
        return this;
    }

    public CvijeceBuilder setMjeseciCvatnje(String mjeseciCvatnje) {
        this.mjeseciCvatnje = mjeseciCvatnje;
        return this;
    }

    public CvijeceBuilder setVrsta(Vrsta vrsta) {
        this.vrsta = vrsta;
        return this;
    }
    public CvijeceBuilder setMjesto(String mjesto) {
        this.mjesto = mjesto;
        return this;
    }

    public Cvijece createCvijece() {
        return new Cvijece(id, naziv, bojaCvijeta,mjesecSjetve , mjeseciCvatnje, vrsta, mjesto);
    }

    @Override
    public String toString() {
        return "CvijeceBuilder{" +
                "naziv='" + naziv + '\'' +
                ", mjesecSjetve='" + mjesecSjetve + '\'' +
                ", bojaCvijeta='" + bojaCvijeta + '\'' +

                ", mjeseciCvatnje='" + mjeseciCvatnje + '\'' +
                ", vrsta=" + vrsta +
                ", mjesto='" + mjesto + '\'' +
                '}';
    }
}