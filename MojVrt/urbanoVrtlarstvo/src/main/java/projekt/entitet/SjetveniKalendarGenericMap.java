package projekt.entitet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SjetveniKalendarGenericMap <K, V>{


    Map<K, V> mapa=new HashMap<>();

    public SjetveniKalendarGenericMap() {

    }

    public void setMapa(Map<K, V> mapa) {
        this.mapa = mapa;
    }
    public void getMapa(Map<K, V> mapa) {
        this.mapa = mapa;
    }


    @Override
    public String toString() {
        return "SjetveniKalendarGenericMap{" +
                ", mapa=" + mapa +
                '}';
    }
}

