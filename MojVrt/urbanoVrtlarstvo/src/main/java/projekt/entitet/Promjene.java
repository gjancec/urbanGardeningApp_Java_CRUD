package projekt.entitet;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Promjene implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime datum;
    private String className;
    private String nazivBiljke;

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getNazivBiljke() {
        return nazivBiljke;
    }

    public void setNazivBiljke(String nazivBiljke) {
        this.nazivBiljke = nazivBiljke;
    }

    @Override
    public String toString() {
        return new StringBuffer(" DatumVrijeme : ").append(this.datum)
                .append(", Class Name : ").append(this.className).append(", Naziv biljke : ").append(this.nazivBiljke)
                .toString();
    }


}
