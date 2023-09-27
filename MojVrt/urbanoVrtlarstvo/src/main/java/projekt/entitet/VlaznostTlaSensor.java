package projekt.entitet;

public class VlaznostTlaSensor implements Sensor {
    private final double vlaga;

    public VlaznostTlaSensor(double vlaga) {
        this.vlaga = vlaga;
    }

    @Override
    public double getReading() {
        return vlaga;
    }
}
