package projekt.entitet;

public abstract class Biljka {

    private Long id;
    private String naziv;

    public Biljka(Long id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public Biljka() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
