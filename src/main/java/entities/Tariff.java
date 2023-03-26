package entities;

import java.math.BigDecimal;
import java.util.List;

public abstract class Tariff {
    private String name;
    private String index;

    public Tariff(String name,String index) {
        this.name = name;
        this.index = index;
    }

    public abstract boolean checkLimit(double totalTime);

    public abstract BigDecimal countCost(List<Call> calls);

    public void setName(String name) {
        this.name = name;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }


}
