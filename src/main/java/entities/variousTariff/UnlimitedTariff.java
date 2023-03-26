package entities.variousTariff;

import entities.Call;
import entities.Tariff;
import java.math.BigDecimal;
import java.util.List;

public class UnlimitedTariff extends Tariff {
    private BigDecimal cost;
    private int limit;
    public UnlimitedTariff(String name, BigDecimal cost, int limit, String index) {
        super(name, index);
        this.cost = cost;
        this.limit = limit;
    }

    @Override
    public boolean checkLimit(double totalTime) {
        if(limit < totalTime){
            return true;
        }
        return false;
    }

    @Override
    public BigDecimal countCost(List<Call> calls) {
        double totalTime = 0;
        BigDecimal totalCost = BigDecimal.ZERO;
        for(Call call : calls){
            double duration = Math.ceil(call.getLength().getSeconds()/60 + 0.5);
            totalTime += duration;
            if (checkLimit(totalTime)){
                call.setCost(BigDecimal.valueOf(duration));
                totalCost.add(call.getCost());
            }
        }
        return totalCost;
    }
}
