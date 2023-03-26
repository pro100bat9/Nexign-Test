package entities.variousTariff;

import entities.Call;
import entities.Tariff;
import java.math.BigDecimal;
import java.util.List;

public class OrdinaryTariff extends Tariff {
    private BigDecimal cost;
    private int limit;

    public OrdinaryTariff(String name, BigDecimal cost, int limit, String index) {
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
        for (Call call : calls){
            double duration = Math.ceil(call.getLength().getSeconds()/60 + 0.5);
            if(call.getType().equals("01") && !checkLimit(totalTime)){
                totalTime += duration;
                call.setCost(BigDecimal.valueOf(duration).multiply(cost));
                totalCost = totalCost.add(call.getCost());
            }
            if(checkLimit(totalTime)){
                call.setCost(BigDecimal.valueOf(duration * 1.5));
                totalCost = totalCost.add(call.getCost());
            }
        }
        return totalCost;

    }
}
