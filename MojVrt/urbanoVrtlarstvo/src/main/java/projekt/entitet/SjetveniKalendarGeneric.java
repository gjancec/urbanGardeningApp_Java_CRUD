package projekt.entitet;

import java.util.List;

public class SjetveniKalendarGeneric<T>{
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
