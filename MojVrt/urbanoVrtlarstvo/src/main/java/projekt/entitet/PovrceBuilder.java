package projekt.entitet;

public class PovrceBuilder {
    private Long id;
    private String naziv;
    private String mjesecSjetve;
    private String mjeseciBerbe;
    private String mjesto;

    public PovrceBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public PovrceBuilder setNaziv(String naziv) {
        this.naziv = naziv;
        return this;
    }

    public PovrceBuilder setMjesecSjetve(String mjesecSjetve) {
        this.mjesecSjetve = mjesecSjetve;
        return this;
    }

    public PovrceBuilder setMjeseciBerbe(String mjeseciBerbe) {
        this.mjeseciBerbe = mjeseciBerbe;
        return this;
    }

    public PovrceBuilder setMjesto(String mjesto) {
        this.mjesto = mjesto;
        return this;
    }

    public Povrce createPovrce() {
        return new Povrce(id, naziv, mjesecSjetve, mjeseciBerbe, mjesto);
    }
}