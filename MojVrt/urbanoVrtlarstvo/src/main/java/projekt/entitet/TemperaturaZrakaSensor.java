package projekt.entitet;

public class TemperaturaZrakaSensor implements Sensor{
    public double temperatura;

    public TemperaturaZrakaSensor(double temperatura) {
        this.temperatura = temperatura;
    }

    @Override
    public double getReading() {
        return temperatura;
    }
}
